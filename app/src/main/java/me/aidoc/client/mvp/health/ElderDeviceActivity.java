package me.aidoc.client.mvp.health;

import android.app.Dialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.miao.demo.CaptureActivity;
import cn.miao.demo.R;
import cn.miao.lib.MiaoApplication;
import cn.miao.lib.listeners.MiaoBindListener;
import cn.miao.lib.listeners.MiaoElderDataLinstener;
import cn.miao.lib.listeners.MiaoElderLinstener;
import cn.miao.lib.listeners.MiaoUnBindListener;
import cn.miao.lib.model.BindDeviceBean;
import cn.miao.lib.model.DataBean;
import cn.miao.lib.model.DeviceBean;
import cn.miao.lib.model.ElderAlertsListBean;
import cn.miao.lib.model.ElderDailyPaperBean;
import cn.miao.lib.model.ElderPositionsListBean;
import cn.miao.lib.model.ElderWearInfoList;
import cn.miao.lib.model.FunctionInfoBean;

public class ElderDeviceActivity extends AppCompatActivity implements View.OnClickListener {
    protected final String TAG = getClass().getSimpleName();
    protected TextView tvElderDeviceName;
    protected TextView tvElderDeviceDesc;
    protected Button btnElderBind;
    protected Button btnElderUnbind;
    protected Button btnAddContact;
    protected Button btnSelectContact;
    protected Button btnAddFamilyReminder;
    protected Button btnSelectFamilyReminder;
    protected Button btnGetDailyPaper;
    protected Button btnGetWearInfo;
    protected Button btnGetPosition;
    protected Button btnGetAlert;
    protected Button btnEncourageElder;
    protected TextView tvLog;
    private static final int RESULT_CODE_SCAN_CODE = 1001;

    protected String deviceNo;
    protected String deviceSn;
    protected String deviceId;
    protected int isbind = 1;
    protected int linkType = 1;
    protected EditText etName;
    protected EditText etNickName;
    protected EditText etMobile;
    protected LinearLayout llElderFunction;
    protected LinearLayout llElderInfo;
    List<FunctionInfoBean> function_info;
    protected String ACTION = "MIAO.BLE.ALERT";
    protected int plat = 1;
    protected String log = "";
    private Dialog alertDialog;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            Log.e(TAG, "handleMessage: " + msg.toString());
            switch (msg.what) {
                case 0:
                    Toast.makeText(ElderDeviceActivity.this, String.valueOf(msg.obj), Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    log = String.valueOf(msg.obj) + "\n" + log;
                    tvLog.setText(log);
                    break;
                case 2:
                    Intent intent = new Intent(ElderDeviceActivity.this, CaptureActivity.class);
                    intent.putExtra("device_sn", String.valueOf(msg.obj));
                    startActivityForResult(intent, RESULT_CODE_SCAN_CODE);
                    break;
                case 3:
                    btnElderBind.setVisibility(View.GONE);
                    btnElderUnbind.setVisibility(View.VISIBLE);
                    llElderFunction.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    btnElderBind.setVisibility(View.VISIBLE);
                    btnElderUnbind.setVisibility(View.GONE);
                    llElderFunction.setVisibility(View.GONE);
                    break;
                case 6:
                    final String device_no = String.valueOf(msg.obj);
                    alertDialog = new AlertDialog.Builder(ElderDeviceActivity.this).
                            setTitle("检查绑定").
                            setMessage("设备已被其他人绑定").setNegativeButton("绑定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.e(TAG, "handleMessage: " + String.valueOf(msg.obj));
//                            bindDevice(deviceSn, device_no);
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
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_elder_device);
        initView();
        initData();
    }

    public void initData() {
        Intent in = getIntent();
        in.setExtrasClassLoader(MiaoApplication.getMiaoHealthManager().getClass().getClassLoader());
        if (in.hasExtra("deviceBean")) {
            DeviceBean deviceBean = in.getParcelableExtra("deviceBean");
            deviceId = deviceBean.getDevcieId();
            deviceSn = deviceBean.getDevice_sn();
            linkType = deviceBean.getLink_type();
            isbind = deviceBean.getIsbind();
            tvElderDeviceName.setText(deviceBean.getDevice_name());
            tvElderDeviceDesc.setText(deviceBean.getDevice_des());
            function_info = deviceBean.getFunction_info();
            sendMessage(4, "");
        } else {
//        bindDeviceBean
            BindDeviceBean bindDeviceBean = in.getParcelableExtra("bindDeviceBean");
            deviceId = bindDeviceBean.getDevcieId();
            deviceSn = bindDeviceBean.getDevice_sn();
            deviceNo = bindDeviceBean.getDevice_no();
            linkType = bindDeviceBean.getLink_type();
            Log.e(TAG, "initData: " + linkType + " " + deviceNo);
            isbind = 1;
            plat = bindDeviceBean.getPlat();
            tvElderDeviceName.setText(bindDeviceBean.getDevice_name());
            tvElderDeviceDesc.setText("" + plat);
            function_info = bindDeviceBean.getFunction_info();
            sendMessage(3, "");
            llElderInfo.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_elder_bind) {
            sendMessage(1, "开始检查绑定状态");
            switch (isbind) {
                case 1://支持绑定
                    switch (linkType) {
                        case 2: //扫描二维码
                            sendMessage(1, "扫描二维码");
                            sendMessage(2, deviceSn);
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
        } else if (view.getId() == R.id.btn_elder_unbind) {
            unBindDevice(deviceSn, deviceNo);
        } else if (view.getId() == R.id.btn_add_contact) {
            Intent in = new Intent(ElderDeviceActivity.this, ElderContactActivity.class);
            in.putExtra("deviceSn", deviceSn);
            in.putExtra("deviceNo", deviceNo);
            startActivity(in);
        } else if (view.getId() == R.id.btn_select_contact) {
            Intent in = new Intent(ElderDeviceActivity.this, ElderContactsListActivity.class);
            in.putExtra("deviceSn", deviceSn);
            in.putExtra("deviceNo", deviceNo);
            startActivity(in);

        } else if (view.getId() == R.id.btn_add_family_reminder) {
            Intent in = new Intent(ElderDeviceActivity.this, FamilyReminderActivity.class);
            in.putExtra("deviceSn", deviceSn);
            in.putExtra("deviceNo", deviceNo);
            startActivity(in);
        } else if (view.getId() == R.id.btn_select_family_reminder) {
            Intent in = new Intent(ElderDeviceActivity.this, FamilyReminderListActivity.class);
            in.putExtra("deviceSn", deviceSn);
            in.putExtra("deviceNo", deviceNo);
            startActivity(in);
        } else if (view.getId() == R.id.btn_get_daily_paper) {
            MiaoApplication.getMiaoHealthElderManager().getDailyPaper(deviceSn, deviceNo, "", new MiaoElderDataLinstener() {
                @Override
                public void onElderDataResponse(int type, DataBean elderDataBean) {
                    if (type == MiaoElderDataLinstener.DAILY_PAPER_DATA) {
                        ElderDailyPaperBean elderDailyPaperBean = (ElderDailyPaperBean) elderDataBean;
                        if (elderDailyPaperBean != null)
                            sendMessage(1, "日报数据：" + elderDailyPaperBean.toString());
                        else sendMessage(1, "暂无数据");

                    }
                }

                @Override
                public void onError(int typeid, int code, String msg) {
                    sendMessage(1, "获取日报失败" + code + " " + msg);
                }
            });

        } else if (view.getId() == R.id.btn_get_wear_info) {
            MiaoApplication.getMiaoHealthElderManager().getWearInfo(deviceSn, deviceNo, "", new MiaoElderDataLinstener() {
                @Override
                public void onElderDataResponse(int type, DataBean elderDataBean) {
                    if (type == MiaoElderDataLinstener.WEAR_INFO_DATA) {
                        ElderWearInfoList elderWearInfoList = (ElderWearInfoList) elderDataBean;
                        if (elderWearInfoList != null&&elderWearInfoList.getData().size()>0) {
                            for (int i = 0; i < elderWearInfoList.getData().size(); i++) {
                                sendMessage(1, "佩戴数据：" + elderWearInfoList.getData().get(i).toString());
                            }
                        } else sendMessage(1, "暂无数据");

                    }
                }

                @Override
                public void onError(int typeid, int code, String msg) {
                    sendMessage(1, "获取佩戴数据失败" + code + " " + msg);
                }
            });
        } else if (view.getId() == R.id.btn_get_position) {
            MiaoApplication.getMiaoHealthElderManager().getElderPosition(deviceSn, deviceNo, new MiaoElderDataLinstener() {
                @Override
                public void onElderDataResponse(int type, DataBean elderDataBean) {
                    if (type == MiaoElderLinstener.GET_ELDER_POSITIONS) {
                        ElderPositionsListBean elderPositionsListBean = (ElderPositionsListBean) elderDataBean;
                        if (elderPositionsListBean != null&&elderPositionsListBean.getElderPositionBeanArrayList().size()>0) {
                            for (int i = 0; i < elderPositionsListBean.getElderPositionBeanArrayList().size(); i++) {
                                sendMessage(1, "老人位置数据：" + elderPositionsListBean.getElderPositionBeanArrayList().get(i).toString());
                            }
                        } else sendMessage(1, "暂无数据");

                    }
                }

                @Override
                public void onError(int typeid, int code, String msg) {
                    sendMessage(1, "获取位置数据失败" + code + " " + msg);
                }
            });
        } else if (view.getId() == R.id.btn_get_alert) {
            MiaoApplication.getMiaoHealthElderManager().getElderAlert(deviceSn, deviceNo, new MiaoElderDataLinstener() {
                @Override
                public void onElderDataResponse(int type, DataBean elderDataBean) {
                    if (type == MiaoElderLinstener.GET_ELDER_ALERTS) {
                        ElderAlertsListBean elderAlertsListBean = (ElderAlertsListBean) elderDataBean;
                        if (elderAlertsListBean != null&&elderAlertsListBean.getElderAlertBeanArrayList().size()>0) {
                            for (int i = 0; i < elderAlertsListBean.getElderAlertBeanArrayList().size(); i++) {
                                sendMessage(1, "获取警告数据：" + elderAlertsListBean.getElderAlertBeanArrayList().get(i).toString());
                            }
                        } else sendMessage(1, "暂无数据");

                    }
                }

                @Override
                public void onError(int typeid, int code, String msg) {
                    sendMessage(1, "获取警告数据失败" + code + " " + msg);
                }
            });
        } else if (view.getId() == R.id.btn_EncourageElder) {
            final String[] items = new String[3];
            //1：您今天的表现真棒，您的孩子为您热情鼓掌、2：您的孩子为您献上一枝花、3：您的孩子给您一个拥抱
            items[0] = "1：您今天的表现真棒，您的孩子为您热情鼓掌";
            items[1] = "2：您的孩子为您献上一枝花";
            items[2] = "3：您的孩子给您一个拥抱";
            final AlertDialog.Builder builder = new AlertDialog.Builder(ElderDeviceActivity.this);
            builder.setTitle("请选择鼓励内容");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    sendMessage(1, "鼓励老人 选择：" + items[i]);
                    MiaoApplication.getMiaoHealthElderManager().EncourageElder(deviceSn, deviceNo, i + 1, new MiaoElderLinstener() {
                        @Override
                        public void onStatus(int typeid, int state) {
                            sendMessage(1, "鼓励发送成功");
                        }

                        @Override
                        public void onError(int typeid, int code, String msg) {
                            sendMessage(1, "鼓励发送失败");
                        }
                    });
                }
            });
            builder.show();
        }
    }

    private void initView() {
        tvElderDeviceName = (TextView) findViewById(R.id.tv_elder_device_name);
        tvElderDeviceDesc = (TextView) findViewById(R.id.tv_elder_device_desc);
        btnElderBind = (Button) findViewById(R.id.btn_elder_bind);
        btnElderBind.setOnClickListener(ElderDeviceActivity.this);
        btnElderUnbind = (Button) findViewById(R.id.btn_elder_unbind);
        btnElderUnbind.setOnClickListener(ElderDeviceActivity.this);
        btnAddContact = (Button) findViewById(R.id.btn_add_contact);
        btnAddContact.setOnClickListener(ElderDeviceActivity.this);
        btnSelectContact = (Button) findViewById(R.id.btn_select_contact);
        btnSelectContact.setOnClickListener(ElderDeviceActivity.this);
        btnAddFamilyReminder = (Button) findViewById(R.id.btn_add_family_reminder);
        btnAddFamilyReminder.setOnClickListener(ElderDeviceActivity.this);
        btnSelectFamilyReminder = (Button) findViewById(R.id.btn_select_family_reminder);
        btnSelectFamilyReminder.setOnClickListener(ElderDeviceActivity.this);
        btnGetDailyPaper = (Button) findViewById(R.id.btn_get_daily_paper);
        btnGetDailyPaper.setOnClickListener(ElderDeviceActivity.this);
        btnGetWearInfo = (Button) findViewById(R.id.btn_get_wear_info);
        btnGetWearInfo.setOnClickListener(ElderDeviceActivity.this);
        btnGetPosition = (Button) findViewById(R.id.btn_get_position);
        btnGetPosition.setOnClickListener(ElderDeviceActivity.this);
        btnGetAlert = (Button) findViewById(R.id.btn_get_alert);
        btnGetAlert.setOnClickListener(ElderDeviceActivity.this);
        btnEncourageElder = (Button) findViewById(R.id.btn_EncourageElder);
        btnEncourageElder.setOnClickListener(ElderDeviceActivity.this);
        tvLog = (TextView) findViewById(R.id.tv_log);
        etName = (EditText) findViewById(R.id.et_name);
        etNickName = (EditText) findViewById(R.id.et_nick_name);
        etMobile = (EditText) findViewById(R.id.et_mobile);
        llElderFunction = (LinearLayout) findViewById(R.id.ll_elder_function);
        llElderInfo = (LinearLayout) findViewById(R.id.ll_elder_info);
    }

    private void sendMessage(int what, Object msg) {
        Message message = new Message();
        message.what = what;
        message.obj = msg;
        handler.sendMessage(message);
    }

    private void bindDevice(String device_sn, final String mDevice_no) {
        sendMessage(1, "开始绑定设备...");
        MiaoApplication.getMiaoHealthElderManager().bindDevice(device_sn, mDevice_no,
                etName.getText().toString(), etNickName.getText().toString(),
                etMobile.getText().toString(), new MiaoBindListener() {
                    @Override
                    public void onBindDeviceSuccess(String device_no) {
                        deviceNo = mDevice_no;
                        sendMessage(1, "绑定成功 " + device_no);
                        sendMessage(3, "");
                    }

                    @Override
                    public void onError(int code, String msg) {
                        sendMessage(1, "绑定失败 code：" + code + " msg:" + msg);
                    }
                });
    }

    private void unBindDevice(String device_sn, String device_no) {
        if (!TextUtils.isEmpty(device_no)) {
            sendMessage(1, "开始解绑设备...");
            MiaoApplication.getMiaoHealthElderManager().unbindDevice(device_sn, device_no, new MiaoUnBindListener() {
                @Override
                public void onUnBindResponse(int unbindStatus) {
                    if (unbindStatus == 1) {
                        deviceNo = "";
                        sendMessage(0, "设备解除绑定成功");
                        finish();
                    } else
                        sendMessage(1, "设备解除绑定失败");
                }

                @Override
                public void onError(int code, String msg) {
                    sendMessage(1, "解除绑定失败 code：" + code + " msg:" + msg);
                }
            });
        } else {
            sendMessage(1, "设备未绑定");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult: " + requestCode + resultCode);
        if (requestCode == RESULT_CODE_SCAN_CODE && resultCode == 1) {
            String device_sn = data.getStringExtra("device_sn");
             deviceNo = data.getStringExtra("device_no");
            sendMessage(1, "二维码扫描成功 " + deviceNo);
            Log.e(TAG, "onActivityResult: " + device_sn + " " + deviceNo);
//            checkDeviceBind(device_sn, device_no);
            bindDevice(device_sn, deviceNo);
        }

    }
}
