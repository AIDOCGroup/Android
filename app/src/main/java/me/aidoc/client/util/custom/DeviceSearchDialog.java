package me.aidoc.client.util.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.List;

import cn.miao.demo.R;


/**
 * 搜索弹出框
 * Created by shule on 2016/3/31.
 */
public class DeviceSearchDialog extends Dialog {

    private Context context;
    private Button  button_cancel;
    private ListView serchList;
    private List<HashMap<String, String>> arraylist;
    private View.OnClickListener onClickListener;
    private AdapterView.OnItemClickListener mItemOnClickListener;

    public DeviceSearchDialog(Context context) {
        super(context);
        this.context = context;

    }

    public DeviceSearchDialog(Context context, View.OnClickListener onClickListener, AdapterView.OnItemClickListener onItemClickListener){
        super(context,R.style.mycenter_ms_dialog);
        this.context = context;
        this.onClickListener = onClickListener;
        this.mItemOnClickListener = onItemClickListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.search_device_dialog_layout);
        initView();
    }

    private void initView(){
        button_cancel = (Button)findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(onClickListener);
        serchList = (ListView) findViewById(R.id.search_list);
        serchList.setOnItemClickListener(mItemOnClickListener);
    }

    public void setReslutData(List<HashMap<String, String>> arraylist){
        this.arraylist = arraylist;
        SimpleAdapter adapter = new SimpleAdapter(context, arraylist, R.layout.device_item,new String[]{"name"}, new int[]{R.id.device_name});
        if(serchList != null){
            serchList.setAdapter(adapter);
        }
    }
}