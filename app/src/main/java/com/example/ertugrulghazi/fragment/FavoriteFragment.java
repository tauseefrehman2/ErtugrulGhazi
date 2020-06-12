package com.example.ertugrulghazi.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ertugrulghazi.R;
import com.example.ertugrulghazi.activities.EpisodeActivity;
import com.example.ertugrulghazi.activities.MainActivity;
import com.example.ertugrulghazi.activities.PlayEpisodeActivity;
import com.example.ertugrulghazi.activities.PlayFavoriteActivity;
import com.example.ertugrulghazi.adapter.Fav2Adapter;
import com.example.ertugrulghazi.adapter.FavoriteAdapter;
import com.example.ertugrulghazi.models.FavoriteModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.ertugrulghazi.models.Constants.extra_position;
import static com.example.ertugrulghazi.models.Constants.extra_seasonName;

//import com.example.ertugrulghazi.database.Er_Database;

public class FavoriteFragment extends Fragment {

    private static final String TAG = "FavoriteFragment";

    private RecyclerView recyclerView;
    private Fav2Adapter adapter;
    private List<FavoriteModel> models;
    private SwipeRefreshLayout refreshLayout;
    private TextView fav_tv;
    private String uId;

    //    private Er_Database db;
    private DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Objects.requireNonNull(((MainActivity) Objects.requireNonNull(getActivity())).getSupportActionBar())
                .setTitle("Ertugrul Ghazi");
        Objects.requireNonNull(((MainActivity) Objects.requireNonNull(getActivity())).getSupportActionBar())
                .setSubtitle("Favorite");

        final View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        init(view);

        LoadFavDrama();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadFavDrama();
            }
        });

        adapter.setOnItemClickListener(new Fav2Adapter.DramaListener() {
            @Override
            public void setEpisode(int pos) {

                Intent intent = new Intent(getContext(), PlayEpisodeActivity.class);
                intent.putExtra(extra_seasonName, models.get(pos).getSeasonName());
                intent.putExtra(extra_position, pos);
                intent.putExtra("videoId", models.get(pos).getVideoId());
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
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {

                final int pos = viewHolder.getAdapterPosition();
                final FavoriteModel model = adapter.getDramaAt(pos);
                Log.d(TAG, "onSwiped: " + models.size());


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

                                FirebaseDatabase.getInstance().getReference("favourite")
                                        .child(model.getVideoId())
                                        .child(uId).removeValue();

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

    private void LoadFavDrama() {

        refreshLayout.setRefreshing(true);
        reference = FirebaseDatabase.getInstance().getReference("favourite");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                models = new ArrayList<>();
                models.clear();

                if (dataSnapshot.getChildrenCount() > 0) {
                    fav_tv.setVisibility(View.GONE);
                }
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child(uId).exists()) {

                        FavoriteModel model = ds.child(uId).getValue(FavoriteModel.class);
                        models.add(model);
                    }
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

    private void init(View view) {
        recyclerView = view.findViewById(R.id.favorite_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Fav2Adapter(getContext());
        recyclerView.setAdapter(adapter);

        refreshLayout = view.findViewById(R.id.favorite_refresh);
        fav_tv = view.findViewById(R.id.favorite_tv);

        SharedPreferences sp = getContext().getSharedPreferences("user_id", Context.MODE_PRIVATE);
        uId = sp.getString("uId", "");
    }
}
