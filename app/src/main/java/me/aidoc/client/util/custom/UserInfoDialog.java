package me.aidoc.client.util.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import cn.miao.demo.R;


/**
 * 用户信息弹出框
 * Created by shule on 2016/3/31.
 */
public class UserInfoDialog extends Dialog {

    private Context context;
    private Button  sure;
    protected RadioGroup rgType;
    protected RadioButton rb_type_man;
    protected RadioButton rb_type_women;
    protected TextView etDialogName;
    protected TextView etDialogAge;
    protected TextView etDialogHeight;
    protected TextView et_dialog_weight;
    protected LinearLayout layout_weight;
    private ArrayList<HashMap<String, String>> arraylist;
    private View.OnClickListener onClickListener;
    private RadioGroup.OnCheckedChangeListener checkedChangeListener;

    public UserInfoDialog(Context context) {
        super(context);
        this.context = context;

    }

    public UserInfoDialog(Context context, View.OnClickListener onClickListener,RadioGroup.OnCheckedChangeListener checkedChangeListener){
        super(context,R.style.mycenter_ms_dialog);
        this.context = context;
        this.onClickListener = onClickListener;
        this.checkedChangeListener = checkedChangeListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.user_info_dialog_layout);
        initView();
    }

    private void initView(){
        sure = (Button)findViewById(R.id.button_sure);
        sure.setOnClickListener(onClickListener);
        rgType = (RadioGroup) findViewById(R.id.rg_type);
        rgType.setOnCheckedChangeListener(checkedChangeListener);
        rb_type_man = (RadioButton) findViewById(R.id.rb_type_man);
        rb_type_women = (RadioButton) findViewById(R.id.rb_type_women);

        etDialogAge = (TextView)findViewById(R.id.et_dialog_age);
        etDialogHeight = (TextView)findViewById(R.id.et_dialog_height);
        et_dialog_weight = (TextView)findViewById(R.id.et_dialog_weight);
        layout_weight = (LinearLayout)findViewById(R.id.layout_weight);
        etDialogAge.setOnClickListener(onClickListener);
        etDialogHeight.setOnClickListener(onClickListener);
        et_dialog_weight.setOnClickListener(onClickListener);
    }

    /**
     * 布局显示
     * @param functional_id
     */
    public void setFunction(int functional_id){
        if(functional_id == 7){
            layout_weight.setVisibility(View.GONE);
        }
    }

    public void setBirthday(String birthday){
        if(etDialogAge != null){
            etDialogAge.setText(birthday+"");
        }
    }

    public void setSex(int sex){
        if(sex == 0){
            rb_type_man.setSelected(true);
        }else {
            rb_type_women.setSelected(true);
        }
    }

    public void setHeight(int height){
        if(etDialogHeight != null){
            etDialogHeight.setText(height+"");
        }
    }

    public void setWeitht(int weitht){
        if(et_dialog_weight != null){
            et_dialog_weight.setText(weitht+"");
        }
    }
}