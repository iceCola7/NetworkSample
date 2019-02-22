package com.cxz.network.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cxz.network.library.annotation.Network;
import com.cxz.network.library.bean.MethodManager;
import com.cxz.network.library.listener.NetChangeObserver;
import com.cxz.network.library.type.NetType;
import com.cxz.network.library.utils.Constants;
import com.cxz.network.library.utils.NetworkUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author chenxz
 * @date 2019/2/21
 * @desc
 */
public class NetStateReceiver extends BroadcastReceiver {

    private NetType netType;
    private Map<Object, List<MethodManager>> networkList;

    // private NetChangeObserver listener;

    public NetStateReceiver() {
        this.netType = NetType.NONE;
        networkList = new HashMap<>();
    }

//    public void setListener(NetChangeObserver listener) {
//        this.listener = listener;
//    }

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
                // listener.onConnect(netType);
            } else {
                Log.e(Constants.LOG_TAG, "没有网络连接");
                // listener.onDisConnect();
            }
            // 通知所有注册的方法，网络发生了变化
            post(netType);
        }

    }

    /**
     * 同时分发
     */
    private void post(NetType netType) {
        Set<Object> set = networkList.keySet();
        // 比如获取 MainActivity 对象
        for (final Object getter : set) {
            // 所有注解的方法
            List<MethodManager> methodList = networkList.get(getter);
            if (methodList != null) {
                // 循环每个方法
                for (final MethodManager method : methodList) {
                    // public void network(Object netType){} // 错误的，不匹配的
                    if (method.getType().isAssignableFrom(netType.getClass())) {
                        switch (method.getNetType()) {
                            case AUTO:
                                invoke(method, getter, netType);
                                break;
                            case WIFI:
                                if (netType == NetType.WIFI || netType == NetType.NONE) {
                                    invoke(method, getter, netType);
                                }
                                break;
                            case CMWAP:
                                if (netType == NetType.CMWAP || netType == NetType.NONE) {
                                    invoke(method, getter, netType);
                                }
                                break;
                            case CMNET:
                                if (netType == NetType.CMNET || netType == NetType.NONE) {
                                    invoke(method, getter, netType);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
    }

    /**
     * 执行方法
     */
    private void invoke(MethodManager method, Object getter, NetType netType) {
        Method execute = method.getMethod();
        try {
            execute.invoke(getter, netType);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void registerObserver(Object register) {
        // 获取 MainActivity 中所有网络监听注解方法
        List<MethodManager> methodList = networkList.get(register);
        if (methodList == null) { // 不为空表示以前添加过注册过
            // 开始添加方法，通过反射
            methodList = findAnnotationMethod(register);
            networkList.put(register, methodList);
        }
    }

    /**
     * 找到 register 里符合要求的注解方法
     */
    private List<MethodManager> findAnnotationMethod(Object register) {
        List<MethodManager> methodList = new ArrayList<>();
        Class<?> clazz = register.getClass();
        // 获取 MainActivity 所有的方法
        Method[] methods = clazz.getMethods();
        // 循环
        for (Method method : methods) {
            // 获取方法的注解
            Network network = method.getAnnotation(Network.class);
            if (network == null) {
                continue;
            }

            // 方法返回值校验
            Type returnType = method.getGenericReturnType();
            if (!"void".equals(returnType.toString())) {
                throw new RuntimeException(method.getName() + "方法返回必须是void");
            }

            // 参数校验
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1) {
                throw new RuntimeException(method.getName() + "方法有且只有一个参数");
            }

            // 过滤上面之后，得到符合要求的方法，才开始添加到集合 methodList
            MethodManager manager = new MethodManager(parameterTypes[0], network.netType(), method);
            methodList.add(manager);

        }
        return methodList;
    }

    public void unRegisterObserver(Object register) {
        if (!networkList.isEmpty()) {
            networkList.remove(register);
        }
        Log.e(Constants.LOG_TAG, register.getClass().getName() + "注销成功");
    }

    public void unRegisterAllObserver() {
        if (!networkList.isEmpty()) {
            networkList.clear();
        }
        NetworkManager.getDefault().getApplication().unregisterReceiver(this);
        networkList = null;
        Log.e(Constants.LOG_TAG, "注销全部成功");
    }
}
