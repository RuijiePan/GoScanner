package com.jb.goscanner.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;

/**
 * Created by panruijie on 2017/9/4.
 * Email : zquprj@gmail.com
 */

public class ColorUtil {

    public static int getColor(Context context, int ResourcesId) {
        return ContextCompat.getColor(context, ResourcesId);
    }
}
