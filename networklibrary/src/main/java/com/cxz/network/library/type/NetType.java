package com.cxz.network.library.type;

/**
 * @author chenxz
 * @date 2019/2/21
 * @desc 网络类型
 */
public enum NetType {

    // 有网络，包括WiFi、GPRS
    AUTO,

    // WIFI 网络
    WIFI,

    // 主要是PC/笔记本电脑/PDA上网
    CMNET,

    // 手机上网
    CMWAP,

    // 没有任何网络
    NONE

}
