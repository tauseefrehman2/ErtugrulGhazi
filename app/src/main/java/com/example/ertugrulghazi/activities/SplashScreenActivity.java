package com.example.ertugrulghazi.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ertugrulghazi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "SplashScreenActivity";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        sharedPreferences = getSharedPreferences("user_id", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String uId = sharedPreferences.getString("uId", "");

        if (!uId.isEmpty()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(intent);
                    finish();
                }
            }, 2000);

        } else {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
            String userKey = reference.push().getKey();
            editor.putString("uId", userKey).apply();

            if (userKey != null) {
                FirebaseDatabase.getInstance().getReference("users").child(userKey).setValue(true)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    startActivity(intent);
                                    finish();
                                } else
                                    Toast.makeText(SplashScreenActivity.this, "Restart the app", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SplashScreenActivity.this, "Restart the app", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        }
    }
}
