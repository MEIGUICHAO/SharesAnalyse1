package com.mgc.sharesanalyse.net

import com.mgc.sharesanalyse.base.Datas
import com.mgc.sharesanalyse.entity.BWCDPJsonBean
import com.mgc.sharesanalyse.entity.BWCQPResultBean
import com.mgc.sharesanalyse.entity.XQInfoBean
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

    /**
     * start=20200421&end=20200911
     */
    @Headers("urlname:${Datas.hisHqUrl}")
    @GET("/hisHq")
    fun getHisHq(@Query("code") code: String,@Query("start") start: String,@Query("end") end: String, @Query("stat") stat: String = "1"): Deferred<String>


    @Headers("urlname:${Datas.hisHqUrl}")
    @GET("/hisHq")
    fun getHisHq(@Query("code") code: String, @Query("stat") stat: String = "1"): Deferred<String>


    @Headers("urlname:${Datas.bwcUrl}","User-Agent:${Datas.UserAgent}","Content-Type:application/json")
    @POST("/api/v2/overbearfood/api_overbear_ordinary_data_list")
    fun getBwcDpList(@Body json: String="{\"userLatitude\":22.543965,\"userLongitude\":113.954606,\"userAddress\":\"华润城润府2期附近\",\"userId\":\"20200429090359-43c4c27f70_user\",\"page\":1,\"limit\":100,\"serviceNoStr\":\"api_overbear_ordinary_data_list\"}"): Deferred<String>


    @Headers("urlname:${Datas.bwcUrl}","User-Agent:${Datas.UserAgent}","Content-Type:application/json")
    @POST("/api/v2/overbearfood/api_overbear_sign_up")
    fun qiangBwc(@Body json: String): Deferred<String>



    @Headers("urlname:${Datas.xqUrl}","User-Agent:${Datas.WinChromeAgent}","Cookie:${Datas.XQCookies}")
    @POST("/query/v1/search/status")
    fun getXQInfo(@Query("q") code: String, @Query("page") page: String = "1", @Query("sort") sort: String = "relevance", @Query("source") source: String = "all"): Deferred<String>


    @Headers("urlname:${Datas.dealDetailUrl}")
    @POST("/corp/view/vCB_AllMemordDetail.php?")
    fun getSinaMineInfo(@Query("stockid") stockid: String): Deferred<String>


//    fun getSharesDatas(@Path("path") string: String): Deferred<String>


}