package com.example.ertugrulghazi.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.ertugrulghazi.R;
import com.example.ertugrulghazi.activities.EpisodeActivity;
import com.example.ertugrulghazi.activities.MainActivity;
import com.example.ertugrulghazi.adapter.SeasonAdapter;
import com.example.ertugrulghazi.models.SeasonModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.ertugrulghazi.models.Constants.extra_seasonName;
import static com.example.ertugrulghazi.models.Constants.extra_dramaName;

public class DramasFragment extends Fragment {

    private RecyclerView recyclerView;
    private SeasonAdapter adapter;
    private List<SeasonModel> models;
    private SwipeRefreshLayout refreshLayout;

    public DramasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dramas, container, false);

        initView(view);

        LoadSeason();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadSeason();
            }
        });

        adapter.setOnItemClickListener(new SeasonAdapter.SeasonListener() {
            @Override
            public void setAchievement(SeasonModel model) {

                Intent intent = new Intent(getContext(), EpisodeActivity.class);
                intent.putExtra(extra_dramaName, model.getDramaName());
                intent.putExtra(extra_seasonName, model.getSeasonName());

                startActivity(intent);
            }
        });


        return view;
    }

    private static final String TAG = "DramasFragment";

    private void LoadSeason() {

        refreshLayout.setRefreshing(true);

        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                models = new ArrayList<>();
                models.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.d(TAG, "onDataChange: " + ds.getKey().substring(0, 6));
                    if (ds.getKey().substring(0, 6).toLowerCase().equals("season"))
                        models.add(new SeasonModel(R.drawable.demo, "Ertugrul Ghazi", ds.getKey()));
                }
                adapter.setDrama(models);
                recyclerView.setAdapter(adapter);
                refreshLayout.setRefreshing(false);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                refreshLayout.setRefreshing(false);

            }
        });
    }

    private void initView(View view) {

        Objects.requireNonNull(((MainActivity) Objects.requireNonNull(getActivity())).getSupportActionBar())
                .setTitle("Ertugrul Ghazi");
        Objects.requireNonNull(((MainActivity) Objects.requireNonNull(getActivity())).getSupportActionBar())
                .setSubtitle("");

        recyclerView = view.findViewById(R.id.season_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new SeasonAdapter();
        refreshLayout = view.findViewById(R.id.season_refLayout);
    }
}
