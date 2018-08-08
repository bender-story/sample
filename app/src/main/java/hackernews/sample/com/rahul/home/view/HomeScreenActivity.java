package hackernews.sample.com.rahul.home.view;


import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import hackernews.sample.com.rahul.R;
import hackernews.sample.com.rahul.Utils.FileUtils;
import hackernews.sample.com.rahul.Utils.NetworkUtils;
import hackernews.sample.com.rahul.Utils.UiUtils;
import hackernews.sample.com.rahul.Utils.Utils;
import hackernews.sample.com.rahul.home.model.StoriesModel;
import hackernews.sample.com.rahul.home.presenter.HomePresenter;
import hackernews.sample.com.rahul.home.presenter.HomePresenterImpl;
import hackernews.sample.com.rahul.rest.ApiClient;

public class HomeScreenActivity extends AppCompatActivity implements HomeView {
    @BindView(R.id.top_stories_recyclerview)
     RecyclerView recyclerView;
    @BindView(R.id.top_stories_progressbar)
     ContentLoadingProgressBar progressBar;

    @BindView(R.id.swipe_container)
     SwipeRefreshLayout swipeContainer;
    private HomeScreenAdapter adapter;

    private HomePresenter homePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        ButterKnife.bind(this);
        homePresenter= new HomePresenterImpl(HomeScreenActivity.this);
//        setDataFetchType(false);
        checkIFTestRun();
        init();
        loadView();
    }

    private void checkIFTestRun(){
        if(Utils.isTestMode(getApplicationContext()))
            ApiClient.callService=false;
    }

    @Override
    public void loadView() {
        if(NetworkUtils.isConnected(getApplicationContext())) {
            loaderVisibilty(true);
            populateRecyclerView();
            pullToRefresh();
        } else {
            UiUtils.createAlertDialog(this);
        }
    }

    @Override
    public void init() {
        getSupportActionBar().setTitle(getResources().getString(R.string.home_screen_head));
    }

    @Override
    public void populateRecyclerView() {
//        progressBar.setVisibility(View.VISIBLE);
        // If true it would take data from web servi
        if(ApiClient.callService)
            homePresenter.getStoriesID();
        else
            //IF false it would fetch data from local json
            homePresenter.getStoriesID(FileUtils.loadJSONFromAsset(this,"stories_json"));


    }

    @Override
    public void pullToRefresh() {
    swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            recyclerView.setAdapter(null);
            loaderVisibilty(true);
            homePresenter.getStoriesID();
            swipeContainer.setRefreshing(false);
        }
    });


    }

    @Override
    public void loadAdapter(ArrayList<StoriesModel> list) {
        loaderVisibilty(false);
        adapter=new HomeScreenAdapter(list);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(HomeScreenActivity.this);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setHasFixedSize(true);

    }

    private void loaderVisibilty(boolean showLoader){
        if(showLoader){
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            swipeContainer.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            swipeContainer.setVisibility(View.VISIBLE);
        }
    }


}
