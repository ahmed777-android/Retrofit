package com.example.retrofit.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.retrofit.R;
import com.example.retrofit.adapter.MovieAdapter;
import com.example.retrofit.adapter.OnItemClick;
import com.example.retrofit.constant.JsonConstants;
import com.example.retrofit.pojo.Movie;
import com.example.retrofit.pojo.Results;
import com.example.retrofit.servic.GitHubService;
import com.example.retrofit.servic.ServiceGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements OnItemClick, View.OnClickListener {
    private MovieAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private Button next, back;
    int page_number_variable = 1;
    int mNoOfColumns;
    Map<String, String> page_number = new HashMap<String, String>();
    private List<Results> mList;
    double width;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNoOfColumns = Utility.calculateNoOfColumns(this,180);
        setSupportActionBar((Toolbar) findViewById(R.id.toolBar));
        next = findViewById(R.id.Next);
        back = findViewById(R.id.Back);
        next.setOnClickListener(this);
        back.setOnClickListener(this);
        mRecyclerView = findViewById(R.id.rv);
        Post(page_number_variable);
    }

    private void setRecyclerView(List<Results> movies) {
        mList = movies;
        mAdapter = new MovieAdapter(this, this);
        RecyclerView.LayoutManager mLayoutManager = null;
        mLayoutManager = new GridLayoutManager(getApplicationContext(),  mNoOfColumns );
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mAdapter.submitList(movies);
        mRecyclerView.setAdapter(mAdapter);
    }

    void Post(int page_num) {
        GitHubService gitHubServiceInterFace = ServiceGenerator.creatServise(GitHubService.class);
        page_number.put("page", String.valueOf(page_num));
        Call<Movie> call = gitHubServiceInterFace.getPopularMovie(JsonConstants.Api_Key, page_number);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                setRecyclerView(response.body().getResults());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(int position, ImageView imageView) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("m", mList.get(position));
       // Toast.makeText(this, "position = " + position, Toast.LENGTH_LONG).show();
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,imageView,"sharedName");
       startActivity(intent,options.toBundle());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Next:
                page_number_variable = page_number_variable + 1;
                next.setText(page_number_variable + 1 + "");
                back.setText(page_number_variable + "");
                Post(page_number_variable);
                break;
            case R.id.Back:
                if (page_number_variable > 1) {
                    page_number_variable = page_number_variable - 1;
                    next.setText(page_number_variable + "");
                    back.setText(page_number_variable + "");
                    Post(page_number_variable);
                }
                break;
        }
    }
}