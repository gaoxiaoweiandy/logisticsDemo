package com.wang.logisticsprocessdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;


import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LogisticsView3 logisticsView;
    private List<LogisticsData> dataList = new ArrayList<>();
    private HorizontalScrollView horizontal_scroll_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logisticsView = findViewById(R.id.logisticsView);
         horizontal_scroll_view = findViewById(R.id.horizontal_scroll_view);


        try {
            intData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        logisticsView.setlogisticsAdapter(new LogisticsAdapter() {
            @Override
            public int getCount() {
                return dataList.size();
            }

            @Override
            public List<LogisticsData> getData() {
                return dataList;
            }
        });

    }

    private void intData() throws JSONException {
        String data = "[{\"context\":\"[苏州市] [苏州横泾]的派件已签收 感谢使用中通快递,期待再次为您服务!\",\"ftime\":\"2017-07-29 11:46:20\",\"time\":\"2017-07-29 11:46:20\"},{\"context\":\"[苏州市] [苏州横泾]的武坤正在第1次派件 电话:188888888 请保持电话畅通、耐心等待\",\"ftime\":\"2017-07-29 07:56:47\",\"time\":\"2017-07-29 07:56:47\"},{\"context\":\"[苏州市] 快件到达 [苏州横泾]\",\"ftime\":\"2017-07-29 03:46:55\",\"time\":\"2017-07-29 03:46:55\"},{\"context\":\"[苏州市] 快件离开 [苏州中转部]已发往[苏州横泾]\",\"ftime\":\"2017-07-29 03:23:31\",\"time\":\"2017-07-29 03:23:31\"},{\"context\":\"[苏州市] 快件到达 [苏州中转部]\",\"ftime\":\"2017-07-29 03:00:03\",\"time\":\"2017-07-29 03:00:03\"},{\"context\":\"[嘉兴市] 快件离开 [杭州中转部]已发往[苏州中转部]\",\"ftime\":\"2017-07-28 22:48:54\",\"time\":\"2017-07-28 22:48:54\"},{\"context\":\"[嘉兴市] 快件到达 [杭州中转部]\",\"ftime\":\"2017-07-28 22:47:11\",\"time\":\"2017-07-28 22:47:11\"},{\"context\":\"[杭州市] 快件离开 [杭州市场部]已发往[苏州中转部]\",\"ftime\":\"2017-07-28 16:54:33\",\"time\":\"2017-07-28 16:54:33\"},{\"context\":\"[杭州市] [杭州彭埠]的杭州萧山保税仓已收件 电话:18055758356\",\"ftime\":\"2017-07-28 10:12:28\",\"time\":\"2017-07-28 10:12:28\"}]";
        JSONArray jsonArray = new JSONArray(data);
        for (int i = 0; i < 6; i++) {
            LogisticsData data1 = new LogisticsData();
            data1.setContext(jsonArray.getJSONObject(i).getString("context"));
            data1.setTime(jsonArray.getJSONObject(i).getString("time"));
            dataList.add(data1);
        }

    }
}
