package com.mgc.sharesanalyse.utils;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zxl on 2019/11/19.
 */

public class LogUtil {
    public static boolean isDebug = true;
    public static final String LOG_TAG = "sharesanalyse";

    private static String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null) {
            return null;
        }
        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }
            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }
            if (st.getClassName().equals(LogUtil.class.getName())) {
                continue;
            }

            return "[" + Thread.currentThread().getName() + "(" + Thread.currentThread().getId()
                    + "): " + st.getFileName() + ":" + st.getLineNumber() + " " + st.getMethodName() + "]";
        }
        return null;
    }

    private static StringBuffer sb = new StringBuffer();

    private static String createMessage(String msg) {
        sb.delete(0, sb.length());
        sb.append(getFunctionName());
        sb.append("-");
        sb.append(msg);
        return sb.toString();
    }

    public static void v(String msg) {
        if (isDebug) {
            android.util.Log.v(LOG_TAG, createMessage(msg));
        }
    }

    public static void v(String tag, String msg) {
        if (isDebug) {
            android.util.Log.v(tag, createMessage(msg));
        }
    }

    public static void v(String tag, String msg, Throwable t) {
        if (isDebug) {
            android.util.Log.v(tag, createMessage(msg), t);
        }
    }

    private static void writeLongLog(String msg) {
//        int segmentSize = 3 * 1024;
//        long length = msg.length();
//        if (length <= segmentSize ) {// 长度小于等于限制直接打印
//            Log.e(tag, msg);
//        }else {
//            while (msg.length() > segmentSize ) {// 循环分段打印日志
//                String logContent = msg.substring(0, segmentSize );
//                msg = msg.replace(logContent, "");
//                Log.e(tag,"-------------------"+ logContent);
//            }
//            Log.e(tag,"-------------------"+ msg);// 打印剩余日志
//        }

    }

    /**
     * 只是查看方法是否运行
     */
    public static void d() {
        if (isDebug) {
            android.util.Log.d(LOG_TAG, createMessage(""));
        }
    }

    /**
     * 使用通用的 tag  在config 中 配置
     *
     * @param msg
     */
    public static void d(String msg) {
        if (isDebug) {
            android.util.Log.d(LOG_TAG, createMessage(msg));

        }
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            android.util.Log.d(tag, createMessage(msg));
        }
    }

    public static void d(String tag, String msg, Throwable t) {
        if (isDebug) {
            android.util.Log.d(tag, createMessage(msg), t);
        }
    }

    public static void i(String msg) {
        if (isDebug) {
            android.util.Log.i(LOG_TAG, createMessage(msg));
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            android.util.Log.i(tag, createMessage(msg));
        }
    }

    public static void i(String tag, String msg, Throwable t) {
        if (isDebug) {
            android.util.Log.i(tag, createMessage(msg), t);
        }
    }

    /**
     * 使用通用tag
     *
     * @param msg
     */
    public static void w(String msg) {
        if (isDebug) {
            android.util.Log.w(LOG_TAG, createMessage(msg));
        }
    }

    public static void w(String tag, String msg) {
        if (isDebug) {
            android.util.Log.w(tag, createMessage(msg));
        }
    }

    public static void w(String tag, String msg, Throwable t) {
        if (isDebug) {
            android.util.Log.w(tag, createMessage(msg), t);
        }
    }

    public static void e(String msg) {
        if (isDebug) {
            android.util.Log.e(LOG_TAG, createMessage(msg));
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
//            String message = createMessage(msg);
//            android.util.Log.e(tag, message);
            android.util.Log.e(tag, getFunctionName());
            printJson(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable t) {
        if (isDebug) {
            String message = createMessage(msg);
            android.util.Log.e(tag, message, t);
        }
    }

    public static void printLine(String tag, boolean isTop) {
        if (isTop) {
            logE(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            logE(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static void printJson(String tag, String msg) {

        String message;

        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(4);//最重要的方法，就一行，返回格式化的json字符串，其中的数字4是缩进字符数
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(4);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        printLine(tag, true);
        message = "" + LINE_SEPARATOR + message;
        String[] lines = message.split(LINE_SEPARATOR);
        for (String line : lines) {
            logE(tag, "║ " + line);
        }
        printLine(tag, false);
    }


    public static void logE(String tag, String msg) {
        if (!TextUtils.isEmpty(msg)) {

            //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
            //  把4*1024的MAX字节打印长度改为2001字符数
            int max_str_length = 2001 - tag.length();
            //大于4000时
            while (msg.length() > max_str_length) {
                Log.e(tag, msg.substring(0, max_str_length));
                msg = msg.substring(max_str_length);
            }
            //剩余部分
            Log.e(tag, msg);
        }
    }
}
