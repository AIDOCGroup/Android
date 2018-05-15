package me.aidoc.client.util.custom.adselector;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

import me.aidoc.client.R;
import me.aidoc.client.entity.bean.AreaData;
import me.aidoc.client.util.custom.adselector.utils.Dev;


/**
 * Created by smartTop on 2016/10/19.
 */

public class BottomDialog extends Dialog {
    private AddressSelector selector;

    public BottomDialog(Context context, List<AreaData> mSelectAddressEntity) {
        super(context, R.style.bottom_dialog);
        init(context, mSelectAddressEntity);
    }

    public BottomDialog(Context context, int themeResId, List<AreaData> mSelectAddressEntity) {
        super(context, themeResId);
        init(context, mSelectAddressEntity);
    }

    public BottomDialog(Context context, boolean cancelable, OnCancelListener cancelListener, List<AreaData> mSelectAddressEntity) {
        super(context, cancelable, cancelListener);
        init(context, mSelectAddressEntity);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context, List<AreaData> mSelectAddressEntity) {
        selector = new AddressSelector(context, mSelectAddressEntity);
        setContentView(selector.getView());

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = Dev.dp2px(context, 300);
        window.setAttributes(params);

        window.setGravity(Gravity.BOTTOM);
    }

    public void setOnAddressSelectedListener(OnAddressSelectedListener listener) {
        this.selector.setOnAddressSelectedListener(listener);
    }

    public static BottomDialog show(Context context, List<AreaData> mSelectAddressEntity) {
        return show(context, null, mSelectAddressEntity);
    }

    public static BottomDialog show(Context context, OnAddressSelectedListener listener, List<AreaData> mSelectAddressEntity) {
        BottomDialog dialog = new BottomDialog(context, R.style.bottom_dialog, mSelectAddressEntity);
        dialog.selector.setOnAddressSelectedListener(listener);
        dialog.show();
        return dialog;
    }

    public void setDialogDismisListener(AddressSelector.OnDialogCloseListener listener) {
        this.selector.setOnDialogCloseListener(listener);
    }

    /**
     * 设置字体选中的颜色
     */
    public void setTextSelectedColor(int selectedColor) {
        this.selector.setTextSelectedColor(selectedColor);
    }

    /**
     * 设置字体没有选中的颜色
     */
    public void setTextUnSelectedColor(int unSelectedColor) {
        this.selector.setTextUnSelectedColor(unSelectedColor);
    }

    /**
     * 设置字体的大小
     */
    public void setTextSize(float dp) {
        this.selector.setTextSize(dp);
    }

    /**
     * 设置字体的背景
     */
    public void setBackgroundColor(int colorId) {
        this.selector.setBackgroundColor(colorId);
    }

    /**
     * 设置指示器的背景
     */
    public void setIndicatorBackgroundColor(int colorId) {
        this.selector.setIndicatorBackgroundColor(colorId);
    }

    /**
     * 设置指示器的背景
     */
    public void setIndicatorBackgroundColor(String color) {
        this.selector.setIndicatorBackgroundColor(color);
    }

    public void setProgressVis(boolean isVis) {
        this.selector.setProgressBarVis(isVis);
    }

    public void retrieveCitiesWith(List<AreaData> mData, int position) {
        this.selector.retrieveCitiesWith(mData, position);
    }

    public void retrieveCountiesWith(List<AreaData> mData, int position){
        this.selector.retrieveCountiesWith(mData, position);
    }

}
