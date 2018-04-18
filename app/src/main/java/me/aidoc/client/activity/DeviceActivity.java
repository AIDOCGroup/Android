package me.aidoc.client.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.icaretech.band.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cn.funtalk.miao.lib.fresco.FrescoManager;
import cn.funtalk.miao.lib.fresco.MSmartCustomView;
import cn.miao.demo.utils.MyToast;
import cn.miao.demo.utils.NetUtil;
import cn.miao.demo.widget.CheckWifiDialog;
import cn.miao.demo.widget.DatePickerDialog;
import cn.miao.demo.widget.DeviceSearchDialog;
import cn.miao.demo.widget.StringPickerDialog;
import cn.miao.demo.widget.UnbindDeviceDialog;
import cn.miao.demo.widget.UserInfoDialog;
import cn.miao.demo.widget.WifiPasDialog;
import cn.miao.lib.MiaoApplication;
import cn.miao.lib.enums.DataTypeEnum;
import cn.miao.lib.listeners.MiaoBindListener;
import cn.miao.lib.listeners.MiaoCheckBindListener;
import cn.miao.lib.listeners.MiaoConnectBleListener;
import cn.miao.lib.listeners.MiaoQueryApiDataListener;
import cn.miao.lib.listeners.MiaoScanBleListener;
import cn.miao.lib.listeners.MiaoUnBindListener;
import cn.miao.lib.listeners.ScanDeviceNoListener;
import cn.miao.lib.model.BindDeviceBean;
import cn.miao.lib.model.BloodGlucoseBean;
import cn.miao.lib.model.BloodPressureBean;
import cn.miao.lib.model.DataBean;
import cn.miao.lib.model.DeviceBean;
import cn.miao.lib.model.FunctionInfoBean;
import cn.miao.lib.model.HeartBean;
import cn.miao.lib.model.SleepBean;
import cn.miao.lib.model.SlimmingBean;
import cn.miao.lib.model.Spo2Bean;
import cn.miao.lib.model.SportBean;
import cn.miao.lib.model.TemperatureBean;

public class DeviceActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener,RadioGroup.OnCheckedChangeListener {
    protected final String TAG = "TianyiDebug";
    protected TextView tvDeviceName;
    protected TextView tvDeviceDesc;
    protected Button btnBind;
    protected Button btn_get_last_data,btn_get_history_data;
    protected TextView tvLog;
    protected TextView btnUnbind;
    private UnbindDeviceDialog unbindDeviceDialog;
    protected Button check_connect;
    protected Button btnDisBle;
    private DeviceSearchDialog deviceSearchDialog;
    private UserInfoDialog userInfoDialog;
    private WifiPasDialog wifiPasDialog;
    private CheckWifiDialog checkWifiDialog;
    private int userSex = 0,userHeight = 170, userWeight= 60;
    private String userBirthday = "1995-01-01";
    private ArrayList<String> heightData = new ArrayList<>();
    private ArrayList<String> weightData = new ArrayList<>();
    /**设备列表*/
    private ArrayList<HashMap<String, String>> arraylist;
    /**wifi列表*/
    private List<HashMap<String, String>> wifilist;
    private ImageView back;
    private MSmartCustomView image;
    protected FrescoManager mFrescoManager;
    private Dialog alertDialog;
    private static final int RESULT_CODE_SCAN_CODE = 1001;
    protected String log = "";
    protected String deviceNo;
    protected String deviceSn;
    protected String deviceId;
    protected int isbind = 1;
    protected int linkType = 1;
    List<FunctionInfoBean> function_info;
    protected int plat = 1;
    protected final int AUTH2RESULT = 1002;
    protected boolean isAleardyBind = false;
    protected SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
            Locale.getDefault());
    private ProgressDialog dialog;
    private String apSSid;
    private String apBSSid;


    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            Log.e(TAG, "handleMessage: " + msg.toString());
            switch (msg.what) {
                case 0:
                    Toast.makeText(DeviceActivity.this, String.valueOf(msg.obj), Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    log = String.valueOf(msg.obj) + "\n" + log;
                    tvLog.setText(log);
                    break;
                case 2:
                    Intent intent = new Intent(DeviceActivity.this, CaptureActivity.class);
                    intent.putExtra("device_sn", String.valueOf(msg.obj));
                    startActivityForResult(intent, RESULT_CODE_SCAN_CODE);
                    break;
                case 3:
                    btnBind.setVisibility(View.GONE);
                    btn_get_last_data.setEnabled(true);
                    btn_get_history_data.setEnabled(true);
                    if (linkType == 1){
                        btnDisBle.setVisibility(View.VISIBLE);
                        btnDisBle.setEnabled(true);
                    }
                    break;
                case 4:
                    btnBind.setVisibility(View.VISIBLE);
                    btn_get_last_data.setEnabled(false);
                    btn_get_history_data.setEnabled(false);
                    btnDisBle.setVisibility(View.GONE);
                    btnDisBle.setEnabled(false);
                    break;
                case 5:
                    arraylist = (ArrayList<HashMap<String, String>>) msg.obj;
                    if(deviceSearchDialog != null){
                        deviceSearchDialog.setReslutData(arraylist);
                    }else {
                        return;
                    }
                    if (arraylist.size() <= 0) {
                        MyToast.showToast(DeviceActivity.this, "未扫描到设备");
                        if(deviceSearchDialog != null){
                            deviceSearchDialog.dismiss();
                        }
                    }
                    break;
                case 6:
                    final String device_no = String.valueOf(msg.obj);
                    alertDialog = new AlertDialog.Builder(DeviceActivity.this).
                            setTitle("检查绑定").
                            setMessage("设备已被其他人绑定").setNegativeButton("绑定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.e(TAG, "handleMessage: " + String.valueOf(msg.obj));
                            bindDevice(deviceSn, device_no);
                            alertDialog.dismiss();
                        }
                    }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            alertDialog.dismiss();
                        }
                    }).create();
                    alertDialog.show();
                    break;
                case 7:
                    if(checkWifiDialog == null){
                        checkWifiDialog = new CheckWifiDialog(DeviceActivity.this, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkWifiDialog.dismiss();
                            }
                        }, new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                checkWifiDialog.dismiss();
                                if(wifilist != null && wifilist.size()>position){
                                    HashMap map = wifilist.get(position);
                                    apSSid = (String)map.get("apSSid");
                                    apBSSid = (String)map.get("apBSSid");
                                    Log.e(TAG, "apSSid =====: " + apSSid+"apBSSid =====: " + apBSSid);
                                    if(wifiPasDialog == null){
                                        wifiPasDialog = new WifiPasDialog(DeviceActivity.this, DeviceActivity.this);
                                    }
                                    wifiPasDialog.show();
                                }
                            }
                        });
                    }
                    checkWifiDialog.show();
                    wifilist = MiaoApplication.getMiaoHealthManager().getWifiList();
                    if(checkWifiDialog != null){
                        checkWifiDialog.setReslutData((ArrayList<HashMap<String,String>>)wifilist);
                    }else {
                        return;
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_bind_device);
        initView();
        initData();
    }

    private void initView() {
        tvDeviceName = (TextView) findViewById(R.id.tv_device_name);
        tvDeviceDesc = (TextView) findViewById(R.id.tv_device_desc);
        btnBind = (Button) findViewById(R.id.btn_bind);
        btnBind.setOnClickListener(DeviceActivity.this);
        btn_get_last_data = (Button) findViewById(R.id.btn_get_last_data);
        btn_get_last_data.setOnClickListener(DeviceActivity.this);
        btn_get_history_data = (Button) findViewById(R.id.btn_get_history_data);
        btn_get_history_data.setOnClickListener(DeviceActivity.this);
        tvLog = (TextView) findViewById(R.id.tv_log);
        btnUnbind = (TextView) findViewById(R.id.btn_unbind);
        btnUnbind.setOnClickListener(DeviceActivity.this);
        btnDisBle = (Button) findViewById(R.id.btn_dis_ble);
        btnDisBle.setOnClickListener(DeviceActivity.this);
        check_connect = (Button) findViewById(R.id.check_connect);
        check_connect.setOnClickListener(DeviceActivity.this);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        dialog = new ProgressDialog(this);
        image = (MSmartCustomView) findViewById(R.id.image);
        mFrescoManager = FrescoManager.getInstance(getApplicationContext());
        // 设置失败时显示的图片
        mFrescoManager.setFailureImageID(R.drawable.miaopuls);
        // 设置占位图
        mFrescoManager.setPlaceholderImageID(R.drawable.miaopuls);
    }

    private void initData() {

        Intent in = getIntent();
        in.setExtrasClassLoader(MiaoApplication.getMiaoHealthManager().getClass().getClassLoader());
        String logo_url = "";
        if (in.hasExtra("deviceBean")) {
            isAleardyBind = false;
            DeviceBean deviceBean = in.getParcelableExtra("deviceBean");
            Log.e(TAG, "deviceBean type id =====: " + deviceBean.getType_id());
            deviceId = deviceBean.getDevcieId();
            deviceSn = deviceBean.getDevice_sn();
            linkType = deviceBean.getLink_type();
            isbind = deviceBean.getIsbind();
            tvDeviceName.setText(deviceBean.getDevice_name());
            int link_type = deviceBean.getLink_type();
            if(link_type == 1){
                tvDeviceDesc.setText("蓝牙设备");
            }else if(link_type == 2){
                tvDeviceDesc.setText("API设备");
            }else if(link_type == 3){
                tvDeviceDesc.setText("二维码设备");
            }
            function_info = deviceBean.getFunction_info();
            logo_url = deviceBean.getLogo();
            sendMessage(4, "");
        } else {
            isAleardyBind = true;
            BindDeviceBean bindDeviceBean = in.getParcelableExtra("bindDeviceBean");
            deviceId = bindDeviceBean.getDevcieId();
            deviceSn = bindDeviceBean.getDevice_sn();
            deviceNo = bindDeviceBean.getDevice_no();
            linkType = bindDeviceBean.getLink_type();
            isbind = 1;
            plat = bindDeviceBean.getPlat();
            tvDeviceName.setText(bindDeviceBean.getDevice_name());
            int link_type = bindDeviceBean.getLink_type();
            if(link_type == 1){
                tvDeviceDesc.setText("蓝牙设备");
            }else if(link_type == 2){
                tvDeviceDesc.setText("API设备");
            }else if(link_type == 3){
                tvDeviceDesc.setText("二维码设备");
            }
            function_info = bindDeviceBean.getFunction_info();
            logo_url = bindDeviceBean.getLogo();
            sendMessage(3, "");
        }

        if(!TextUtils.isEmpty(logo_url)){
            image.setImageURI(logo_url);
        }
        Log.e(TAG, "initData: " + linkType + " " + deviceNo);

        for (int i = 1; i <= 250; i++) {
            heightData.add(i + "");
            weightData.add(i + "");
        }

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_bind) {
            switch (isbind) {
                case 1://支持绑定
                    switch (linkType) {
                        case 1: //蓝牙链接
                            deviceSearchDialog = new DeviceSearchDialog(DeviceActivity.this,this,this);
                            deviceSearchDialog.show();
                            scanBLEDevice(deviceId, deviceSn);
                            break;
                        case 2: //扫描二维码
                            sendMessage(1, "扫描二维码");
                            sendMessage(2, deviceSn);
                            break;
                        case 3:// 授权
                            sendMessage(1, "开始授权");
                            startAuth2Activity(deviceSn);
                            break;
                        case 4:// 通过wifi连接的设备
                            sendMessage(1, "开始扫描wifi");
                            sendMessage(7, null);
                            break;
                    }
                    break;
                case 2: //设备不支持绑定
                    sendMessage(1, "设备不支持绑定");
                    break;
                case 3: //设备即将上线
                    sendMessage(1, "设备即将上线");
                    break;
                case 4:  //设备下线
                    sendMessage(1, "设备下线");
                    break;
            }
        } else if (view.getId() == R.id.btn_get_last_data) {
            sendMessage(1, "获取当前数据");
            getDeviceData(deviceId, deviceSn, deviceNo);
        } else if (view.getId() == R.id.btn_get_history_data) {
            clearLog();
            sendMessage(1, "获取历史数据");
            showDataDialog(2,deviceSn, deviceNo);
//            getDeviceData(deviceId, deviceSn, deviceNo);
        }else if (view.getId() == R.id.btn_unbind) {
            if(unbindDeviceDialog == null){
                unbindDeviceDialog = new UnbindDeviceDialog(DeviceActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(view.getId() == R.id.button_sure){
                            if(unbindDeviceDialog != null){
                                unbindDeviceDialog.dismiss();
                            }
                            unBindDevice(deviceSn, deviceNo);
                        }else if(view.getId() == R.id.button_cancel){
                            if(unbindDeviceDialog != null){
                                unbindDeviceDialog.dismiss();
                            }
                        }
                    }
                });
            }
            unbindDeviceDialog.show();
            unbindDeviceDialog.setTitleType(2);
        } else if (view.getId() == R.id.btn_dis_ble) {
            if (MiaoApplication.getMiaoHealthManager() == null) return;
            MiaoApplication.getMiaoHealthManager().disConnectAll();
        } else if (view.getId() == R.id.check_connect) {
            if (MiaoApplication.getMiaoHealthManager() == null) return;
            checkDeviceConnectStatus(deviceId);
        } else if (view.getId() == R.id.back) {
            finish();
        } else if (view.getId() == R.id.button_cancel) {
            if(deviceSearchDialog != null){
                deviceSearchDialog.dismiss();
                deviceSearchDialog = null;
                MiaoApplication.getMiaoHealthManager().stopScanBLEDevice();
            }
        }else if (view.getId() == R.id.button_sure) {
            HashMap params = new HashMap();
            try {
                params.put("sex",userSex);
                params.put("birthday",userBirthday);
                params.put("height",userHeight+1);
                params.put("weight",userWeight+1);
            }catch (Exception e){
                e.printStackTrace();
            }
            getBleDeviceData(deviceId, deviceSn, deviceNo,params);
            if(userInfoDialog != null){
                userInfoDialog.dismiss();
            }
        }else if (view.getId() == R.id.et_dialog_age) {
            DatePickerDialog.Builder builder = new DatePickerDialog.Builder(this);
            DatePickerDialog dialog = builder.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
                @Override
                public void onDateSelected(int[] dates) {
                    userBirthday = dates[0] + "-" + String.format("%02d", dates[1]) + "-" + String.format("%02d", dates[2]);
                    userBirthday = dates[0] + "-" + dates[1] + "-" + dates[2];
                    if(userInfoDialog != null){
                        userInfoDialog.setBirthday(userBirthday);
                    }
                }
            }).create();
            dialog.show();
        }else if (view.getId() == R.id.et_dialog_height) {
            StringPickerDialog.Builder builder = new StringPickerDialog.Builder(this);
            StringPickerDialog dialog = builder.setData(heightData).setSelection(userHeight==0?169:userHeight-1).setTitle("选择身高")
                    .setOnDataSelectedListener(new StringPickerDialog.OnDataSelectedListener() {
                        @Override
                        public void onDataSelected(String itemValue) {

                            for (int i = 0; i <heightData.size() ; i++) {
                                if (itemValue.equals(heightData.get(i))) {
                                    userHeight=i+1;
                                    if(userInfoDialog != null){
                                        Log.e(TAG, "userInfoDialog: " + userHeight);
                                        userInfoDialog.setHeight(userHeight);
                                    }
                                }
                            }
                        }
                    }).create();
            dialog.show();
        }else if (view.getId() == R.id.et_dialog_weight) {
            StringPickerDialog.Builder builder = new StringPickerDialog.Builder(this);
            StringPickerDialog dialog = builder.setData(weightData).setSelection(userWeight==0?60:userWeight-1).setTitle("选择体重")
                    .setOnDataSelectedListener(new StringPickerDialog.OnDataSelectedListener() {
                        @Override
                        public void onDataSelected(String itemValue) {

                            for (int i = 0; i <weightData.size() ; i++) {
                                if (itemValue.equals(weightData.get(i))) {
                                    userWeight = i+1;
                                    if(userInfoDialog != null){
                                        userInfoDialog.setWeitht(userWeight);
                                    }
                                }
                            }
                        }
                    }).create();
            dialog.show();
        }else if (view.getId() == R.id.wifi_sure) {
            String password = "";
            if(wifiPasDialog != null){
                password = wifiPasDialog.getPassWord();
                wifiPasDialog.dismiss();
            }
            MiaoApplication.getMiaoHealthManager().scanDevice_no(apSSid, apBSSid, password,new ScanDeviceNoListener() {
                @Override
                public void onScanResult(String deviceNo) {
                    Log.e(TAG, "onScanResult =====: " + deviceNo);
                    checkDeviceBind(deviceSn, deviceNo);
                }

                @Override
                public void onError(int i, String s) {
                    DeviceActivity.this.sendMessage(1,"未扫描到设备");
                }
            });
        }
    }


    private void sendMessage(int what, Object msg) {
        Message message = new Message();
        message.what = what;
        message.obj = msg;
        handler.sendMessage(message);
    }

    /**
     * 打开授权认证页面
     */
    private void startAuth2Activity(String deviceSn){
        Intent intent = new Intent(DeviceActivity.this,TestWebViewActivity.class);
        intent.putExtra("deviceSn", deviceSn);
        startActivityForResult(intent,AUTH2RESULT);
    }

    private void checkDeviceBind(final String device_sn, final String device_no) {
        sendMessage(1, "开始检测是被是否被绑定...");
        if(!NetUtil.chackNetStatus(DeviceActivity.this)){
            MyToast.showToast(DeviceActivity.this,"网络连接异常，请稍后再试");
            return;
        }
        MiaoApplication.getMiaoHealthManager().checkDevice(device_sn, device_no, new MiaoCheckBindListener() {
            @Override
            public void onCheckBindRespone(int bindState) {
                switch (bindState) {
                    case 1://1-设备未被绑定
                        sendMessage(1, "设备未被绑定");
                        bindDevice(device_sn, device_no);
                        break;
                    case 2://2-设备已被其他人绑定
                        sendMessage(1, "设备已被其他人绑定");
                        sendMessage(6, device_no);

                        break;
                    case 3: //3-设备已被自己绑定
                        sendMessage(1, "设备已被自己绑定");
                        deviceNo = device_no;
                        sendMessage(3,"");
                        break;
                }
                setDivision();
            }

            @Override
            public void onError(int code, String msg) {
                sendMessage(1, "检查绑定失败 code：" + code + " msg:" + msg);
                setDivision();
            }
        });
    }

    private void bindDevice(String device_sn, final String device_no) {
        sendMessage(1, "开始绑定设备...");
        if(!NetUtil.chackNetStatus(DeviceActivity.this)){
            MyToast.showToast(DeviceActivity.this,"网络连接异常，请稍后再试");
            return;
        }
        MiaoApplication.getMiaoHealthManager().bindDevice(device_sn, device_no, new MiaoBindListener() {
            @Override
            public void onBindDeviceSuccess(String device_no) {
                deviceNo = device_no;
                sendMessage(1, "绑定成功 " + device_no);
                setDivision();
                sendMessage(3, "");
            }

            @Override
            public void onError(int code, String msg) {
                sendMessage(1, "绑定失败 code：" + code + " msg:" + msg);
                setDivision();
            }
        });
    }

    private void unBindDevice(String device_sn, String device_no) {
        if (!TextUtils.isEmpty(device_no)) {
            sendMessage(1, "开始解绑设备...");
            MiaoApplication.getMiaoHealthManager().unbindDevice(device_sn, device_no, new MiaoUnBindListener() {
                @Override
                public void onUnBindResponse(int unbindStatus) {
                    if (unbindStatus == 1) {
                        deviceNo = "";
                        sendMessage(0, "设备解除绑定成功");
                        finish();
                    } else
                        sendMessage(1, "设备解除绑定失败");
                    setDivision();
                }

                @Override
                public void onError(int code, String msg) {
                    sendMessage(1, "解除绑定失败 code：" + code + " msg:" + msg);
                    setDivision();
                }
            });
        } else {
            sendMessage(1, "设备未绑定");
            setDivision();
        }
    }

    private void scanBLEDevice(String deviceId, String device_sn) {
        MiaoApplication.getMiaoHealthManager().scanBLEDevice(deviceId, device_sn, 1000 * 10, new MiaoScanBleListener() {
            @Override
            public void onScanbleResponse(ArrayList<HashMap<String, String>> arraylist) {
                sendMessage(5, arraylist);
            }
        });
    }

    /**
     * 获取蓝牙连接状态
     * @param deviceId
     */
    private void checkDeviceConnectStatus(String deviceId) {
        sendMessage(1, "开始检查连接状态...");
        int connect = MiaoApplication.getMiaoHealthManager().checkDeviceConnect(deviceId);
        sendMessage(1, "连接状态..."+connect);
    }


    private void getBleDeviceData(final String deviceId, final String device_sn, final String device_no,HashMap map) {
        clearLog();
        sendMessage(1, "开始连接蓝牙获取数据...");
        setDivision();
        MiaoApplication.getMiaoHealthManager().fetchBLEConnect(deviceId, device_sn, device_no, map, new MiaoConnectBleListener() {
            @Override
            public void onBleStatusChange(int code, String msg) {
                sendMessage(1, "蓝牙状态改变 code：" + code + " msg:" + msg);
                Log.i(TAG, "onBleStatusChange===code=" + code +"    msg==="+msg);
                if(code == 5 || code == 2){
                    setDivision();
                }
            }

            @Override
            public void onBleDeviceMsg(int i, String s) {
                sendMessage(1, i + " : " + s);
            }

            @Override
            public <T extends DataBean> void onBleDataResponse(DataTypeEnum dataTypeEnum, T bleDataBean) {
                Log.i(TAG, "onBleDataResponse===" + dataTypeEnum +"    bleDataBean==="+bleDataBean);
                if (dataTypeEnum == DataTypeEnum.DATA_BLOOD_GLUCOSE) {
                    BloodGlucoseBean boodglucoseBean = (BloodGlucoseBean) bleDataBean;
                    if (boodglucoseBean != null)
                        sendMessage(1, "血糖值： " + boodglucoseBean.getGlucose_value());
                    else
                        sendMessage(1, "获取血糖数据失败");
                } else if (dataTypeEnum == DataTypeEnum.DATA_TEMPERATURE) {
                    TemperatureBean temperatureBean = (TemperatureBean) bleDataBean;
                    if (temperatureBean != null)
                        sendMessage(1, "体温: " + temperatureBean.getTemperature());
                    else
                        sendMessage(1, "获取蓝牙体温数据失败");
                } else if (dataTypeEnum == DataTypeEnum.DATA_SLIMMING) {
                    SlimmingBean slimmingBean = (SlimmingBean) bleDataBean;
                    if (slimmingBean != null) {
                        sendMessage(1, "体重:" + slimmingBean.getWeight() +"kg"
                                +"\n体脂率:" + slimmingBean.getFat_ratio()+"%"
                                + "\n肌肉量:" + slimmingBean.getMuscle()+"kg"
                                + "\n骨重:" + slimmingBean.getBone_mass()+"kg"
                                + "\n基础代谢:" + slimmingBean.getMetabolism()+"kcal"
                                + "\n体水分:" + slimmingBean.getMoisture() + "%"
                                + "\nbmi:" + slimmingBean.getBmi());
                    } else {
                        sendMessage(1, "获取蓝牙瘦身数据失败");
                    }
                } else if (dataTypeEnum == DataTypeEnum.DATA_BLOOD_PRESSURE) {
                    BloodPressureBean blood = (BloodPressureBean) bleDataBean;
                    if (blood != null) {
                        sendMessage(1,"高压:" + blood.getHigh_press()
                                + "\n低压:" + blood.getLow_press()
                                + "\n心率：" + blood.getHeart_rate());
                    } else {
                        sendMessage(1, "获取蓝血压瘦身数据失败");
                    }
                } else if (dataTypeEnum == DataTypeEnum.DATA_SLEEP) {
                    SleepBean sleepBean = (SleepBean) bleDataBean;
                    if (sleepBean != null) {
                        sendMessage(1, "有效睡眠:" + sleepBean.getEffect_duration() + "秒"
                                + "\n总睡眠:" + sleepBean.getDuration() + "秒"
                                + "\n深睡:" + sleepBean.getDeep_time()+ "秒"
                                + "\n浅睡:" + sleepBean.getLight_time() + "秒"
                                + "\n测量时间:" + sleepBean.getDate_time()
                                + "\n开始时间:" + sleepBean.getStart_at()
                                + "\n结束时间:" + sleepBean.getEnd_at()
//                                + "\n开始测量时间:" + sleepBean.getMeasure_time()
                        );
                        //+ "睡眠质量数据:" + sleepBean.getQuality()
                    } else {
                        sendMessage(1, "获取蓝牙睡眠数据失败");
                    }
                } else if (dataTypeEnum == DataTypeEnum.DATA_SPORT) {
                    SportBean sportBean = (SportBean) bleDataBean;
                    if (sportBean != null) {
                        sendMessage(1, "步数:" + sportBean.getSteps()+"步"
                                + "\n卡路里:" + sportBean.getCalories()+"Kcal"
                                + "\n距离里:" + sportBean.getDistance()+"米"
                                + "\n时间:" + sportBean.getDate_time());
                    } else {
                        sendMessage(1, "获取蓝牙运动数据失败");
                    }
                } else if (dataTypeEnum == DataTypeEnum.DATA_HEART) {
                    HeartBean heartBean = (HeartBean) bleDataBean;
                    if (heartBean != null) {
                        sendMessage(1, "心率:" + heartBean.getHeart_rate());
                    } else {
                        sendMessage(1, "获取蓝牙瘦心率数据失败");
                    }
                } else if(dataTypeEnum == DataTypeEnum.DATA_SPO2){
                    Spo2Bean spo2Bean = (Spo2Bean) bleDataBean;
                    if (spo2Bean != null) {
                        sendMessage(1, "血氧:" + spo2Bean.getBlood_oxygen()
                                + "\n心率:" + spo2Bean.getHeart_rate());
                    } else {
                        sendMessage(1, "获取蓝牙血氧数据失败");
                    }
                }
                setDivision();
            }

            @Override
            public void onBleDataErr() {
                sendMessage(1, "获取蓝牙数据失败");
                setDivision();
            }
        });
    }

    private void getDeviceData(final String deviceId, final String device_sn, final String device_no) {

        if (!TextUtils.isEmpty(device_no)) {
            switch (linkType) {
                case 1://1:蓝牙
                    getCurrentData(deviceId,device_sn,device_no);
                    break;
                case 2://2:二维码,
                case 3://3:auth2授权)
                    showDataDialog(3,device_sn, device_no);
                    break;
            }
        } else {
            sendMessage(1, "请绑定后再获取数据");
            setDivision();
        }
    }


    /**
     * 获取数据弹出框
     * @param type
     * @param device_sn
     * @param device_no
     */
    public void showDataDialog(final int type,final String device_sn, final String device_no){
        String[] items = new String[function_info.size()];
        for (int i = 0; i < function_info.size(); i++) {
            items[i] = function_info.get(i).getFunctional_name();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(DeviceActivity.this);
        if(type == 1){
            builder.setTitle("请选择设备");
        }else {
            builder.setTitle("请选择数据类型");
        }
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final int selectId = function_info.get(i).getFunctional_id();
//                sendMessage(1, "获取api数据...");
                DataTypeEnum dataTypeEnum = null;
                switch (selectId) {
                    case 1:
                        dataTypeEnum = DataTypeEnum.DATA_SPORT;
                        break;
                    case 2:
                        dataTypeEnum = DataTypeEnum.DATA_SLEEP;
                        break;
                    case 3:
                        dataTypeEnum = DataTypeEnum.DATA_BLOOD_PRESSURE;
                        break;
                    case 4:
                        dataTypeEnum = DataTypeEnum.DATA_BLOOD_GLUCOSE;
                        break;
                    case 5:
                        dataTypeEnum = DataTypeEnum.DATA_TEMPERATURE;
                        break;
                    case 7:
                        dataTypeEnum = DataTypeEnum.DATA_SLIMMING;
                        break;
                    case 8:
                        dataTypeEnum = DataTypeEnum.DATA_HEART;
                        break;
                    case 9:
                        dataTypeEnum = DataTypeEnum.DATA_SPO2;
                        break;
                }
                long current = 0;
                long sevenDaysAgo = 0;
                if(type == 2 || (type == 1 && isAleardyBind)){
                    current = System.currentTimeMillis();
                    sevenDaysAgo = current -60*60*24*7*1000;
                }
                dialog.show();
                MiaoApplication.getMiaoHealthManager().fetchApiDeviceData(device_sn, device_no, sevenDaysAgo, current,
                        dataTypeEnum, new MiaoQueryApiDataListener() {
                            @Override
                            public <T extends DataBean> void onApiDataResponse(DataTypeEnum dataTypeEnum, ArrayList<T> ApiDataBean) {
                                sendMessage(1, "获取成功");
                                setDivision();
                                dialog.dismiss();
                                if (dataTypeEnum == DataTypeEnum.DATA_BLOOD_GLUCOSE) {
                                    ArrayList<BloodGlucoseBean> bean = (ArrayList<BloodGlucoseBean>) ApiDataBean;
                                    if (bean != null && bean.size() > 0) {
                                        for (int i = 0; i < bean.size(); i++) {
                                            sendMessage(1, "血糖数据:" + bean.get(i).getGlucose_value()
                                                    + "\n测量时间:" + format.format(new Date(bean.get(i).getMeasure_time()))
                                                    + "\n数据源:" + bean.get(i).getData_source()
                                            );
                                            setDivision();
                                        }
                                    } else{
                                        sendMessage(1, "暂无血糖数据");
                                        setDivision();
                                    }
                                } else if (dataTypeEnum == DataTypeEnum.DATA_BLOOD_PRESSURE) {
                                    ArrayList<BloodPressureBean> bean = (ArrayList<BloodPressureBean>) ApiDataBean;
                                    if (bean != null && bean.size() > 0) {
                                        for (int i = 0; i < bean.size(); i++) {
                                            sendMessage(1, "高压:" + bean.get(i).getHigh_press()
                                                    + "\n低压:" + bean.get(i).getLow_press()
                                                    + "\n心率:" + bean.get(i).getHeart_rate()
                                                    + "\n测量时间:" + format.format(new Date(bean.get(i).getMeasure_time()))
                                                    + "\n数据源:" + bean.get(i).getData_source());
                                            setDivision();
                                        }
                                    } else {
                                        sendMessage(1, "暂无血压数据");
                                        setDivision();
                                    }
                                } else if (dataTypeEnum == DataTypeEnum.DATA_SPORT) {
                                    ArrayList<SportBean> bean = (ArrayList<SportBean>) ApiDataBean;
                                    if (bean != null && bean.size() > 0) {
                                        for (int i = 0; i < bean.size(); i++) {
                                            sendMessage(1, "步数:" + bean.get(i).getSteps()+"步"
                                                    + "\n卡路里数据:" + bean.get(i).getCalories()+"cal"
                                                    + "\n距离里数据:" + bean.get(i).getDistance()+"米"
                                                    + "\n时间:" + bean.get(i).getDate_time()
                                                    + "\n数据源:" + bean.get(i).getData_source()
                                            );
                                            setDivision();
                                        }
                                    } else {
                                        sendMessage(1, "暂无运动数据");
                                        setDivision();
                                    }
                                } else if (dataTypeEnum == DataTypeEnum.DATA_SLEEP) {
                                    ArrayList<SleepBean> bean = (ArrayList<SleepBean>) ApiDataBean;
                                    if (bean != null && bean.size() > 0) {
                                        for (int i = 0; i < bean.size(); i++) {
                                            String startTime = TextUtils.isEmpty(bean.get(i).getStart_at())?"0":format.format(new Date(Long.parseLong(bean.get(i).getStart_at())));
                                            String endTime = TextUtils.isEmpty(bean.get(i).getEnd_at())?"0":format.format(new Date(Long.parseLong(bean.get(i).getEnd_at())));
                                            sendMessage(1, "有效睡眠数据:" + bean.get(i).getEffect_duration() + "秒"
                                                    + "\n总睡眠:" + bean.get(i).getDuration() + "秒"
                                                    + "\n深睡:" + bean.get(i).getDeep_time()+ "秒"
                                                    + "\n浅睡:" + bean.get(i).getLight_time() + "秒"
                                                    + "\n时间:" + bean.get(i).getDate_time()
                                                    + "\n开始时间:" + startTime
                                                    + "\n结束时间:" + endTime
                                                    + "\n数据源:" + bean.get(i).getData_source()
                                            );
                                        }
                                    } else {
                                        sendMessage(1, "暂无睡眠数据");
                                        setDivision();
                                    }
                                } else if (dataTypeEnum == DataTypeEnum.DATA_SLIMMING) {
                                    ArrayList<SlimmingBean> bean = (ArrayList<SlimmingBean>) ApiDataBean;
                                    if (bean != null && bean.size() > 0) {
                                        for (int i = 0; i < bean.size(); i++) {
                                            sendMessage(1, "体重数据:" + bean.get(i).getWeight() +"kg"
                                                    +"\n体脂率数据:" + bean.get(i).getFat_ratio()+"% "
                                                    + "\n肌肉量:" + bean.get(i).getMuscle()+"kg "
                                                    + "\n骨重数据:" + bean.get(i).getBone_mass()+"kg"
                                                    + "\n基础代谢数据:" + bean.get(i).getMetabolism()+"kcal"
                                                    + "\n体水分数据:" + bean.get(i).getMoisture() + "%"
                                                    + "\nBMI:" + bean.get(i).getBmi()
                                                    + "\n测量时间:" + format.format(new Date(bean.get(i).getMeasure_time()))
                                                    + "\n数据源:" + bean.get(i).getData_source()
                                            );
                                            setDivision();
                                        }
                                    } else {
                                        sendMessage(1, "暂无瘦身数据");
                                        setDivision();
                                    }
                                } else if (dataTypeEnum == DataTypeEnum.DATA_TEMPERATURE) {
                                    ArrayList<TemperatureBean> bean = (ArrayList<TemperatureBean>) ApiDataBean;
                                    if (bean != null && bean.size() > 0) {
                                        for (int i = 0; i < bean.size(); i++) {
                                            sendMessage(1,"体温数据:" + bean.get(i).getTemperature()
                                                    + "\n测量时间:" + format.format(new Date(bean.get(i).getMeasure_time()))
                                                    + "\n数据源:" + bean.get(i).getData_source()
                                            );
                                            setDivision();
                                        }
                                    } else {
                                        sendMessage(1, "暂无体温数据");
                                        setDivision();
                                    }
                                } else if (dataTypeEnum == DataTypeEnum.DATA_HEART) {
                                    ArrayList<HeartBean> bean = (ArrayList<HeartBean>) ApiDataBean;
                                    if (bean != null && bean.size() > 0) {
                                        for (int i = 0; i < bean.size(); i++) {
                                            sendMessage(1, "心率数据:" + bean.get(i).getHeart_rate()
                                                    + "\n测量时间:" + format.format(new Date(bean.get(i).getMeasure_time()))
                                                    + "\n数据源:" + bean.get(i).getData_source()
                                            );
                                            setDivision();
                                        }
                                    } else {
                                        sendMessage(1, "暂无心率数据");
                                        setDivision();
                                    }
                                }else if(dataTypeEnum == DataTypeEnum.DATA_SPO2){
                                    ArrayList<Spo2Bean> bean = (ArrayList<Spo2Bean>) ApiDataBean;
                                    if (bean != null) {
                                        for (int i = 0; i < bean.size(); i++) {
                                            sendMessage(1,"心率数据:" + bean.get(i).getHeart_rate()
                                                    + "\n血氧数据:" + bean.get(i).getBlood_oxygen()
                                                    + "\n数据源:" + bean.get(i).getData_source());
                                            setDivision();
                                        }
                                    } else {
                                        sendMessage(1, "暂无血氧数据");
                                        setDivision();
                                    }
                                }
                            }

                            @Override
                            public void onError(int code, String msg) {
                                dialog.dismiss();
                                sendMessage(1, "获取数据失败 code：" + code + " msg:" + msg);
                                setDivision();
                            }

                        });
            }
        });
        builder.show();
    }


    public void getCurrentData(final String deviceId, final String device_sn, final String device_no){
        boolean needDialog = false;
        int functional_id = 0;
        //目前手环和体脂称需要弹框输入个人信息
        for (int i = 0; i < function_info.size(); i++) {
            functional_id = function_info.get(i).getFunctional_id();
            if ( functional_id == 7 || functional_id == 1 || functional_id == 9) {
                needDialog = true;
            }

        }
        if (needDialog) {
            //初始化身高数据
            if (StringUtil.isEmpty(userBirthday)) {
                userBirthday = "1995-01-01";
            }
            showUserInfoDialog(functional_id);
        } else {
            getBleDeviceData(deviceId, device_sn, device_no,null);
        }
    }


    public void showUserInfoDialog(int functional_id){
        if(userInfoDialog == null){
            userInfoDialog = new UserInfoDialog(DeviceActivity.this,DeviceActivity.this,DeviceActivity.this);
        }
        userInfoDialog.show();
        userInfoDialog.setFunction(functional_id);
        if(userInfoDialog != null){
            userInfoDialog.setBirthday(userBirthday);
            userInfoDialog.setSex(userSex);
            userInfoDialog.setHeight(userHeight);
            userInfoDialog.setWeitht(userWeight);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: " + requestCode + resultCode);
        if (requestCode == RESULT_CODE_SCAN_CODE && resultCode == 1) {
            String device_sn = data.getStringExtra("device_sn");
            String device_no = data.getStringExtra("device_no");
            sendMessage(1, "二维码扫描成功 " + device_no);
            Log.e(TAG, "onActivityResult: " + device_sn + " " + device_no);
            checkDeviceBind(device_sn, device_no);
        } else if(requestCode == AUTH2RESULT && resultCode == 2) {
            int result = data.getIntExtra("result",0);
            switch (result){
                case 1:
                    sendMessage(1, "绑定成功");
                    setDivision();
                    deviceNo = data.getStringExtra("device_no");
                    sendMessage(3, "");
                    break;
                case -1:
                    sendMessage(1, "绑定失败");
                    setDivision();
                    break;
                case -2:
                    sendMessage(1, "绑定页面出错");
                    setDivision();
                    break;
                default:
                    break;
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MiaoApplication.getMiaoHealthManager().disConnectAll();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(deviceSearchDialog != null){
            deviceSearchDialog.dismiss();
            deviceSearchDialog = null;
        }
        if(arraylist != null && arraylist.size() > position){
            checkDeviceBind(deviceSn, arraylist.get(position).get("mac"));
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_type_0:
                userSex = 0;
                break;
            case R.id.rb_type_1:
                userSex = 1;
                break;
            default:
                break;
        }
    }

    private void setDivision(){
        sendMessage(1, "-------------------------------------------------------------------------");
//        sendMessage(1, "\n");
    }

    private void clearLog(){
        log = "";
    }
}
