package me.aidoc.client.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import cn.miao.demo.utils.MyToast;
import cn.miao.demo.utils.NetUtil;
import cn.miao.lib.MiaoApplication;
import cn.miao.lib.enums.DataTypeEnum;
import cn.miao.lib.listeners.MiaoQueryApiDataListener;
import cn.miao.lib.model.AllDeviceDataBean;
import cn.miao.lib.model.BloodGlucoseBean;
import cn.miao.lib.model.BloodPressureBean;
import cn.miao.lib.model.DataBean;
import cn.miao.lib.model.HeartBean;
import cn.miao.lib.model.SleepBean;
import cn.miao.lib.model.SlimmingBean;
import cn.miao.lib.model.Spo2Bean;
import cn.miao.lib.model.SportBean;
import cn.miao.lib.model.TemperatureBean;

public class GetApiDatasActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,View.OnClickListener {

    protected ListView llGetApiData;
    protected TextView tvLog;
    protected RelativeLayout activityGetApiDatas;
    protected ArrayList<String> list;
    protected ArrayList<Integer> listID;
    protected String log = "";
    private ImageView back;
    protected SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
            Locale.getDefault());
    private ProgressDialog dialog;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(GetApiDatasActivity.this, String.valueOf(msg.obj), Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    log = String.valueOf(msg.obj) + "\n" + log;
//                    tvLog.setText(log);
                    break;
                case 2:
                    Intent intent = new Intent(GetApiDatasActivity.this,GetApiDataReslutActivity.class);
                    intent.putExtra("result",log);
                    String title = msg.getData().getString("title");
                    intent.putExtra("title",title);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_get_api_datas);
        initView();
        list = new ArrayList<>();
        listID = new ArrayList<>();
        list.add("运动");
        listID.add(1);
        list.add("睡眠");
        listID.add(2);
        list.add("血压");
        listID.add(3);
        list.add("血糖");
        listID.add(4);
        list.add("温度");
        listID.add(5);
        list.add("瘦身（体重体重，体脂）");
        listID.add(7);
        list.add("心率");
        listID.add(8);
        list.add("血氧");
        listID.add(9);
        list.add("所有数据");
        listID.add(10);

        ApiDataAdaopter apiDataAdaopter = new ApiDataAdaopter();
        llGetApiData.setAdapter(apiDataAdaopter);
        llGetApiData.setOnItemClickListener(this);
        setListViewHeightBasedOnChildren(llGetApiData);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    private void initView() {
        llGetApiData = (ListView) findViewById(R.id.ll_get_api_data);
        tvLog = (TextView) findViewById(R.id.tv_log);
        activityGetApiDatas = (RelativeLayout) findViewById(R.id.activity_get_api_datas);
        dialog = new ProgressDialog(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        if(!NetUtil.chackNetStatus(GetApiDatasActivity.this)){
            MyToast.showToast(GetApiDatasActivity.this,"网络连接异常，请稍后再试");
            return;
        }
        if (MiaoApplication.getMiaoHealthManager() == null) return;
        log = "";
        sendMessage(1, "获取" + list.get(position) + "数据");
        setDivision();
        dialog.show();
        long current = System.currentTimeMillis();
        long sevenDaysAgo = current -60*60*24*7*1000;
        MiaoApplication.getMiaoHealthManager().fetchApiDeviceData("", "", sevenDaysAgo, current,
                DataTypeEnum.getDataTypeEnumType(listID.get(position)), new MiaoQueryApiDataListener() {
                    @Override
                    public <T extends DataBean> void onApiDataResponse(DataTypeEnum dataTypeEnum, ArrayList<T> ApiDataBean) {
                        sendMessage(1, "获取成功");
                        setDivision();
                        dialog.dismiss();
                        if (dataTypeEnum == DataTypeEnum.DATA_BLOOD_GLUCOSE) {
                            ArrayList<BloodGlucoseBean> bean = (ArrayList<BloodGlucoseBean>) ApiDataBean;
                            if (bean != null && bean.size() > 0) {
                                for (int i = 0; i < bean.size(); i++) {
                                    sendMessage(1, "血糖值:" + bean.get(i).getGlucose_value()
                                            + "\n测量时间:" + format.format(new Date(bean.get(i).getMeasure_time()))
                                            + "\n数据源:" + bean.get(i).getData_source());
                                    setDivision();
                                }
                            } else{
                                sendMessage(1, "暂无数据");
                                setDivision();
                            }
                        } else if (dataTypeEnum == DataTypeEnum.DATA_BLOOD_PRESSURE) {
                            ArrayList<BloodPressureBean> bean = (ArrayList<BloodPressureBean>) ApiDataBean;
                            if (bean != null && bean.size() > 0) {
                                for (int i = 0; i < bean.size(); i++) {
                                    sendMessage(1, "高压:" + bean.get(i).getHigh_press()
                                            + "\n低压:" + bean.get(i).getLow_press()
                                            + "\n心率:" + bean.get(i).getHeart_rate()
                                            + "\n时间:" + format.format(new Date(bean.get(i).getMeasure_time()))
                                            + "\n数据源:" + bean.get(i).getData_source()
                                    );
                                    setDivision();
                                }
                            } else {
                                sendMessage(1, "暂无数据");
                                setDivision();
                            }
                        } else if (dataTypeEnum == DataTypeEnum.DATA_SPORT) {
                            ArrayList<SportBean> bean = (ArrayList<SportBean>) ApiDataBean;
                            if (bean != null && bean.size() > 0) {
                                for (int i = 0; i < bean.size(); i++) {
                                    sendMessage(1, "步数:" + bean.get(i).getSteps()+"步"
                                            + "\n卡路里:" + bean.get(i).getCalories()+"cal"
                                            + "\n距离:" + bean.get(i).getDistance()+"米"
                                            + "\n时间:" + bean.get(i).getDate_time()
                                            + "\n数据源:" + bean.get(i).getData_source()
                                    );
                                    setDivision();
                                }
                            } else {
                                sendMessage(1, "暂无数据");
                                setDivision();
                            }
                        } else if (dataTypeEnum == DataTypeEnum.DATA_SLEEP) {
                            ArrayList<SleepBean> bean = (ArrayList<SleepBean>) ApiDataBean;
                            if (bean != null && bean.size() > 0) {
                                for (int i = 0; i < bean.size(); i++) {
                                    sendMessage(1, "有效睡眠:" + bean.get(i).getEffect_duration() + "秒"
                                            + "\n总睡眠:" + bean.get(i).getDuration() + "秒"
                                            + "\n深睡:" + bean.get(i).getDeep_time()+ "秒"
                                            + "\n浅睡:" + bean.get(i).getLight_time() + "秒"
                                            + "\n时间:" + bean.get(i).getDate_time()
                                            + "\n数据源:" + bean.get(i).getData_source()
                                            );
                                    setDivision();
                                    //+ "睡眠质量数据:" + bean.get(i).getQuality()
                                }
                            } else {
                                sendMessage(1, "暂无数据");
                                setDivision();
                            }
                        } else if (dataTypeEnum == DataTypeEnum.DATA_SLIMMING) {
                            ArrayList<SlimmingBean> bean = (ArrayList<SlimmingBean>) ApiDataBean;
                            if (bean != null && bean.size() > 0) {
                                for (int i = 0; i < bean.size(); i++) {
                                    sendMessage(1,"体重:" + bean.get(i).getWeight() +"kg"
                                            +"\n体脂率:" + bean.get(i).getFat_ratio()+"% "
                                            + "\n肌肉量:" + bean.get(i).getMuscle()+"kg"
                                            + "\n骨重:" + bean.get(i).getBone_mass()+"kg"
                                            + "\n基础代谢:" + bean.get(i).getMetabolism()+"kcal"
                                            + "\n体水分:" + bean.get(i).getMoisture() + "%"
                                            + "\n时间:" + format.format(new Date(bean.get(i).getMeasure_time()))
                                            + "\n数据源:" + bean.get(i).getData_source()
                                            + "\nBMI:" + bean.get(i).getBmi()
                                    );
                                    setDivision();
                                }
                            } else {
                                sendMessage(1, "暂无数据");
                                setDivision();
                            }
                        } else if (dataTypeEnum == DataTypeEnum.DATA_TEMPERATURE) {
                            ArrayList<TemperatureBean> bean = (ArrayList<TemperatureBean>) ApiDataBean;
                            if (bean != null && bean.size() > 0) {
                                for (int i = 0; i < bean.size(); i++) {
                                    sendMessage(1, "体温:" + bean.get(i).getTemperature()
                                            + "\n时间:" + format.format(new Date(bean.get(i).getMeasure_time()))
                                            + "\n数据源:" + bean.get(i).getData_source()
                                    );
                                    setDivision();
                                }
                            } else {
                                sendMessage(1, "暂无数据");
                                setDivision();
                            }
                        } else if (dataTypeEnum == DataTypeEnum.DATA_HEART) {
                            ArrayList<HeartBean> bean = (ArrayList<HeartBean>) ApiDataBean;
                            if (bean != null && bean.size() > 0) {
                                for (int i = 0; i < bean.size(); i++) {
                                    sendMessage(1,"心率数据:" + bean.get(i).getHeart_rate()
                                            + "\n时间:" +format.format(new Date(bean.get(i).getMeasure_time()))
                                            + "\n数据源:" + bean.get(i).getData_source()
                                    );
                                    setDivision();
                                }
                            } else {
                                sendMessage(1, "暂无" + dataTypeEnum + "数据");
                                setDivision();
                            }
                        }else if(dataTypeEnum == DataTypeEnum.DATA_SPO2){
                            ArrayList<Spo2Bean> bean = (ArrayList<Spo2Bean>) ApiDataBean;
                            if (bean != null) {
                                for (int i = 0; i < bean.size(); i++) {
                                    sendMessage(1, "心率:" + bean.get(i).getHeart_rate()
                                            + "\n血氧:" + bean.get(i).getBlood_oxygen()
                                            + "\n时间:" + format.format(new Date(bean.get(i).getMeasure_time()))
                                    );
                                    setDivision();
                                }
                            } else {
                                sendMessage(1, "暂无数据");
                                setDivision();
                            }
                        }else if(dataTypeEnum == DataTypeEnum.DATA_ALL){
                            ArrayList<AllDeviceDataBean> list = (ArrayList<AllDeviceDataBean>) ApiDataBean;
                            if(list != null && list.size() >= 1){
                                AllDeviceDataBean allDeviceDataBean = list.get(0);
                                //运动
                                ArrayList<SportBean> sportList = allDeviceDataBean.getSportList();
                                for (int i = 0; i < sportList.size(); i++) {
                                    SportBean bean = sportList.get(i);
                                    sendMessage(1,"步数:" + bean.getSteps()+"步"
                                            + "\n卡路里:" + bean.getCalories()+"cal"
                                            + "\n距离:" + bean.getDistance()+"米"
                                            + "\n时间:" + bean.getDate_time()
                                            + "\n数据源:" + bean.getData_source()
                                    );
                                    setDivision();
                                }

                                //睡眠
                                ArrayList<SleepBean> sleepBeen = allDeviceDataBean.getSleepList();
                                for (int i = 0; i < sleepBeen.size(); i++) {
                                    SleepBean bean = sleepBeen.get(i);
                                    sendMessage(1,"有效睡眠:" + bean.getEffect_duration() + "秒"
                                            + "\n总睡眠:" + bean.getDuration() + "秒"
                                            + "\n深睡:" + bean.getDeep_time() + "秒"
                                            + "\n浅睡:" + bean.getLight_time() + "秒"
                                            + "\n时间:" + bean.getMeasure_time()
                                            + "\n数据源:" + bean.getData_source()
                                    );
                                    setDivision();
                                }

                                //血糖
                                ArrayList<BloodGlucoseBean> bloodGlucoseList = allDeviceDataBean.getBlood_glcoseList();
                                for (int i = 0; i < bloodGlucoseList.size(); i++) {
                                    BloodGlucoseBean bean = bloodGlucoseList.get(i);
                                    sendMessage(1,"血糖值"+bean.getGlucose_value()
                                            + "\n测量时间:" + format.format(new Date(bean.getMeasure_time()))
                                            + "\n数据源:" + bean.getData_source()
                                    );
                                    setDivision();
                                }

                                //血压
                                ArrayList<BloodPressureBean> bloodPressureBeenList = allDeviceDataBean.getBlood_pressList();
                                for (int i = 0; i < bloodPressureBeenList.size(); i++) {
                                    BloodPressureBean bean = bloodPressureBeenList.get(i);
                                    sendMessage(1,"\n高压:" + bean.getHigh_press()
                                            + "\n低压:" + bean.getLow_press()
                                            + "\n心率:" + bean.getHeart_rate()
                                            + "\n时间:" + format.format(new Date(bean.getMeasure_time()))
                                            + "\n数据源:" + bean.getData_source()
                                    );
                                    setDivision();
                                }

                                //瘦身
                                ArrayList<SlimmingBean> slimmingBeenList = allDeviceDataBean.getSlimmingList();
                                for (int i = 0; i < slimmingBeenList.size(); i++) {
                                    SlimmingBean bean = slimmingBeenList.get(i);
                                    sendMessage(1,"体重数据:" + bean.getWeight()+ "kg" +
                                            "\n体脂率数据:" + bean.getFat_ratio()+ "%"
                                            + "\n肌肉量:" + bean.getMuscle()+ "kg"
                                            + "\n骨重数据:" + bean.getBone_mass()+ "kg"
                                            + "\n基础代谢数据:" + bean.getMetabolism()+"kcal"
                                            + "\n体水分数据:" + bean.getMoisture() + "%"
                                            + "\nBMI:" + bean.getBmi()
                                            + "\n时间:" + format.format(new Date(bean.getMeasure_time()))
                                            + "\n数据源:" + bean.getData_source()
                                    );
                                    setDivision();
                                }

                                //温度
                                ArrayList<TemperatureBean> temperatureBeen = allDeviceDataBean.getTemperatureList();
                                for (int i = 0; i < temperatureBeen.size(); i++) {
                                    TemperatureBean bean = temperatureBeen.get(i);
                                    sendMessage(1,"体温数据:" + bean.getTemperature()
                                            + "\n时间:" + format.format(new Date(bean.getMeasure_time()))
                                            + "\n数据源:" + bean.getData_source()
                                    );
                                    setDivision();
                                }

                                //心率
                                ArrayList<HeartBean> heartBeen = allDeviceDataBean.getHeartList();
                                for (int i = 0; i < heartBeen.size(); i++) {
                                    HeartBean bean = heartBeen.get(i);
                                    sendMessage(1,"心率:" + bean.getHeart_rate()
                                            + "\n时间:" +format.format(new Date(bean.getMeasure_time()))
                                            + "\n数据源:" + bean.getData_source()
                                    );
                                    setDivision();
                                }

                                //血氧
                                ArrayList<Spo2Bean> spo2Been = allDeviceDataBean.getSpo2List();
                                for (int i = 0; i < spo2Been.size(); i++) {
                                    Spo2Bean bean = spo2Been.get(i);
                                    sendMessage(1,"心率:" + bean.getHeart_rate()
                                            + "\n血氧:" + bean.getBlood_oxygen()
                                            + "\n时间:" + format.format(new Date(bean.getMeasure_time())
                                            + "\n数据源:" + bean.getData_source())
                                    );
                                    setDivision();
                                }

                            }
                        }
                        sendMessage(2, list.get(position));

                    }

                    @Override
                    public void onError(int code, String msg) {
                        dialog.dismiss();
                        sendMessage(1, "获取" + list.get(position) + "数据失败 code：" + code + " msg:" + msg);
                        sendMessage(2, list.get(position));
                        setDivision();
                    }
                });
    }

    private void sendMessage(int what, Object msg) {
        Message message = new Message();
        message.what = what;
        message.obj = msg;
        if(what == 2){
            Bundle bundle = new Bundle();
            bundle.putString("title",msg+"");
            message.setData(bundle);
        }
        handler.sendMessage(message);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }

    private class ApiDataAdaopter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = LayoutInflater.from(GetApiDatasActivity.this).inflate(R.layout.api_item_list, null);
            TextView tv_item_name = (TextView) convertView.findViewById(R.id.tv_item_name);
            tv_item_name.setText(list.get(position));
            return convertView;
        }
    }

    //列表高度调整
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    private void setDivision(){
        sendMessage(1, "-------------------------------------------------------------------------");
//        sendMessage(1, "\n");
    }

}
