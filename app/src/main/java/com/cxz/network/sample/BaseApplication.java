package com.cxz.network.sample;

import android.app.Application;

import com.cxz.network.library.NetworkManager;

/**
 * @author chenxz
 * @date 2019/2/22
 * @desc
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkManager.getDefault().init(this);
    }
}
