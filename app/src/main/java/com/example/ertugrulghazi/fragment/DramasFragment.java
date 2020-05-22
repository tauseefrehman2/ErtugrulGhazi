package com.example.ertugrulghazi.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ertugrulghazi.R;
import com.example.ertugrulghazi.activities.EpisodeActivity;
import com.example.ertugrulghazi.activities.MainActivity;
import com.example.ertugrulghazi.adapter.SeasonAdapter;
import com.example.ertugrulghazi.models.SeasonModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.ertugrulghazi.models.Constants.extra_seasonName;
import static com.example.ertugrulghazi.models.Constants.extra_dramaName;

public class DramasFragment extends Fragment {

    private RecyclerView recyclerView;
    private SeasonAdapter adapter;
    private List<SeasonModel> models;

    public DramasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dramas, container, false);

        initView(view);

        models.add(new SeasonModel(R.drawable.demo, "Ertugrul Ghazi", "Season 1"));
        models.add(new SeasonModel(R.drawable.demo, "Ertugrul Ghazi", "Season 2"));
        models.add(new SeasonModel(R.drawable.demo, "Ertugrul Ghazi", "Season 3"));
        models.add(new SeasonModel(R.drawable.demo, "Ertugrul Ghazi", "Season 4"));
        models.add(new SeasonModel(R.drawable.demo, "Ertugrul Ghazi", "Season 5"));
        adapter.setDrama(models);
        recyclerView.setAdapter(adapter);


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

    private void initView(View view) {

        Objects.requireNonNull(((MainActivity) Objects.requireNonNull(getActivity())).getSupportActionBar())
                .setTitle("Ertugrul Ghazi");
        Objects.requireNonNull(((MainActivity) Objects.requireNonNull(getActivity())).getSupportActionBar())
                .setSubtitle("");

        recyclerView = view.findViewById(R.id.season_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new SeasonAdapter();
        models = new ArrayList<>();
    }
}
