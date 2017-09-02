package com.jb.goscanner.util.log;

import android.util.Log;


/**
 * Log封装类
 *
 * @author skye
 */
public class Loger {

    public static boolean sDebug = true;

    public static void setDEBUG(boolean debug) {
        sDebug = debug;
    }

    /**
     * @param tag 标识符
     * @param msg 打印信息
     */
    public static void v(String tag, String msg) {
        if (sDebug) {
            Log.v(tag, msg);
        }
    }

    /**
     * @param tag 标识符
     * @param msg 打印信息
     * @param tr  抛出的异常
     */
    public static void v(String tag, String msg, Throwable tr) {
        if (sDebug) {
            Log.v(tag, msg, tr);
        }
    }

    /**
     * @param tag 标识符
     * @param msg 打印信息
     */
    public static void d(String tag, String msg) {
        if (sDebug) {
            Log.d(tag, msg);
        }
    }

    /**
     * @param tag 标识符
     * @param msg 打印信息
     * @param tr  抛出的异常
     */
    public static void d(String tag, String msg, Throwable tr) {
        if (sDebug) {
            Log.d(tag, msg, tr);
        }
    }

    /**
     * @param tag 标识符
     * @param msg 打印信息
     */
    public static void i(String tag, String msg) {
        if (sDebug) {
            Log.i(tag, msg);
        }
    }

    /**
     * @param tag 标识符
     * @param msg 打印信息
     * @param tr  抛出的异常
     */
    public static void i(String tag, String msg, Throwable tr) {
        if (sDebug) {
            Log.i(tag, msg, tr);
        }
    }

    /**
     * @param tag 标识符
     * @param msg 打印信息
     */
    public static void w(String tag, String msg) {
        if (sDebug) {
            Log.w(tag, msg);
        }
    }

    /**
     * @param tag 标识符
     * @param msg 打印信息
     * @param tr  抛出的异常
     */
    public static void w(String tag, String msg, Throwable tr) {
        if (sDebug) {
            Log.w(tag, msg, tr);
        }
    }

    /**
     * @param tag 标识符
     * @param msg 打印信息
     */
    public static void e(String tag, String msg) {
        if (sDebug) {
            Log.e(tag, msg);
        }
    }

    /**
     * @param tag 标识符
     * @param msg 打印信息
     * @param tr  抛出的异常
     */
    public static void e(String tag, String msg, Throwable tr) {
        if (sDebug) {
            Log.e(tag, msg, tr);
        }
    }
}
