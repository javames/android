package com.hong.cookbook.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Administrator on 2018/3/3.
 */
@Entity
public class HotSelect {
    @Id(autoincrement = true)
    private Long id;
    private String key;

    @Generated(hash = 1957393643)
    public HotSelect(Long id, String key) {
        this.id = id;
        this.key = key;
    }

    @Generated(hash = 19976623)
    public HotSelect() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
