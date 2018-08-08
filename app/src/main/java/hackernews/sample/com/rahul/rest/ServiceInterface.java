package hackernews.sample.com.rahul.rest;

import java.util.List;

import hackernews.sample.com.rahul.home.model.StoriesModel;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceInterface {
    @GET("v0/topstories.json?print=pretty")
    Observable<List<Integer>> getStories();

    @GET("v0/item/{itemId}.json?print=pretty")
    Observable<StoriesModel> getStory(@Path("itemId") String itemId);

//    @GET("/item/{itemId}.json")
//    Observable<Discussion> getComment(@Path("itemId") long itemId);
}
