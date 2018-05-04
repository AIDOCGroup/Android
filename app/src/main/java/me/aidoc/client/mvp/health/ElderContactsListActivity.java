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
import cn.miao.lib.model.ElderContactsListBean;

public class ElderContactsListActivity extends AppCompatActivity {

    protected ListView listElderContacts;
    protected ElderContactsListBean elderContactsListBean;
    protected ElderAdapter elderAdapter;
    private String deviceSn, deviceNo;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_elder_contacts_list);
        initView();

        initData();
    }

    private void initView() {
        listElderContacts = (ListView) findViewById(R.id.list_elder_contacts);
    }

    private void initData() {
        deviceSn = getIntent().getStringExtra("deviceSn");
        deviceNo = getIntent().getStringExtra("deviceNo");
        refurbish();

    }

    public void refurbish() {
        MiaoApplication.getMiaoHealthElderManager().getEdlerContacts(deviceSn, deviceNo, new MiaoElderDataLinstener() {
            @Override
            public void onElderDataResponse(final int type, final DataBean elderDataBean) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (type == MiaoElderLinstener.GET_ELDER_CONTACTS) {
                            elderContactsListBean = (ElderContactsListBean) elderDataBean;
                            if (elderContactsListBean == null || elderContactsListBean.getElderContactsBeen().size() <= 0) {
                                Toast.makeText(ElderContactsListActivity.this, "暂无数据", Toast.LENGTH_LONG).show();
                            } else {
                                if (elderAdapter != null)
                                    elderAdapter.notifyDataSetChanged();
                                else {
                                    elderAdapter = new ElderAdapter();
                                    listElderContacts.setAdapter(elderAdapter);
                                    elderAdapter.notifyDataSetChanged();
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
                        Toast.makeText(ElderContactsListActivity.this, "获取列表失败！", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }


    private class ElderAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return elderContactsListBean.getElderContactsBeen().size();
        }

        @Override
        public Object getItem(int position) {
            return elderContactsListBean.getElderContactsBeen().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.elder_item_list, null);
            }
            TextView tvItemName = (TextView) convertView.findViewById(R.id.tv_item_name);
            TextView tvItemDesc = (TextView) convertView.findViewById(R.id.tv_item_desc);
            Button btnUpdata = (Button) convertView.findViewById(R.id.btn_updata);
            Button btnDelete = (Button) convertView.findViewById(R.id.btn_delete);
            tvItemName.setText(elderContactsListBean.getElderContactsBeen().get(position).getName());
            tvItemDesc.setText(elderContactsListBean.getElderContactsBeen().get(position).getMobile());
            btnUpdata.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(ElderContactsListActivity.this, ElderContactActivity.class);
                    in.putExtra("deviceSn", deviceSn);
                    in.putExtra("deviceNo", deviceNo);
                    in.putExtra("contactId", elderContactsListBean.getElderContactsBeen().get(position).getId());
                    in.putExtra("contactName", elderContactsListBean.getElderContactsBeen().get(position).getName());
                    in.putExtra("contactMobile", elderContactsListBean.getElderContactsBeen().get(position).getMobile());
                    startActivityForResult(in, 1);
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MiaoApplication.getMiaoHealthElderManager().deleteEdlerContact(deviceSn, deviceNo, elderContactsListBean.getElderContactsBeen().get(position).getId(), new MiaoElderLinstener() {
                        @Override
                        public void onStatus(int typeid, int state) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    refurbish();
                                    Toast.makeText(ElderContactsListActivity.this, "删除成功", Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onError(int typeid, int code, String msg) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ElderContactsListActivity.this, "删除失败！", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                }
            });
            return convertView;
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
