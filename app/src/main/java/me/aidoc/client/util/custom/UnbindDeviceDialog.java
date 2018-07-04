package me.aidoc.client.util.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.miao.demo.R;


/**
 * 解绑弹出框
 * Created by shule on 2016/3/31.
 */
public class UnbindDeviceDialog extends Dialog {

    private Context context;
    private TextView textView_title_mydialog;
    private Button button_sure, button_cancel;
    private View.OnClickListener onClickListener;

    public UnbindDeviceDialog(Context context) {
        super(context);
        this.context = context;

    }

    public UnbindDeviceDialog(Context context, View.OnClickListener onClickListener){
        super(context,R.style.mycenter_ms_dialog);
        this.context = context;
        this.onClickListener = onClickListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.unbind_device_dialog_layout);
        initView();
    }

    private void initView(){
        button_sure = (Button)findViewById(R.id.button_sure);
        button_cancel = (Button)findViewById(R.id.button_cancel);
        textView_title_mydialog = (TextView)findViewById(R.id.textView_title_mydialog);
        button_sure.setOnClickListener(onClickListener);
        button_cancel.setOnClickListener(onClickListener);
    }

    public void setTitleType(int type){
        if(type == 1){
            textView_title_mydialog.setText("是否解绑所有设备？");
        }else if(type == 2){
            textView_title_mydialog.setText("是否解绑该设备？");
        }
    }
}