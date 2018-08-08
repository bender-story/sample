package hackernews.sample.com.rahul.story.view;

import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hackernews.sample.com.rahul.Constants;
import hackernews.sample.com.rahul.R;
import hackernews.sample.com.rahul.Utils.FileUtils;
import hackernews.sample.com.rahul.Utils.NetworkUtils;
import hackernews.sample.com.rahul.Utils.UiUtils;
import hackernews.sample.com.rahul.home.model.StoriesModel;
import hackernews.sample.com.rahul.rest.ApiClient;
import hackernews.sample.com.rahul.story.model.StoryAdapterModel;
import hackernews.sample.com.rahul.story.presenter.StoryPresenter;
import hackernews.sample.com.rahul.story.presenter.StoryPresenterImpl;

public class StoryActivity extends AppCompatActivity implements StoryView {
    @BindView(R.id.story_recyclerview)
    public RecyclerView recyclerView;
    @BindView(R.id.story_progressbar)
    ContentLoadingProgressBar progressBar;

    private StoriesModel mainStory;
    private int position;

    private StoryAdapter adapter;

    private StoryPresenter storyPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        ButterKnife.bind(this);
        init();
        loadView();

    }

    @Override
    public void loadView() {
        if(NetworkUtils.isConnected(getApplicationContext())) {
            populateRecyclerView();

        } else {
            UiUtils.createAlertDialog(this);
        }




    }

    @Override
    public void init() {
        getSupportActionBar().setTitle(getResources().getString(R.string.story_screen_head));
        mainStory=(StoriesModel) getIntent().getSerializableExtra(Constants.STORY_OBJECT_EXTRA);
        position=getIntent().getIntExtra(Constants.POSITION_EXTRA,0);

        // Add the first item as the story to the list so that it shown at the top of the adapter
        List<StoryAdapterModel> list=new ArrayList<>();
        list.add(new StoryAdapterModel(StoryAdapter.HEADER_TYPE,mainStory));
        storyPresenter= new StoryPresenterImpl(this,list);


    }

    @Override
    public void populateRecyclerView() {
        // if true fetch data online
        if(ApiClient.callService)
        storyPresenter.getComments(mainStory.getKids());
        else{
            //if false fetch data offline
            String jsonString[]= new String[2];
            jsonString[0]= FileUtils.loadJSONFromAsset(this,"comments_json");
            jsonString[1]= FileUtils.loadJSONFromAsset(this,"subComments_json");
            storyPresenter.getComments(jsonString);
        }

    }

    @Override
    public void loadAdapter(List<StoryAdapterModel> list) {
        progressBar.setVisibility(View.GONE);
        adapter=new StoryAdapter((ArrayList<StoryAdapterModel>) list);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(StoryActivity.this);
        recyclerView.setLayoutManager(layoutManager);
    }
}
