package com.example.ertugrulghazi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
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
    private int lastPosition = -1;

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

        setAnimation(myViewHolder.itemView, i);

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