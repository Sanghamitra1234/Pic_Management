package com.example.picmanagement.OpenPhotos;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.picmanagement.R;


public class OpenPhoto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_photo);

        Bundle bundle=getIntent().getExtras();
        String photoUri=bundle.getString("PHOTO_URI");
        ImageView imageView=(ImageView)findViewById(R.id.openPhotoId);
        Glide.with(this.getApplicationContext())
                .load(photoUri)
                .skipMemoryCache(true)
                .into(imageView);
    }
}