package com.mgc.sharesanalyse.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class AnalyseSizeBean {

    @Id
    private Long id;
    private int Code;
    private Double percent;
    private int countSize;
    private int tenTimesSize;
    private int ge100mSize;
    private int ge50mSize;
    private int ge20mSize;
    private int ge10mSize;
    private int ge5mSize;
    private int gt1000TimesSize;
    private int gt100TimesSize;
    private int PPGtCurSize;
    private int CurGtPPSize;
    private int B0;
    private int S0;
    private String B0Str;
    private String S0Str;
    private Double current;
    private String tenTimesSizeStr;
    private String ge100mSizeStr;
    private String ge50mSizeStr;
    private String ge20mSizeStr;
    private String ge10mSizeStr;
    private String ge5mSizeStr;
    private String gt1000TimesSizeStr;
    private String gt100TimesSizeStr;
    private String PPGtCurSizeStr;
    private String CurGtPPSizeStr;
    @Generated(hash = 1984512402)
    public AnalyseSizeBean(Long id, int Code, Double percent, int countSize,
            int tenTimesSize, int ge100mSize, int ge50mSize, int ge20mSize,
            int ge10mSize, int ge5mSize, int gt1000TimesSize, int gt100TimesSize,
            int PPGtCurSize, int CurGtPPSize, int B0, int S0, String B0Str,
            String S0Str, Double current, String tenTimesSizeStr,
            String ge100mSizeStr, String ge50mSizeStr, String ge20mSizeStr,
            String ge10mSizeStr, String ge5mSizeStr, String gt1000TimesSizeStr,
            String gt100TimesSizeStr, String PPGtCurSizeStr,
            String CurGtPPSizeStr) {
        this.id = id;
        this.Code = Code;
        this.percent = percent;
        this.countSize = countSize;
        this.tenTimesSize = tenTimesSize;
        this.ge100mSize = ge100mSize;
        this.ge50mSize = ge50mSize;
        this.ge20mSize = ge20mSize;
        this.ge10mSize = ge10mSize;
        this.ge5mSize = ge5mSize;
        this.gt1000TimesSize = gt1000TimesSize;
        this.gt100TimesSize = gt100TimesSize;
        this.PPGtCurSize = PPGtCurSize;
        this.CurGtPPSize = CurGtPPSize;
        this.B0 = B0;
        this.S0 = S0;
        this.B0Str = B0Str;
        this.S0Str = S0Str;
        this.current = current;
        this.tenTimesSizeStr = tenTimesSizeStr;
        this.ge100mSizeStr = ge100mSizeStr;
        this.ge50mSizeStr = ge50mSizeStr;
        this.ge20mSizeStr = ge20mSizeStr;
        this.ge10mSizeStr = ge10mSizeStr;
        this.ge5mSizeStr = ge5mSizeStr;
        this.gt1000TimesSizeStr = gt1000TimesSizeStr;
        this.gt100TimesSizeStr = gt100TimesSizeStr;
        this.PPGtCurSizeStr = PPGtCurSizeStr;
        this.CurGtPPSizeStr = CurGtPPSizeStr;
    }
    @Generated(hash = 3803182)
    public AnalyseSizeBean() {
    }
    public int getCountSize() {
        return this.countSize;
    }
    public void setCountSize(int countSize) {
        this.countSize = countSize;
    }
    public int getTenTimesSize() {
        return this.tenTimesSize;
    }
    public void setTenTimesSize(int tenTimesSize) {
        this.tenTimesSize = tenTimesSize;
    }
    public int getGe100mSize() {
        return this.ge100mSize;
    }
    public void setGe100mSize(int ge100mSize) {
        this.ge100mSize = ge100mSize;
    }
    public int getGe50mSize() {
        return this.ge50mSize;
    }
    public void setGe50mSize(int ge50mSize) {
        this.ge50mSize = ge50mSize;
    }
    public int getGe20mSize() {
        return this.ge20mSize;
    }
    public void setGe20mSize(int ge20mSize) {
        this.ge20mSize = ge20mSize;
    }
    public int getGe10mSize() {
        return this.ge10mSize;
    }
    public void setGe10mSize(int ge10mSize) {
        this.ge10mSize = ge10mSize;
    }
    public int getGe5mSize() {
        return this.ge5mSize;
    }
    public void setGe5mSize(int ge5mSize) {
        this.ge5mSize = ge5mSize;
    }
    public int getGt1000TimesSize() {
        return this.gt1000TimesSize;
    }
    public void setGt1000TimesSize(int gt1000TimesSize) {
        this.gt1000TimesSize = gt1000TimesSize;
    }
    public int getGt100TimesSize() {
        return this.gt100TimesSize;
    }
    public void setGt100TimesSize(int gt100TimesSize) {
        this.gt100TimesSize = gt100TimesSize;
    }
    public int getPPGtCurSize() {
        return this.PPGtCurSize;
    }
    public void setPPGtCurSize(int PPGtCurSize) {
        this.PPGtCurSize = PPGtCurSize;
    }
    public int getCurGtPPSize() {
        return this.CurGtPPSize;
    }
    public void setCurGtPPSize(int CurGtPPSize) {
        this.CurGtPPSize = CurGtPPSize;
    }
    public String getTenTimesSizeStr() {
        return this.tenTimesSizeStr;
    }
    public void setTenTimesSizeStr(String tenTimesSizeStr) {
        this.tenTimesSizeStr = tenTimesSizeStr;
    }
    public String getGe100mSizeStr() {
        return this.ge100mSizeStr;
    }
    public void setGe100mSizeStr(String ge100mSizeStr) {
        this.ge100mSizeStr = ge100mSizeStr;
    }
    public String getGe50mSizeStr() {
        return this.ge50mSizeStr;
    }
    public void setGe50mSizeStr(String ge50mSizeStr) {
        this.ge50mSizeStr = ge50mSizeStr;
    }
    public String getGe20mSizeStr() {
        return this.ge20mSizeStr;
    }
    public void setGe20mSizeStr(String ge20mSizeStr) {
        this.ge20mSizeStr = ge20mSizeStr;
    }
    public String getGe10mSizeStr() {
        return this.ge10mSizeStr;
    }
    public void setGe10mSizeStr(String ge10mSizeStr) {
        this.ge10mSizeStr = ge10mSizeStr;
    }
    public String getGe5mSizeStr() {
        return this.ge5mSizeStr;
    }
    public void setGe5mSizeStr(String ge5mSizeStr) {
        this.ge5mSizeStr = ge5mSizeStr;
    }
    public String getGt1000TimesSizeStr() {
        return this.gt1000TimesSizeStr;
    }
    public void setGt1000TimesSizeStr(String gt1000TimesSizeStr) {
        this.gt1000TimesSizeStr = gt1000TimesSizeStr;
    }
    public String getGt100TimesSizeStr() {
        return this.gt100TimesSizeStr;
    }
    public void setGt100TimesSizeStr(String gt100TimesSizeStr) {
        this.gt100TimesSizeStr = gt100TimesSizeStr;
    }
    public String getPPGtCurSizeStr() {
        return this.PPGtCurSizeStr;
    }
    public void setPPGtCurSizeStr(String PPGtCurSizeStr) {
        this.PPGtCurSizeStr = PPGtCurSizeStr;
    }
    public String getCurGtPPSizeStr() {
        return this.CurGtPPSizeStr;
    }
    public void setCurGtPPSizeStr(String CurGtPPSizeStr) {
        this.CurGtPPSizeStr = CurGtPPSizeStr;
    }
    public int getCode() {
        return this.Code;
    }
    public void setCode(int Code) {
        this.Code = Code;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Double getPercent() {
        return this.percent;
    }
    public void setPercent(Double percent) {
        this.percent = percent;
    }
    public int getB0() {
        return this.B0;
    }
    public void setB0(int B0) {
        this.B0 = B0;
    }
    public int getS0() {
        return this.S0;
    }
    public void setS0(int S0) {
        this.S0 = S0;
    }
    public String getB0Str() {
        return this.B0Str;
    }
    public void setB0Str(String B0Str) {
        this.B0Str = B0Str;
    }
    public String getS0Str() {
        return this.S0Str;
    }
    public void setS0Str(String S0Str) {
        this.S0Str = S0Str;
    }
    public Double getCurrent() {
        return this.current;
    }
    public void setCurrent(Double current) {
        this.current = current;
    }
}
