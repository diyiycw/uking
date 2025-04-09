package com.example.uwb_3v5.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import java.util.UUID; // 用于生成唯一的ID

import com.example.uwb_3v5.R;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class Overview extends LinearLayout {
    private String uniqueId; // 存储组件的独立ID
    private String MemberName; // 存储成员姓名
    private String avatar; // 存储成员头像


    public Overview(Context context) {
        super(context);
        init(context);
    }

    public Overview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Overview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // 初始化方法，调用子类的布局加载方法
    private void init(Context context) {
        // 调用虚函数，加载具体的布局（由子类提供）
        LayoutInflater.from(context).inflate(getLayoutResource(), this, true);

        // 为每个组件生成并设置一个独立的 ID
        uniqueId = generateUniqueId();
    }

    // 虚函数，子类重写此函数来指定不同的布局
    protected int getLayoutResource() {
        return R.layout.kid_overview; // 默认返回 kid_overview 布局
    }

    // 生成唯一的 ID
    private String generateUniqueId() {
        return UUID.randomUUID().toString(); // 使用 UUID 生成唯一 ID
    }

    // 获取生成的唯一 ID
    public String getUniqueId() {
        return uniqueId;
    }

    // 获取成员姓名
    public String getMemberName() {
        return MemberName;
    }

    // 随机生成一个范围内的整数
    public int getValue(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    // 新增的设置文本内容的方法
    public void setTextViewContent(int textViewId, String content) {
        TextView textView = findViewById(textViewId); // 假设 TextView 的 ID 是 textViewKidInfo
        if (textView != null) {
            textView.setText(content);
            textView.setTextColor(getColorForScore(Integer.parseInt(content)));
        }
    }

        // 新增的方法，接收 ImageView 的 ID 和 drawable 资源 ID，设置图片
        public void setImageViewContent(int imageViewId, int drawableResId) {
            ImageView imageView = findViewById(imageViewId);
            if (imageView != null) {
                imageView.setImageResource(drawableResId);  // 设置图片资源
            }
        }

    // 新增的方法，根据数值设置对应的图片
    public void setImageBasedOnValue(int imageViewId, int value) {
        int drawableResId = R.drawable.xinqinghenhao;  // 默认图片

        if (value >= 0 && value <= 20) {
            drawableResId = R.drawable.xinqinghenhao;
        } else if (value >= 21 && value <= 40) {
            drawableResId = R.drawable.xinqingbucuo;
        } else if (value >= 41 && value <= 60) {
            drawableResId = R.drawable.xinqingshuobuqingchu;
        } else if (value >= 61 && value <= 80) {
            drawableResId = R.drawable.xinqingjiaocha;
        } else if (value >= 81 && value <= 100) {
            drawableResId = R.drawable.xinqinghencha;
        }

        // 调用之前的 setImageViewContent 方法，设置图片
        setImageViewContent(imageViewId, drawableResId);
    }

    // 根据分数返回相应的颜色
    public int getColorForScore(int score) {
        if (score >= 0 && score <= 20) {
            return ContextCompat.getColor(getContext(), R.color.good_mood);  // 使用 ContextCompat 获取颜色
        } else if (score >= 21 && score <= 40) {
            return ContextCompat.getColor(getContext(), R.color.better_mood);
        } else if (score >= 41 && score <= 60) {
            return ContextCompat.getColor(getContext(), R.color.common_mood);
        } else if (score >= 61 && score <= 80) {
            return ContextCompat.getColor(getContext(), R.color.worse_mood);
        } else if (score >= 81 && score <= 100) {
            return ContextCompat.getColor(getContext(), R.color.bad_mood);
        } else {
            return Color.BLACK;  // 默认颜色为黑色
        }
    }

}