package com.mgc.sharesanalyse.net

sealed class LoadState(val type: Int,val json:String) {
    class Loading(type: Int = 0, json: String = "") : LoadState(type,json)
    class Success(type: Int = 0, json: String = ""): LoadState(type,json)
    class Fail(type: Int = 0, json: String = "") : LoadState(type,json)
    class GoNext(type: Int = 0, json: String = "") : LoadState(type,json)
}