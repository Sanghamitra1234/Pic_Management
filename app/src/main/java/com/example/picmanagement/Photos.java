package com.example.picmanagement;

import android.net.Uri;

public class Photos {
    Photos(){};
    public Uri uri;
    public String name;
    public String bucket_name;
    public String path;

    public Photos(Uri uri, String name, String bucket_name, String path) {
        this.uri = uri;
        this.name = name;
        this.bucket_name = bucket_name;
        this.path=path;
    }
}
