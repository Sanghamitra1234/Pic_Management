package com.example.picmanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    public static List<String> intBucketNames;
    public static List<String> extBucketNames;
    public static ArrayList<Image> imageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestStoragePermission();
        Image images = new Image();
        imageList=new ArrayList<>();
        intBucketNames=getFolders(images.cursorInt,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
       extBucketNames=getFolders(images.cursorExt,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.folderView);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //Send data to adapter
        FolderAdapter folderAdapter = new FolderAdapter(MainActivity.this, intBucketNames);
        recyclerView.setAdapter(folderAdapter); // set the Adapter to RecyclerView
        if(extBucketNames.size()!=0){
            FolderAdapter folderAdapter1 = new FolderAdapter(MainActivity.this, extBucketNames);
            recyclerView.setAdapter(folderAdapter1);
        }

    }

    public List<String> getFolders(Cursor c, Uri uri){
        List<String> folder=new ArrayList<>();
        Image i=new Image();
        int idColumn = c.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
        int nameColumn = c.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
        int bucketName=c.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

        while(c.moveToNext()) {
            String folderNameSingle=c.getString(bucketName);
            String name=c.getString(nameColumn);
            long id=c.getLong(idColumn);
            //Uri contentUri = ContentUris.withAppendedId(uri, id);
           // Log.v("cursor", folderNameSingle+" "+id+" "+nameColumn);
            imageList.add(new Image(null,name,folderNameSingle));
            if (!folder.contains(folderNameSingle)) {
                folder.add(folderNameSingle);
            }


        }
        return folder;
    }
    private void requestStoragePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    class Image {
        public Uri uri;
        public String name;
        public String bucket_name;

        public Image() {}

        public Image(Uri uri, String name, String bucket_name) {
            this.uri = uri;
            this.name = name;
            this.bucket_name = bucket_name;
        }



        Cursor cursorInt = getContentResolver().query(
                MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                null, "_data IS NOT NULL) GROUP BY (bucket_display_name",
                null, null);

        Cursor cursorExt = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, "_data IS NOT NULL) GROUP BY (bucket_display_name",
                null, null);
    }

}