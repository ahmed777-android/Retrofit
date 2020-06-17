package com.example.retrofit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.retrofit.R;
import com.example.retrofit.pojo.Results;

public class MovieDetailActivity extends AppCompatActivity {
    Results results;
    ImageView poster,backdrop_path;
    TextView title,tv_description,language;
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ditails);
        if (getIntent().hasExtra("m")){
         results= getIntent().getParcelableExtra("m");
        }
        poster= findViewById(R.id.poster_details);
        backdrop_path= findViewById(R.id.backdrop_path);
        title= findViewById(R.id.title_details);
        ratingBar= findViewById(R.id.rating_par);
        tv_description= findViewById(R.id.tv_description);
        language= findViewById(R.id.language);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.placeholder(R.drawable.ic_launcher_background);
        Glide.with(this).load(results.getPoster_path()).apply(requestOptions)
                .into(poster);
        Glide.with(this).load(results.getBackdrop_path())
                .into(backdrop_path);
         title.setText(results.getOriginal_title());
        ratingBar.setRating((float) results.getVote_average()/2);
        tv_description.setText( "OverView :\n"+results.getOverview());
        language.setText( "Language :"+results.getOriginal_language());
    }
}