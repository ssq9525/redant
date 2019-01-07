package com.xby.ssq.wavlink.utils;

import android.content.Context;
import android.text.TextUtils;

import com.xby.ssq.wavlink.common.utils.AESUtil;
import com.xby.ssq.wavlink.common.utils.Prefs;
import com.xby.ssq.wavlink.common.wifi.WifiAdmin;
import com.xby.ssq.wavlink.commonapp.utils.DeviceManager;
import com.xby.ssq.wavlink.commonapp.utils.model.User;

public class UserUtil {

    private Context context;

    public static final String USER_INFO = "user_info";
    public static final String NAME = "name";
    public static final String PWD = "pwd";
    public static final String isRember = "rember";
    public static final String ENCODE_KEY = "Silver_Crest_12345678901";
    private WifiAdmin wifiAdmin;
    private String singleId;


    public UserUtil(Context context) {
        this.context = context;
    }

    /**
     * 读取用户信息
     *
     * @return
     */
    public User readUser() {

        singleId = getSingleId();

        String name = Prefs.with(context, USER_INFO).read(NAME + singleId, "");
        name = AESUtil.decrypt(ENCODE_KEY, name);
        String pwd = Prefs.with(context, USER_INFO).read(PWD + singleId, "");
        pwd = AESUtil.decrypt(ENCODE_KEY, pwd);
        boolean isRemember = Prefs.with(context, USER_INFO).readBoolean(isRember + singleId, false);
        User user = new User();
        user.setName(name);
        user.setRember(isRemember);
        user.setPwd(pwd);
        return user;
    }


    /**
     * 保存用户数据
     */
    public void saveUser(String pwd, boolean isCheck) {
        singleId = getSingleId();
        pwd = AESUtil.encrypt(ENCODE_KEY, pwd);
        Prefs.with(context, USER_INFO).write(PWD + singleId, pwd);
        Prefs.with(context, USER_INFO).writeBoolean(isRember + singleId, isCheck);

    }


    /**
     * 用户数据清空
     */
    public void clearUser() {
        singleId = getSingleId();
        Prefs.with(context, USER_INFO).write(NAME + singleId, "");
        Prefs.with(context, USER_INFO).write(PWD + singleId, "");
        Prefs.with(context, USER_INFO).writeBoolean(isRember + singleId, false);
    }

    private String getSingleId() {
        String mSSid = "";
        wifiAdmin = new WifiAdmin(context);
        String mac = DeviceManager.getInstance(context).getNewDevice().getMac();
        if (!TextUtils.isEmpty(mac)) {
            mSSid = mac;
        } else
            mSSid = wifiAdmin.getWifiName();
        return mSSid;
    }
}
