package com.example.uwb_3v5.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.example.uwb_3v5.R;

public class KidOverview extends Overview {

    public KidOverview(Context context) {
        super(context);
        init(context);
    }

    public KidOverview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public KidOverview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // 使用 LayoutInflater 加载 kid_overview.xml 布局
        LayoutInflater.from(context).inflate(R.layout.kid_overview, this, true);
    }
}
