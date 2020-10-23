package com.example.viewpagervideoplayer.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viewpagervideoplayer.R;
import com.example.viewpagervideoplayer.model.Video;

import java.util.List;

public class VideoPlayerAdapter extends RecyclerView.Adapter<VideoPlayerAdapter.VideoViewHolder> {
    public static Context context;
    private final List<Video> videoList;

    public VideoPlayerAdapter(Context context, List<Video> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(
                                R.layout.video_item,
                                parent,
                                false
                        )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.textViewVideoTitle.setText(videoList.get(position).getVideoTitle());
        holder.setVideoData(videoList.get(position));
    }


    @Override
    public int getItemCount() {
        return videoList.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewVideoTitle;
        VideoView videoView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.id_vw);
            textViewVideoTitle = itemView.findViewById(R.id.id_tv_video_title);

        }

        void setVideoData(Video video) {
            textViewVideoTitle.setText(video.getVideoTitle());

            MediaController controller = new MediaController(context);
            controller.setMediaPlayer(videoView);
            videoView.setMediaController(controller);

            videoView.setVideoPath(String.valueOf(video.getVideoUri()));
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.start();
                }
            });
        }
    }
}
