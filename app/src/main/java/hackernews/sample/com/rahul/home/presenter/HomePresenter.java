package hackernews.sample.com.rahul.home.presenter;

//presenter Interface for home screen

public interface HomePresenter {
    // method to get  online data to load UI
void getStoriesID();
    // method to get  offline data to load UI
void getStoriesID(String string);
}
