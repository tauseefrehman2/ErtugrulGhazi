package com.example.ertugrulghazi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.ertugrulghazi.R;
//import com.example.ertugrulghazi.database.Er_Database;


public class InsertDataActivity extends AppCompatActivity {

//    private Er_Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);
//        db = Er_Database.getInstance(this);

        final ProgressDialog pd = new ProgressDialog(this);
        //pd.setTitle("Inserting record");
        pd.setMessage("Wait Drama is being processing");
        pd.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pd.dismiss();
//                insertDataInDb();
                startActivity(new Intent(InsertDataActivity.this, MainActivity.class));
                finish();

            }
        }, 2000);

    }

//    private void insertDataInDb() {
//
//        db.episodeDAO().insert(new EpisodeModel("Ertugrul", "Season 1", "Episode 1", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/demoVideo.mp4?alt=media&token=0c7fd2f2-f2ac-41f5-a196-0bad280f0b6a",
//                0, R.drawable.thumbnailone));
//        db.episodeDAO().insert(new EpisodeModel("Ertugrul", "Season 1", "Episode 2", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video2.mp4?alt=media&token=28ac6d40-0004-47f2-8385-b1c7ecb71bd4",
//                0, R.drawable.thumbnailtwo));
//        db.episodeDAO().insert(new EpisodeModel("Ertugrul", "Season 1", "Episode 3", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video3.mp4?alt=media&token=100c46f3-9442-450b-a636-f0a2d54fc8a3",
//                0, R.drawable.thumbnailthree));
//
//        db.episodeDAO().insert(new EpisodeModel("Ertugrul", "Season 2", "Episode 1", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/demoVideo.mp4?alt=media&token=0c7fd2f2-f2ac-41f5-a196-0bad280f0b6a",
//                0, R.drawable.thumbnailone));
//        db.episodeDAO().insert(new EpisodeModel("Ertugrul", "Season 2", "Episode 3", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video3.mp4?alt=media&token=100c46f3-9442-450b-a636-f0a2d54fc8a3",
//                0, R.drawable.thumbnailthree));
//        db.episodeDAO().insert(new EpisodeModel("Ertugrul", "Season 2", "Episode 2", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video2.mp4?alt=media&token=28ac6d40-0004-47f2-8385-b1c7ecb71bd4",
//                0, R.drawable.thumbnailtwo));
//
//        db.episodeDAO().insert(new EpisodeModel("Ertugrul", "Season 3", "Episode 2", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video2.mp4?alt=media&token=28ac6d40-0004-47f2-8385-b1c7ecb71bd4",
//                0, R.drawable.thumbnailtwo));
//        db.episodeDAO().insert(new EpisodeModel("Ertugrul", "Season 3", "Episode 1", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/demoVideo.mp4?alt=media&token=0c7fd2f2-f2ac-41f5-a196-0bad280f0b6a",
//                0, R.drawable.thumbnailone));
//        db.episodeDAO().insert(new EpisodeModel("Ertugrul", "Season 3", "Episode 3", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video3.mp4?alt=media&token=100c46f3-9442-450b-a636-f0a2d54fc8a3",
//                0, R.drawable.thumbnailthree));
//
//        db.episodeDAO().insert(new EpisodeModel("Ertugrul", "Season 4", "Episode 3", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video3.mp4?alt=media&token=100c46f3-9442-450b-a636-f0a2d54fc8a3",
//                0, R.drawable.thumbnailthree));
//        db.episodeDAO().insert(new EpisodeModel("Ertugrul", "Season 4", "Episode 1", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/demoVideo.mp4?alt=media&token=0c7fd2f2-f2ac-41f5-a196-0bad280f0b6a",
//                0, R.drawable.thumbnailone));
//        db.episodeDAO().insert(new EpisodeModel("Ertugrul", "Season 4", "Episode 2", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video2.mp4?alt=media&token=28ac6d40-0004-47f2-8385-b1c7ecb71bd4",
//                0, R.drawable.thumbnailtwo));
//
//        db.episodeDAO().insert(new EpisodeModel("Ertugrul", "Season 5", "Episode 1", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/demoVideo.mp4?alt=media&token=0c7fd2f2-f2ac-41f5-a196-0bad280f0b6a",
//                0, R.drawable.thumbnailone));
//        db.episodeDAO().insert(new EpisodeModel("Ertugrul", "Season 5", "Episode 3", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video3.mp4?alt=media&token=100c46f3-9442-450b-a636-f0a2d54fc8a3",
//                0, R.drawable.thumbnailthree));
//        db.episodeDAO().insert(new EpisodeModel("Ertugrul", "Season 5", "Episode 2", "https://firebasestorage.googleapis.com/v0/b/ertugrul-ghazi-5e229.appspot.com/o/video2.mp4?alt=media&token=28ac6d40-0004-47f2-8385-b1c7ecb71bd4",
//                0, R.drawable.thumbnailtwo));
//
//    }

}
