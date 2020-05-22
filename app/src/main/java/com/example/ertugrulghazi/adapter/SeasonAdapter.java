package com.example.ertugrulghazi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ertugrulghazi.R;
import com.example.ertugrulghazi.models.SeasonModel;

import java.util.ArrayList;
import java.util.List;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.MyViewHolder> {

    private List<SeasonModel> mModels = new ArrayList<>();
    private SeasonListener listener;

    public void setDrama(List<SeasonModel> models) {
        mModels = models;
        notifyDataSetChanged();
    }

    public SeasonModel getSeasonAt(int position) {
        return mModels.get(position);
    }

    public void removeItem(int position) {
        mModels.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(SeasonModel model, int position) {
        mModels.add(position, model);
        notifyItemInserted(position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cust_season_layout, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        SeasonModel mSingleModel = mModels.get(i);
        myViewHolder.dramaImage_iv.setImageResource(mSingleModel.getImage());
        myViewHolder.dramaName_tv.setText(mSingleModel.getDramaName());
        myViewHolder.seasonName_tv.setText(mSingleModel.getSeasonName());
    }

    /*Count total items*/
    @Override
    public int getItemCount() {
        return mModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView dramaImage_iv;
        TextView dramaName_tv;
        TextView seasonName_tv;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            dramaImage_iv = itemView.findViewById(R.id.custDrama_iv);
            dramaName_tv = itemView.findViewById(R.id.custMain_dramaName_tv);
            seasonName_tv = itemView.findViewById(R.id.custMain_seasonName_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.setAchievement(mModels.get(position));
                }
            });
        }
    }

    public interface SeasonListener {
        void setAchievement(SeasonModel model);
    }

    public void setOnItemClickListener(SeasonListener listener) {
        this.listener = listener;
    }
}