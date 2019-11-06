# NetworkSample

采用 `EventBus` 原理实现 `Android` 网络状态变化监听

## 使用方式

##### 第一步：添加依赖

```
implementation 'com.cxz:networklib:1.0.0'
```

##### 第二步：在 Application 里对框架初始化

```
public void onCreate() {
    super.onCreate();
    NetworkManager.getDefault().init(this);
}
```

##### 第三步：分别在 Activity 的 onCreate() 和 onDestroy() 里进行注册和反注册操作

```
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
	// 注册
    NetworkManager.getDefault().registerObserver(this);
}
```
```
protected void onDestroy() {
    super.onDestroy();
	// 反注册
    NetworkManager.getDefault().unRegisterObserver(this);
	// 反注册全部，建议在项目的 MainActivity 方法里调用
    NetworkManager.getDefault().unRegisterAllObserver();
}
```

##### 第四步：编写带有注解的方法来接收监听结果

```
@Network(netType = NetType.AUTO)
public void network(NetType netType) {
    tvState.setText(netType.name());
    switch (netType) {
        case WIFI:
            Log.e(TAG, "WIFI");
            break;
        case CMNET:
        case CMWAP:
            Log.e(TAG, "有网络");
            break;
        case NONE:
            Log.e(TAG, "没有网络");
            break;
    }
}
```
