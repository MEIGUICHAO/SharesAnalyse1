package com.mgc.sharesanalyse.utils

import com.mgc.sharesanalyse.base.*
import com.mgc.sharesanalyse.entity.*

object DataSettingUtils {

    fun setP50FilterMaxMinValue(
        indexType: String,
        tbname: String,
        dFilter: P50FilterBBKJRangeBean.DFilter,
        pair: Pair<String, String>
    ): P50FilterBBKJRangeBean.DFilter {
        val spliteArray  =tbname.split("_R_")[0].split("_")
        val day = spliteArray[spliteArray.size-1].toInt()
//        LogUtil.d("day:$day,indexType:$indexType,min:${pair.first},max:${pair.second}")
        when (indexType) {
            "OM_M"->{
                when (day) {
                    3->{
                        LogUtil.d("day:$day,indexType:$indexType,min:${pair.first},max:${pair.second}")
                        dFilter.d03Bean.minBean.oM_M = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oM_M = pair.second.toFloat()
                        LogUtil.d("day:$day,indexType:$indexType,!!!min:${ dFilter.d03Bean.minBean.oM_M },max:${dFilter.d03Bean.maxBean.oM_M }")
                    }
                    5->{
                        dFilter.d05Bean.minBean.oM_M = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oM_M = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.oM_M = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oM_M = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.oM_M = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oM_M = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.oM_M = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oM_M = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.oM_M = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oM_M = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.oM_M = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oM_M = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.oM_M = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oM_M = pair.second.toFloat()
                    }
                }
            }
            "OM_C"->{
                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.oM_C = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oM_C = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.oM_C = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oM_C = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.oM_C = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oM_C = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.oM_C = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oM_C = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.oM_C = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oM_C = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.oM_C = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oM_C = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.oM_C = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oM_C = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.oM_C = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oM_C = pair.second.toFloat()
                    }
                }
            }
            "OM_P"->{
                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.oM_P = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oM_P = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.oM_P = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oM_P = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.oM_P = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oM_P = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.oM_P = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oM_P = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.oM_P = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oM_P = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.oM_P = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oM_P = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.oM_P = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oM_P = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.oM_P = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oM_P = pair.second.toFloat()
                    }
                }
            }
            "OM_L"->{

                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.oM_L = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oM_L = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.oM_L = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oM_L = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.oM_L = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oM_L = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.oM_L = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oM_L = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.oM_L = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oM_L = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.oM_L = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oM_L = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.oM_L = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oM_L = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.oM_L = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oM_L = pair.second.toFloat()
                    }
                }
            }
            "OC_M"->{

                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.oC_M = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oC_M = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.oC_M = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oC_M = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.oC_M = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oC_M = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.oC_M = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oC_M = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.oC_M = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oC_M = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.oC_M = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oC_M = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.oC_M = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oC_M = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.oC_M = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oC_M = pair.second.toFloat()
                    }
                }
            }
            "OC_C"->{
                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.oC_C = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oC_C = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.oC_C = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oC_C = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.oC_C = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oC_C = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.oC_C = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oC_C = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.oC_C = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oC_C = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.oC_C = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oC_C = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.oC_C = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oC_C = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.oC_C = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oC_C = pair.second.toFloat()
                    }
                }}
            "OC_P"->{

                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.oC_P = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oC_P = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.oC_P = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oC_P = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.oC_P = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oC_P = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.oC_P = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oC_P = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.oC_P = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oC_P = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.oC_P = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oC_P = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.oC_P = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oC_P = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.oC_P = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oC_P = pair.second.toFloat()
                    }
                }
            }
            "OC_L"->{

                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.oC_L = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oC_L = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.oC_L = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oC_L = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.oC_L = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oC_L = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.oC_L = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oC_L = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.oC_L = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oC_L = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.oC_L = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oC_L = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.oC_L = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oC_L = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.oC_L = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oC_L = pair.second.toFloat()
                    }
                }
            }
            "OO_M"->{
                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.oO_M = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oO_M = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.oO_M = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oO_M = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.oO_M = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oO_M = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.oO_M = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oO_M = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.oO_M = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oO_M = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.oO_M = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oO_M = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.oO_M = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oO_M = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.oO_M = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oO_M = pair.second.toFloat()
                    }
                }}
            "OO_C"->{

                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.oO_C = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oO_C = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.oO_C = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oO_C = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.oO_C = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oO_C = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.oO_C = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oO_C = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.oO_C = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oO_C = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.oO_C = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oO_C = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.oO_C = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oO_C = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.oO_C = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oO_C = pair.second.toFloat()
                    }
                }
            }
            "OO_P"->{
                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.oO_P = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oO_P = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.oO_P = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oO_P = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.oO_P = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oO_P = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.oO_P = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oO_P = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.oO_P = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oO_P = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.oO_P = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oO_P = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.oO_P = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oO_P = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.oO_P = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oO_P = pair.second.toFloat()
                    }
                }}
            "OO_L"->{

                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.oO_L = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oO_L = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.oO_L = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oO_L = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.oO_L = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oO_L = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.oO_L = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oO_L = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.oO_L = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oO_L = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.oO_L = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oO_L = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.oO_L = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oO_L = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.oO_L = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oO_L = pair.second.toFloat()
                    }
                }
            }
            "OL_M"->{

                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.oL_M = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oL_M = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.oL_M = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oL_M = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.oL_M = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oL_M = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.oL_M = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oL_M = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.oL_M = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oL_M = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.oL_M = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oL_M = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.oL_M = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oL_M = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.oL_M = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oL_M = pair.second.toFloat()
                    }
                }
            }
            "OL_C"->{

                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.oL_C = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oL_C = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.oL_C = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oL_C = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.oL_C = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oL_C = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.oL_C = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oL_C = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.oL_C = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oL_C = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.oL_C = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oL_C = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.oL_C = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oL_C = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.oL_C = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oL_C = pair.second.toFloat()
                    }
                }
            }
            "OL_P"->{

                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.oL_P = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oL_P = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.oL_P = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oL_P = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.oL_P = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oL_P = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.oL_P = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oL_P = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.oL_P = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oL_P = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.oL_P = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oL_P = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.oL_P = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oL_P = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.oL_P = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oL_P = pair.second.toFloat()
                    }
                }
            }
            "OL_L"->{

                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.oL_L = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oL_L = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.oL_L = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oL_L = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.oL_L = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oL_L = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.oL_L = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oL_L = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.oL_L = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oL_L = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.oL_L = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oL_L = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.oL_L = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oL_L = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.oL_L = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oL_L = pair.second.toFloat()
                    }
                }
            }
            "OM_OC"->{

                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.oM_OC = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oM_OC = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.oM_OC = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oM_OC = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.oM_OC = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oM_OC = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.oM_OC = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oM_OC = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.oM_OC = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oM_OC = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.oM_OC = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oM_OC = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.oM_OC = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oM_OC = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.oM_OC = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oM_OC = pair.second.toFloat()
                    }
                }
            }
            "OM_OP"->{

                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.oM_OP = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oM_OP = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.oM_OP = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oM_OP = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.oM_OP = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oM_OP = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.oM_OP = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oM_OP = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.oM_OP = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oM_OP = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.oM_OP = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oM_OP = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.oM_OP = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oM_OP = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.oM_OP = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oM_OP = pair.second.toFloat()
                    }
                }
            }
            "OM_OL"->{

                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.oM_OL = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oM_OL = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.oM_OL = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oM_OL = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.oM_OL = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oM_OL = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.oM_OL = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oM_OL = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.oM_OL = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oM_OL = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.oM_OL = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oM_OL = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.oM_OL = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oM_OL = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.oM_OL = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oM_OL = pair.second.toFloat()
                    }
                }
            }
            "OC_OP"->{

                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.oC_OP = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oC_OP = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.oC_OP = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oC_OP = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.oC_OP = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oC_OP = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.oC_OP = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oC_OP = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.oC_OP = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oC_OP = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.oC_OP = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oC_OP = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.oC_OP = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oC_OP = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.oC_OP = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oC_OP = pair.second.toFloat()
                    }
                }
            }
            "OC_OL"->{

                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.oC_OL = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oC_OL = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.oC_OL = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oC_OL = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.oC_OL = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oC_OL = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.oC_OL = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oC_OL = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.oC_OL = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oC_OL = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.oC_OL = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oC_OL = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.oC_OL = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oC_OL = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.oC_OL = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oC_OL = pair.second.toFloat()
                    }
                }
            }
            "OP_OL"->{
                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.oP_OL = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.oP_OL = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.oP_OL = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.oP_OL = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.oP_OL = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.oP_OL = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.oP_OL = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.oP_OL = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.oP_OL = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.oP_OL = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.oP_OL = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.oP_OL = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.oP_OL = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.oP_OL = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.oP_OL = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.oP_OL = pair.second.toFloat()
                    }
                }
            }
            "M_C"->{

                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.m_C = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.m_C = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.m_C = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.m_C = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.m_C = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.m_C = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.m_C = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.m_C = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.m_C = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.m_C = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.m_C = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.m_C = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.m_C = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.m_C = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.m_C = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.m_C = pair.second.toFloat()
                    }
                }
            }
            "M_P"->{

                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.m_P = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.m_P = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.m_P = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.m_P = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.m_P = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.m_P = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.m_P = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.m_P = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.m_P = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.m_P = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.m_P = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.m_P = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.m_P = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.m_P = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.m_P = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.m_P = pair.second.toFloat()
                    }
                }
            }
            "M_L"->{

                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.m_L = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.m_L = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.m_L = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.m_L = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.m_L = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.m_L = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.m_L = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.m_L = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.m_L = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.m_L = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.m_L = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.m_L = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.m_L = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.m_L = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.m_L = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.m_L = pair.second.toFloat()
                    }
                }
            }
            "C_P"->{

                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.c_P = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.c_P = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.c_P = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.c_P = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.c_P = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.c_P = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.c_P = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.c_P = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.c_P = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.c_P = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.c_P = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.c_P = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.c_P = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.c_P = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.c_P = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.c_P = pair.second.toFloat()
                    }
                }
            }
            "C_L"->{

                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.c_L = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.c_L = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.c_L = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.c_L = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.c_L = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.c_L = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.c_L = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.c_L = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.c_L = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.c_L = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.c_L = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.c_L = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.c_L = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.c_L = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.c_L = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.c_L = pair.second.toFloat()
                    }
                }
            }
            "P_L"->{
                when (day) {
                    3->{
                        dFilter.d03Bean.minBean.p_L = pair.first.toFloat()
                        dFilter.d03Bean.maxBean.p_L = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05Bean.minBean.p_L = pair.first.toFloat()
                        dFilter.d05Bean.maxBean.p_L = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10Bean.minBean.p_L = pair.first.toFloat()
                        dFilter.d10Bean.maxBean.p_L = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15Bean.minBean.p_L = pair.first.toFloat()
                        dFilter.d15Bean.maxBean.p_L = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20Bean.minBean.p_L = pair.first.toFloat()
                        dFilter.d20Bean.maxBean.p_L = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25Bean.minBean.p_L = pair.first.toFloat()
                        dFilter.d25Bean.maxBean.p_L = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30Bean.minBean.p_L = pair.first.toFloat()
                        dFilter.d30Bean.maxBean.p_L = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36Bean.minBean.p_L = pair.first.toFloat()
                        dFilter.d36Bean.maxBean.p_L = pair.second.toFloat()
                    }
                }
            }
            "S_A_TR"->{
                when (day) {
                    3->{
                        dFilter.d03TRBean.minBean.s_A_TR = pair.first.toFloat()
                        dFilter.d03TRBean.maxBean.s_A_TR = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05TRBean.minBean.s_A_TR = pair.first.toFloat()
                        dFilter.d05TRBean.maxBean.s_A_TR = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10TRBean.minBean.s_A_TR = pair.first.toFloat()
                        dFilter.d10TRBean.maxBean.s_A_TR = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15TRBean.minBean.s_A_TR = pair.first.toFloat()
                        dFilter.d15TRBean.maxBean.s_A_TR = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20TRBean.minBean.s_A_TR = pair.first.toFloat()
                        dFilter.d20TRBean.maxBean.s_A_TR = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25TRBean.minBean.s_A_TR = pair.first.toFloat()
                        dFilter.d25TRBean.maxBean.s_A_TR = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30TRBean.minBean.s_A_TR = pair.first.toFloat()
                        dFilter.d30TRBean.maxBean.s_A_TR = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36TRBean.minBean.s_A_TR = pair.first.toFloat()
                        dFilter.d36TRBean.maxBean.s_A_TR = pair.second.toFloat()
                    }
                }
            }
            "S_R_TR"->{
                when (day) {
                    3->{
                        dFilter.d03TRBean.minBean.s_R_TR = pair.first.toFloat()
                        dFilter.d03TRBean.maxBean.s_R_TR = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05TRBean.minBean.s_R_TR = pair.first.toFloat()
                        dFilter.d05TRBean.maxBean.s_R_TR = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10TRBean.minBean.s_R_TR = pair.first.toFloat()
                        dFilter.d10TRBean.maxBean.s_R_TR = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15TRBean.minBean.s_R_TR = pair.first.toFloat()
                        dFilter.d15TRBean.maxBean.s_R_TR = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20TRBean.minBean.s_R_TR = pair.first.toFloat()
                        dFilter.d20TRBean.maxBean.s_R_TR = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25TRBean.minBean.s_R_TR = pair.first.toFloat()
                        dFilter.d25TRBean.maxBean.s_R_TR = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30TRBean.minBean.s_R_TR = pair.first.toFloat()
                        dFilter.d30TRBean.maxBean.s_R_TR = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36TRBean.minBean.s_R_TR = pair.first.toFloat()
                        dFilter.d36TRBean.maxBean.s_R_TR = pair.second.toFloat()
                    }
                }
            }
            "S_B_TR"->{

                when (day) {
                    3->{
                        dFilter.d03TRBean.minBean.s_B_TR = pair.first.toFloat()
                        dFilter.d03TRBean.maxBean.s_B_TR = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05TRBean.minBean.s_B_TR = pair.first.toFloat()
                        dFilter.d05TRBean.maxBean.s_B_TR = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10TRBean.minBean.s_B_TR = pair.first.toFloat()
                        dFilter.d10TRBean.maxBean.s_B_TR = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15TRBean.minBean.s_B_TR = pair.first.toFloat()
                        dFilter.d15TRBean.maxBean.s_B_TR = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20TRBean.minBean.s_B_TR = pair.first.toFloat()
                        dFilter.d20TRBean.maxBean.s_B_TR = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25TRBean.minBean.s_B_TR = pair.first.toFloat()
                        dFilter.d25TRBean.maxBean.s_B_TR = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30TRBean.minBean.s_B_TR = pair.first.toFloat()
                        dFilter.d30TRBean.maxBean.s_B_TR = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36TRBean.minBean.s_B_TR = pair.first.toFloat()
                        dFilter.d36TRBean.maxBean.s_B_TR = pair.second.toFloat()
                    }
                }
            }
            "S_C_TR"->{

                when (day) {
                    3->{
                        dFilter.d03TRBean.minBean.s_C_TR = pair.first.toFloat()
                        dFilter.d03TRBean.maxBean.s_C_TR = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05TRBean.minBean.s_C_TR = pair.first.toFloat()
                        dFilter.d05TRBean.maxBean.s_C_TR = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10TRBean.minBean.s_C_TR = pair.first.toFloat()
                        dFilter.d10TRBean.maxBean.s_C_TR = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15TRBean.minBean.s_C_TR = pair.first.toFloat()
                        dFilter.d15TRBean.maxBean.s_C_TR = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20TRBean.minBean.s_C_TR = pair.first.toFloat()
                        dFilter.d20TRBean.maxBean.s_C_TR = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25TRBean.minBean.s_C_TR = pair.first.toFloat()
                        dFilter.d25TRBean.maxBean.s_C_TR = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30TRBean.minBean.s_C_TR = pair.first.toFloat()
                        dFilter.d30TRBean.maxBean.s_C_TR = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36TRBean.minBean.s_C_TR = pair.first.toFloat()
                        dFilter.d36TRBean.maxBean.s_C_TR = pair.second.toFloat()
                    }
                }
            }
            "K_A_TR"->{

                when (day) {
                    3->{
                        dFilter.d03TRBean.minBean.k_A_TR = pair.first.toFloat()
                        dFilter.d03TRBean.maxBean.k_A_TR = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05TRBean.minBean.k_A_TR = pair.first.toFloat()
                        dFilter.d05TRBean.maxBean.k_A_TR = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10TRBean.minBean.k_A_TR = pair.first.toFloat()
                        dFilter.d10TRBean.maxBean.k_A_TR = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15TRBean.minBean.k_A_TR = pair.first.toFloat()
                        dFilter.d15TRBean.maxBean.k_A_TR = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20TRBean.minBean.k_A_TR = pair.first.toFloat()
                        dFilter.d20TRBean.maxBean.k_A_TR = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25TRBean.minBean.k_A_TR = pair.first.toFloat()
                        dFilter.d25TRBean.maxBean.k_A_TR = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30TRBean.minBean.k_A_TR = pair.first.toFloat()
                        dFilter.d30TRBean.maxBean.k_A_TR = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36TRBean.minBean.k_A_TR = pair.first.toFloat()
                        dFilter.d36TRBean.maxBean.k_A_TR = pair.second.toFloat()
                    }
                }
            }
            "K_R_TR"->{

                when (day) {
                    3->{
                        dFilter.d03TRBean.minBean.k_R_TR = pair.first.toFloat()
                        dFilter.d03TRBean.maxBean.k_R_TR = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05TRBean.minBean.k_R_TR = pair.first.toFloat()
                        dFilter.d05TRBean.maxBean.k_R_TR = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10TRBean.minBean.k_R_TR = pair.first.toFloat()
                        dFilter.d10TRBean.maxBean.k_R_TR = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15TRBean.minBean.k_R_TR = pair.first.toFloat()
                        dFilter.d15TRBean.maxBean.k_R_TR = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20TRBean.minBean.k_R_TR = pair.first.toFloat()
                        dFilter.d20TRBean.maxBean.k_R_TR = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25TRBean.minBean.k_R_TR = pair.first.toFloat()
                        dFilter.d25TRBean.maxBean.k_R_TR = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30TRBean.minBean.k_R_TR = pair.first.toFloat()
                        dFilter.d30TRBean.maxBean.k_R_TR = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36TRBean.minBean.k_R_TR = pair.first.toFloat()
                        dFilter.d36TRBean.maxBean.k_R_TR = pair.second.toFloat()
                    }
                }
            }
            "K_B_TR"->{

                when (day) {
                    3->{
                        dFilter.d03TRBean.minBean.k_B_TR = pair.first.toFloat()
                        dFilter.d03TRBean.maxBean.k_B_TR = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05TRBean.minBean.k_B_TR = pair.first.toFloat()
                        dFilter.d05TRBean.maxBean.k_B_TR = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10TRBean.minBean.k_B_TR = pair.first.toFloat()
                        dFilter.d10TRBean.maxBean.k_B_TR = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15TRBean.minBean.k_B_TR = pair.first.toFloat()
                        dFilter.d15TRBean.maxBean.k_B_TR = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20TRBean.minBean.k_B_TR = pair.first.toFloat()
                        dFilter.d20TRBean.maxBean.k_B_TR = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25TRBean.minBean.k_B_TR = pair.first.toFloat()
                        dFilter.d25TRBean.maxBean.k_B_TR = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30TRBean.minBean.k_B_TR = pair.first.toFloat()
                        dFilter.d30TRBean.maxBean.k_B_TR = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36TRBean.minBean.k_B_TR = pair.first.toFloat()
                        dFilter.d36TRBean.maxBean.k_B_TR = pair.second.toFloat()
                    }
                }
            }
            "K_C_TR"->{

                when (day) {
                    3->{
                        dFilter.d03TRBean.minBean.k_C_TR = pair.first.toFloat()
                        dFilter.d03TRBean.maxBean.k_C_TR = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05TRBean.minBean.k_C_TR = pair.first.toFloat()
                        dFilter.d05TRBean.maxBean.k_C_TR = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10TRBean.minBean.k_C_TR = pair.first.toFloat()
                        dFilter.d10TRBean.maxBean.k_C_TR = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15TRBean.minBean.k_C_TR = pair.first.toFloat()
                        dFilter.d15TRBean.maxBean.k_C_TR = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20TRBean.minBean.k_C_TR = pair.first.toFloat()
                        dFilter.d20TRBean.maxBean.k_C_TR = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25TRBean.minBean.k_C_TR = pair.first.toFloat()
                        dFilter.d25TRBean.maxBean.k_C_TR = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30TRBean.minBean.k_C_TR = pair.first.toFloat()
                        dFilter.d30TRBean.maxBean.k_C_TR = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36TRBean.minBean.k_C_TR = pair.first.toFloat()
                        dFilter.d36TRBean.maxBean.k_C_TR = pair.second.toFloat()
                    }
                }
            }
            "K_SL_A_TR"->{

                when (day) {
                    3->{
                        dFilter.d03TRBean.minBean.k_SL_A_TR = pair.first.toFloat()
                        dFilter.d03TRBean.maxBean.k_SL_A_TR = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05TRBean.minBean.k_SL_A_TR = pair.first.toFloat()
                        dFilter.d05TRBean.maxBean.k_SL_A_TR = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10TRBean.minBean.k_SL_A_TR = pair.first.toFloat()
                        dFilter.d10TRBean.maxBean.k_SL_A_TR = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15TRBean.minBean.k_SL_A_TR = pair.first.toFloat()
                        dFilter.d15TRBean.maxBean.k_SL_A_TR = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20TRBean.minBean.k_SL_A_TR = pair.first.toFloat()
                        dFilter.d20TRBean.maxBean.k_SL_A_TR = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25TRBean.minBean.k_SL_A_TR = pair.first.toFloat()
                        dFilter.d25TRBean.maxBean.k_SL_A_TR = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30TRBean.minBean.k_SL_A_TR = pair.first.toFloat()
                        dFilter.d30TRBean.maxBean.k_SL_A_TR = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36TRBean.minBean.k_SL_A_TR = pair.first.toFloat()
                        dFilter.d36TRBean.maxBean.k_SL_A_TR = pair.second.toFloat()
                    }
                }
            }
            "K_SL_R_TR"->{

                when (day) {
                    3->{
                        dFilter.d03TRBean.minBean.k_SL_R_TR = pair.first.toFloat()
                        dFilter.d03TRBean.maxBean.k_SL_R_TR = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05TRBean.minBean.k_SL_R_TR = pair.first.toFloat()
                        dFilter.d05TRBean.maxBean.k_SL_R_TR = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10TRBean.minBean.k_SL_R_TR = pair.first.toFloat()
                        dFilter.d10TRBean.maxBean.k_SL_R_TR = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15TRBean.minBean.k_SL_R_TR = pair.first.toFloat()
                        dFilter.d15TRBean.maxBean.k_SL_R_TR = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20TRBean.minBean.k_SL_R_TR = pair.first.toFloat()
                        dFilter.d20TRBean.maxBean.k_SL_R_TR = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25TRBean.minBean.k_SL_R_TR = pair.first.toFloat()
                        dFilter.d25TRBean.maxBean.k_SL_R_TR = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30TRBean.minBean.k_SL_R_TR = pair.first.toFloat()
                        dFilter.d30TRBean.maxBean.k_SL_R_TR = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36TRBean.minBean.k_SL_R_TR = pair.first.toFloat()
                        dFilter.d36TRBean.maxBean.k_SL_R_TR = pair.second.toFloat()
                    }
                }
            }
            "K_SL_B_TR"->{

                when (day) {
                    3->{
                        dFilter.d03TRBean.minBean.k_SL_B_TR = pair.first.toFloat()
                        dFilter.d03TRBean.maxBean.k_SL_B_TR = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05TRBean.minBean.k_SL_B_TR = pair.first.toFloat()
                        dFilter.d05TRBean.maxBean.k_SL_B_TR = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10TRBean.minBean.k_SL_B_TR = pair.first.toFloat()
                        dFilter.d10TRBean.maxBean.k_SL_B_TR = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15TRBean.minBean.k_SL_B_TR = pair.first.toFloat()
                        dFilter.d15TRBean.maxBean.k_SL_B_TR = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20TRBean.minBean.k_SL_B_TR = pair.first.toFloat()
                        dFilter.d20TRBean.maxBean.k_SL_B_TR = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25TRBean.minBean.k_SL_B_TR = pair.first.toFloat()
                        dFilter.d25TRBean.maxBean.k_SL_B_TR = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30TRBean.minBean.k_SL_B_TR = pair.first.toFloat()
                        dFilter.d30TRBean.maxBean.k_SL_B_TR = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36TRBean.minBean.k_SL_B_TR = pair.first.toFloat()
                        dFilter.d36TRBean.maxBean.k_SL_B_TR = pair.second.toFloat()
                    }
                }
            }
            "K_SL_C_TR"->{
                when (day) {
                    3->{
                        dFilter.d03TRBean.minBean.k_SL_C_TR = pair.first.toFloat()
                        dFilter.d03TRBean.maxBean.k_SL_C_TR = pair.second.toFloat()
                    }
                    5->{
                        dFilter.d05TRBean.minBean.k_SL_C_TR = pair.first.toFloat()
                        dFilter.d05TRBean.maxBean.k_SL_C_TR = pair.second.toFloat()
                    }
                    10->{
                        dFilter.d10TRBean.minBean.k_SL_C_TR = pair.first.toFloat()
                        dFilter.d10TRBean.maxBean.k_SL_C_TR = pair.second.toFloat()
                    }
                    15->{
                        dFilter.d15TRBean.minBean.k_SL_C_TR = pair.first.toFloat()
                        dFilter.d15TRBean.maxBean.k_SL_C_TR = pair.second.toFloat()
                    }
                    20->{
                        dFilter.d20TRBean.minBean.k_SL_C_TR = pair.first.toFloat()
                        dFilter.d20TRBean.maxBean.k_SL_C_TR = pair.second.toFloat()
                    }
                    25->{
                        dFilter.d25TRBean.minBean.k_SL_C_TR = pair.first.toFloat()
                        dFilter.d25TRBean.maxBean.k_SL_C_TR = pair.second.toFloat()
                    }
                    30->{
                        dFilter.d30TRBean.minBean.k_SL_C_TR = pair.first.toFloat()
                        dFilter.d30TRBean.maxBean.k_SL_C_TR = pair.second.toFloat()
                    }
                    36->{
                        dFilter.d36TRBean.minBean.k_SL_C_TR = pair.first.toFloat()
                        dFilter.d36TRBean.maxBean.k_SL_C_TR = pair.second.toFloat()
                    }
                }}
        }
        return dFilter

    }

    fun setFilterBeanType(
        rangeMin: Int,
        mDFilter: P50FilterBBKJRangeBean.DFilter,
        p50FilterBBKjRangeBean: P50FilterBBKJRangeBean
    ) {
        when (rangeMin) {
            -70->{
                p50FilterBBKjRangeBean.r_N70_N60 = GsonHelper.parse(GsonHelper.toJson(mDFilter),P50FilterBBKJRangeBean.DFilter::class.java)
                LogUtil.d("r_N70_N60-->${GsonHelper.toJson(mDFilter)}")
            }
            -50->{
                p50FilterBBKjRangeBean.r_N50_N40 = GsonHelper.parse(GsonHelper.toJson(mDFilter),P50FilterBBKJRangeBean.DFilter::class.java)
                LogUtil.d("r_N50_N40-->${GsonHelper.toJson(mDFilter)}")
            }
            -40->{
                p50FilterBBKjRangeBean.r_N40_N30 = GsonHelper.parse(GsonHelper.toJson(mDFilter),P50FilterBBKJRangeBean.DFilter::class.java)
                LogUtil.d("r_N40_N30-->${GsonHelper.toJson(mDFilter)}")
            }
            -30->{
                p50FilterBBKjRangeBean.r_N30_N20 = GsonHelper.parse(GsonHelper.toJson(mDFilter),P50FilterBBKJRangeBean.DFilter::class.java)
                LogUtil.d("r_N30_N20-->${GsonHelper.toJson(mDFilter)}")
            }
            -20->{
                p50FilterBBKjRangeBean.r_N20_N10 = GsonHelper.parse(GsonHelper.toJson(mDFilter),P50FilterBBKJRangeBean.DFilter::class.java)
                LogUtil.d("r_N20_N10-->${GsonHelper.toJson(mDFilter)}")
            }
            -10->{
                p50FilterBBKjRangeBean.r_N10_0 = GsonHelper.parse(GsonHelper.toJson(mDFilter),P50FilterBBKJRangeBean.DFilter::class.java)
                LogUtil.d("r_N10_0-->${GsonHelper.toJson(mDFilter)}")
            }
            0->{
                p50FilterBBKjRangeBean.r_0_10 = GsonHelper.parse(GsonHelper.toJson(mDFilter),P50FilterBBKJRangeBean.DFilter::class.java)
                LogUtil.d("r_0_10-->${GsonHelper.toJson(mDFilter)}")
            }
            10->{
                p50FilterBBKjRangeBean.r_10_20 = GsonHelper.parse(GsonHelper.toJson(mDFilter),P50FilterBBKJRangeBean.DFilter::class.java)
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
        LogUtil.d("OC:${oldBeanList[0].date}--$OC,OO:$OO")



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

        LogUtil.d("originOM_M:$originOM_M")
        when (x) {
            35->{
                if ((originOM_M >= -70 && originOM_M <= -60)||(originOM_M >= -50 && originOM_M <= -40)||(originOM_M >= -40 && originOM_M <= -30)
                    ||(originOM_M >= -30 && originOM_M <= -20)||(originOM_M >= -20 && originOM_M <= -10)||(originOM_M >= -10 && originOM_M <= 0)
                    || (originOM_M >= 0 && originOM_M <= 10)||(originOM_M >= 10 && originOM_M <= 20)) {
                    mDFilter.d36Bean = P50FilterBBKJRangeBean.MaxMinBean()
                    LogUtil.d("originOM_M:$originOM_M")
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
            29->{
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
            24->{
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
            19->{
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
            14->{
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
            9->{
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
            4->{
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
            2->{
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
        LogUtil.d("needContinue:${needContinue}")
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
            LogUtil.d("fitlerType:$fitlerType,day:$day")
            when (fitlerType) {
                -70->{
                    p50FilterBBKJRangeBean.r_N70_N60?.let {
                        when (day) {
                            35->it.d36Bean
                            29->it.d30Bean
                            24->it.d25Bean
                            19->it.d20Bean
                            14->it.d15Bean
                            9->it.d10Bean
                            4->it.d05Bean
                            2->it.d03Bean
                            else->null
                        }
                    }
                }
                -50->{
                    p50FilterBBKJRangeBean.r_N50_N40?.let{
                        when (day) {
                            35->it.d36Bean
                            29->it.d30Bean
                            24->it.d25Bean
                            19->it.d20Bean
                            14->it.d15Bean
                            9->it.d10Bean
                            4->it.d05Bean
                            2->it.d03Bean
                            else->null
                        }
                    }
                }
                -40->{
                    p50FilterBBKJRangeBean.r_N40_N30?.let{
                        when (day) {
                            35->it.d36Bean
                            29->it.d30Bean
                            24->it.d25Bean
                            19->it.d20Bean
                            14->it.d15Bean
                            9->it.d10Bean
                            4->it.d05Bean
                            2->it.d03Bean
                            else->null
                        }
                    }
                }
                -30->{
                    p50FilterBBKJRangeBean.r_N30_N20?.let{
                        when (day) {
                            35->it.d36Bean
                            29->it.d30Bean
                            24->it.d25Bean
                            19->it.d20Bean
                            14->it.d15Bean
                            9->it.d10Bean
                            4->it.d05Bean
                            2->it.d03Bean
                            else->null
                        }
                    }
                }
                -20->{
                    p50FilterBBKJRangeBean.r_N20_N10?.let{
                        when (day) {
                            35->it.d36Bean
                            29->it.d30Bean
                            24->it.d25Bean
                            19->it.d20Bean
                            14->it.d15Bean
                            9->it.d10Bean
                            4->it.d05Bean
                            2->it.d03Bean
                            else->null
                        }
                    }
                }
                -10->{
                    p50FilterBBKJRangeBean.r_N10_0?.let{
                        when (day) {
                            35->it.d36Bean
                            29->it.d30Bean
                            24->it.d25Bean
                            19->it.d20Bean
                            14->it.d15Bean
                            9->it.d10Bean
                            4->it.d05Bean
                            2->it.d03Bean
                            else->null
                        }
                    }
                }
                0->{
                    p50FilterBBKJRangeBean.r_0_10?.let{
                        when (day) {
                            35->it.d36Bean
                            29->it.d30Bean
                            24->it.d25Bean
                            19->it.d20Bean
                            14->it.d15Bean
                            9->it.d10Bean
                            4->it.d05Bean
                            2->it.d03Bean
                            else->null
                        }
                    }
                }
                10->{
                    p50FilterBBKJRangeBean.r_10_20?.let{
                        when (day) {
                            35->it.d36Bean
                            29->it.d30Bean
                            24->it.d25Bean
                            19->it.d20Bean
                            14->it.d15Bean
                            9->it.d10Bean
                            4->it.d05Bean
                            2->it.d03Bean
                            else->null
                        }
                    }
                }
                else->{
                    null
                }
            }
        }
        val trBean = kotlin.run {
            when (fitlerType) {
                -70->{
                    p50FilterBBKJRangeBean.r_N70_N60?.let{
                        when (day) {
                            35->it.d36TRBean
                            29->it.d30TRBean
                            24->it.d25TRBean
                            19->it.d20TRBean
                            14->it.d15TRBean
                            9->it.d10TRBean
                            4->it.d05TRBean
                            2->it.d03TRBean
                            else->null
                        }
                    }
                }
                -50->{
                    p50FilterBBKJRangeBean.r_N50_N40?.let{
                        when (day) {
                            35->it.d36TRBean
                            29->it.d30TRBean
                            24->it.d25TRBean
                            19->it.d20TRBean
                            14->it.d15TRBean
                            9->it.d10TRBean
                            4->it.d05TRBean
                            2->it.d03TRBean
                            else->null
                        }
                    }
                }
                -40->{
                    p50FilterBBKJRangeBean.r_N40_N30?.let{
                        when (day) {
                            35->it.d36TRBean
                            29->it.d30TRBean
                            24->it.d25TRBean
                            19->it.d20TRBean
                            14->it.d15TRBean
                            9->it.d10TRBean
                            4->it.d05TRBean
                            2->it.d03TRBean
                            else->null
                        }
                    }
                }
                -30->{
                    p50FilterBBKJRangeBean.r_N30_N20?.let{
                        when (day) {
                            35->it.d36TRBean
                            29->it.d30TRBean
                            24->it.d25TRBean
                            19->it.d20TRBean
                            14->it.d15TRBean
                            9->it.d10TRBean
                            4->it.d05TRBean
                            2->it.d03TRBean
                            else->null
                        }
                    }
                }
                -20->{
                    p50FilterBBKJRangeBean.r_N20_N10?.let{
                        when (day) {
                            35->it.d36TRBean
                            29->it.d30TRBean
                            24->it.d25TRBean
                            19->it.d20TRBean
                            14->it.d15TRBean
                            9->it.d10TRBean
                            4->it.d05TRBean
                            2->it.d03TRBean
                            else->null
                        }
                    }
                }
                -10->{
                    p50FilterBBKJRangeBean.r_N10_0?.let{
                        when (day) {
                            35->it.d36TRBean
                            29->it.d30TRBean
                            24->it.d25TRBean
                            19->it.d20TRBean
                            14->it.d15TRBean
                            9->it.d10TRBean
                            4->it.d05TRBean
                            2->it.d03TRBean
                            else->null
                        }
                    }
                }
                0->{
                    p50FilterBBKJRangeBean.r_0_10?.let{
                        when (day) {
                            35->it.d36TRBean
                            29->it.d30TRBean
                            24->it.d25TRBean
                            19->it.d20TRBean
                            14->it.d15TRBean
                            9->it.d10TRBean
                            4->it.d05TRBean
                            2->it.d03TRBean
                            else->null
                        }
                    }
                }
                10->{
                    p50FilterBBKJRangeBean.r_10_20?.let{
                        when (day) {
                            35->it.d36TRBean
                            29->it.d30TRBean
                            24->it.d25TRBean
                            19->it.d20TRBean
                            14->it.d15TRBean
                            9->it.d10TRBean
                            4->it.d05TRBean
                            2->it.d03TRBean
                            else->null
                        }
                    }
                }
                else->{
                    null
                }
            }
        }
        val mTrBean = TR_K_SLL_Bean()
        setTRDatas(mTrBean,0,mCodeList.size-1,mCodeList)

        if (null==bean||null==trBean){
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
            )&&judeTRParameter(mTrBean,trBean)
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
        val mBoolean =  mTrBean.k_A_TR>=trMinBean.k_A_TR&&mTrBean.k_A_TR<=trMaxBean.k_A_TR&&mTrBean.k_R_TR>=trMinBean.k_R_TR&&mTrBean.k_R_TR<=trMaxBean.k_R_TR&&mTrBean.k_B_TR>=trMinBean.k_B_TR&&mTrBean.k_B_TR<=trMaxBean.k_B_TR&&
        mTrBean.s_A_TR>=trMinBean.s_A_TR&&mTrBean.s_A_TR<=trMaxBean.s_A_TR&&mTrBean.s_R_TR>=trMinBean.s_R_TR&&mTrBean.s_R_TR<=trMaxBean.s_R_TR&&mTrBean.s_B_TR>=trMinBean.s_B_TR&&mTrBean.s_B_TR<=trMaxBean.s_B_TR&&
        mTrBean.k_SL_A_TR>=trMinBean.k_SL_A_TR&&mTrBean.k_SL_A_TR<=trMaxBean.k_SL_A_TR&&mTrBean.k_SL_R_TR>=trMinBean.k_SL_R_TR&&mTrBean.k_SL_R_TR<=trMaxBean.k_SL_R_TR&&mTrBean.k_SL_B_TR>=trMinBean.k_SL_B_TR&&mTrBean.k_SL_B_TR<=trMaxBean.k_SL_B_TR
        LogUtil.d("$mBoolean")
        return mBoolean
    }

    private fun judeFilterParameter(
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
        var beginBoolean =  (OM_M >= minBean.oM_M && OM_M <= maxBean.oM_M) && (OM_C >= minBean.oM_C && OM_C <= maxBean.oM_C) && (OM_P >= minBean.oM_P && OM_P <= maxBean.oM_P) && (OM_L >= minBean.oM_L && OM_L <= maxBean.oM_L) &&
                (OL_M >= minBean.oL_M && OL_M <= maxBean.oL_M) && (OL_C >= minBean.oL_C && OL_C <= maxBean.oL_C) && (OL_P >= minBean.oL_P && OL_P <= maxBean.oL_P) && (OL_L >= minBean.oL_L && OL_L <= maxBean.oL_L) &&
                (OC_M >= minBean.oC_M && OC_M <= maxBean.oC_M) && (OC_C >= minBean.oC_C && OC_C <= maxBean.oC_C) && (OC_P >= minBean.oC_P && OC_P <= maxBean.oC_P) && (OC_L >= minBean.oC_L && OC_L <= maxBean.oC_L) &&
                (OP_M >= minBean.oO_M && OP_M <= maxBean.oO_M) && (OP_C >= minBean.oO_C && OP_C <= maxBean.oO_C) && (OP_P >= minBean.oO_P && OP_P <= maxBean.oO_P) && (OP_L >= minBean.oO_L && OP_L <= maxBean.oO_L) &&
                (OM_OC >= minBean.oM_OC && OM_OC <= maxBean.oM_OC) && (OM_OP >= minBean.oM_OP && OM_OP <= maxBean.oM_OP) && (OM_OL >= minBean.oM_OL && OM_OL <= maxBean.oM_OL) && (OC_OP >= minBean.oC_OP && OC_OP <= maxBean.oC_OP) && (OC_OL >= minBean.oC_OL && OC_OL <= maxBean.oC_OL) && (OP_OL >= minBean.oP_OL && OP_OL <= maxBean.oP_OL) &&
                (M_C >= minBean.m_C && M_C <= maxBean.m_C) && (M_P >= minBean.m_P && M_P <= maxBean.m_P) && (M_L >= minBean.m_L && M_L <= maxBean.m_L) && (C_P >= minBean.c_P && C_P <= maxBean.c_P) && (C_L >= minBean.c_L && C_L <= maxBean.c_L) && (P_L >= minBean.p_L && P_L <= maxBean.p_L)
        if (!beginBoolean) {
            return beginBoolean
        } else {
            when (fitlerType) {
                -30->{
                    when (day) {
                        4->{
                            beginBoolean = OM_P<6&&OL_C>-20&&OM_OC<6
                        }
                        9->{
                            LogUtil.d("9---> $OC_M>-23,$OC_C>-16,$OP_M>-25,$OP_C>-28,$OP_P>-10,$OP_L>-5,$OP_M>-32,$OL_C>-25,$OL_C>-25,$OL_P>-16,$OL_L>-8,$C_P<15")
                            beginBoolean = OC_M>-23&&OC_C>-16&&OP_M>-25&&OP_C>-28&&OP_P>-10&&OP_L>-5&&OP_M>-32&&OL_C>-25&&OL_C>-25&&OL_P>-16&&OL_L>-8&&C_P<15
                        }
                        14->{
                            beginBoolean = OM_M>-17&&OM_C>-15&&OC_M>-30&&OC_C>-20&&OP_M>-28&&OP_C>-20&&OL_M>-35&&OL_C>-30&&M_P<23&&M_L<26&&C_P<18&&C_L<20
                        }
                        19->{
                            beginBoolean = OM_M>-17&&OC_M>-30&&OC_C>-20&&OL_M>-42&&OL_C>-35&&M_P<22&&M_L<25&&C_P<17&&C_L<20
                        }
                        24->{
                            beginBoolean = OC_M>-30&&OC_C>-25&&OP_M>-40&&OP_C>-30&&OL_M>-48&&M_L<28&&C_P<20&&C_L<20
                        }
                        29->{
                            beginBoolean = OP_M>-44&&OP_C>-33&&M_L<28&&C_L<22
                        }
                        35->{
//                            LogUtil.d("35--->$OC_M>-40,$OP_M>-50,$OP_C>-40,$OL_M>-51,$OL_C>-50,$M_L<30")
                            beginBoolean = OC_M>-40&&OP_M>-50&&OP_C>-40&&OL_M>-51&&OL_C>-50&&M_L<30
                        }
                    }
                }
            }

        }
        LogUtil.d("fitlerType:$fitlerType,day:$day,beginBoolean:$beginBoolean")
        return beginBoolean
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

    fun setFilterP50ResultType(originOM_M:Float,mDFilter: P50FilterBBKJRangeBean.DFilter,mP50Bean: P50FilterBBKJRangeBean) {
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

    fun getFilterP50ResultType(originOM_M:Float): Int {
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
        LogUtil.d(type.toString())
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
}