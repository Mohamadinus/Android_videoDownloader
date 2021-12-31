package com.example.viddownloader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {
    Context context;
    ArrayList<Status> list;

    public StatusAdapter(Context context, ArrayList<Status> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Status status = list.get(position);
        if (status.getUri().toString().endsWith(".mp4")){
            holder.play.setVisibility(View.VISIBLE);

        }else {
            holder.play.setVisibility(View.INVISIBLE);
        }
        Glide.with(context).load(status.getUri()).into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.getUri().toString().endsWith(".mp4")) {
                    String destpath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.SAVE_FOLDER_NAME;
                    Intent intent = new Intent(context, VideoPlayer.class);
                    intent.putExtra("VIDEO", status.getUri().toString());


                    context.startActivity(intent);
                }
            }
        });

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckFolder();
                String path = list.get(position).getFilePath();
                File filepath = new File(path);
                String destpath = Environment.getExternalStorageDirectory().getAbsolutePath()+Constant.SAVE_FOLDER_NAME;
                File destfile = new File(destpath);

                try {
                    FileUtils.copyFileToDirectory(filepath,destfile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MediaScannerConnection.scanFile(
                        context,
                        new String[]{destpath+status.getFileName()},
                        new String[]{"*/*"},
                        new MediaScannerConnection.MediaScannerConnectionClient() {
                            @Override
                            public void onMediaScannerConnected() {

                            }

                            @Override
                            public void onScanCompleted(String s, Uri uri) {

                            }
                        }
                );
                Toast.makeText(context, "Status Saved"+destpath , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void CheckFolder() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+Constant.SAVE_FOLDER_NAME;
        File file = new File(path);
        boolean isDirectory = file.exists();
        if (!isDirectory){
            isDirectory = file.mkdir();
        }
        if (isDirectory){
            Log.d("myapp","right ");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image,download,play;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image =itemView.findViewById(R.id.image);
            download = itemView.findViewById(R.id.download);
            play = itemView.findViewById(R.id.playicon);
        }
    }
}
