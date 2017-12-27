package com.android.messaging.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;

/**
 * Way Lin, 20171219.
 */

public class FreemeUtils {

    public static int getThemeAttrColor(Context context, @AttrRes int colorAttr) {
        TypedArray array = context.obtainStyledAttributes(null, new int[]{colorAttr});

        try {
            return array.getColor(0, 0);
        } finally {
            array.recycle();
        }
    }

    public static View getViewFromStub(View root, int viewId, int stubId) {
        View view = root.findViewById(viewId);
        if (view == null) {
            ViewStub stub = (ViewStub) root.findViewById(stubId);
            view = stub.inflate();
        }
        return view;
    }

    public static void setVisibility(View view, boolean visible) {
        Assert.notNull(view);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public static void setEnabled(View view, boolean enable) {
        Assert.notNull(view);
        view.setEnabled(enable);
    }

    public static View getActionModeView(Context context, int customId) {
        final int layoutResId = customId > 0 ?
                customId : com.android.messaging.R.layout.freeme_layout_action_mode;
        return LayoutInflater.from(context).inflate(layoutResId, null);
    }
}
