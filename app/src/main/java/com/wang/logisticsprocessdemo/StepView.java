package com.wang.logisticsprocessdemo;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import java.util.List;

public class StepView extends View {
    private static final String TAG = "StepView extends";
    Paint mPaint; // 绘制除了被选中的节点，其余节点的圆，以及线段。
    Context mContext;
    StepAdapter mStepAdapter; //回调函数，获取节点的大小
    float mHeight;  //线段的高度，即粗细
    float mNodeRadius;  //圆形节点中 最内（最小）的那个圆的半径
    int mNodeDistance;//圆形节点间隔距离
    int mLeft;   //第1个节点的圆心x坐标, 必须得有，且必须大于某个值，在此为20dp，否则绘制文字时有可能溢出左边界
    int mTop;   //线段距离父容器的上边距，必须有，且必须大于某个值，在此为100dp，否则绘制文字时有可能溢出上边界
    int mSelectPosition = 0; //默认第一个节点被选中
    int mTextY; //文本与线段上边缘的margin

    public StepView(Context context) {
        super(context);
        this.mContext = context;
    }
    public StepView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }
    public StepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.LogisticsView);
        //线段的高度，读取XML属性配置值，将属性值当作dp转为px
        mHeight = typedArray.getInt(R.styleable.LogisticsView_line_height, 5);
        //高度最高为5dp
        if(mHeight > 5){
            mHeight = 5;
        }
        mHeight = Dp2pxUtils.dip2px(mContext, mHeight);

        //圆的半径
        mNodeRadius = typedArray.getInt(R.styleable.LogisticsView_nodeRadius, 5);
        //半径最大为5dp
        if(mNodeRadius > 5){
            mNodeRadius = 5;
        }
        mNodeRadius = Dp2pxUtils.dip2px(mContext, mNodeRadius);

        //节点间线段的长度
        mNodeDistance = typedArray.getInt(R.styleable.LogisticsView_nodeDistance, 60);
        mNodeDistance = Dp2pxUtils.dip2px(mContext, mNodeDistance);

        //边距，left是第一个节点的圆心x坐标，top是线段上边缘距离父容器的顶部边距
        mLeft = Dp2pxUtils.dip2px(mContext, 20);
        mTop = Dp2pxUtils.dip2px(mContext, 30);

        //文本标注距离 “线段矩形”上边缘的距离
        mTextY = (int) ((mTop - mNodeRadius * 2) - Dp2pxUtils.dip2px(mContext, 6));
        //创建画笔
        initPaint();
    }

    /**
     * 初始化画笔
     */
    public void initPaint(){
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public void setSelectPosition(int selectPosition) {
        this.mSelectPosition = selectPosition;
        invalidate();
    }

    /**
     * 设置适配数据
     */
    public void setStepAdapter(StepAdapter mstepAdapter) {
        this.mStepAdapter = mstepAdapter;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mStepAdapter == null || mStepAdapter.getCount() == 0){
            return;
        }
        List data = mStepAdapter.getData();
        for (int i = 0; i < mStepAdapter.getCount(); i++) {
            //绘制线段，不会绘制最后1条线段，例如5个节点，只需绘制4条线段
            if (i != (mStepAdapter.getCount() - 1)) {
                if (i < mSelectPosition) { //绘制线段的颜色，被选中的节点之前的所有线段为蓝色，后面为灰色
                    mPaint.setColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    mPaint.setColor(getResources().getColor(R.color.describe_color));
                }
                mPaint.setAlpha(255);
                canvas.drawRect(i * mNodeDistance + mLeft, mTop, (i + 1) * mNodeDistance + mLeft, mTop + mHeight, mPaint);
            }

            //绘制圆形节点及之上的标注文字
            if (i <= mSelectPosition) {
                //绘制圆形节点及文字标注的颜色，第1个节点一直到被选中的节点 均为蓝色，包括它们的文字标注也为蓝色
                mPaint.setColor(getResources().getColor(R.color.colorPrimary));
            } else {
                mPaint.setColor(getResources().getColor(R.color.describe_color));
            }
            //绘制标注文字
            mPaint.setAlpha(255);
            mPaint.setTextSize(Dp2pxUtils.dip2px(mContext, 14));
            String text = ((StepData) data.get(i)).getStepText();
            int x = (int) ((i * mNodeDistance + mLeft) - (mPaint.measureText(text) / 2));
            canvas.drawText(((StepData) data.get(i)).getStepText() + "", x,mTextY , mPaint);

            //每1个节点由3个同心圆组成，每个圆的透明度不同，最初为纯蓝色
            //第1个圆,不透明
            canvas.drawCircle(i * mNodeDistance + mLeft, mHeight / 2 + mTop, mNodeRadius, mPaint);
            //第2个圆，改变透明度
            mPaint.setAlpha(160);
            canvas.drawCircle(i * mNodeDistance + mLeft, mHeight / 2 + mTop, mNodeRadius + 8, mPaint);
            //第3个圆，改变透明度
            mPaint.setAlpha(100);
            canvas.drawCircle(i * mNodeDistance + mLeft, mHeight / 2 + mTop, mNodeRadius + 16, mPaint);
        }
    }

    /**
     * 为这个自定义View确定宽高
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mStepAdapter == null || mStepAdapter.getCount() == 0)
            return;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        printMode(widthMode,1);
        printMode(heightMode,2);


        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.i(TAG,"heightSize="+heightSize+",type=2");
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        Log.i(TAG,"widthSize="+widthSize+",type=1");
        setMeasuredDimension(mStepAdapter.getCount() * mNodeDistance + mLeft, heightMeasureSpec);
    }

    /**
     *
     * @param Mode
     * @param type:1宽度
     */
    public void printMode(int Mode,int type)
    {
        switch (Mode){
            case MeasureSpec.EXACTLY:
                Log.i(TAG,"Mode= EXACTLY,type="+type);
                break;
            case MeasureSpec.AT_MOST:
                Log.i(TAG,"Mode= AT_MOST,type="+type);
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.i(TAG,"Mode= UNSPECIFIED,type="+type);
                break;
        }

    }

    /**
     * 测量文字的高度
     * --经测试后发现，采用另一种带Rect的方式，获得的数据并不准确。
     * 特别是在一些对文字有一些倾斜处理的时候
     * @param paint
     * @return
     */
    public static float measureTextHeight(Paint paint){
        float height = 0f;
        if(null == paint){
            return height;
        }
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        height = fontMetrics.descent - fontMetrics.ascent;
        return height;
    }
}
