package com.example.viewpagervideoplayer;

import android.Manifest;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.viewpagervideoplayer.adapter.VideoPlayerAdapter;
import com.example.viewpagervideoplayer.model.Video;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 111;
    private ViewPager2 viewPager2Video;
    private TextView textView;
    private MaterialButton materialButton;
    private List<Video> videoList;
    private VideoPlayerAdapter videoPlayerAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2Video = findViewById(R.id.id_vp);
        textView = findViewById(R.id.tv_permission);
        materialButton = findViewById(R.id.id_button);
        videoList = new ArrayList<>();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_REQUEST_CODE);
        } else {
            setAdapter();
            textView.setVisibility(View.GONE);
            materialButton.setVisibility(View.GONE);
        }

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePermission();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void takePermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_REQUEST_CODE);
        } else {
            setAdapter();
            textView.setVisibility(View.GONE);
            materialButton.setVisibility(View.GONE);
        }
    }

    private void setAdapter() {
        videoList = getAllVideo();
        videoPlayerAdapter = new VideoPlayerAdapter(this, videoList);
        viewPager2Video.setAdapter(videoPlayerAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    setAdapter();
                    textView.setVisibility(View.GONE);
                    materialButton.setVisibility(View.GONE);
                } else {
                    textView.setVisibility(View.VISIBLE);
                    materialButton.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public ArrayList<Video> getAllVideo() {
        ArrayList<Video> list = new ArrayList<>();
        String[] projection = {MediaStore.Video.Media._ID, MediaStore.Video.Media.DISPLAY_NAME};
        Cursor cursor = getApplication().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        if (cursor != null) {
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
            int titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
            while (cursor.moveToNext()) {
                long id = cursor.getLong(idColumn);
                String title = cursor.getString(titleColumn);
                Uri data = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);

                list.add(new Video(id, title, data));
            }
        }
        return list;
    }
}