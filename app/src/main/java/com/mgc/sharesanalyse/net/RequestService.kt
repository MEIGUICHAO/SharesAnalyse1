package com.mgc.sharesanalyse.net

import com.mgc.sharesanalyse.base.Datas
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface RequestService {

    @GET("list={path}")
    fun getSharesDatas(@Path("path") string: String): Deferred<String>


    @Headers("urlname:${Datas.dealDetailUrl}")
    @GET("/quotes_service/api/json_v2.php/CN_Bill.GetBillList")
    fun getDealDetai(@Query("symbol") symbol: String, @Query("day") day: String, @Query("sort") sort: String="ticktime", @Query("amount") amount: String="0", @Query("type") type: String="0", @Query("num") num: String="60000",
                     @Query("asc") asc: String="0", @Query("volume") volume: String="0"): Deferred<String>


    @Headers("urlname:${Datas.pricehisUrl}")
    @GET("/pricehis.php")
    fun getPricehis(@Query("symbol") symbol: String, @Query("startdate") startdate: String, @Query("enddate") enddate: String): Deferred<String>




//    fun getSharesDatas(@Path("path") string: String): Deferred<String>


}