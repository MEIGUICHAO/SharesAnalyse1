package com.mgc.sharesanalyse.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Month8Data {

    @Id
    private Long id;

    private String YMD;

    private Long timeStamp;
    //index
    private String name;

    private String json;



    @Generated(hash = 1808485077)
    public Month8Data(Long id, String YMD, Long timeStamp, String name,
            String json) {
        this.id = id;
        this.YMD = YMD;
        this.timeStamp = timeStamp;
        this.name = name;
        this.json = json;
    }

    @Generated(hash = 216857273)
    public Month8Data() {
    }



    public String getYMD() {
        return this.YMD;
    }

    public void setYMD(String YMD) {
        this.YMD = YMD;
    }

    public Long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJson() {
        return this.json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
