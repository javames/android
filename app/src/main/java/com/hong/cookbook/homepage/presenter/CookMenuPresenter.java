package com.hong.cookbook.homepage.presenter;

import android.content.Context;

import com.hong.cookbook.bean.CookBean;
import com.hong.cookbook.homepage.contact.CookMenuContact;
import com.hong.mvplib.mvpbase.BasePresenter;
import com.hong.mvplib.mvpbase.impl.BasePresenterImpl;

import java.util.List;

/**
 * Created by Administrator on 2018/3/3.
 */

public class CookMenuPresenter extends BasePresenterImpl<CookMenuContact.View> implements CookMenuContact.Presenter{


    public CookMenuPresenter(Context context, CookMenuContact.View view) {
        super(context, view);
    }

    @Override
    public void getData() {

    }
}
