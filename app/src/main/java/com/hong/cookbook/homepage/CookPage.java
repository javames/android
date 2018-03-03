package com.hong.cookbook.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hong.cookbook.BaseLazyFragment;
import com.hong.cookbook.CookDtlActivity;
import com.hong.cookbook.JSONUtils;
import com.hong.cookbook.OnVerticalScrollListener;
import com.hong.cookbook.R;
import com.hong.cookbook.adapter.GridListAdapter;
import com.hong.cookbook.bean.CookBean;
import com.hong.cookbook.bean.CookMenuBean;
import com.hong.cookbook.bean.HistorySelect;
import com.hong.cookbook.bean.Menu;
import com.hong.cookbook.event.TopMsg;
import com.hong.cookbook.greendao.BeanDaoUtil;
import com.hong.cookbook.greendao.MenuDaoUtil;
import com.hong.cookbook.http.CommonApi;
import com.hong.cookbook.http.RetrofitService;
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
    private static String COOKNAME = "name";
    private ArrayList<CookBean.ResultBean.ChildsBeanX.ChildsBean> childList;

    private GridListAdapter adapter;

    private boolean isFirstVisible;

    private int page = 1;
    private int totalPage = 1;

    private String ctgId;
    private String cookName;

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

    public static CookPage newInstance(String keyName) {
        CookPage cookPage = new CookPage();
        Bundle bundle = new Bundle();
        bundle.putString(COOKNAME, keyName);
        cookPage.setArguments(bundle);
        return cookPage;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        childList = (ArrayList<CookBean.ResultBean.ChildsBeanX.ChildsBean>) arguments.getSerializable(CTGID);

        if (null != childList) {
            ctgId = childList.get(0).getCategoryInfo().getCtgId();
        }


        cookName = arguments.getString(COOKNAME);

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
        Log.i("show", ctgId + " // 网络请求数据 ");
        if (!TextUtils.isEmpty(ctgId)) {
            initRecy(null, ctgId);
        }

        if (!TextUtils.isEmpty(cookName)) {
            initRecy(cookName, null);
        }

        //
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);

        if (isVisible) {
            Log.i("show", " isVisible true ");
            Log.i("show", " onFragmentVisibleChange: " + isFirstVisible);
            if (!isFirstVisible) {
                Log.i("show", " ctgId: " + ctgId);
                Log.i("show", " 本地数据库获取数据填充 ");
                List<Menu> menus = menuDaoUtil.queryMenuByMenuId(ctgId);
                ArrayList<CookMenuBean.ResultBean.ListBean> listBean = JSONUtils.fromJsonList(menus.get(0).getMenuDtl(), CookMenuBean.ResultBean.ListBean.class);

                Log.i("show", " 数据长度： " + listBean.size());

                adapter.setNewData(listBean);
            }

        } else {
            Log.i("show", " isVisible false ");
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
        //保存刷新的数据到数据库
        menuDaoUtil = new MenuDaoUtil(mContext);
        filterTab = view.findViewById(R.id.filter_lab);
        if (!TextUtils.isEmpty(cookName)) {
            filterTab.setVisibility(View.GONE);
        }
        if (null != childList) {
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
                    ctgId = ctId;
                    page = 1;
                    initRecy(null, ctgId);
                }
            });
        }

        adapter = new GridListAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.bindToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List data = adapter.getData();
                CookMenuBean.ResultBean.ListBean listBean = (CookMenuBean.ResultBean.ListBean) data.get(position);
                CookMenuBean.ResultBean.ListBean.RecipeBean recipe = listBean.getRecipe();
                Intent intent = new Intent(mContext, CookDtlActivity.class);
                intent.putExtra("recipe", recipe);
                mContext.startActivity(intent);
            }
        });

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page += 1;
                if (page == totalPage) {
                    adapter.loadMoreEnd();
                } else {
                    initRecy(cookName, ctgId);
                }
            }
        }, recyclerView);

        recyclerView.setOnScrollListener(new OnVerticalScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrolledUp() {
                super.onScrolledUp();
                Log.i("CookPage", "onScrolledUp()");
                if (onScroll != null) {
                    onScroll.onScrolledUp();
                }
            }

            @Override
            public void onScrolledDown() {
                super.onScrolledDown();
                Log.i("CookPage", "onScrolledDown()");
                if (onScroll != null) {
                    onScroll.onScrolledDown();
                }

            }

            @Override
            public void onScrolledToTop() {
                super.onScrolledToTop();
                Log.i("CookPage", "onScrolledToTop()");

            }

            @Override
            public void onScrolledToBottom() {
                super.onScrolledToBottom();
                Log.i("CookPage", "onScrolledToBottom()");

            }
        });
    }

    private void initRecy(String name, final String cid) {
        RetrofitService.getInstance().createAPI()
                .getMenuCooks(page, name, cid, CommonApi.KEY, size)
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


                                if (page == 1) {
                                    if (!TextUtils.isEmpty(cid)) {
                                        List<Menu> menus = menuDaoUtil.queryMenuByMenuId(cid);
                                        if (menus == null || (menus != null && menus.size() == 0)) {
                                            menuDaoUtil.insertMenu(new Menu(null, cid, JSONUtils.GsonString(list)));
                                        } else {
                                            menuDaoUtil.updateMenu(new Menu(menus.get(0).getId(), cid, JSONUtils.GsonString(list)));
                                        }
                                    }
                                    adapter.setNewData(list);
                                    recyclerView.smoothScrollToPosition(0);
                                } else {
                                    adapter.addData(list);
                                    adapter.loadMoreComplete();
                                }
                            }

                            if(!TextUtils.isEmpty(cookName)){
                                HistorySelect historySelect = new HistorySelect();
                                historySelect.setId(null);
                                historySelect.setKey(cookName);
                                BeanDaoUtil.getInstance().insertBean(historySelect);
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

    public void setOnScrollListener(OnScroll onScroll) {
        this.onScroll = onScroll;
    }

    public interface OnScroll {

        void onScrolledDown();

        void onScrolledUp();

        void onScrolledToTop();

        void onScrolledToBottom();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("CookPage", "onStart()");
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(TopMsg event) {
        if (recyclerView != null) {
            recyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("CookPage", "onStop()");
        EventBus.getDefault().unregister(this);
    }
}
