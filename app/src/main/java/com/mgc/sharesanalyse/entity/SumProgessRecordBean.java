package com.mgc.sharesanalyse.entity;

import com.mgc.sharesanalyse.utils.DBUtils;

public class SumProgessRecordBean {
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public BaseRecordBean getcHDDProgressBean() {
        return cHDDProgressBean;
    }

    public void setcHDDProgressBean(BaseRecordBean cHDDProgressBean) {
        this.cHDDProgressBean = cHDDProgressBean;
    }

    private BaseRecordBean cHDDProgressBean;

    public static class BaseRecordBean {
        private int date;

        public int getDate() {
            return date;
        }

        public void setDate(int date) {
            this.date = date;
        }
    }


}
