package com.xby.ssq.wavlink.common.http;

import android.content.Context;

import com.xby.ssq.wavlink.common.utils.SSLSocketFactoryUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitWrapper {

    private static RetrofitWrapper instance;
    private Retrofit mRetrofit;
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static final long CONNECT_TIMEOUT = 5;
    private static final long READ_TIMEOUT = 10;
    private static Context mContext;

    public RetrofitWrapper(Context context,String url) {
        mContext = context;
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor())
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .sslSocketFactory(SSLSocketFactoryUtils.createSSLSocketFactory(), SSLSocketFactoryUtils.createTrustAllManager())
                .hostnameVerifier(new SSLSocketFactoryUtils.TrustAllHostnameVerifier())
                .addInterceptor(new LoggingInterceptor())
                .readTimeout(READ_TIMEOUT,TimeUnit.SECONDS)
                .build();
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }



    public <T> T create(final Class<T> service) {
        return mRetrofit.create(service);
    }


}
