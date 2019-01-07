package com.xby.ssq.wavlink.common.http.api;

import com.xby.ssq.wavlink.common.model.CategoryResult;
import com.xby.ssq.wavlink.common.model.ResultsBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RouteAPi {

    /**
     * @param category 类别
     * @param count    条目数目
     * @param page     页数
     */
    @GET("data/{category}/{count}/{page}")
    Observable<CategoryResult> getCategoryData(@Path("category") String category, @Path("count") int count, @Path("page") int page);


    @GET("data/{category}/{count}/{page}")
    Observable<CategoryResult<ResultsBean>> getCategoryData1(@Path("category") String category, @Path("count") int count, @Path("page") int page);


    /**
     * 获取设备初始信息
     * @return
     */
    @GET("803F5D.txt")
    Observable<ResponseBody> getData();


    /**
     * 获取设备详细信息
     * @param url
     * @return
     */
    @GET("{url}")
    Observable<ResponseBody> getDetail(@Path("url") String url);


    /**
     * 登录
     * @return
     */
    @GET("cgi-bin/applogin.cgi")
    Observable<ResponseBody> login(@Query("api") String api, @Query("password") String password,
                                   @Query("ipaddr") String ipaddr, @Query("lang") String lang);


    /**
     * 登出
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @POST("cgi-bin/applogin.cgi")
    Observable<ResponseBody> logout(@Body RequestBody body);

//    api=app&password=bc793047f62fb42dde43bf37ec7c2294&&username=Admin123&&ipaddr=192.168.0.110
}
