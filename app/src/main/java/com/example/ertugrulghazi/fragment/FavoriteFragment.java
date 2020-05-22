package com.example.ertugrulghazi.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ertugrulghazi.Dao.FavDAO;
import com.example.ertugrulghazi.R;
import com.example.ertugrulghazi.activities.MainActivity;
import com.example.ertugrulghazi.adapter.FavoriteAdapter;
import com.example.ertugrulghazi.database.database;
import com.example.ertugrulghazi.models.FavoriteModel;
import com.example.ertugrulghazi.viewmodel.FavViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FavoriteFragment extends Fragment {

    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;
    private List<FavoriteModel> models;
    private FavViewModel viewModel;
    private TextView loading_tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Objects.requireNonNull(((MainActivity) Objects.requireNonNull(getActivity())).getSupportActionBar())
                .setTitle("Ertugrul Ghazi");
        Objects.requireNonNull(((MainActivity) Objects.requireNonNull(getActivity())).getSupportActionBar())
                .setSubtitle("Favorite");


        database.getInstance(getContext()).favDAO().getAllFav();


        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        init(view);
        viewModel = ViewModelProviders.of(getActivity()).get(FavViewModel.class);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait favorite dramas are processing");
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewModel.getAllFav().observe(getActivity(), new Observer<List<FavoriteModel>>() {
                    @Override
                    public void onChanged(List<FavoriteModel> favoriteModels) {
                        if (favoriteModels.size() > 1) {
                            Toast.makeText(getContext(), "not null", Toast.LENGTH_SHORT).show();
                        }
                        adapter.setDrama(favoriteModels);
                        progressDialog.dismiss();
                    }
                });
            }
        }, 2000);
        return view;
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.favorite_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FavoriteAdapter(getContext());
        recyclerView.setAdapter(adapter);
        models = new ArrayList<>();
    }
}
