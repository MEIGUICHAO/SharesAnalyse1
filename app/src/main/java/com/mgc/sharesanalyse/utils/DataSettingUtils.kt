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
        fitlerType: Int,
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
                if ((originOM_M >= -70 && originOM_M <= -60)||(originOM_M >= -50 && originOM_M <= -40)||(originOM_M >= -40 && originOM_M <= -30)
                    ||(originOM_M >= -30 && originOM_M <= -20)||(originOM_M >= -20 && originOM_M <= -10)||(originOM_M >= -10 && originOM_M <= 0)
                    || (originOM_M >= 0 && originOM_M <= 10)||(originOM_M >= 10 && originOM_M <= 20)) {
                    mDFilter.d36Bean = P50FilterBBKJRangeBean.MaxMinBean()
                    needContinue = judeParameterAndGetResult(
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
        if (null==bean){
            return false
        }
        val maxBean = bean.maxBean
        val minBean = bean.minBean
        val OM_M = OM - M
        val OM_C = OM - C
        val OM_P = OM - O
        val OM_L = OM - L

        val OL_M = OL - M
        val OL_C = OL - C
        val OL_P = OL - O
        val OL_L = OL - L

        val OC_M = OC - M
        val OC_C = OC - C
        val OC_P = OC - O
        val OC_L = OC - L

        val OP_M = OO - M
        val OP_C = OO - C
        val OP_P = OO - O
        val OP_L = OO - L

        val OM_OC = OM - OC
        val OM_OP = OM - OO
        val OM_OL = OM - OL
        val OC_OP = OC - OO
        val OC_OL = OC - OL
        val OP_OL = OO - OL

        val M_C = M - C
        val M_P = M - O
        val M_L = M - L
        val C_P = C - O
        val C_L = C - L
        val P_L = O - L


        val mMaxBean = BaseFilterKJBBRangeBean()
        if (judeFilterParameter(
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
        ) {
            setMaxDatas(mMaxBean, OM, M, C, L, OC, OL, OO, O)
            mDMaxBean.maxBean = mMaxBean
        } else {
            needContinue1 = false
        }
        return needContinue1
    }

    private fun judeFilterParameter(
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
        return (OM_M >= minBean.oM_M && OM_M <= maxBean.oM_M) && (OM_C >= minBean.oM_C && OM_C <= maxBean.oM_C) && (OM_P >= minBean.oM_P && OM_P <= maxBean.oM_P) && (OM_L >= minBean.oM_L && OM_L <= maxBean.oM_L) &&
                (OL_M >= minBean.oL_M && OL_M <= maxBean.oL_M) && (OL_C >= minBean.oL_C && OL_C <= maxBean.oL_C) && (OL_P >= minBean.oL_P && OL_P <= maxBean.oL_P) && (OL_L >= minBean.oL_L && OL_L <= maxBean.oL_L) &&
                (OC_M >= minBean.oC_M && OC_M <= maxBean.oC_M) && (OC_C >= minBean.oC_C && OC_C <= maxBean.oC_C) && (OC_P >= minBean.oC_P && OC_P <= maxBean.oC_P) && (OC_L >= minBean.oC_L && OC_L <= maxBean.oC_L) &&
                (OP_M >= minBean.oO_M && OP_M <= maxBean.oO_M) && (OP_C >= minBean.oO_C && OP_C <= maxBean.oO_C) && (OP_P >= minBean.oO_P && OP_P <= maxBean.oO_P) && (OP_L >= minBean.oO_L && OP_L <= maxBean.oO_L) &&
                (OM_OC >= minBean.oM_OC && OM_OC <= maxBean.oM_OC) && (OM_OP >= minBean.oM_OP && OM_OP <= maxBean.oM_OP) && (OM_OL >= minBean.oM_OL && OM_OL <= maxBean.oM_OL) && (OC_OP >= minBean.oC_OP && OC_OP <= maxBean.oC_OP) && (OC_OL >= minBean.oC_OL && OC_OL <= maxBean.oC_OL) && (OP_OL >= minBean.oP_OL && OP_OL <= maxBean.oP_OL) &&
                (M_C >= minBean.m_C && M_C <= maxBean.m_C) && (M_P >= minBean.m_P && M_P <= maxBean.m_P) && (M_L >= minBean.m_L && M_L <= maxBean.m_L) && (C_P >= minBean.c_P && C_P <= maxBean.c_P) && (C_L >= minBean.c_L && C_L <= maxBean.c_L) && (P_L >= minBean.p_L && P_L <= maxBean.p_L)
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
        return type
    }

}