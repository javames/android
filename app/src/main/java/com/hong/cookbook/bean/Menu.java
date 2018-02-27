package com.hong.cookbook.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/2/26.
 */

@Entity
public class Menu {

    @Id(autoincrement = true)
    private Long id;
    private String menuId;
    private String menuDtl;

    @Generated(hash = 1535829552)
    public Menu(Long id, String menuId, String menuDtl) {
        this.id = id;
        this.menuId = menuId;
        this.menuDtl = menuDtl;
    }

    @Generated(hash = 1631206187)
    public Menu() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuDtl() {
        return menuDtl;
    }

    public void setMenuDtl(String menuDtl) {
        this.menuDtl = menuDtl;
    }
}
