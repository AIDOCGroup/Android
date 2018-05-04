package me.aidoc.client.mvp.health;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cn.miao.demo.R;
import cn.miao.lib.MiaoApplication;
import cn.miao.lib.listeners.MiaoElderDataLinstener;
import cn.miao.lib.listeners.MiaoElderLinstener;
import cn.miao.lib.model.DataBean;
import cn.miao.lib.model.ElderFamilyList;

public class FamilyReminderListActivity extends AppCompatActivity {
    private Handler handler = new Handler(Looper.getMainLooper());
    protected ListView lvFamilyReminder;
    private String deviceSn, deviceNo;

    private ElderFamilyList elderFamilyList;
    private FamilyReminderAdapter familyReminderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_family_reminder_list);
        initView();
        initData();
    }

    private void initData() {
        deviceSn = getIntent().getStringExtra("deviceSn");
        deviceNo = getIntent().getStringExtra("deviceNo");
        refurbish();

    }

    private void initView() {
        lvFamilyReminder = (ListView) findViewById(R.id.lv_family_reminder);
    }

    public void refurbish() {
        MiaoApplication.getMiaoHealthElderManager().getFamilyReminderList(deviceSn, deviceNo, new MiaoElderDataLinstener() {
            @Override
            public void onElderDataResponse(final int type, final DataBean elderDataBean) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (type == MiaoElderDataLinstener.FAMILY_REMINDER_LIST_DATA) {
                            elderFamilyList = (ElderFamilyList) elderDataBean;
                            if (elderFamilyList == null || elderFamilyList.getData().size() <= 0) {
                                Toast.makeText(FamilyReminderListActivity.this, "暂无数据", Toast.LENGTH_LONG).show();
                            } else {
                                if (familyReminderAdapter != null)
                                    familyReminderAdapter.notifyDataSetChanged();
                                else {
                                    familyReminderAdapter = new FamilyReminderAdapter();
                                    lvFamilyReminder.setAdapter(familyReminderAdapter);
                                    familyReminderAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                });

            }

            @Override
            public void onError(int typeid, int code, String msg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(FamilyReminderListActivity.this, "获取列表失败！", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private class FamilyReminderAdapter extends BaseAdapter {

        protected View rootView;
        protected TextView tvItemDate;
        protected TextView tvItemTime;
        protected TextView tvItemType;
        protected TextView tvItemReminder;
        protected TextView tvItemContent;
        protected Button btnUpdata;
        protected Button btnDelete;

        @Override
        public int getCount() {
            return elderFamilyList.getData().size();
        }

        @Override
        public Object getItem(int position) {
            return elderFamilyList.getData().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.elder_family_reminder_item_list, null);
            }
            initView(convertView);
            tvItemDate.setText(elderFamilyList.getData().get(position).getStart_at());
            tvItemTime.setText(elderFamilyList.getData().get(position).getStart_time());
            tvItemContent.setText(elderFamilyList.getData().get(position).getContent());
            int type = elderFamilyList.getData().get(position).getRemind_type();
            switch (type) {
                case 0:
                    tvItemType.setText("无");
                    break;
                case 1:
                    tvItemType.setText("睡觉");
                    break;
                case 2:
                    tvItemType.setText("起床");
                    break;
                case 3:
                    tvItemType.setText("活动");
                    break;
                case 4:
                    tvItemType.setText("休息");
                    break;
                case 5:
                    tvItemType.setText("吃药");
                    break;
            }
            int repeat = elderFamilyList.getData().get(position).getRepeat_type();
            switch (repeat) {
                case 0:
                    tvItemReminder.setText("不重复");
                    break;
                case 1:
                    tvItemReminder.setText("按天重复");
                    break;
                case 2:
                    tvItemReminder.setText("按周重复");
                    break;
                case 3:
                    tvItemReminder.setText("按月重复");
                    break;
                case 4:
                    tvItemReminder.setText("按年重复");
                    break;
            }
            btnUpdata.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(FamilyReminderListActivity.this, FamilyReminderActivity.class);
                    in.putExtra("deviceSn", deviceSn);
                    in.putExtra("deviceNo", deviceNo);
                    in.putExtra("remind_id", elderFamilyList.getData().get(position).getId());
                    in.putExtra("start_at", elderFamilyList.getData().get(position).getStart_at());
                    in.putExtra("start_time", elderFamilyList.getData().get(position).getStart_time());
                    in.putExtra("remind_type", elderFamilyList.getData().get(position).getRemind_type());
                    in.putExtra("repeat_type", elderFamilyList.getData().get(position).getRepeat_type());
                    in.putExtra("content", elderFamilyList.getData().get(position).getContent());
                    startActivityForResult(in, 1);
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MiaoApplication.getMiaoHealthElderManager().deleteFamilyRemind(deviceSn, deviceNo,
                            elderFamilyList.getData().get(position).getId(), new MiaoElderLinstener() {
                                @Override
                                public void onStatus(int typeid, int state) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            refurbish();
                                            Toast.makeText(FamilyReminderListActivity.this, "删除成功", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }

                                @Override
                                public void onError(int typeid, int code, String msg) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(FamilyReminderListActivity.this, "删除失败！", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            });
                }
            });
            return convertView;
        }

        private void initView(View rootView) {
            tvItemDate = (TextView) rootView.findViewById(R.id.tv_item_date);
            tvItemTime = (TextView) rootView.findViewById(R.id.tv_item_time);
            tvItemType = (TextView) rootView.findViewById(R.id.tv_item_type);
            tvItemReminder = (TextView) rootView.findViewById(R.id.tv_item_reminder);
            tvItemContent = (TextView) rootView.findViewById(R.id.tv_item_content);
            btnUpdata = (Button) rootView.findViewById(R.id.btn_updata);
            btnDelete = (Button) rootView.findViewById(R.id.btn_delete);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            refurbish();
        }
    }
}
