package com.mgc.sharesanalyse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.util.forEach
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.galanz.rxretrofit.network.RetrofitManager
import com.mgc.sharesanalyse.base.Datas
import com.mgc.sharesanalyse.entity.Month8Data
import com.mgc.sharesanalyse.entity.Month8DataDao
import com.mgc.sharesanalyse.net.LoadState
import com.mgc.sharesanalyse.utils.DaoUtilsStore
import com.mgc.sharesanalyse.utils.DateUtils
import com.mgc.sharesanalyse.utils.FormatterEnum
import com.mgc.sharesanalyse.utils.LogUtil
import com.mgc.sharesanalyse.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.greenrobot.greendao.query.WhereCondition
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.loadState.observe(this, Observer {
            when (it) {
                is LoadState.Success->  {
                    for (index in 0..9) {
                        val data = viewModel.sharesDats.value?.get(index)
                        var month8Data = Month8Data()
                        month8Data.json = data
                        month8Data.name = index.toString()
                        month8Data.timeStamp = System.currentTimeMillis()
                        month8Data.ymd =
                            DateUtils.format(System.currentTimeMillis(), FormatterEnum.YYYYMMDD)
                        DaoUtilsStore.getInstance().month8DataDaoUtils.insert(month8Data)
                    }


                    runBlocking {
                        delay(1000 * 30)
                        viewModel.requestData()
                    }
                }
                is LoadState.Fail ->  {

                }
                is LoadState.Loading ->  {
                    btnRequest.isClickable = false
                }
            }
        })
        btnRequest.setOnClickListener{
            viewModel.requestData()
        }
    }

}
