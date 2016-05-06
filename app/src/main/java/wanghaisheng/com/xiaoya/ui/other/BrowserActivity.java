package wanghaisheng.com.xiaoya.ui.other;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import javax.inject.Inject;

import butterknife.Bind;
import wanghaisheng.com.xiaoya.R;
import wanghaisheng.com.xiaoya.ui.BaseSwipeBackActivity;
import wanghaisheng.com.xiaoya.utils.StringHelper;
import wanghaisheng.com.xiaoya.utils.ToastHelper;

/**
 * Created by sheng on 2016/4/21.
 */
public class BrowserActivity extends BaseSwipeBackActivity{
    public static final String ARG_URL = "url";
    public static final String ARG_TITLE = "title";

    public static void startActivity(Context mContext, String url) {
        Intent intent = new Intent(mContext, BrowserActivity.class);
        intent.putExtra(ARG_URL, url);
        mContext.startActivity(intent);
    }

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    ToastHelper mToastHelper;
    @Inject
    StringHelper mStringHelper;
    private String url;
    private BrowserFragment mFragment;

    @Override
    public int getLayoutId() {
        return R.layout.brower_activity_layout;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void getDatas(Bundle savedInstanceState) {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.removeGroup(R.id.browser);
        getMenuInflater().inflate(R.menu.menu_browser, menu);

        String shareContent = String.format("%s %s ", getTitle() + "", url + "");
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareContent);


        MenuItem shareItem = menu.findItem(R.id.share);
        ShareActionProvider shareProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        shareProvider.setShareHistoryFileName("channe_share.xml");
        shareProvider.setShareIntent(shareIntent);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh) {
            if (mFragment != null) {
                mFragment.reload();
            }
        } else if (item.getItemId() == R.id.copy) {
            mStringHelper.copy(url);
        } else if (item.getItemId() == R.id.to_browser) {
            try {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                startActivity(intent);
            } catch (Exception e) {
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initView() {

        initToolBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        url = getIntent().getStringExtra("url");
        mFragment = BrowserFragment.newInstance(url, "");
        getSupportFragmentManager().beginTransaction().replace(R.id.content, mFragment).commit();
    }

    @Override
    public void initData() {

    }
}
