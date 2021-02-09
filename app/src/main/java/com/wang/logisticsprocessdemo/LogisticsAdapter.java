package com.wang.logisticsprocessdemo;

import java.util.List;

/**
 * User: 734238158@qq.com
 * Date: 2018-01-27
 */
public interface LogisticsAdapter {

    /**
     * 返回集合大小
     * @return
     */
    int getCount();

    /**
     * 适配数据的集合
     * @return
     */
    List<LogisticsData> getData();

}
