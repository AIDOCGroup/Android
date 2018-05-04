package me.aidoc.client.mvp.health;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import cn.miao.demo.R;
import cn.miao.lib.MiaoApplication;
import cn.miao.lib.listeners.MiaoElderLinstener;

public class FamilyReminderActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    protected EditText etReminderDate;
    protected EditText etReminderTime;
    protected RadioButton rbType0;
    protected RadioButton rbType1;
    protected RadioButton rbType2;
    protected RadioButton rbType3;
    protected RadioButton rbType4;
    protected RadioButton rbType5;
    protected RadioGroup rgType;
    protected RadioButton rbRepeat0;
    protected RadioButton rbRepeat1;
    protected RadioButton rbRepeat2;
    protected RadioButton rbRepeat3;
    protected RadioButton rbRepeat4;
    protected RadioGroup rgRepeat;
    protected EditText etReminderContent;
    protected Button btnAddReminder;
    private Handler handler = new Handler(Looper.getMainLooper());
    private String deviceSn, deviceNo;
    private int checkTypePosition = 0;
    private int checkRepeatPosition = 0;
    private boolean isAdd = true;
    private int remind_id, remind_type, repeat_type;
    private String start_at, start_time, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_family_reminder);
        initView();

        deviceSn = getIntent().getStringExtra("deviceSn");
        deviceNo = getIntent().getStringExtra("deviceNo");

        if (getIntent().hasExtra("remind_id")) {
            isAdd = false;
            remind_id = getIntent().getIntExtra("remind_id", 0);
            start_at = getIntent().getStringExtra("start_at");
            start_time = getIntent().getStringExtra("start_time");
            remind_type = getIntent().getIntExtra("remind_type", 0);
            repeat_type = getIntent().getIntExtra("repeat_type", 0);
            content = getIntent().getStringExtra("content");



        } else {
            isAdd = true;
            remind_type = 0;
            repeat_type = 0;
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            String date = sDateFormat.format(new java.util.Date());
            start_at = date.substring(0, date.indexOf(" "));
            start_time = date.substring(date.indexOf(" ") + 1, date.length());
        }

        etReminderDate.setText(start_at);
        etReminderTime.setText(start_time);
        switch (remind_type){
            case 0:
                rgType.check(rbType0.getId());
                break;
            case 1:
                rgType.check(rbType1.getId());
                break;
            case 2:
                rgType.check(rbType2.getId());
                break;
            case 3:
                rgType.check(rbType3.getId());
                break;
            case 4:
                rgType.check(rbType4.getId());
                break;
            case 5:
                rgType.check(rbType5.getId());
                break;
        }

        switch (repeat_type){
            case 0:
                rgRepeat.check(rbRepeat0.getId());
                break;
            case 1:
                rgRepeat.check(rbRepeat1.getId());
                break;
            case 2:
                rgRepeat.check(rbRepeat2.getId());
                break;
            case 3:
                rgRepeat.check(rbRepeat3.getId());
                break;
            case 4:
                rgRepeat.check(rbRepeat4.getId());
                break;
        }

        etReminderContent.setText(content);



    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_add_reminder) {
            /*if (!TextUtils.isEmpty(etReminderDate.getText()) &&
                    !TextUtils.isEmpty(etReminderTime.getText()) &&
                    !TextUtils.isEmpty(etReminderContent.getText())) {*/
                if (isAdd) {
                    MiaoApplication.getMiaoHealthElderManager().addFamilyReminder(deviceSn, deviceNo,
                            etReminderDate.getText().toString(),
                            etReminderTime.getText().toString(),
                            checkTypePosition, checkRepeatPosition,
                            etReminderContent.getText().toString(), new MiaoElderLinstener() {
                                @Override
                                public void onStatus(int typeid, final int state) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (state==1){
                                            Toast.makeText(FamilyReminderActivity.this, "添加成功！", Toast.LENGTH_LONG).show();
                                            finish();
                                            }else{
                                                Toast.makeText(FamilyReminderActivity.this, "添加失败！", Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    });
                                }

                                @Override
                                public void onError(int typeid, final int code, final String msg) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(FamilyReminderActivity.this, "添加失败！" + code + " " + msg, Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            });
                } else {
                    MiaoApplication.getMiaoHealthElderManager().updateFamilyRemind(deviceSn, deviceNo,
                            remind_id,
                            etReminderDate.getText().toString(),
                            etReminderTime.getText().toString(),
                            checkTypePosition, checkRepeatPosition,
                            etReminderContent.getText().toString(), new MiaoElderLinstener() {
                                @Override
                                public void onStatus(int typeid, final int state) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (state==1){
                                                Toast.makeText(FamilyReminderActivity.this, "修改成功！", Toast.LENGTH_LONG).show();
                                                finish();
                                            }else{
                                                Toast.makeText(FamilyReminderActivity.this, "修改失败！", Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    });
                                }

                                @Override
                                public void onError(int typeid, final int code, final String msg) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(FamilyReminderActivity.this, "修改失败！" + code + " " + msg, Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            });
                }

            /*} else {
                Toast.makeText(FamilyReminderActivity.this, "请输入完整信息", Toast.LENGTH_LONG).show();
            }*/

        }
    }

    private void initView() {
        etReminderDate = (EditText) findViewById(R.id.et_reminder_date);
        etReminderTime = (EditText) findViewById(R.id.et_reminder_time);
        rbType0 = (RadioButton) findViewById(R.id.rb_type_0);
        rbType1 = (RadioButton) findViewById(R.id.rb_type_1);
        rbType2 = (RadioButton) findViewById(R.id.rb_type_2);
        rbType3 = (RadioButton) findViewById(R.id.rb_type_3);
        rbType4 = (RadioButton) findViewById(R.id.rb_type_4);
        rbType5 = (RadioButton) findViewById(R.id.rb_type_5);
        rgType = (RadioGroup) findViewById(R.id.rg_type);
        rgType.setOnCheckedChangeListener(this);
        rbRepeat0 = (RadioButton) findViewById(R.id.rb_repeat_0);
        rbRepeat1 = (RadioButton) findViewById(R.id.rb_repeat_1);
        rbRepeat2 = (RadioButton) findViewById(R.id.rb_repeat_2);
        rbRepeat3 = (RadioButton) findViewById(R.id.rb_repeat_3);
        rbRepeat4 = (RadioButton) findViewById(R.id.rb_repeat_4);
        rgRepeat = (RadioGroup) findViewById(R.id.rg_repeat);
        rgRepeat.setOnCheckedChangeListener(this);
        etReminderContent = (EditText) findViewById(R.id.et_reminder_content);
        btnAddReminder = (Button) findViewById(R.id.btn_add_reminder);
        btnAddReminder.setOnClickListener(FamilyReminderActivity.this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_type_0:
                checkTypePosition = 0;
                break;
            case R.id.rb_type_1:
                checkTypePosition = 1;
                break;
            case R.id.rb_type_2:
                checkTypePosition = 2;
                break;
            case R.id.rb_type_3:
                checkTypePosition = 3;
                break;
            case R.id.rb_type_4:
                checkTypePosition = 4;
                break;
            case R.id.rb_type_5:
                checkTypePosition = 5;
                break;
            case R.id.rb_repeat_0:
                checkRepeatPosition = 0;
                break;
            case R.id.rb_repeat_1:
                checkRepeatPosition = 1;
                break;
            case R.id.rb_repeat_2:
                checkRepeatPosition = 2;
                break;
            case R.id.rb_repeat_3:
                checkRepeatPosition = 3;
                break;
            case R.id.rb_repeat_4:
                checkRepeatPosition = 4;
                break;


        }
    }
}
