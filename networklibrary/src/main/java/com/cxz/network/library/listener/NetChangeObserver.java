package com.cxz.network.library.listener;

import com.cxz.network.library.type.NetType;

/**
 * @author chenxz
 * @date 2019/2/21
 * @desc
 */
public interface NetChangeObserver {

    /**
     * 网络连接时调用
     */
    void onConnect(NetType type);

    /**
     * 没有网络时调用
     */
    void onDisConnect();

}
