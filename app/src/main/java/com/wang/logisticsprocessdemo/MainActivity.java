package com.wang.logisticsprocessdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private StepView stepView;
    private List<StepData> dataList = new ArrayList<>();
    private Button btSel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stepView = findViewById(R.id.stepView);
        //默认选中第2个节点
        stepView.setSelectPosition(2);

        //选中最后第5个节点
        btSel = findViewById(R.id.btSel);
        btSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepView.setSelectPosition(4);
            }
        });

        intStepData();

        //StepView与Activity通信，通过接口回调的方式
        stepView.setStepAdapter(new StepAdapter() {
            @Override
            public int getCount() {
                return dataList.size();
            }
            @Override
            public List<StepData> getData() {
                return dataList;
            }
        });
    }
    /**
     * 各阶段上的标注文字
     */
    private void intStepData() {
        StepData data1 = new StepData();
        data1.setStepText("已接");
        StepData data2 = new StepData();
        data2.setStepText("提货");
        StepData data3 = new StepData();
        data3.setStepText("运输");
        StepData data4 = new StepData();
        data4.setStepText("榜单");
        StepData data5 = new StepData();
        data5.setStepText("完成");
        dataList.add(data1);
        dataList.add(data2);
        dataList.add(data3);
        dataList.add(data4);
        dataList.add(data5);
    }
}
