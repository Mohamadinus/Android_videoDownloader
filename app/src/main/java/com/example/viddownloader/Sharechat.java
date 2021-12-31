package com.example.viddownloader;


import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class Sharechat extends AppCompatActivity {
    EditText editText;
    Button btn,btn2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharechat);
        editText = findViewById(R.id.sharechatet1);
        btn = findViewById(R.id.sharechatbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                downloadvideo();
            }
        });
        btn2 = findViewById(R.id.sharechatbtn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                editText.setText(clipboard.getText().toString());
            }
        });

    }

    private void downloadvideo() {
        try {
            URL url = new URL(editText.getText().toString());
            String host = url.getHost();
            if (host.contains("sharechat.com")){
                new download().execute(editText.getText().toString());
            }else {
                Toast.makeText(this, "url is invalid", Toast.LENGTH_SHORT).show();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    class download extends AsyncTask<String, Void ,Document> {
        Document  scdoc;
        @Override
        protected Document doInBackground(String... strings) {
            try {
                scdoc = Jsoup.connect(strings[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return scdoc;
        }

        @Override
        protected void onPostExecute(Document document) {
            String videoUrl = Objects.requireNonNull(document.select("meta[property=\"og:video:secure_url\"]").last()).attr("content");
            if (!videoUrl.equals("")){
                utils.download(videoUrl, new File(utils.RootDirectorySharechat),Sharechat.this,"facebook "+System.currentTimeMillis()+".mp4");
            }
            else {
                Toast.makeText(Sharechat.this, "", Toast.LENGTH_SHORT).show();
            }
        }
    }
}