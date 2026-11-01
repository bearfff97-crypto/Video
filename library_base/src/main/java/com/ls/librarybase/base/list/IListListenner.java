package com.ls.librarybase.base.list;

import com.ls.network.bean.ResList;

public interface IListListenner<T> {
    /**
     * 网络请求成功
     * @param videos
     * @param isFirst 是否是第一次加载
     */
    void onLoadFinish(boolean isFirst,ResList<T> videos);

    /**
     * 网络请求失败
     * @param statusCode 错误码
     *
     */
    void onLoadFailure(int statusCode);
}
