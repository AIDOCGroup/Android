package me.aidoc.client.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.aidoc.client.R;
import me.aidoc.client.entity.resp.CaseCenterResp;

public class CaseListAdapter extends RecyclerView.Adapter<CaseListAdapter.Holder> {
    private Context mContext;
    private List<CaseCenterResp> mResIds;

    public CaseListAdapter(Context context, List<CaseCenterResp> respList) {
        mContext = context;
        mResIds = respList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.item_recycler, parent, false);
        return new Holder(root);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.tvReason.setText(mResIds.get(position).getVisitReason());
        holder.tvResult.setText(mResIds.get(position).getVisitResult());
        holder.tvVisitTime.setText("" + mResIds.get(position).getVisitDay());
        holder.tvDoctorName.setText(mResIds.get(position).getDoctorName());
    }

    @Override
    public int getItemCount() {
        return mResIds.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvReason;
        private TextView tvResult;
        private TextView tvVisitTime;
        private TextView tvDoctorName;

        Holder(View itemView) {
            super(itemView);

            View main = itemView.findViewById(R.id.main);
            main.setOnClickListener(this);

            View delete = itemView.findViewById(R.id.delete);
            delete.setOnClickListener(this);

            // 设置几个TextView
            tvReason = itemView.findViewById(R.id.tvReason);
            tvResult = itemView.findViewById(R.id.tvResult);
            tvVisitTime = itemView.findViewById(R.id.tvVisitTime);
            tvDoctorName = itemView.findViewById(R.id.tvDoctorName);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main:
                    if (onClickListener != null)
                        onClickListener.onMainClick(getAdapterPosition());
                    break;

                case R.id.delete:
                    int pos = getAdapterPosition();
                    mResIds.remove(pos);
                    notifyItemRemoved(pos);
                    if (onClickListener != null)
                        onClickListener.onDeleteClick(getAdapterPosition());
                    break;
            }
        }
    }

    public interface OnClickListener {
        void onMainClick(int position);

        void onDeleteClick(int position);
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener listenner) {
        onClickListener = listenner;
    }


}