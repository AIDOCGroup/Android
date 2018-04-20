package me.aidoc.client.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import cn.miao.demo.adapter.GvDeviceTypeAdapter;
import cn.miao.demo.utils.MyToast;
import cn.miao.demo.utils.NetUtil;
import cn.miao.lib.MiaoApplication;
import cn.miao.lib.listeners.MiaoDeviceTypeListener;
import cn.miao.lib.model.DeviceTypeBean;

public class DeviceTypeListActivity extends AppCompatActivity implements View.OnClickListener{

    protected final String TAG = getClass().getSimpleName();
    private ArrayList<DeviceTypeBean> deviceTypes;
    private ImageView back;
    private GridView gv_device_type;
    private GvDeviceTypeAdapter adapter;
    private ProgressDialog dialog;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e(TAG, "handleMessage: " + msg.toString());
            switch (msg.what) {
                case 0:
                    MyToast.showToast(DeviceTypeListActivity.this, String.valueOf(msg.obj));
                    break;
                case 1:
                    adapter = new GvDeviceTypeAdapter(deviceTypes,DeviceTypeListActivity.this);
                    if( gv_device_type != null) {
                        gv_device_type.setAdapter(adapter);
                    }
                    break;
                case 2:
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_list);
        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        gv_device_type = (GridView) findViewById(R.id.gv_device_type);
        gv_device_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(DeviceTypeListActivity.this, DeviceListActivity.class);
                in.putExtra("type_id", deviceTypes.get(position).getId());
                startActivity(in);
            }
        });
        dialog = new ProgressDialog(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDeviceTypeList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }

    private void getDeviceTypeList() {
        if(!NetUtil.chackNetStatus(DeviceTypeListActivity.this)){
            MyToast.showToast(DeviceTypeListActivity.this,"网络连接异常，请稍后再试");
            return;
        }
        if(MiaoApplication.getMiaoHealthManager()==null)return;
        dialog.show();
        MiaoApplication.getMiaoHealthManager().fetchDeviceTypeList(new MiaoDeviceTypeListener() {
            @Override
            public void onDeviceTyapeResponse(ArrayList<DeviceTypeBean> device_types) {
                dialog.dismiss();
                if (device_types != null)
                    deviceTypes = device_types;
                sendMessage(1, "");
            }

            @Override
            public void onError(int code, String msg) {
                dialog.dismiss();
                sendMessage(0, "设备类型列表获取失败 code：" + code + " msg:" + msg);
            }
        });
    }

    private void sendMessage(int what, String msg) {
        Message message = new Message();
        message.what = what;
        message.obj = msg;
        handler.sendMessage(message);
    }
}
