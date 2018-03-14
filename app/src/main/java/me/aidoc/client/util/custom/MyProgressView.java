package me.aidoc.client.util.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import me.aidoc.client.util.DensityUtil;

public class MyProgressView extends View {
    private static final String TAG = "MyProgressView";
    private Paint textPaint;//绘制文字的画笔
    private Paint anglePaint;//这是画弧的画笔
    private Paint bottomTextPaint;
    private int max = 100;
    private int progress;
    private float arcAngle = 360 * 1f;//
    private Context mContext;

    public MyProgressView(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public MyProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
    }

    public MyProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initPainters();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(DensityUtil.dp2px(mContext, 5), DensityUtil.dp2px(mContext, 5),
                DensityUtil.dp2px(mContext, 125), DensityUtil.dp2px(mContext, 125));
        float startAngle = 270f;
        float sweepAngle = progress / (float) max * arcAngle;
        anglePaint.setColor(Color.parseColor("#ECECEC"));
        canvas.drawArc(rectF, startAngle, arcAngle, false, anglePaint);
        anglePaint.setColor(Color.parseColor("#019FE0"));
        canvas.drawArc(rectF, startAngle, sweepAngle, false, anglePaint);

        float textHeight = textPaint.descent() + textPaint.ascent();
        String text = progress + "%";
        textPaint.setTextSize(DensityUtil.dp2px(mContext, 24));
        // 文字X七点
//        canvas.drawText(text, 100 + 150 - textPaint.measureText(text) / 2, 100 + 150 - textHeight / 2, textPaint);
        canvas.drawText(text, DensityUtil.dp2px(mContext, 65) - textPaint.measureText(text) / 2, DensityUtil.dp2px(mContext, 48) - textHeight / 2, textPaint);
//        textPaint.setTextSize(dIP);
//        String suffixText = "%";
//        canvas.drawText(suffixTe、xt, 100 + 150 + textPaint.measureText(text) / 2 + 12, 100 + 150 + textHeight, textPaint);
//        canvas.drawText(suffixText, DensityUtil.dip2px(mContext, 60) + textPaint.measureText(text) / 2 + DensityUtil.dip2px(mContext, 10), DensityUtil.dip2px(mContext, 60) - textHeight / 2 + textHeight, textPaint);

        //
//        String bottomText = "memory";
//        float arcBottomHeight = 150 * (float) (1 - Math.cos(36 / 180 * Math.PI));//文字的高度
//        canvas.drawText(bottomText,100+150-bottomTextPaint.measureText(bottomText)/2,400-arcBottomHeight,bottomTextPaint);
    }

    /**
     * 设置进度值,实时更新进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        this.progress = progress;
        if (this.progress > max) {
            this.progress %= max;
            return;
        }
        invalidate();
    }

    /**
     * 获取当前进度值
     *
     * @return
     */
    public int getProgress() {
        return progress;
    }

    private void initPainters() {

        bottomTextPaint = new Paint();
        bottomTextPaint.setColor(Color.BLUE);
        bottomTextPaint.setTextSize(40);
        bottomTextPaint.setStrokeWidth(8);

        textPaint = new Paint();
        textPaint.setColor(Color.parseColor("#019FE0"));
        textPaint.setTextSize(24);//设置文字的大小
        textPaint.setAntiAlias(true);
        textPaint.setFakeBoldText(true);// 粗体
        anglePaint = new Paint();
        anglePaint.setStrokeWidth(DensityUtil.dp2px(mContext, 10));//20
        anglePaint.setColor(Color.parseColor("#019FE0"));
        anglePaint.setStyle(Paint.Style.STROKE);//设置成空心
        anglePaint.setAntiAlias(true);
        anglePaint.setStrokeCap(Paint.Cap.ROUND);
    }
}
