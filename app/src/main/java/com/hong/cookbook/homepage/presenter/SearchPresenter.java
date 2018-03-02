package com.hong.cookbook.homepage.presenter;


import android.content.Context;

import com.hong.cookbook.bean.CookBean;
import com.hong.cookbook.homepage.contact.HomeContact;
import com.hong.cookbook.homepage.contact.SearchContact;
import com.hong.cookbook.http.Api;
import com.hong.cookbook.http.CommonApi;
import com.hong.mvplib.mvpbase.impl.BasePresenterImpl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/1.
 */

public class SearchPresenter extends BasePresenterImpl<SearchContact.View> implements SearchContact.Presenter{

    public SearchPresenter(Context context, SearchContact.View view) {
        super(context, view);
    }

    @Override
    public void getData() {
//        Api.getInstance().getAllCategorys(CommonApi.KEY).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                        t: CookBean? -> initPage(t)
//                }, {
//               Toast.makeText(mContext.get(), "网络请求出现错误！", Toast.LENGTH_LONG).show()
//        })
        Disposable subscribe = Api.getInstance().getAllCategorys(CommonApi.KEY).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CookBean>() {
                    @Override
                    public void accept(CookBean cookBean) throws Exception {
                        if("success".equals(cookBean.getMsg())){
                            view.setData(cookBean.getResult().getChilds());
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        addDisposable(subscribe);
    }


}
