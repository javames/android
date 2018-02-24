package com.hong.cookbook;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hong.cookbook.bean.CookBean;
import com.hong.cookbook.bean.CookMenuBean;
import com.hong.cookbook.http.CommonApi;
import com.hong.cookbook.http.HttpResult;
import com.hong.cookbook.http.RetrofitService;
import com.hong.cookbook.widget.CustomGridItemDecoration;
import com.hong.cookbook.widget.FilterTab;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/2/23.
 */

public class CookPage extends BaseLazyFragment {

    private Context context;
    private RecyclerView recyclerView;
    private FilterTab filterTab;
    private static String CTGID="ctgId";
    private ArrayList<CookBean.ResultBean.ChildsBeanX.ChildsBean> childList;

    private PageAdapter adapter;

    private int page=1;
    private int totalPage=1;

    private int size=20;

    public static CookPage newInstance(List<CookBean.ResultBean.ChildsBeanX.ChildsBean> cookId){
        CookPage cookPage=new CookPage();
        Bundle bundle=new Bundle();
        bundle.putSerializable(CTGID, (Serializable) cookId);
        cookPage.setArguments(bundle);
        return cookPage;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        childList = (ArrayList<CookBean.ResultBean.ChildsBeanX.ChildsBean>) arguments.getSerializable(CTGID);
    }


    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        String ctgId = childList.get(0).getCategoryInfo().getCtgId();


        initRecy(ctgId);
        //
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_cookpage,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.list_cook);

        filterTab=view.findViewById(R.id.filter_lab);
        int count=childList.size();
        String [] items=new String[count];
        for (int i = 0; i <count ; i++) {
            items[i]=childList.get(i).getCategoryInfo().getNameX();
        }
        filterTab.setData(items);
        filterTab.setShowTabCount(5);
        filterTab.invalite();

        filterTab.setOnItemClickListener(new FilterTab.OnItemClick() {
            @Override
            public void itemClick(int position, View view) {
                String ctgId = childList.get(position).getCategoryInfo().getCtgId();
                initRecy(ctgId);
            }
        });

    }

    private void initRecy(String cid){
        RetrofitService.getInstance().createAPI()
                .getMenuCooks(page,null,cid,CommonApi.KEY,size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CookMenuBean>() {
                    @Override
                    public void accept(CookMenuBean cookBeanHttpResult) throws Exception {
                        String msg = cookBeanHttpResult.getMsg();
                        if("success".equals(msg)){
                            CookMenuBean.ResultBean resultBean=cookBeanHttpResult.getResult();
                            if(resultBean!=null){
                                totalPage = resultBean.getTotal();

                                List<CookMenuBean.ResultBean.ListBean> list = resultBean.getList();

                                adapter=new PageAdapter(list);
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(gridLayoutManager);

                                recyclerView.setAdapter(adapter);
                                adapter.bindToRecyclerView(recyclerView);
                            }



                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(context,"网络请求出现错误！", Toast.LENGTH_LONG).show();
                    }
                });
    }


    private class PageAdapter extends BaseQuickAdapter<CookMenuBean.ResultBean.ListBean,BaseViewHolder>{

        public PageAdapter(@Nullable List<CookMenuBean.ResultBean.ListBean> data) {
            super(R.layout.grid_item,data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CookMenuBean.ResultBean.ListBean item) {
            ImageView imageView = helper.getView(R.id.food_icon);
            String thumbnail=item.getThumbnail();
            if(!TextUtils.isEmpty(thumbnail)){
                GlideUtil.INSTANCE.loadImg(mContext,thumbnail,imageView);
            }
            TextView title = helper.getView(R.id.food_txt);
            title.setText(item.getName());

            TextView lab=helper.getView(R.id.food_lab);
            lab.setText(item.getCtgTitles());

            TextView make=helper.getView(R.id.food_make);
            String ingredients=item.getRecipe().getIngredients();
            if(!TextUtils.isEmpty(ingredients)){
                make.setText(item.getRecipe().getIngredients().replaceAll("[\\[\\]]", ""));
            }else{
                make.setText(item.getRecipe().getSumary());
            }

        }
    }
}
