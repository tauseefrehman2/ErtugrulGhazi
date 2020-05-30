package com.example.ertugrulghazi.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ertugrulghazi.R;
import com.example.ertugrulghazi.activities.EpisodeActivity;
import com.example.ertugrulghazi.activities.MainActivity;
import com.example.ertugrulghazi.activities.PlayEpisodeActivity;
import com.example.ertugrulghazi.activities.PlayFavoriteActivity;
import com.example.ertugrulghazi.adapter.EpisodeAdapter;
import com.example.ertugrulghazi.adapter.FavoriteAdapter;
import com.example.ertugrulghazi.database.Er_Database;
import com.example.ertugrulghazi.models.EpisodeModel;
import com.example.ertugrulghazi.models.FavoriteModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.ertugrulghazi.models.Constants.extra_position;
import static com.example.ertugrulghazi.models.Constants.extra_seasonName;

public class FavoriteFragment extends Fragment {

    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;

    private Er_Database db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Objects.requireNonNull(((MainActivity) Objects.requireNonNull(getActivity())).getSupportActionBar())
                .setTitle("Ertugrul Ghazi");
        Objects.requireNonNull(((MainActivity) Objects.requireNonNull(getActivity())).getSupportActionBar())
                .setSubtitle("Favorite");

        db = Er_Database.getInstance(getActivity().getApplicationContext());

        final View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        init(view);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
//        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Favorite Drama's are processing");
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (db.favDAO().getAllFav().size() < 1) {
                    view.findViewById(R.id.favorite_tv).setVisibility(View.VISIBLE);
                } else {
                    adapter.setDrama(db.favDAO().getAllFav());
                    view.findViewById(R.id.favorite_tv).setVisibility(View.GONE);
                }

                progressDialog.dismiss();
            }
        }, 2000);

        adapter.setOnItemClickListener(new FavoriteAdapter.FavoriteListener() {
            @Override
            public void setFav(int pos) {
                Intent intent = new Intent(getContext(), PlayFavoriteActivity.class);
                intent.putExtra(extra_position, pos);
                startActivity(intent);
            }
        });

        //Touch helper for swapping recycler view items
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder
                    viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                final int pos = viewHolder.getAdapterPosition();
                final FavoriteModel model = adapter.getDramaAt(pos);

                /*get drama from main list and update to 0*/
                EpisodeModel epiModel = db.episodeDAO().getDramaById(model.getEpiId());
                final EpisodeModel updateEpiModel = new EpisodeModel(epiModel.getId(),
                        epiModel.getDramaName(), epiModel.getSeasonName(),
                        epiModel.getEpisodeName(), epiModel.getUrl(), 0, epiModel.getThumbnail());


                adapter.removeItem(pos);
                android.app.AlertDialog.Builder dialogBuilder =
                        new android.app.AlertDialog.Builder(getContext());
                dialogBuilder.setCancelable(false)
                        .setTitle("Confirmation")
                        .setIcon(R.drawable.ic_vec_delete)
                        .setMessage("Do you really wants to remove?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                db.favDAO().delete(model);
                                db.episodeDAO().update(updateEpiModel);
                                dialog.dismiss();
                                Toast.makeText(getContext(), "Drama removed from favorite", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.restoreItem(model, pos);
                        dialog.dismiss();
                    }
                });
                dialogBuilder.show();
            }
        });
        helper.attachToRecyclerView(recyclerView);


        return view;
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.favorite_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FavoriteAdapter(getContext());
        recyclerView.setAdapter(adapter);
    }
}
