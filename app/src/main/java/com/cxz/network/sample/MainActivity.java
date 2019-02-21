package com.cxz.network.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.cxz.network.library.NetworkManager;
import com.cxz.network.library.listener.NetChangeObserver;
import com.cxz.network.library.type.NetType;
import com.cxz.network.library.utils.Constants;

public class MainActivity extends AppCompatActivity implements NetChangeObserver {

    TextView tvState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvState = findViewById(R.id.tv_state);

        NetworkManager.getDefault().init(getApplication());
        NetworkManager.getDefault().setListener(this);

    }

    @Override
    public void onConnect(NetType type) {
        Log.e(Constants.LOG_TAG, "连接了" + type.name());
        tvState.setText("连接了" + type.name());
    }

    @Override
    public void onDisConnect() {
        Log.e(Constants.LOG_TAG, "没有网络");
        tvState.setText("没有网络");
    }
}
