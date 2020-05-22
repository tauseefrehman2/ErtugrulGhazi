package com.example.ertugrulghazi.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ertugrulghazi.R;
import com.example.ertugrulghazi.models.EpisodeModel;
import com.example.ertugrulghazi.models.FavoriteModel;
import com.example.ertugrulghazi.viewmodel.FavViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.MyViewHolder> {

    private static final String TAG = "EpisodeAdapter";

    private List<EpisodeModel> mModels = new ArrayList<>();
    private List<FavoriteModel> mFavModels = new ArrayList<>();
    private DramaListener listener;
    private Context context;
    private FavViewModel viewModel;


    public EpisodeAdapter(Context context) {
        this.context = context;
        viewModel = ViewModelProviders.of((FragmentActivity) context).get(FavViewModel.class);
    }

    public void setDrama(List<EpisodeModel> models) {
        mModels = models;
        getAllFav();
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

        //Make thumbnail of video using async task
        loadImageAsyncTask loadImageAsyncTask = new loadImageAsyncTask(myViewHolder.dramaImage_iv, myViewHolder.progressBar);
        loadImageAsyncTask.execute(mSingleModel.getUrl());
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
        ProgressBar progressBar;
        ImageView fav_iv;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            dramaImage_iv = itemView.findViewById(R.id.custEpi_iv);
            DramaName_tv = itemView.findViewById(R.id.custEpi_dramaName_tv);
            episodeName_tv = itemView.findViewById(R.id.custEpi_episodeName_tv);
            progressBar = itemView.findViewById(R.id.custEpi_pb);
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
                    getAllFav();
                    EpisodeModel model = mModels.get(getAdapterPosition());
                    boolean isContain = false;
                    for (FavoriteModel model1 : mFavModels) {
                        if (model1.getEpiId() == model.getEpiId()) {
                            isContain = true;
                            break;
                        }
                    }
                    if (!isContain) {
                        FavoriteModel favModel = new FavoriteModel(model.getEpiId(), model.getSeasonName(), model.getEpisodeName(), model.getUrl());
                        viewModel.insert(favModel);
                        Toast.makeText(context, "Added to favorite", Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(context, "Already added", Toast.LENGTH_SHORT).show();

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

    private void getAllFav() {
        viewModel = ViewModelProviders.of((FragmentActivity) context).get(FavViewModel.class);
        viewModel.getAllFav().observe((FragmentActivity) context, new Observer<List<FavoriteModel>>() {
            @Override
            public void onChanged(List<FavoriteModel> favoriteModels) {
                mFavModels = favoriteModels;
            }
        });
    }
}
