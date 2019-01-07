package com.xby.ssq.wavlink.utils;

import android.content.Context;
import android.widget.Toast;

import com.xby.ssq.wavlink.bean.Person;
import com.xby.ssq.wavlink.common.LoadingDialog.LoadingDialog;
import com.xby.ssq.wavlink.common.http.RetrofitWrapper;
import com.xby.ssq.wavlink.common.http.api.RouteAPi;
import com.xby.ssq.wavlink.common.utils.LogUtil;
import com.xby.ssq.wavlink.common.wifi.WifiAdmin;
import com.xby.ssq.wavlink.commonapp.utils.CallCheckBack;
import com.xby.ssq.wavlink.commonapp.utils.DeviceManager;
import com.xby.ssq.wavlink.commonapp.utils.RouteUtil;
import com.xby.ssq.wavlink.commonapp.utils.model.WifiDevice;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * 加载设备信息类
 */

public class LoadDevice {
    private ThreadIPCheckConn mThreadCheckConn = null;

    private CallCheckBack callFun;
    protected Disposable disposable;

    private boolean isComplete=false;
    private int doNum=0;

    String strIp;

    String oldIp;//记录live_set的ip地址

    String logTag="LoadDevice";

    private LoadingDialog mLoadingDialog;

    private static WeakReference<Context> mContext;



    public LoadDevice(Context context) {
        mContext = new WeakReference<Context>(context);
        Person person=new Person();
        person.setName("test");
        Toast.makeText(context,person.getName(),Toast.LENGTH_LONG).show();
    }

    public LoadDevice() {

    }
    public void test(Context context){
        mContext = new WeakReference<Context>(context);
        Person person=new Person();
        person.setName("test200");
        Toast.makeText(context,person.getName(),Toast.LENGTH_LONG).show();
    }

    public void StartCheck(CallCheckBack callFun) {
        this.callFun = callFun;

        if (mThreadCheckConn == null) {
            mThreadCheckConn = new ThreadIPCheckConn();
        }
        mThreadCheckConn.start();
    }

    private class ThreadIPCheckConn extends Thread {

        public ThreadIPCheckConn() {

        }

        @Override
        public void run() {
            Context context = mContext.get();
            RouteUtil routeUtil = new RouteUtil(context);

            strIp = routeUtil.HostToIp("ap.setup");

            if (strIp == null || strIp.length() == 0) {

                strIp = routeUtil.GetMyGateWay();
            }

            doNum=0;

            loadData(strIp);

            //等待前面执行结束，如果失败，则从中继器获取IP
            while (loadDataIng){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(!isComplete){
                loadDataFromRepeater();
            }

        }
    }


    private boolean loadDataIng=false;
    //从中继器获取IP
    private void loadDataFromRepeater(){
        String strIp=null,strTemp;
        ArrayList<String> strIps=new ArrayList<>();
        Context context = mContext.get();
        RouteUtil routeUtil = new RouteUtil(context);
        strIp=routeUtil.GetMylocalIP();
        strTemp=strIp.substring(0,strIp.lastIndexOf(".")+1);
        doNum=0;
        callFun.pushProcess("showLoading");
        for(int i=1;i<=254&&!isComplete;i++){
            /*while (loadDataIng){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/
            if(isComplete){
                i=254;
                break;
            }
            strIp=strTemp+i;
            loadData(strIp);
        }

        //等待执行完成
        while (doNum<254){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        callFun.pushProcess("dismissLoading");
        if(!isComplete){
            LogUtil.e("返回的设备信息", "失败");
            if (callFun != null)
                callFun.ShowStatus(false, null);
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            };
        }

        return;
    }


    /**
     * 加载 803F5D.txt
     * @param strIP
     */
    private void loadData(final String strIP) {
        LogUtil.i(logTag,strIP);
        loadDataIng=true;
        String strUrl = Constant.HTTP_METHOD + "://" + strIP + "/";
        final Context context = mContext.get();
        final RouteAPi routeAPi = new RetrofitWrapper(context, strUrl).create(RouteAPi.class);
        routeAPi.getData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if((!reLoadDetail)&&(isComplete)){
                            return;
                        }

                        String url = null;
                        try {
                            url = responseBody.string().toString();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        LogUtil.e("retrofit", url + "-->");
                        System.out.println(url);
                        String[] urlSub = url.split("\\\r\n");

                        if(urlSub.length<3){
                            //不符合要求的WIFI,当错误处理
                            doNum++;//计数器加1
                            loadDataIng=false;
                            return;
                        }
                        //旧版
                        if (urlSub != null && urlSub.length <= 4) {

                            WifiDevice newDevice = new WifiDevice();
                            newDevice.setLiveSetting(urlSub[0]);
                            newDevice.setAppLogin(urlSub[1]);
                            newDevice.setSetPwd(urlSub[2]);

                            DeviceManager.getInstance(context).setNewDevice(newDevice);
                            loadDetail(strIP);
                        }//新版
                        else if (urlSub != null && urlSub.length >= 10) {

                            WifiDevice newDevice = new WifiDevice();
                            newDevice.setLiveSetting(urlSub[0]);
                            newDevice.setAppLogin(urlSub[1]);
                            newDevice.setSetPwd(urlSub[2]);

                            String ver = urlSub[3];
                            if (ver.contains("=")) {
                                String[] vers = ver.split("=");
                                ver = vers[1];
                            } else
                                ver = "1";
                            newDevice.setVer(ver);
                            if (ver.compareTo("2") >= 0)
                                DeviceManager.getInstance(context).setNewDevice(true);
                            else
                                DeviceManager.getInstance(context).setNewDevice(false);

                            newDevice.setHomeUrl(getUrl(urlSub[4]));
                            newDevice.setWizardUrl(getUrl(urlSub[5]));
                            newDevice.setWifiUrl(getUrl(urlSub[6]));
                            newDevice.setLedUrl(getUrl(urlSub[7]));
                            newDevice.setStatusUrl(getUrl(urlSub[8]));
                            newDevice.setSetUpUrl(getUrl(urlSub[9]));

                            if (urlSub.length >= 11) {
                                String initUrl = urlSub[10];
                                newDevice.setInitUrl(getUrl(initUrl));
                            }

                            DeviceManager.getInstance(context).setNewDevice(newDevice);
                            loadDetail(strIP);
                        }
                        oldIp = strIP;

                    }

                    @Override
                    public void onError(Throwable e) {
                        doNum++;//计数器加1
                        loadDataIng=false;

                        /*if (callFun != null)
                            callFun.ShowStatus(false, null);
                        if (!disposable.isDisposed())
                            disposable.dispose();//取消订阅*/

                    }

                    @Override
                    public void onComplete() {
                        //doNum++;//计数器加1
                        loadDataIng=false;
                    }
                });

    }


    /**
     * 加载设备live_setting信息
     */
    boolean reLoadDetail=false;
    private void loadDetail(String ip) {
        if((!reLoadDetail)&&(isComplete)){
            return;
        }
        reLoadDetail=false;
        loadDataIng=false;

        String strUrl = Constant.HTTP_METHOD + "://" + ip + "/";
        final Context context = mContext.get();
        RouteAPi routeAPi = new RetrofitWrapper(context, strUrl).create(RouteAPi.class);
        routeAPi.getDetail(DeviceManager.getInstance(context).getNewDevice().getLiveSetting())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if((!reLoadDetail)&&(isComplete)){
                            return;
                        }

                        //isComplete=true;
                        doNum=254;//计数器加满，执行完成
                        loadDataIng=false;

                        String result = null;
                        try {
                            result = responseBody.string().toString();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        LogUtil.e("retrofit", result + "----->");
                        WifiDevice wifiDevice = dealData(result);
                        //旧设备的处理
                        if(!DeviceManager.getInstance(context).isNewDevice()) {
                            if (callFun != null)
                                callFun.ShowStatus(true, wifiDevice);
                            isComplete=true;
                            if (!disposable.isDisposed())
                                disposable.dispose();
                            return;
                        }

                        //新设备--连接最新的路由
                        if(!DeviceManager.getInstance(context).getNewDevice().wifi_ip.equals(oldIp))
                        {
                            oldIp = DeviceManager.getInstance(context).getNewDevice().wifi_ip;
                            //isComplete=false;//当前面的WIFI通过以后，后面的WIFI就不该再设了
                            reLoadDetail=true;
                            loadDetail(DeviceManager.getInstance(context).getNewDevice().wifi_ip);
                        }
                        else
                        {
                            if (callFun != null)
                                callFun.ShowStatus(true, wifiDevice);
                            isComplete=true;
                            if (!disposable.isDisposed())
                                disposable.dispose();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        doNum++;
                        loadDataIng=false;
                        e.printStackTrace();
                        //isComplete=false;//有问题，如果前面的ip已经通过了检验，而后面的IP有错，这时就矛盾了

                        /*if (callFun != null)
                            callFun.ShowStatus(false, null);
                        if (!disposable.isDisposed())
                            disposable.dispose();*/
                    }

                    @Override
                    public void onComplete() {
                        doNum++;
                        loadDataIng=false;
                    }
                });
    }


    /**
     * load live-set文件
     *
     * @param strVer
     * @return
     */
    private WifiDevice dealData(String strVer) {
        Context context = mContext.get();
        String[] urlName = strVer.split("\\\r\n");
        if (urlName == null || urlName.length < 4 || urlName[0] == null || urlName[0].length() == 0)
            return null;

        String wifiName = "";
        String[] ssidName = urlName[0].split("\\=");
        if (ssidName.length >= 2 && ssidName[0] != null && ssidName[0].length() > 0) {
            wifiName = ssidName[1];

        } else {
            wifiName = urlName[0];
        }

        if (wifiName == null || wifiName.length() == 0)
            return null;

        WifiDevice newDevice = DeviceManager.getInstance(context).getNewDevice();
        WifiAdmin admin = new WifiAdmin(context);
        newDevice.wif_ssid = admin.getWifiName();

        newDevice.wifi_ip = urlName[3];

        if (urlName != null && urlName.length >= 7) {

            String initSource = urlName[5];
            String initCode = getValue(initSource);
            if(initCode.equals("10"))//初始化
                newDevice.setInit(true);
            else
                newDevice.setInit(false);


            //mac地址
            String sourceMac = urlName[6];
            String mac = getValue(sourceMac);
            newDevice.setMac(mac);
        }
        if (urlName != null && urlName.length >= 8) {
            newDevice.random = urlName[7];
        }
        return newDevice;
    }


    private String getUrl(String sourceUrl) {
        int index = sourceUrl.indexOf("=");
        sourceUrl = sourceUrl.substring(index + 1, sourceUrl.length());
        return sourceUrl;
    }


    private  String getValue(String source)
    {
        String des ="";
        if (source.contains("=")) {
            String[] vers = source.split("=");
            des = vers[1];
        }
        return des;
    }

}
