package com.example.viddownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipboardManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Facebook extends AppCompatActivity {
EditText editText;
Button btn,btn2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
        editText = findViewById(R.id.facebooket1);
        btn = findViewById(R.id.facebookbtn);
        btn2 = findViewById(R.id.facebookbtn2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                downloadvideo();
            }
        });
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
            if (host.contains("facebook.com")){
                new download().execute(editText.getText().toString());
            }else {
                Toast.makeText(this, "url is invalid", Toast.LENGTH_SHORT).show();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    class download extends AsyncTask<String, Void , Document>{
        Document fbdoc;
        @Override
        protected Document doInBackground(String... strings) {
            try {
                fbdoc = Jsoup.connect(strings[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fbdoc;
        }

        @Override
        protected void onPostExecute(Document document) {
            String videoUrl = document.select("meta[property=\"og:video\"]").last().attr("content");
            if (!videoUrl.equals("")){
                utils.download(videoUrl,utils.RootDirectory,Facebook.this,"facebook "+System.currentTimeMillis()+".mp4");
            }else {
                Toast.makeText(Facebook.this, "", Toast.LENGTH_SHORT).show();
            }
        }
    }
}