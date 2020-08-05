package com.mgc.sharesanalyse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.galanz.rxretrofit.network.RetrofitManager
import com.mgc.sharesanalyse.base.Datas
import com.mgc.sharesanalyse.net.LoadState
import com.mgc.sharesanalyse.utils.LogUtil
import com.mgc.sharesanalyse.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.loadState.observe(this, Observer {
            when (it) {
                is LoadState.Success->  {
                    viewModel.sharesDats
                }
                is LoadState.Fail ->  {

                }
                is LoadState.Loading ->  {

                }
            }
        })
        btnRequest.setOnClickListener{
            viewModel.requestData()
        }
    }

}
