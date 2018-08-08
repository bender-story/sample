package hackernews.sample.com.rahul.story.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import hackernews.sample.com.rahul.home.model.StoriesModel;
import hackernews.sample.com.rahul.rest.ApiClient;
import hackernews.sample.com.rahul.rest.ServiceInterface;
import hackernews.sample.com.rahul.story.model.CommentsModel;
import hackernews.sample.com.rahul.story.model.StoryAdapterModel;
import hackernews.sample.com.rahul.story.view.StoryAdapter;
import hackernews.sample.com.rahul.story.view.StoryView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class StoryPresenterImpl implements StoryPresenter {
    private final StoryView view;
    private ServiceInterface serviceInterface;
    // List that is used to bind data to adapter.
    private final List<StoryAdapterModel> list;
    private List<StoriesModel> parentCommentsList;
    private List<StoriesModel> childCommentsList;

    private final int PARENT_COMMENT=0;
    private final int CHILD_COMMENT=1;



    public StoryPresenterImpl(StoryView view,List<StoryAdapterModel> list){
        this.view=view;
        serviceInterface = ApiClient.getApiClient().create(ServiceInterface.class);
        this.list=list;

    }

    @Override
    public void getComments(List<Integer> kids) {
        if(kids!=null && kids.size()!=0)
        fetchComments(kids,PARENT_COMMENT);
        else
            view.loadAdapter(list);
    }


    @Override
    public void getComments(String[] jsonString) {
        // gets parent comments
        parentCommentsList=getListForOffline(jsonString[0]);
        //gets child comments
        childCommentsList=getListForOffline(jsonString[1]);
        sortComments();
        view.loadAdapter(list);

    }
    private ArrayList<StoriesModel> getListForOffline(String json){
        TypeToken<ArrayList<StoriesModel>> token = new TypeToken<ArrayList<StoriesModel>>() {};
        Gson gson = new Gson();
        return gson.fromJson(json, token.getType());
    }


    private void getChildComments() {
        if(parentCommentsList!=null && parentCommentsList.size()!=0)
        {
            fetchComments(getCommentsChildList(),CHILD_COMMENT);
        }


    }

    private void fetchComments(List<Integer> storyIds, final int type){
        Single<List<StoriesModel>> mStoryObservable=subListStories(storyIds).cache();
        mStoryObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<StoriesModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<StoriesModel> storiesModels) {
                        if(type==PARENT_COMMENT){
                            parentCommentsList=storiesModels;
                            getChildComments();
                        }else if(type==CHILD_COMMENT){
                            childCommentsList=storiesModels;
                            sortComments();
                            view.loadAdapter(list);
                        }
//                        view.loadAdapter((ArrayList<StoriesModel>) storiesModels);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });


    }



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

// method to get first level of comments id's.
    private ArrayList<Integer> getCommentsChildList(){
        ArrayList<Integer> commentsChildList=new ArrayList<>();

        for (StoriesModel model : parentCommentsList)
        {
            if(model.getKids()!=null)
            commentsChildList.add (model.getKids().get(0));
        }

        return commentsChildList;


    }

    private void sortComments(){
        for (StoriesModel model : parentCommentsList)
        {
            CommentsModel commentsModel= new CommentsModel(model.getId(),model);
            for (StoriesModel childModel: childCommentsList){
                if(model.getId().equals(childModel.getParent())) {
                    commentsModel.setChildComment(childModel);
                    break;
                }
            }

            list.add(new StoryAdapterModel(StoryAdapter.COMMENT_TYPE,commentsModel));
        }
    }
}
