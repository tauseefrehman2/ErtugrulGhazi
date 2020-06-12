package com.example.ertugrulghazi.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.ertugrulghazi.R;
//import com.example.ertugrulghazi.database.Er_Database;
import com.example.ertugrulghazi.models.FavoriteModel;
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

public class PlayFavoriteActivity extends AppCompatActivity {

    private static final String TAG = "PlayFavoriteActivity";

    private MKPlayer mkplayer;
    private List<FavoriteModel> models;
    private int pos;
    private DatabaseReference myRef;

    private AdView adView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_favorite);

        init();
        if (getIntent().hasExtra(extra_position)) {
            pos = getIntent().getIntExtra(extra_position, 0);
        }

        myRef = FirebaseDatabase.getInstance().getReference("favourite");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                models = new ArrayList<>();
                models.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    models.add(ds.getValue(FavoriteModel.class));
                }
//                mkplayer.play(models.get(pos).getUrl());
                mkplayer.setTitle(models.get(pos).getEpisodeName().concat(" ").concat(models.get(pos).getSeasonName()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //play episode using player
        mkplayer.setPlayerCallbacks(new MKPlayer.playerCallbacks() {
            @Override
            public void onNextClick() {
                if (models.isEmpty()) {
                    Toast.makeText(PlayFavoriteActivity.this, "Please wait", Toast.LENGTH_SHORT).show();
                    return;
                }
                int tempPos = pos;
                pos += 1;
                if (pos <= models.size() - 1) {
//                    mkplayer.play(models.get(pos).getUrl());
                    mkplayer.setTitle(models.get(pos).getEpisodeName().concat(" ").concat("Ertugrul Ghazi"));
                } else {
                    pos = tempPos;
                    Toast.makeText(PlayFavoriteActivity.this, "No more videos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPreviousClick() {
                if (models.isEmpty()) {
                    Toast.makeText(PlayFavoriteActivity.this, "Please wait", Toast.LENGTH_SHORT).show();
                    return;
                }
                int tempPos = pos;
                pos -= 1;
                if (pos >= 0) {
//                    mkplayer.play(models.get(pos).getUrl());
                    mkplayer.setTitle(models.get(pos).getEpisodeName().concat(" ").concat("Ertugrul Ghazi"));
                } else {
                    pos = tempPos;
                    Toast.makeText(PlayFavoriteActivity.this, "No more videos", Toast.LENGTH_SHORT).show();
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

    private void init() {
        adView = findViewById(R.id.playFavEpisode_banner);
//        db = Er_Database.getInstance(this);
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
