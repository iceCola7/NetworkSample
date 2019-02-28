package com.cxz.network.library;

import android.app.Application;
import android.content.IntentFilter;

import com.cxz.network.library.utils.Constants;

/**
 * @author chenxz
 * @date 2019/2/21
 * @desc
 */
public class NetworkManager {

    private static volatile NetworkManager instance;
    private Application application;
    private NetStateReceiver receiver;

    private NetworkManager() {
        receiver = new NetStateReceiver();
    }

    public static NetworkManager getDefault() {
        if (instance == null) {
            synchronized (NetworkManager.class) {
                if (instance == null) {
                    instance = new NetworkManager();
                }
            }
        }
        return instance;
    }

    public Application getApplication() {
        if (application == null) {
            throw new RuntimeException("NetworkManager.getDefault().init() 未初始化");
        }
        return application;
    }

    public void init(Application application) {
        this.application = application;
        // 做广播注册
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ANDROID_NET_CHANGE_ACTION);
        application.registerReceiver(receiver, filter);

        // 第二种方式监听，不通过网络
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            ConnectivityManager.NetworkCallback networkCallback = new NetworkCallbackImpl();
//            NetworkRequest.Builder builder = new NetworkRequest.Builder();
//            NetworkRequest request = builder.build();
//            ConnectivityManager connectivityManager = (ConnectivityManager) NetworkManager.getDefault().getApplication()
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            if (connectivityManager != null) {
//                connectivityManager.registerNetworkCallback(request, networkCallback);
//            }
//        } else {
//        }
    }

//    public void setListener(NetChangeObserver listener) {
//        receiver.setListener(listener);
//    }

    /**
     * 注册
     */
    public void registerObserver(Object register) {
        receiver.registerObserver(register);
    }

    /**
     * 移除
     */
    public void unRegisterObserver(Object register) {
        receiver.unRegisterObserver(register);
    }

    /**
     * 移除所有
     */
    public void unRegisterAllObserver() {
        receiver.unRegisterAllObserver();
    }

}
