package com.mgc.sharesanalyse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mgc.sharesanalyse.base.sortDescReasoningByDate
import com.mgc.sharesanalyse.entity.ReasoningRevBean
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
            if (index < 6000) {
                if (type == 2 || type == 3) {
                    val (filterList,fList2,fList3) = DBUtils.getReasoningAllJudgeResult(tb,it)
                    val requestP = when(type){
                        2-> 30.toFloat()
                        else->50.toFloat()
                    }
                    var (fuCount, rCount) = getFuRRCount(filterList, requestP)
                    var (fu1Count, r1Count) = getFuRRCount(fList2, requestP)
                    var (fu2Count, r2Count) = getFuRRCount(fList3, requestP)
                    result = result + "${it.n},c-->${it.code},d-->${it.d},p-->${it.p}\n"
                    result = getResultStr(result, filterList, fuCount, rCount)
                    result = getResultStr(result, fList2, fu1Count, r1Count)
                    result = getResultStr(result, fList3, fu2Count, r2Count)
                    result = result + "------------------------\n"
                }
                index++
            }
        }
        tvResult.setText(result)
    }

    private fun getResultStr(
        result: String,
        filterList: ArrayList<ReasoningRevBean>,
        fuCount: Int,
        rCount: Int
    ): String {
        var result1 = result
        result1 =
            result1 + "mp->${filterList[0].p},lp->${filterList[filterList.size - 1].p},fuR->${fuCount}/${filterList.size},requestR->${rCount}/${filterList.size}\n"
        return result1
    }

    private fun getFuRRCount(
        filterList: ArrayList<ReasoningRevBean>,
        requestP: Float
    ): Pair<Int, Int> {
        var fuCount = 0
        var rCount = 0
        filterList.forEach {
            if (it.p < 0) {
                fuCount++
            } else if (it.p >= requestP) {
                rCount++
            }
        }
        return Pair(fuCount, rCount)
    }
}