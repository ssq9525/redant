package com.xby.ssq.wavlink.commonapp.utils;

import android.content.Context;

import com.xby.ssq.wavlink.common.http.RetrofitWrapper1;
import com.xby.ssq.wavlink.common.http.api.RouteAPi;
import com.xby.ssq.wavlink.common.utils.LogUtil;
import com.xby.ssq.wavlink.common.wifi.WifiAdmin;
import com.xby.ssq.wavlink.commonapp.utils.model.WifiDevice;
import com.xby.ssq.wavlink.utils.Constant;
import com.xby.ssq.wavlink.utils.LoadDevice;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

 /**
 * 登录类
 */
public class Loginer {

    private Context context;

    protected Disposable disposable;

    private LoginConditionListener mLoginConditionListener;

    public Loginer(Context context) {
        this.context = context;
        wifiAdmin = new WifiAdmin(context);
    }

    private WifiAdmin wifiAdmin;


    /**
     * 登陆
     *
     * @param strPwd
     */
    public void login(LoginConditionListener loginConditionListener, String strPwd) {
        this.mLoginConditionListener = loginConditionListener;


        LogUtil.e("密码加密", strPwd);
//        LogUtil.e("ip地址", wifiAdmin.getlocalip());

        //不需要重新加载设备信息
        if (!DeviceManager.getInstance(context).isNeedReloadData())
            doLogin(strPwd);
        else//需要重新加载设备信息，则先请求wifi信息
            requestWifiInfo(strPwd);

    }


    /**
     * 用保存的用户名密码登陆
     */
    public void loginOld(final LoginConditionListener loginConditionListener) {


//        String username = DeviceManager.getInstance(context).getNewDevice().username;
//        String pwd = DeviceManager.getInstance(context).getNewDevice().password;
//        if (username == null || username.length() == 0 || pwd == null || pwd.length() == 0)
//            return;
//        username = Sha256.getSHA256StrJava(username);
//        pwd = Sha256.getSHA256StrJava(pwd);
//        this.mLoginConditionListener = loginConditionListener;
//
//        doLogin(username, pwd);
    }


    /**
     * 请求wifi信息
     */
    private void requestWifiInfo(final String pwd) {
        new LoadDevice(context).StartCheck(new CallCheckBack() {
            @Override
            public void ShowStatus(boolean bSuccessful, WifiDevice newDevice) {

                if (bSuccessful && newDevice != null) {
                    DeviceManager.getInstance(context).setNewDevice(newDevice);
                    DeviceManager.getInstance(context).setbSuccessful(bSuccessful);
                    doLogin(pwd);

                } else {
                    if (mLoginConditionListener != null)
                        mLoginConditionListener.fail();
                }
            }

            @Override
            public void pushProcess(String str) {

            }

        });
    }


    private void doLogin(String pwd) {

        String localIp = new RouteUtil(context).GetMylocalIP();


        WifiDevice device = DeviceManager.getInstance(context).getNewDevice();

        pwd = md5(pwd);

        Locale lang = Locale.getDefault();
        String curLang = lang.toString();
        LogUtil.e("当前语言", curLang);
        final String pCmdStr = Constant.HTTP_METHOD + "://" + device.wifi_ip +"/";


        final RouteAPi routeAPi = RetrofitWrapper1.getInstance(pCmdStr, context).create(RouteAPi.class);
        Observable observable;
        if(DeviceManager.getInstance(context).isNewDevice())
            observable = routeAPi.login("app",pwd,localIp,curLang);
        else
            observable = routeAPi.login("app",pwd,localIp,"en");
        observable.subscribeOn(Schedulers.io())
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                        return throwableObservable.zipWith(Observable.range(1, 3), new BiFunction<Throwable, Integer, Integer>() {
                            @Override
                            public Integer apply(Throwable throwable, Integer integer) throws Exception {
                                return integer;
                            }
                        }).flatMap(new Function<Integer, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Integer integer) throws Exception {
                                return Observable.timer((long) Math.pow(3, 3), TimeUnit.SECONDS);
                            }
                        });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        loginResult(responseBody);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (mLoginConditionListener != null)
                            mLoginConditionListener.fail();
                        if (disposable != null && !disposable.isDisposed()) {
                            disposable.dispose();
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (disposable != null && !disposable.isDisposed()) {
                            disposable.dispose();
                        }
                    }
                });


    }

    private void loginResult(ResponseBody responseBody) {
        String strRet = null;
        try {
            strRet = responseBody.string().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtil.e("登录返回字段",strRet);
        if (strRet != null && strRet.length() > 0 && strRet.contains("\n1")) {
            LogUtil.e("登陆成功", "--->");
            if (mLoginConditionListener != null)
                mLoginConditionListener.success();
        } else {
            LogUtil.e("登陆失败", "--->");
            if (mLoginConditionListener != null)
                mLoginConditionListener.fail();
        }
    }

    private String getJson(String localIp, String strMd5Pwd, String curLang) {
        StringBuilder sb = new StringBuilder();
        sb.append("api");
        sb.append("=");
        sb.append("app1");
        sb.append("&");

        sb.append("password");
        sb.append("=");
        sb.append(strMd5Pwd);
        sb.append("&");

        sb.append("ipaddr");
        sb.append("=");
        sb.append(localIp);
        sb.append("&");

        sb.append("lang");
        sb.append("=");
        sb.append(curLang);
        LogUtil.e("拼成的字符", sb.toString());
        return sb.toString();
    }


    private String md5(String string) {
        if (string == null || string.length() <= 0) {
            return "";
        }
        MessageDigest md5 = null;

        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";

            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    public interface LoginConditionListener {
        void success();

        void fail();
    }
}
