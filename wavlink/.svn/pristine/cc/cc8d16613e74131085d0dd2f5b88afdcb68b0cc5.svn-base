package com.xby.ssq.wavlink.common;

public class MessageEvent {

    private int state = 1;

    public static final int LINKED_NEW_SUCCESS = 1;  //连接新wifi，并且请求数据成功

    public static final int UNLINKED = 2; //断开wifi

    public static final int RELINKED = 3; //再次连接上之前的wifi

    public static final int LINK_NEW_FAIL = 4; //连接wifi成功，但请求数据失败



    public MessageEvent(int state) {
        this.state = state;
    }


    public int getState() {
        return state;
    }
}
