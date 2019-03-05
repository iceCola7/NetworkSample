package com.cxz.network.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.cxz.network.library.NetworkManager;
import com.cxz.network.library.annotation.Network;
import com.cxz.network.library.type.NetType;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    TextView tvState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvState = findViewById(R.id.tv_state);

        NetworkManager.getDefault().registerObserver(this);

    }

    @Network(netType = NetType.AUTO)
    public void network(NetType netType) {
        tvState.setText(netType.name());
        switch (netType) {
            case WIFI:
                Log.e(TAG, "WIFI");
                break;
            case CMNET:
            case CMWAP:
                // 有网络
                Log.e(TAG, "有网络");
                break;

            case NONE:
                // 没有网络，提示用户跳转到设置
                Log.e(TAG, "没有网络");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkManager.getDefault().unRegisterObserver(this);
        NetworkManager.getDefault().unRegisterAllObserver();
    }
}
