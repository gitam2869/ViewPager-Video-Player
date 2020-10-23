package com.example.viewpagervideoplayer.model;

import android.net.Uri;

public class Video {
    private long videoId;
    private String videoTitle;
    private Uri videoUri;

    public Video(long videoId, String videoTitle, Uri videoUri) {
        this.videoId = videoId;
        this.videoTitle = videoTitle;
        this.videoUri = videoUri;
    }

    public long getVideoId() {
        return videoId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public Uri getVideoUri() {
        return videoUri;
    }
}
