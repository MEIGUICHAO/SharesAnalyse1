package com.mgc.sharesanalyse.base

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import com.mgc.sharesanalyse.entity.Month8Data
import com.mgc.sharesanalyse.net.LoadState
import com.mgc.sharesanalyse.utils.DaoUtilsStore
import com.mgc.sharesanalyse.utils.DateUtils
import com.mgc.sharesanalyse.utils.FormatterEnum
import com.mgc.sharesanalyse.utils.LogUtil
import com.mgc.sharesanalyse.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.greenrobot.greendao.database.Database
import java.util.Observer

open class BaseService : Service() {

    var mBinder = StocksBinder()


    class StocksBinder : BaseBinder() {

    }

    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()

    }

}