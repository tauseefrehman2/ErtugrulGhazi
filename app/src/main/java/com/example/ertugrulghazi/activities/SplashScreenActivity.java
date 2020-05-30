package com.example.ertugrulghazi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.ertugrulghazi.R;
import com.example.ertugrulghazi.database.Er_Database;

public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Er_Database db = Er_Database.getInstance(this);
        final int size = db.episodeDAO().getAllEpisode().size();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (size < 1) {
                    startActivity(new Intent(SplashScreenActivity.this, InsertDataActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                    finish();
                }
            }
        }, 2000);

    }
}
