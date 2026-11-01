package com.ls.librarybase.base.list;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.ls.librarybase.base.BaseViewModel;
import com.ls.network.bean.ResList;
import com.ls.network.config.ErrorStatusConfig;

import java.util.List;

public class BaseListViewModel<T,V extends BaseListModel> extends BaseViewModel implements IListListenner<T>{
    public V mModel;
    public BaseListViewModel(V model){
        this.mModel=model;
        mModel.setListenner(this);
    }
    //列表需要使用的数据
    public MutableLiveData<ResList<T>> mDatas = new MutableLiveData<>();
    //是否还能继续加载更多 默认是true
    //是否还能继续加载更多  默认是true
    public MutableLiveData<Boolean> mIsloadMore =new MutableLiveData<>(true);
    public void requestDatas(boolean isFirst){
        if(isFirst){
            mIsloadMore.setValue(true);
        }
        mModel.requestDatas(isFirst);


    }

    public MutableLiveData<ResList<T>> getDatas() {
        return mDatas;
    }

    public MutableLiveData<Boolean> getIsloadMore() {
        return mIsloadMore;
    }


    @Override
    public void onLoadFinish(boolean isFirst, ResList<T> datas) {

        if(isFirst){
            //第一次加载或者是刷新的情况
            mDatas.setValue(datas);
        }
        else {
            //分页加载的情况
            ResList<T> value = mDatas.getValue();
            List<T> list = value.getList();
            list.addAll(datas.getList());
            mDatas.setValue(value);
        }
        int count=datas.getCount();
        if(mDatas.getValue().getList().size()>=count){
            mIsloadMore.setValue(false);
        }
    }

    @Override
    public void onLoadFailure(int statusCode) {
        mErrorCode.setValue(statusCode);
        if(statusCode== ErrorStatusConfig.ERROR_STATUS_EMPTY){
            mIsloadMore.setValue(false);
        }
    }
}
