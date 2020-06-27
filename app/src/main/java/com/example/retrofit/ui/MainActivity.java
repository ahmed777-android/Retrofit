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
import android.util.Log;
import android.view.View;
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
import com.example.retrofit.services.PowerEventReceiver;
import com.example.retrofit.services.UtilServices;

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
    private PowerEventReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (receiver == null) {
            receiver = new PowerEventReceiver();
            UtilServices.registerPowerEventsReceiver(MainActivity.this, receiver);
        }
        setUI();
        Post(page_number_variable);
    }

    void setUI() {
        mNoOfColumns = UtilityNumView.calculateNoOfColumns(this, 180);
        next = findViewById(R.id.Next);
        back = findViewById(R.id.Back);
        next.setOnClickListener(this);
        back.setOnClickListener(this);
        setSupportActionBar((Toolbar) findViewById(R.id.toolBar));
        mRecyclerView = findViewById(R.id.rv);
        mAdapter = new MovieAdapter(this, this);
        RecyclerView.LayoutManager mLayoutManager = null;
        mLayoutManager = new GridLayoutManager(getApplicationContext(), mNoOfColumns);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

    }

    private void setRecyclerView(List<Results> movies) {
        mList = movies;
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
                Toast.makeText(MainActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(int position, ImageView imageView) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("m", mList.get(position));
        // Toast.makeText(this, "position = " + position, Toast.LENGTH_LONG).show();
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, imageView, "sharedName");
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Next:
                int nex = Integer.parseInt(next.getText().toString());
                next.setText(nex + 1 + "");
                back.setText(nex + "");
                page_number_variable = page_number_variable + 1;
                Post(page_number_variable);
                Toast.makeText(this, "FROM >" + page_number_variable, Toast.LENGTH_LONG).show();
                break;
            case R.id.Back:
                if (page_number_variable == 1) {
                    break;
                }
                int nex1 = Integer.parseInt(next.getText().toString()); //3
                if (nex1 == 2) {
                    back.setText("HOME");
                    next.setText("" + 1);
                    page_number_variable = page_number_variable - 1;
                    Post(page_number_variable);
                    Toast.makeText(this,"FROM ==2"+page_number_variable,Toast.LENGTH_LONG).show();

                }
                else if (nex1 != 0) {
                    back.setText("" + (nex1 - 2));  //
                    next.setText("" + (nex1 - 1));//
                    page_number_variable = page_number_variable - 1;
                    Post(page_number_variable);
                    Toast.makeText(this,"FROM !=0 "+page_number_variable,Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        UtilServices.unregisterPowerEventsReceiver(MainActivity.this, receiver);
        receiver = null;
        super.onDestroy();
    }
}