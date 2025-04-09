package com.example.uwb_3v5;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.uwb_3v5.views.KidOverview;
import com.example.uwb_3v5.views.OldOverview;
import com.example.uwb_3v5.views.Overview;

public class MainActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "AppPreferences";
    private static final String KID_OVERVIEW_COUNT = "KidOverviewCount";
    private static final String OLD_OVERVIEW_COUNT = "OldOverviewCount";
    private int kidOverviewCount = 0;
    private int oldOverviewCount = 0;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // 设置导航按钮点击事件
        findViewById(R.id.navigation_button_to_kid).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, KidActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.navigation_button_to_old).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, OldActivity.class);
            startActivity(intent);
        });

//        *****************************************************

        // 找到布局文件中的容器和按钮
        container = findViewById(R.id.overview);
        Button addKidOverviewButton = findViewById(R.id.AddKidButton);
        Button addOldOverviewButton = findViewById(R.id.AddOldButton);

        // 从 SharedPreferences 中获取已添加的 Overview 数量
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        kidOverviewCount = preferences.getInt(KID_OVERVIEW_COUNT, 0);
        oldOverviewCount = preferences.getInt(OLD_OVERVIEW_COUNT, 0);

        // 恢复之前添加的 KidOverview 组件
        for (int i = 0; i < kidOverviewCount; i++) {
            addKidOverview();
        }
        // 恢复之前添加的 OldOverview 组件
        for (int i = 0; i < oldOverviewCount; i++) {
            addOldOverview();
        }

        // 给按钮设置点击事件
        addKidOverviewButton.setOnClickListener(v -> {
            // 动态添加 KidOverview 组件
            addKidOverview();
            // 增加组件计数，并保存到 SharedPreferences
            kidOverviewCount++;
            saveKidOverviewCount(kidOverviewCount);
        });
        // 给按钮设置点击事件
        addOldOverviewButton.setOnClickListener(v -> {
            // 动态添加 OldOverview 组件
            addOldOverview();
            // 增加组件计数，并保存到 SharedPreferences
            oldOverviewCount++;
            saveOldOverviewCount(oldOverviewCount);
        });
    }

    // 动态创建并添加 KidOverview 组件
    private void addKidOverview() {
        final KidOverview kidOverview = new KidOverview(this);
        // 生成一个随机数值（例如：0到100）
        int randomValue = kidOverview.getValue(0, 100);
        int relaxValue = kidOverview.getValue(0, 40);
        int stressValue = kidOverview.getValue(70, 100);

        // 设置 KidOverview 中的状态值基于随机值
        kidOverview.setTextViewContent(R.id.kid_today_overview_value, String.valueOf(randomValue));
        kidOverview.setTextViewContent(R.id.kid_today_relax_value, String.valueOf(relaxValue));
        kidOverview.setTextViewContent(R.id.kid_today_stress_value, String.valueOf(stressValue));

        // 设置 KidOverview 中的图片基于随机值
        kidOverview.setImageBasedOnValue(R.id.kid_today_overview_emoji, randomValue);

        container.addView(kidOverview);

        // 给组件设置长按事件监听器
        kidOverview.setOnLongClickListener(v -> {
            // 弹出确认对话框，询问是否删除组件
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("删除成员")
                    .setMessage("确定要删除该成员吗？")
                    .setPositiveButton("确定", (dialog, which) -> {
                        // 移除组件并更新持久化信息
                        removeKidOverview(kidOverview);
                    })
                    .setNegativeButton("取消", null)
                    .show();
            return true; // 返回 true 表示长按事件已处理
        });
    }
    private void addOldOverview() {
        final OldOverview OldOverview = new OldOverview(this);
        OldOverview.setTextViewContent(R.id.old_heart_rate, String.valueOf(OldOverview.getValue(40, 90)));
        OldOverview.setTextViewContent(R.id.old_breath_rate, String.valueOf(OldOverview.getValue(5, 25)));

        container.addView(OldOverview);
        // 给组件设置长按事件监听器
        OldOverview.setOnLongClickListener(v -> {
            // 弹出确认对话框，询问是否删除组件
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("删除成员")
                    .setMessage("确定要删除该成员吗？")
                    .setPositiveButton("确定", (dialog, which) -> {
                        // 移除组件并更新持久化信息
                        removeOldOverview(OldOverview);
                    })
                    .setNegativeButton("取消", null)
                    .show();
            return true; // 返回 true 表示长按事件已处理
        });
    }

    // 移除组件并更新持久化信息
    private void removeKidOverview(View kidOverview) {
        // 从容器中移除该组件
        container.removeView(kidOverview);

        // 更新计数器并保存到 SharedPreferences
        kidOverviewCount--;
        saveKidOverviewCount(kidOverviewCount);
    }
    private void removeOldOverview(View oldOverview) {
        // 从容器中移除该组件
        container.removeView(oldOverview);

        // 更新计数器并保存到 SharedPreferences
        oldOverviewCount--;
        saveOldOverviewCount(oldOverviewCount);
    }

    // 保存 KidOverview 组件的数量到 SharedPreferences
    private void saveKidOverviewCount(int count) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KID_OVERVIEW_COUNT, count);
        editor.apply();
    }
    // 保存 OldOverview 组件的数量到 SharedPreferences
    private void saveOldOverviewCount(int count) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(OLD_OVERVIEW_COUNT, count);
        editor.apply();
    }

    // 保存成员姓名到 SharedPreferences
//    private void saveMemberName(String name, Overview overview, int avatar) {
//        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(overview.getMemberName(), name); // 使用Membername作为键
//        editor.putInt(overview.getMemberName() + "'s avatar", avatar);  // 将 int 值存入 SharedPreferences
//        editor.apply();
//    }


}