package com.mgc.sharesanalyse.net

import com.mgc.sharesanalyse.base.Datas
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface RequestService {

    @GET("list={path}")
    fun getSharesDatas(@Path("path") string: String): Deferred<String>


}