package com.hong.cookbook;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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
import com.hong.cookbook.bean.Menu;
import com.hong.cookbook.event.TopMsg;
import com.hong.cookbook.greendao.MenuDaoUtil;
import com.hong.cookbook.http.CommonApi;
import com.hong.cookbook.http.HttpResult;
import com.hong.cookbook.http.RetrofitService;
import com.hong.cookbook.widget.CustomGridItemDecoration;
import com.hong.cookbook.widget.FilterTab;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private static String CTGID = "ctgId";
    private ArrayList<CookBean.ResultBean.ChildsBeanX.ChildsBean> childList;

    private PageAdapter adapter;

    private boolean isFirstVisible;

    private int page = 1;
    private int totalPage = 1;
    private String ctgId;

    private int size = 20;

    private MenuDaoUtil menuDaoUtil;

    private View rootView;// 缓存Fragment view

    private OnScroll onScroll;

    public static CookPage newInstance(List<CookBean.ResultBean.ChildsBeanX.ChildsBean> cookId) {
        CookPage cookPage = new CookPage();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CTGID, (Serializable) cookId);
        cookPage.setArguments(bundle);
        return cookPage;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        childList = (ArrayList<CookBean.ResultBean.ChildsBeanX.ChildsBean>) arguments.getSerializable(CTGID);

        ctgId = childList.get(0).getCategoryInfo().getCtgId();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Log.i("show", ctgId + " // onDestroyView() ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();

        Log.i("show", ctgId + " // onFragmentFirstVisible() ");
        isFirstVisible = true;
        initRecy(ctgId);
        //
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        Log.i("show", " isFirstVisible: " + isFirstVisible);
        if (isVisible) {
            if (!isFirstVisible) {
                Log.i("show", ctgId + " // onFragmentVisibleChange() ");
                Log.i("show", " ctgId: " + ctgId);
                List<Menu> menus = menuDaoUtil.queryMenuByMenuId(ctgId);
                ArrayList<CookMenuBean.ResultBean.ListBean> listBean = JSONUtils.fromJsonList(menus.get(0).getMenuDtl(), CookMenuBean.ResultBean.ListBean.class);

                adapter.setNewData(listBean);


            }

        } else {
            isFirstVisible = false;
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (null == rootView) {
            rootView = inflater.inflate(R.layout.fragment_cookpage, null);
        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        final ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }



        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.list_cook);

        filterTab = view.findViewById(R.id.filter_lab);
        int count = childList.size();
        String[] items = new String[count];
        for (int i = 0; i < count; i++) {
            items[i] = childList.get(i).getCategoryInfo().getNameX();
        }
        filterTab.setData(items);
        filterTab.setShowTabCount(5);
        filterTab.invalite();
        filterTab.setSelectPostion(0);

        filterTab.setOnItemClickListener(new FilterTab.OnItemClick() {
            @Override
            public void itemClick(int position, View view) {
                String ctId = childList.get(position).getCategoryInfo().getCtgId();
                ctgId=ctId;
                page=1;
                initRecy(ctgId);
            }
        });

        adapter = new PageAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.bindToRecyclerView(recyclerView);

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page+=1;
                if(page==totalPage){
                    adapter.loadMoreEnd();
                }else {
                    initRecy(ctgId);
                }
            }
        },recyclerView);

        recyclerView.setOnScrollListener(new OnVerticalScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrolledUp() {
                super.onScrolledUp();
                Log.i("CookPage","onScrolledUp()");
                if(onScroll!=null){
                    onScroll.onScrolledUp();
                }
            }

            @Override
            public void onScrolledDown() {
                super.onScrolledDown();
                Log.i("CookPage","onScrolledDown()");
                if(onScroll!=null){
                    onScroll.onScrolledDown();
                }

            }

            @Override
            public void onScrolledToTop() {
                super.onScrolledToTop();
                Log.i("CookPage","onScrolledToTop()");

            }

            @Override
            public void onScrolledToBottom() {
                super.onScrolledToBottom();
                Log.i("CookPage","onScrolledToBottom()");

            }
        });
    }

    private void initRecy(final String cid) {
        RetrofitService.getInstance().createAPI()
                .getMenuCooks(page, null, cid, CommonApi.KEY, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CookMenuBean>() {
                    @Override
                    public void accept(CookMenuBean cookBeanHttpResult) throws Exception {
                        String msg = cookBeanHttpResult.getMsg();
                        if ("success".equals(msg)) {
                            CookMenuBean.ResultBean resultBean = cookBeanHttpResult.getResult();
                            if (resultBean != null) {
                                totalPage = resultBean.getTotal();

                                List<CookMenuBean.ResultBean.ListBean> list = resultBean.getList();

                                //保存刷新的数据到数据库
                                menuDaoUtil = new MenuDaoUtil(mContext);
                                if (page == 1) {
                                    List<Menu> menus = menuDaoUtil.queryMenuByMenuId(cid);
                                    if (menus == null || (menus != null && menus.size() == 0)) {
                                        menuDaoUtil.insertMenu(new Menu(null, cid, JSONUtils.GsonString(list)));
                                    } else {
                                        menuDaoUtil.updateMenu(new Menu(menus.get(0).getId(), cid, JSONUtils.GsonString(list)));
                                    }
                                    adapter.setNewData(list);
                                    recyclerView.smoothScrollToPosition(0);
                                }else{
                                    adapter.addData(list);
                                    adapter.loadMoreComplete();
                                }
                            }


                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(context, "网络请求出现错误！", Toast.LENGTH_LONG).show();
                    }
                });
    }


    private class PageAdapter extends BaseQuickAdapter<CookMenuBean.ResultBean.ListBean, BaseViewHolder> {

        public PageAdapter() {
            super(R.layout.grid_item);
        }

        @Override
        protected void convert(BaseViewHolder helper, CookMenuBean.ResultBean.ListBean item) {
            ImageView imageView = helper.getView(R.id.food_icon);
            String thumbnail = item.getThumbnail();
            if (!TextUtils.isEmpty(thumbnail)) {
                GlideUtil.INSTANCE.loadImg(mContext, thumbnail, imageView);
            }
            TextView title = helper.getView(R.id.food_txt);
            title.setText(item.getName());

            TextView lab = helper.getView(R.id.food_lab);
            lab.setText(item.getCtgTitles());

            TextView make = helper.getView(R.id.food_make);
            String ingredients = item.getRecipe().getIngredients();
            if (!TextUtils.isEmpty(ingredients)) {
                make.setText(item.getRecipe().getIngredients().replaceAll("[\\[\\]]", ""));
            } else {
                make.setText(item.getRecipe().getSumary());
            }

        }
    }


    public void setOnScrollListener(OnScroll onScroll){
        this.onScroll=onScroll;
    }
    public interface OnScroll{

        void onScrolledDown();
        void onScrolledUp();

        void onScrolledToTop();
        void onScrolledToBottom();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("CookPage","onStart()");
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(TopMsg event) {
        if(recyclerView!=null){
            recyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("CookPage","onStop()");
        EventBus.getDefault().unregister(this);
    }
}
