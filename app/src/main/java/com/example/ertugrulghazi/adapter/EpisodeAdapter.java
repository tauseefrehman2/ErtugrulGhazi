package com.example.ertugrulghazi.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ertugrulghazi.R;
import com.example.ertugrulghazi.database.Er_Database;
import com.example.ertugrulghazi.models.EpisodeModel;
import com.example.ertugrulghazi.models.FavoriteModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.MyViewHolder> {

    private static final String TAG = "EpisodeAdapter";

    private List<EpisodeModel> mModels = new ArrayList<>();
    private DramaListener listener;
    private Context context;
    private Er_Database db;
    private int lastPosition = -1;
    private int isChangesDetect = 0;

    public EpisodeAdapter(Context context) {
        this.context = context;
        db = Er_Database.getInstance(context);
    }

    public void setDrama(List<EpisodeModel> models) {
        mModels = models;
        notifyDataSetChanged();
    }

    public EpisodeModel getDramaAt(int position) {
        return mModels.get(position);
    }

    public void removeItem(int position) {
        mModels.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(EpisodeModel model, int position) {
        mModels.add(position, model);
        notifyItemInserted(position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cust_episode_layout, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        EpisodeModel mSingleModel = mModels.get(i);

        myViewHolder.DramaName_tv.setText(mSingleModel.getDramaName());
        myViewHolder.episodeName_tv.setText(mSingleModel.getEpisodeName());
        myViewHolder.dramaImage_iv.setImageResource(mSingleModel.getThumbnail());

        if (mSingleModel.getIsFav() == 1) {
            myViewHolder.fav_iv.setImageResource(R.drawable.ic_vec_favorite_red);
        } else myViewHolder.fav_iv.setImageResource(R.drawable.ic_vec_favorite);

        //Make thumbnail of video using async task
//        if (isChangesDetect == 0) { /*this will stop to create thumbnail again and again*/
//            loadImageAsyncTask loadImageAsyncTask = new loadImageAsyncTask(myViewHolder.dramaImage_iv, myViewHolder.progressBar);
//            loadImageAsyncTask.execute(mSingleModel.getUrl());
//        }
        setAnimation(myViewHolder.itemView, i);
    }

    /*Count total items*/
    @Override
    public int getItemCount() {
        return mModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView dramaImage_iv;
        TextView DramaName_tv;
        TextView episodeName_tv;
        ImageView fav_iv;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            dramaImage_iv = itemView.findViewById(R.id.custEpi_iv);
            DramaName_tv = itemView.findViewById(R.id.custEpi_dramaName_tv);
            episodeName_tv = itemView.findViewById(R.id.custEpi_episodeName_tv);
            fav_iv = itemView.findViewById(R.id.custEpisode_fav_iv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.setEpisode(position);
                }
            });

            fav_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isChangesDetect = 1;
                    EpisodeModel model = mModels.get(getAdapterPosition());

                    /*Check if drama is favorite or not
                     * if 0 drama is not favorite if 1 drama is favorite*/
                    int favStatus = model.getIsFav() == 0 ? 1 : 0;
                    EpisodeModel updateEpiModel = new EpisodeModel(model.getId(), model.getDramaName(), model.getSeasonName(),
                            model.getEpisodeName(), model.getUrl(), favStatus, model.getThumbnail());
                    db.episodeDAO().update(updateEpiModel);
                    mModels.set(getAdapterPosition(), updateEpiModel);

                    FavoriteModel favModel = db.favDAO().getDramaById(model.getId());

                    //Add drama also in database
                    FavoriteModel favModelUpdate = new FavoriteModel(model.getId(),
                            model.getSeasonName(), model.getEpisodeName(), model.getUrl());

                    if (favStatus == 0) {
                        favModelUpdate.setId(favModel.getId());
                        db.favDAO().delete(favModelUpdate);
                        Toast.makeText(context, "Removed from favorite", Toast.LENGTH_SHORT).show();
                    }
                    if (favStatus == 1) {
                        db.favDAO().insert(favModelUpdate);
                        Toast.makeText(context, "Added to favorite", Toast.LENGTH_SHORT).show();
                    }
                    notifyItemChanged(getAdapterPosition());

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
}
