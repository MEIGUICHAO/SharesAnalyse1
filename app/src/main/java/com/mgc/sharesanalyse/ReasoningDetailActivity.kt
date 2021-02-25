package com.mgc.sharesanalyse

import android.os.Bundle
import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mgc.sharesanalyse.base.RecyclerAdapter
import com.mgc.sharesanalyse.base.UniversalItemDecoration
import com.mgc.sharesanalyse.base.ViewHolder
import com.mgc.sharesanalyse.entity.ReasoningRevBean
import com.mgc.sharesanalyse.utils.DBUtils
import com.mgc.sharesanalyse.utils.LogUtil
import com.mgc.sharesanalyse.utils.ResUtil
import kotlinx.android.synthetic.main.act_reasoning_result.*

class ReasoningDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_reasoning_result)
        val tb = intent.getStringExtra("TB")
        val bean = intent.getSerializableExtra("BEAN") as ReasoningRevBean
        val dataMap = SparseArray<String>()

        if (tb!!.contains("OC_OO")) {

            val ocooList = DBUtils.getReasoningOOOCAllJudgeResult(tb, bean)

            for (i in 0 until ocooList.size) {
                LogUtil.d("--$i---")
                var endStr = "\n---------"
                dataMap.put(
                    i,
                    "${ocooList[i].n},c-->${ocooList[i].code},d-->${ocooList[i].d},p-->${ocooList[i].p}" +
                            "\n lp-->${ocooList[i].lp},mp-->${ocooList[i].mp}" +
                            "\n ao-->${ocooList[i].after_O_P},ac-->${ocooList[i].after_C_P}" +
                            "${endStr}"
                )
            }

        } else {

            val list = ArrayList<ReasoningRevBean>()
            val (filterList, fList2, fList3) = DBUtils.getReasoningAllJudgeResult(tb, bean)
            list.addAll(filterList)
            list.addAll(fList2)
            list.addAll(fList3)
            LogUtil.d("list.size->${list.size}")
            for (i in 0 until list.size) {
                LogUtil.d("--$i---")
                var addStr = ""
                if (i == 0) {
                    addStr = "----Filter1-----\n"
                }
                if (i == filterList.size) {
                    addStr = "----Filter2-----\n"
                }
                if (i == (filterList.size + fList2.size)) {
                    addStr = "----Filter3-----\n"
                }
                var endStr = ""
                if (i < filterList.size) {
                    endStr = "\n${list[i].mA_1}&&${list[i].mA_3}&&${list[i].mA_5}\n------------"
                }
                dataMap.put(
                    i,
                    "${addStr}${list[i].n},c-->${list[i].code},d-->${list[i].d},p-->${list[i].p}" +
                            "\n lp-->${list[i].lp},mp-->${list[i].mp}" +
                            "\n ao-->${list[i].after_O_P},ac-->${list[i].after_C_P}" +
                            "${endStr}"
                )
            }
        }

        val adapter = object : RecyclerAdapter<String>(this, R.layout.item_tv, dataMap) {
            override fun convert(vh: ViewHolder, t: String?, pos: Int) {
                vh.setText(R.id.tvResult, t)
            }
        }

//        recycleView.addItemDecoration(object : UniversalItemDecoration() {
//            override fun getItemOffsets(position: Int): Decoration {
//
//                val decoration = ColorDecoration()
//                if (position == 0 || position == filterList.size || position == (filterList.size + fList2.size) - 1) {
//                    decoration.decorationColor = ResUtil.getC(R.color.transparent)
//                    decoration.top =28;
//                    val text = when (position) {
//                        0 -> "Filter1-->"
//                        filterList.size -> "Filter2-->"
//                        else->"Filter3-->"
//                    }
//                    decoration.drawText(text)
//                }
//
//                return decoration
//            }
//
//        })
        recycleView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        recycleView.adapter = adapter

    }
}