package me.aidoc.client.mvp.health;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.miao.demo.R;
import cn.miao.lib.MiaoApplication;
import cn.miao.lib.listeners.MiaoElderLinstener;

public class ElderContactActivity extends AppCompatActivity implements View.OnClickListener {

    protected EditText etAddContactName;
    protected EditText etAddContactMobile;
    protected Button btnAddContactr;
    protected LinearLayout activityElderAddContact;
    private String deviceSn, deviceNo;
    private Handler handler = new Handler(Looper.getMainLooper());

    private boolean isAdd = true;
    private int contactId;
    private String contactName;
    private String contactMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_elder_add_contact);
        initView();
        deviceSn = getIntent().getStringExtra("deviceSn");
        deviceNo = getIntent().getStringExtra("deviceNo");
        if (getIntent().hasExtra("contactId")) {
            isAdd = false;
            contactId = getIntent().getIntExtra("contactId", 0);
            contactName = getIntent().getStringExtra("contactName");
            contactMobile = getIntent().getStringExtra("contactMobile");
            etAddContactName.setText(contactName);
            etAddContactMobile.setText(contactMobile);
            btnAddContactr.setText("修改联系人");
        } else {
            isAdd = true;
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_add_contactr) {
            if (!TextUtils.isEmpty(etAddContactName.getText().toString()) &&
                    !TextUtils.isEmpty(etAddContactMobile.getText().toString())) {
                if (isAdd) {
                    MiaoApplication.getMiaoHealthElderManager().addEdlerContact(deviceSn, deviceNo,
                            etAddContactName.getText().toString(), etAddContactMobile.getText().toString(),
                            new MiaoElderLinstener() {
                                @Override
                                public void onStatus(int typeid, final int state) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (state==1){
                                                Toast.makeText(ElderContactActivity.this, "添加成功！", Toast.LENGTH_LONG).show();
                                                finish();
                                            }else{
                                                Toast.makeText(ElderContactActivity.this, "添加失败！", Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    });
                                }

                                @Override
                                public void onError(int typeid, final int code, final String msg) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ElderContactActivity.this, "添加失败！" + code + " " + msg, Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            });
                } else {
                    MiaoApplication.getMiaoHealthElderManager().updateEdlerContact(deviceSn, deviceNo, contactId, etAddContactName.getText().toString(), etAddContactMobile.getText().toString(), new MiaoElderLinstener() {
                        @Override
                        public void onStatus(int typeid, final int state) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (state==1){
                                        Toast.makeText(ElderContactActivity.this, "修改成功！", Toast.LENGTH_LONG).show();
                                        finish();
                                    }else{
                                        Toast.makeText(ElderContactActivity.this, "修改失败！", Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        }

                        @Override
                        public void onError(int typeid, final int code, final String msg) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ElderContactActivity.this, "修改失败！" + code + " " + msg, Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                }
            } else {
                Toast.makeText(ElderContactActivity.this, "姓名和电话号不能为空！", Toast.LENGTH_LONG).show();
            }

        }
    }

    private void initView() {
        etAddContactName = (EditText) findViewById(R.id.et_add_contact_name);
        etAddContactMobile = (EditText) findViewById(R.id.et_add_contact_mobile);
        btnAddContactr = (Button) findViewById(R.id.btn_add_contactr);
        btnAddContactr.setOnClickListener(ElderContactActivity.this);
        activityElderAddContact = (LinearLayout) findViewById(R.id.activity_elder_add_contact);
    }
}
