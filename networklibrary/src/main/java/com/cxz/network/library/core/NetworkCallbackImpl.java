package com.cxz.network.library.core;

import android.annotation.TargetApi;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.util.Log;

import com.cxz.network.library.utils.Constants;

/**
 * @author chenxz
 * @date 2019/2/22
 * @desc
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class NetworkCallbackImpl extends ConnectivityManager.NetworkCallback {

    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
        Log.e(Constants.LOG_TAG, "网络已连接");
    }

    @Override
    public void onLost(Network network) {
        super.onLost(network);
        Log.e(Constants.LOG_TAG, "网络已中断");
    }

    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.e(Constants.LOG_TAG, "网络发生改变，类型为 WIFI");
            } else {
                Log.e(Constants.LOG_TAG, "网络发生改变，类型为 其他");
            }
        }
    }
}
