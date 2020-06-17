package com.example.retrofit.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit.R;

class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
      ImageView poster;
      TextView  title,rating;

    OnItemClick onItemClick;

    public MovieViewHolder(@NonNull View itemView , OnItemClick onClick) {
        super(itemView);
        poster = itemView.findViewById(R.id.poster);
        title = itemView.findViewById(R.id.title);
        rating = itemView.findViewById(R.id.rating);
        itemView.setOnClickListener(this);
        onItemClick= onClick;
    }

    @Override
    public void onClick(View v) {
        onItemClick.onItemClick(getAdapterPosition(),poster);
    }
}
