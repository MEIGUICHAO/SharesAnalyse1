package com.mgc.sharesanalyse.utils

import com.mgc.sharesanalyse.NewApiActivity
import com.mgc.sharesanalyse.base.*
import com.mgc.sharesanalyse.entity.*

object DataSettingUtils {
    lateinit var mActivity: NewApiActivity

    fun setActivity(activity: NewApiActivity) {
        mActivity = activity
    }

    fun setP50FilterMaxMinValue(
        indexType: String,
        tbname: String,
        dFilter: P50FilterBBKJRangeBean.DFilter,
        pair: Pair<String, String>
    ): P50FilterBBKJRangeBean.DFilter {
        val spliteArray = tbname.split("_R_")[0].split("_")
        val day = spliteArray[spliteArray.size - 1].toInt()
//        LogUtil.d("day:$day,indexType:$indexType,min:${pair.first},max:${pair.second}")
        when (indexType) {
            "OM_M" -> {
                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.oM_M = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oM_M = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.oM_M = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oM_M = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.oM_M = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oM_M = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.oM_M = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oM_M = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.oM_M = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oM_M = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.oM_M = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oM_M = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.oM_M = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oM_M = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.oM_M = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oM_M = pair.second.toFloat()
                    }
                }
            }
            "OM_C" -> {
                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.oM_C = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oM_C = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.oM_C = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oM_C = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.oM_C = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oM_C = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.oM_C = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oM_C = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.oM_C = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oM_C = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.oM_C = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oM_C = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.oM_C = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oM_C = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.oM_C = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oM_C = pair.second.toFloat()
                    }
                }
            }
            "OM_P" -> {
                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.oM_P = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oM_P = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.oM_P = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oM_P = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.oM_P = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oM_P = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.oM_P = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oM_P = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.oM_P = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oM_P = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.oM_P = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oM_P = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.oM_P = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oM_P = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.oM_P = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oM_P = pair.second.toFloat()
                    }
                }
            }
            "OM_L" -> {

                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.oM_L = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oM_L = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.oM_L = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oM_L = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.oM_L = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oM_L = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.oM_L = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oM_L = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.oM_L = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oM_L = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.oM_L = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oM_L = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.oM_L = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oM_L = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.oM_L = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oM_L = pair.second.toFloat()
                    }
                }
            }
            "OC_M" -> {

                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.oC_M = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oC_M = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.oC_M = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oC_M = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.oC_M = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oC_M = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.oC_M = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oC_M = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.oC_M = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oC_M = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.oC_M = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oC_M = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.oC_M = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oC_M = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.oC_M = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oC_M = pair.second.toFloat()
                    }
                }
            }
            "OC_C" -> {
                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.oC_C = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oC_C = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.oC_C = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oC_C = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.oC_C = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oC_C = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.oC_C = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oC_C = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.oC_C = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oC_C = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.oC_C = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oC_C = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.oC_C = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oC_C = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.oC_C = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oC_C = pair.second.toFloat()
                    }
                }
            }
            "OC_P" -> {

                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.oC_P = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oC_P = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.oC_P = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oC_P = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.oC_P = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oC_P = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.oC_P = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oC_P = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.oC_P = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oC_P = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.oC_P = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oC_P = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.oC_P = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oC_P = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.oC_P = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oC_P = pair.second.toFloat()
                    }
                }
            }
            "OC_L" -> {

                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.oC_L = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oC_L = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.oC_L = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oC_L = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.oC_L = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oC_L = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.oC_L = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oC_L = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.oC_L = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oC_L = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.oC_L = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oC_L = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.oC_L = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oC_L = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.oC_L = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oC_L = pair.second.toFloat()
                    }
                }
            }
            "OO_M" -> {
                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.oO_M = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oO_M = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.oO_M = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oO_M = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.oO_M = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oO_M = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.oO_M = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oO_M = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.oO_M = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oO_M = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.oO_M = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oO_M = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.oO_M = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oO_M = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.oO_M = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oO_M = pair.second.toFloat()
                    }
                }
            }
            "OO_C" -> {

                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.oO_C = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oO_C = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.oO_C = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oO_C = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.oO_C = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oO_C = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.oO_C = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oO_C = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.oO_C = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oO_C = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.oO_C = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oO_C = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.oO_C = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oO_C = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.oO_C = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oO_C = pair.second.toFloat()
                    }
                }
            }
            "OO_P" -> {
                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.oO_P = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oO_P = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.oO_P = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oO_P = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.oO_P = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oO_P = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.oO_P = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oO_P = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.oO_P = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oO_P = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.oO_P = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oO_P = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.oO_P = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oO_P = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.oO_P = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oO_P = pair.second.toFloat()
                    }
                }
            }
            "OO_L" -> {

                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.oO_L = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oO_L = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.oO_L = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oO_L = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.oO_L = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oO_L = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.oO_L = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oO_L = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.oO_L = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oO_L = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.oO_L = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oO_L = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.oO_L = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oO_L = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.oO_L = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oO_L = pair.second.toFloat()
                    }
                }
            }
            "OL_M" -> {

                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.oL_M = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oL_M = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.oL_M = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oL_M = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.oL_M = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oL_M = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.oL_M = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oL_M = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.oL_M = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oL_M = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.oL_M = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oL_M = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.oL_M = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oL_M = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.oL_M = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oL_M = pair.second.toFloat()
                    }
                }
            }
            "OL_C" -> {

                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.oL_C = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oL_C = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.oL_C = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oL_C = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.oL_C = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oL_C = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.oL_C = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oL_C = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.oL_C = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oL_C = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.oL_C = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oL_C = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.oL_C = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oL_C = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.oL_C = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oL_C = pair.second.toFloat()
                    }
                }
            }
            "OL_P" -> {

                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.oL_P = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oL_P = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.oL_P = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oL_P = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.oL_P = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oL_P = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.oL_P = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oL_P = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.oL_P = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oL_P = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.oL_P = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oL_P = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.oL_P = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oL_P = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.oL_P = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oL_P = pair.second.toFloat()
                    }
                }
            }
            "OL_L" -> {

                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.oL_L = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oL_L = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.oL_L = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oL_L = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.oL_L = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oL_L = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.oL_L = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oL_L = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.oL_L = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oL_L = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.oL_L = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oL_L = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.oL_L = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oL_L = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.oL_L = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oL_L = pair.second.toFloat()
                    }
                }
            }
            "OM_OC" -> {

                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.oM_OC = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oM_OC = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.oM_OC = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oM_OC = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.oM_OC = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oM_OC = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.oM_OC = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oM_OC = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.oM_OC = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oM_OC = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.oM_OC = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oM_OC = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.oM_OC = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oM_OC = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.oM_OC = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oM_OC = pair.second.toFloat()
                    }
                }
            }
            "OM_OP" -> {

                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.oM_OP = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oM_OP = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.oM_OP = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oM_OP = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.oM_OP = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oM_OP = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.oM_OP = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oM_OP = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.oM_OP = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oM_OP = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.oM_OP = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oM_OP = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.oM_OP = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oM_OP = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.oM_OP = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oM_OP = pair.second.toFloat()
                    }
                }
            }
            "OM_OL" -> {

                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.oM_OL = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oM_OL = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.oM_OL = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oM_OL = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.oM_OL = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oM_OL = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.oM_OL = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oM_OL = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.oM_OL = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oM_OL = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.oM_OL = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oM_OL = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.oM_OL = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oM_OL = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.oM_OL = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oM_OL = pair.second.toFloat()
                    }
                }
            }
            "OC_OP" -> {

                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.oC_OP = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oC_OP = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.oC_OP = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oC_OP = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.oC_OP = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oC_OP = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.oC_OP = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oC_OP = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.oC_OP = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oC_OP = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.oC_OP = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oC_OP = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.oC_OP = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oC_OP = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.oC_OP = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oC_OP = pair.second.toFloat()
                    }
                }
            }
            "OC_OL" -> {

                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.oC_OL = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oC_OL = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.oC_OL = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oC_OL = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.oC_OL = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oC_OL = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.oC_OL = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oC_OL = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.oC_OL = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oC_OL = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.oC_OL = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oC_OL = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.oC_OL = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oC_OL = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.oC_OL = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oC_OL = pair.second.toFloat()
                    }
                }
            }
            "OP_OL" -> {
                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.oP_OL = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oP_OL = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.oP_OL = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oP_OL = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.oP_OL = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oP_OL = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.oP_OL = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oP_OL = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.oP_OL = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oP_OL = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.oP_OL = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oP_OL = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.oP_OL = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oP_OL = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.oP_OL = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oP_OL = pair.second.toFloat()
                    }
                }
            }
            "M_C" -> {

                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.m_C = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.m_C = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.m_C = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.m_C = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.m_C = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.m_C = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.m_C = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.m_C = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.m_C = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.m_C = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.m_C = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.m_C = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.m_C = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.m_C = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.m_C = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.m_C = pair.second.toFloat()
                    }
                }
            }
            "M_P" -> {

                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.m_P = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.m_P = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.m_P = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.m_P = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.m_P = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.m_P = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.m_P = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.m_P = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.m_P = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.m_P = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.m_P = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.m_P = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.m_P = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.m_P = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.m_P = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.m_P = pair.second.toFloat()
                    }
                }
            }
            "M_L" -> {

                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.m_L = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.m_L = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.m_L = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.m_L = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.m_L = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.m_L = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.m_L = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.m_L = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.m_L = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.m_L = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.m_L = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.m_L = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.m_L = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.m_L = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.m_L = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.m_L = pair.second.toFloat()
                    }
                }
            }
            "C_P" -> {

                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.c_P = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.c_P = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.c_P = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.c_P = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.c_P = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.c_P = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.c_P = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.c_P = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.c_P = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.c_P = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.c_P = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.c_P = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.c_P = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.c_P = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.c_P = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.c_P = pair.second.toFloat()
                    }
                }
            }
            "C_L" -> {

                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.c_L = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.c_L = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.c_L = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.c_L = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.c_L = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.c_L = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.c_L = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.c_L = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.c_L = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.c_L = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.c_L = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.c_L = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.c_L = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.c_L = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.c_L = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.c_L = pair.second.toFloat()
                    }
                }
            }
            "P_L" -> {
                when (day) {
                    3 -> {
                        dFilter.d03Bean.minBean.p_L = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.p_L = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05Bean.minBean.p_L = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.p_L = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10Bean.minBean.p_L = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.p_L = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15Bean.minBean.p_L = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.p_L = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20Bean.minBean.p_L = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.p_L = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25Bean.minBean.p_L = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.p_L = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30Bean.minBean.p_L = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.p_L = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36Bean.minBean.p_L = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.p_L = pair.second.toFloat()
                    }
                }
            }
            "S_A_TR" -> {
                when (day) {
                    3 -> {
                        dFilter.d03TRBean.minBean.s_A_TR = pair.first.toFloat()
                        dFilter.d03TRBean.maxBean.s_A_TR = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05TRBean.minBean.s_A_TR = pair.first.toFloat()
                        dFilter.d05TRBean.maxBean.s_A_TR = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10TRBean.minBean.s_A_TR = pair.first.toFloat()
                        dFilter.d10TRBean.maxBean.s_A_TR = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15TRBean.minBean.s_A_TR = pair.first.toFloat()
                        dFilter.d15TRBean.maxBean.s_A_TR = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20TRBean.minBean.s_A_TR = pair.first.toFloat()
                        dFilter.d20TRBean.maxBean.s_A_TR = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25TRBean.minBean.s_A_TR = pair.first.toFloat()
                        dFilter.d25TRBean.maxBean.s_A_TR = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30TRBean.minBean.s_A_TR = pair.first.toFloat()
                        dFilter.d30TRBean.maxBean.s_A_TR = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36TRBean.minBean.s_A_TR = pair.first.toFloat()
                        dFilter.d36TRBean.maxBean.s_A_TR = pair.second.toFloat()
                    }
                }
            }
            "S_R_TR" -> {
                when (day) {
                    3 -> {
                        dFilter.d03TRBean.minBean.s_R_TR = pair.first.toFloat()
                        dFilter.d03TRBean.maxBean.s_R_TR = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05TRBean.minBean.s_R_TR = pair.first.toFloat()
                        dFilter.d05TRBean.maxBean.s_R_TR = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10TRBean.minBean.s_R_TR = pair.first.toFloat()
                        dFilter.d10TRBean.maxBean.s_R_TR = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15TRBean.minBean.s_R_TR = pair.first.toFloat()
                        dFilter.d15TRBean.maxBean.s_R_TR = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20TRBean.minBean.s_R_TR = pair.first.toFloat()
                        dFilter.d20TRBean.maxBean.s_R_TR = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25TRBean.minBean.s_R_TR = pair.first.toFloat()
                        dFilter.d25TRBean.maxBean.s_R_TR = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30TRBean.minBean.s_R_TR = pair.first.toFloat()
                        dFilter.d30TRBean.maxBean.s_R_TR = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36TRBean.minBean.s_R_TR = pair.first.toFloat()
                        dFilter.d36TRBean.maxBean.s_R_TR = pair.second.toFloat()
                    }
                }
            }
            "S_B_TR" -> {

                when (day) {
                    3 -> {
                        dFilter.d03TRBean.minBean.s_B_TR = pair.first.toFloat()
                        dFilter.d03TRBean.maxBean.s_B_TR = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05TRBean.minBean.s_B_TR = pair.first.toFloat()
                        dFilter.d05TRBean.maxBean.s_B_TR = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10TRBean.minBean.s_B_TR = pair.first.toFloat()
                        dFilter.d10TRBean.maxBean.s_B_TR = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15TRBean.minBean.s_B_TR = pair.first.toFloat()
                        dFilter.d15TRBean.maxBean.s_B_TR = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20TRBean.minBean.s_B_TR = pair.first.toFloat()
                        dFilter.d20TRBean.maxBean.s_B_TR = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25TRBean.minBean.s_B_TR = pair.first.toFloat()
                        dFilter.d25TRBean.maxBean.s_B_TR = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30TRBean.minBean.s_B_TR = pair.first.toFloat()
                        dFilter.d30TRBean.maxBean.s_B_TR = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36TRBean.minBean.s_B_TR = pair.first.toFloat()
                        dFilter.d36TRBean.maxBean.s_B_TR = pair.second.toFloat()
                    }
                }
            }
            "S_C_TR" -> {

                when (day) {
                    3 -> {
                        dFilter.d03TRBean.minBean.s_C_TR = pair.first.toFloat()
                        dFilter.d03TRBean.maxBean.s_C_TR = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05TRBean.minBean.s_C_TR = pair.first.toFloat()
                        dFilter.d05TRBean.maxBean.s_C_TR = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10TRBean.minBean.s_C_TR = pair.first.toFloat()
                        dFilter.d10TRBean.maxBean.s_C_TR = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15TRBean.minBean.s_C_TR = pair.first.toFloat()
                        dFilter.d15TRBean.maxBean.s_C_TR = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20TRBean.minBean.s_C_TR = pair.first.toFloat()
                        dFilter.d20TRBean.maxBean.s_C_TR = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25TRBean.minBean.s_C_TR = pair.first.toFloat()
                        dFilter.d25TRBean.maxBean.s_C_TR = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30TRBean.minBean.s_C_TR = pair.first.toFloat()
                        dFilter.d30TRBean.maxBean.s_C_TR = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36TRBean.minBean.s_C_TR = pair.first.toFloat()
                        dFilter.d36TRBean.maxBean.s_C_TR = pair.second.toFloat()
                    }
                }
            }
            "K_A_TR" -> {

                when (day) {
                    3 -> {
                        dFilter.d03TRBean.minBean.k_A_TR = pair.first.toFloat()
                        dFilter.d03TRBean.maxBean.k_A_TR = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05TRBean.minBean.k_A_TR = pair.first.toFloat()
                        dFilter.d05TRBean.maxBean.k_A_TR = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10TRBean.minBean.k_A_TR = pair.first.toFloat()
                        dFilter.d10TRBean.maxBean.k_A_TR = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15TRBean.minBean.k_A_TR = pair.first.toFloat()
                        dFilter.d15TRBean.maxBean.k_A_TR = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20TRBean.minBean.k_A_TR = pair.first.toFloat()
                        dFilter.d20TRBean.maxBean.k_A_TR = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25TRBean.minBean.k_A_TR = pair.first.toFloat()
                        dFilter.d25TRBean.maxBean.k_A_TR = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30TRBean.minBean.k_A_TR = pair.first.toFloat()
                        dFilter.d30TRBean.maxBean.k_A_TR = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36TRBean.minBean.k_A_TR = pair.first.toFloat()
                        dFilter.d36TRBean.maxBean.k_A_TR = pair.second.toFloat()
                    }
                }
            }
            "K_R_TR" -> {

                when (day) {
                    3 -> {
                        dFilter.d03TRBean.minBean.k_R_TR = pair.first.toFloat()
                        dFilter.d03TRBean.maxBean.k_R_TR = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05TRBean.minBean.k_R_TR = pair.first.toFloat()
                        dFilter.d05TRBean.maxBean.k_R_TR = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10TRBean.minBean.k_R_TR = pair.first.toFloat()
                        dFilter.d10TRBean.maxBean.k_R_TR = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15TRBean.minBean.k_R_TR = pair.first.toFloat()
                        dFilter.d15TRBean.maxBean.k_R_TR = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20TRBean.minBean.k_R_TR = pair.first.toFloat()
                        dFilter.d20TRBean.maxBean.k_R_TR = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25TRBean.minBean.k_R_TR = pair.first.toFloat()
                        dFilter.d25TRBean.maxBean.k_R_TR = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30TRBean.minBean.k_R_TR = pair.first.toFloat()
                        dFilter.d30TRBean.maxBean.k_R_TR = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36TRBean.minBean.k_R_TR = pair.first.toFloat()
                        dFilter.d36TRBean.maxBean.k_R_TR = pair.second.toFloat()
                    }
                }
            }
            "K_B_TR" -> {

                when (day) {
                    3 -> {
                        dFilter.d03TRBean.minBean.k_B_TR = pair.first.toFloat()
                        dFilter.d03TRBean.maxBean.k_B_TR = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05TRBean.minBean.k_B_TR = pair.first.toFloat()
                        dFilter.d05TRBean.maxBean.k_B_TR = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10TRBean.minBean.k_B_TR = pair.first.toFloat()
                        dFilter.d10TRBean.maxBean.k_B_TR = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15TRBean.minBean.k_B_TR = pair.first.toFloat()
                        dFilter.d15TRBean.maxBean.k_B_TR = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20TRBean.minBean.k_B_TR = pair.first.toFloat()
                        dFilter.d20TRBean.maxBean.k_B_TR = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25TRBean.minBean.k_B_TR = pair.first.toFloat()
                        dFilter.d25TRBean.maxBean.k_B_TR = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30TRBean.minBean.k_B_TR = pair.first.toFloat()
                        dFilter.d30TRBean.maxBean.k_B_TR = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36TRBean.minBean.k_B_TR = pair.first.toFloat()
                        dFilter.d36TRBean.maxBean.k_B_TR = pair.second.toFloat()
                    }
                }
            }
            "K_C_TR" -> {

                when (day) {
                    3 -> {
                        dFilter.d03TRBean.minBean.k_C_TR = pair.first.toFloat()
                        dFilter.d03TRBean.maxBean.k_C_TR = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05TRBean.minBean.k_C_TR = pair.first.toFloat()
                        dFilter.d05TRBean.maxBean.k_C_TR = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10TRBean.minBean.k_C_TR = pair.first.toFloat()
                        dFilter.d10TRBean.maxBean.k_C_TR = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15TRBean.minBean.k_C_TR = pair.first.toFloat()
                        dFilter.d15TRBean.maxBean.k_C_TR = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20TRBean.minBean.k_C_TR = pair.first.toFloat()
                        dFilter.d20TRBean.maxBean.k_C_TR = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25TRBean.minBean.k_C_TR = pair.first.toFloat()
                        dFilter.d25TRBean.maxBean.k_C_TR = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30TRBean.minBean.k_C_TR = pair.first.toFloat()
                        dFilter.d30TRBean.maxBean.k_C_TR = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36TRBean.minBean.k_C_TR = pair.first.toFloat()
                        dFilter.d36TRBean.maxBean.k_C_TR = pair.second.toFloat()
                    }
                }
            }
            "K_SL_A_TR" -> {

                when (day) {
                    3 -> {
                        dFilter.d03TRBean.minBean.k_SL_A_TR = pair.first.toFloat()
                        dFilter.d03TRBean.maxBean.k_SL_A_TR = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05TRBean.minBean.k_SL_A_TR = pair.first.toFloat()
                        dFilter.d05TRBean.maxBean.k_SL_A_TR = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10TRBean.minBean.k_SL_A_TR = pair.first.toFloat()
                        dFilter.d10TRBean.maxBean.k_SL_A_TR = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15TRBean.minBean.k_SL_A_TR = pair.first.toFloat()
                        dFilter.d15TRBean.maxBean.k_SL_A_TR = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20TRBean.minBean.k_SL_A_TR = pair.first.toFloat()
                        dFilter.d20TRBean.maxBean.k_SL_A_TR = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25TRBean.minBean.k_SL_A_TR = pair.first.toFloat()
                        dFilter.d25TRBean.maxBean.k_SL_A_TR = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30TRBean.minBean.k_SL_A_TR = pair.first.toFloat()
                        dFilter.d30TRBean.maxBean.k_SL_A_TR = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36TRBean.minBean.k_SL_A_TR = pair.first.toFloat()
                        dFilter.d36TRBean.maxBean.k_SL_A_TR = pair.second.toFloat()
                    }
                }
            }
            "K_SL_R_TR" -> {

                when (day) {
                    3 -> {
                        dFilter.d03TRBean.minBean.k_SL_R_TR = pair.first.toFloat()
                        dFilter.d03TRBean.maxBean.k_SL_R_TR = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05TRBean.minBean.k_SL_R_TR = pair.first.toFloat()
                        dFilter.d05TRBean.maxBean.k_SL_R_TR = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10TRBean.minBean.k_SL_R_TR = pair.first.toFloat()
                        dFilter.d10TRBean.maxBean.k_SL_R_TR = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15TRBean.minBean.k_SL_R_TR = pair.first.toFloat()
                        dFilter.d15TRBean.maxBean.k_SL_R_TR = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20TRBean.minBean.k_SL_R_TR = pair.first.toFloat()
                        dFilter.d20TRBean.maxBean.k_SL_R_TR = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25TRBean.minBean.k_SL_R_TR = pair.first.toFloat()
                        dFilter.d25TRBean.maxBean.k_SL_R_TR = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30TRBean.minBean.k_SL_R_TR = pair.first.toFloat()
                        dFilter.d30TRBean.maxBean.k_SL_R_TR = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36TRBean.minBean.k_SL_R_TR = pair.first.toFloat()
                        dFilter.d36TRBean.maxBean.k_SL_R_TR = pair.second.toFloat()
                    }
                }
            }
            "K_SL_B_TR" -> {

                when (day) {
                    3 -> {
                        dFilter.d03TRBean.minBean.k_SL_B_TR = pair.first.toFloat()
                        dFilter.d03TRBean.maxBean.k_SL_B_TR = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05TRBean.minBean.k_SL_B_TR = pair.first.toFloat()
                        dFilter.d05TRBean.maxBean.k_SL_B_TR = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10TRBean.minBean.k_SL_B_TR = pair.first.toFloat()
                        dFilter.d10TRBean.maxBean.k_SL_B_TR = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15TRBean.minBean.k_SL_B_TR = pair.first.toFloat()
                        dFilter.d15TRBean.maxBean.k_SL_B_TR = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20TRBean.minBean.k_SL_B_TR = pair.first.toFloat()
                        dFilter.d20TRBean.maxBean.k_SL_B_TR = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25TRBean.minBean.k_SL_B_TR = pair.first.toFloat()
                        dFilter.d25TRBean.maxBean.k_SL_B_TR = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30TRBean.minBean.k_SL_B_TR = pair.first.toFloat()
                        dFilter.d30TRBean.maxBean.k_SL_B_TR = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36TRBean.minBean.k_SL_B_TR = pair.first.toFloat()
                        dFilter.d36TRBean.maxBean.k_SL_B_TR = pair.second.toFloat()
                    }
                }
            }
            "K_SL_C_TR" -> {
                when (day) {
                    3 -> {
                        dFilter.d03TRBean.minBean.k_SL_C_TR = pair.first.toFloat()
                        dFilter.d03TRBean.maxBean.k_SL_C_TR = pair.second.toFloat()
                    }
                    5 -> {
                        dFilter.d05TRBean.minBean.k_SL_C_TR = pair.first.toFloat()
                        dFilter.d05TRBean.maxBean.k_SL_C_TR = pair.second.toFloat()
                    }
                    10 -> {
                        dFilter.d10TRBean.minBean.k_SL_C_TR = pair.first.toFloat()
                        dFilter.d10TRBean.maxBean.k_SL_C_TR = pair.second.toFloat()
                    }
                    15 -> {
                        dFilter.d15TRBean.minBean.k_SL_C_TR = pair.first.toFloat()
                        dFilter.d15TRBean.maxBean.k_SL_C_TR = pair.second.toFloat()
                    }
                    20 -> {
                        dFilter.d20TRBean.minBean.k_SL_C_TR = pair.first.toFloat()
                        dFilter.d20TRBean.maxBean.k_SL_C_TR = pair.second.toFloat()
                    }
                    25 -> {
                        dFilter.d25TRBean.minBean.k_SL_C_TR = pair.first.toFloat()
                        dFilter.d25TRBean.maxBean.k_SL_C_TR = pair.second.toFloat()
                    }
                    30 -> {
                        dFilter.d30TRBean.minBean.k_SL_C_TR = pair.first.toFloat()
                        dFilter.d30TRBean.maxBean.k_SL_C_TR = pair.second.toFloat()
                    }
                    36 -> {
                        dFilter.d36TRBean.minBean.k_SL_C_TR = pair.first.toFloat()
                        dFilter.d36TRBean.maxBean.k_SL_C_TR = pair.second.toFloat()
                    }
                }
            }
        }
        return dFilter

    }

    fun setFilterBeanType(
        rangeMin: Int,
        mDFilter: P50FilterBBKJRangeBean.DFilter,
        p50FilterBBKjRangeBean: P50FilterBBKJRangeBean
    ) {
        when (rangeMin) {
            -70 -> {
                p50FilterBBKjRangeBean.r_N70_N60 = GsonHelper.parse(
                    GsonHelper.toJson(mDFilter),
                    P50FilterBBKJRangeBean.DFilter::class.java
                )
                LogUtil.d("r_N70_N60-->${GsonHelper.toJson(mDFilter)}")
            }
            -50 -> {
                p50FilterBBKjRangeBean.r_N50_N40 = GsonHelper.parse(
                    GsonHelper.toJson(mDFilter),
                    P50FilterBBKJRangeBean.DFilter::class.java
                )
                LogUtil.d("r_N50_N40-->${GsonHelper.toJson(mDFilter)}")
            }
            -40 -> {
                p50FilterBBKjRangeBean.r_N40_N30 = GsonHelper.parse(
                    GsonHelper.toJson(mDFilter),
                    P50FilterBBKJRangeBean.DFilter::class.java
                )
                LogUtil.d("r_N40_N30-->${GsonHelper.toJson(mDFilter)}")
            }
            -30 -> {
                p50FilterBBKjRangeBean.r_N30_N20 = GsonHelper.parse(
                    GsonHelper.toJson(mDFilter),
                    P50FilterBBKJRangeBean.DFilter::class.java
                )
                LogUtil.d("r_N30_N20-->${GsonHelper.toJson(mDFilter)}")
            }
            -20 -> {
                p50FilterBBKjRangeBean.r_N20_N10 = GsonHelper.parse(
                    GsonHelper.toJson(mDFilter),
                    P50FilterBBKJRangeBean.DFilter::class.java
                )
                LogUtil.d("r_N20_N10-->${GsonHelper.toJson(mDFilter)}")
            }
            -10 -> {
                p50FilterBBKjRangeBean.r_N10_0 = GsonHelper.parse(
                    GsonHelper.toJson(mDFilter),
                    P50FilterBBKJRangeBean.DFilter::class.java
                )
                LogUtil.d("r_N10_0-->${GsonHelper.toJson(mDFilter)}")
            }
            0 -> {
                p50FilterBBKjRangeBean.r_0_10 = GsonHelper.parse(
                    GsonHelper.toJson(mDFilter),
                    P50FilterBBKJRangeBean.DFilter::class.java
                )
                LogUtil.d("r_0_10-->${GsonHelper.toJson(mDFilter)}")
            }
            10 -> {
                p50FilterBBKjRangeBean.r_10_20 = GsonHelper.parse(
                    GsonHelper.toJson(mDFilter),
                    P50FilterBBKJRangeBean.DFilter::class.java
                )
                LogUtil.d("r_10_20-->${GsonHelper.toJson(mDFilter)}")
            }

        }
    }

    fun setP50BeanInit(mDFilter: P50FilterBBKJRangeBean.DFilter) {
        mDFilter.d03Bean = P50FilterBBKJRangeBean.MaxMinBean()
        mDFilter.d03Bean.maxBean = BaseFilterKJBBRangeBean()
        mDFilter.d03Bean.minBean = BaseFilterKJBBRangeBean()
        mDFilter.d03TRBean = P50FilterBBKJRangeBean.MaxMinTRBean()
        mDFilter.d03TRBean.maxBean = BaseFilterTRBBRangeBean()
        mDFilter.d03TRBean.minBean = BaseFilterTRBBRangeBean()
        mDFilter.d05Bean = P50FilterBBKJRangeBean.MaxMinBean()
        mDFilter.d05Bean.maxBean = BaseFilterKJBBRangeBean()
        mDFilter.d05Bean.minBean = BaseFilterKJBBRangeBean()
        mDFilter.d05TRBean = P50FilterBBKJRangeBean.MaxMinTRBean()
        mDFilter.d05TRBean.maxBean = BaseFilterTRBBRangeBean()
        mDFilter.d05TRBean.minBean = BaseFilterTRBBRangeBean()
        mDFilter.d10Bean = P50FilterBBKJRangeBean.MaxMinBean()
        mDFilter.d10Bean.maxBean = BaseFilterKJBBRangeBean()
        mDFilter.d10Bean.minBean = BaseFilterKJBBRangeBean()
        mDFilter.d10TRBean = P50FilterBBKJRangeBean.MaxMinTRBean()
        mDFilter.d10TRBean.maxBean = BaseFilterTRBBRangeBean()
        mDFilter.d10TRBean.minBean = BaseFilterTRBBRangeBean()
        mDFilter.d15Bean = P50FilterBBKJRangeBean.MaxMinBean()
        mDFilter.d15Bean.maxBean = BaseFilterKJBBRangeBean()
        mDFilter.d15Bean.minBean = BaseFilterKJBBRangeBean()
        mDFilter.d15TRBean = P50FilterBBKJRangeBean.MaxMinTRBean()
        mDFilter.d15TRBean.maxBean = BaseFilterTRBBRangeBean()
        mDFilter.d15TRBean.minBean = BaseFilterTRBBRangeBean()
        mDFilter.d20Bean = P50FilterBBKJRangeBean.MaxMinBean()
        mDFilter.d20Bean.maxBean = BaseFilterKJBBRangeBean()
        mDFilter.d20Bean.minBean = BaseFilterKJBBRangeBean()
        mDFilter.d20TRBean = P50FilterBBKJRangeBean.MaxMinTRBean()
        mDFilter.d20TRBean.maxBean = BaseFilterTRBBRangeBean()
        mDFilter.d20TRBean.minBean = BaseFilterTRBBRangeBean()
        mDFilter.d25Bean = P50FilterBBKJRangeBean.MaxMinBean()
        mDFilter.d25Bean.maxBean = BaseFilterKJBBRangeBean()
        mDFilter.d25Bean.minBean = BaseFilterKJBBRangeBean()
        mDFilter.d25TRBean = P50FilterBBKJRangeBean.MaxMinTRBean()
        mDFilter.d25TRBean.maxBean = BaseFilterTRBBRangeBean()
        mDFilter.d25TRBean.minBean = BaseFilterTRBBRangeBean()
        mDFilter.d30Bean = P50FilterBBKJRangeBean.MaxMinBean()
        mDFilter.d30Bean.maxBean = BaseFilterKJBBRangeBean()
        mDFilter.d30Bean.minBean = BaseFilterKJBBRangeBean()
        mDFilter.d30TRBean = P50FilterBBKJRangeBean.MaxMinTRBean()
        mDFilter.d30TRBean.maxBean = BaseFilterTRBBRangeBean()
        mDFilter.d30TRBean.minBean = BaseFilterTRBBRangeBean()
        mDFilter.d36Bean = P50FilterBBKJRangeBean.MaxMinBean()
        mDFilter.d36Bean.maxBean = BaseFilterKJBBRangeBean()
        mDFilter.d36Bean.minBean = BaseFilterKJBBRangeBean()
        mDFilter.d36TRBean = P50FilterBBKJRangeBean.MaxMinTRBean()
        mDFilter.d36TRBean.maxBean = BaseFilterTRBBRangeBean()
        mDFilter.d36TRBean.minBean = BaseFilterTRBBRangeBean()
    }

    fun filterP50Result(
        fitlerType: Int,
        originOM_M: Float,
        mDFilter: P50FilterBBKJRangeBean.DFilter,
        p50FilterBBKJRangeBean: P50FilterBBKJRangeBean,
        x: Int,
        targetBeanList: ArrayList<CodeHDDBean>,
        oldBeanList: ArrayList<CodeHDDBean>
    ): Boolean {
        oldBeanList.sortAscByDate()
        targetBeanList.sortAscByDate()
        val mCodeList = ArrayList<CodeHDDBean>()
        mCodeList.addAll(targetBeanList)
        mCodeList.addAll(oldBeanList)

//        LogUtil.d("------oldBeanList----------")
//        for (i in 0..oldBeanList.size-1) {
//            LogUtil.d("${oldBeanList[i].date}---cp:${oldBeanList[i].cp}---op:${oldBeanList[i].op}")
//        }

        val OC = oldBeanList[0].p_MA_J.aacp
        val OO = oldBeanList[oldBeanList.size - 1].p_MA_J.aacp


//        LogUtil.d("------targetBeanList----------")
//        targetBeanList.forEach {
//            LogUtil.d("${it.date}---cp:${it.cp}---op:${it.op}")
//        }

        val C = targetBeanList[0].p_MA_J.aacp
        val O = targetBeanList[targetBeanList.size - 1].p_MA_J.aaop

        val OM = oldBeanList.getRevBeansOM()
        val OL = oldBeanList.getRevBeansOL()
        val M = targetBeanList.getRevBeansOM()
        val L = targetBeanList.getRevBeansOL()
//        LogUtil.d("M:$M,L:$L,C:$C,O:$O")
        var needContinue = true

        when (x) {
            35 -> {
                if ((originOM_M >= -70 && originOM_M <= -60) || (originOM_M >= -50 && originOM_M <= -40) || (originOM_M >= -40 && originOM_M <= -30)
                    || (originOM_M >= -30 && originOM_M <= -20) || (originOM_M >= -20 && originOM_M <= -10) || (originOM_M >= -10 && originOM_M <= 0)
                    || (originOM_M >= 0 && originOM_M <= 10) || (originOM_M >= 10 && originOM_M <= 20)
                ) {
                    mDFilter.d36Bean = P50FilterBBKJRangeBean.MaxMinBean()
                    needContinue = judeParameterAndGetResult(
                        mCodeList,
                        x,
                        fitlerType,
                        p50FilterBBKJRangeBean,
                        OM,
                        M,
                        C,
                        O,
                        L,
                        OL,
                        OC,
                        OO,
                        mDFilter.d36Bean,
                        needContinue
                    )
                } else {
                    needContinue = false
                }

            }
            29 -> {
                mDFilter.d30Bean = P50FilterBBKJRangeBean.MaxMinBean()
                needContinue = judeParameterAndGetResult(
                    mCodeList,
                    x,
                    fitlerType,
                    p50FilterBBKJRangeBean,
                    OM,
                    M,
                    C,
                    O,
                    L,
                    OL,
                    OC,
                    OO,
                    mDFilter.d30Bean,
                    needContinue
                )

            }
            24 -> {
                mDFilter.d25Bean = P50FilterBBKJRangeBean.MaxMinBean()
                needContinue = judeParameterAndGetResult(
                    mCodeList,
                    x,
                    fitlerType,
                    p50FilterBBKJRangeBean,
                    OM,
                    M,
                    C,
                    O,
                    L,
                    OL,
                    OC,
                    OO,
                    mDFilter.d25Bean,
                    needContinue
                )

            }
            19 -> {
                mDFilter.d20Bean = P50FilterBBKJRangeBean.MaxMinBean()
                needContinue = judeParameterAndGetResult(
                    mCodeList,
                    x,
                    fitlerType,
                    p50FilterBBKJRangeBean,
                    OM,
                    M,
                    C,
                    O,
                    L,
                    OL,
                    OC,
                    OO,
                    mDFilter.d20Bean,
                    needContinue
                )

            }
            14 -> {
                mDFilter.d15Bean = P50FilterBBKJRangeBean.MaxMinBean()
                needContinue = judeParameterAndGetResult(
                    mCodeList,
                    x,
                    fitlerType,
                    p50FilterBBKJRangeBean,
                    OM,
                    M,
                    C,
                    O,
                    L,
                    OL,
                    OC,
                    OO,
                    mDFilter.d15Bean,
                    needContinue
                )

            }
            9 -> {
                mDFilter.d10Bean = P50FilterBBKJRangeBean.MaxMinBean()
                needContinue = judeParameterAndGetResult(
                    mCodeList,
                    x,
                    fitlerType,
                    p50FilterBBKJRangeBean,
                    OM,
                    M,
                    C,
                    O,
                    L,
                    OL,
                    OC,
                    OO,
                    mDFilter.d10Bean,
                    needContinue
                )

            }
            4 -> {
                mDFilter.d05Bean = P50FilterBBKJRangeBean.MaxMinBean()
                needContinue = judeParameterAndGetResult(
                    mCodeList,
                    x,
                    fitlerType,
                    p50FilterBBKJRangeBean,
                    OM,
                    M,
                    C,
                    O,
                    L,
                    OL,
                    OC,
                    OO,
                    mDFilter.d05Bean,
                    needContinue
                )

            }
            2 -> {
                mDFilter.d03Bean = P50FilterBBKJRangeBean.MaxMinBean()
                needContinue = judeParameterAndGetResult(
                    mCodeList,
                    x,
                    fitlerType,
                    p50FilterBBKJRangeBean,
                    OM,
                    M,
                    C,
                    O,
                    L,
                    OL,
                    OC,
                    OO,
                    mDFilter.d03Bean,
                    needContinue
                )

            }
        }
        return needContinue

    }

    private fun judeParameterAndGetResult(
        mCodeList: ArrayList<CodeHDDBean>,
        day: Int,
        fitlerType: Int,
        p50FilterBBKJRangeBean: P50FilterBBKJRangeBean,
        OM: Float,
        M: Float,
        C: Float,
        O: Float,
        L: Float,
        OL: Float,
        OC: Float,
        OO: Float,
        mDMaxBean: P50FilterBBKJRangeBean.MaxMinBean,
        needContinue: Boolean
    ): Boolean {
        var needContinue1 = needContinue
        val bean = kotlin.run {
//            arrayOf(3,5,10,15,20,25,30,36)
            when (fitlerType) {
                -70 -> {
                    p50FilterBBKJRangeBean.r_N70_N60?.let {
                        when (day) {
                            35 -> it.d36Bean
                            29 -> it.d30Bean
                            24 -> it.d25Bean
                            19 -> it.d20Bean
                            14 -> it.d15Bean
                            9 -> it.d10Bean
                            4 -> it.d05Bean
                            2 -> it.d03Bean
                            else -> null
                        }
                    }
                }
                -50 -> {
                    p50FilterBBKJRangeBean.r_N50_N40?.let {
                        when (day) {
                            35 -> it.d36Bean
                            29 -> it.d30Bean
                            24 -> it.d25Bean
                            19 -> it.d20Bean
                            14 -> it.d15Bean
                            9 -> it.d10Bean
                            4 -> it.d05Bean
                            2 -> it.d03Bean
                            else -> null
                        }
                    }
                }
                -40 -> {
                    p50FilterBBKJRangeBean.r_N40_N30?.let {
                        when (day) {
                            35 -> it.d36Bean
                            29 -> it.d30Bean
                            24 -> it.d25Bean
                            19 -> it.d20Bean
                            14 -> it.d15Bean
                            9 -> it.d10Bean
                            4 -> it.d05Bean
                            2 -> it.d03Bean
                            else -> null
                        }
                    }
                }
                -30 -> {
                    p50FilterBBKJRangeBean.r_N30_N20?.let {
                        when (day) {
                            35 -> it.d36Bean
                            29 -> it.d30Bean
                            24 -> it.d25Bean
                            19 -> it.d20Bean
                            14 -> it.d15Bean
                            9 -> it.d10Bean
                            4 -> it.d05Bean
                            2 -> it.d03Bean
                            else -> null
                        }
                    }
                }
                -20 -> {
                    p50FilterBBKJRangeBean.r_N20_N10?.let {
                        when (day) {
                            35 -> it.d36Bean
                            29 -> it.d30Bean
                            24 -> it.d25Bean
                            19 -> it.d20Bean
                            14 -> it.d15Bean
                            9 -> it.d10Bean
                            4 -> it.d05Bean
                            2 -> it.d03Bean
                            else -> null
                        }
                    }
                }
                -10 -> {
                    p50FilterBBKJRangeBean.r_N10_0?.let {
                        when (day) {
                            35 -> it.d36Bean
                            29 -> it.d30Bean
                            24 -> it.d25Bean
                            19 -> it.d20Bean
                            14 -> it.d15Bean
                            9 -> it.d10Bean
                            4 -> it.d05Bean
                            2 -> it.d03Bean
                            else -> null
                        }
                    }
                }
                0 -> {
                    p50FilterBBKJRangeBean.r_0_10?.let {
                        when (day) {
                            35 -> it.d36Bean
                            29 -> it.d30Bean
                            24 -> it.d25Bean
                            19 -> it.d20Bean
                            14 -> it.d15Bean
                            9 -> it.d10Bean
                            4 -> it.d05Bean
                            2 -> it.d03Bean
                            else -> null
                        }
                    }
                }
                10 -> {
                    p50FilterBBKJRangeBean.r_10_20?.let {
                        when (day) {
                            35 -> it.d36Bean
                            29 -> it.d30Bean
                            24 -> it.d25Bean
                            19 -> it.d20Bean
                            14 -> it.d15Bean
                            9 -> it.d10Bean
                            4 -> it.d05Bean
                            2 -> it.d03Bean
                            else -> null
                        }
                    }
                }
                else -> {
                    null
                }
            }
        }
//        val trBean = kotlin.run {
//            when (fitlerType) {
//                -70->{
//                    p50FilterBBKJRangeBean.r_N70_N60?.let{
//                        when (day) {
//                            35->it.d36TRBean
//                            29->it.d30TRBean
//                            24->it.d25TRBean
//                            19->it.d20TRBean
//                            14->it.d15TRBean
//                            9->it.d10TRBean
//                            4->it.d05TRBean
//                            2->it.d03TRBean
//                            else->null
//                        }
//                    }
//                }
//                -50->{
//                    p50FilterBBKJRangeBean.r_N50_N40?.let{
//                        when (day) {
//                            35->it.d36TRBean
//                            29->it.d30TRBean
//                            24->it.d25TRBean
//                            19->it.d20TRBean
//                            14->it.d15TRBean
//                            9->it.d10TRBean
//                            4->it.d05TRBean
//                            2->it.d03TRBean
//                            else->null
//                        }
//                    }
//                }
//                -40->{
//                    p50FilterBBKJRangeBean.r_N40_N30?.let{
//                        when (day) {
//                            35->it.d36TRBean
//                            29->it.d30TRBean
//                            24->it.d25TRBean
//                            19->it.d20TRBean
//                            14->it.d15TRBean
//                            9->it.d10TRBean
//                            4->it.d05TRBean
//                            2->it.d03TRBean
//                            else->null
//                        }
//                    }
//                }
//                -30->{
//                    p50FilterBBKJRangeBean.r_N30_N20?.let{
//                        when (day) {
//                            35->it.d36TRBean
//                            29->it.d30TRBean
//                            24->it.d25TRBean
//                            19->it.d20TRBean
//                            14->it.d15TRBean
//                            9->it.d10TRBean
//                            4->it.d05TRBean
//                            2->it.d03TRBean
//                            else->null
//                        }
//                    }
//                }
//                -20->{
//                    p50FilterBBKJRangeBean.r_N20_N10?.let{
//                        when (day) {
//                            35->it.d36TRBean
//                            29->it.d30TRBean
//                            24->it.d25TRBean
//                            19->it.d20TRBean
//                            14->it.d15TRBean
//                            9->it.d10TRBean
//                            4->it.d05TRBean
//                            2->it.d03TRBean
//                            else->null
//                        }
//                    }
//                }
//                -10->{
//                    p50FilterBBKJRangeBean.r_N10_0?.let{
//                        when (day) {
//                            35->it.d36TRBean
//                            29->it.d30TRBean
//                            24->it.d25TRBean
//                            19->it.d20TRBean
//                            14->it.d15TRBean
//                            9->it.d10TRBean
//                            4->it.d05TRBean
//                            2->it.d03TRBean
//                            else->null
//                        }
//                    }
//                }
//                0->{
//                    p50FilterBBKJRangeBean.r_0_10?.let{
//                        when (day) {
//                            35->it.d36TRBean
//                            29->it.d30TRBean
//                            24->it.d25TRBean
//                            19->it.d20TRBean
//                            14->it.d15TRBean
//                            9->it.d10TRBean
//                            4->it.d05TRBean
//                            2->it.d03TRBean
//                            else->null
//                        }
//                    }
//                }
//                10->{
//                    p50FilterBBKJRangeBean.r_10_20?.let{
//                        when (day) {
//                            35->it.d36TRBean
//                            29->it.d30TRBean
//                            24->it.d25TRBean
//                            19->it.d20TRBean
//                            14->it.d15TRBean
//                            9->it.d10TRBean
//                            4->it.d05TRBean
//                            2->it.d03TRBean
//                            else->null
//                        }
//                    }
//                }
//                else->{
//                    null
//                }
//            }
//        }
//        val mTrBean = TR_K_SLL_Bean()
//        setTRDatas(mTrBean,0,mCodeList.size-1,mCodeList)

        if (null == bean) {
            return false
        }
        val maxBean = bean.maxBean
        val minBean = bean.minBean
        val OM_M = ((OM - M) / OM * 100).toKeep2()
        val OM_C = ((OM - C) / OM * 100).toKeep2()
        val OM_P = ((OM - O) / OM * 100).toKeep2()
        val OM_L = ((OM - L) / OM * 100).toKeep2()

        val OL_M = ((OL - M) / OL * 100).toKeep2()
        val OL_C = ((OL - C) / OL * 100).toKeep2()
        val OL_P = ((OL - O) / OL * 100).toKeep2()
        val OL_L = ((OL - L) / OL * 100).toKeep2()

        val OC_M = ((OC - M) / OC * 100).toKeep2()
        val OC_C = ((OC - C) / OC * 100).toKeep2()
        val OC_P = ((OC - O) / OC * 100).toKeep2()
        val OC_L = ((OC - L) / OC * 100).toKeep2()

        val OP_M = ((OO - M) / OO * 100).toKeep2()
        val OP_C = ((OO - C) / OO * 100).toKeep2()
        val OP_P = ((OO - O) / OO * 100).toKeep2()
        val OP_L = ((OO - L) / OO * 100).toKeep2()

        val OM_OC = ((OM - OC) / OM * 100).toKeep2()
        val OM_OP = ((OM - OO) / OM * 100).toKeep2()
        val OM_OL = ((OM - OL) / OM * 100).toKeep2()
        val OC_OP = ((OC - OO) / OC * 100).toKeep2()
        val OC_OL = ((OC - OL) / OC * 100).toKeep2()
        val OP_OL = ((OO - OL) / OO * 100).toKeep2()

        val M_C = ((M - C) / M * 100).toKeep2()
        val M_P = ((M - O) / M * 100).toKeep2()
        val M_L = ((M - L) / M * 100).toKeep2()
        val C_P = ((C - O) / C * 100).toKeep2()
        val C_L = ((C - L) / C * 100).toKeep2()
        val P_L = ((O - L) / O * 100).toKeep2()


        val mMaxBean = BaseFilterKJBBRangeBean()
        if (judeFilterParameter(
                mCodeList[0],
                fitlerType,
                day,
                OM_M,
                minBean,
                maxBean,
                OM_C,
                OM_P,
                OM_L,
                OL_M,
                OL_C,
                OL_P,
                OL_L,
                OC_M,
                OC_C,
                OC_P,
                OC_L,
                OP_M,
                OP_C,
                OP_P,
                OP_L,
                OM_OC,
                OM_OP,
                OM_OL,
                OC_OP,
                OC_OL,
                OP_OL,
                M_C,
                M_P,
                M_L,
                C_P,
                C_L,
                P_L
            )
//            &&judeTRParameter(mTrBean,trBean)
        ) {
            setMaxDatas(mMaxBean, OM, M, C, L, OC, OL, OO, O)
            mDMaxBean.maxBean = mMaxBean
        } else {
            needContinue1 = false
        }
        return needContinue1
    }

    private fun judeTRParameter(
        mTrBean: TR_K_SLL_Bean,
        trBean: P50FilterBBKJRangeBean.MaxMinTRBean
    ): Boolean {
        val trMaxBean = trBean.maxBean
        val trMinBean = trBean.minBean
        val mBoolean =
            mTrBean.k_A_TR >= trMinBean.k_A_TR && mTrBean.k_A_TR <= trMaxBean.k_A_TR && mTrBean.k_R_TR >= trMinBean.k_R_TR && mTrBean.k_R_TR <= trMaxBean.k_R_TR && mTrBean.k_B_TR >= trMinBean.k_B_TR && mTrBean.k_B_TR <= trMaxBean.k_B_TR &&
                    mTrBean.s_A_TR >= trMinBean.s_A_TR && mTrBean.s_A_TR <= trMaxBean.s_A_TR && mTrBean.s_R_TR >= trMinBean.s_R_TR && mTrBean.s_R_TR <= trMaxBean.s_R_TR && mTrBean.s_B_TR >= trMinBean.s_B_TR && mTrBean.s_B_TR <= trMaxBean.s_B_TR &&
                    mTrBean.k_SL_A_TR >= trMinBean.k_SL_A_TR && mTrBean.k_SL_A_TR <= trMaxBean.k_SL_A_TR && mTrBean.k_SL_R_TR >= trMinBean.k_SL_R_TR && mTrBean.k_SL_R_TR <= trMaxBean.k_SL_R_TR && mTrBean.k_SL_B_TR >= trMinBean.k_SL_B_TR && mTrBean.k_SL_B_TR <= trMaxBean.k_SL_B_TR
        LogUtil.d("$mBoolean")
        return mBoolean
    }

    private fun judeFilterParameter(
        codeHDDBean: CodeHDDBean,
        fitlerType: Int,
        day: Int,
        OM_M: Float,
        minBean: BaseFilterKJBBRangeBean,
        maxBean: BaseFilterKJBBRangeBean,
        OM_C: Float,
        OM_P: Float,
        OM_L: Float,
        OL_M: Float,
        OL_C: Float,
        OL_P: Float,
        OL_L: Float,
        OC_M: Float,
        OC_C: Float,
        OC_P: Float,
        OC_L: Float,
        OP_M: Float,
        OP_C: Float,
        OP_P: Float,
        OP_L: Float,
        OM_OC: Float,
        OM_OP: Float,
        OM_OL: Float,
        OC_OP: Float,
        OC_OL: Float,
        OP_OL: Float,
        M_C: Float,
        M_P: Float,
        M_L: Float,
        C_P: Float,
        C_L: Float,
        P_L: Float
    ): Boolean {
        LogUtil.d("judeFilterParameter")
//        LogUtil.d("OM_M >= minBean.oM_M && OM_M <= maxBean.oM_M):${OM_M >= minBean.oM_M && OM_M <= maxBean.oM_M}")
//        LogUtil.d("(OM_C >= minBean.oM_C && OM_C <= maxBean.oM_C):${(OM_C >= minBean.oM_C && OM_C <= maxBean.oM_C)}")
//        LogUtil.d("(OM_P >= minBean.oM_P && OM_P <= maxBean.oM_P):${OM_P >= minBean.oM_P && OM_P <= maxBean.oM_P}")
//        LogUtil.d("(OM_L >= minBean.oM_L && OM_L <= maxBean.oM_L):${OM_L >= minBean.oM_L && OM_L <= maxBean.oM_L}")
//        LogUtil.d("(OL_M >= minBean.oL_M && OL_M <= maxBean.oL_M):${OL_M >= minBean.oL_M && OL_M <= maxBean.oL_M}")
//        LogUtil.d("(OL_C >= minBean.oL_C && OL_C <= maxBean.oL_C):${OL_C >= minBean.oL_C && OL_C <= maxBean.oL_C}")
//        LogUtil.d("(OL_P:${OL_P} >= minBean.oL_P:${minBean.oL_P} && OL_P <= maxBean.oL_P:${maxBean.oL_P}):${OL_P >= minBean.oL_P} && ${OL_P <= maxBean.oL_P}")
//        LogUtil.d("(OL_L >= minBean.oL_L && OL_L <= maxBean.oL_L):${OL_L >= minBean.oL_L && OL_L <= maxBean.oL_L}")
//        LogUtil.d("(OC_M >= minBean.oC_M && OC_M <= maxBean.oC_M):${OC_M >= minBean.oC_M && OC_M <= maxBean.oC_M}")
//        LogUtil.d("(OC_C >= minBean.oC_C && OC_C <= maxBean.oC_C):${OC_C >= minBean.oC_C && OC_C <= maxBean.oC_C}")
//        LogUtil.d("(OC_P >= minBean.oC_P && OC_P <= maxBean.oC_P):${OC_P >= minBean.oC_P && OC_P <= maxBean.oC_P}")
//        LogUtil.d("(OC_L >= minBean.oC_L && OC_L <= maxBean.oC_L):${OC_L >= minBean.oC_L && OC_L <= maxBean.oC_L}")
//        LogUtil.d("(OP_M >= minBean.oO_M && OP_M <= maxBean.oO_M):${OP_M >= minBean.oO_M && OP_M <= maxBean.oO_M}")
//        LogUtil.d("(OP_C >= minBean.oO_C && OP_C <= maxBean.oO_C):${OP_C >= minBean.oO_C && OP_C <= maxBean.oO_C}")
//        LogUtil.d("(OP_P >= minBean.oO_P && OP_P <= maxBean.oO_P):${OP_P >= minBean.oO_P && OP_P <= maxBean.oO_P}")
//        LogUtil.d("(OP_L >= minBean.oO_L && OP_L <= maxBean.oO_L):${OP_L >= minBean.oO_L && OP_L <= maxBean.oO_L}")
        var beginBoolean =
            (OM_M >= minBean.oM_M && OM_M <= maxBean.oM_M) && (OM_C >= minBean.oM_C && OM_C <= maxBean.oM_C) && (OM_P >= minBean.oM_P && OM_P <= maxBean.oM_P) && (OM_L >= minBean.oM_L && OM_L <= maxBean.oM_L) &&
                    (OL_M >= minBean.oL_M && OL_M <= maxBean.oL_M) && (OL_C >= minBean.oL_C && OL_C <= maxBean.oL_C) && (OL_P >= minBean.oL_P && OL_P <= maxBean.oL_P) && (OL_L >= minBean.oL_L && OL_L <= maxBean.oL_L) &&
                    (OC_M >= minBean.oC_M && OC_M <= maxBean.oC_M) && (OC_C >= minBean.oC_C && OC_C <= maxBean.oC_C) && (OC_P >= minBean.oC_P && OC_P <= maxBean.oC_P) && (OC_L >= minBean.oC_L && OC_L <= maxBean.oC_L) &&
                    (OP_M >= minBean.oO_M && OP_M <= maxBean.oO_M) && (OP_C >= minBean.oO_C && OP_C <= maxBean.oO_C) && (OP_P >= minBean.oO_P && OP_P <= maxBean.oO_P) && (OP_L >= minBean.oO_L && OP_L <= maxBean.oO_L)
                    &&
                    (OM_OC >= minBean.oM_OC && OM_OC <= maxBean.oM_OC) && (OM_OP >= minBean.oM_OP && OM_OP <= maxBean.oM_OP) && (OM_OL >= minBean.oM_OL && OM_OL <= maxBean.oM_OL) && (OC_OP >= minBean.oC_OP && OC_OP <= maxBean.oC_OP) && (OC_OL >= minBean.oC_OL && OC_OL <= maxBean.oC_OL) && (OP_OL >= minBean.oP_OL && OP_OL <= maxBean.oP_OL) &&
                    (M_C >= minBean.m_C && M_C <= maxBean.m_C) && (M_P >= minBean.m_P && M_P <= maxBean.m_P) && (M_L >= minBean.m_L && M_L <= maxBean.m_L) && (C_P >= minBean.c_P && C_P <= maxBean.c_P) && (C_L >= minBean.c_L && C_L <= maxBean.c_L) && (P_L >= minBean.p_L && P_L <= maxBean.p_L)
        mActivity.setReasoningProgress("${codeHDDBean.name}-->${codeHDDBean.date}-->beginBoolean:${beginBoolean}")
        if (!beginBoolean) {
            LogUtil.d("${codeHDDBean.name}-->fitlerType:$fitlerType,day:$day,beginBoolean:$beginBoolean")
            return beginBoolean
        } else {
            when (fitlerType) {
                -30 -> {
                    when (day) {
                        4 -> {
                            beginBoolean = OM_P < 6 && OL_C > -20 && OM_OC < 6
                        }
                        9 -> {
                            LogUtil.d("9---> $OC_M>-23,$OC_C>-16,$OP_M>-25,$OP_C>-28,$OP_P>-10,$OP_L>-5,$OP_M>-32,$OL_C>-25,$OL_C>-25,$OL_P>-16,$OL_L>-8,$C_P<15")
                            beginBoolean =
                                OC_M > -23 && OC_C > -16 && OP_M > -25 && OP_C > -28 && OP_P > -10 && OP_L > -5 && OP_M > -32 && OL_C > -25 && OL_C > -25 && OL_P > -16 && OL_L > -8 && C_P < 15
                        }
                        14 -> {
                            beginBoolean =
                                OM_M > -17 && OM_C > -15 && OC_M > -30 && OC_C > -20 && OP_M > -28 && OP_C > -20 && OL_M > -35 && OL_C > -30 && M_P < 23 && M_L < 26 && C_P < 18 && C_L < 20
                        }
                        19 -> {
                            beginBoolean =
                                OM_M > -17 && OC_M > -30 && OC_C > -20 && OL_M > -42 && OL_C > -35 && M_P < 22 && M_L < 25 && C_P < 17 && C_L < 20
                        }
                        24 -> {
                            beginBoolean =
                                OC_M > -30 && OC_C > -25 && OP_M > -40 && OP_C > -30 && OL_M > -48 && M_L < 28 && C_P < 20 && C_L < 20
                        }
                        29 -> {
                            beginBoolean = OP_M > -44 && OP_C > -33 && M_L < 28 && C_L < 22
                        }
                        35 -> {
//                            LogUtil.d("35--->$OC_M>-40,$OP_M>-50,$OP_C>-40,$OL_M>-51,$OL_C>-50,$M_L<30")
                            beginBoolean =
                                OC_M > -40 && OP_M > -50 && OP_C > -40 && OL_M > -51 && OL_C > -50 && M_L < 30
                        }
                    }
                }

                else -> {
                    if (day < 35) {
                        val tbName =
                            "${Datas.BB_FIL_COPY_}A_RTB_50_${day + 1}_R_${Math.abs(fitlerType)}_${Math.abs(
                                fitlerType + 10
                            )}"
                        val tbDerbyName =
                            "${Datas.BB_FIL_COPY_}Derby_A_RTB_50_${day + 1}_R_${Math.abs(fitlerType)}_${Math.abs(
                                fitlerType + 10
                            )}"

                        beginBoolean = stepByStepJudge(
                            tbName,
                            OM_M,
                            OM_C,
                            OM_P,
                            OM_L,
                            OL_M,
                            OL_C,
                            OL_P,
                            OL_L,
                            OC_M,
                            OC_C,
                            OC_P,
                            OC_L,
                            OP_M,
                            OP_C,
                            OP_P,
                            OP_L,
                            tbDerbyName,
                            OM_OC,
                            OM_OP,
                            OM_OL,
                            OC_OP,
                            OC_OL,
                            OP_OL,
                            M_C,
                            M_P,
                            M_L,
                            C_P,
                            C_L,
                            P_L
                        )
                        LogUtil.d("${codeHDDBean.name}-->stepByStepJudge-->$beginBoolean")

                    }
                }
            }

        }
        mActivity.setReasoningProgress("${codeHDDBean.name}-->${codeHDDBean.date}-->day:$day,stepByStepJudge:${beginBoolean}")
        LogUtil.d("${codeHDDBean.name}-->fitlerType:$fitlerType,day:$day,beginBoolean:$beginBoolean")
        return beginBoolean
    }

    private fun stepByStepJudge(
        tbName: String,
        OM_M: Float,
        OM_C: Float,
        OM_P: Float,
        OM_L: Float,
        OL_M: Float,
        OL_C: Float,
        OL_P: Float,
        OL_L: Float,
        OC_M: Float,
        OC_C: Float,
        OC_P: Float,
        OC_L: Float,
        OP_M: Float,
        OP_C: Float,
        OP_P: Float,
        OP_L: Float,
        tbDerbyName: String,
        OM_OC: Float,
        OM_OP: Float,
        OM_OL: Float,
        OC_OP: Float,
        OC_OL: Float,
        OP_OL: Float,
        M_C: Float,
        M_P: Float,
        M_L: Float,
        C_P: Float,
        C_L: Float,
        P_L: Float
    ): Boolean {
        var beginBoolean1 = false
        val (rangeMax, rangeMin) = getRangeMaxMin(tbName, Datas.REV_FILTERDB + "2020")
        var firstStepBoolean = false
        for (i in rangeMin..rangeMax step Datas.FILTER_PROGRESS) {
            if (OM_M <= (i + Datas.FILTER_PROGRESS) && OM_M >= i) {
//                LogUtil.d("OM_M------>begin")
                val list = DBUtils.getAAFilterAllByTbName(
                    "SELECT * FROM $tbName WHERE OM_M >=? AND OM_M<?",
                    arrayOf(
                        i.toString(),
                        (i + Datas.FILTER_PROGRESS).toString()
                    )
                )
                var maxOM_M = -10086.toFloat()
                var maxOM_C = -10086.toFloat()
                var maxOM_P = -10086.toFloat()
                var maxOM_L = -10086.toFloat()
                var maxOC_M = -10086.toFloat()
                var maxOC_C = -10086.toFloat()
                var maxOC_P = -10086.toFloat()
                var maxOC_L = -10086.toFloat()
                var maxOO_M = -10086.toFloat()
                var maxOO_C = -10086.toFloat()
                var maxOO_P = -10086.toFloat()
                var maxOO_L = -10086.toFloat()
                var maxOL_M = -10086.toFloat()
                var maxOL_C = -10086.toFloat()
                var maxOL_P = -10086.toFloat()
                var maxOL_L = -10086.toFloat()
                var minOM_M = 10086.toFloat()
                var minOM_C = 10086.toFloat()
                var minOM_P = 10086.toFloat()
                var minOM_L = 10086.toFloat()
                var minOC_M = 10086.toFloat()
                var minOC_C = 10086.toFloat()
                var minOC_P = 10086.toFloat()
                var minOC_L = 10086.toFloat()
                var minOO_M = 10086.toFloat()
                var minOO_C = 10086.toFloat()
                var minOO_P = 10086.toFloat()
                var minOO_L = 10086.toFloat()
                var minOL_M = 10086.toFloat()
                var minOL_C = 10086.toFloat()
                var minOL_P = 10086.toFloat()
                var minOL_L = 10086.toFloat()
                list?.forEach {
                    if (it is ReverseKJsonBean) {
                        if (maxOM_M < it.oM_M) {
                            maxOM_M = it.oM_M
                        }
                        if (minOM_M > it.oM_M) {
                            minOM_M = it.oM_M
                        }
                        if (maxOM_C < it.oM_C) {
                            maxOM_C = it.oM_C
                        }
                        if (minOM_C > it.oM_C) {
                            minOM_C = it.oM_C
                        }
                        if (maxOM_P < it.oM_P) {
                            maxOM_P = it.oM_P
                        }
                        if (minOM_P > it.oM_P) {
                            minOM_P = it.oM_P
                        }
                        if (maxOM_L < it.oM_L) {
                            maxOM_L = it.oM_L
                        }
                        if (minOM_L > it.oM_L) {
                            minOM_L = it.oM_L
                        }
                        if (maxOC_M < it.oC_M) {
                            maxOC_M = it.oC_M
                        }
                        if (minOC_M > it.oC_M) {
                            minOC_M = it.oC_M
                        }
                        if (maxOC_C < it.oC_C) {
                            maxOC_C = it.oC_C
                        }
                        if (minOC_C > it.oC_C) {
                            minOC_C = it.oC_C
                        }
                        if (maxOC_P < it.oC_P) {
                            maxOC_P = it.oC_P
                        }
                        if (minOC_P > it.oC_P) {
                            minOC_P = it.oC_P
                        }
                        if (maxOC_L < it.oC_L) {
                            maxOC_L = it.oC_L
                        }
                        if (minOC_L > it.oC_L) {
                            minOC_L = it.oC_L
                        }
                        if (maxOO_M < it.oO_M) {
                            maxOO_M = it.oO_M
                        }
                        if (minOO_M > it.oO_M) {
                            minOO_M = it.oO_M
                        }
                        if (maxOO_C < it.oO_C) {
                            maxOO_C = it.oO_C
                        }
                        if (minOO_C > it.oO_C) {
                            minOO_C = it.oO_C
                        }
                        if (maxOO_P < it.oO_P) {
                            maxOO_P = it.oO_P
                        }
                        if (minOO_P > it.oO_P) {
                            minOO_P = it.oO_P
                        }
                        if (maxOO_L < it.oO_L) {
                            maxOO_L = it.oO_L
                        }
                        if (minOO_L > it.oO_L) {
                            minOO_L = it.oO_L
                        }
                        if (maxOL_M < it.oL_M) {
                            maxOL_M = it.oL_M
                        }
                        if (minOL_M > it.oL_M) {
                            minOL_M = it.oL_M
                        }
                        if (maxOL_C < it.oL_C) {
                            maxOL_C = it.oL_C
                        }
                        if (minOL_C > it.oL_C) {
                            minOL_C = it.oL_C
                        }
                        if (maxOL_P < it.oL_P) {
                            maxOL_P = it.oL_P
                        }
                        if (minOL_P > it.oL_P) {
                            minOL_P = it.oL_P
                        }
                        if (maxOL_L < it.oL_L) {
                            maxOL_L = it.oL_L
                        }
                        if (minOL_L > it.oL_L) {
                            minOL_L = it.oL_L
                        }
                    }
                }
//                LogUtil.d("($OM_M >= $minOM_M && $OM_M <= $maxOM_M) && ($OM_C >= $minOM_C && $OM_C <= $maxOM_C) && ($OM_P >= $minOM_P && $OM_P <= $maxOM_P) && ($OM_L >= $minOM_L && $OM_L <= $maxOM_L)")
                firstStepBoolean =
                    (OM_M >= minOM_M && OM_M <= maxOM_M) && (OM_C >= minOM_C && OM_C <= maxOM_C) && (OM_P >= minOM_P && OM_P <= maxOM_P) && (OM_L >= minOM_L && OM_L <= maxOM_L) &&
                            (OL_M >= minOL_M && OL_M <= maxOL_M) && (OL_C >= minOL_C && OL_C <= maxOL_C) && (OL_P >= minOL_P && OL_P <= maxOL_P) && (OL_L >= minOL_L && OL_L <= maxOL_L) &&
                            (OC_M >= minOC_M && OC_M <= maxOC_M) && (OC_C >= minOC_C && OC_C <= maxOC_C) && (OC_P >= minOC_P && OC_P <= maxOC_P) && (OC_L >= minOC_L && OC_L <= maxOC_L) &&
                            (OP_M >= minOO_M && OP_M <= maxOO_M) && (OP_C >= minOO_C && OP_C <= maxOO_C) && (OP_P >= minOO_P && OP_P <= maxOO_P) && (OP_L >= minOO_L && OP_L <= maxOO_L)
                break
            }
        }
//        if (firstStepBoolean) {
//            val pairDerby = DBUtils.selectMaxMinValueByTbAndColumn(
//                tbDerbyName,
//                "OM_OC",
//                Datas.REV_FILTERDB + "2020"
//            )
//            val rangeDerbyMax =
//                (pairDerby.second.toFloat() / 10).toInt() * 10 + 10 - Datas.FILTER_DERBY_PROGRESS
//            val rangeDerbyMin = (pairDerby.first.toFloat() / 10).toInt() * 10 - 10
//            for (y in rangeDerbyMin..rangeDerbyMax step Datas.FILTER_DERBY_PROGRESS) {
//                if (OM_OC <= (y + Datas.FILTER_DERBY_PROGRESS) && OM_OC >= y) {
//                    LogUtil.d("OM_OC------>begin")
//                    val derbylist = DBUtils.getDerbyAAFilterAllByTbName(
//                        "SELECT * FROM $tbDerbyName WHERE OM_OC >=? AND OM_OC<?",
//                        arrayOf(
//                            y.toString(),
//                            (y + Datas.FILTER_DERBY_PROGRESS).toString()
//                        )
//                    )
//                    var maxOM_OC = -10086.toFloat()
//                    var maxOM_OP = -10086.toFloat()
//                    var maxOM_OL = -10086.toFloat()
//                    var maxOC_OP = -10086.toFloat()
//                    var maxOC_OL = -10086.toFloat()
//                    var maxOP_OL = -10086.toFloat()
//                    var maxM_C = -10086.toFloat()
//                    var maxM_P = -10086.toFloat()
//                    var maxM_L = -10086.toFloat()
//                    var maxC_P = -10086.toFloat()
//                    var maxC_L = -10086.toFloat()
//                    var maxP_L = -10086.toFloat()
//                    var minOM_OC = 10086.toFloat()
//                    var minOM_OP = 10086.toFloat()
//                    var minOM_OL = 10086.toFloat()
//                    var minOC_OP = 10086.toFloat()
//                    var minOC_OL = 10086.toFloat()
//                    var minOP_OL = 10086.toFloat()
//                    var minM_C = 10086.toFloat()
//                    var minM_P = 10086.toFloat()
//                    var minM_L = 10086.toFloat()
//                    var minC_P = 10086.toFloat()
//                    var minC_L = 10086.toFloat()
//                    var minP_L = 10086.toFloat()
//                    derbylist?.forEach {
//                        if (it is ReverseKJsonBean) {
//                            if (maxOM_OC < it.oM_OC) {
//                                maxOM_OC = it.oM_OC
//                            }
//                            if (minOM_OC > it.oM_OC) {
//                                minOM_OC = it.oM_OC
//                            }
//                            if (maxOM_OP < it.oM_OP) {
//                                maxOM_OP = it.oM_OP
//                            }
//                            if (minOM_OP > it.oM_OP) {
//                                minOM_OP = it.oM_OP
//                            }
//                            if (maxOM_OL < it.oM_OL) {
//                                maxOM_OL = it.oM_OL
//                            }
//                            if (minOM_OL > it.oM_OL) {
//                                minOM_OL = it.oM_OL
//                            }
//                            if (maxOC_OL < it.oC_OL) {
//                                maxOC_OL = it.oC_OL
//                            }
//                            if (minOC_OL > it.oC_OL) {
//                                minOC_OL = it.oC_OL
//                            }
//                            if (maxOC_OP < it.oC_OP) {
//                                maxOC_OP = it.oC_OP
//                            }
//                            if (minOC_OP > it.oC_OP) {
//                                minOC_OP = it.oC_OP
//                            }
//                            if (maxOP_OL < it.oP_OL) {
//                                maxOP_OL = it.oP_OL
//                            }
//                            if (minOP_OL > it.oP_OL) {
//                                minOP_OL = it.oP_OL
//                            }
//                            if (maxM_C < it.m_C) {
//                                maxM_C = it.m_C
//                            }
//                            if (minM_C > it.m_C) {
//                                minM_C = it.m_C
//                            }
//                            if (maxM_P < it.m_P) {
//                                maxM_P = it.m_P
//                            }
//                            if (minM_P > it.m_P) {
//                                minM_P = it.m_P
//                            }
//                            if (maxM_L < it.m_L) {
//                                maxM_L = it.m_L
//                            }
//                            if (minM_L > it.m_L) {
//                                minM_L = it.m_L
//                            }
//                            if (maxC_P < it.c_P) {
//                                maxC_P = it.c_P
//                            }
//                            if (minC_P > it.c_P) {
//                                minC_P = it.c_P
//                            }
//                            if (maxC_L < it.c_L) {
//                                maxC_L = it.c_L
//                            }
//                            if (minC_L > it.c_L) {
//                                minC_L = it.c_L
//                            }
//                            if (maxP_L < it.p_L) {
//                                maxP_L = it.p_L
//                            }
//                            if (minP_L > it.p_L) {
//                                minP_L = it.p_L
//                            }
//                        }
//                    }
//
//                    if ((OM_OC >= minOM_OC && OM_OC <= maxOM_OC) && (OM_OP >= minOM_OP && OM_OP <= maxOM_OP) && (OM_OL >= minOM_OL && OM_OL <= maxOM_OL) && (OC_OP >= minOC_OP && OC_OP <= maxOC_OP) && (OC_OL >= minOC_OL && OC_OL <= maxOC_OL) && (OP_OL >= minOP_OL && OP_OL <= maxOP_OL) &&
//                        (M_C >= minM_C && M_C <= maxM_C) && (M_P >= minM_P && M_P <= maxM_P) && (M_L >= minM_L && M_L <= maxM_L) && (C_P >= minC_P && C_P <= maxC_P) && (C_L >= minC_L && C_L <= maxC_L) && (P_L >= minP_L && P_L <= maxP_L)
//                    ) {
//                        beginBoolean1 = true
//                        break
//                    }
//                }
//            }
//        }
        beginBoolean1 = firstStepBoolean
        return beginBoolean1
    }

    fun getRangeMaxMinByColumm(tbName: String, dbName: String, columm: String,isOCOO: Boolean): Pair<Int, Int> {
        val pair =
            DBUtils.selectMaxMinValueByTbAndColumn(tbName, columm, dbName)
        val (rangeMax, rangeMin) = getMMByValue(pair,isOCOO)
        return Pair(rangeMax, rangeMin)
    }

    fun getRangeMaxMin(tbName: String, dbName: String): Pair<Int, Int> {
        val pair =
            DBUtils.selectMaxMinValueByTbAndColumn(tbName, "OM_M", dbName)
        val (rangeMax, rangeMin) = getMMByValue(pair)
        return Pair(rangeMax, rangeMin)
    }

    fun getRangeMaxMiByCodeList(
        isOCOO:Boolean,
        tbName: String,
        column: String,
        codeList: ArrayList<String>,
        dbName: String
    ): Pair<Int, Int> {
        val pair =
            DBUtils.selectMaxMinValueByTbAndColumnByCodeList(codeList, tbName, column, dbName)
        val (rangeMax, rangeMin) = getMMByValue(pair,isOCOO)
        return Pair(rangeMax, rangeMin)
    }

    fun getMMByValue(pair: Pair<String, String>,isOCOO:Boolean = false): Pair<Int, Int> {
        val progress = if (isOCOO) Datas.FILTER_OC_OO_PROGRESS else Datas.FILTER_PROGRESS
        val rangeMax = if (pair.second.toFloat() > 0) {
            (pair.second.toFloat() / progress).toInt() * progress + if ((pair.second.toFloat() % progress.toFloat()) == 0.toFloat()) 0  else progress
        } else {
            (pair.second.toFloat() / progress).toInt() * progress
        }
        val rangeMin =if (pair.first.toFloat() > 0) {
            (pair.first.toFloat() / progress).toInt() * progress
        } else {
            (pair.first.toFloat() / progress).toInt() * progress - if ((pair.first.toFloat() % progress.toFloat()) == 0.toFloat()) 0  else progress
        }
        return Pair(rangeMax, rangeMin)
    }

    private fun setMaxDatas(
        mMaxBean: BaseFilterKJBBRangeBean,
        OM: Float,
        M: Float,
        C: Float,
        L: Float,
        OC: Float,
        OL: Float,
        OO: Float,
        O: Float
    ) {
        mMaxBean.oM_M = ((OM - M) / OM * 100).toKeep2()
        mMaxBean.oM_C = ((OM - C) / OM * 100).toKeep2()
        mMaxBean.oM_L = ((OM - L) / OM * 100).toKeep2()
        mMaxBean.oM_P = ((OM - O) / OM * 100).toKeep2()
        mMaxBean.oM_OC = ((OM - OC) / OM * 100).toKeep2()
        mMaxBean.oM_OL = ((OM - OL) / OM * 100).toKeep2()
        mMaxBean.oM_OP = ((OM - OO) / OM * 100).toKeep2()

        mMaxBean.oL_L = ((OL - L) / OL * 100).toKeep2()
        mMaxBean.oL_M = ((OL - M) / OL * 100).toKeep2()
        mMaxBean.oL_C = ((OL - C) / OL * 100).toKeep2()
        mMaxBean.oL_P = ((OL - O) / OL * 100).toKeep2()

        mMaxBean.oC_L = ((OC - L) / OC * 100).toKeep2()
        mMaxBean.oC_M = ((OC - M) / OC * 100).toKeep2()
        mMaxBean.oC_C = ((OC - C) / OC * 100).toKeep2()
        mMaxBean.oC_P = ((OC - O) / OC * 100).toKeep2()
        mMaxBean.oC_OL = ((OC - OL) / OC * 100).toKeep2()
        mMaxBean.oC_OP = ((OC - OO) / OC * 100).toKeep2()

        mMaxBean.oO_L = ((OO - L) / OO * 100).toKeep2()
        mMaxBean.oO_M = ((OO - M) / OO * 100).toKeep2()
        mMaxBean.oO_C = ((OO - C) / OO * 100).toKeep2()
        mMaxBean.oO_P = ((OO - O) / OO * 100).toKeep2()
        mMaxBean.oP_OL = ((OO - OL) / OO * 100).toKeep2()

        mMaxBean.m_C = ((M - C) / M * 100).toKeep2()
        mMaxBean.m_P = ((M - O) / M * 100).toKeep2()
        mMaxBean.m_L = ((M - L) / M * 100).toKeep2()
        mMaxBean.c_P = ((C - O) / C * 100).toKeep2()
        mMaxBean.c_L = ((C - L) / C * 100).toKeep2()
        mMaxBean.p_L = ((O - L) / O * 100).toKeep2()
    }

    fun setFilterP50ResultType(
        originOM_M: Float,
        mDFilter: P50FilterBBKJRangeBean.DFilter,
        mP50Bean: P50FilterBBKJRangeBean
    ) {
        if (originOM_M >= -70 && originOM_M <= -60) {
            mP50Bean.r_N70_N60 = mDFilter
        } else if (originOM_M >= -50 && originOM_M <= -40) {
            mP50Bean.r_N50_N40 = mDFilter
        } else if (originOM_M >= -40 && originOM_M <= -30) {
            mP50Bean.r_N40_N30 = mDFilter
        } else if (originOM_M >= -30 && originOM_M <= -20) {
            mP50Bean.r_N30_N20 = mDFilter
        } else if (originOM_M >= -20 && originOM_M <= -10) {
            mP50Bean.r_N20_N10 = mDFilter
        } else if (originOM_M >= -10 && originOM_M <= 0) {
            mP50Bean.r_N10_0 = mDFilter
        } else if (originOM_M >= 0 && originOM_M <= 10) {
            mP50Bean.r_0_10 = mDFilter
        } else if (originOM_M >= 10 && originOM_M <= 20) {
            mP50Bean.r_10_20 = mDFilter
        }
    }

    fun getFilterP50ResultType(originOM_M: Float): Int {
        var type = -10086
        if (originOM_M >= -70 && originOM_M <= -60) {
            type = -70
        } else if (originOM_M >= -50 && originOM_M <= -40) {
            type = -50
        } else if (originOM_M >= -40 && originOM_M <= -30) {
            type = -40
        } else if (originOM_M >= -30 && originOM_M <= -20) {
            type = -30
        } else if (originOM_M >= -20 && originOM_M <= -10) {
            type = -20
        } else if (originOM_M >= -10 && originOM_M <= 0) {
            type = -10
        } else if (originOM_M >= 0 && originOM_M <= 10) {
            type = 0
        } else if (originOM_M >= 10 && originOM_M <= 20) {
            type = 10
        }
        return type
    }

    fun setTRDatas(
        trKSllBean: TR_K_SLL_Bean,
        targetBeanBegin: Int,
        oldBeanEnd: Int,
        mCHDDList: ArrayList<CodeHDDBean>
    ) {
        trKSllBean.s_A_TR = kotlin.run {
            var result = 0.toFloat()
            for (i in targetBeanBegin..oldBeanEnd) {
                val bean = mCHDDList[i]
                result = result + bean.tr.percent2Float()
            }
            result.toKeep2()
        }
        trKSllBean.s_R_TR = kotlin.run {
            var result = 0.toFloat()
            for (i in targetBeanBegin..oldBeanEnd) {
                if (mCHDDList[i].op < mCHDDList[i].cp) {
                    val bean = mCHDDList[i]
                    result = result + bean.tr.percent2Float()
                }
            }
            result.toKeep2()
        }
        trKSllBean.s_B_TR = kotlin.run {
            var result = 0.toFloat()
            for (i in targetBeanBegin..oldBeanEnd) {
                if (mCHDDList[i].op > mCHDDList[i].cp) {
                    val bean = mCHDDList[i]
                    result = result + bean.tr.percent2Float()
                }
            }
            result.toKeep2()
        }
        trKSllBean.s_C_TR = (trKSllBean.s_R_TR - trKSllBean.s_B_TR).toKeep2()

        //K_TR
        trKSllBean.k_A_TR = kotlin.run {
            var result = 0.toFloat()
            for (i in targetBeanBegin..oldBeanEnd) {
                val bean = mCHDDList[i]
                result = result + bean.tr.percent2Float() * bean.p
            }
            result.toKeep2()
        }
        trKSllBean.k_R_TR = kotlin.run {
            var result = 0.toFloat()
            for (i in targetBeanBegin..oldBeanEnd) {
                if (mCHDDList[i].op < mCHDDList[i].cp) {
                    val bean = mCHDDList[i]
                    result = result + bean.tr.percent2Float() * bean.p
                }
            }
            result.toKeep2()
        }
        trKSllBean.k_B_TR = kotlin.run {
            var result = 0.toFloat()
            for (i in targetBeanBegin..oldBeanEnd) {
                if (mCHDDList[i].op > mCHDDList[i].cp) {
                    val bean = mCHDDList[i]
                    result = result + bean.tr.percent2Float() * bean.p
                }
            }
            result.toKeep2()
        }
        trKSllBean.k_C_TR = (trKSllBean.k_R_TR + trKSllBean.k_B_TR).toKeep2()
        //K_TR_SLL
        trKSllBean.k_SL_A_TR = kotlin.run {
            var result = 0.toFloat()
            for (i in targetBeanBegin..oldBeanEnd) {
                val bean = mCHDDList[i]
                val mp = bean.p_MA_J.amp
                val lp = bean.p_MA_J.alp
                result = result + ((mp - lp) / lp) * 100 * bean.p
            }
            result.toKeep2()
        }
        trKSllBean.k_SL_R_TR = kotlin.run {
            var result = 0.toFloat()
            for (i in targetBeanBegin..oldBeanEnd) {
                if (mCHDDList[i].op < mCHDDList[i].cp) {
                    val bean = mCHDDList[i]
                    val mp = bean.p_MA_J.amp
                    val lp = bean.p_MA_J.alp
                    val op = bean.op
                    val cp = bean.cp
                    result =
                        result + (bean.tr.percent2Float() + ((cp - lp) / lp) * 100 - ((mp - cp) / cp) * 100) * bean.p
                }
            }
            result.toKeep2()
        }
        trKSllBean.k_SL_B_TR = kotlin.run {
            var result = 0.toFloat()
            for (i in targetBeanBegin..oldBeanEnd) {
                if (mCHDDList[i].op > mCHDDList[i].cp) {
                    val bean = mCHDDList[i]

                    val mp = bean.p_MA_J.amp
                    val lp = bean.p_MA_J.alp
                    val op = bean.op
                    val cp = bean.cp
                    result =
                        result + (bean.tr.percent2Float() + ((mp - cp) / cp) * 100 - ((cp - lp) / lp) * 100) * bean.p
                }
            }
            result.toKeep2()
        }
        trKSllBean.k_SL_C_TR = (trKSllBean.k_SL_R_TR + trKSllBean.k_SL_B_TR).toKeep2()
    }

    fun getReasoningAllJudgeBean(
        list: ArrayList<BaseReverseImp>,
        derbyList: ArrayList<BaseReverseImp>,
        date: Int
    ): ReasoningAllJudgeBean {
        val reasoningAllJudgeBean = ReasoningAllJudgeBean()

        var maxOM_M = -10086.toFloat()
        var maxOM_C = -10086.toFloat()
        var maxOM_P = -10086.toFloat()
        var maxOM_L = -10086.toFloat()
        var maxOC_M = -10086.toFloat()
        var maxOC_C = -10086.toFloat()
        var maxOC_P = -10086.toFloat()
        var maxOC_L = -10086.toFloat()
        var maxOO_M = -10086.toFloat()
        var maxOO_C = -10086.toFloat()
        var maxOO_P = -10086.toFloat()
        var maxOO_L = -10086.toFloat()
        var maxOL_M = -10086.toFloat()
        var maxOL_C = -10086.toFloat()
        var maxOL_P = -10086.toFloat()
        var maxOL_L = -10086.toFloat()
        var minOM_M = 10086.toFloat()
        var minOM_C = 10086.toFloat()
        var minOM_P = 10086.toFloat()
        var minOM_L = 10086.toFloat()
        var minOC_M = 10086.toFloat()
        var minOC_C = 10086.toFloat()
        var minOC_P = 10086.toFloat()
        var minOC_L = 10086.toFloat()
        var minOO_M = 10086.toFloat()
        var minOO_C = 10086.toFloat()
        var minOO_P = 10086.toFloat()
        var minOO_L = 10086.toFloat()
        var minOL_M = 10086.toFloat()
        var minOL_C = 10086.toFloat()
        var minOL_P = 10086.toFloat()
        var minOL_L = 10086.toFloat()


        var maxOM_OC = -10086.toFloat()
        var maxOM_OP = -10086.toFloat()
        var maxOM_OL = -10086.toFloat()
        var maxOC_OP = -10086.toFloat()
        var maxOC_OL = -10086.toFloat()
        var maxOP_OL = -10086.toFloat()
        var maxM_C = -10086.toFloat()
        var maxM_P = -10086.toFloat()
        var maxM_L = -10086.toFloat()
        var maxC_P = -10086.toFloat()
        var maxC_L = -10086.toFloat()
        var maxP_L = -10086.toFloat()
        var minOM_OC = 10086.toFloat()
        var minOM_OP = 10086.toFloat()
        var minOM_OL = 10086.toFloat()
        var minOC_OP = 10086.toFloat()
        var minOC_OL = 10086.toFloat()
        var minOP_OL = 10086.toFloat()
        var minM_C = 10086.toFloat()
        var minM_P = 10086.toFloat()
        var minM_L = 10086.toFloat()
        var minC_P = 10086.toFloat()
        var minC_L = 10086.toFloat()
        var minP_L = 10086.toFloat()
        derbyList.forEach {
            if (it is ReverseKJsonBean) {
                if (maxOM_OC < it.oM_OC) {
                    maxOM_OC = it.oM_OC
                }
                if (maxOM_OP < it.oM_OP) {
                    maxOM_OP = it.oM_OP
                }
                if (maxOM_OL < it.oM_OL) {
                    maxOM_OL = it.oM_OL
                }
                if (maxOC_OP < it.oC_OP) {
                    maxOC_OP = it.oC_OP
                }
                if (maxOC_OL < it.oC_OL) {
                    maxOC_OL = it.oC_OL
                }
                if (maxOP_OL< it.oP_OL) {
                    maxOP_OL = it.oP_OL
                }
                if (maxM_C < it.m_C) {
                    maxM_C = it.m_C
                }
                if (maxM_P < it.m_P) {
                    maxM_P = it.m_P
                }
                if (maxM_L < it.m_L) {
                    maxM_L = it.m_L
                }
                if (maxC_P < it.c_P) {
                    maxC_P = it.c_P
                }
                if (maxC_L < it.c_L) {
                    maxC_L = it.c_L
                }
                if (maxP_L< it.p_L) {
                    maxP_L = it.p_L
                }


                if (minOM_OC > it.oM_OC) {
                    minOM_OC = it.oM_OC
                }
                if (minOM_OP > it.oM_OP) {
                    minOM_OP = it.oM_OP
                }
                if (minOM_OL > it.oM_OL) {
                    minOM_OL = it.oM_OL
                }
                if (minOC_OP > it.oC_OP) {
                    minOC_OP = it.oC_OP
                }
                if (minOC_OL > it.oC_OL) {
                    minOC_OL = it.oC_OL
                }
                if (minOP_OL> it.oP_OL) {
                    minOP_OL = it.oP_OL
                }
                if (minM_C > it.m_C) {
                    minM_C = it.m_C
                }
                if (minM_P > it.m_P) {
                    minM_P = it.m_P
                }
                if (minM_L > it.m_L) {
                    minM_L = it.m_L
                }
                if (minC_P > it.c_P) {
                    minC_P = it.c_P
                }
                if (minC_L > it.c_L) {
                    minC_L = it.c_L
                }
                if (minP_L> it.p_L) {
                    minP_L = it.p_L
                }
            }
        }

        list.forEach {
            if (it is ReverseKJsonBean) {
                if (maxOM_M < it.oM_M) {
                    maxOM_M = it.oM_M
                }
                if (minOM_M > it.oM_M) {
                    minOM_M = it.oM_M
                }
                if (maxOM_C < it.oM_C) {
                    maxOM_C = it.oM_C
                }
                if (minOM_C > it.oM_C) {
                    minOM_C = it.oM_C
                }
                if (maxOM_P < it.oM_P) {
                    maxOM_P = it.oM_P
                }
                if (minOM_P > it.oM_P) {
                    minOM_P = it.oM_P
                }
                if (maxOM_L < it.oM_L) {
                    maxOM_L = it.oM_L
                }
                if (minOM_L > it.oM_L) {
                    minOM_L = it.oM_L
                }
                if (maxOC_M < it.oC_M) {
                    maxOC_M = it.oC_M
                }
                if (minOC_M > it.oC_M) {
                    minOC_M = it.oC_M
                }
                if (maxOC_C < it.oC_C) {
                    maxOC_C = it.oC_C
                }
                if (minOC_C > it.oC_C) {
                    minOC_C = it.oC_C
                }
                if (maxOC_P < it.oC_P) {
                    maxOC_P = it.oC_P
                }
                if (minOC_P > it.oC_P) {
                    minOC_P = it.oC_P
                }
                if (maxOC_L < it.oC_L) {
                    maxOC_L = it.oC_L
                }
                if (minOC_L > it.oC_L) {
                    minOC_L = it.oC_L
                }
                if (maxOO_M < it.oO_M) {
                    maxOO_M = it.oO_M
                }
                if (minOO_M > it.oO_M) {
                    minOO_M = it.oO_M
                }
                if (maxOO_C < it.oO_C) {
                    maxOO_C = it.oO_C
                }
                if (minOO_C > it.oO_C) {
                    minOO_C = it.oO_C
                }
                if (maxOO_P < it.oO_P) {
                    maxOO_P = it.oO_P
                }
                if (minOO_P > it.oO_P) {
                    minOO_P = it.oO_P
                }
                if (maxOO_L < it.oO_L) {
                    maxOO_L = it.oO_L
                }
                if (minOO_L > it.oO_L) {
                    minOO_L = it.oO_L
                }
                if (maxOL_M < it.oL_M) {
                    maxOL_M = it.oL_M
                }
                if (minOL_M > it.oL_M) {
                    minOL_M = it.oL_M
                }
                if (maxOL_C < it.oL_C) {
                    maxOL_C = it.oL_C
                }
                if (minOL_C > it.oL_C) {
                    minOL_C = it.oL_C
                }
                if (maxOL_P < it.oL_P) {
                    maxOL_P = it.oL_P
                }
                if (minOL_P > it.oL_P) {
                    minOL_P = it.oL_P
                }
                if (maxOL_L < it.oL_L) {
                    maxOL_L = it.oL_L
                }
                if (minOL_L > it.oL_L) {
                    minOL_L = it.oL_L
                }
            }
        }
        reasoningAllJudgeBean.count = list.size
        reasoningAllJudgeBean.d_T = date
//        if (date != 36) {
//            reasoningAllJudgeBean.l_S_T = i - Datas.FILTER_PROGRESS
//        }
        reasoningAllJudgeBean.oM_M_D = maxOM_M.toGetReasoningJudgeDFloat()
        reasoningAllJudgeBean.oM_C_D = maxOM_C.toGetReasoningJudgeDFloat()
        reasoningAllJudgeBean.oM_P_D = maxOM_P.toGetReasoningJudgeDFloat()
        reasoningAllJudgeBean.oM_L_D = maxOM_L.toGetReasoningJudgeDFloat()
        reasoningAllJudgeBean.oC_M_D = maxOC_M.toGetReasoningJudgeDFloat()
        reasoningAllJudgeBean.oC_C_D = maxOC_C.toGetReasoningJudgeDFloat()
        reasoningAllJudgeBean.oC_P_D = maxOC_P.toGetReasoningJudgeDFloat()
        reasoningAllJudgeBean.oC_L_D = maxOC_L.toGetReasoningJudgeDFloat()
        reasoningAllJudgeBean.oO_M_D = maxOO_M.toGetReasoningJudgeDFloat()
        reasoningAllJudgeBean.oO_C_D = maxOO_C.toGetReasoningJudgeDFloat()
        reasoningAllJudgeBean.oO_P_D = maxOO_P.toGetReasoningJudgeDFloat()
        reasoningAllJudgeBean.oO_L_D = maxOO_L.toGetReasoningJudgeDFloat()
        reasoningAllJudgeBean.oL_M_D = maxOL_M.toGetReasoningJudgeDFloat()
        reasoningAllJudgeBean.oL_C_D = maxOL_C.toGetReasoningJudgeDFloat()
        reasoningAllJudgeBean.oL_P_D = maxOL_P.toGetReasoningJudgeDFloat()
        reasoningAllJudgeBean.oL_L_D = maxOL_L.toGetReasoningJudgeDFloat()

        reasoningAllJudgeBean.oM_M_X = minOM_M.toGetReasoningJudgeXFloat()
        reasoningAllJudgeBean.oM_C_X = minOM_C.toGetReasoningJudgeXFloat()
        reasoningAllJudgeBean.oM_P_X = minOM_P.toGetReasoningJudgeXFloat()
        reasoningAllJudgeBean.oM_L_X = minOM_L.toGetReasoningJudgeXFloat()
        reasoningAllJudgeBean.oC_M_X = minOC_M.toGetReasoningJudgeXFloat()
        reasoningAllJudgeBean.oC_C_X = minOC_C.toGetReasoningJudgeXFloat()
        reasoningAllJudgeBean.oC_P_X = minOC_P.toGetReasoningJudgeXFloat()
        reasoningAllJudgeBean.oC_L_X = minOC_L.toGetReasoningJudgeXFloat()
        reasoningAllJudgeBean.oO_M_X = minOO_M.toGetReasoningJudgeXFloat()
        reasoningAllJudgeBean.oO_C_X = minOO_C.toGetReasoningJudgeXFloat()
        reasoningAllJudgeBean.oO_P_X = minOO_P.toGetReasoningJudgeXFloat()
        reasoningAllJudgeBean.oO_L_X = minOO_L.toGetReasoningJudgeXFloat()
        reasoningAllJudgeBean.oL_M_X = minOL_M.toGetReasoningJudgeXFloat()
        reasoningAllJudgeBean.oL_C_X = minOL_C.toGetReasoningJudgeXFloat()
        reasoningAllJudgeBean.oL_P_X = minOL_P.toGetReasoningJudgeXFloat()
        reasoningAllJudgeBean.oL_L_X = minOL_L.toGetReasoningJudgeXFloat()

        reasoningAllJudgeBean.oM_OC_D = maxOM_OC.toGetReasoningJudgeDFloat()
        reasoningAllJudgeBean.oM_OP_D = maxOM_OP.toGetReasoningJudgeDFloat()
        reasoningAllJudgeBean.oM_OL_D = maxOM_OL.toGetReasoningJudgeDFloat()
        reasoningAllJudgeBean.oC_OP_D = maxOC_OP.toGetReasoningJudgeDFloat()
        reasoningAllJudgeBean.oC_OL_D = maxOC_OL.toGetReasoningJudgeDFloat()
        reasoningAllJudgeBean.oP_OL_D = maxOP_OL.toGetReasoningJudgeDFloat()
        reasoningAllJudgeBean.m_C_D = maxM_C.toGetReasoningJudgeDFloat()
        reasoningAllJudgeBean.m_P_D = maxM_P.toGetReasoningJudgeDFloat()
        reasoningAllJudgeBean.m_L_D = maxM_L.toGetReasoningJudgeDFloat()
        reasoningAllJudgeBean.c_P_D = maxC_P.toGetReasoningJudgeDFloat()
        reasoningAllJudgeBean.c_L_D = maxC_L.toGetReasoningJudgeDFloat()
        reasoningAllJudgeBean.p_L_D = maxP_L.toGetReasoningJudgeDFloat()

        reasoningAllJudgeBean.oM_OC_X= minOM_OC.toGetReasoningJudgeXFloat()
        reasoningAllJudgeBean.oM_OP_X= minOM_OP.toGetReasoningJudgeXFloat()
        reasoningAllJudgeBean.oM_OL_X= minOM_OL.toGetReasoningJudgeXFloat()
        reasoningAllJudgeBean.oC_OP_X= minOC_OP.toGetReasoningJudgeXFloat()
        reasoningAllJudgeBean.oC_OL_X= minOC_OL.toGetReasoningJudgeXFloat()
        reasoningAllJudgeBean.oP_OL_X= minOP_OL.toGetReasoningJudgeXFloat()
        reasoningAllJudgeBean.m_C_X= minM_C.toGetReasoningJudgeXFloat()
        reasoningAllJudgeBean.m_P_X= minM_P.toGetReasoningJudgeXFloat()
        reasoningAllJudgeBean.m_L_X= minM_L.toGetReasoningJudgeXFloat()
        reasoningAllJudgeBean.c_P_X= minC_P.toGetReasoningJudgeXFloat()
        reasoningAllJudgeBean.c_L_X= minC_L.toGetReasoningJudgeXFloat()
        reasoningAllJudgeBean.p_L_X= minP_L.toGetReasoningJudgeXFloat()
        return reasoningAllJudgeBean
    }

    fun revAllReasoning30(
        pt: Int,
        tagList: Array<String>,
        tagIndex: Int,
        dayList: Array<Int>,
        dateRangeIndex: Int,
        list: ArrayList<BaseReverseImp>,
        date: Int,
        insertTB: String,
        iList: ArrayList<Int>
    ) {

        val nextTbName = "A_RTB_${pt}_${dayList[dateRangeIndex]}"
        val nextTbDerbyName = "Derby_A_RTB_${pt}_${dayList[dateRangeIndex]}"
        val mNextCodeList = list.getCodeList()
        val addstr =
            mNextCodeList.getCodeArrayAndLimitSQL(true) + Datas.debugEndstr + Datas.reasoning_debug_end_str
        val countLimit = if (list.size >= 4) 4 else 2
        val comlumn = tagList[tagIndex]
        val (nextMax, nextMin) = getRangeMaxMiByCodeList(
            false,
            nextTbName,
            comlumn,
            mNextCodeList,
            Datas.REVERSE_KJ_DB
        )
        var nextContinue = 0
        for (n in nextMin..nextMax step Datas.FILTER_PROGRESS) {
            if (nextContinue > 0) {
//                LogUtil.d("nextTbName-->$nextTbName ,nextContinue:$nextContinue")
                nextContinue--
                continue
            }
            var dlist = getDlist(nextTbName,comlumn, addstr, n, nextContinue)
            if (null == dlist) {
                continue
            }
            while (dlist!!.size < countLimit && (n + (nextContinue + 1) * Datas.FILTER_PROGRESS) <= nextMax) {
                nextContinue++
                dlist = getDlist(nextTbName,comlumn, addstr, n, nextContinue)
            }
            while ((list.size - dlist!!.size == 1)) {
                nextContinue++
                dlist = getDlist(nextTbName, comlumn,addstr, n, nextContinue)
            }
            if (dlist.size > 1) {
                if (tagIndex == tagList.size - 1) {
                    val derbyList = list.getAllJudgeDerbyList(nextTbDerbyName)
                    val reasoningAllJudgeBean =
                        getReasoningAllJudgeBean(dlist, derbyList, date)
                    for (a in 7 - dateRangeIndex until iList.size) {
                        iList[a] = 0
                    }
                    iList[7 - dateRangeIndex] = n
                    reasoningAllJudgeBean.f36_T = iList[0]
                    reasoningAllJudgeBean.f30_T = iList[1]
                    reasoningAllJudgeBean.f25_T = iList[2]
                    reasoningAllJudgeBean.f20_T = iList[3]
                    reasoningAllJudgeBean.f15_T = iList[4]
                    reasoningAllJudgeBean.f10_T = iList[5]
                    reasoningAllJudgeBean.f05_T = iList[6]
                    reasoningAllJudgeBean.f03_T = iList[7]
                    var istr = ""
                    val idaylist = arrayListOf("36", "30", "25", "20", "15", "10", "5", "3")
                    for (q in 0 until iList.size) {
                        istr = istr + "F_T_${idaylist[q]}-->${iList[q]};"
                    }
                    LogUtil.d("nextTbName-->$nextTbName-->${dlist.size},OM-->($n-${n + Datas.FILTER_PROGRESS + nextContinue * Datas.FILTER_PROGRESS}),istr-->$istr")
                    DBUtils.insertAllJudgeTB(reasoningAllJudgeBean, insertTB)
                }
                var mdateRangeIndex = dateRangeIndex
                var mTagIndex = tagIndex+1
                if (mTagIndex == tagList.size) {
                    mdateRangeIndex = dateRangeIndex - 1
                    mTagIndex = 0
                }
                if ((mdateRangeIndex) >= 0) {
                    revAllReasoning30(
                        pt,
                        tagList,
                        mTagIndex,
                        dayList,
                        mdateRangeIndex,
                        dlist,
                        dayList[mdateRangeIndex],
                        insertTB,
                        iList
                    )
                }
            }
        }
    }

    fun revOCOOlReasoning(
        tagList: Array<String>,
        tagIndex: Int,
        dayList: Array<Int>,
        dateRangeIndex: Int,
        list: ArrayList<BaseReverseImp>,
        date: Int,
        tbName: String,
        insertTB: String
    ) {
        var needSkip = false
        if (tagIndex == tagList.size - 1) {
            if (date == 3 || date == 15 || date == 25 || date == 35 || date == 45 || date == 55 || date == 65) {
                needSkip = true
            }
        } else if (tagIndex == tagList.size - 2) {
            if (date == 3 ) {
                needSkip = true
            }
        }
        if (needSkip) {
            var mTagIndex = tagIndex
            var mDateRangeIndex = dateRangeIndex -1
            if (date == 3) {
                mTagIndex++
                mDateRangeIndex = dayList.size-2
            }
            if (mTagIndex < tagList.size) {
                revOCOOlReasoning(
                    tagList,
                    mTagIndex,
                    dayList,
                    mDateRangeIndex,
                    list,
                    dayList[mDateRangeIndex],
                    tbName,
                    insertTB
                )
                return
            }
        }
        val mNextCodeList = list.getCodeList()
        val addstr =
            mNextCodeList.getCodeArrayAndLimitSQL(true) + Datas.debugEndstr + Datas.reasoning_debug_end_str

        val column = "${tagList[tagIndex]}$date"
        val (nextMax, nextMin) = getRangeMaxMiByCodeList(
            true,
            tbName,
            column,
            mNextCodeList,
            Datas.REVERSE_KJ_DB
        )

        var nextContinue = 0
        val countLimit = if (list.size >= 4) 4 else 2
        for (n in nextMin..nextMax step Datas.FILTER_OC_OO_PROGRESS) {
            if (nextContinue > 0) {
                nextContinue--
                continue
            }
            var dlist = getOCOODlist(tbName, addstr, column,n,nextContinue)
            if (null == dlist) {
                continue
            }

            while (dlist!!.size < countLimit && (n + (nextContinue + 1) * Datas.FILTER_OC_OO_PROGRESS) <= nextMax) {
                nextContinue++
                dlist = getOCOODlist(tbName, addstr, column,n, nextContinue)
            }
//            val (mTagIndex, mDateRangeIndex) = getNextRevOCOOParameter(
//                tagIndex,
//                dateRangeIndex,
//                tagList,
//                dayList
//            )
            if (dlist.size > 0) {
                if (tagIndex == tagList.size-2 && date == 5) {
                    val reasoningAllJudgeBean = getReasoningOCOOJudgeBean(list,n,n+ Datas.FILTER_OC_OO_PROGRESS)
                    val codeInfo =
                        list.getCodeList().getCodeArrayAndLimitSQL(true) + Datas.debugEndstr + Datas.reasoning_debug_end_str
                    LogUtil.d("codeInfo--(${reasoningAllJudgeBean.oC70_X}-${reasoningAllJudgeBean.oC70_D}):\n$codeInfo")
                    DBUtils.insertOCOOJudgeTB(reasoningAllJudgeBean, insertTB)
                } else {
                    revOCOOlReasoning(
                        tagList,
                        if (date==3) tagIndex + 1 else tagIndex,
                        dayList,
                        if (dateRangeIndex>0)dateRangeIndex-1 else dayList.size - 2,
                        dlist,
                        dayList[if (dateRangeIndex>0)dateRangeIndex-1 else dayList.size - 2],
                        tbName,
                        insertTB
                    )
                }
            }
        }
    }



    fun getOCOODlist(
        nextTbName: String,
        addstr: String,
        column: String,
        n: Int,
        nextContinue: Int
    ): ArrayList<BaseReverseImp>? {
        val dlist = DBUtils.getFilterAllByTbName(
            Datas.REVERSE_KJ_DB,
            "SELECT * FROM $nextTbName WHERE $column >=? AND $column<? $addstr",
            arrayOf(
                n.toString(),
                (n + Datas.FILTER_OC_OO_PROGRESS+ nextContinue * Datas.FILTER_PROGRESS ).toString()
            ),true
        )
        return dlist
    }

    fun getDlist(
        nextTbName: String,
        column: String,
        addstr: String,
        n: Int,
        nextContinue: Int
    ): ArrayList<BaseReverseImp>? {
        val dlist = DBUtils.getFilterAllByTbName(
            Datas.REVERSE_KJ_DB,
            "SELECT * FROM $nextTbName WHERE $column >=? AND $column<? $addstr",
            arrayOf(
                n.toString(),
                (n + Datas.FILTER_PROGRESS + nextContinue * Datas.FILTER_PROGRESS).toString()
            )
        )
        return dlist
    }

    fun filterAllReasoning(
        allOmM: Float,
        dayType: Int,
        targetBeanList: ArrayList<CodeHDDBean>,
        oldBeanList: ArrayList<CodeHDDBean>,
        allReasoning50Bean: ReasoningRevBean,
        allReasoning30Bean: ReasoningRevBean,
        continue50: Boolean,
        continue30: Boolean
    ): Pair<Boolean, Boolean> {

        oldBeanList.sortAscByDate()
        targetBeanList.sortAscByDate()
        val OC = oldBeanList[0].p_MA_J.aacp
        val OO = oldBeanList[oldBeanList.size - 1].p_MA_J.aacp
        val C = targetBeanList[0].p_MA_J.aacp
        val O = targetBeanList[targetBeanList.size - 1].p_MA_J.aaop
//        val targetDate = targetBeanList[0].date
        val OM = oldBeanList.getRevBeansOM()
        val OL = oldBeanList.getRevBeansOL()
        val M = targetBeanList.getRevBeansOM()
        val L = targetBeanList.getRevBeansOL()
        var need50Continue = continue50
        var need30Continue = continue30

        val OM_M = ((OM - M) / OM * 100).toKeep2()
        val OM_C = ((OM - C) / OM * 100).toKeep2()
        val OM_P = ((OM - O) / OM * 100).toKeep2()
        val OM_L = ((OM - L) / OM * 100).toKeep2()

        val OL_M = ((OL - M) / OL * 100).toKeep2()
        val OL_C = ((OL - C) / OL * 100).toKeep2()
        val OL_P = ((OL - O) / OL * 100).toKeep2()
        val OL_L = ((OL - L) / OL * 100).toKeep2()

        val OC_M = ((OC - M) / OC * 100).toKeep2()
        val OC_C = ((OC - C) / OC * 100).toKeep2()
        val OC_P = ((OC - O) / OC * 100).toKeep2()
        val OC_L = ((OC - L) / OC * 100).toKeep2()

        val OP_M = ((OO - M) / OO * 100).toKeep2()
        val OP_C = ((OO - C) / OO * 100).toKeep2()
        val OP_P = ((OO - O) / OO * 100).toKeep2()
        val OP_L = ((OO - L) / OO * 100).toKeep2()


        val OM_OC = ((OM - OC) / OM * 100).toKeep2()
        val OM_OP = ((OM - OO) / OM * 100).toKeep2()
        val OM_OL = ((OM - OL) / OM * 100).toKeep2()
        val OC_OP = ((OC - OO) / OC * 100).toKeep2()
        val OC_OL = ((OC - OL) / OC * 100).toKeep2()
        val OP_OL = ((OO - OL) / OO * 100).toKeep2()

        val M_C = ((M - C) / M * 100).toKeep2()
        val M_P = ((M - O) / M * 100).toKeep2()
        val M_L = ((M - L) / M * 100).toKeep2()
        val C_P = ((C - O) / C * 100).toKeep2()
        val C_L = ((C - L) / C * 100).toKeep2()
        val P_L = ((O - L) / O * 100).toKeep2()

        if (need50Continue) {
            val juede50BeanList = DBUtils.getReasoningAllJudgeBeanByAllOmM(
                allOmM,
                true,
                dayType + 1,
                allReasoning50Bean
            )
            need50Continue = getNeedContinue(
                dayType + 1,
                juede50BeanList,
                need50Continue,
                OM_M,
                OM_C,
                OM_P,
                OM_L,
                OL_M,
                OL_C,
                OL_P,
                OL_L,
                OC_M,
                OC_C,
                OC_P,
                OC_L,
                OP_M,
                OP_C,
                OP_P,
                OP_L,
                allReasoning50Bean
            )
            if (need50Continue) {
                need50Continue = getDerbyNeedContinue(
                    juede50BeanList,
                    need50Continue,
                    OM_OC,
                    OM_OP,
                    OM_OL,
                    OC_OP,
                    OC_OL,
                    OP_OL,
                    M_C,
                    M_P,
                    M_L,
                    C_P,
                    C_L,
                    P_L
                )

//                LogUtil.d("getDerbyNeedContinue-->$need50Continue")
            }
        }
        if (need30Continue) {
            val juede30BeanList = DBUtils.getReasoningAllJudgeBeanByAllOmM(
                allOmM,
                false,
                dayType + 1,
                allReasoning30Bean
            )
            need30Continue = getNeedContinue(
                dayType + 1,
                juede30BeanList,
                need30Continue,
                OM_M,
                OM_C,
                OM_P,
                OM_L,
                OL_M,
                OL_C,
                OL_P,
                OL_L,
                OC_M,
                OC_C,
                OC_P,
                OC_L,
                OP_M,
                OP_C,
                OP_P,
                OP_L,
                allReasoning30Bean
            )
            if (need30Continue) {
                need30Continue = getDerbyNeedContinue(
                    juede30BeanList,
                    need30Continue,
                    OM_OC,
                    OM_OP,
                    OM_OL,
                    OC_OP,
                    OC_OL,
                    OP_OL,
                    M_C,
                    M_P,
                    M_L,
                    C_P,
                    C_L,
                    P_L
                )
//                LogUtil.d("getDerbyNeedContinue-->$need30Continue")
            }
        }

        return Pair(need50Continue, need30Continue)


    }

    private fun getNeedContinue(
        dayType: Int,
        juede50BeanList: ArrayList<ReasoningAllJudgeBean>,
        need50Continue: Boolean,
        OM_M: Float,
        OM_C: Float,
        OM_P: Float,
        OM_L: Float,
        OL_M: Float,
        OL_C: Float,
        OL_P: Float,
        OL_L: Float,
        OC_M: Float,
        OC_C: Float,
        OC_P: Float,
        OC_L: Float,
        OP_M: Float,
        OP_C: Float,
        OP_P: Float,
        OP_L: Float,
        allReasoning50Bean: ReasoningRevBean
    ): Boolean {
        var need50Continue1 = need50Continue
        if (juede50BeanList.size > 0) {
            juede50BeanList.forEach {
                need50Continue1 =
                    (OM_M >= it.oM_M_X && OM_M <= it.oM_M_D) && (OM_C >= it.oM_C_X && OM_C <= it.oM_C_D) && (OM_P >= it.oM_P_X && OM_P <= it.oM_P_D) && (OM_L >= it.oM_L_X && OM_L <= it.oM_L_D) &&
                            (OL_M >= it.oL_M_X && OL_M <= it.oL_M_D) && (OL_C >= it.oL_C_X && OL_C <= it.oL_C_D) && (OL_P >= it.oL_P_X && OL_P <= it.oL_P_D) && (OL_L >= it.oL_L_X && OL_L <= it.oL_L_D) &&
                            (OC_M >= it.oC_M_X && OC_M <= it.oC_M_D) && (OC_C >= it.oC_C_X && OC_C <= it.oC_C_D) && (OC_P >= it.oC_P_X && OC_P <= it.oC_P_D) && (OC_L >= it.oC_L_X && OC_L <= it.oC_L_D) &&
                            (OP_M >= it.oO_M_X && OP_M <= it.oO_M_D) && (OP_C >= it.oO_C_X && OP_C <= it.oO_C_D) && (OP_P >= it.oO_P_X && OP_P <= it.oO_P_D) && (OP_L >= it.oO_L_X && OP_L <= it.oO_L_D)
//                    LogUtil.d("1--->${(OM_M >= it.oM_M_X && OM_M <= it.oM_M_D)&&(OM_C >= it.oM_C_X && OM_C <= it.oM_C_D)&&(OM_P >= it.oM_P_X && OM_P <= it.oM_P_D)&&(OM_L >= it.oM_L_X && OM_L <= it.oM_L_D)}")
//                    LogUtil.d("2--->${(OL_M >= it.oL_M_X && OL_M <= it.oL_M_D)&&(OL_C >= it.oL_C_X && OL_C <= it.oL_C_D)&&(OL_P >= it.oL_P_X && OL_P <= it.oL_P_D)&&(OL_L >= it.oL_L_X && OL_L <= it.oL_L_D)}")
//                    LogUtil.d("3--->${(OC_M >= it.oC_M_X && OC_M <= it.oC_M_D)&&(OC_C >= it.oC_C_X && OC_C <= it.oC_C_D)&&(OC_P >= it.oC_P_X && OC_P <= it.oC_P_D)&&(OC_L >= it.oC_L_X && OC_L <= it.oC_L_D)}")
////                    LogUtil.d("OC_P-->$OC_P")
//                    LogUtil.d("3--->${"${OC_M >= it.oC_M_X && OC_M <= it.oC_M_D },${OC_C >= it.oC_C_X && OC_C <= it.oC_C_D},${OC_P >= it.oC_P_X && OC_P <= it.oC_P_D},${OC_L >= it.oC_L_X && OC_L <= it.oC_L_D}"}")
//                    LogUtil.d("4--->${(OP_M >= it.oO_M_X && OP_M <= it.oO_M_D)&&(OP_C >= it.oO_C_X && OP_C <= it.oO_C_D)&&(OP_P >= it.oO_P_X && OP_P <= it.oO_P_D)&&(OP_L >= it.oO_L_X && OP_L <= it.oO_L_D)}")

                if (need50Continue1) {
                    allReasoning50Bean.f36_T = it.f36_T
                    allReasoning50Bean.f30_T = it.f30_T
                    allReasoning50Bean.f25_T = it.f25_T
                    allReasoning50Bean.f20_T = it.f20_T
                    allReasoning50Bean.f15_T = it.f15_T
                    allReasoning50Bean.f10_T = it.f10_T
                    allReasoning50Bean.f05_T = it.f05_T
                    allReasoning50Bean.f03_T = it.f03_T
                    when (dayType) {
                        36->{
                            allReasoning50Bean.l36 = it.oL_L_X.toGetSimpleX()
                            allReasoning50Bean.c36 = it.oC_C_X.toGetSimpleX()
                            allReasoning50Bean.o36 = it.oO_P_X.toGetSimpleX()
                        }
                        30->{
                            allReasoning50Bean.l30 = it.oL_L_X.toGetSimpleX()
                            allReasoning50Bean.c30 = it.oC_C_X.toGetSimpleX()
                            allReasoning50Bean.o30 = it.oO_P_X.toGetSimpleX()
                        }
                        25->{
                            allReasoning50Bean.l25 = it.oL_L_X.toGetSimpleX()
                            allReasoning50Bean.c25 = it.oC_C_X.toGetSimpleX()
                            allReasoning50Bean.o25 = it.oO_P_X.toGetSimpleX()
                        }
                        20->{
                            allReasoning50Bean.l20 = it.oL_L_X.toGetSimpleX()
                            allReasoning50Bean.c20 = it.oC_C_X.toGetSimpleX()
                            allReasoning50Bean.o20 = it.oO_P_X.toGetSimpleX()
                        }
                        15->{
                            allReasoning50Bean.l15 = it.oL_L_X.toGetSimpleX()
                            allReasoning50Bean.c15 = it.oC_C_X.toGetSimpleX()
                            allReasoning50Bean.o15 = it.oO_P_X.toGetSimpleX()
                        }
                        10->{
                            allReasoning50Bean.l10 = it.oL_L_X.toGetSimpleX()
                            allReasoning50Bean.c10 = it.oC_C_X.toGetSimpleX()
                            allReasoning50Bean.o10 = it.oO_P_X.toGetSimpleX()
                        }
                        5->{
                            allReasoning50Bean.l05 = it.oL_L_X.toGetSimpleX()
                            allReasoning50Bean.c05 = it.oC_C_X.toGetSimpleX()
                            allReasoning50Bean.o05 = it.oO_P_X.toGetSimpleX()
                        }
                        else ->{
                            allReasoning50Bean.l03 = it.oL_L_X.toGetSimpleX()
                            allReasoning50Bean.c03 = it.oC_C_X.toGetSimpleX()
                            allReasoning50Bean.o03 = it.oO_P_X.toGetSimpleX()
                        }
                    }
                }
            }
        } else {
            need50Continue1 = false
//            LogUtil.d("juede50BeanList-->$need50Continue1")
        }
        return need50Continue1
    }

    private fun getDerbyNeedContinue(
        juede50BeanList: ArrayList<ReasoningAllJudgeBean>,
        need50Continue: Boolean,
        OM_OC: Float,
        OM_OP: Float,
        OM_OL: Float,
        OC_OP: Float,
        OC_OL: Float,
        OP_OL: Float,
        M_C: Float,
        M_P: Float,
        M_L: Float,
        C_P: Float,
        C_L: Float,
        P_L: Float
    ): Boolean {
        var need50Continue1 = need50Continue
        if (juede50BeanList.size > 0) {
            juede50BeanList.forEach {
                need50Continue1 =
                    (OM_OC >= it.oM_OC_X && OM_OC <= it.oM_OC_D) && (OM_OP >= it.oM_OP_X && OM_OP <= it.oM_OP_D) && (OM_OL >= it.oM_OL_X && OM_OL <= it.oM_OL_D) && (OC_OP >= it.oC_OP_X && OC_OP <= it.oC_OP_D) &&
                            (OC_OL >= it.oC_OL_X && OC_OL <= it.oC_OL_D) && (OP_OL >= it.oP_OL_X && OP_OL <= it.oP_OL_D) &&
                            (M_C >= it.m_C_X && M_C  <= it.m_C_D) && (M_P >= it.m_P_X && M_P <= it.m_P_D) && (M_L >= it.m_L_X && M_L <= it.m_L_D) && (C_P >= it.c_P_X && C_P <= it.c_P_D) &&
                            (C_L >= it.c_L_X && C_L <= it.c_L_D) && (P_L >= it.p_L_X && P_L <= it.p_L_D)
//                LogUtil.d("1-->${(OM_OC >= it.oM_OC_X && OM_OC <= it.oM_OC_D)},$OM_OC,${it.oM_OC_X},${it.oM_OC_D}")
//                LogUtil.d("2-->${(OM_OP >= it.oM_OP_X && OM_OP <= it.oM_OP_D)}")
//                LogUtil.d("3-->${(OM_OL >= it.oM_OL_X && OM_OL <= it.oM_OL_D)}")
//                LogUtil.d("4-->${(OC_OP >= it.oC_OP_X && OC_OP <= it.oC_OP_D)}")
//                LogUtil.d("5-->${(OC_OL >= it.oC_OL_X && OC_OL <= it.oC_OL_D)}")
//                LogUtil.d("6-->${(OP_OL >= it.oP_OL_X && OP_OL <= it.oP_OL_D)}")
//                LogUtil.d("7-->${(M_C >= it.m_C_X && M_C  <= it.m_C_D) }")
//                LogUtil.d("8-->${(M_P >= it.m_P_X && M_P <= it.m_P_D)}")
//                LogUtil.d("9-->${(M_L >= it.m_L_X && M_L <= it.m_L_D)}")
//                LogUtil.d("10-->${(C_P >= it.c_P_X && C_P <= it.c_P_D) }")
//                LogUtil.d("11-->${(C_L >= it.c_L_X && C_L <= it.c_L_D)}")
//                LogUtil.d("12-->${(P_L >= it.p_L_X && P_L <= it.p_L_D)}")
            }
        } else {
            need50Continue1 = false
        }
        return need50Continue1
    }

    fun getReasoningOCOOJudgeBean(
        list: java.util.ArrayList<BaseReverseImp>,
        min: Int,
        max: Int
    ): ReasoningAllJudgeBean {

        val reasoningAllJudgeBean = ReasoningAllJudgeBean()
        reasoningAllJudgeBean.oC3_D


        if (list.size > 1) {
            reasoningAllJudgeBean.oC3_D = -10086.toFloat()
            reasoningAllJudgeBean.oC5_D = -10086.toFloat()
            reasoningAllJudgeBean.oC10_D = -10086.toFloat()
            reasoningAllJudgeBean.oC15_D = -10086.toFloat()
            reasoningAllJudgeBean.oC20_D = -10086.toFloat()
            reasoningAllJudgeBean.oC25_D = -10086.toFloat()
            reasoningAllJudgeBean.oC30_D = -10086.toFloat()
            reasoningAllJudgeBean.oC35_D = -10086.toFloat()
            reasoningAllJudgeBean.oC40_D = -10086.toFloat()
            reasoningAllJudgeBean.oC45_D = -10086.toFloat()
            reasoningAllJudgeBean.oC50_D = -10086.toFloat()
            reasoningAllJudgeBean.oC55_D = -10086.toFloat()
            reasoningAllJudgeBean.oC60_D = -10086.toFloat()
            reasoningAllJudgeBean.oC65_D = -10086.toFloat()
            reasoningAllJudgeBean.oC70_D = -10086.toFloat()

            reasoningAllJudgeBean.oC3_X = 10086.toFloat()
            reasoningAllJudgeBean.oC5_X = 10086.toFloat()
            reasoningAllJudgeBean.oC10_X = 10086.toFloat()
            reasoningAllJudgeBean.oC15_X = 10086.toFloat()
            reasoningAllJudgeBean.oC20_X = 10086.toFloat()
            reasoningAllJudgeBean.oC25_X = 10086.toFloat()
            reasoningAllJudgeBean.oC30_X = 10086.toFloat()
            reasoningAllJudgeBean.oC35_X = 10086.toFloat()
            reasoningAllJudgeBean.oC40_X = 10086.toFloat()
            reasoningAllJudgeBean.oC45_X = 10086.toFloat()
            reasoningAllJudgeBean.oC50_X = 10086.toFloat()
            reasoningAllJudgeBean.oC55_X = 10086.toFloat()
            reasoningAllJudgeBean.oC60_X = 10086.toFloat()
            reasoningAllJudgeBean.oC65_X = 10086.toFloat()
            reasoningAllJudgeBean.oC70_X = 10086.toFloat()

            reasoningAllJudgeBean.pP5_D = -10086.toFloat()
            reasoningAllJudgeBean.pP10_D = -10086.toFloat()
            reasoningAllJudgeBean.pP15_D = -10086.toFloat()
            reasoningAllJudgeBean.pP20_D = -10086.toFloat()
            reasoningAllJudgeBean.pP25_D = -10086.toFloat()
            reasoningAllJudgeBean.pP30_D = -10086.toFloat()
            reasoningAllJudgeBean.pP35_D = -10086.toFloat()
            reasoningAllJudgeBean.pP40_D = -10086.toFloat()
            reasoningAllJudgeBean.pP45_D = -10086.toFloat()
            reasoningAllJudgeBean.pP50_D = -10086.toFloat()
            reasoningAllJudgeBean.pP55_D = -10086.toFloat()
            reasoningAllJudgeBean.pP60_D = -10086.toFloat()
            reasoningAllJudgeBean.pP65_D = -10086.toFloat()
            reasoningAllJudgeBean.pP70_D = -10086.toFloat()

            reasoningAllJudgeBean.pP5_X = 10086.toFloat()
            reasoningAllJudgeBean.pP10_X = 10086.toFloat()
            reasoningAllJudgeBean.pP15_X = 10086.toFloat()
            reasoningAllJudgeBean.pP20_X = 10086.toFloat()
            reasoningAllJudgeBean.pP25_X = 10086.toFloat()
            reasoningAllJudgeBean.pP30_X = 10086.toFloat()
            reasoningAllJudgeBean.pP35_X = 10086.toFloat()
            reasoningAllJudgeBean.pP40_X = 10086.toFloat()
            reasoningAllJudgeBean.pP45_X = 10086.toFloat()
            reasoningAllJudgeBean.pP50_X = 10086.toFloat()
            reasoningAllJudgeBean.pP55_X = 10086.toFloat()
            reasoningAllJudgeBean.pP60_X = 10086.toFloat()
            reasoningAllJudgeBean.pP65_X = 10086.toFloat()
            reasoningAllJudgeBean.pP70_X = 10086.toFloat()



            reasoningAllJudgeBean.ppP5_D = -10086.toFloat()
            reasoningAllJudgeBean.ppP10_D = -10086.toFloat()
            reasoningAllJudgeBean.ppP20_D = -10086.toFloat()
            reasoningAllJudgeBean.ppP30_D = -10086.toFloat()
            reasoningAllJudgeBean.ppP40_D = -10086.toFloat()
            reasoningAllJudgeBean.ppP50_D = -10086.toFloat()
            reasoningAllJudgeBean.ppP60_D = -10086.toFloat()
            reasoningAllJudgeBean.ppP70_D = -10086.toFloat()

            reasoningAllJudgeBean.ppP5_X = 10086.toFloat()
            reasoningAllJudgeBean.ppP10_X = 10086.toFloat()
            reasoningAllJudgeBean.ppP20_X = 10086.toFloat()
            reasoningAllJudgeBean.ppP30_X = 10086.toFloat()
            reasoningAllJudgeBean.ppP40_X = 10086.toFloat()
            reasoningAllJudgeBean.ppP50_X = 10086.toFloat()
            reasoningAllJudgeBean.ppP60_X = 10086.toFloat()
            reasoningAllJudgeBean.ppP70_X = 10086.toFloat()


            reasoningAllJudgeBean.s_A_TR_D = -10086.toFloat()
            reasoningAllJudgeBean.s_R_TR_D = -10086.toFloat()
            reasoningAllJudgeBean.s_B_TR_D = -10086.toFloat()
            reasoningAllJudgeBean.s_C_TR_D = -10086.toFloat()
            reasoningAllJudgeBean.k_A_TR_D = -10086.toFloat()
            reasoningAllJudgeBean.k_R_TR_D = -10086.toFloat()
            reasoningAllJudgeBean.k_B_TR_D = -10086.toFloat()
            reasoningAllJudgeBean.k_C_TR_D = -10086.toFloat()
            reasoningAllJudgeBean.k_SL_A_TR_D = -10086.toFloat()
            reasoningAllJudgeBean.k_SL_R_TR_D = -10086.toFloat()
            reasoningAllJudgeBean.k_SL_B_TR_D = -10086.toFloat()
            reasoningAllJudgeBean.k_SL_C_TR_D = -10086.toFloat()

            reasoningAllJudgeBean.s_A_TR_X = 10086.toFloat()
            reasoningAllJudgeBean.s_R_TR_X = 10086.toFloat()
            reasoningAllJudgeBean.s_B_TR_X = 10086.toFloat()
            reasoningAllJudgeBean.s_C_TR_X = 10086.toFloat()
            reasoningAllJudgeBean.k_A_TR_X = 10086.toFloat()
            reasoningAllJudgeBean.k_R_TR_X = 10086.toFloat()
            reasoningAllJudgeBean.k_B_TR_X = 10086.toFloat()
            reasoningAllJudgeBean.k_C_TR_X = 10086.toFloat()
            reasoningAllJudgeBean.k_SL_A_TR_X = 10086.toFloat()
            reasoningAllJudgeBean.k_SL_R_TR_X = 10086.toFloat()
            reasoningAllJudgeBean.k_SL_B_TR_X = 10086.toFloat()
            reasoningAllJudgeBean.k_SL_C_TR_X = 10086.toFloat()



            list.forEach {
                if (it is ReverseKJsonBean) {

                    if (reasoningAllJudgeBean.oC3_D < it.oC3) {
                        reasoningAllJudgeBean.oC3_D = it.oC3.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oC5_D < it.oC5) {
                        reasoningAllJudgeBean.oC5_D = it.oC5.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oC10_D < it.oC10) {
                        reasoningAllJudgeBean.oC10_D = it.oC10.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oC15_D < it.oC15) {
                        reasoningAllJudgeBean.oC15_D = it.oC15.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oC20_D < it.oC20) {
                        reasoningAllJudgeBean.oC20_D = it.oC20.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oC25_D < it.oC25) {
                        reasoningAllJudgeBean.oC25_D = it.oC25.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oC30_D < it.oC30) {
                        reasoningAllJudgeBean.oC30_D = it.oC30.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oC35_D < it.oC35) {
                        reasoningAllJudgeBean.oC35_D = it.oC35.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oC40_D < it.oC40) {
                        reasoningAllJudgeBean.oC40_D = it.oC40.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oC45_D < it.oC45) {
                        reasoningAllJudgeBean.oC45_D = it.oC45.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oC50_D < it.oC50) {
                        reasoningAllJudgeBean.oC50_D = it.oC50.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oC55_D < it.oC55) {
                        reasoningAllJudgeBean.oC55_D = it.oC55.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oC60_D < it.oC60) {
                        reasoningAllJudgeBean.oC60_D = it.oC60.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oC65_D < it.oC65) {
                        reasoningAllJudgeBean.oC65_D = it.oC65.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oC70_D < it.oC70) {
                        reasoningAllJudgeBean.oC70_D = it.oC70.toOCOORevJudgeMax()
                    }

                    if (reasoningAllJudgeBean.oO3_D < it.oO3) {
                        reasoningAllJudgeBean.oO3_D = it.oO3.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oO5_D < it.oO5) {
                        reasoningAllJudgeBean.oO5_D = it.oO5.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oO10_D < it.oO10) {
                        reasoningAllJudgeBean.oO10_D = it.oO10.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oO15_D < it.oO15) {
                        reasoningAllJudgeBean.oO15_D = it.oO15.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oO20_D < it.oO20) {
                        reasoningAllJudgeBean.oO20_D = it.oO20.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oO25_D < it.oO25) {
                        reasoningAllJudgeBean.oO25_D = it.oO25.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oO30_D < it.oO30) {
                        reasoningAllJudgeBean.oO30_D = it.oO30.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oO35_D < it.oO35) {
                        reasoningAllJudgeBean.oO35_D = it.oO35.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oO40_D < it.oO40) {
                        reasoningAllJudgeBean.oO40_D = it.oO40.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oO45_D < it.oO45) {
                        reasoningAllJudgeBean.oO45_D = it.oO45.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oO50_D < it.oO50) {
                        reasoningAllJudgeBean.oO50_D = it.oO50.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oO55_D < it.oO55) {
                        reasoningAllJudgeBean.oO55_D = it.oO55.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oO60_D < it.oO60) {
                        reasoningAllJudgeBean.oO60_D = it.oO60.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oO65_D < it.oO65) {
                        reasoningAllJudgeBean.oO65_D = it.oO65.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.oO70_D < it.oO70) {
                        reasoningAllJudgeBean.oO70_D = it.oO70.toOCOORevJudgeMax()
                    }

                    if (reasoningAllJudgeBean.pP5_D < it.pP5) {
                        reasoningAllJudgeBean.pP5_D = it.pP5.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.pP10_D < it.pP10) {
                        reasoningAllJudgeBean.pP10_D = it.pP10.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.pP15_D < it.pP15) {
                        reasoningAllJudgeBean.pP15_D = it.pP15.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.pP20_D < it.pP20) {
                        reasoningAllJudgeBean.pP20_D = it.pP20.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.pP25_D < it.pP25) {
                        reasoningAllJudgeBean.pP25_D = it.pP25.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.pP30_D < it.pP30) {
                        reasoningAllJudgeBean.pP30_D = it.pP30.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.pP35_D < it.pP35) {
                        reasoningAllJudgeBean.pP35_D = it.pP35.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.pP40_D < it.pP40) {
                        reasoningAllJudgeBean.pP40_D = it.pP40.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.pP45_D < it.pP45) {
                        reasoningAllJudgeBean.pP45_D = it.pP45.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.pP50_D < it.pP50) {
                        reasoningAllJudgeBean.pP50_D = it.pP50.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.pP55_D < it.pP55) {
                        reasoningAllJudgeBean.pP55_D = it.pP55.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.pP60_D < it.pP60) {
                        reasoningAllJudgeBean.pP60_D = it.pP60.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.pP65_D < it.pP65) {
                        reasoningAllJudgeBean.pP65_D = it.pP65.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.pP70_D < it.pP70) {
                        reasoningAllJudgeBean.pP70_D = it.pP70.toOCOORevJudgeMax()
                    }


                    if (reasoningAllJudgeBean.ppP5_D < it.ppP5) {
                        reasoningAllJudgeBean.ppP5_D = it.ppP5.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.ppP10_D < it.ppP10) {
                        reasoningAllJudgeBean.ppP10_D = it.ppP10.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.ppP20_D < it.ppP20) {
                        reasoningAllJudgeBean.ppP20_D = it.ppP20.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.ppP30_D < it.ppP30) {
                        reasoningAllJudgeBean.ppP30_D = it.ppP30.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.ppP40_D < it.ppP40) {
                        reasoningAllJudgeBean.ppP40_D = it.ppP40.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.ppP50_D < it.ppP50) {
                        reasoningAllJudgeBean.ppP50_D = it.ppP50.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.ppP60_D < it.ppP60) {
                        reasoningAllJudgeBean.ppP60_D = it.ppP60.toOCOORevJudgeMax()
                    }
                    if (reasoningAllJudgeBean.ppP70_D < it.ppP70) {
                        reasoningAllJudgeBean.ppP70_D = it.ppP70.toOCOORevJudgeMax()
                    }

                    if (reasoningAllJudgeBean.oC3_X > it.oC3) {
                        reasoningAllJudgeBean.oC3_X = it.oC3.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oC5_X > it.oC5) {
                        reasoningAllJudgeBean.oC5_X = it.oC5.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oC10_X > it.oC10) {
                        reasoningAllJudgeBean.oC10_X = it.oC10.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oC15_X > it.oC15) {
                        reasoningAllJudgeBean.oC15_X = it.oC15.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oC20_X > it.oC20) {
                        reasoningAllJudgeBean.oC20_X = it.oC20.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oC25_X > it.oC25) {
                        reasoningAllJudgeBean.oC25_X = it.oC25.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oC30_X > it.oC30) {
                        reasoningAllJudgeBean.oC30_X = it.oC30.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oC35_X > it.oC35) {
                        reasoningAllJudgeBean.oC35_X = it.oC35.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oC40_X > it.oC40) {
                        reasoningAllJudgeBean.oC40_X = it.oC40.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oC45_X > it.oC45) {
                        reasoningAllJudgeBean.oC45_X = it.oC45.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oC50_X > it.oC50) {
                        reasoningAllJudgeBean.oC50_X = it.oC50.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oC55_X > it.oC55) {
                        reasoningAllJudgeBean.oC55_X = it.oC55.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oC60_X > it.oC60) {
                        reasoningAllJudgeBean.oC60_X = it.oC60.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oC65_X > it.oC65) {
                        reasoningAllJudgeBean.oC65_X = it.oC65.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oC70_X > it.oC70) {
                        reasoningAllJudgeBean.oC70_X = it.oC70.toOCOORevJudgeMin()
                    }


                    if (reasoningAllJudgeBean.oO3_X > it.oO3) {
                        reasoningAllJudgeBean.oO3_X = it.oO3.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oO5_X > it.oO5) {
                        reasoningAllJudgeBean.oO5_X = it.oO5.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oO10_X > it.oO10) {
                        reasoningAllJudgeBean.oO10_X = it.oO10.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oO15_X > it.oO15) {
                        reasoningAllJudgeBean.oO15_X = it.oO15.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oO20_X > it.oO20) {
                        reasoningAllJudgeBean.oO20_X = it.oO20.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oO25_X > it.oO25) {
                        reasoningAllJudgeBean.oO25_X = it.oO25.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oO30_X > it.oO30) {
                        reasoningAllJudgeBean.oO30_X = it.oO30.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oO35_X > it.oO35) {
                        reasoningAllJudgeBean.oO35_X = it.oO35.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oO40_X > it.oO40) {
                        reasoningAllJudgeBean.oO40_X = it.oO40.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oO45_X > it.oO45) {
                        reasoningAllJudgeBean.oO45_X = it.oO45.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oO50_X > it.oO50) {
                        reasoningAllJudgeBean.oO50_X = it.oO50.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oO55_X > it.oO55) {
                        reasoningAllJudgeBean.oO55_X = it.oO55.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oO60_X > it.oO60) {
                        reasoningAllJudgeBean.oO60_X = it.oO60.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oO65_X > it.oO65) {
                        reasoningAllJudgeBean.oO65_X = it.oO65.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.oO70_X > it.oO70) {
                        reasoningAllJudgeBean.oO70_X = it.oO70.toOCOORevJudgeMin()
                    }


                    if (reasoningAllJudgeBean.pP5_X > it.pP5) {
                        reasoningAllJudgeBean.pP5_X = it.pP5.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.pP10_X > it.pP10) {
                        reasoningAllJudgeBean.pP10_X = it.pP10.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.pP15_X > it.pP15) {
                        reasoningAllJudgeBean.pP15_X = it.pP15.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.pP20_X > it.pP20) {
                        reasoningAllJudgeBean.pP20_X = it.pP20.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.pP25_X > it.pP25) {
                        reasoningAllJudgeBean.pP25_X = it.pP25.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.pP30_X > it.pP30) {
                        reasoningAllJudgeBean.pP30_X = it.pP30.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.pP35_X > it.pP35) {
                        reasoningAllJudgeBean.pP35_X = it.pP35.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.pP40_X > it.pP40) {
                        reasoningAllJudgeBean.pP40_X = it.pP40.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.pP45_X > it.pP45) {
                        reasoningAllJudgeBean.pP45_X = it.pP45.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.pP50_X > it.pP50) {
                        reasoningAllJudgeBean.pP50_X = it.pP50.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.pP55_X > it.pP55) {
                        reasoningAllJudgeBean.pP55_X = it.pP55.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.pP60_X > it.pP60) {
                        reasoningAllJudgeBean.pP60_X = it.pP60.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.pP65_X > it.pP65) {
                        reasoningAllJudgeBean.pP65_X = it.pP65.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.pP70_X > it.pP70) {
                        reasoningAllJudgeBean.pP70_X = it.pP70.toOCOORevJudgeMin()
                    }



                    if (reasoningAllJudgeBean.ppP5_X > it.ppP5) {
                        reasoningAllJudgeBean.ppP5_X = it.ppP5.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.ppP10_X > it.ppP10) {
                        reasoningAllJudgeBean.ppP10_X = it.ppP10.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.ppP20_X > it.ppP20) {
                        reasoningAllJudgeBean.ppP20_X = it.ppP20.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.ppP30_X > it.ppP30) {
                        reasoningAllJudgeBean.ppP30_X = it.ppP30.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.ppP40_X > it.ppP40) {
                        reasoningAllJudgeBean.ppP40_X = it.ppP40.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.ppP50_X > it.ppP50) {
                        reasoningAllJudgeBean.ppP50_X = it.ppP50.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.ppP60_X > it.ppP60) {
                        reasoningAllJudgeBean.ppP60_X = it.ppP60.toOCOORevJudgeMin()
                    }
                    if (reasoningAllJudgeBean.ppP70_X > it.ppP70) {
                        reasoningAllJudgeBean.ppP70_X = it.ppP70.toOCOORevJudgeMin()
                    }
                }
            }
        } else {

            list.forEach {

                if (it is ReverseKJsonBean) {
                    reasoningAllJudgeBean.oC3_D = it.oC3.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oC5_D  = it.oC5.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oC10_D  = it.oC10.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oC15_D  = it.oC15.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oC20_D  = it.oC20.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oC25_D  = it.oC25.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oC30_D  = it.oC30.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oC35_D  = it.oC35.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oC40_D  = it.oC40.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oC45_D  = it.oC45.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oC50_D  = it.oC50.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oC55_D  = it.oC55.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oC60_D  = it.oC60.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oC65_D  = it.oC65.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oC70_D  = it.oC70.toOCOORevJudgeMax()


                    reasoningAllJudgeBean.oC3_X  = it.oC3.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oC5_X  = it.oC5.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oC10_X  = it.oC10.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oC15_X  = it.oC15.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oC20_X  = it.oC20.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oC25_X  = it.oC25.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oC30_X  = it.oC30.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oC35_X  = it.oC35.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oC40_X  = it.oC40.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oC45_X  = it.oC45.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oC50_X  = it.oC50.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oC55_X  = it.oC55.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oC60_X  = it.oC60.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oC65_X  = it.oC65.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oC70_X  = it.oC70.toOCOORevJudgeMin()

                    reasoningAllJudgeBean.oO3_D  = it.oO3.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oO5_D  = it.oO5.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oO10_D  = it.oO10.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oO15_D  = it.oO15.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oO20_D  = it.oO20.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oO25_D  = it.oO25.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oO30_D  = it.oO30.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oO35_D  = it.oO35.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oO40_D  = it.oO40.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oO45_D  = it.oO45.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oO50_D  = it.oO50.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oO55_D  = it.oO55.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oO60_D  = it.oO60.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oO65_D  = it.oO65.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.oO70_D  = it.oO70.toOCOORevJudgeMax()


                    reasoningAllJudgeBean.oO3_X  = it.oO3.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oO5_X  = it.oO5.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oO10_X  = it.oO10.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oO15_X  = it.oO15.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oO20_X  = it.oO20.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oO25_X  = it.oO25.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oO30_X  = it.oO30.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oO35_X  = it.oO35.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oO40_X  = it.oO40.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oO45_X  = it.oO45.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oO50_X  = it.oO50.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oO55_X  = it.oO55.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oO60_X  = it.oO60.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oO65_X  = it.oO65.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.oO70_X  = it.oO70.toOCOORevJudgeMin()


                    reasoningAllJudgeBean.pP5_D  = it.pP5.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.pP10_D  = it.pP10.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.pP15_D  = it.pP15.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.pP20_D  = it.pP20.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.pP25_D  = it.pP25.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.pP30_D  = it.pP30.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.pP35_D  = it.pP35.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.pP40_D  = it.pP40.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.pP45_D  = it.pP45.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.pP50_D  = it.pP50.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.pP55_D  = it.pP55.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.pP60_D  = it.pP60.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.pP65_D  = it.pP65.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.pP70_D  = it.pP70.toOCOORevJudgeMax()


                    reasoningAllJudgeBean.pP5_X  = it.pP5.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.pP10_X  = it.pP10.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.pP15_X  = it.pP15.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.pP20_X  = it.pP20.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.pP25_X  = it.pP25.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.pP30_X  = it.pP30.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.pP35_X  = it.pP35.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.pP40_X  = it.pP40.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.pP45_X  = it.pP45.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.pP50_X  = it.pP50.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.pP55_X  = it.pP55.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.pP60_X  = it.pP60.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.pP65_X  = it.pP65.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.pP70_X  = it.pP70.toOCOORevJudgeMin()


                    reasoningAllJudgeBean.ppP5_D  = it.ppP5.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.ppP10_D  = it.ppP10.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.ppP20_D  = it.ppP20.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.ppP30_D  = it.ppP30.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.ppP40_D  = it.ppP40.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.ppP50_D  = it.ppP50.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.ppP60_D  = it.ppP60.toOCOORevJudgeMax()
                    reasoningAllJudgeBean.ppP70_D  = it.ppP70.toOCOORevJudgeMax()


                    reasoningAllJudgeBean.ppP5_X  = it.ppP5.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.ppP10_X  = it.ppP10.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.ppP20_X  = it.ppP20.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.ppP30_X  = it.ppP30.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.ppP40_X  = it.ppP40.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.ppP50_X  = it.ppP50.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.ppP60_X  = it.ppP60.toOCOORevJudgeMin()
                    reasoningAllJudgeBean.ppP70_X  = it.ppP70.toOCOORevJudgeMin()
                }
            }
        }
        reasoningAllJudgeBean.count = list.size
        return reasoningAllJudgeBean
    }

    private fun getOCOOPercent(
        OValue: Float,
        origin: Float
    ) = ((OValue - origin) / origin * 100).toKeep2()

    fun getRevKJOCOOBean(
        index:Int,
        mCHDDList: ArrayList<CodeHDDBean>,
        code: String,
        targetBean: CodeHDDBean?,
        requestBean: CodeHDDBean?
    ): ReverseKJsonBean {
        val revKJOCOOBean = ReverseKJsonBean()
        val originOC = mCHDDList.getPPValueByAsc(index,0,2)
        if (null != targetBean && null != requestBean) {
            revKJOCOOBean.code = code.toInt()
            revKJOCOOBean.n = targetBean.name
            revKJOCOOBean.date = targetBean.date.toInt()
            revKJOCOOBean.d_D = requestBean.date.toInt()
        }

        revKJOCOOBean.oC3 = getOCOOPercent(mCHDDList.getMaxValueInRangeByAsc(index,0,3), originOC)
        revKJOCOOBean.oC5 = getOCOOPercent(mCHDDList.getMaxValueInRangeByAsc(index,4,5), originOC)
        revKJOCOOBean.oC10 = getOCOOPercent(mCHDDList.getMaxValueInRangeByAsc(index,6,10), originOC)
        revKJOCOOBean.oC15 = getOCOOPercent(mCHDDList.getMaxValueInRangeByAsc(index,11,15), originOC)
        revKJOCOOBean.oC20 = getOCOOPercent(mCHDDList.getMaxValueInRangeByAsc(index,16,20), originOC)
        revKJOCOOBean.oC25 = getOCOOPercent(mCHDDList.getMaxValueInRangeByAsc(index,21,25), originOC)
        revKJOCOOBean.oC30 = getOCOOPercent(mCHDDList.getMaxValueInRangeByAsc(index,26,30), originOC)
        revKJOCOOBean.oC35 = getOCOOPercent(mCHDDList.getMaxValueInRangeByAsc(index,31,35), originOC)
        revKJOCOOBean.oC40 = getOCOOPercent(mCHDDList.getMaxValueInRangeByAsc(index,36,40), originOC)
        revKJOCOOBean.oC45 = getOCOOPercent(mCHDDList.getMaxValueInRangeByAsc(index,41,45), originOC)
        revKJOCOOBean.oC50 = getOCOOPercent(mCHDDList.getMaxValueInRangeByAsc(index,46,50), originOC)
        revKJOCOOBean.oC55 = getOCOOPercent(mCHDDList.getMaxValueInRangeByAsc(index,51,55), originOC)
        revKJOCOOBean.oC60 = getOCOOPercent(mCHDDList.getMaxValueInRangeByAsc(index,56,60), originOC)
        revKJOCOOBean.oC65 = getOCOOPercent(mCHDDList.getMaxValueInRangeByAsc(index,61,65), originOC)
        revKJOCOOBean.oC70 = getOCOOPercent(mCHDDList.getMaxValueInRangeByAsc(index,66,70), originOC)


        revKJOCOOBean.oO3 = getOCOOPercent(mCHDDList.getMinValueInRangeByAsc(index,0,3), originOC)
        revKJOCOOBean.oO5 = getOCOOPercent(mCHDDList.getMinValueInRangeByAsc(index,4,5), originOC)
        revKJOCOOBean.oO10 = getOCOOPercent(mCHDDList.getMinValueInRangeByAsc(index,6,10), originOC)
        revKJOCOOBean.oO15 = getOCOOPercent(mCHDDList.getMinValueInRangeByAsc(index,11,15), originOC)
        revKJOCOOBean.oO20 = getOCOOPercent(mCHDDList.getMinValueInRangeByAsc(index,16,20), originOC)
        revKJOCOOBean.oO25 = getOCOOPercent(mCHDDList.getMinValueInRangeByAsc(index,21,25), originOC)
        revKJOCOOBean.oO30 = getOCOOPercent(mCHDDList.getMinValueInRangeByAsc(index,26,30), originOC)
        revKJOCOOBean.oO35 = getOCOOPercent(mCHDDList.getMinValueInRangeByAsc(index,31,35), originOC)
        revKJOCOOBean.oO40 = getOCOOPercent(mCHDDList.getMinValueInRangeByAsc(index,36,40), originOC)
        revKJOCOOBean.oO45 = getOCOOPercent(mCHDDList.getMinValueInRangeByAsc(index,41,45), originOC)
        revKJOCOOBean.oO50 = getOCOOPercent(mCHDDList.getMinValueInRangeByAsc(index,46,50), originOC)
        revKJOCOOBean.oO55 = getOCOOPercent(mCHDDList.getMinValueInRangeByAsc(index,51,55), originOC)
        revKJOCOOBean.oO60 = getOCOOPercent(mCHDDList.getMinValueInRangeByAsc(index,56,60), originOC)
        revKJOCOOBean.oO65 = getOCOOPercent(mCHDDList.getMinValueInRangeByAsc(index,61,65), originOC)
        revKJOCOOBean.oO70 = getOCOOPercent(mCHDDList.getMinValueInRangeByAsc(index,66,70), originOC)


        revKJOCOOBean.pP5 = getOCOOPercent(mCHDDList.getPPValueByAsc(index,4,5), originOC)
        revKJOCOOBean.pP10 = getOCOOPercent(mCHDDList.getPPValueByAsc(index,6,10), originOC)
        revKJOCOOBean.pP15 = getOCOOPercent(mCHDDList.getPPValueByAsc(index,11,15), originOC)
        revKJOCOOBean.pP20 = getOCOOPercent(mCHDDList.getPPValueByAsc(index,16,20), originOC)
        revKJOCOOBean.pP25 = getOCOOPercent(mCHDDList.getPPValueByAsc(index,21,25), originOC)
        revKJOCOOBean.pP30 = getOCOOPercent(mCHDDList.getPPValueByAsc(index,26,30), originOC)
        revKJOCOOBean.pP35 = getOCOOPercent(mCHDDList.getPPValueByAsc(index,31,35), originOC)
        revKJOCOOBean.pP40 = getOCOOPercent(mCHDDList.getPPValueByAsc(index,36,40), originOC)
        revKJOCOOBean.pP45 = getOCOOPercent(mCHDDList.getPPValueByAsc(index,41,45), originOC)
        revKJOCOOBean.pP50 = getOCOOPercent(mCHDDList.getPPValueByAsc(index,46,50), originOC)
        revKJOCOOBean.pP55 = getOCOOPercent(mCHDDList.getPPValueByAsc(index,51,55), originOC)
        revKJOCOOBean.pP60 = getOCOOPercent(mCHDDList.getPPValueByAsc(index,56,60), originOC)
        revKJOCOOBean.pP65 = getOCOOPercent(mCHDDList.getPPValueByAsc(index,61,65), originOC)
        revKJOCOOBean.pP70 = getOCOOPercent(mCHDDList.getPPValueByAsc(index,66,70), originOC)

        revKJOCOOBean.ppP5 = getOCOOPercent(mCHDDList.getPPPValueByAsc(index,5), originOC)
        revKJOCOOBean.ppP10 = getOCOOPercent(mCHDDList.getPPPValueByAsc(index,10), originOC)
        revKJOCOOBean.ppP20 = getOCOOPercent(mCHDDList.getPPPValueByAsc(index,20), originOC)
        revKJOCOOBean.ppP30 = getOCOOPercent(mCHDDList.getPPPValueByAsc(index,30), originOC)
        revKJOCOOBean.ppP40 = getOCOOPercent(mCHDDList.getPPPValueByAsc(index,40), originOC)
        revKJOCOOBean.ppP50 = getOCOOPercent(mCHDDList.getPPPValueByAsc(index,50), originOC)
        revKJOCOOBean.ppP60 = getOCOOPercent(mCHDDList.getPPPValueByAsc(index,60), originOC)
        revKJOCOOBean.ppP70 = getOCOOPercent(mCHDDList.getPPPValueByAsc(index,70), originOC)
        return revKJOCOOBean
    }

    fun getInsertRevKJOCOOBean(
        index:Int,
        mCHDDList: ArrayList<CodeHDDBean>
    ): ReverseKJsonBean {
        val revKJOCOOBean = ReverseKJsonBean()
        val originOC = mCHDDList.getPPValueByDesc(index,0,2)

        revKJOCOOBean.oC3 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,0,3), originOC)
        revKJOCOOBean.oC5 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,4,5), originOC)
        revKJOCOOBean.oC10 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,6,10), originOC)
        revKJOCOOBean.oC15 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,11,15), originOC)
        revKJOCOOBean.oC20 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,16,20), originOC)
        revKJOCOOBean.oC25 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,21,25), originOC)
        revKJOCOOBean.oC30 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,26,30), originOC)
        revKJOCOOBean.oC35 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,31,35), originOC)
        revKJOCOOBean.oC40 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,36,40), originOC)
        revKJOCOOBean.oC45 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,41,45), originOC)
        revKJOCOOBean.oC50 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,46,50), originOC)
        revKJOCOOBean.oC55 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,51,55), originOC)
        revKJOCOOBean.oC60 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,56,60), originOC)
        revKJOCOOBean.oC65 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,61,65), originOC)
        revKJOCOOBean.oC70 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,66,70), originOC)

        revKJOCOOBean.oO3 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,0,3), originOC)
        revKJOCOOBean.oO5 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,4,5), originOC)
        revKJOCOOBean.oO10 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,6,10), originOC)
        revKJOCOOBean.oO15 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,11,15), originOC)
        revKJOCOOBean.oO20 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,16,20), originOC)
        revKJOCOOBean.oO25 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,21,25), originOC)
        revKJOCOOBean.oO30 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,26,30), originOC)
        revKJOCOOBean.oO35 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,31,35), originOC)
        revKJOCOOBean.oO40 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,36,40), originOC)
        revKJOCOOBean.oO45 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,41,45), originOC)
        revKJOCOOBean.oO50 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,46,50), originOC)
        revKJOCOOBean.oO55 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,51,55), originOC)
        revKJOCOOBean.oO60 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,56,60), originOC)
        revKJOCOOBean.oO65 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,61,65), originOC)
        revKJOCOOBean.oO70 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,66,70), originOC)


        revKJOCOOBean.pP5 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,4,5), originOC)
        revKJOCOOBean.pP10 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,6,10), originOC)
        revKJOCOOBean.pP15 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,11,15), originOC)
        revKJOCOOBean.pP20 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,16,20), originOC)
        revKJOCOOBean.pP25 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,21,25), originOC)
        revKJOCOOBean.pP30 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,26,30), originOC)
        revKJOCOOBean.pP35 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,31,35), originOC)
        revKJOCOOBean.pP40 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,36,40), originOC)
        revKJOCOOBean.pP45 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,41,45), originOC)
        revKJOCOOBean.pP50 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,46,50), originOC)
        revKJOCOOBean.pP55 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,51,55), originOC)
        revKJOCOOBean.pP60 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,56,60), originOC)
        revKJOCOOBean.pP65 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,61,65), originOC)
        revKJOCOOBean.pP70 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,66,70), originOC)


        revKJOCOOBean.ppP5 = getOCOOPercent(mCHDDList.getPPPValueByDesc(index,5), originOC)
        revKJOCOOBean.ppP10 = getOCOOPercent(mCHDDList.getPPPValueByDesc(index,10), originOC)
        revKJOCOOBean.ppP20 = getOCOOPercent(mCHDDList.getPPPValueByDesc(index,20), originOC)
        revKJOCOOBean.ppP30 = getOCOOPercent(mCHDDList.getPPPValueByDesc(index,30), originOC)
        revKJOCOOBean.ppP40 = getOCOOPercent(mCHDDList.getPPPValueByDesc(index,40), originOC)
        revKJOCOOBean.ppP50 = getOCOOPercent(mCHDDList.getPPPValueByDesc(index,50), originOC)
        revKJOCOOBean.ppP60 = getOCOOPercent(mCHDDList.getPPPValueByDesc(index,60), originOC)
        revKJOCOOBean.ppP70 = getOCOOPercent(mCHDDList.getPPPValueByDesc(index,70), originOC)
        return revKJOCOOBean
    }

    fun getOCOOReasoningRevBean(
        index:Int,
        mCHDDList: ArrayList<CodeHDDBean>
    ): ReasoningRevBean {
        val revKJOCOOBean = ReasoningRevBean()
        val originOC = mCHDDList.getPPValueByDesc(index,0,2)

        revKJOCOOBean.oC3 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,0,3), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oC5 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,4,5), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oC10 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,6,10), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oC15 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,11,15), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oC20 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,16,20), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oC25 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,21,25), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oC30 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,26,30), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oC35 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,31,35), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oC40 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,36,40), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oC45 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,41,45), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oC50 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,46,50), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oC55 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,51,55), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oC60 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,56,60), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oC65 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,61,65), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oC70 = getOCOOPercent(mCHDDList.getMaxValueInRangeByDesc(index,66,70), originOC).toOCOORevJudgeMin()


        revKJOCOOBean.oO3 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,0,3), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oO5 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,4,5), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oO10 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,6,10), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oO15 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,11,15), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oO20 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,16,20), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oO25 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,21,25), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oO30 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,26,30), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oO35 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,31,35), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oO40 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,36,40), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oO45 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,41,45), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oO50 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,46,50), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oO55 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,51,55), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oO60 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,56,60), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oO65 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,61,65), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.oO70 = getOCOOPercent(mCHDDList.getMinValueInRangeByDesc(index,66,70), originOC).toOCOORevJudgeMin()

        revKJOCOOBean.pP5 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,4,5), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.pP10 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,6,10), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.pP15 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,11,15), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.pP20 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,16,20), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.pP25 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,21,25), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.pP30 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,26,30), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.pP35 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,31,35), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.pP40 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,36,40), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.pP45 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,41,45), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.pP50 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,46,50), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.pP55 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,51,55), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.pP60 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,56,60), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.pP65 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,61,65), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.pP70 = getOCOOPercent(mCHDDList.getPPValueByDesc(index,66,70), originOC).toOCOORevJudgeMin()


        revKJOCOOBean.ppP5 = getOCOOPercent(mCHDDList.getPPPValueByDesc(index,5), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.ppP10 = getOCOOPercent(mCHDDList.getPPPValueByDesc(index,10), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.ppP20 = getOCOOPercent(mCHDDList.getPPPValueByDesc(index,20), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.ppP30 = getOCOOPercent(mCHDDList.getPPPValueByDesc(index,30), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.ppP40 = getOCOOPercent(mCHDDList.getPPPValueByDesc(index,40), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.ppP50 = getOCOOPercent(mCHDDList.getPPPValueByDesc(index,50), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.ppP60 = getOCOOPercent(mCHDDList.getPPPValueByDesc(index,60), originOC).toOCOORevJudgeMin()
        revKJOCOOBean.ppP70 = getOCOOPercent(mCHDDList.getPPPValueByDesc(index,70), originOC).toOCOORevJudgeMin()
        return revKJOCOOBean
    }

}
