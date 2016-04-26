package wanghaisheng.com.xiaoya.ui.setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;

import javax.inject.Inject;

import wanghaisheng.com.xiaoya.AppContext;
import wanghaisheng.com.xiaoya.R;
import wanghaisheng.com.xiaoya.di.component.DaggerFragmentComponent;
import wanghaisheng.com.xiaoya.di.module.FragmentModule;
import wanghaisheng.com.xiaoya.ui.BaseActivity;
import wanghaisheng.com.xiaoya.utils.CacheHelper;
import wanghaisheng.com.xiaoya.utils.DataCleanHelper;
import wanghaisheng.com.xiaoya.utils.FileHelper;
import wanghaisheng.com.xiaoya.utils.SettingPrefHelper;
import wanghaisheng.com.xiaoya.utils.ToastUtil;

/**
 * Created by sheng on 2016/4/20.
 */
public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener,Preference.OnPreferenceClickListener {
//    private ListPreference pTextSize;// 字体大小
    private Preference pPicSavePath;// 图片保存路径
    private Preference pClearCache;
    private ListPreference pSwipeBackEdgeMode;// 手势返回方向

    @Inject
    SettingPrefHelper mSettingPrefHelper;
    @Inject
    CacheHelper mCacheHelper;
    @Inject
    DataCleanHelper mDataCleanHelper;
    @Inject
    ToastUtil toastUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerFragmentComponent.builder()
                .fragmentModule(new FragmentModule(this))
                .appComponent(((AppContext) getActivity().getApplication()).getApplicationComponent())
                .build().inject(this);
        addPreferencesFromResource(R.xml.setting);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());


        /*pTextSize = (ListPreference) findPreference("pTextSize");
        pTextSize.setOnPreferenceChangeListener(this);
        setListSetting(Integer.parseInt(prefs.getString("pTextSize", "4")), R.array.txtSizeNum, pTextSize);*/


        pPicSavePath = findPreference("pPicSavePath");
        pPicSavePath.setOnPreferenceClickListener(this);
        pPicSavePath.setSummary("/sdcard" + File.separator + mSettingPrefHelper.getPicSavePath() + File.separator);


        pClearCache = findPreference("pClearCache");
        pClearCache.setOnPreferenceClickListener(this);
        pClearCache.setSummary(mCacheHelper.getCacheSize());

        pSwipeBackEdgeMode = (ListPreference) findPreference("pSwipeBackEdgeMode");
        pSwipeBackEdgeMode.setOnPreferenceChangeListener(this);
        setListSetting(Integer.parseInt(prefs.getString("pSwipeBackEdgeMode", "0")), R.array.swipeBackEdgeMode, pSwipeBackEdgeMode);

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
       /* if ("pTextSize".equals(preference.getKey())) {
            setListSetting(Integer.parseInt(newValue.toString()), R.array.txtSizeNum, pTextSize);
        } else */
        if ("pSwipeBackEdgeMode".equals(preference.getKey())) {
            setListSetting(Integer.parseInt(newValue.toString()), R.array.swipeBackEdgeMode, pSwipeBackEdgeMode);
            ((BaseActivity) getActivity()).reload();
        }
        /*else if ("pNightMode".equals(preference.getKey())) {
            //mBus.post(new ChangeThemeEvent());
            if (getActivity() instanceof BaseActivity)
                ((BaseActivity) getActivity()).reload();
        }*/
        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if ("pPicSavePath".equals(preference.getKey())) {
            modifyImageSavePath();
        } else if ("pClearCache".equals(preference.getKey())) {
            cleanCache();
        }


        return true;
    }


    private void cleanCache() {
        new MaterialDialog.Builder(getActivity()).title("提示").content("正在清空缓存...").progress(true,0).show();
        mDataCleanHelper.cleanApplicationCache();
        Toast.makeText(getActivity(), "缓存清理成功", Toast.LENGTH_SHORT).show();
        pClearCache.setSummary(mCacheHelper.getCacheSize());
    }

    /*private void setTextSize(int value) {
        String[] valueTitleArr = getResources().getStringArray(R.array.txtSizeNum);
        pTextSize.setSummary(valueTitleArr[value]);
    }*/

    protected void setListSetting(int value, int hintId, ListPreference listPreference) {
        String[] valueTitleArr = getResources().getStringArray(hintId);
        listPreference.setSummary(valueTitleArr[value]);
    }

    @Inject
    FileHelper mFileHelper;


    private void modifyImageSavePath() {
        new MaterialDialog.Builder(getActivity())
                .title("修改图片保存路径")
                .input(null, mSettingPrefHelper.getPicSavePath(), new MaterialDialog.InputCallback() {
            @Override
            public void onInput(MaterialDialog materialDialog, CharSequence charSequence) {
                if (TextUtils.isEmpty(charSequence)) {
                    toastUtil.showToast("路径不能为空");
                    return;
                }
                String path = mFileHelper.getSdcardPath() + File.separator + charSequence + File.separator;
                File file = new File(path);
                if (file.exists() || file.mkdirs()) {
                    mSettingPrefHelper.setPicSavePath(charSequence.toString());
                    pPicSavePath.setSummary("/sdcard" + File.separator + charSequence + File.separator);
                    toastUtil.showToast("更新成功");
                } else {
                    toastUtil.showToast("更新失败");
                }

            }
        }).negativeText("取消").show();
    }

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }
}