package com.mgc.sharesanalyse.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

/**
 * Gson解析工具类
 * Created by Czg on 2016/8/3.
 */
public class GsonHelper {

    private static Gson instance;

    private GsonHelper() {
    }

    public static Gson getInstance() {
        if (instance == null) {
            instance = new Gson();
        }
        return instance;
    }

    /**
     * 解析一个Object
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T parse(String json, Class<T> type) {
        if (json == null || "".equals(json)) {
            return null;
        }
        try {
            if (type == String.class) {
                return (T) json;
        }
        return getInstance().fromJson(json, type);
        } catch (Exception e) {
            if (json.contains("},}")) {
                try {
                    json = json.replace("},}", "");
                    return getInstance().fromJson(json, type);
                } catch (Exception e1) {
                    if (json.contains("CTC_D")) {
                        try {
                            json = json+ "}}";
                            return getInstance().fromJson(json, type);
                        } catch (Exception e2) {
                            LogUtil.e(getInstance().getClass().getSimpleName(), "Exception" + e2.toString() + "\njson---:\n" + json);
                        }
                    }
                    LogUtil.e(getInstance().getClass().getSimpleName(), "Exception" + e1.toString() + "\njson---:\n" + json);
                }
            }

            LogUtil.e(getInstance().getClass().getSimpleName(), "Exception" + e.toString() + "\njson---:\n" + json);
            return null;
        }
    }

    /**
     * 解析一个Array
     * @param json
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> parseArray(String json, Class<T> cls) {
        try {
            if (json == null || "".equals(json)) {
                return null;
            }
            ArrayList<T> mList = new ArrayList<>();
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for (final JsonElement elem : array) {
                mList.add(getInstance().fromJson(elem, cls));
            }
            return mList;
        } catch (Exception e) {
            LogUtil.e("parseArray",e.toString());
            return null;
        }
    }


    /**
     * 实体类转换成Json字符串
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        return getInstance().toJson(object);
    }
}
