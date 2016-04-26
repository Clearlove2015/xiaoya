package wanghaisheng.com.xiaoya.presenter.daily;

import android.content.Context;

import com.apkfuns.logutils.LogUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import wanghaisheng.com.xiaoya.api.Daily.DailyApi;
import wanghaisheng.com.xiaoya.beans.Story;
import wanghaisheng.com.xiaoya.db.DBStory;
import wanghaisheng.com.xiaoya.db.DBStoryDao;
import wanghaisheng.com.xiaoya.db.StoryCollection;
import wanghaisheng.com.xiaoya.db.StoryCollectionDao;
import wanghaisheng.com.xiaoya.presenter.base.BaseDetailPresenter;
import wanghaisheng.com.xiaoya.utils.ListUtils;

/**
 * Created by sheng on 2016/4/15.
 */
public class StoryDetailPresenter extends BaseDetailPresenter<Story,StoryDetailView> {
    private static final String TAG = "StoryDetailPresenter";

    @Inject
    DailyApi dailyApi;

    @Inject
    StoryCollectionDao storyCollectionDao;
    @Inject
    DBStoryDao dbStoryDao;

    @Inject @Singleton
    public StoryDetailPresenter() {}

    @Override
    public void detachView() {
        if (null != iView) {
            iView = null;
        }
    }

    /**
     * 通过DailyApi加载Story的详细数据
     */
    public void loadEntityDetail(int storyId, Context context) {
        dailyApi.getStory(storyId)
                .subscribe(new Subscriber<Story>(){
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.v(e);
//                        iView.error("数据访问异常，请稍后重试");
//                        iView.hideLoading();
                    }

                    @Override
                    public void onNext(Story story) {
                        iView.renderEntityView(story);
                    }
                });
    }

    //收藏story
    public void collectEntity(final Story story) {
        DBStory dbStory = convertStoryToDBStory(story);

        LogUtils.v(TAG,"print DBSTORY................");
        LogUtils.v(dbStory);

        dbStory.setIs_collected(true);
        dbStoryDao.insertOrReplace(dbStory);
        StoryCollection storyCollection = convertStoryToStoryCollection(story);
        storyCollectionDao.insertOrReplace(storyCollection);

        iView.collectSuccess();
    }

    /**
     * 取消收藏
     * @param story
     */
    public void unCollectEntity(final Story story) {
        DBStory dbStory = convertStoryToDBStory(story);
        dbStory.setIs_collected(false);
        dbStoryDao.delete(dbStory);
        StoryCollection storyCollection = convertStoryToStoryCollection(story);
        storyCollectionDao.delete(storyCollection);

        iView.uncollectSuccess();
    }

    /**
     * 检测是否article已经收藏
     * @param story
     * @return
     */
    public void checkIfCollected(final Story story) {
        Observable<StoryCollection> articleCollectionObservable = Observable.create(new Observable.OnSubscribe<StoryCollection>() {
            @Override
            public void call(Subscriber<? super StoryCollection> subscriber) {
                subscriber.onNext(storyCollectionDao.load((long) story.getId()));
            }
        });

        articleCollectionObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StoryCollection>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(e.getMessage());
                    }

                    @Override
                    public void onNext(StoryCollection storyCollection) {
                        iView.updateCollectionFlag(null!=storyCollection);
                    }
                });

    }

    /**
     * 复制daily中的属性到DailyCollection
     * @param story
     * @return
     */
    private StoryCollection convertStoryToStoryCollection(Story story) {
        StoryCollection collection = new StoryCollection();
        collection.setId((long)story.getId());
        collection.setTitle(story.getTitle());
        collection.setImage(story.getImage());
        if(!ListUtils.isEmpty(story.getImages())) {
            collection.setImages(story.getImages().get(0));
        }

        collection.setBody(story.getBody());
        collection.setShare_url(story.getShare_url());

        return collection;
    }

    /**
     * 复制Story中的数据到DBStory中
     * @param story
     * @return
     */
    private DBStory convertStoryToDBStory(Story story) {
        DBStory dbStory = new DBStory();
        dbStory.setId((long) story.getId());
        dbStory.setTitle(story.getTitle());
        dbStory.setImage(story.getImage());
        if(!ListUtils.isEmpty(story.getImages())) {
            dbStory.setImages(story.getImages().get(0));
        }

        dbStory.setBody(story.getBody());
        dbStory.setShare_url(story.getShare_url());

        return dbStory;
    }


}
