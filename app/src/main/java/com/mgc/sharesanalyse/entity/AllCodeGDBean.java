package com.mgc.sharesanalyse.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AllCodeGDBean {
    @Id
    long id;
    String code;
    String name;
    @Generated(hash = 1529775374)
    public AllCodeGDBean(long id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }
    @Generated(hash = 80221880)
    public AllCodeGDBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
