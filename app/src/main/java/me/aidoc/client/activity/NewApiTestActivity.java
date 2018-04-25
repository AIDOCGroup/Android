package me.aidoc.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cn.funtalk.miao.lib.fresco.FrescoManager;
import cn.funtalk.miao.lib.fresco.MSmartCustomView;
import cn.miao.lib.MiaoApplication;
import cn.miao.lib.listeners.MiaoDeviceDetailListener;
import cn.miao.lib.model.BindGuideBean;
import cn.miao.lib.model.DeviceBean;
import cn.miao.lib.model.DeviceDetailBean;
import cn.miao.lib.model.FunctionInfoBean;
import cn.miao.lib.model.MeasureGuideBean;
import cn.miao.lib.model.ProcDetailBean;

/**
 * 网页绑定设备
 *
 * @created 2016_3-25
 * @author shule
 */
public class NewApiTestActivity extends Activity implements View.OnClickListener {
    protected final String TAG = getClass().getSimpleName();
    private Button next_button;
    private DeviceBean deviceBean = null;
    private String buy_url = null;
    private FrescoManager mFrescoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_aip_test_view);
        initView();
        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    public void initView() {
        Intent in = getIntent();
        in.setExtrasClassLoader(MiaoApplication.getMiaoHealthManager().getClass().getClassLoader());
        if (in.hasExtra("deviceBean")) {
            deviceBean = in.getParcelableExtra("deviceBean");
        }else {
            Log.e(TAG, "deviceBean: null" );
        }

        next_button = (Button) findViewById(R.id.next_button);
        next_button.setOnClickListener(this);

        mFrescoManager = FrescoManager.getInstance(getApplicationContext());
        // 设置失败时显示的图片
        mFrescoManager.setFailureImageID(R.drawable.miaopuls);
        // 设置占位图
        mFrescoManager.setPlaceholderImageID(R.drawable.miaopuls);
    }

    public void getData(){
        //测试详情
        MiaoApplication.getMiaoHealthManager().fetchDeviceDetail(deviceBean.getDevice_sn(), new MiaoDeviceDetailListener() {

            @Override
            public void onDeviceDelResponse(DeviceDetailBean deviceDetailBean) {
                final DeviceDetailBean deviceDetailBean1 = deviceDetailBean;
                //更新主线程UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setView(deviceDetailBean1);
                    }
                });

                Log.e(TAG, "onDeviceDelResponse: " + deviceDetailBean + " " + deviceDetailBean.getDevice_name());
                Log.e(TAG, "onDeviceDelResponse: " + deviceDetailBean.getDevice_name_en());
                Log.e(TAG, "onDeviceDelResponse: " + deviceDetailBean.getBuy_url());
                Log.e(TAG, "onDeviceDelResponse: " + deviceDetailBean.getConnect_name());

                ArrayList<FunctionInfoBean> functionInfoBeanList =  deviceDetailBean.getFunction_info();
                if(functionInfoBeanList != null){
                    for (int i = 0;i<functionInfoBeanList.size();i++){
                        FunctionInfoBean bean = functionInfoBeanList.get(i);
                        Log.e(TAG, "onDeviceDelResponse: " + bean.getFunctional_name());
                        Log.e(TAG, "onDeviceDelResponse: " + bean.getFunctional_id());
                    }
                }

                ArrayList<BindGuideBean> bindGuideBeanList =  deviceDetailBean.getBindGuide_info();
                if(bindGuideBeanList != null){
                    for (int i = 0;i<bindGuideBeanList.size();i++){
                        BindGuideBean bean = bindGuideBeanList.get(i);
                        Log.e(TAG, "onDeviceDelResponse: " + bean.getDesc());
                        Log.e(TAG, "onDeviceDelResponse: " + bean.getDesc_en());
                        Log.e(TAG, "onDeviceDelResponse: " + bean.getImg());
                        Log.e(TAG, "onDeviceDelResponse: " + bean.getOrder());
                    }
                }

                ArrayList<MeasureGuideBean> measureGuideBeanList =  deviceDetailBean.getMeasureGuide_info();
                if(measureGuideBeanList != null){
                    for (int i = 0;i<measureGuideBeanList.size();i++){
                        MeasureGuideBean bean = measureGuideBeanList.get(i);
                        Log.e(TAG, "onDeviceDelResponse: " + bean.getDesc());
                        Log.e(TAG, "onDeviceDelResponse: " + bean.getDesc_en());
                        Log.e(TAG, "onDeviceDelResponse: " + bean.getImg());
                        Log.e(TAG, "onDeviceDelResponse: " + bean.getOrder());
                    }
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    public void setView(DeviceDetailBean deviceDetailBean){
        ((TextView)findViewById(R.id.device_name)).setText(deviceDetailBean.getDevice_name());
        ((TextView)findViewById(R.id.device_name_en)).setText(deviceDetailBean.getDevice_name_en());
        ((TextView)findViewById(R.id.device_desc_en)).setText(deviceDetailBean.getDevice_des_en());
        ((TextView)findViewById(R.id.device_desc)).setText(deviceDetailBean.getDevice_des());
        ((TextView)findViewById(R.id.buy_url)).setText(deviceDetailBean.getBuy_url());
        ArrayList<ProcDetailBean> procDetailBeanList = deviceDetailBean.getProcDetail_info();
        if(procDetailBeanList != null){
            for (int i = 0;i<procDetailBeanList.size();i++){
                ProcDetailBean procDetailBean = procDetailBeanList.get(i);
                switch (i){
                    case 0:
                        ((TextView)findViewById(R.id.order1)).setText(procDetailBean.getOrder()+"");
                        ((TextView)findViewById(R.id.text1)).setText(procDetailBean.getDesc()+"\n"+"\n"+ procDetailBean.getDesc_en());
                        String url1 = procDetailBean.getImg();
                        if(!TextUtils.isEmpty(url1)){
                            ((MSmartCustomView) findViewById(R.id.src1)).setImageURI(url1);
                        }
                        break;
                    case 1:
                        ((TextView)findViewById(R.id.order2)).setText(procDetailBean.getOrder()+"");
                        ((TextView)findViewById(R.id.text2)).setText(procDetailBean.getDesc()+"\n"+"\n"+ procDetailBean.getDesc_en());
                        String url2 = procDetailBean.getImg();
                        if(!TextUtils.isEmpty(url2)){
                            ((MSmartCustomView) findViewById(R.id.src2)).setImageURI(url2);
                        }
                        break;
                    case 2:
                        ((TextView)findViewById(R.id.order3)).setText(procDetailBean.getOrder()+"");
                        ((TextView)findViewById(R.id.text3)).setText(procDetailBean.getDesc()+"\n"+"\n"+ procDetailBean.getDesc_en());
                        String url3 = procDetailBean.getImg();
                        if(!TextUtils.isEmpty(url3)){
                            ((MSmartCustomView) findViewById(R.id.src3)).setImageURI(url3);
                        }
                        break;
                }
            }
        }


        ArrayList<BindGuideBean> bindGuideBeanList = deviceDetailBean.getBindGuide_info();
        if(bindGuideBeanList != null){
            for (int i = 0;i<bindGuideBeanList.size();i++){
                BindGuideBean bindGuideBean = bindGuideBeanList.get(i);
                switch (i){
                    case 0:
                        ((TextView)findViewById(R.id.order4)).setText(bindGuideBean.getOrder()+"");
                        ((TextView)findViewById(R.id.text4)).setText(bindGuideBean.getDesc()+"\n"+"\n"+ bindGuideBean.getDesc_en());
                        String url4 = bindGuideBean.getImg();
                        if(!TextUtils.isEmpty(url4)){
                            ((MSmartCustomView) findViewById(R.id.src4)).setImageURI(url4);
                        }
                        break;
                    case 1:
                        ((TextView)findViewById(R.id.order5)).setText(bindGuideBean.getOrder()+"");
                        ((TextView)findViewById(R.id.text5)).setText(bindGuideBean.getDesc()+"\n"+"\n"+ bindGuideBean.getDesc_en());
                        String url5 = bindGuideBean.getImg();
                        if(!TextUtils.isEmpty(url5)){
                            ((MSmartCustomView) findViewById(R.id.src5)).setImageURI(url5);
                        }
                        break;
                    case 2:
                        ((TextView)findViewById(R.id.order6)).setText(bindGuideBean.getOrder()+"");
                        ((TextView)findViewById(R.id.text6)).setText(bindGuideBean.getDesc()+"\n"+"\n"+ bindGuideBean.getDesc_en());
                        String url6 = bindGuideBean.getImg();
                        if(!TextUtils.isEmpty(url6)){
                            ((MSmartCustomView) findViewById(R.id.src6)).setImageURI(url6);
                        }
                        break;
                }
            }
        }

        ArrayList<MeasureGuideBean> measureGuideBeanList = deviceDetailBean.getMeasureGuide_info();
        if(measureGuideBeanList != null){
            for (int i = 0;i<measureGuideBeanList.size();i++){
                MeasureGuideBean measureGuideBean = measureGuideBeanList.get(i);
                switch (i){
                    case 0:
                        ((TextView)findViewById(R.id.order7)).setText(measureGuideBean.getOrder()+"");
                        ((TextView)findViewById(R.id.text7)).setText(measureGuideBean.getDesc()+"\n"+"\n"+ measureGuideBean.getDesc_en());
                        String url7 = measureGuideBean.getImg();
                        if(!TextUtils.isEmpty(url7)){
                            ((MSmartCustomView) findViewById(R.id.src7)).setImageURI(url7);
                        }
                        break;
                    case 1:
                        ((TextView)findViewById(R.id.order8)).setText(measureGuideBean.getOrder()+"");
                        ((TextView)findViewById(R.id.text8)).setText(measureGuideBean.getDesc()+"\n"+"\n"+ measureGuideBean.getDesc_en());
                        String url8 = measureGuideBean.getImg();
                        if(!TextUtils.isEmpty(url8)){
                            ((MSmartCustomView) findViewById(R.id.src8)).setImageURI(url8);
                        }
                        break;
                    case 2:
                        ((TextView)findViewById(R.id.order9)).setText(measureGuideBean.getOrder()+"");
                        ((TextView)findViewById(R.id.text9)).setText(measureGuideBean.getDesc()+"\n"+"\n"+ measureGuideBean.getDesc_en());
                        String url9 = measureGuideBean.getImg();
                        if(!TextUtils.isEmpty(url9)){
                            ((MSmartCustomView) findViewById(R.id.src9)).setImageURI(url9);
                        }
                        break;
                }
            }
        }


        findViewById(R.id.buy_url).setOnClickListener(this);
        buy_url = deviceDetailBean.getBuy_url();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.next_button){
            if(deviceBean == null) return;
            if(!deviceBean.getDevice_sn().equals("2016011301")){
                Intent in = new Intent(NewApiTestActivity.this, DeviceActivity.class);
                in.putExtra("deviceBean", deviceBean);
                startActivity(in);
            }
        }else if(v.getId() == R.id.buy_url){
            Intent in = new Intent(NewApiTestActivity.this, DeviceBuyActivity.class);
            in.putExtra("buy_url", buy_url);
            startActivity(in);
        }
    }


    final class MyWebViewClient extends WebViewClient {

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //TLog.e("test", "url Override =====" + url + "  time: " + sDateFormat.format(new java.util.Date()));
            view.loadUrl(url);
            return true;
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

        }

        // 页面出错
        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            Toast.makeText(NewApiTestActivity.this, "页面出错了", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}