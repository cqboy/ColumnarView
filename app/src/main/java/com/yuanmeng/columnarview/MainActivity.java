package com.yuanmeng.columnarview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {

        Random random = new Random();
        // 初始化星期数据
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            values.add(random.nextInt(5000));
        }
        List<String> labels = new ArrayList<>();
        labels.add("一");
        labels.add("二");
        labels.add("三");
        labels.add("四");
        labels.add("五");
        labels.add("六");
        labels.add("日");
        // view填充数据
        ColumnarView columnar1 = (ColumnarView) findViewById(R.id.columnar1);
        columnar1.setLabels(labels);
        columnar1.setValues(values);



        // 初始化日数据
        values = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            values.add(random.nextInt(5000));
        }
        labels = new ArrayList<>();
        labels.add("1");
        labels.add("15");
        labels.add("30");
        ColumnarView columnar2 = (ColumnarView) findViewById(R.id.columnar2);
        columnar2.setLabels(labels);
        columnar2.setValues(values);
    }
}
