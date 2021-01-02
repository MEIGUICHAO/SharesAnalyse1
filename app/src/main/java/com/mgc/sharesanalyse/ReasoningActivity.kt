package com.mgc.sharesanalyse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mgc.sharesanalyse.base.sortDescReasoningByDate
import com.mgc.sharesanalyse.utils.DBUtils
import kotlinx.android.synthetic.main.act_reasoning_result.*

class ReasoningActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_reasoning_result)
        val list = DBUtils.getReasoningResult()
        list.sortDescReasoningByDate()
        var result = ""
        list.forEach {
            result = result+ "n-->${it.n},code-->${it.code},d-->${it.d}\n"
        }
        tvResult.setText(result)
    }
}