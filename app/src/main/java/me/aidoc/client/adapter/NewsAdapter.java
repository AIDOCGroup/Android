package me.aidoc.client.adapter;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import me.aidoc.client.entity.resp.NewsListResp;
import me.aidoc.client.util.ImageUtil;

public class NewsAdapter extends BaseQuickAdapter<NewsListResp.ListBean, BaseViewHolder> {
    public NewsAdapter() {
        super(me.aidoc.client.R.layout.item_discover_news, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsListResp.ListBean item) {
        ImageView imageView = helper.getView(me.aidoc.client.R.id.id_news_cover);
        ImageUtil.show(imageView,item.getCover());
        ((TextView) helper.getView(me.aidoc.client.R.id.tv_news_title)).setText(""+item.getTitle());
        ((TextView) helper.getView(me.aidoc.client.R.id.tv_news_date)).setText(""+item.getCreated_at());
        ((TextView) helper.getView(me.aidoc.client.R.id.tv_news_watch)).setText(""+item.getWatch_num());
        ((TextView) helper.getView(me.aidoc.client.R.id.tv_news_forward)).setText(""+item.getForward_num());

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
