package com.example.ertugrulghazi.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ertugrulghazi.R;
import com.example.ertugrulghazi.models.EpisodeModel;
import com.khizar1556.mkvideoplayer.MKPlayer;

import java.util.ArrayList;
import java.util.List;

import static com.example.ertugrulghazi.models.Constants.extra_dramaName;
import static com.example.ertugrulghazi.models.Constants.extra_position;
import static com.example.ertugrulghazi.models.Constants.extra_seasonName;

public class PlayEpisodeActivity extends AppCompatActivity {

    private static final String TAG = "PlayEpisodeActivity";

    private MKPlayer mkplayer;
    private List<EpisodeModel> models;
    private String dramaName;
    private String seasonName;
    private int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_episode);

        init();
        if (getIntent().hasExtra(extra_position)) {
            seasonName = getIntent().getStringExtra(extra_seasonName);
            dramaName = getIntent().getStringExtra(extra_dramaName);
            pos = getIntent().getIntExtra(extra_position, 0);
        }

        if (seasonName != null) {
            if (seasonName.equalsIgnoreCase("season 1"))
                season1();
            if (seasonName.equalsIgnoreCase("season 2"))
                season2();
            if (seasonName.equalsIgnoreCase("season 3"))
                season3();
            if (seasonName.equalsIgnoreCase("season 4"))
                season4();
            if (seasonName.equalsIgnoreCase("season 5"))
                season5();
        }

        mkplayer.play(models.get(pos).getUrl());
        mkplayer.setTitle(models.get(pos).getEpisodeName().concat(" ").concat(models.get(pos).getDramaName()));

        mkplayer.setPlayerCallbacks(new MKPlayer.playerCallbacks() {
            @Override
            public void onNextClick() {
                pos += 1;
                if (pos <= models.size() - 1) {
                    mkplayer.play(models.get(pos).getUrl());
                    mkplayer.setTitle(models.get(pos).getEpisodeName().concat(" ").concat(models.get(pos).getDramaName()));
                } else
                    Toast.makeText(PlayEpisodeActivity.this, "No more videos", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPreviousClick() {
                pos -= 1;
                if (pos >= 0) {
                    mkplayer.play(models.get(pos).getUrl());
                    mkplayer.setTitle(models.get(pos).getEpisodeName().concat(" ").concat(models.get(pos).getDramaName()));
                } else
                    Toast.makeText(PlayEpisodeActivity.this, "No more videos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged: called");
        super.onConfigurationChanged(newConfig);
        if (mkplayer != null) {
            mkplayer.onConfigurationChanged(newConfig);
        }
    }

    private void init() {
        pos = 0;
        models = new ArrayList<>();
        mkplayer = new MKPlayer(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mkplayer != null) {
            mkplayer.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mkplayer != null) {
            mkplayer.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mkplayer != null) {
            mkplayer.onDestroy();
        }
    }

    @Override
    public void onBackPressed() {

        if (mkplayer != null && mkplayer.onBackPressed()) {
            finish();
            return;
        } else {
            mkplayer.stop();
            finish();
        }
        super.onBackPressed();
    }

    private void season1() {

        models.add(new EpisodeModel(1, dramaName, seasonName, "Episode1", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/demoVideo.mp4?alt=media&token=0c7fd2f2-f2ac-41f5-a196-0bad280f0b6a"));
        models.add(new EpisodeModel(2, dramaName, seasonName, "Episode2", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video2.mp4?alt=media&token=28ac6d40-0004-47f2-8385-b1c7ecb71bd4"));
        models.add(new EpisodeModel(3, dramaName, seasonName, "Episode3", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video3.mp4?alt=media&token=100c46f3-9442-450b-a636-f0a2d54fc8a3"));
    }

    private void season2() {
        models.add(new EpisodeModel(4, dramaName, seasonName, "Episode3", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video3.mp4?alt=media&token=100c46f3-9442-450b-a636-f0a2d54fc8a3"));
        models.add(new EpisodeModel(5, dramaName, seasonName, "Episode2", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video2.mp4?alt=media&token=28ac6d40-0004-47f2-8385-b1c7ecb71bd4"));
        models.add(new EpisodeModel(6, dramaName, seasonName, "Episode1", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/demoVideo.mp4?alt=media&token=0c7fd2f2-f2ac-41f5-a196-0bad280f0b6a"));

    }

    private void season3() {

        models.add(new EpisodeModel(7, dramaName, seasonName, "Episode2", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video2.mp4?alt=media&token=28ac6d40-0004-47f2-8385-b1c7ecb71bd4"));
        models.add(new EpisodeModel(8, dramaName, seasonName, "Episode1", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/demoVideo.mp4?alt=media&token=0c7fd2f2-f2ac-41f5-a196-0bad280f0b6a"));
        models.add(new EpisodeModel(9, dramaName, seasonName, "Episode3", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video3.mp4?alt=media&token=100c46f3-9442-450b-a636-f0a2d54fc8a3"));

    }

    private void season4() {

        models.add(new EpisodeModel(11, dramaName, seasonName, "Episode2", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video2.mp4?alt=media&token=28ac6d40-0004-47f2-8385-b1c7ecb71bd4"));
        models.add(new EpisodeModel(12, dramaName, seasonName, "Episode3", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video3.mp4?alt=media&token=100c46f3-9442-450b-a636-f0a2d54fc8a3"));
        models.add(new EpisodeModel(13, dramaName, seasonName, "Episode1", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/demoVideo.mp4?alt=media&token=0c7fd2f2-f2ac-41f5-a196-0bad280f0b6a"));

    }

    private void season5() {

        models.add(new EpisodeModel(15, dramaName, seasonName, "Episode1", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/demoVideo.mp4?alt=media&token=0c7fd2f2-f2ac-41f5-a196-0bad280f0b6a"));
        models.add(new EpisodeModel(16, dramaName, seasonName, "Episode3", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video3.mp4?alt=media&token=100c46f3-9442-450b-a636-f0a2d54fc8a3"));
        models.add(new EpisodeModel(17, dramaName, seasonName, "Episode2", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video2.mp4?alt=media&token=28ac6d40-0004-47f2-8385-b1c7ecb71bd4"));

    }
}
