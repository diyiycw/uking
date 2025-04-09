package com.example.uwb_3v5.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.uwb_3v5.R;

public class OldOverview extends Overview {

    public OldOverview(Context context) {
        super(context);
    }

    public OldOverview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OldOverview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 重写虚函数，返回 old_overview 布局
    @Override
    protected int getLayoutResource() {
        return R.layout.old_overview; // 返回 old_overview 布局
    }

    public void setTextViewContent(int textViewId, String content) {
        TextView textView = findViewById(textViewId); // 假设 TextView 的 ID 是 textViewKidInfo
        if (textView != null) {
            textView.setText(content);
        }
    }
}