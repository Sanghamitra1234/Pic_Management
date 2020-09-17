package com.example.picmanagement.Edit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.picmanagement.R;

import iamutkarshtiwari.github.io.ananas.editimage.EditImageActivity;
import iamutkarshtiwari.github.io.ananas.editimage.ImageEditorIntentBuilder;

public class EditPhoto extends AppCompatActivity {
    private final int PHOTO_EDITOR_REQUEST_CODE = 231;// Any integer value as a request code.\
    private static String photoUri="";
    private static String outputUri="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);
        Bundle bundle=getIntent().getExtras();
        photoUri=bundle.getString("PHOTO_URI_EDIT");
        ImageView imageView=(ImageView)findViewById(R.id.editPhotoId);
        Glide.with(this.getApplicationContext())
                .load(photoUri)
                .skipMemoryCache(true)
                .into(imageView);
        try {

            Intent intent = new ImageEditorIntentBuilder(getApplicationContext(), photoUri, photoUri)
                    .withAddText() // Add the features you need
                    .withPaintFeature()
                    .withFilterFeature()
                    .withRotateFeature()
                    .withCropFeature()
                    .withBrightnessFeature()
                    .withSaturationFeature()
                    .withBeautyFeature()
                    .withStickerFeature()
                    .forcePortrait(true)  // Add this to force portrait mode (It's set to false by default)
                    .setSupportActionBarVisibility(false) // To hide app's default action bar
                    .withSourcePath(photoUri)
                    .build();


            EditImageActivity.start(this, intent, PHOTO_EDITOR_REQUEST_CODE);
        } catch (Exception e) {
            Log.e("Demo App", e.getMessage()); // This could throw if either `sourcePath` or `outputPath` is blank or Null
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PHOTO_EDITOR_REQUEST_CODE) { // same code you used while starting
            String newFilePath = data.getStringExtra(outputUri);
           // boolean isImageEdit = data.getBooleanExtra(EditImageActivity.IMAGE_IS_EDIT, false);
        }
    }
}