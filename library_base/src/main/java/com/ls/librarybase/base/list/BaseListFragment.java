package com.ls.librarybase.base.list;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ls.librarybase.R;
import com.ls.librarybase.base.BaseFragment;
import com.ls.librarybase.base.BaseViewModel;
import com.ls.librarybase.databinding.LayoutSrtRecyclerviewBinding;
import com.ls.librarybase.databinding.LayoutStatusViewBinding;
import com.ls.network.bean.ResList;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

public abstract class BaseListFragment<T> extends BaseFragment<LayoutSrtRecyclerviewBinding, BaseListViewModel> {
    private RecyclerView.Adapter mAdapter ;
    @Override
    protected int getLayoutResId() {
        return R.layout.layout_srt_recyclerview;
    }

    @Override
    protected int getBindingVariableId() {
        return 0;
    }

    @Override
    protected void initView() {
        //初始化recycleView
      //  LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager layoutManager=getLayoutManager();
        mDataBinding.recyclerView.setLayoutManager(layoutManager);


        //初始化适配器
        mAdapter=getAdapter();
        mDataBinding.recyclerView.setAdapter(mAdapter);
        //   int pageType = getArguments().getInt(HomeConfig.KEY_VIDEO_LIST_TYPE);
        //为smartRefreshLayout添加 加载更多的回调
        mDataBinding.smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //下拉触发这个方法
                //mViewModel.requestData(mPageType,false);
               // onLoadMoreDatas(false);
                mViewModel.requestDatas(false);
            }
        });
        mDataBinding.smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //刷新
              //  mViewModel.requestData(mPageType,true);
               // onLoadMoreDatas(true);
                mViewModel.requestDatas(true);

            }
        });

    }



    @Override
    protected void initData() {
        mViewModel.requestDatas(true);
        mViewModel.getDatas().observe(getViewLifecycleOwner(), new Observer<ResList<T>>() {
            @Override
            public void onChanged(ResList<T> datas) {
                SmartRefreshLayout smartRefreshLayout = mDataBinding.smartRefreshLayout;
                if (smartRefreshLayout.isRefreshing()) {
                    smartRefreshLayout.finishRefresh();
                }
                if (smartRefreshLayout.isLoading()) {
                    smartRefreshLayout.finishLoadMore();
                }
                List<T> lists= datas.getList();
                onDatasRequestSuccess(lists);
                //mAdapter.setvideos(resVideos);
            }
        });
        mViewModel.getIsloadMore().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoadMore) {
                mDataBinding.smartRefreshLayout.setEnableLoadMore(isLoadMore);
                if (!isLoadMore) {
                    Toast.makeText(getContext(), "没有更多数据了", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    protected abstract RecyclerView.Adapter getAdapter();
    protected abstract RecyclerView.LayoutManager getLayoutManager() ;
    protected abstract void onDatasRequestSuccess(List<T >list);



}
