package com.xby.ssq.wavlink.common.http;

import com.xby.ssq.wavlink.common.utils.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoggingInterceptor implements Interceptor {


    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        LogUtil.e("发送url",originalRequest.toString());

        Request authorised = originalRequest.newBuilder()
                .build();
        Response response = chain.proceed(authorised);//执行此次请求
        LogUtil.e("拦截器", response.code()+"");
        if (response.code() == 401) {//返回为token失效
//            LogUtil.e("拦截器", "401");
            Request newRequest = originalRequest.newBuilder()
                    .build();//
            return chain.proceed(newRequest);
        }
        return response;
    }

}