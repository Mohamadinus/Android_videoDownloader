package com.example.viddownloader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import at.huber.youtubeExtractor.YouTubeUriExtractor;
import at.huber.youtubeExtractor.YtFile;

public class Youtube extends AppCompatActivity {
Button btn,btn2;
EditText et1;
String youTubeURL = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        et1 = findViewById(R.id.et1);
        btn = findViewById(R.id.btn);
        btn2 = findViewById(R.id.btn2);
        download();
        download2();

    }



    public void YTDownload(final int itag) {
        String VideoURLDownload = youTubeURL;
        @SuppressLint("StaticFieldLeak") YouTubeUriExtractor youTubeUriExtractor = new YouTubeUriExtractor(this) {
            @Override
            public void onUrisAvailable(String videoId, final String videoTitle, SparseArray<YtFile> ytFiles) {
                if ((ytFiles != null)) {
                    String downloadURL = ytFiles.get(itag).getUrl();
                    Log.e("Download URL: ", downloadURL);
                    if(itag==18 || itag == 22) {
                        String mp4=".mp4";
                        DownloadManagingF(downloadURL, videoTitle,mp4);
                    }else if (itag == 251){
                        String mp3=".mp3";
                        DownloadManagingF(downloadURL,videoTitle,mp3);
                    }

                } else Toast.makeText(Youtube.this, "Error With URL", Toast.LENGTH_LONG).show();
            }
        };
        youTubeUriExtractor.execute(VideoURLDownload);
    }

    public void DownloadManagingF(String downloadURL, String videoTitle,String extentiondwn){
        if (downloadURL != null) {
            DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadURL));
            request.setTitle(videoTitle);
            request.setDestinationInExternalPublicDir("/Download/Vid Downloader/", videoTitle + extentiondwn);
            if (downloadManager != null) {
                Toast.makeText(getApplicationContext(),"Downloading...",Toast.LENGTH_SHORT).show();
                downloadManager.enqueue(request);
            }
            BroadcastReceiver onComplete = new BroadcastReceiver() {
                public void onReceive(Context ctxt, Intent intent) {
                    Toast.makeText(getApplicationContext(),"Download Completed",Toast.LENGTH_SHORT).show();

                    Uri selectedUri = Uri.parse(Environment.getExternalStorageDirectory() + "/Download/Vid Downloader/");
                    Intent intentop = new Intent(Intent.ACTION_VIEW);
                    intentop.setDataAndType(selectedUri, "resource/folder");

                    if (intentop.resolveActivityInfo(getPackageManager(), 0) != null)
                    {
                        startActivity(intentop);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Saved on: Download/Vid-Downloader",Toast.LENGTH_LONG).show();
                    }
                    unregisterReceiver(this);
                    finish();
                }
            };
            registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        }
    }

    private void download() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youTubeURL = et1.getText().toString();
                if (youTubeURL.contains("http")||youTubeURL.contains("https")||youTubeURL.contains("youtube.com/shorts")) {
                    YTDownload(22);
                }
                else {
                    Toast.makeText(Youtube.this,"Enter URL First",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void download2() {
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                et1.setText(clipboard.getText().toString());
            }
        });
    }
}