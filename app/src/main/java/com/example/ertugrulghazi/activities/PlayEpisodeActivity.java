package com.example.ertugrulghazi.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ertugrulghazi.R;
import com.example.ertugrulghazi.models.EpisodeModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.khizar1556.mkvideoplayer.MKPlayer;

import java.util.ArrayList;
import java.util.List;

import static com.example.ertugrulghazi.models.Constants.extra_position;
import static com.example.ertugrulghazi.models.Constants.extra_seasonName;

public class PlayEpisodeActivity extends AppCompatActivity {

    private static final String TAG = "PlayEpisodeActivity";

    private MKPlayer mkplayer;
    private List<EpisodeModel> models;
    private String seasonName;
    private int pos;
    private AdView adView;
    private InterstitialAd mInterstitialAd;

    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_episode);

        init();
        if (getIntent().hasExtra(extra_position)) {
            seasonName = getIntent().getStringExtra(extra_seasonName);
            pos = getIntent().getIntExtra(extra_position, 0);
        }
        //Load episode from firebase
        myRef = FirebaseDatabase.getInstance().getReference(seasonName.toLowerCase());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                models = new ArrayList<>();
                models.clear();
                Log.d(TAG, "onDataChange: " + dataSnapshot.toString());
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    models.add(snapshot.getValue(EpisodeModel.class));
                }
                mkplayer.play(models.get(pos).getUrl());
                mkplayer.setTitle(models.get(pos).getEpisodeName().concat(" ").concat("Ertugrul Ghazi"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                refreshLayout.setRefreshing(false);
            }
        });

        //play episode using player
        mkplayer.setPlayerCallbacks(new MKPlayer.playerCallbacks() {
            @Override
            public void onNextClick() {
                if (models.isEmpty()) {
                    Toast.makeText(PlayEpisodeActivity.this, "Please wait", Toast.LENGTH_SHORT).show();
                    return;
                }
                int tempPos = pos;
                pos += 1;
                if (pos <= models.size() - 1) {
                    mkplayer.play(models.get(pos).getUrl());
                    mkplayer.setTitle(models.get(pos).getEpisodeName().concat(" ").concat("Ertugrul Ghazi"));
                } else {
                    pos = tempPos;
                    Toast.makeText(PlayEpisodeActivity.this, "No more videos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPreviousClick() {
                if (models.isEmpty()) {
                    Toast.makeText(PlayEpisodeActivity.this, "Please wait", Toast.LENGTH_SHORT).show();
                    return;
                }
                int tempPos = pos;
                pos -= 1;
                if (pos >= 0) {
                    mkplayer.play(models.get(pos).getUrl());
                    mkplayer.setTitle(models.get(pos).getEpisodeName().concat(" ").concat("Ertugrul Ghazi"));
                } else {
                    pos = tempPos;
                    Toast.makeText(PlayEpisodeActivity.this, "No more videos", Toast.LENGTH_SHORT).show();
                }
            }
        });


        /*Implement banner ads here*/
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        /*Load interstitial ad*/
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged: called");
        super.onConfigurationChanged(newConfig);
        if (mkplayer != null) {
            mkplayer.onConfigurationChanged(newConfig);
        }
    }

    private void LoadEpisode(String seasonName) {

    }

    private void init() {
        mkplayer = new MKPlayer(this);
        adView = findViewById(R.id.playEpisode_banner);
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
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
            finish();
            return;
        } else {
            mkplayer.stop();
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
            finish();
        }
        super.onBackPressed();
    }
}
