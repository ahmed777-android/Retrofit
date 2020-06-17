package com.example.retrofit.adapter;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.retrofit.R;
import com.example.retrofit.pojo.Movie;
import com.example.retrofit.pojo.Results;

public class MovieAdapter extends ListAdapter<Results, MovieViewHolder> {

    Context mContext;
    OnItemClick onItemClick;
    RequestOptions requestOptions = new RequestOptions();

    public MovieAdapter(Context context, OnItemClick onItemClick) {
        super(diffCallback);
        this.mContext = context;
        this.onItemClick = onItemClick;
    }

    static DiffUtil.ItemCallback<Results> diffCallback = new DiffUtil.ItemCallback<Results>() {
        @Override
        public boolean areItemsTheSame(@NonNull Results oldItem, @NonNull Results newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Results oldItem, @NonNull Results newItem) {
            return oldItem.getPoster_path().equals(newItem.getPoster_path());
        }
    };

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_view, parent, false);
        return new MovieViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Results movie = getItem(position);
        requestOptions = requestOptions.placeholder(R.drawable.ic_launcher_background);
        if (movie != null) {
            Glide.with(mContext).load(movie.getPoster_path()).apply(requestOptions)
                    .into(holder.poster);
            holder.title.setText(movie.getTitle());
            holder.rating.setText(movie.getVote_average()+"");
        }
    }


}
