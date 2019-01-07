package com.xby.ssq.wavlink.login.data;

import com.xby.ssq.wavlink.commonapp.utils.model.WifiDevice;

public interface DataSource {


    public interface DataLoadBack {
        void callback(WifiDevice wifiDevice, boolean isSuccess);
    }


    public void loadData(DataLoadBack dataLoadBack);
}
