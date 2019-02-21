package com.cxz.network.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cxz.network.library.listener.NetChangeObserver;
import com.cxz.network.library.type.NetType;
import com.cxz.network.library.utils.Constants;
import com.cxz.network.library.utils.NetworkUtils;

/**
 * @author chenxz
 * @date 2019/2/21
 * @desc
 */
public class NetStateReceiver extends BroadcastReceiver {

    private NetType netType;

    private NetChangeObserver listener;

    public NetStateReceiver() {
        this.netType = NetType.NONE;
    }

    public void setListener(NetChangeObserver listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            Log.e(Constants.LOG_TAG, "异常了");
            return;
        }

        // 处理广播事件
        if (intent.getAction().equalsIgnoreCase(Constants.ANDROID_NET_CHANGE_ACTION)) {
            Log.e(Constants.LOG_TAG, "网络发生改变");
            netType = NetworkUtils.getNetType(); // 网络类型
            if (NetworkUtils.isNetworkAvailable()) { // 网络连接了
                Log.e(Constants.LOG_TAG, "网络连接成功");
                listener.onConnect(netType);
            }else{
                Log.e(Constants.LOG_TAG, "没有网络连接");
                listener.onDisConnect();
            }
        }

    }
}
