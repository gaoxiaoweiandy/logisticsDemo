package com.wang.logisticsprocessdemo;

public class StepData {
    private String context;
    private String mStepText;//物流各节点上的文本描述

    public String getContext() {
        return context;
    }
    public StepData setContext(String context) {
        this.context = context;
        return this;
    }
    public String getStepText() {
        return mStepText;
    }
    public StepData setStepText(String stepText) {
        this.mStepText = stepText;
        return this;
    }


}