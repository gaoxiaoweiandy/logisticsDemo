package com.wang.logisticsprocessdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import java.util.List;

/**
 * User: 734238158@qq.com
 * Date: 2018-01-27
 */
public class LogisticsView3 extends View {
    float width;
    float height = 6;
    float nodeRadius;
    Paint paint;
    Context context;

    /**
     * 节点间隔
     */
    int nodeDistance;

    /**
     * 边距
     */
    int left = 40;
    int top = 30;

    int dWidth;
    int dHeight;


    public LogisticsView3(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public LogisticsView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LogisticsView);
        width = typedArray.getDimension(R.styleable.LogisticsView_width, 5);
        height = typedArray.getDimension(R.styleable.LogisticsView_height, 5);
        nodeRadius = typedArray.getDimension(R.styleable.LogisticsView_nodeRadius, 6);
        init();
    }

    public LogisticsView3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorAccent));
        paint.setAntiAlias(true);
        nodeDistance = Dp2pxUtils.dip2px(context, 80);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        dWidth = wm.getDefaultDisplay().getWidth();
        dHeight = wm.getDefaultDisplay().getHeight();
    }
    LogisticsAdapter logisticsAdapter;

    /**
     * 设置适配数据
     */
    public void setlogisticsAdapter(LogisticsAdapter logisticsAdapter) {
        this.logisticsAdapter = logisticsAdapter;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (logisticsAdapter == null || logisticsAdapter.getCount() == 0)
            return;
        List data = logisticsAdapter.getData();

        for (int i = 0; i < logisticsAdapter.getCount(); i++) {
            if (i != (logisticsAdapter.getCount() - 1)) {

                //canvas.drawRect(left, i * nodeDistance + top + Dp2pxUtils.dip2px(context, 10f), width + left, (i + 1) * nodeDistance + top - Dp2pxUtils.dip2px(context, 10f), paint);
                canvas.drawRect(i * nodeDistance + left + Dp2pxUtils.dip2px(context, 10f),
                        top,
                        (i + 1) * nodeDistance + left - Dp2pxUtils.dip2px(context, 10f), top+height, paint);
            }
            if (i == 0) {
                Paint mPaint = new Paint();
                mPaint.setAntiAlias(true);
                mPaint.setColor(getResources().getColor(R.color.colorPrimaryDark));
                //画文字
                mPaint.setTextSize(Dp2pxUtils.dip2px(context, 8));
               //gxw- canvas.drawText(((LogisticsData) data.get(i)).getTime() + "", left * 2 + nodeRadius * 2 + 10, (i + 1) * nodeDistance + top - 20, mPaint);
                canvas.drawText(((LogisticsData) data.get(i)).getTime() + "",  (i + 1) * nodeDistance + left - 20, top  - nodeRadius * 2 - 10, mPaint);

                //画圆
               //gxw- canvas.drawCircle(width / 2 + top, i * nodeDistance + top, nodeRadius + 2, mPaint);
                canvas.drawCircle(i * nodeDistance + left, height/2+top, nodeRadius + 2, mPaint);//gxw+
                mPaint.setStyle(Paint.Style.STROKE);//设置为空心
                mPaint.setStrokeWidth(8);//空心宽度
                mPaint.setAlpha(88);
               //gxw- canvas.drawCircle(width / 2 + left, i * nodeDistance + top, nodeRadius + 4, mPaint);
                canvas.drawCircle(i * nodeDistance + left, height / 2 + top, nodeRadius + 4, mPaint);//gxw+
            } else {
                paint.setColor(getResources().getColor(R.color.colorAccent));
               //gxw- canvas.drawCircle(width / 2 + left, i * nodeDistance + top, nodeRadius, paint);
                canvas.drawCircle(i * nodeDistance + left, height / 2 + top, nodeRadius, paint);
             //gxw-   canvas.drawLine(left * 2 + nodeRadius * 2, i * nodeDistance + top, dWidth, i * nodeDistance + top, paint); //画线
                //画文字
                paint.setTextSize(Dp2pxUtils.dip2px(context, 8));
                //gxw-canvas.drawText(((LogisticsData) data.get(i)).getTime() + "", left * 2 + nodeRadius * 2 + 10, (i + 1) * nodeDistance + top - 20, paint);
                canvas.drawText(((LogisticsData) data.get(i)).getTime() + "",  (i + 1) * nodeDistance + left - 20, top  - nodeRadius * 2 - 10, paint);//gxw+
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (logisticsAdapter == null || logisticsAdapter.getCount() == 0)
            return;
        setMeasuredDimension(widthMeasureSpec, logisticsAdapter.getCount() * nodeDistance + top);
    }
}
