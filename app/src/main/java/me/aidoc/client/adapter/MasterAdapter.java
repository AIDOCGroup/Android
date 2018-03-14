package me.aidoc.client.adapter;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import me.aidoc.client.R;
import me.aidoc.client.entity.resp.MasterResp;
import me.aidoc.client.util.ImageUtil;

public class MasterAdapter extends BaseQuickAdapter<MasterResp.DataBean, BaseViewHolder> {
    public MasterAdapter() {
        super(R.layout.item_discover_master, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, MasterResp.DataBean item) {

        ImageView imageView = helper.getView(R.id.ivAvatar);
        if(item.getAvatar()!=null){
            ImageUtil.showCircle(imageView,item.getAvatar());
        }
        ImageView ivPosition = helper.getView(R.id.ivPosition);
        TextView tvPosition = helper.getView(R.id.tvPosition);


        TextView tvName = helper.getView(R.id.tvName);
        TextView tvSign = helper.getView(R.id.tvSign);
        TextView tvStep = helper.getView(R.id.tvStep);
        tvName.setText(item.getNickname());
        tvSign.setText(item.getSignature());
        tvStep.setText(""+item.getDataNumber());
        if(helper.getPosition()<3){
            ivPosition.setVisibility(View.VISIBLE);
            tvPosition.setVisibility(View.GONE);
            switch (helper.getPosition()){
                case 0:
                    ivPosition.setImageResource(R.drawable.icon_master_first);
                    break;
                case 1:
                    ivPosition.setImageResource(R.drawable.icon_master_second);
                    break;
                case 2:
                    ivPosition.setImageResource(R.drawable.icon_master_third);
                    break;
            }
        }else {
            ivPosition.setVisibility(View.GONE);
            tvPosition.setVisibility(View.VISIBLE);
            tvPosition.setText(""+(helper.getPosition()+1));
        }


//        ((TextView) helper.getView(me.aidoc.tianyi.R.id.tv_news_title)).setText(""+item.getTitle());
//        ((TextView) helper.getView(me.aidoc.tianyi.R.id.tv_news_date)).setText(""+item.getCreated_at());
//        ((TextView) helper.getView(me.aidoc.tianyi.R.id.tv_news_watch)).setText(""+item.getWatch_num());
//        ((TextView) helper.getView(me.aidoc.tianyi.R.id.tv_news_forward)).setText(""+item.getForward_num());

    }

    ClickableSpan clickableSpan = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
//            ToastUtils.showShortToast("事件触发了 landscapes and nedes");
        }

        @Override
        public void updateDrawState(TextPaint ds) {
//            ds.setColor(Utils.getContext().getResources().getColor(R.color.clickspan_color));
//            ds.setUnderlineText(true);
        }
    };


}
