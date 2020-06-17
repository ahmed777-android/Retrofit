package com.example.retrofit.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.retrofit.pojo.Results;

public class ListItemCallback extends DiffUtil.ItemCallback<Results>
{
    @Override
    public boolean areItemsTheSame
            (@NonNull Results oldItem, @NonNull Results newItem) {
        return oldItem.getTitle().equals(newItem.getTitle());
    }

    @Override
    public boolean areContentsTheSame
            (@NonNull Results oldItem, @NonNull Results newItem) {
        return oldItem.getPoster_path().
                equals(newItem.getPoster_path());
    }
}
