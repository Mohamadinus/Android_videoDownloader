package com.example.viddownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permission();
    }

    public void layout1(View view) {
        Intent intent = new Intent(MainActivity.this,Youtube.class);
        startActivity(intent);
    }

    public void layout2(View view) {
        Intent intent = new Intent(MainActivity.this,Whatsapp.class);
        startActivity(intent);
    }

    public void layout3(View view) {
        Intent intent = new Intent(MainActivity.this,Instagram.class);
        startActivity(intent);
    }

    public void layout4(View view) {
        Intent intent = new Intent(MainActivity.this,Sharechat.class);
        startActivity(intent);
    }

    public void layout5(View view) {
        Intent intent = new Intent(MainActivity.this,Facebook.class);
        startActivity(intent);
    }
    private void permission() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(MainActivity.this, "Permission required", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }
}