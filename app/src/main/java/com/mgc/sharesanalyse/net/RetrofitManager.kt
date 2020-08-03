// IntelliJ API Decompiler stub source generated from a class file
// Implementation of methods is not available

package com.galanz.rxretrofit.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mgc.sharesanalyse.base.Datas
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitManager {
    var retrofit: Retrofit? = null

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(Datas.url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }



}

