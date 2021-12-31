package com.example.viddownloader;

import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hcr2bot.instagramvideosdownloader.InstaVideo;

public class Instagram extends AppCompatActivity {
EditText editText;
Button button1,button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram);
        editText = findViewById(R.id.instagramet1);
        button1 = findViewById(R.id.instagrambtn);
        button2 = findViewById(R.id.instagrambtn2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InstaVideo.downloadVideo(getApplicationContext(), editText.getText().toString());
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                if(clipboard != null) {
                    editText.setText(clipboard.getText().toString());
                }else {
                    Toast.makeText(getApplicationContext(), "No url found", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}