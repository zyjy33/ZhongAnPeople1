package com.hengyushop.demo.at;

import com.android.hengyu.web.DialogProgress;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zams.www.health.HospitalHallActivity;

import android.app.ActivityGroup;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * 封装Activity
 */
public abstract class BaseActivity extends ActivityGroup {
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    protected DialogProgress dialogProgress;

    /**
     * 改写父类方法，横竖屏转换不重新开始activity生命周期
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 4.0的时候换回来
         */
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
        dialogProgress = new DialogProgress(BaseActivity.this);

        init();

    }

    /**
     *
     */
    private void init() {

    }

    /**
     * 设置全屏
     */

    /**
     * 重置
     */
    @Override
    protected void onResume() {

        // getWindow().getDecorView().setSystemUiVisibility(View.GONE);
        /**
         * 设置为横屏
         */

        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialogProgress.CloseProgress();
        // 结束Activity&从堆栈中移除
    }

}
