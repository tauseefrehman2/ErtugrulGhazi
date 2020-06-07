package com.example.ertugrulghazi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ertugrulghazi.R;
import com.example.ertugrulghazi.adapter.EpisodeAdapter;
import com.example.ertugrulghazi.fragment.DownloadFragment;
import com.example.ertugrulghazi.fragment.DramasFragment;
import com.example.ertugrulghazi.fragment.FavoriteFragment;
import com.example.ertugrulghazi.models.EpisodeModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private DatabaseReference myRef;

    private SwipeRefreshLayout refreshLayout;


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


        LoadEpisode(seasonName);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadEpisode(seasonName);
            }
        });
    }

    private void LoadEpisode(final String seasonName) {
        refreshLayout.setRefreshing(true);
        myRef = FirebaseDatabase.getInstance().getReference(seasonName.toLowerCase());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                models = new ArrayList<>();
                models.clear();
                Log.d(TAG, "onDataChange: " + dataSnapshot.toString());
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    EpisodeModel model = snapshot.getValue(EpisodeModel.class);
                    model.setSeasonName(seasonName);
                    models.add(model);
                }
                adapter.setDrama(models);
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void init() {

        refreshLayout = findViewById(R.id.episode_refreshLayout);
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

                Toast.makeText(EpisodeActivity.this, "" + pos, Toast.LENGTH_SHORT).show();
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
