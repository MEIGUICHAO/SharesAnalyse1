package com.mgc.sharesanalyse

import android.os.Bundle
import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mgc.sharesanalyse.base.RecyclerAdapter
import com.mgc.sharesanalyse.base.ViewHolder
import com.mgc.sharesanalyse.base.sortDescReasoningByDate
import com.mgc.sharesanalyse.entity.ReasoningRevBean
import com.mgc.sharesanalyse.utils.DBUtils
import com.mgc.sharesanalyse.utils.LogUtil
import kotlinx.android.synthetic.main.act_reasoning_result.*

class ReasoningActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_reasoning_result)
        val type = intent.getIntExtra("TYPE", 1)
        val tb = when (type) {
            1 -> "Reasoning"
            2 -> "All_Reasoning_30"
            else -> "All_Reasoning_50"
        }
        val dataMap = SparseArray<String>()
        val cliMap = SparseArray<ReasoningRevBean>()
        val list = DBUtils.getReasoningResult(tb)
        list.sortDescReasoningByDate()
        val removeList = ArrayList<ReasoningRevBean>()
        if (type == 2) {
            for (i in 0 until if (list.size>200) 200 else list.size){
                val initList = DBUtils.getReasoningInitAllJudgeResult(tb,list[i])
                val (fuCount, rCount) = getFuRRCount(initList, 30.toFloat())
                if (type30Judge(fuCount,rCount, initList)) {
                    removeList.add(list[i])
                }
            }
            LogUtil.d("list.size->${list.size},${list[0].d}")
            LogUtil.d("removeList.size->${list.size}")
            removeList.forEach {
                list.remove(it)
            }
            LogUtil.d("list.size->${list.size}")
        }

        for (i in 0 until list.size) {
            dataMap.put(i, null)
            cliMap.put(i, list[i])
        }

        val adapter = object : RecyclerAdapter<String>(this, R.layout.item_tv, dataMap) {
            override fun convert(vh: ViewHolder, t: String?, pos: Int) {
                if (null == dataMap[pos]) {
                    val result = getShowResult(type, tb, cliMap[pos])
                    dataMap.put(pos, result)
                    vh.setText(R.id.tvResult, result)
                } else {
                    vh.setText(R.id.tvResult, t)
                }
            }

        }
        recycleView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recycleView.adapter = adapter
    }

    private fun getShowResult(
        type: Int,
        tb: String,
        it: ReasoningRevBean
    ): String {
        var result = ""
        if (type == 2 || type == 3) {
            val (filterList, fList2, fList3) = DBUtils.getReasoningAllJudgeResult(tb, it)
            val requestP = when (type) {
                2 -> 30.toFloat()
                else -> 50.toFloat()
            }
            val (fuCount, rCount) = getFuRRCount(filterList, requestP)
            var needContinue = true
            result = result + "${it.n},c-->${it.code},d-->${it.d},p-->${it.p}\n"
            if (type == 2 && type30Judge(fuCount,rCount, filterList)) {
                needContinue = false
            }
            if (needContinue) {
                val (fu1Count, r1Count) = getFuRRCount(fList2, requestP)
                if (type == 3 && fu1Count > 0 && fList2.size < 3) {
                    needContinue = false
                }
                if (needContinue) {
                    val (fu2Count, r2Count) = getFuRRCount(fList3, requestP)
                    result = getResultStr(result, filterList, fuCount, rCount)
                    result = getResultStr(result, fList2, fu1Count, r1Count)
                    result = getResultStr(result, fList3, fu2Count, r2Count)
                }
            }
        }
        result = result + "------------------------------------------------\n"
        return result
    }

    private fun type30Judge(
        fuCount: Int,
        rCount: Int,
        filterList: ArrayList<ReasoningRevBean>
    ) = rCount * 3 <= filterList.size && fuCount > 0

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