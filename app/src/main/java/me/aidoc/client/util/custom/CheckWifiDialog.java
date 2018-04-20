package me.aidoc.client.util.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import cn.miao.demo.R;


/**
 * Wifi选择弹出框
 * Created by shule on 2017/12/13.
 */
public class CheckWifiDialog extends Dialog {

    private Context context;
    private Button  button_cancel;
    private ListView serchList;
    private ArrayList<HashMap<String, String>> arraylist;
    private View.OnClickListener onClickListener;
    private AdapterView.OnItemClickListener mItemOnClickListener;

    public CheckWifiDialog(Context context) {
        super(context);
        this.context = context;

    }

    public CheckWifiDialog(Context context, View.OnClickListener onClickListener, AdapterView.OnItemClickListener onItemClickListener){
        super(context,R.style.mycenter_ms_dialog);
        this.context = context;
        this.onClickListener = onClickListener;
        this.mItemOnClickListener = onItemClickListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.check_wifi_dialog_layout);
        initView();
    }

    private void initView(){
        button_cancel = (Button)findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(onClickListener);
        serchList = (ListView) findViewById(R.id.search_list);
        serchList.setOnItemClickListener(mItemOnClickListener);
    }

    public void setReslutData(ArrayList<HashMap<String, String>> arraylist){
        this.arraylist = arraylist;
        SimpleAdapter adapter = new SimpleAdapter(context, arraylist, R.layout.device_item,new String[]{"apSSid"}, new int[]{R.id.device_name});
        if(serchList != null){
            serchList.setAdapter(adapter);
        }
    }
}