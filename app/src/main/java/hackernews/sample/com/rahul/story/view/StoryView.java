package hackernews.sample.com.rahul.story.view;



import java.util.List;

import hackernews.sample.com.rahul.story.model.StoryAdapterModel;

public interface StoryView {
    void loadView();

    void init();

    void populateRecyclerView();
    void loadAdapter(List<StoryAdapterModel> list);
}
