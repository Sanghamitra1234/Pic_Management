package com.example.picmanagement;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.picmanagement.Photo.PhotoActivity;

import java.util.List;


public class FolderAdapter extends RecyclerView.Adapter <FolderAdapter.FolderViewHolder> {
    List<String> folderNames;
    Context context;
    public FolderAdapter(Context context, List<String> folderNames){
        this.context=context;
        this.folderNames=folderNames;
    }
    public class FolderViewHolder extends RecyclerView.ViewHolder {
        public TextView FolderTitleSingle;

        public FolderViewHolder(View view) {
            super(view);
            FolderTitleSingle=(TextView) view.findViewById(R.id.folderName);
        }
    }


    @NonNull
    @Override
    public FolderAdapter.FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.folder_view_rows, parent, false);

        return new FolderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FolderAdapter.FolderViewHolder holder,  int position) {
        holder.FolderTitleSingle.setText(folderNames.get(position));
        String s=folderNames.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent newIntent = new Intent(view.getContext(), PhotoActivity.class);
                newIntent.putExtra("FOLDER_NAME",s);
                view.getContext().startActivity(newIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return folderNames.size();
    }
}
