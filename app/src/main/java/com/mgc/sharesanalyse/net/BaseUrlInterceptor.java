package com.mgc.sharesanalyse.net;

import android.text.TextUtils;
import android.util.Log;

import com.mgc.sharesanalyse.base.Datas;
import com.mgc.sharesanalyse.utils.LogUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BaseUrlInterceptor implements Interceptor {

    private String referer;

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取request
        Request request = chain.request();
        //从request中获取原有的HttpUrl实例oldHttpUrl
        HttpUrl oldHttpUrl = request.url();
        //获取request的创建者builder
        Request.Builder builder = request.newBuilder();
        //从request中获取headers，通过给定的键url_name
        List<String> headerValues = request.headers("urlname");
        List<String> agentrValues = request.headers("User-Agent");
        List<String> contentType = request.headers("Content-Type");
        List<String> cookieType = request.headers("Cookie");
        if (!TextUtils.isEmpty(referer)) {
            builder.addHeader("referer", referer);
            referer = "";
        }
        if (agentrValues.size() > 0) {
            builder.removeHeader("User-Agent");
            builder.addHeader("User-Agent", agentrValues.get(0));
        }

        if (contentType.size() > 0) {
            builder.removeHeader("Content-Type");
            builder.addHeader("Content-Type", contentType.get(0));
        }

        if (cookieType.size() > 0) {
            builder.removeHeader("Cookie");
            builder.addHeader("Cookie", cookieType.get(0));
        }
        if (headerValues.size() > 0) {
            //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
            builder.removeHeader("urlname");
            //匹配获得新的BaseUrl
            String headerValue = headerValues.get(0);
            HttpUrl newBaseUrl = null;
            String httpTag = "https";
            newBaseUrl = HttpUrl.parse(headerValue);
            //重建新的HttpUrl，修改需要修改的url部分
            HttpUrl newFullUrl = oldHttpUrl
                    .newBuilder()
                    .scheme(httpTag)//更换网络协议
                    .host(newBaseUrl.host())//更换主机名
//                    .port(newBaseUrl.port())//更换端口
                    .build();
            //重建这个request，通过builder.url(newFullUrl).build()；
            // 然后返回一个response至此结束修改
            LogUtil.d("newBaseUrl:"+newBaseUrl.host());
            LogUtil.d("Url", "intercept: " + newFullUrl.toString());
            if (newBaseUrl.host().equals("dcfm.eastmoney.com")) {
                String url = newFullUrl.toString().replace("%28", "(").replace("%29", ")").replace("%25", "%");
                LogUtil.d("Url", "intercept-->" + url.toString());
                return chain.proceed(builder.url(url).build());
            }
            return chain.proceed(builder.url(newFullUrl).build());
        }
        return chain.proceed(request);
    }
}
