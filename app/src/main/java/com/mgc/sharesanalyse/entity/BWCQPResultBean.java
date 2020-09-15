package com.mgc.sharesanalyse.entity;

public class BWCQPResultBean {

    /**
     * success : null
     * fail : null
     * code : 1
     * message : Already success
     * data : {"businessId":"20200428151950-5fc381e926_business","buyNumber":null}
     * customMessage :
     * count : null
     * systemError : false
     */

    private Object success;
    private Object fail;
    private int code;
    private String message;
    private DataBean data;
    private String customMessage;
    private Object count;
    private boolean systemError;

    public Object getSuccess() {
        return success;
    }

    public void setSuccess(Object success) {
        this.success = success;
    }

    public Object getFail() {
        return fail;
    }

    public void setFail(Object fail) {
        this.fail = fail;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }

    public Object getCount() {
        return count;
    }

    public void setCount(Object count) {
        this.count = count;
    }

    public boolean isSystemError() {
        return systemError;
    }

    public void setSystemError(boolean systemError) {
        this.systemError = systemError;
    }

    public static class DataBean {
        /**
         * businessId : 20200428151950-5fc381e926_business
         * buyNumber : null
         */

        private String businessId;
        private Object buyNumber;

        public String getBusinessId() {
            return businessId;
        }

        public void setBusinessId(String businessId) {
            this.businessId = businessId;
        }

        public Object getBuyNumber() {
            return buyNumber;
        }

        public void setBuyNumber(Object buyNumber) {
            this.buyNumber = buyNumber;
        }
    }
}
