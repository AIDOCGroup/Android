package me.aidoc.client.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hd.hedong.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hedong on 16-11-9.
 */
public class MostLeftAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private List<String> mList = new ArrayList<>();
    private MyItemClickListener mItemClickListener;
    private int mPosition;
    private static int TYPE_NORMAL = 101;
    private static int TYPE_SELECT = 102;

    public MostLeftAdapter(Context context) {
        mContext = context;
        mPosition = 0;
    }

    public void setList(List<String> list) {
        mList = list;
    }

    public List<String> getList() {
        return mList;
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //根据type显示布局，选中与未选中，当然你可以自行修改
        if (viewType == TYPE_SELECT) {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.most_left_list_item, parent, false);
            return new ItemViewSelectHolder(view, mItemClickListener);
        } else {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.most_left_list_item, parent, false);
            return new ItemViewHolder(view, mItemClickListener);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder item = (ItemViewHolder) holder;
        item.mostLeftText.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mPosition) {
            return TYPE_SELECT;
        } else {
            return TYPE_NORMAL;
        }
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public interface MyItemClickListener {
        void onItemClick(View view, int postion);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MyItemClickListener mListener;
        @BindView(R.id.most_left_text)
        TextView mostLeftText;

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

    private class ItemViewSelectHolder extends ItemViewHolder {
        public ItemViewSelectHolder(View view, MyItemClickListener listener) {
            super(view, listener);
            itemView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.most_left_select_bg));
            mostLeftText.setTextColor(mContext.getResources().getColor(R.color.red_pre));
        }
    }
}
