// IntelliJ API Decompiler stub source generated from a class file
// Implementation of methods is not available

package com.galanz.rxretrofit.network

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mgc.sharesanalyse.base.Datas
import com.mgc.sharesanalyse.net.BaseUrlInterceptor
import com.mgc.sharesanalyse.net.LenientGsonConverterFactory
import com.mgc.sharesanalyse.net.RequestService
import com.mgc.sharesanalyse.utils.LogUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitManager {

    val reqApi by lazy {

        LogUtil.d("mgc", "RetrofitManager lazy init")
        //日志拦截器
        var httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        //Okhttp对象
        var okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(BaseUrlInterceptor())
            .build()
//        var gson = Gson()
        val retrofit =
            Retrofit.Builder().baseUrl(Datas.url)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(LenientGsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
        return@lazy retrofit.create(RequestService::class.java)
    }


}

