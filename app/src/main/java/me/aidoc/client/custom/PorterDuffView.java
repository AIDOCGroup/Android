package me.aidoc.client.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

public class PorterDuffView extends View {

    // 支持绘制任何东西
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap mBitmap;
    private Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);

    public PorterDuffView(Context context) {
        super(context);
    }

    public PorterDuffView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PorterDuffView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (mBitmap != null) {
            final int width = right - left;
            final int height = bottom - top;
            final int minDimension = Math.min(width, height);

            final int bitmapWidth = mBitmap.getWidth();
            if (minDimension != bitmapWidth) {
                mBitmap = null;
            }
        }
    }

    /**
     * 设置新的绘图模式---更新视图
     *
     * @param mode
     */
    public void setPorterDuffMode(PorterDuff.Mode mode) {
        this.mXfermode = new PorterDuffXfermode(mode);
        mBitmap = null;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap == null) {
            createBitmap();
        }
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
    }

    // 绘制结束之后mBitmap上就有了像素信息
    private void createBitmap() {
        final int width = getWidth();
        final int height = getHeight();
        final int minDimension = Math.min(width, height);
        final Rect rect = new Rect();
        rect.right = minDimension - 1;
        rect.bottom = rect.right;
        mBitmap = Bitmap.createBitmap(minDimension, minDimension,
                Bitmap.Config.ARGB_8888);

        Bitmap src = BitmapFactory.decodeResource(getResources(),
                R.drawable.skia_dst);
        final Canvas canvas = new Canvas(mBitmap);
        canvas.drawBitmap(src, null, rect, mPaint);// 绘制到目标矩形上

        src = BitmapFactory.decodeResource(getResources(), R.drawable.skia_src);

        mPaint.setXfermode(mXfermode);
        canvas.drawBitmap(src, null, rect, mPaint);

        mPaint.setXfermode(null);

    }

}