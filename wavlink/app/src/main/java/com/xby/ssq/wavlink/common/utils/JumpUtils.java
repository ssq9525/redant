package com.xby.ssq.wavlink.common.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class JumpUtils {
    private Activity context;

    public static final String DATA = "data";


    public JumpUtils(Activity context) {
        this.context = context;
    }


    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param data
     */
    public void startActivityWithString(Class<?> clz, String data) {
        Intent intent = new Intent();
        intent.setClass(context, clz);
        intent.putExtra(DATA,data);
        context.startActivity(intent);
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param data
     */
    public void startActivityWithBoolean(Class<?> clz, boolean data) {
        Intent intent = new Intent();
        intent.setClass(context, clz);
        intent.putExtra(DATA,data);
        context.startActivity(intent);
    }

    public void startActivityClear(Class<?> clz) {
        startActivityClear(clz, null);
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param bundle
     */
    public void startActivityClear(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }



    /**
     * 含有Bundle通过Class打开编辑界面
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * add by ssq 2018-8-8
     * 用浏览器打开指定网页
     * @param url 网址
     * */
    public void openWebByBrowser(String url){

        Intent intent= new Intent();
        intent.setAction("android.intent.action.VIEW");
        //Uri content_url = Uri.parse("http://wilink.speedtestcustom.com/");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        //intent.setClassName("com.android.browser","com.android.browser.BrowserActivity");//内置浏览器
        //intent.setClassName("com.uc.browser", "com.uc.browser.ActivityUpdate");//uc浏览器
        //intent.setClassName("com.opera.mini.android", "com.opera.mini.android.Browser");//opera浏览器
        //intent.setClassName("com.tencent.mtt", "com.tencent.mtt.MainActivity");//qq浏览器
        context.startActivity(intent);
    }
}
