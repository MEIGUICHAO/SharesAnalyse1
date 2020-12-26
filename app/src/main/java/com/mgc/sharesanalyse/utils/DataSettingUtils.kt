package com.mgc.sharesanalyse.utils

import com.mgc.sharesanalyse.base.getRevBeansOL
import com.mgc.sharesanalyse.base.getRevBeansOM
import com.mgc.sharesanalyse.entity.BaseFilterKJBBRangeBean
import com.mgc.sharesanalyse.entity.BaseFilterTRBBRangeBean
import com.mgc.sharesanalyse.entity.CodeHDDBean
import com.mgc.sharesanalyse.entity.P50FilterBBKJRangeBean

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
                p50FilterBBKjRangeBean.r_N70_N60 = mDFilter
            }
            -50->{
                p50FilterBBKjRangeBean.r_N50_N40 = mDFilter
            }
            -40->{
                p50FilterBBKjRangeBean.r_N40_N30 = mDFilter
            }
            -30->{
                p50FilterBBKjRangeBean.r_N30_N20 = mDFilter
            }
            -20->{
                p50FilterBBKjRangeBean.r_N20_N10 = mDFilter
            }
            -10->{
                p50FilterBBKjRangeBean.r_N10_0 = mDFilter
            }
            0->{
                p50FilterBBKjRangeBean.r_0_10 = mDFilter
            }
            10->{
                p50FilterBBKjRangeBean.r_10_20 = mDFilter
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
        originOM_M: Float,
        mDFilter: P50FilterBBKJRangeBean.DFilter,
        p50FilterBBKJRangeBean: P50FilterBBKJRangeBean,
        x: Int,
        targetBeanList: ArrayList<CodeHDDBean>,
        oldBeanList: ArrayList<CodeHDDBean>
    ): Boolean {
        val OM = oldBeanList.getRevBeansOM()
        val OL = oldBeanList.getRevBeansOL()
        val OC = oldBeanList[0].p_MA_J.aacp
        val OO = oldBeanList[oldBeanList.size - 1].p_MA_J.aacp

        val M = targetBeanList.getRevBeansOM()
        val L = targetBeanList.getRevBeansOL()

        val C = targetBeanList[0].p_MA_J.aacp
        val O = targetBeanList[targetBeanList.size - 1].p_MA_J.aaop
        var needContinue = true

        when (x) {
            35->{
                mDFilter.d36Bean = P50FilterBBKJRangeBean.MaxMinBean()
                val mMaxBean = BaseFilterKJBBRangeBean()
                if ((originOM_M >= -70 && originOM_M <= -60)||(originOM_M >= -50 && originOM_M <= -40)||(originOM_M >= -40 && originOM_M <= -30)
                    ||(originOM_M >= -30 && originOM_M <= -20)||(originOM_M >= -20 && originOM_M <= -10)||(originOM_M >= -10 && originOM_M <= 0)
                    || (originOM_M >= 0 && originOM_M <= 10)||(originOM_M >= 10 && originOM_M <= 20)) {
                    setMaxDatas(mMaxBean, OM, M, C, L, OC, OL, OO, O)
                    mDFilter.d36Bean.maxBean = mMaxBean
                } else {
                    needContinue = false
                }

            }
            29->{}
            24->{}
            19->{}
            14->{}
            9->{}
            4->{}
            2->{}
        }
        return needContinue

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
        mMaxBean.oM_M = OM - M
        mMaxBean.oM_C = OM - C
        mMaxBean.oM_L = OM - L
        mMaxBean.oM_OC = OM - OC
        mMaxBean.oM_OL = OM - OL
        mMaxBean.oM_OP = OM - OO

        mMaxBean.oL_L = OL - L
        mMaxBean.oL_M = OL - M
        mMaxBean.oL_C = OL - C
        mMaxBean.oL_P = OL - O

        mMaxBean.oC_L = OC - L
        mMaxBean.oC_M = OC - M
        mMaxBean.oC_C = OC - C
        mMaxBean.oC_P = OC - O
        mMaxBean.oC_OL = OC - OL
        mMaxBean.oC_OP = OC - OO

        mMaxBean.oO_L = OO - L
        mMaxBean.oO_M = OO - M
        mMaxBean.oO_C = OO - C
        mMaxBean.oO_P = OO - O
        mMaxBean.oP_OL = OO - OL

        mMaxBean.m_C = M - C
        mMaxBean.m_P = M - O
        mMaxBean.m_L = M - L
        mMaxBean.c_P = C - O
        mMaxBean.c_L = C - L
        mMaxBean.p_L = O - L
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

}