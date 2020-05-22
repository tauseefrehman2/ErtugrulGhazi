package com.example.ertugrulghazi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ertugrulghazi.R;
import com.example.ertugrulghazi.adapter.EpisodeAdapter;
import com.example.ertugrulghazi.fragment.DownloadFragment;
import com.example.ertugrulghazi.fragment.DramasFragment;
import com.example.ertugrulghazi.fragment.FavoriteFragment;
import com.example.ertugrulghazi.models.EpisodeModel;
import com.example.ertugrulghazi.models.FavoriteModel;
import com.example.ertugrulghazi.viewmodel.FavViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.ertugrulghazi.models.Constants.extra_dramaName;
import static com.example.ertugrulghazi.models.Constants.extra_position;
import static com.example.ertugrulghazi.models.Constants.extra_seasonName;

public class EpisodeActivity extends AppCompatActivity {
    private static final String TAG = "EpisodeActivity";

    private EpisodeAdapter adapter;
    private List<EpisodeModel> models;

    private String dramaName = null;
    private String seasonName = null;

    private FavViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode);

        init();
        if (getIntent().hasExtra(extra_dramaName)) {
            dramaName = getIntent().getStringExtra(extra_dramaName);
            seasonName = getIntent().getStringExtra(extra_seasonName);
            setTitle(dramaName);
            getSupportActionBar().setSubtitle(seasonName);
        } else setTitle("Dramas");

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
    }

    private void init() {
        models = new ArrayList<>();
        adapter = new EpisodeAdapter(EpisodeActivity.this);

        RecyclerView recyclerView = findViewById(R.id.episode_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new EpisodeAdapter.DramaListener() {
            @Override
            public void setEpisode(int pos) {

                Intent intent = new Intent(EpisodeActivity.this, PlayEpisodeActivity.class);
                intent.putExtra(extra_dramaName, models.get(pos).getDramaName());
                intent.putExtra(extra_seasonName, seasonName);
                intent.putExtra(extra_position, pos);
                startActivity(intent);
            }
        });
    }


    //bottom navigation click listener
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    //pending jobs
                    switch (item.getItemId()) {
                        case R.id.home_bottom_nav:
                            getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity_container, new DramasFragment()).commit();
                            break;
                        case R.id.download_bottom_nav:
                            getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity_container, new DownloadFragment()).commit();
                            break;
                        case R.id.favorite_bottom_nav:
                            getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity_container, new FavoriteFragment()).commit();
                            break;
                    }
                    return true;
                }
            };


    private void season1() {

        models.add(new EpisodeModel(1, dramaName, seasonName, "Episode1", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/demoVideo.mp4?alt=media&token=0c7fd2f2-f2ac-41f5-a196-0bad280f0b6a"));
        models.add(new EpisodeModel(2, dramaName, seasonName, "Episode2", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video2.mp4?alt=media&token=28ac6d40-0004-47f2-8385-b1c7ecb71bd4"));
        models.add(new EpisodeModel(3, dramaName, seasonName, "Episode3", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video3.mp4?alt=media&token=100c46f3-9442-450b-a636-f0a2d54fc8a3"));
        adapter.setDrama(models);
    }

    private void season2() {

        models.add(new EpisodeModel(4, dramaName, seasonName, "Episode3", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video3.mp4?alt=media&token=100c46f3-9442-450b-a636-f0a2d54fc8a3"));
        models.add(new EpisodeModel(5, dramaName, seasonName, "Episode2", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video2.mp4?alt=media&token=28ac6d40-0004-47f2-8385-b1c7ecb71bd4"));
        models.add(new EpisodeModel(6, dramaName, seasonName, "Episode1", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/demoVideo.mp4?alt=media&token=0c7fd2f2-f2ac-41f5-a196-0bad280f0b6a"));
        adapter.setDrama(models);
    }

    private void season3() {

        models.add(new EpisodeModel(7, dramaName, seasonName, "Episode2", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video2.mp4?alt=media&token=28ac6d40-0004-47f2-8385-b1c7ecb71bd4"));
        models.add(new EpisodeModel(8, dramaName, seasonName, "Episode1", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/demoVideo.mp4?alt=media&token=0c7fd2f2-f2ac-41f5-a196-0bad280f0b6a"));
        models.add(new EpisodeModel(9, dramaName, seasonName, "Episode3", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video3.mp4?alt=media&token=100c46f3-9442-450b-a636-f0a2d54fc8a3"));
        adapter.setDrama(models);
    }

    private void season4() {

        models.add(new EpisodeModel(11, dramaName, seasonName, "Episode2", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video2.mp4?alt=media&token=28ac6d40-0004-47f2-8385-b1c7ecb71bd4"));
        models.add(new EpisodeModel(12, dramaName, seasonName, "Episode3", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video3.mp4?alt=media&token=100c46f3-9442-450b-a636-f0a2d54fc8a3"));
        models.add(new EpisodeModel(13, dramaName, seasonName, "Episode1", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/demoVideo.mp4?alt=media&token=0c7fd2f2-f2ac-41f5-a196-0bad280f0b6a"));
        adapter.setDrama(models);
    }

    private void season5() {

        models.add(new EpisodeModel(15, dramaName, seasonName, "Episode1", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/demoVideo.mp4?alt=media&token=0c7fd2f2-f2ac-41f5-a196-0bad280f0b6a"));
        models.add(new EpisodeModel(16, dramaName, seasonName, "Episode3", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video3.mp4?alt=media&token=100c46f3-9442-450b-a636-f0a2d54fc8a3"));
        models.add(new EpisodeModel(17, dramaName, seasonName, "Episode2", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video2.mp4?alt=media&token=28ac6d40-0004-47f2-8385-b1c7ecb71bd4"));
        adapter.setDrama(models);
    }
}
