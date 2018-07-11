package me.aidoc.client.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.LinearLayout;

public class BezierLayout extends LinearLayout {

    private int bottomPadding = 60;

    private static final String TAG = "BezierLayout";

    private Path path;

    private Paint paint;

    private int mWidth;

    private int mHeight;

    private PointF startPoint;

    private PointF endPoint;

    public BezierLayout(Context context) {
        super(context);
        init();
    }

    public BezierLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BezierLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

        path = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        Log.d(TAG, ": mwidth = " + mWidth);

        startPoint = new PointF(0, mHeight - dp2Px(bottomPadding));
        endPoint = new PointF(mWidth, mHeight - dp2Px(bottomPadding));

//        path.moveTo(0, mHeight - dp2Px(bottomPadding));
//        path.lineTo(0, mHeight);
//        path.lineTo(mWidth, mHeight);
//        path.lineTo(mWidth, mHeight - dp2Px(bottomPadding));
//        // 贝塞尔曲线
//        path.quadTo(mWidth / 2f, mHeight, 0, mHeight - dp2Px(bottomPadding));
//        path.close();




        int width=mWidth;
        int height=mHeight;
        int orignalHeight=bottomPadding;
        path.moveTo(0, orignalHeight);
        path.cubicTo(0, orignalHeight, width / 2, orignalHeight + 2.2f * (height - orignalHeight), width, orignalHeight);
        path.lineTo(width, height);
        path.lineTo(0, height);
        path.close();


//        mClipPath.rewind();
//        mClipPath.moveTo(0, orignalHeight);
//        mClipPath.cubicTo(0, orignalHeight, width / 2, orignalHeight + 2.2f * (height - orignalHeight), width, orignalHeight);
//        mClipPath.lineTo(width, height);
//        mClipPath.lineTo(0, height);
//        mClipPath.close();



    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);


    }

    private int dp2Px(int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
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