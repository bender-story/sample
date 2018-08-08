package hackernews.sample.com.rahul.home.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import hackernews.sample.com.rahul.home.model.StoriesModel;
import hackernews.sample.com.rahul.home.view.HomeView;
import hackernews.sample.com.rahul.rest.ApiClient;
import hackernews.sample.com.rahul.rest.ServiceInterface;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

// Presenter Implementer for Home screen
public class HomePresenterImpl implements HomePresenter {
    private final HomeView view;
   private ServiceInterface serviceInterface;
    private List<Integer> listStoryId = new ArrayList<>();


    public HomePresenterImpl(HomeView view){
        this.view=view;
        serviceInterface = ApiClient.getApiClient().create(ServiceInterface.class);

    }


// This method will get the stroies ids from the web service.
    @Override
    public void getStoriesID() {
        listStoryId = new ArrayList<>();
        Observable<List<Integer>> observable = serviceInterface.getStories();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> longs) {
                        listStoryId.clear();
                        listStoryId.addAll(longs);
                    }

                    @Override
                    public void onError(Throwable e) {
                    Log.d("error",e.toString());
                    }

                    @Override
                    public void onComplete() {
                        fetchStories(listStoryId.subList(0,30));
                    }
                });

    }
// Call dummy data for testing
    @Override
    public void getStoriesID(String string) {
        TypeToken<ArrayList<StoriesModel>> token = new TypeToken<ArrayList<StoriesModel>>() {};
        Gson gson = new Gson();
        ArrayList<StoriesModel> storiesModelArrayList = gson.fromJson(string, token.getType());
        view.loadAdapter(storiesModelArrayList);
    }

    // this method would fetch the details of the each story.
    private void fetchStories(List<Integer> storyIds){
         Single<List<StoriesModel>> mStoryObservable=subListStories(storyIds).cache();
         mStoryObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new SingleObserver<List<StoriesModel>>() {
                     @Override
                     public void onSubscribe(Disposable d) {

                     }

                     @Override
                     public void onSuccess(List<StoriesModel> storiesModels) {
                        view.loadAdapter((ArrayList<StoriesModel>) storiesModels);
                     }

                     @Override
                     public void onError(Throwable e) {

                     }
                 });


    }


// loops through the service calls for fetching the stories.
        @SuppressWarnings("unchecked")
        private Single<List<StoriesModel>> subListStories(final List<Integer> list) {

            return Observable
                    .fromArray(list)
                    .flatMap(new Function<List<Integer>, Observable<Integer>>() {
                        @Override
                        public Observable<Integer> apply(List<Integer> integers) {
                            return Observable.fromIterable(integers);

                        }
                    })
                    .flatMap(new Function<Integer, ObservableSource<StoriesModel>>() {
                        @Override
                        public ObservableSource<StoriesModel> apply(Integer integer) {
                            return serviceInterface.getStory(String.valueOf(integer));
                        }
                    })
                    .onErrorReturn(new Function<Throwable, StoriesModel>() {
                        @Override
                        public StoriesModel apply(Throwable throwable) {
                            return null;
                        }
                    })
                    .filter(new Predicate<StoriesModel>() {
                        @Override
                        public boolean test(StoriesModel storiesModel) {
                            return storiesModel != null ;
                        }
                    })
                    .toList()
                    .map(new Function<List<StoriesModel>, List<StoriesModel>>() {
                        @Override
                        public List<StoriesModel> apply(List<StoriesModel> storiesModels) {
//                            return sortStories(storiesModels, list);
                            return storiesModels;
                        }



                    });
        }




}
