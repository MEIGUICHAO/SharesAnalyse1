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
        val type = intent.getIntExtra("TYPE",1)
        val tb = when (type) {
            1->"Reasoning"
            2->"All_Reasoning_30"
            else->"All_Reasoning_50"
        }
        val list = DBUtils.getReasoningResult(tb)
        list.sortDescReasoningByDate()
        var result = ""
        var index = 0
        list.forEach {
            if (index < 60) {
                result = result + "n-->${it.n},code-->${it.code},d-->${it.d},36_T-->${it.f36_T}\n"
                index++
            }
        }
        tvResult.setText(result)
    }
}