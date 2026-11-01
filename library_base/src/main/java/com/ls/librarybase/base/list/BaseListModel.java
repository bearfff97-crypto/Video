package com.ls.librarybase.base.list;

public abstract class BaseListModel {
    protected int mPage=1;//当前请求的页数
    protected static final int mLimit =10;//每页需要请求的数量
    protected IListListenner mListenner;

    public void setListenner(IListListenner listenner) {

        this.mListenner = listenner;
    }

    /**
     * 分页加载网络数据
     *
     * @param isFirst
     */
    public abstract void requestDatas(boolean isFirst);
}
