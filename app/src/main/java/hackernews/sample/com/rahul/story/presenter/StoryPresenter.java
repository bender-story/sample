package hackernews.sample.com.rahul.story.presenter;

import java.util.List;

public interface StoryPresenter {
    // method to get  online data to load UI
    void getComments(List<Integer> kids);
    // method to get  offline data to load UI
    void getComments(String jsonString[]);
}
