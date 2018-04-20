package me.aidoc.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.miao.demo.adapter.DeviceListAdapter;
import cn.miao.demo.utils.MyToast;
import cn.miao.demo.utils.NetUtil;
import cn.miao.demo.utils.PullToRefreshBase;
import cn.miao.demo.utils.PullToRefreshListView;
import cn.miao.lib.MiaoApplication;
import cn.miao.lib.listeners.MiaoDeviceListListener;
import cn.miao.lib.model.DeviceBean;
import cn.miao.lib.model.DeviceListBean;

public class DeviceListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,View.OnClickListener {

    protected final String TAG = getClass().getSimpleName();
    private PullToRefreshListView pullListView;
    protected ListView lvList;
    protected DeviceListBean deviceListBean;
    protected DeviceListAdapter deviceListAdapter;
    private ImageView back,search,search_icon;
    private EditText search_text;
    private RelativeLayout search_layout;
    private boolean canSerch = false; //输入框状态
    private int type_id;
    private int page_no = 1;
    public final static String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm";
    private static SimpleDateFormat sdf = new SimpleDateFormat();
    private ArrayList<DeviceBean> mList;
    private int selection = 0;//下拉刷新位置标记
    private int totalPage = 0;

    private static final int RESULT_CODE_SCAN_CODE = 1001;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e(TAG, "handleMessage: " + msg.toString());
            switch (msg.what) {
                case 0:
                    Toast.makeText(DeviceListActivity.this, String.valueOf(msg.obj), Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    if (deviceListBean != null)
                        deviceListAdapter.setList(mList);
                    deviceListAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    Intent intent = new Intent(DeviceListActivity.this, CaptureActivity.class);
                    intent.putExtra("device_sn", String.valueOf(msg.obj));
                    startActivityForResult(intent, RESULT_CODE_SCAN_CODE);
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_pulllist);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        search = (ImageView) findViewById(R.id.search);
        search_icon = (ImageView) findViewById(R.id.search_icon);
        search_layout = (RelativeLayout) findViewById(R.id.search_layout);
        search_text = (EditText) findViewById(R.id.search_text);
        back.setOnClickListener(this);
        search_layout.setOnClickListener(this);
        search.setOnClickListener(this);
        search_text.setOnClickListener(this);
        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s.toString())){
                    searchDeviceList(type_id,s.toString());
                }else {
                    mList.clear();
                    sendMessage(1, "");
                }
            }
        });

        pullListView =  (PullToRefreshListView) findViewById(R.id.lv_device_type_list);
        lvList = pullListView.getRefreshableView();
        lvList.setVerticalScrollBarEnabled(false);
        lvList.setCacheColorHint(0);
        lvList.setDivider(getResources().getDrawable(R.color.transparent));
        lvList.setSelector(R.color.transparent);
        lvList.setDividerHeight(15);
        lvList.setOnItemClickListener(this);

        pullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                // 设置时间显示的格式
                if(canSerch){
                    pullListView.onPullDownRefreshComplete();
                    return;
                }
                String format = FORMAT_DATE_TIME;
                pullListView.setLastUpdatedLabel(getCurrentTime(format));
                page_no = 1;
                getDeviceList(type_id,page_no);
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                if(canSerch){
                    MyToast.showToast(DeviceListActivity.this,"已加载全部");
                    pullListView.onPullUpRefreshComplete();
                    return;
                }
                page_no++;
                if(mList != null){
                    selection = mList.size();
                }
                Log.e(TAG, "page_no: " +page_no+"      totalPage: " +totalPage);
                if(page_no > totalPage){
                    MyToast.showToast(DeviceListActivity.this,"已加载全部");
                    pullListView.onPullUpRefreshComplete();
                }else {
                    getDeviceList(type_id,page_no);
                }
            }
        });
    }


    private void initData() {
        mList = new ArrayList<DeviceBean>();
        type_id = getIntent().getIntExtra("type_id", 1);
        deviceListAdapter = new DeviceListAdapter(DeviceListActivity.this);
        lvList.setAdapter(deviceListAdapter);
        getDeviceList(type_id,page_no);
    }


    public String getCurrentTime(String format) {
        if (format == null || format.trim().equals("")) {
            sdf.applyPattern(FORMAT_DATE_TIME);
        } else {
            sdf.applyPattern(format);
        }
        return sdf.format(new Date());
    }

    /**
     * 根据类型获取设备列表
     * @param type_id
     * @param page_no
     */
    private void getDeviceList(long type_id,final int page_no) {
        if(!NetUtil.chackNetStatus(DeviceListActivity.this)){
            MyToast.showToast(DeviceListActivity.this,"网络连接异常，请稍后再试");
            return;
        }
        if (MiaoApplication.getMiaoHealthManager() == null) return;
        MiaoApplication.getMiaoHealthManager().fetchDeviceList(type_id, page_no, new MiaoDeviceListListener() {
            @Override
            public void onDeviceLisResponse(DeviceListBean deviceList) {
                if (deviceList != null)
                    deviceListBean = deviceList;
                ArrayList<DeviceBean> list = deviceListBean.getData();
                totalPage = deviceListBean.getTotal_page();
                if(list != null || list.size() > 0){
                    if(page_no == 1){
                        mList.clear();
                    }
                    for (int i = 0; i < list.size(); i++) {
                        mList.add(list.get(i));
                    }
                }
                pullListView.onPullDownRefreshComplete();
                pullListView.onPullUpRefreshComplete();
                sendMessage(1, "");
            }

            @Override
            public void onError(int code, String msg) {
                sendMessage(0, "设备类型列表获取失败 code：" + code + " msg:" + msg);
                pullListView.onPullDownRefreshComplete();
                pullListView.onPullUpRefreshComplete();
            }
        });
    }

    /**
     * 搜索设备
     * @param type_id
     * @param name
     */
    public void searchDeviceList(int type_id,String name){
        if (MiaoApplication.getMiaoHealthManager() == null) return;
        MiaoApplication.getMiaoHealthManager().fetchSearchDevice(type_id, name, new MiaoDeviceListListener() {
            @Override
            public void onDeviceLisResponse(DeviceListBean deviceList) {
                if (deviceList != null)
                    deviceListBean = deviceList;
                ArrayList<DeviceBean> list = deviceListBean.getData();
                totalPage = deviceListBean.getTotal_page();
                if(list != null && list.size() > 0){
                    if(page_no == 1){
                        mList.clear();
                    }
                    for (int i = 0; i < list.size(); i++) {
                        mList.add(list.get(i));
                    }
                }else {
                    MyToast.showToast(DeviceListActivity.this,"未搜索到相关设备");
                }
                pullListView.onPullDownRefreshComplete();
                pullListView.onPullUpRefreshComplete();
                sendMessage(1, "");
            }

            @Override
            public void onError(int code, String msg) {
                sendMessage(0, "设备类型列表获取失败 code：" + code + " msg:" + msg);
                pullListView.onPullDownRefreshComplete();
                pullListView.onPullUpRefreshComplete();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                if(!canSerch){
                    finish();
                }else {
                    setSearchStatus(false);
                    search_text.setText("");
                }
                break;
            case R.id.search:
                searchDeviceList(type_id,search_text.getText()+"");
                break;
            case R.id.search_text:
            case R.id.search_layout:
                if(!canSerch){
                    setSearchStatus(true);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        DeviceBean deviceBean = mList.get(i);
        Intent in = new Intent(DeviceListActivity.this, DeviceWebViewActivity.class);
        in.putExtra("deviceBean", deviceBean);
        startActivity(in);

    }

    /**
     * 搜索输入框状态变化
     * @param showSearch
     */
    public void setSearchStatus(boolean showSearch){
        search.setVisibility(showSearch?View.VISIBLE:View.GONE);
        search_icon.setVisibility(showSearch?View.GONE:View.VISIBLE);
        back.setImageDrawable(showSearch?getResources().getDrawable(R.drawable.cmbkb_emotionstore_progresscancelbtn)
                :getResources().getDrawable(R.drawable.btn_fanhui));
        canSerch = showSearch;
        if(canSerch){
            mList.clear();
            sendMessage(1, "");
        }else {
            getDeviceList(type_id,page_no);
        }
    }

    private void sendMessage(int what, String msg) {
        Message message = new Message();
        message.what = what;
        message.obj = msg;
        handler.sendMessage(message);
    }

}
