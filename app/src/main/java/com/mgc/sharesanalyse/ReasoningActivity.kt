package com.mgc.sharesanalyse

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.SparseArray
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mgc.sharesanalyse.base.Datas
import com.mgc.sharesanalyse.base.RecyclerAdapter
import com.mgc.sharesanalyse.base.ViewHolder
import com.mgc.sharesanalyse.base.sortDescReasoningByDate
import com.mgc.sharesanalyse.entity.ReasoningRevBean
import com.mgc.sharesanalyse.utils.DBUtils
import com.mgc.sharesanalyse.utils.DateUtils
import com.mgc.sharesanalyse.utils.FormatterEnum
import com.mgc.sharesanalyse.utils.ResUtil
import kotlinx.android.synthetic.main.act_reasoning_result.*

class ReasoningActivity : AppCompatActivity() {
    val cliMap = SparseArray<ReasoningRevBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_reasoning_result)
        val type = intent.getIntExtra("TYPE", 1)
        val tb = when (type) {
            1 -> "Reasoning"
            2 -> "All_Reasoning_30"
            3 -> "All_Reasoning_50"
            4 -> Datas.ALL_Reaoning_OC_OO_30
            else -> Datas.ALL_Reaoning_OC_OO_50
        }
        val dataMap = SparseArray<String>()
        val list = DBUtils.getReasoningResult(tb)
        list.sortDescReasoningByDate()
        val removeList = ArrayList<ReasoningRevBean>()
        val countLimit = 600
//        if (type == 2) {
//            for (i in 0 until if (list.size>countLimit) countLimit else list.size){
//                val initList = DBUtils.getReasoningInitAllJudgeResult(tb,list[i])
//                val (fuCount, rCount) = getFuRRCount(initList, 30.toFloat())
//                if (type30Judge(fuCount,rCount, initList)) {
//                    removeList.add(list[i])
//                }
//            }
//            LogUtil.d("list.size->${list.size},${list[0].d}")
//            LogUtil.d("removeList.size->${list.size}")
//            removeList.forEach {
//                list.remove(it)
//            }
//            LogUtil.d("list.size->${list.size}")
//        }

        for (i in 0 until list.size) {
            dataMap.put(i, null)
            cliMap.put(i, list[i])
        }

        val adapter = object : RecyclerAdapter<String>(this, R.layout.item_tv, dataMap) {
            override fun convert(vh: ViewHolder, t: String?, pos: Int) {
                if (null == dataMap[pos]) {
                    val pair  = getShowResult(type, tb, cliMap[pos],pos)
                    val result = pair.first
                    cliMap[pos].isShowRed = pair.second
                    dataMap.put(pos, result)
                    vh.setText(R.id.tvResult, result)
                } else {
                    vh.setText(R.id.tvResult, t)
                }
                vh.getView<TextView>(R.id.tvResult).setTextColor(if (cliMap[pos].show==1) ResUtil.getC(R.color.blue) else (if (cliMap[pos].isShowRed) ResUtil.getC(R.color.holo_red_light) else ResUtil.getC(R.color.text_C)))
                vh.setOnClickListener(R.id.tvResult,object:View.OnClickListener{
                    override fun onClick(v: View?) {
                        if (null != cliMap[pos] && type != 1) {
                            val intent =
                                Intent(this@ReasoningActivity, ReasoningDetailActivity::class.java)
                            intent.putExtra("BEAN", cliMap[pos])
                            intent.putExtra("TB", tb)
                            startActivity(intent)
                        }
                    }

                })
                vh.setOnLongClickListener(R.id.tvResult,object : View.OnLongClickListener {
                    override fun onLongClick(p0: View?): Boolean {
                        val builder = AlertDialog.Builder(this@ReasoningActivity)
                        builder.setMessage(if (cliMap[pos].show==0)"collect?" else "cancell collect?")
                        builder.setPositiveButton("comfir",
                            object : DialogInterface.OnClickListener {
                                override fun onClick(p0: DialogInterface?, p1: Int) {
                                    cliMap[pos].show = if (cliMap[pos].show==0) 1 else 0
                                    DBUtils.updateReasoningShow(tb, cliMap[pos])
                                    recycleView.adapter?.notifyItemChanged(pos)
                                }

                            })
                        builder.create().show()
                        return true
                    }

                })
            }

        }
        recycleView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recycleView.adapter = adapter
    }

    private fun getShowResult(
        type: Int,
        tb: String,
        it: ReasoningRevBean,
        pos: Int
    ): Pair<String, Boolean> {
        var result = ""
        if (type == 2 || type == 3) {
            val (filterList, fList2, fList3) = DBUtils.getReasoningAllJudgeResult(tb, it)
            val requestP = when (type) {
                2 -> 30.toFloat()
                else -> 50.toFloat()
            }
            val (fuCount, rCount) = getFuRRCount(filterList, requestP)
            var needContinue = true
            result = result + getBaseResult(it)
//            if (type == 2 && type30Judge(fuCount, rCount, filterList)) {
//                needContinue = false
//                cliMap.put(pos,null)
//            }
            if (needContinue) {
                val (fu1Count, r1Count) = getFuRRCount(fList2, requestP)
//                if (type == 3 && fuCount * 2 >= filterList.size && fu1Count > 0) {
//                    needContinue = false
//                    cliMap.put(pos, null)
//                }
                if (needContinue) {
                    val (fu2Count, r2Count) = getFuRRCount(fList3, requestP)
                    result = getResultStr(result, filterList, fuCount, rCount)
                    result = getResultStr(result, fList2, fu1Count, r1Count)
                    result = getResultStr(result, fList3, fu2Count, r2Count)
                }
            }
        } else if (type == 4 || type == 5) {
            val ocooList = DBUtils.getReasoningOOOCAllJudgeResult(tb, it)
            val requestP = when (type) {
                4 -> 30.toFloat()
                else -> 50.toFloat()
            }
            val (fuCount, rCount) = getFuRRCount(ocooList, requestP)
            result = result + getBaseResult(it)
            result = getResultStr(result, ocooList, fuCount, rCount)
        } else {
            result = result + getBaseResult(it)
        }
        val showRed = it.lp < 0 && it.p >= 0 && it.d_D.equals("null")
        result = result + "------------------------------------------------\n"
        return Pair(result,showRed)
    }

    private var dayTS = 24 * 60 * 60 * 1000

    private fun getBaseResult(it: ReasoningRevBean): String {
        var ddStr = ""
        if (it.d_D.equals("null")) {
            val workdayList = ArrayList<String>()
            var dTimeStamp = DateUtils.parse(it.d,FormatterEnum.YYYYMMDD)
            while (workdayList.size < Datas.REV_DAYS) {
                dTimeStamp = dTimeStamp + dayTS
                if (DateUtils.isWeekDay(dTimeStamp).first) {
                    workdayList.add(DateUtils.format(dTimeStamp, FormatterEnum.MMDD))
                }
            }
            val cp = DBUtils.queryCPByCodeAndDate(it.code.toString(),it.d)
            ddStr = "R_D_D->${workdayList[workdayList.size-1]},CP->$cp,"
        }

        return "${it.n},c-->${it.code},d-->${it.d},p-->${it.p}\n" + "dd->${it.d_D},${ddStr}lp->${it.lp},mp->${it.mp},p->${it.p}\n"

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