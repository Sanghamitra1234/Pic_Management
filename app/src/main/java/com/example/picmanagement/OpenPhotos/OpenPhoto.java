package com.example.picmanagement.OpenPhotos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.picmanagement.Edit.CropFragment;
import com.example.picmanagement.Edit.EditFragment;
import com.example.picmanagement.Edit.EditPhoto;
import com.example.picmanagement.Edit.HomeFragment;
import com.example.picmanagement.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class OpenPhoto extends AppCompatActivity {
    public static String photoUri="";
    public static int position=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_photo);
        Bundle extras = getIntent().getExtras();
        photoUri = extras.getString("PHOTO_URI").toString();
        position=extras.getInt("PHOTO_URI_POSITION");
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        openFragment(new HomeFragment());



        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.home:
                        openFragment(new HomeFragment());
                        return true;

                    case R.id.crop:
                        openFragment(new CropFragment());
                        return true;

                    case R.id.edit:
                        openFragment(new EditFragment());
                        return true;


                }


                return false;
            }
        });

    }

    void openFragment(Fragment fragment){
        Bundle bundle = new Bundle();
        bundle.putString("PHOTO_OPEN_URI", photoUri);
        bundle.putInt("PHOTO_URI_POSITION",position);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
//        Bundle bundle=getIntent().getExtras();
//        String photoUri=bundle.getString("PHOTO_URI");
//        ImageView imageView=(ImageView)findViewById(R.id.openPhotoId);
//        Button button=(Button) findViewById(R.id.edit_btn);
//        Glide.with(this.getApplicationContext())
//                .load(photoUri)
//                .skipMemoryCache(true)
//                .into(imageView);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Going for Editing !! zoom ", Toast.LENGTH_SHORT).show();
//                Intent newIntent = new Intent(view.getContext(), EditPhoto.class);
//                newIntent.putExtra("PHOTO_URI_EDIT",photoUri);
//                view.getContext().startActivity(newIntent);
//            }
//        });

