package com.cxz.networklib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.cxz.networklib.NetworkManager;
import com.cxz.networklib.type.NetType;

/**
 * @author chenxz
 * @date 2019/2/21
 * @desc
 */
public class NetworkUtils {

    /**
     * 网络是否可用
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) NetworkManager.getDefault().getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) return false;
        // 返回所有网络信息
        NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
        if (info != null) {
            for (NetworkInfo anInfo : info) {
                if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取当前的网络类型： -1：没有网络  1：WIFI网络  2：wap 网络  3：net 网络
     */
    public static NetType getNetType() {
        ConnectivityManager connectivityManager = (ConnectivityManager) NetworkManager.getDefault().getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) return NetType.NONE;
        // 获取当前激活的网络连接信息
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NetType.NONE;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
                return NetType.CMNET;
            } else {
                return NetType.CMWAP;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            return NetType.WIFI;
        }
        return NetType.NONE;
    }

}
