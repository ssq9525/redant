package com.xby.ssq.wavlink.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseFragment extends Fragment {

    public BaseFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(initLayout(), container, false);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

        initData();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.getState() == MessageEvent.LINKED_NEW_SUCCESS) {
            linkedNet();
        } else if (event.getState() == MessageEvent.UNLINKED) {
            unLinkNet();
        } else if (event.getState() == MessageEvent.RELINKED)
            reLinkNet();
        else if(event.getState() == MessageEvent.LINK_NEW_FAIL)
            linkFail();
    }

    /**
     * 网路断开
     */
    public abstract void unLinkNet();

    /**
     * 连接上网路
     */
    public abstract void linkedNet();

    /**
     * 再次连接上网路
     */
    public abstract void reLinkNet();


    /**
     * 连接wifi成功，但请求数据失败
     */
    public abstract void linkFail();

    /**
     * 设置布局
     *
     * @return
     */
    public abstract int initLayout();

    /**
     * 初始化布局
     */
    public abstract void initView(View view);

    /**
     * 设置数据
     */
    public abstract void initData();


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
