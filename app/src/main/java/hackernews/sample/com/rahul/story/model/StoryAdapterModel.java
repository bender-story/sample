package hackernews.sample.com.rahul.story.model;

import hackernews.sample.com.rahul.home.model.StoriesModel;

public class StoryAdapterModel {

    private int type;
    private StoriesModel mainStory=null;
    private CommentsModel commentsModel=null;

    public StoryAdapterModel(int type, StoriesModel mainStory) {
        this.type = type;
        this.mainStory = mainStory;
    }

    public StoryAdapterModel(int type, CommentsModel commentsModel) {
        this.type = type;
        this.commentsModel = commentsModel;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public StoriesModel getMainStory() {
        return mainStory;
    }

    public void setMainStory(StoriesModel mainStory) {
        this.mainStory = mainStory;
    }

    public CommentsModel getCommentsModel() {
        return commentsModel;
    }

    public void setCommentsModel(CommentsModel commentsModel) {
        this.commentsModel = commentsModel;
    }
}
