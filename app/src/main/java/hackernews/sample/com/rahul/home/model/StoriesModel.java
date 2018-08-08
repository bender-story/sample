package hackernews.sample.com.rahul.home.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoriesModel implements Serializable {
    @SerializedName("by")
    @Expose
    private String by;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("kids")
    @Expose
    private List<Integer> kids = null;
    @SerializedName("parent")
    @Expose
    private Integer parent;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("type")
    @Expose
    private String type;


    @SerializedName("deleted")
    @Expose
    private boolean deleted;
    @SerializedName("dead")
    @Expose
    private boolean dead;
    @SerializedName("poll")
    @Expose
    private Integer poll;

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("score")
    @Expose
    private Integer score;

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("parts")
    @Expose
    private List<Integer> parts = null;

    @SerializedName("descendants")
    @Expose
    private Integer descendants;



    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getKids() {
        return kids;
    }

    public void setKids(List<Integer> kids) {
        this.kids = kids;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public Integer getPoll() {
        return poll;
    }

    public void setPoll(Integer poll) {
        this.poll = poll;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Integer> getParts() {
        return parts;
    }

    public void setParts(List<Integer> parts) {
        this.parts = parts;
    }

    public Integer getDescendants() {
        return descendants;
    }

    public void setDescendants(Integer descendants) {
        this.descendants = descendants;
    }
}
