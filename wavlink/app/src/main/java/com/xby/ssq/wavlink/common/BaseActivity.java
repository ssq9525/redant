package com.xby.ssq.wavlink.common;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xby.ssq.wavlink.R;
import com.xby.ssq.wavlink.common.utils.ActivityManager;

public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar toolBar;

    protected TextView tvTitle;

    //AppBarLayout appBarLayout;

    public ImageView rightIv;
    public ImageView leftIv;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManager().pushActivity(this);
        //全屏
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //导航栏 @ 底部
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //window.setStatusBarColor(getResources().getColor(R.color.white));
        //window.setNavigationBarColor(R.color.blue);
        window.setNavigationBarColor(getResources().getColor(R.color.white));

        setContentView(R.layout.activity_main2);

        initData();

        //appBarLayout = findViewById(R.id.appBar_layout);
        toolBar =  findViewById(R.id.tool_bar);
        tvTitle =  findViewById(R.id.tv_title);
        FrameLayout contentView = findViewById(R.id.ContentView);
        toolBar.setTitle("");

        rightIv = findViewById(R.id.title_iv);
        leftIv = findViewById(R.id.left_title_iv);




        toolBar.setNavigationIcon(R.mipmap.set_back_nor);

        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LayoutInflater.from(this).inflate(initLayout(), contentView);

        setNavigationShow(false);

        initView();

        steepStatusBar();

    }


    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 设置标题
     *
     * @return
     */
    public void setTitle(String title) {
        setNavigationShow(true);
        tvTitle.setText(title);
    }

    /**
     * 设置标题
     *
     * @return
     */
    public void setTitle(int title) {
        setNavigationShow(true);
        tvTitle.setText(title);
    }

    private void setNavigationShow(boolean isShow) {
        //appBarLayout.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }


    public void setRightImage(int imgId)
    {
        rightIv.setImageResource(imgId);
    }
    public void setLeftImage(int imgId)
    {
        leftIv.setImageResource(imgId);
    }



    /**
     * 隐藏软件盘
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 点击软键盘之外的空白处，隐藏软件盘
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideSoftKeyBoard(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    private boolean isShouldHideSoftKeyBoard(View view, MotionEvent event) {
        if (view != null && (view instanceof EditText)) {
            int[] l = { 0, 0 };
            view.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top +view.getHeight(), right = left
                    + view.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // If click the EditText event ,ignore it
                return false;
            } else {
                return true;
            }
        }
        // if the focus is EditText,ignore it;
        return false;
    }

    /**
     * 显示软键盘
     */
    public void showInputMethod(){
        if (getCurrentFocus() != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(),0);
        }
    }





    /**
     * 设置布局
     *
     * @return
     */
    public abstract int initLayout();

    /**
     * 初始化布局
     */
    public abstract void initView();

    /**
     * 设置数据
     */
    public abstract void initData();


    @Override
    protected void onDestroy() {
        ActivityManager.getActivityManager().popActivity(this);
        super.onDestroy();
    }
}
