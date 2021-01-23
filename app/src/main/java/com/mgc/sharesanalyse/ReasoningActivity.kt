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
                result = result + "${it.n},c-->${it.code},d-->${it.d},p-->${it.p}\n"
                if (type == 2 || type == 3) {
                    val filterList = DBUtils.getReasoningAllJudgeResult(tb,it)
                    val requestP = when(type){
                        2-> 30.toFloat()
                        else->50.toFloat()
                    }
                    var fuCount = 0
                    var rCount = 0
                    filterList.forEach {
                        if (it.p < 0) {
                            fuCount++
                        } else if (it.p>=requestP) {
                            rCount++
                        }
                    }
                    result = result + "mp->${filterList[0].p},lp->${filterList[filterList.size-1].p},fuR->${fuCount}/${filterList.size},requestR->${rCount}/${filterList.size}\n"
                    result = result + "------------------------\n"
                }
                index++
            }
        }
        tvResult.setText(result)
    }
}