package hackernews.sample.com.rahul.story.model;

import hackernews.sample.com.rahul.home.model.StoriesModel;

public class CommentsModel {

    private int id;
    private StoriesModel parentComment;
    private StoriesModel childComment=null;

    public CommentsModel(int id, StoriesModel parentComment) {
        this.id = id;
        this.parentComment = parentComment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StoriesModel getParentComment() {
        return parentComment;
    }

    public void setParentComment(StoriesModel parentComment) {
        this.parentComment = parentComment;
    }

    public StoriesModel getChildComment() {
        return childComment;
    }

    public void setChildComment(StoriesModel childComment) {
        this.childComment = childComment;
    }
}
