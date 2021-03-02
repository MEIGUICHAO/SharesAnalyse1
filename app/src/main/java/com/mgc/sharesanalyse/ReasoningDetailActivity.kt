package com.mgc.sharesanalyse

import android.os.Bundle
import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mgc.sharesanalyse.base.*
import com.mgc.sharesanalyse.entity.CodeHDDBean
import com.mgc.sharesanalyse.entity.ReasoningRevBean
import com.mgc.sharesanalyse.utils.*
import kotlinx.android.synthetic.main.act_reasoning_result.*

class ReasoningDetailActivity : AppCompatActivity() {
    var isOCOO = true
    val showMap = SparseArray<String>()
    lateinit var mFilterList: ArrayList<ReasoningRevBean>
    lateinit var mFList2: ArrayList<ReasoningRevBean>
    lateinit var mFList3: ArrayList<ReasoningRevBean>
    lateinit var ocooList: ArrayList<ReasoningRevBean>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_reasoning_result)
        val tb = intent.getStringExtra("TB")
        val bean = intent.getSerializableExtra("BEAN") as ReasoningRevBean
        val dataMap = SparseArray<String>()

        val mlist = getCHDDDateList()
        isOCOO = tb!!.contains("OC_OO")
        ocooList = DBUtils.getReasoningOOOCAllJudgeResult(tb, bean)

        if (tb.contains("OC_OO")) {


            for (i in 0 until ocooList.size) {
                LogUtil.d("--$i---")
                var endStr = "\n---------"
                dataMap.put(
                    i,"${ocooList[i].code}###${ocooList[i].d}###${ocooList[i].n}###${ocooList[i].p}###" +
                            "\nlp-->${ocooList[i].lp},mp-->${ocooList[i].mp}###" +
                            "\nao-->${ocooList[i].after_O_P},ac-->${ocooList[i].after_C_P}$endStr"
                )
                showMap.put(i,null)
            }

        } else {

            val list = ArrayList<ReasoningRevBean>()
            val (filterList, fList2, fList3) = DBUtils.getReasoningAllJudgeResult(tb, bean)
            list.addAll(filterList)
            list.addAll(fList2)
            list.addAll(fList3)
            mFilterList = filterList
            mFList2 = fList2
            mFList3 = fList3
            LogUtil.d("list.size->${list.size}")
            for (i in 0 until list.size) {
                LogUtil.d("--$i---")

                var endStr = ""
                if (i < filterList.size) {
                    endStr = "\n${list[i].mA_1}&&${list[i].mA_3}&&${list[i].mA_5}\n------------"
                }
                dataMap.put(
                    i,"${list[i].code}###${list[i].d}###${list[i].n}###${list[i].p}###\nlp-->${list[i].lp},mp-->${list[i].mp}###\nao-->${list[i].after_O_P},ac-->${list[i].after_C_P}$endStr"
//                    "${addStr}${list[i].n},c-->${list[i].code},d-->${list[i].d},p-->${list[i].p}" +
//                            "\n lp-->${list[i].lp},mp-->${list[i].mp}" +
//                            "\n ao-->${list[i].after_O_P},ac-->${list[i].after_C_P}" +dateRangeInfo+
//                            "${endStr}"
                )
                showMap.put(i,null)
            }
        }

        val adapter = object : RecyclerAdapter<String>(this, R.layout.item_tv, dataMap) {
            override fun convert(vh: ViewHolder, t: String?, pos: Int) {
                if (null == showMap.get(pos)) {
                    val array = t!!.split("###")
                    val codeDateList = getCHDDCodeAllList(mlist, array[0])
                    var dateRangeInfo = getDateRangeInfo(codeDateList, array[1])
                    dateRangeInfo = "${array[2]},c-->${array[0]},d-->${array[1]},p-->${array[3]}${array[4]}$dateRangeInfo${array[5]}"
                    if (!isOCOO) {
                        var addStr = ""
                        if (pos == 0) {
                            addStr = "----Filter1-----\n"
                        }
                        if (pos == mFilterList.size) {
                            addStr = "----Filter2-----\n"
                        }
                        if (pos == (mFilterList.size + mFList2.size)) {
                            addStr = "----Filter3-----\n"
                        }
                        dateRangeInfo = addStr + dateRangeInfo
                    }
                    showMap.put(pos,dateRangeInfo)
                }
                vh.setText(R.id.tvResult, showMap.get(pos))
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

    private fun getDateRangeInfo(
        codeDateList: ArrayList<CodeHDDBean>,
        date:String,isOOOC:Boolean = true
    ): String {
        var dateRangeInfo = ""
        var dateBefor = 70
        if (!isOOOC) {
            dateBefor = 72
        }
        for (x in codeDateList.size - 1 downTo 0) {
            if (codeDateList[x].date.equals(date)) {
                dateRangeInfo = "\n${codeDateList[x - dateBefor].date}-->${codeDateList[x].date}"
                break
            }

        }
        return dateRangeInfo
    }


    private fun getCHDDCodeAllList(
        mList: ArrayList<String>,
        code: String
    ): ArrayList<CodeHDDBean> {
        val mCHDDList = ArrayList<CodeHDDBean>()
        mList.forEach {
            val date = "20${it}01"
            var month = DateUtils.changeFormatter(
                DateUtils.parse(date, FormatterEnum.YYYYMMDD),
                FormatterEnum.YYMM
            ).toInt()
            val dbName = code.toCodeHDD(month.toString(), FormatterEnum.YYMM)
            val codeList = DBUtils.queryCHDDByTableName("DD_${code.toCompleteCode()}", dbName)
            codeList?.let {
                mCHDDList.addAll(it)
            }
        }
        return mCHDDList
    }


    private fun getCHDDDateList(): ArrayList<String> {
        val pathList = FileUtil.getFileNameList(Datas.DBPath)
        val mList = ArrayList<String>()
        pathList.forEach {
            if (it.contains("SZ_CHDD_") && !it.contains("journal")) {
                mList.add(it.replace("SZ_CHDD_", ""))
            }
        }
        mList.sortStringDateAsc(FormatterEnum.YYMM)

        return mList
    }
}