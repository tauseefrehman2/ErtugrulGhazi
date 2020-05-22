package com.example.ertugrulghazi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ertugrulghazi.R;
import com.example.ertugrulghazi.models.EpisodeModel;
import com.example.ertugrulghazi.models.FavoriteModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {

    private List<FavoriteModel> mModels = new ArrayList<>();
    private FavoriteListener listener;
    private Context context;

    public FavoriteAdapter(Context context) {
        this.context = context;
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

        FavoriteModel mSingleModel = mModels.get(i);

        myViewHolder.seasonName_tv.setText(mSingleModel.getSeasonName());
        myViewHolder.episodeName_tv.setText(mSingleModel.getEpisodeName());
    }

    /*Count total items*/
    @Override
    public int getItemCount() {
        return mModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView seasonName_tv;
        TextView episodeName_tv;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            seasonName_tv = itemView.findViewById(R.id.custFav_seasonName_tv);
            episodeName_tv = itemView.findViewById(R.id.custFav_episodeName_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.setFav(position);
                }
            });
        }
    }

    public interface FavoriteListener {
        void setFav(int pos);
    }

    public void setOnItemClickListener(FavoriteListener listener) {
        this.listener = listener;
    }
}