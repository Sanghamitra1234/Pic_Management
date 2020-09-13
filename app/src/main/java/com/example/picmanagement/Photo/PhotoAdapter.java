package com.example.picmanagement.Photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.picmanagement.FolderAdapter;
import com.example.picmanagement.R;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter <PhotoAdapter.PhotoViewHolder>{
    ArrayList<PhotoActivity.Photo> photoPath;
    Context context;

    public PhotoAdapter(Context context, ArrayList<PhotoActivity.Photo> photoPath){
        this.context=context;
        this.photoPath=photoPath;
    }
    public class PhotoViewHolder extends  RecyclerView.ViewHolder {
        public ImageView folderPhotos;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            folderPhotos=(ImageView)itemView.findViewById(R.id.folderEachImages);
        }
    }
    @NonNull
    @Override
    public PhotoAdapter.PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_view_for_each_folder, parent, false);

        return new PhotoAdapter.PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.PhotoViewHolder holder, int position) {
        Glide.with(context)
                .load(photoPath.get(position).path)
                .skipMemoryCache(true)
                .into(holder.folderPhotos);
//        Bitmap bitmap = BitmapFactory.decodeFile(photoPath.get(position).path);
//        holder.folderPhotos.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return photoPath.size();
    }


}
