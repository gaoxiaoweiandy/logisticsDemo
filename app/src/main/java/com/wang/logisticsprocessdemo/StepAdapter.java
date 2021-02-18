package com.wang.logisticsprocessdemo;

import java.util.List;

/**
 * xw.gao
 * 物流各阶段的节点个数与要显示的文本数据
 */
public interface StepAdapter {

    /**
     * 返回集合大小
     * @return
     */
    int getCount();

    /**
     * 适配数据的集合
     * @return
     */
    List<StepData> getData();

}
