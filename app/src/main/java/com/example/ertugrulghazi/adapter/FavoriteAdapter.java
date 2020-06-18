package com.example.ertugrulghazi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ertugrulghazi.R;
import com.example.ertugrulghazi.models.FavoriteModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {

    private List<FavoriteModel> mModels = new ArrayList<>();
    private FavoriteListener listener;
    private Context context;

    private int lastPosition = -1;

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

        setAnimation(myViewHolder.itemView, i);

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

//            seasonName_tv = itemView.findViewById(R.id.custFav_seasonName_tv);
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