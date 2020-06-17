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

    private static final ListItemCallback ListItemCallback = new ListItemCallback();
    Context mContext;
    OnItemClick onItemClick;
    RequestOptions requestOptions = new RequestOptions();

    public MovieAdapter(Context context, OnItemClick onItemClick) {
        super(ListItemCallback);
        this.mContext = context;
        this.onItemClick = onItemClick;
    }

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
