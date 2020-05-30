package com.example.ertugrulghazi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ertugrulghazi.R;
import com.example.ertugrulghazi.adapter.EpisodeAdapter;
import com.example.ertugrulghazi.database.Er_Database;
import com.example.ertugrulghazi.fragment.DownloadFragment;
import com.example.ertugrulghazi.fragment.DramasFragment;
import com.example.ertugrulghazi.fragment.FavoriteFragment;
import com.example.ertugrulghazi.models.EpisodeModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import static com.example.ertugrulghazi.models.Constants.extra_dramaName;
import static com.example.ertugrulghazi.models.Constants.extra_position;
import static com.example.ertugrulghazi.models.Constants.extra_seasonName;

public class EpisodeActivity extends AppCompatActivity {
    private static final String TAG = "EpisodeActivity";

    private EpisodeAdapter adapter;
    private List<EpisodeModel> models;
    private String seasonName = null;

    private Er_Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode);

        init();
        if (getIntent().hasExtra(extra_dramaName)) {
            seasonName = getIntent().getStringExtra(extra_seasonName);
            setTitle("Ertugrul Ghazi");
            getSupportActionBar().setSubtitle(seasonName);
        } else setTitle("Dramas");

        adapter.setDrama(db.episodeDAO().getAllEpisodeBySeason(seasonName));
    }

    private void init() {
        db = Er_Database.getInstance(EpisodeActivity.this);
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
}
