package wanghaisheng.com.xiaoya.navigator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.apkfuns.logutils.LogUtils;

/**
 * Created by sheng on 2016/5/7.
 */
public class BaseNavigator {

    public void start(Context context,Intent intent) {
//        if(!(context instanceof Activity)) {
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        }

        context.startActivity(intent);
    }

    public void start(Context context,Class<?> cls) {
        Intent intent = new Intent(context,cls);
        start(context,intent);
    }

    public void start(Context context, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(context,cls);
        intent.putExtras(bundle);

        start(context,intent);
    }

    /**
     * 设置转场动画的bundle
     * @param activity
     * @param intent
     * @param bundle
     */
    public void start(Activity activity,  Intent intent, Bundle bundle) {
        LogUtils.d(activity);
        ActivityCompat.startActivity(activity, intent, bundle);
    }


}