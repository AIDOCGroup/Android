package me.aidoc.client.util.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.miao.demo.R;


/**
 * wifi密码弹出框
 * Created by shule on 2016/3/31.
 */
public class WifiPasDialog extends Dialog {

    private Context context;
    private Button  wifi_sure;
    protected EditText et_passw;
    private View.OnClickListener onClickListener;

    public WifiPasDialog(Context context) {
        super(context);
        this.context = context;

    }

    public WifiPasDialog(Context context, View.OnClickListener onClickListener){
        super(context,R.style.mycenter_ms_dialog);
        this.context = context;
        this.onClickListener = onClickListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.wifi_passw_dialog_layout);
        initView();
    }

    private void initView(){
        wifi_sure = (Button)findViewById(R.id.wifi_sure);
        wifi_sure.setOnClickListener(onClickListener);
        et_passw = (EditText)findViewById(R.id.et_passw);
    }

    public String getPassWord(){
        return et_passw.getText()+"";
    }

}