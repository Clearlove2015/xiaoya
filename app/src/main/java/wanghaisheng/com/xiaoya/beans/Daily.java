package wanghaisheng.com.xiaoya.beans;

import java.util.List;

/**
 * Created by sheng on 2016/4/12.
 */
public class Daily {

    private int id;

    private int date;

    private List<Story> stories;

    private List<Story> top_stories;

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Story> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<Story> top_stories) {
        this.top_stories = top_stories;
    }

}
