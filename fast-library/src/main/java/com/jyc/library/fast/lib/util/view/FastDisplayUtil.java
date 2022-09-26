package com.jyc.library.fast.lib.util.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import com.jyc.library.fast.lib.util.AppGlobals;

/// @author jyc
/// 创建日期：2021/4/26
/// 描述：FastDisplayUtil
public class FastDisplayUtil {
    public static int dp2px(float dp, Resources resources) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }

    public static int dp2px(float dp) {
        Resources resources = AppGlobals.INSTANCE.get().getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }

    public static int sp2px(float sp, Resources resources) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.getDisplayMetrics());
    }

    public static int sp2px(float sp) {
        Resources resources = AppGlobals.INSTANCE.get().getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.getDisplayMetrics());
    }

    /**
     * 将px值转换为dp值
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int px2dp(float pxValue) {
        Resources resources = AppGlobals.INSTANCE.get().getResources();
        final float scale = resources.getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int px2sp(float pxValue) {
        Resources resources = AppGlobals.INSTANCE.get().getResources();
        final float fontScale = resources.getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int getDisplayWidthInPx(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return size.x;
        }

        return 0;
    }

    public static int getDisplayHeightInPx(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return size.y;
        }

        return 0;
    }
}
