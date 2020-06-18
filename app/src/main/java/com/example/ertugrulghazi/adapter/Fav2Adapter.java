package com.example.ertugrulghazi.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ertugrulghazi.R;
import com.example.ertugrulghazi.activities.EpisodeActivity;
import com.example.ertugrulghazi.activities.MainActivity;
import com.example.ertugrulghazi.models.EpisodeModel;
import com.example.ertugrulghazi.models.FavoriteModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

//import com.example.ertugrulghazi.database.Er_Database;

public class Fav2Adapter extends RecyclerView.Adapter<Fav2Adapter.MyViewHolder> {

    private static final String TAG = "EpisodeAdapter";

    private List<FavoriteModel> mModels = new ArrayList<>();
    private DramaListener listener;
    private Context context;
    //    private Er_Database db;
    private int lastPosition = -1;
    private int isChangesDetect = 0;
    private String userId;
    SharedPreferences sharedPreferences;


    public Fav2Adapter(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("user_id", MODE_PRIVATE);
        userId = sharedPreferences.getString("uId", "");
    }

    public void setDrama(List<FavoriteModel> models) {
        mModels = models;
        notifyDataSetChanged();
    }

    public FavoriteModel getDramaAt(int position) {
        return mModels.get(position);
    }

    public void removeItem(int position) {
        mModels.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(FavoriteModel model, int position) {
        mModels.add(position, model);
        notifyItemInserted(position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cust_fav_layout, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        final FavoriteModel mSingleModel = mModels.get(i);

        myViewHolder.DramaName_tv.setText("Ertugrul Ghazi");
        myViewHolder.episodeName_tv.setText(mSingleModel.getEpisodeName());
        myViewHolder.thumbNail.setImageResource(R.drawable.demo);

        setAnimation(myViewHolder.itemView, i);
    }

    /*Count total items*/
    @Override
    public int getItemCount() {
        return mModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView DramaName_tv;
        TextView episodeName_tv;
        ImageView delete_iv;
        ImageView thumbNail;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            DramaName_tv = itemView.findViewById(R.id.custFav_dramaName_tv);
            episodeName_tv = itemView.findViewById(R.id.custFav_episodeName_tv);
            delete_iv = itemView.findViewById(R.id.custFav_delete_iv);
            thumbNail = itemView.findViewById(R.id.custFav_iv);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.setEpisode(position);
                }
            });
        }
    }

    public interface DramaListener {
        void setEpisode(int pos);
    }

    public void setOnItemClickListener(DramaListener listener) {
        this.listener = listener;
    }


    //Load image AsyncTask class where thumbnail is created from url
    private class loadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
        WeakReference<ImageView> imageViewWeakReference;
        WeakReference<ProgressBar> progressBarWeakReference;

        loadImageAsyncTask(ImageView dramaImage_iv, ProgressBar progressBar) {
            imageViewWeakReference = new WeakReference<>(dramaImage_iv);
            progressBarWeakReference = new WeakReference<>(progressBar);
        }

        @Override
        protected void onPreExecute() {
            progressBarWeakReference.get().setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {

            Bitmap bitmap = null;
            try {
                bitmap = retrieveVideoFrameFromVideo(strings[0]);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            progressBarWeakReference.get().setVisibility(View.INVISIBLE);
            imageViewWeakReference.get().setImageBitmap(bitmap);
        }

    }

    //Method that convert url to thumbnail
    private Bitmap retrieveVideoFrameFromVideo(String videoPath) throws Throwable {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)" + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }


    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
//            TranslateAnimation anim = new TranslateAnimation(0,-1000,0,-1000);
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//            anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
            anim.setDuration(550);//to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }

    private void isFavourite(final String videoId, final ImageView imageView) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("favourite")
                .child(videoId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(userId).exists()) {
                    imageView.setImageResource(R.drawable.ic_vec_favorite_red);
                    imageView.setTag("favourite");
                } else {
                    imageView.setImageResource(R.drawable.ic_vec_favorite);
                    imageView.setTag("fav");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
