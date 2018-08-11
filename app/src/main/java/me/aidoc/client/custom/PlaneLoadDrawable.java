package me.aidoc.client.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 */

public class PlaneLoadDrawable extends PlaneDrawable {

    public PlaneLoadDrawable(Context context, PullRefreshLayout layout) {
        super(context, layout);
    }

    @Override
    public void setPercent(float percent) {
        mPercent = percent;
        int centerX = getBounds().centerX();
        int bottom = getBounds().bottom;
        rectF.left = centerX - drawableMinddleWidth * mPercent;
        rectF.right = centerX + drawableMinddleWidth * mPercent;
        rectF.top = bottom;
        rectF.bottom = bottom + drawableMinddleWidth * 2 * mPercent;
    }

    @Override
    public void draw(Canvas canvas) {
        int num = (int) (System.currentTimeMillis() / 50 % 11);
        final int saveCount = canvas.save();
        canvas.translate(0, mOffset);
        Bitmap bitmap = bitmaps.get(num);
        canvas.drawBitmap(bitmap, null, rectF, null);
        canvas.restoreToCount(saveCount);
    }
}
