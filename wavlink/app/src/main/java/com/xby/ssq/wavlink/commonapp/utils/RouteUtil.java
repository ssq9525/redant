package com.xby.ssq.wavlink.commonapp.utils;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class RouteUtil {
    private Context mContext;




    public RouteUtil(Context context) {
        this.mContext = context;
    }




//    public boolean login(WifiDevice device, String localIp, String name, String pwd) {
//
//
//        if (device == null || device.strCommand == null || device.strCommand.length() <= 0 || pwd == null || pwd.length() <= 0 || device.wifi_ip == null || device.wifi_ip.length() <= 0)
//            return false;
//
//        String strMd5Pwd = Mymd5(pwd);
//
//
//        String[] subStr = DeviceManager.getInstance(mContext).getLiveBean().getAllUrl().split("\\\r\n");
//
//        if (subStr.length >= 2 && subStr[1] != null && subStr[1].length() > 0) {
//            String pCmdStr = "https://" + device.wifi_ip + subStr[1] + strMd5Pwd + "&&username=" + name + "&&ipaddr=" + localIp;
//            LogUtil.e("url",pCmdStr);
//
//            String strRet = WiFiHttpSet.get(pCmdStr);
//            if (strRet != null && strRet.length() > 0 && strRet.contains("\n1")) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//    private String Mymd5(String string) {
//        if (string == null || string.length() <= 0) {
//            return "";
//        }
//        MessageDigest md5 = null;
//
//        try {
//            md5 = MessageDigest.getInstance("MD5");
//            byte[] bytes = md5.digest(string.getBytes());
//            String result = "";
//
//            for (byte b : bytes) {
//                String temp = Integer.toHexString(b & 0xff);
//                if (temp.length() == 1) {
//                    temp = "0" + temp;
//                }
//                result += temp;
//            }
//            return result;
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }


//    public WifiDevice OpenIP(String strIP) {
//
//        String strUrl = "https://" + strIP + "/";
//        String strRet = WiFiHttpSet.get(strUrl);
//        if (strRet == null || strRet.length() == 0 || strRet.contains("404"))
//            return null;
//        System.out.println(strRet);
//
//        String[] urlSub = strRet.split("\\\r\n");
//        if (urlSub == null || urlSub.length < 2 || urlSub[0] == null || urlSub[0].length() == 0)
//            return null;
//
//        strUrl = "https://" + strIP + "/" + urlSub[0];
//        String strVer = WiFiHttpSet.get(strUrl);
//        if (strVer == null || strVer.length() == 0 || strVer.contains("404"))
//            return null;
//        LogUtil.e("返回的数据", strVer);
//
//        String[] urlName = strVer.split("\\\r\n");
//        if (urlName == null || urlName.length < 4 || urlName[0] == null || urlName[0].length() == 0)
//            return null;
//
//        String wifiName = "";
//        String[] ssidName = urlName[0].split("\\=");
//        if (ssidName.length >= 2 && ssidName[0] != null && ssidName[0].length() > 0) {
//            wifiName = ssidName[1];
//
//        } else {
//            wifiName = urlName[0];
//        }
//
//        if (wifiName == null || wifiName.length() == 0)
//            return null;
//
//        WifiDevice newDevice = new WifiDevice();
//        newDevice.strCommand = strRet;
////        newDevice.wif_ssid = wifiName;
//        WifiAdmin admin = new WifiAdmin(mContext);
//        newDevice.wif_ssid = admin.getWifiName();
//
//        if (urlName[3] == null || urlName[3].length() <= 0)
//            newDevice.wifi_ip = strIP;
//        else
//            newDevice.wifi_ip = urlName[3];
//
//        if (urlName != null && urlName.length >= 7) {
//            if (urlName[6] != null && urlName[6].length() > 0)
//                try {
//                    newDevice.flag = Integer.valueOf(urlName[6]);
//                } catch (NumberFormatException e) {
//                    newDevice.flag = 0;
//                    e.printStackTrace();
//                }
//        }
//        return newDevice;
//    }

    //获取路邮器的地址信息
    public String GetMylocalIP() {

        WifiManager wifi_service = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = wifi_service.getDhcpInfo();
        WifiInfo wifiinfo = wifi_service.getConnectionInfo();
        System.out.println("Wifi info----->" + wifiinfo.getIpAddress());
        System.out.println("DHCP info gateway----->" + Formatter.formatIpAddress(dhcpInfo.gateway));
        System.out.println("DHCP info netmask----->" + Formatter.formatIpAddress(dhcpInfo.netmask));
        System.out.println("DHCP info locaip----->" + Formatter.formatIpAddress(dhcpInfo.ipAddress));
        return Formatter.formatIpAddress(dhcpInfo.ipAddress);
    }

    public String GetMyGateWay() {
        WifiManager wifi_service = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = wifi_service.getDhcpInfo();
        WifiInfo wifiinfo = wifi_service.getConnectionInfo();
        return Formatter.formatIpAddress(dhcpInfo.gateway);
    }

    public String GetWiFiSSID() {
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo == null) return "";
        //Log.d("wifiInfo", wifiInfo.toString());
        //Log.d("SSID",wifiInfo.getSSID());
        return wifiInfo.getSSID();
    }

    public String HostToIp(String strHost) {
        if (strHost == null || strHost.length() <= 0) return "";

        try {
            InetAddress x = InetAddress.getByName(strHost);
            String ip_devdiv = x.getHostAddress();//得到字符串形式的ip地址
            return ip_devdiv;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            //Log.d("putoutmsg", "域名解析出错");
        }
        return "";
    }
}
