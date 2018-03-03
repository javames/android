package com.hong.cookbook.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Administrator on 2018/3/3.
 */

@Entity
public class HistorySelect {

    @Id(autoincrement = true)
    private Long id;
    private String key;

    @Generated(hash = 1704816930)
    public HistorySelect(Long id, String key) {
        this.id = id;
        this.key = key;
    }

    @Generated(hash = 1040526215)
    public HistorySelect() {
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
