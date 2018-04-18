package me.aidoc.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.miao.demo.adapter.BindDeviceListAdapter;
import cn.miao.demo.elder.ElderDeviceActivity;
import cn.miao.demo.utils.MyToast;
import cn.miao.demo.utils.NetUtil;
import cn.miao.demo.utils.PullToRefreshBase;
import cn.miao.demo.utils.PullToRefreshListView;
import cn.miao.demo.widget.UnbindDeviceDialog;
import cn.miao.lib.MiaoApplication;
import cn.miao.lib.listeners.MiaoUnBindAllListener;
import cn.miao.lib.listeners.MiaoUserDeviceListListener;
import cn.miao.lib.model.BindDeviceBean;
import cn.miao.lib.model.BindDeviceListBean;

public class BindDeviceListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
         View.OnClickListener{

    protected final String TAG = getClass().getSimpleName();
    private PullToRefreshListView pullListView;
    protected ListView lvList;
    private ImageView back;
    private TextView unbind_all;
    private UnbindDeviceDialog unbindDeviceDialog;
    private int page_no = 1;
    public final static String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm";
    private static SimpleDateFormat sdf = new SimpleDateFormat();
    private int totalPage = 0;
    private ArrayList<BindDeviceBean> mList;
    protected BindDeviceListBean bindDeviceListBean;
    protected BindDeviceListAdapter bindDeviceListAdapter;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e(TAG, "handleMessage: " + msg.toString());
            switch (msg.what) {
                case 0:
                    Toast.makeText(BindDeviceListActivity.this, String.valueOf(msg.obj), Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    if (bindDeviceListBean != null)
                        bindDeviceListAdapter.setBindDeviceBeans(mList);
                    bindDeviceListAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.bind_list_layout);
        initView();
        initData();
    }


    private void initView() {
        pullListView =  (PullToRefreshListView) findViewById(R.id.lv_device_type_list);
        lvList = pullListView.getRefreshableView();
        lvList.setVerticalScrollBarEnabled(false);
        lvList.setCacheColorHint(0);
        lvList.setDivider(getResources().getDrawable(R.color.transparent));
        lvList.setSelector(R.color.transparent);
        lvList.setDividerHeight(15);
        lvList.setOnItemClickListener(this);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        unbind_all = (TextView) findViewById(R.id.unbind_all);
        unbind_all.setOnClickListener(this);

        pullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                // 设置时间显示的格式
                String format = FORMAT_DATE_TIME;
                pullListView.setLastUpdatedLabel(getCurrentTime(format));
                page_no = 1;
                getMyDeviceList(page_no);
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                page_no++;
                Log.e(TAG, "page_no: " +page_no+"      totalPage: " +totalPage);
                if(page_no > totalPage){
                    MyToast.showToast(BindDeviceListActivity.this,"已加载全部");
                    pullListView.onPullUpRefreshComplete();
                }else {
                    getMyDeviceList(page_no);
                }
            }
        });
    }

    private void initData() {
        mList = new ArrayList<BindDeviceBean>();
        bindDeviceListAdapter = new BindDeviceListAdapter(BindDeviceListActivity.this);
        lvList.setAdapter(bindDeviceListAdapter);
    }


    public String getCurrentTime(String format) {
        if (format == null || format.trim().equals("")) {
            sdf.applyPattern(FORMAT_DATE_TIME);
        } else {
            sdf.applyPattern(format);
        }
        return sdf.format(new Date());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyDeviceList(page_no);
    }

    /**
     * 弹出类监听
     */
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_sure:
                    unBindAllDevice();
                    break;
                case R.id.button_cancel:
                    break;
                default:
                    break;
            }
            unbindDeviceDialog.dismiss();
        }
    };

    private void getMyDeviceList(final int page_no) {
        if(!NetUtil.chackNetStatus(BindDeviceListActivity.this)){
            MyToast.showToast(BindDeviceListActivity.this,"网络连接异常，请稍后再试");
            return;
        }
        if(MiaoApplication.getMiaoHealthManager()==null)return;
        MiaoApplication.getMiaoHealthManager().fetchUserDeviceList(page_no, new MiaoUserDeviceListListener() {
            @Override
            public void onUserDeviceListResponse(BindDeviceListBean bindDeviceList) {
                if (bindDeviceList != null)
                    bindDeviceListBean = bindDeviceList;
                ArrayList<BindDeviceBean> list = bindDeviceListBean.getData();
                totalPage = bindDeviceListBean.getTotal_page();
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

            }
        });
    }


    private void unBindAllDevice() {
        MiaoApplication.getMiaoHealthManager().unbindAllDevice(new MiaoUnBindAllListener() {
            @Override
            public void onUnBindResponse(int unbindStatus) {
                if (unbindStatus == 1) {
                    MyToast.showToast(BindDeviceListActivity.this, "设备解除绑定成功");
                    page_no = 1;
                    getMyDeviceList(page_no);
                }else if (unbindStatus == 2) {
                    MyToast.showToast(BindDeviceListActivity.this, "成功解除部分设备，其余设备解绑请稍后重试");
                    page_no = 1;
                    getMyDeviceList(page_no);
                }else if (unbindStatus == 3){
                    MyToast.showToast(BindDeviceListActivity.this, "设备解除绑定失败");
                }
            }

            @Override
            public void onError(int code, String msg) {
                MyToast.showToast(BindDeviceListActivity.this, "解除绑定失败 code：" + code + " msg:" + msg);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        BindDeviceBean bindDeviceBean=  mList.get(i);
        if (bindDeviceBean.getDevice_sn().equals("2016011301")) {
            Intent in = new Intent(BindDeviceListActivity.this, ElderDeviceActivity.class);
            in.putExtra("bindDeviceBean", bindDeviceBean);
            startActivity(in);
        }else {
            Intent in = new Intent(BindDeviceListActivity.this, DeviceActivity.class);
            in.putExtra("bindDeviceBean", bindDeviceBean);
            startActivity(in);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.unbind_all:
                if(unbindDeviceDialog == null){
                    unbindDeviceDialog = new UnbindDeviceDialog(BindDeviceListActivity.this,itemsOnClick);
                }
                unbindDeviceDialog.show();
                unbindDeviceDialog.setTitleType(1);
                break;
            default:
                break;
        }
    }

    private void sendMessage(int what, String msg) {
        Message message = new Message();
        message.what = what;
        message.obj = msg;
        handler.sendMessage(message);
    }
}
