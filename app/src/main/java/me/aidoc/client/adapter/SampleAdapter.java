package me.aidoc.client.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.aidoc.client.entity.resp.NewsListResp;
import me.aidoc.client.util.ImageUtil;


public class SampleAdapter extends BaseAdapter {

    private List<NewsListResp.ListBean> mDataSet;
    private Context context;

    public SampleAdapter(Context context,List<NewsListResp.ListBean> dataSet) {
        this.mDataSet = dataSet;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mDataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, me.aidoc.client.R.layout.item_discover_news, null);
        ImageView imageView =  convertView.findViewById(me.aidoc.client.R.id.id_news_cover);
        ImageUtil.show(imageView,mDataSet.get(position).getCover());
        TextView tvTitle =  convertView.findViewById(me.aidoc.client.R.id.tv_news_title);
        tvTitle.setText(mDataSet.get(position).getTitle());
        TextView tvNewData =  convertView.findViewById(me.aidoc.client.R.id.tv_news_date);
        tvNewData.setText(""+mDataSet.get(position).getCreated_at());
        TextView tvWatch =  convertView.findViewById(me.aidoc.client.R.id.tv_news_watch);
        tvWatch.setText(""+mDataSet.get(position).getWatch_num());
        TextView tvForward =  convertView.findViewById(me.aidoc.client.R.id.tv_news_forward);
        tvForward.setText(""+mDataSet.get(position).getForward_num());
        return convertView;
    }

}
