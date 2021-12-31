package com.example.viddownloader;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import android.widget.Toast;

import java.io.File;

public class utils {
    public static String RootDirectorySharechat = "/Vid Downloader/Sharechat videos/";
    public static String RootDirectoryinstagram = "/Vid Downloader/Instagram videos/";
    public static File RootDirectory = new File(Environment.getExternalStorageDirectory()+"/Download/Vid Downloader/Facebook videos/");
    public static void createFileFolder(){
        if (!RootDirectory.exists()){
            RootDirectory.mkdirs();
        }
    }

    public static void download(String downloadPath, File destinationPath, Context context, String filename){
        Toast.makeText(context, "Starting Download", Toast.LENGTH_SHORT).show();
        Uri uri = Uri.parse(downloadPath);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setTitle(filename);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,destinationPath+filename);
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
    }
}
