package com.mgc.sharesanalyse.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UpdateCodeListBean {

    @Id
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    private int date;

    @Generated(hash = 601387392)
    public UpdateCodeListBean(long id, int date) {
        this.id = id;
        this.date = date;
    }

    @Generated(hash = 1786545228)
    public UpdateCodeListBean() {
    }
}
