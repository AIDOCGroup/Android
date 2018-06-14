package me.aidoc.client.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hd.hedong.myapplication.R;
import com.hd.hedong.myapplication.bean.MostSeriesBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hedong on 16-11-9.
 */
public class MostRightAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<MostSeriesBean> mList = new ArrayList<>();
    private MyItemClickListener mItemClickListener;

    public MostRightAdapter(Context context) {
        mContext = context;
    }

    public void setList(List<MostSeriesBean> list) {
        if (list!=null){
            mList = list;
        }

    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.series_list_item, parent, false);
        return new ItemViewHolder(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder item = (ItemViewHolder) holder;
        final MostSeriesBean bean = mList.get(position);
        item.mostRightName.setText(bean.getName());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public interface MyItemClickListener {
        void onItemClick(View view, int postion);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MyItemClickListener mListener;
        @BindView(R.id.series_name)
        TextView mostRightName;

        public ItemViewHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            this.mListener = listener;
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getLayoutPosition());
            }
        }
    }
}
