package com.example.uwb_3v5;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.widget.LinearLayout;

import android.graphics.Color;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class OldActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_old);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.old), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // 找到布局中的按钮
        LinearLayout navigationLayoutToHome = findViewById(R.id.navigation_button_old_to_home);
        // 设置点击事件
        navigationLayoutToHome.setOnClickListener(v -> {
            // 使用Intent跳转到另一个Activity
            Intent intent = new Intent(OldActivity.this, MainActivity.class);
            startActivity(intent);
        });
        // 找到布局中的按钮
        LinearLayout navigationLayoutToKid = findViewById(R.id.navigation_button_old_to_kid);
        // 设置点击事件
        navigationLayoutToKid.setOnClickListener(v -> {
            // 使用Intent跳转到另一个Activity
            Intent intent = new Intent(OldActivity.this, KidActivity.class);
            startActivity(intent);
        });

        // 获取 LineChart
        LineChart lineChart = findViewById(R.id.LineChartOld);
        // 配置 LineChart 数据和样式
        setupLineChart(lineChart);



        // 获取 HorizontalBarChart
        HorizontalBarChart horizontalBarChart = findViewById(R.id.horizontalBarChart);
        // 配置 HorizontalBarChart
        setupHorizontalBarChart(horizontalBarChart);



        // 获取 CandleStickChart
        CandleStickChart candleStickChart = findViewById(R.id.candleStickChart);
        // 配置 CandleStickChart
        setupCandleStickChart(candleStickChart);
    }

    private void setupLineChart(LineChart lineChart) {
        // 创建心率和呼吸率数据
        List<Entry> heartRateEntries = generateHeartRateData();
        List<Entry> breathingRateEntries = generateBreathingRateData();

        int heart_dot_color = ContextCompat.getColor(this, R.color.purple);
        int heart_line_color = ContextCompat.getColor(this, R.color.bad_mood);
        int breath_dot_color = ContextCompat.getColor(this, R.color.plws);
        int breath_line_color = ContextCompat.getColor(this, R.color.worse_mood);

        // 创建心率数据集
        LineDataSet heartRateDataSet = new LineDataSet(heartRateEntries, "心率");
        heartRateDataSet.setColor(heart_line_color); // 设置心率线条颜色
        heartRateDataSet.setLineWidth(2f); // 设置线条宽度
        heartRateDataSet.setCircleColor(heart_dot_color); // 设置数据点颜色
        heartRateDataSet.setCircleRadius(4f); // 设置数据点半径
        heartRateDataSet.setValueTextColor(heart_dot_color); // 数据点的值颜色
        heartRateDataSet.setValueTextSize(10f); // 数据点的值字体大小

        // 创建呼吸率数据集
        LineDataSet breathingRateDataSet = new LineDataSet(breathingRateEntries, "呼吸率");
        breathingRateDataSet.setColor(breath_line_color); // 设置呼吸率线条颜色
        breathingRateDataSet.setLineWidth(2f);
        breathingRateDataSet.setCircleColor(breath_dot_color);
        breathingRateDataSet.setCircleRadius(4f);
        breathingRateDataSet.setValueTextColor(breath_dot_color);
        breathingRateDataSet.setValueTextSize(10f);
        breathingRateDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT); // 将呼吸率数据集依赖于右侧Y轴

        // 创建 LineData 并添加数据集
        LineData lineData = new LineData(heartRateDataSet, breathingRateDataSet);
        lineChart.setData(lineData);

        // 配置 X 轴
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // X 轴显示在底部
        xAxis.setGranularity(1f); // X 轴间隔为 1
        xAxis.setAxisMinimum(0f); // 设置 X 轴最小值
        xAxis.setAxisMaximum(24f); // 设置 X 轴最大值
        xAxis.setDrawGridLines(false); // 禁用网格线

        // 配置左侧Y轴（心率）
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f); // 设置 Y 轴最小值
        leftAxis.setAxisMaximum(200f); // 设置 Y 轴最大值（假设最大心率为 200）

        // 配置右侧Y轴（呼吸率）
        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(true); // 启用右侧Y轴
        rightAxis.setAxisMinimum(0f); // 设置右侧Y轴最小值
        rightAxis.setAxisMaximum(30f); // 设置右侧Y轴最大值（假设最大呼吸率为30）

        // 禁用图表描述
        lineChart.getDescription().setEnabled(false);

        // 刷新图表
        lineChart.invalidate();
    }

    private List<Entry> generateHeartRateData() {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i <= 24; i++) { // 生成 24 小时的心率数据
            entries.add(new Entry(i, 40 + (float) (Math.random() * 60))); // 心率范围 40~100
        }
        return entries;
    }

    private List<Entry> generateBreathingRateData() {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i <= 24; i++) { // 生成 24 小时的呼吸率数据
            entries.add(new Entry(i, 8 + (float) (Math.random() * 20))); // 呼吸率范围 8~28
        }
        return entries;
    }

    private void setupHorizontalBarChart(HorizontalBarChart chart) {
        // 模拟活动时长数据（周一到周日）
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 1.5f)); // 周一 1.5 小时
        entries.add(new BarEntry(1, 2.0f)); // 周二 2 小时
        entries.add(new BarEntry(2, 1.0f)); // 周三 1 小时
        entries.add(new BarEntry(3, 3.0f)); // 周四 3 小时
        entries.add(new BarEntry(4, 4.5f)); // 周五 4.5 小时
        entries.add(new BarEntry(5, 2.5f)); // 周六 2.5 小时
        entries.add(new BarEntry(6, 5.0f)); // 周日 5 小时

        int[] colors = new int[] {
                ContextCompat.getColor(this, R.color.common_mood),
                ContextCompat.getColor(this, R.color.orange),
                ContextCompat.getColor(this, R.color.good_mood),
                ContextCompat.getColor(this, R.color.better_mood),
                ContextCompat.getColor(this, R.color.cyan),
                ContextCompat.getColor(this, R.color.worse_mood),
                ContextCompat.getColor(this, R.color.bad_mood)
        };

        // 创建数据集
        BarDataSet dataSet = new BarDataSet(entries, "活动时长 (小时)");
        dataSet.setColors(colors); // 设置柱状条颜色
        dataSet.setValueTextColor(Color.BLACK); // 设置值的颜色
        dataSet.setValueTextSize(12f); // 设置值的字体大小

        // 创建 BarData 对象
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.7f); // 设置柱状条宽度

        // 设置数据到图表
        chart.setData(barData);

        // 配置 X 轴
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // X 轴在底部
        xAxis.setDrawGridLines(false); // 禁用网格线
        xAxis.setGranularity(1f); // 间隔为 1
        xAxis.setValueFormatter(new IndexAxisValueFormatter(
                new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"})); // 设置 X 轴标签

        // 配置 Y 轴
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(0f); // Y 轴最小值为 0
        leftAxis.setAxisMaximum(6f); // Y 轴最大值为 6（假设活动时长不超过 6 小时）
        leftAxis.setGranularity(1f); // 间隔为 1
        leftAxis.setEnabled(false); // 隐藏左侧 Y 轴

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setAxisMinimum(0f);
        rightAxis.setAxisMaximum(6f);
        rightAxis.setGranularity(1f);

        // 禁用描述
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false); // 禁用图例

        // 刷新图表
        chart.invalidate();
    }

    private void setupCandleStickChart(CandleStickChart chart) {
        // 模拟 30 天的心率范围数据（低点、开盘值、高点、收盘值）
        List<CandleEntry> entries = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            float low = 60 + (float) (Math.random() * 10);   // 心率最低值
            float high = 100 + (float) (Math.random() * 10); // 心率最高值
            float open = low + (float) (Math.random() * 20); // 心率开盘值
            float close = low + (float) (Math.random() * 20); // 心率收盘值
            entries.add(new CandleEntry(i, high, low, open, close));
        }

        int[] colors = new int[] {
                ContextCompat.getColor(this, R.color.common_mood),
                ContextCompat.getColor(this, R.color.orange),
                ContextCompat.getColor(this, R.color.good_mood),
                ContextCompat.getColor(this, R.color.better_mood),
                ContextCompat.getColor(this, R.color.cyan),
                ContextCompat.getColor(this, R.color.worse_mood),
                ContextCompat.getColor(this, R.color.bad_mood)
        };

        // 创建数据集
        CandleDataSet dataSet = new CandleDataSet(entries, "心率范围变化（红色上升，蓝色下降）");
        dataSet.setColor(Color.DKGRAY); // 设置线条颜色
        dataSet.setShadowColor(Color.BLACK); // 设置影线颜色
        dataSet.setShadowWidth(0.7f); // 设置影线宽度
        dataSet.setDecreasingColor(colors[0]); // 设置收盘价低于开盘价的颜色
        dataSet.setDecreasingPaintStyle(Paint.Style.FILL); // 设置收盘价低于开盘价的填充样式
        dataSet.setIncreasingColor(colors[5]); // 设置收盘价高于开盘价的颜色
        dataSet.setIncreasingPaintStyle(Paint.Style.FILL); // 设置收盘价高于开盘价的填充样式
        dataSet.setNeutralColor(colors[3]); // 设置收盘价等于开盘价的颜色
        dataSet.setHighlightLineWidth(1f); // 设置高亮线宽度

        // 创建数据对象
        CandleData candleData = new CandleData(dataSet);

        // 设置数据到图表
        chart.setData(candleData);

        // 配置 X 轴
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // X 轴显示在底部
        xAxis.setGranularity(1f); // 间隔为 1
        xAxis.setAxisMinimum(0); // X 轴最小值
        xAxis.setAxisMaximum(30); // X 轴最大值（30 天）
        xAxis.setDrawGridLines(false); // 禁用网格线
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return "Day " + ((int) value + 1); // 设置 X 轴标签
            }
        });

        // 配置 Y 轴
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(50f); // Y 轴最小值
        leftAxis.setAxisMaximum(150f); // Y 轴最大值
        leftAxis.setGranularity(10f); // 间隔为 10

        chart.getAxisRight().setEnabled(false); // 禁用右侧 Y 轴

        // 禁用描述
        chart.getDescription().setEnabled(false);

        // 刷新图表
        chart.invalidate();
    }
}