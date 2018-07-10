package me.aidoc.client.custom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.inuker.bluetooth.R;

/**
 * Created by Hqs on 2018/7/7
 * 协议列表的Adapter，点击按钮发送
 */
public class ProtocolAdapter extends RecyclerView.Adapter<ProtocolAdapter.ProtocolViewHolder> {

    private Context mContext ;
    private String[] methods ;
    public interface OnButtonClickListener{
        void onButtonClick(View view, int position);
    }
    public OnButtonClickListener mOnButtonClickListener ;

    public ProtocolAdapter(Context context, String[] methods){
        mContext = context ;
        this.methods = methods ;
    }

    @Override
    public ProtocolViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_protocol,parent,false);
        ProtocolViewHolder viewHolder = new ProtocolViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProtocolViewHolder holder, final int position) {
        holder.tvProtocol.setText(methods[position]+"");
//        Log.e("adapter",methods.get(position));
        holder.btnSendProtocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnButtonClickListener.onButtonClick(v,position);
            }
        });
    }


    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener){
        this.mOnButtonClickListener = onButtonClickListener ;
    }

    @Override
    public int getItemCount() {
        return methods.length;
    }

    class ProtocolViewHolder extends RecyclerView.ViewHolder{

        public final TextView tvProtocol;
        public final Button btnSendProtocol;

        public ProtocolViewHolder(View itemView) {
            super(itemView);
            tvProtocol = (TextView) itemView.findViewById(R.id.tv_protocol);
            btnSendProtocol = (Button) itemView.findViewById(R.id.btn_send_protocol);
        }

    }
}
