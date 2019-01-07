package com.xby.ssq.wavlink.commonapp.utils;

import android.content.Context;

import com.xby.ssq.wavlink.common.wifi.WifiAdmin;
import com.xby.ssq.wavlink.commonapp.utils.model.WifiDevice;

import java.lang.ref.WeakReference;

public class DeviceManager {

    private static DeviceManager instance;
    private static WeakReference<Context> mWeakContext;

    private boolean isNewDevice; //是否是新的设备



    public boolean isNewDevice() {
        return isNewDevice;
    }

    public void setNewDevice(boolean newDevice) {
        isNewDevice = newDevice;
    }

    private DeviceManager() {
    }

    public static DeviceManager getInstance(Context context)

    {
        mWeakContext = new WeakReference<>(context);
        if (instance == null)
            instance = new DeviceManager();
        return instance;
    }

    /**
     * 数据是否成功返回
     */
    private boolean bSuccessful = false;


    /**
     * wifi信息
     */
    private WifiDevice newDevice;

    public boolean isbSuccessful() {
        return bSuccessful;
    }

    public void setbSuccessful(boolean bSuccessful) {
        this.bSuccessful = bSuccessful;
    }


    //是否连接上设备
    public boolean isLinkToWifi() {
        Context context = mWeakContext.get();
        WifiAdmin wifiAdmin = new WifiAdmin(context);
        if (wifiAdmin == null || wifiAdmin.getWifiName() == null || wifiAdmin.getWifiName().length() == 0)
            return false;
        return true;

    }


    /**
     * 是否连接wifi成功
     *
     * @return
     */
    public boolean isLinkToWifiSuccess() {
        Context context = mWeakContext.get();
        WifiAdmin wifiAdmin = new WifiAdmin(context);
        if (bSuccessful && newDevice != null && wifiAdmin.getWifiName().equals(newDevice.wif_ssid)) {
            return true;
        }
        return false;
    }


    /**
     * 设置成不能设置密码状态
     */
    public void setNoNeedPwd() {
        newDevice.flag = 1;
    }

    /**
     * 是否需要设置密码
     *
     * @return
     */
    public boolean isNeedSetPwd() {
        int flag = newDevice.flag;
        if (flag == 0)
            return true;
        return false;
    }

    public WifiDevice getNewDevice() {
        if (newDevice == null)
            newDevice = new WifiDevice();
        return newDevice;
    }

    public void setNewDevice(WifiDevice newDevice) {
        this.newDevice = newDevice;
    }


    /**
     * 是否需要重新请求数据
     *
     * @return
     */
    public boolean isNeedReloadData() {
        Context context = mWeakContext.get();
        WifiAdmin wifiAdmin = new WifiAdmin(context);
        String wifiName = wifiAdmin.getWifiName();
        if (wifiName == null || wifiName.length() == 0)
            return false;
        if (newDevice == null || newDevice.wif_ssid == null)
            return true;
        if (!wifiName.equals(newDevice.wif_ssid))
            return true;
        else {
            if (bSuccessful && newDevice != null)
                return false;
            else
                return true;
        }
    }


}
