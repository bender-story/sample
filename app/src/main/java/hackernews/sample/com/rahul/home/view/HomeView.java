package hackernews.sample.com.rahul.home.view;

import java.util.ArrayList;

import hackernews.sample.com.rahul.home.model.StoriesModel;

public interface HomeView {

     void loadView();

     void init();

     void populateRecyclerView();

     void pullToRefresh();

     void loadAdapter(ArrayList<StoriesModel> list);


}
