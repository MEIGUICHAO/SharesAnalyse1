package com.mgc.sharesanalyse.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.mgc.sharesanalyse.base.App;


/**
 * 获取资源工具
 */

public class ResUtil {



    public static int getColorWithAlpha(float alpha, int baseColor) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & baseColor;
        return a + rgb;
    }
    /**
     * get color
     *
     * @param res
     * @return
     */
    public static int getC(@ColorRes int res) {
        return ContextCompat.getColor(App.getContext(), res);
    }

    /**
     * get String
     *
     * @param res
     * @return
     */
    public static String getS(@StringRes int res) {
        return App.getContext().getString(res);
    }


    public static String[] getSArray( int res) {
        return App.getContext().getResources().getStringArray(res);
    }

    /**
     * get String
     *
     * @param res
     * @return
     */
    public static String getS(@StringRes int res, Object... params) {

        return String.format(getS(res),params);
    }
    /**
     * 获取尺寸
     */
    public static float getDimen(Context context, @DimenRes int id) {
        return context.getResources().getDimension(id);
    }

    /**
     * 获取Drawable
     */
    public static Drawable getDrawable(Context context, @DrawableRes int id) {
        return ContextCompat.getDrawable(context, id);
    }

    /**
     * 根据名字获取ID
     */
    public static int getId(String name, String type) {
        int id = 0;
        try {
            id = App.getContext().getResources().getIdentifier(name, type,
                    App.getContext().getPackageName());
        } catch (Exception e) {
        }
        return id;
    }

    /**
     * 去掉小数点后面的 0
     *
     * @param d
     * @return
     */
    public static String getText(double d) {
        return d % 1 == 0 ? (int) d + "" : d + "";
    }
}
