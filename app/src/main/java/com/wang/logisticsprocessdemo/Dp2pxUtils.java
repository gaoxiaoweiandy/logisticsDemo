package com.wang.logisticsprocessdemo;

import android.content.Context;

/**
 * Created by Administrator on 2018/1/27 0027.
 */

public class Dp2pxUtils {
    /**
     * dp转成为 px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 10.5f);
    }
}
