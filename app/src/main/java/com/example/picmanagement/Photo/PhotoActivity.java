package com.example.picmanagement.Photo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentUris;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.example.picmanagement.R;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.List;

import static com.example.picmanagement.MainActivity.imageList;

public class PhotoActivity extends AppCompatActivity {
    class Photo{
        public Photo(){};
        public Uri uri;
        public String name;
        public String bucket_name;
        public String path;

        public Photo(Uri uri, String name, String bucket_name, String path) {
            this.uri = uri;
            this.name = name;
            this.bucket_name = bucket_name;
            this.path=path;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        Bundle extras = getIntent().getExtras();
        String bucketName = extras.getString("FOLDER_NAME").toString();
        //Log.v("linkedFile", bucketName);
        ArrayList<Photo> imageList = new ArrayList<>();
        getImages(bucketName,imageList);

        for(int i=0;i<imageList.size();i++){
            Log.v("imgList", imageList.get(i).bucket_name+" "+imageList.get(i).uri+" "+imageList.get(i).path);
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        PhotoAdapter photoAdapter = new PhotoAdapter(this, imageList);
        recyclerView.setAdapter(photoAdapter);
    }
    public void getImages(String album_name, ArrayList<Photo> imageList){
        Uri uriExternal= MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri uriInternal=MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        Cursor cursorExternal = getContentResolver().query(uriExternal, null, "bucket_display_name = \"" + album_name + "\"", null, null);
        Cursor cursorInternal = getContentResolver().query(uriInternal, null, "bucket_display_name = \"" + album_name + "\"", null, null);
        Cursor cursor = new MergeCursor(new Cursor[]{cursorExternal, cursorInternal});

        int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
        int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
        int bucketName=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        int pathId=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        while(cursor.moveToNext()) {
            String folderNameSingle=cursor.getString(bucketName);
            String name=cursor.getString(nameColumn);
            long id=cursor.getLong(idColumn);
            String path=cursor.getString(pathId);

            Uri contentUri = ContentUris.withAppendedId(uriExternal, id); // Have to change soon

            imageList.add(new Photo(contentUri,name,folderNameSingle,path));

        }
    }
}