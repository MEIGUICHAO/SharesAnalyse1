package com.mgc.sharesanalyse.entity;


public class HolderChangeJsonBean {

    /**
     * HolderNum : 15562
     * PreviousHolderNum : 34
     * HolderNumChange : 15528
     * HolderNumChangeRate : 45670.5882
     * RangeChangeRate : -
     * EndDate : 2021-03-03T00:00:00
     * HolderAvgCapitalisation : 1702898.08507904
     * HolderAvgStockQuantity : 3212.95
     * TotalCapitalisation : 26500500000
     * CapitalStock : 50000000
     * NoticeDate : 2021-03-02T00:00:00
     * CapitalStockChange : 50000000
     * CapitalStockChangeEvent : -
     * ClosePrice : 530.01
     */

    private Integer HolderNum;
    private Integer PreviousHolderNum;
    private Integer HolderNumChange;
    private Double HolderNumChangeRate;
    private String RangeChangeRate;
    private String EndDate;
    private Double HolderAvgCapitalisation;
    private Double HolderAvgStockQuantity;
    private Long TotalCapitalisation;
    private Integer CapitalStock;
    private String NoticeDate;
    private Integer CapitalStockChange;
    private String CapitalStockChangeEvent;
    private String ClosePrice;

    public Integer getHolderNum() {
        return HolderNum;
    }

    public void setHolderNum(Integer holderNum) {
        HolderNum = holderNum;
    }

    public Integer getPreviousHolderNum() {
        return PreviousHolderNum;
    }

    public void setPreviousHolderNum(Integer previousHolderNum) {
        PreviousHolderNum = previousHolderNum;
    }

    public Integer getHolderNumChange() {
        return HolderNumChange;
    }

    public void setHolderNumChange(Integer holderNumChange) {
        HolderNumChange = holderNumChange;
    }

    public Double getHolderNumChangeRate() {
        return HolderNumChangeRate;
    }

    public void setHolderNumChangeRate(Double holderNumChangeRate) {
        HolderNumChangeRate = holderNumChangeRate;
    }

    public String getRangeChangeRate() {
        return RangeChangeRate;
    }

    public void setRangeChangeRate(String rangeChangeRate) {
        RangeChangeRate = rangeChangeRate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public Double getHolderAvgCapitalisation() {
        return HolderAvgCapitalisation;
    }

    public void setHolderAvgCapitalisation(Double holderAvgCapitalisation) {
        HolderAvgCapitalisation = holderAvgCapitalisation;
    }

    public Double getHolderAvgStockQuantity() {
        return HolderAvgStockQuantity;
    }

    public void setHolderAvgStockQuantity(Double holderAvgStockQuantity) {
        HolderAvgStockQuantity = holderAvgStockQuantity;
    }

    public Long getTotalCapitalisation() {
        return TotalCapitalisation;
    }

    public void setTotalCapitalisation(Long totalCapitalisation) {
        TotalCapitalisation = totalCapitalisation;
    }

    public Integer getCapitalStock() {
        return CapitalStock;
    }

    public void setCapitalStock(Integer capitalStock) {
        CapitalStock = capitalStock;
    }

    public String getNoticeDate() {
        return NoticeDate;
    }

    public void setNoticeDate(String noticeDate) {
        NoticeDate = noticeDate;
    }

    public Integer getCapitalStockChange() {
        return CapitalStockChange;
    }

    public void setCapitalStockChange(Integer capitalStockChange) {
        CapitalStockChange = capitalStockChange;
    }

    public String getCapitalStockChangeEvent() {
        return CapitalStockChangeEvent;
    }

    public void setCapitalStockChangeEvent(String capitalStockChangeEvent) {
        CapitalStockChangeEvent = capitalStockChangeEvent;
    }

    public String getClosePrice() {
        return ClosePrice;
    }

    public void setClosePrice(String closePrice) {
        ClosePrice = closePrice;
    }
}
