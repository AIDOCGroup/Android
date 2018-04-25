package me.aidoc.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import cn.miao.lib.DeviceBindWebView;
import cn.miao.lib.listeners.WebBindResultNewListener;

/**
 * 测试webView页面
 */
public class TestWebViewActivity extends Activity {

    private DeviceBindWebView mDeviceBindWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.webview_test);
        initView();
    }

    private void initView() {
        String deviceSn = getIntent().getStringExtra("deviceSn");
        mDeviceBindWebView = (DeviceBindWebView) findViewById(R.id.test_webview);
        /*mDeviceBindWebView.setBindResultListener(new WebBindResultListener() {

            @Override
            public void setBindResult(int result) {
                *//** 1成功，-1失败,-2页面出错*//*
//                Toast.makeText(TestWebViewActivity.this, "setBindResult===" + result, Toast.LENGTH_LONG).show();

                Intent intent = new Intent();
                intent.putExtra("result",result);
                setResult(2,intent);
                finish();
            }
        });*/
        mDeviceBindWebView.setBindResultNewListener(new WebBindResultNewListener() {
            @Override
            public void setBindResult(int result, String device_no) {
                /** 1成功，-1失败,-2页面出错*/
//                Toast.makeText(TestWebViewActivity.this, "setBindResult===" + result+"  device_no=="+device_no, Toast.LENGTH_LONG).show();

                Intent intent = new Intent();
                intent.putExtra("result",result);
                intent.putExtra("device_no",device_no);
                setResult(2,intent);
                finish();
            }
        });
        mDeviceBindWebView.setDeviceSn(deviceSn);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDeviceBindWebView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDeviceBindWebView.onPause();
    }

}

