package com.jb.goscanner.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;

import com.jb.goscanner.R;

/**
 * Created by panruijie on 2017/9/4.
 * Email : zquprj@gmail.com
 */

public class ColorUtil {

    public static int getColor(Context context, int ResourcesId) {
        return ContextCompat.getColor(context, ResourcesId);
    }

    public static int getColorPrimary(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

}
