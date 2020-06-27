package com.example.retrofit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.retrofit.R;
import com.example.retrofit.pojo.Results;
import com.example.retrofit.services.PowerEventReceiver;
import com.example.retrofit.services.UtilServices;

public class MovieDetailActivity extends AppCompatActivity {
    Results results;
    ImageView poster,backdrop_path;
    TextView title,tv_description,language;
    RatingBar ratingBar;
    private PowerEventReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ditails);
        if (getIntent().hasExtra("m")){
         results= getIntent().getParcelableExtra("m");
        }

        setUi();
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
    void  setUi(){
        poster= findViewById(R.id.poster_details);
        backdrop_path= findViewById(R.id.backdrop_path);
        title= findViewById(R.id.title_details);
        ratingBar= findViewById(R.id.rating_par);
        tv_description= findViewById(R.id.tv_description);
        language= findViewById(R.id.language);
    }
}


/*
   findViewById(R.id.btntest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (receiver == null) {
                    receiver = new PowerEventReceiver();
                    UtilServices.registerPowerEventsReceiver(MovieDetailActivity.this, receiver);
                } else {
                    UtilServices.unregisterPowerEventsReceiver(MovieDetailActivity.this, receiver);
                    receiver = null;
                }



            }
        });
 */