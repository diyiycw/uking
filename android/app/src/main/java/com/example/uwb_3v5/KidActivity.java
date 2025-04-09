package com.example.uwb_3v5;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.BubbleChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KidActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kid);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.kids), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 找到布局中的按钮
        LinearLayout navigationLayoutToHome = findViewById(R.id.navigation_button_to_home);

        // 设置点击事件
        navigationLayoutToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 使用Intent跳转到另一个Activity
                Intent intent = new Intent(KidActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // 找到布局中的按钮
        LinearLayout navigationLayoutToOld = findViewById(R.id.navigation_button_kid_to_old);

        // 设置点击事件
        navigationLayoutToOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 使用Intent跳转到另一个Activity
                Intent intent = new Intent(KidActivity.this, OldActivity.class);
                startActivity(intent);
            }
        });

        // 获取 LineChart 视图
        LineChart lineChart = findViewById(R.id.chart);

        // 随机生成数据
        List<Entry> entries = generateRandomData();

        // 创建 LineDataSet
        LineDataSet dataSet = new LineDataSet(entries, "压力值"); // 数据标签
        dataSet.setLineWidth(2f); // 线条宽度
        dataSet.setCircleRadius(4f); // 数据点圆圈半径
        dataSet.setValueTextSize(10f); // 数据点值的文本大小

        // 设置颜色
        dataSet.setColor(ContextCompat.getColor(this, R.color.plws)); // 设置线条颜色为红色
        dataSet.setCircleColor(ContextCompat.getColor(this, R.color.gray)); // 设置数据点颜色为蓝色
        dataSet.setValueTextColor(Color.BLACK); // 设置数据点值的文字颜色

        // 设置填充颜色
        dataSet.setDrawFilled(true); // 启用填充
        dataSet.setFillColor(ContextCompat.getColor(this, R.color.worse_mood)); // 设置填充颜色为青色

        // 配置 LineChart
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        // 配置 X 轴
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // X 轴显示在底部
        xAxis.setGranularity(1f); // 间隔为 1
        xAxis.setAxisMinimum(0f); // 最小值为 0
        xAxis.setAxisMaximum(23f); // 最大值为 23

        // 更新图表
        lineChart.invalidate(); // 刷新图表
        // 获取 BubbleChart
        BubbleChart bubbleChart = findViewById(R.id.bubbleChart);
        // 配置 BubbleChart
        setupBubbleChart(bubbleChart);


        // 获取 PieChart
        PieChart pieChart = findViewById(R.id.pieChart);
        // 设置饼图数据和样式
        setupPieChart(pieChart);

    }



    private List<Entry> generateRandomData() {
        List<Entry> entries = new ArrayList<>();
        Random random = new Random();

        for (int hour = 0; hour < 24; hour++) {
            float pressure = random.nextInt(101); // 随机生成 0 到 100 的压力值
            entries.add(new Entry(hour, pressure));
        }

        return entries;
    }


    private void setupBubbleChart(BubbleChart bubbleChart) {
        // 生成气泡数据
        List<BubbleDataSet> dataSets = generateBubbleData();

        // 创建图表数据对象
        BubbleData bubbleData = new BubbleData();
        for (BubbleDataSet dataSet : dataSets) {
            bubbleData.addDataSet(dataSet);
        }

        // 设置数据到 BubbleChart
        bubbleChart.setData(bubbleData);

        // 配置 X 轴
        XAxis xAxis = bubbleChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(2f); // 每 2 小时一个跨度
        xAxis.setAxisMinimum(0f); // X 轴最小值
        xAxis.setAxisMaximum(23f); // X 轴最大值
        xAxis.setLabelCount(12, true); // 显示 12 个标签（0～23，每 2 个小时）

        // 配置 Y 轴
        YAxis yAxisLeft = bubbleChart.getAxisLeft();
        yAxisLeft.setGranularity(1f); // 每 1 个跨度
        yAxisLeft.setAxisMinimum(1f); // 星期最小值为 1
        yAxisLeft.setAxisMaximum(7f); // 星期最大值为 7
        // 使用自定义 ValueFormatter
        yAxisLeft.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return getWeekdayLabel((int) value); // 转换为 int 并获取星期标签
            }
        });        bubbleChart.getAxisRight().setEnabled(false); // 禁用右侧 Y 轴

        // 禁用描述
        bubbleChart.getDescription().setEnabled(false);
        bubbleChart.setDrawGridBackground(false);

        // 刷新图表
        bubbleChart.invalidate();
    }

    private List<BubbleDataSet> generateBubbleData() {
        Random random = new Random();
        List<BubbleDataSet> dataSets = new ArrayList<>();

        // 按星期生成数据
//        int[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.CYAN, Color.MAGENTA, Color.YELLOW, Color.DKGRAY};

        int[] colors = new int[] {
                ContextCompat.getColor(this, R.color.common_mood),
                ContextCompat.getColor(this, R.color.orange),
                ContextCompat.getColor(this, R.color.good_mood),
                ContextCompat.getColor(this, R.color.better_mood),
                ContextCompat.getColor(this, R.color.cyan),
                ContextCompat.getColor(this, R.color.worse_mood),
                ContextCompat.getColor(this, R.color.bad_mood)
        };
        for (int weekDay = 1; weekDay <= 7; weekDay++) {
            List<BubbleEntry> entries = new ArrayList<>();

            // 每个星期生成 5 个气泡
            for (int i = 0; i < 5; i++) {
                float x = random.nextInt(24); // 随机生成 0～23 的小时
                float y = weekDay; // 固定 Y 值为星期
                float size = random.nextInt(101); // 随机生成 0 到 100 的整数值——气泡的值
                entries.add(new BubbleEntry(x, y, size));
            }

            // 创建数据集
            BubbleDataSet dataSet = new BubbleDataSet(entries, "星期" + getWeekdayLabel(weekDay));
            // 设置值格式化器，只显示整数
            dataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return String.valueOf((int) value); // 将浮点数转换为整数
                }
            });

            dataSet.setColor(colors[weekDay - 1]); // 设置颜色
            dataSet.setValueTextColor(Color.BLACK); // 设置文本颜色
            dataSet.setValueTextSize(10f); // 设置文本大小
            dataSets.add(dataSet);
        }

        return dataSets;
    }

    private String getWeekdayLabel(float weekDay) {
        switch ((int) weekDay) {
            case 1:
                return "一";
            case 2:
                return "二";
            case 3:
                return "三";
            case 4:
                return "四";
            case 5:
                return "五";
            case 6:
                return "六";
            case 7:
                return "日";
            default:
                return "";
        }
    }

    private void setupPieChart(PieChart pieChart) {
        // 创建数据
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(10f, "心情很差"));
        entries.add(new PieEntry(18f, "心情不好"));
        entries.add(new PieEntry(54f, "心情平常"));
        entries.add(new PieEntry(14f, "心情不错"));
        entries.add(new PieEntry(4f, "心情很好"));

        // 创建数据集
        PieDataSet dataSet = new PieDataSet(entries, "一月内心情占比");

        // 设置标签在扇形外部
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE); // 标签位置在外部
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE); // 标签的 X 位置也在外部
        dataSet.setValueTextColor(Color.BLACK); // 设置值文字颜色

        // 设置标签和指引线样式
        dataSet.setSliceSpace(2f); // 设置扇形间距
        dataSet.setValueLinePart1OffsetPercentage(80.f); // 第一段指引线的偏移
        dataSet.setValueLinePart1Length(0.4f); // 第一段指引线长度
        dataSet.setValueLinePart2Length(0.8f); // 第二段指引线长度
        dataSet.setValueLineColor(Color.BLACK); // 指引线颜色

        // 获取颜色资源
        int badMood = ContextCompat.getColor(this, R.color.bad_mood);
        int worseMood = ContextCompat.getColor(this, R.color.worse_mood);
        int commonMood = ContextCompat.getColor(this, R.color.common_mood);
        int betterMood = ContextCompat.getColor(this, R.color.better_mood);
        int goodMood = ContextCompat.getColor(this, R.color.good_mood);

        // 设置到 PieDataSet
        dataSet.setColors(new int[]{badMood, worseMood, commonMood, betterMood, goodMood});

        dataSet.setSliceSpace(2f); // 设置分割间距
        dataSet.setValueTextSize(12f); // 设置值文字大小

        // 配置数据对象
        PieData pieData = new PieData(dataSet);
        // 设置百分比格式化器
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        // 设置饼图显示百分比
        pieChart.setUsePercentValues(true);
        // 设置数据到 PieChart
        pieChart.setData(pieData);

        // 设置标签颜色为黑色
        pieChart.setEntryLabelColor(Color.BLACK); // 标签文本颜色

        // 设置饼图样式
        pieChart.setHoleRadius(50f); // 设置空心半径（百分比）
        pieChart.setTransparentCircleRadius(70f); // 设置透明圆环半径

        pieChart.setCenterText("心情平常\n次数最多"); // 设置中心文本
        pieChart.setCenterTextSize(16f); // 设置中心文本大小
//        int CenterTextColor = ContextCompat.getColor(this, R.color.common_mood);
        pieChart.setCenterTextColor(Color.BLACK); // 设置中心文本颜色

        pieChart.getDescription().setEnabled(false); // 禁用描述
        pieChart.getLegend().setEnabled(false); // 禁用图例

        // 刷新图表
        pieChart.invalidate();
    }

}