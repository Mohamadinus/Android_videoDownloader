package com.example.viddownloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Whatsapp extends AppCompatActivity {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    File[]files;
    ArrayList<Status> listFile = new ArrayList<>();
    StatusAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatsapp);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        swipeRefreshLayout = findViewById(R.id.refresh);
        CheckPermission();
//        playVideo();
        StatusView();
    }



    private void CheckPermission() {
        Dexter.withContext(this)
                .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.INTERNET)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        StatusList();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                        StatusList();
                    }
                }).check();
    }

    private void  StatusView() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                StatusList();
                swipeRefreshLayout.setRefreshing(true);
                {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }, 2000);
                }
            }
        });

    }


    private void StatusList() {
        listFile.clear();
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter= new StatusAdapter(Whatsapp.this,getData());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private ArrayList<Status> getData() {
        Status status;
        String filepath = Environment.getExternalStorageDirectory().getAbsolutePath()+Constant.FOLDER_NAME+"Media/.Statuses";
        File file = new File(filepath);
        files = file.listFiles();
        for(int i = 0; i< files.length; i++){
            File file1 = files[i];
            status =new Status();
            status.setUri(Uri.fromFile(file1));
            status.setFilePath(files[i].getAbsolutePath());
            status.setFileName(file1.getName());

            if (!status.getUri().toString().endsWith(".nomedia")){
                listFile.add(status);
            }
        }

        return listFile;
    }

}