package com.ls.feature_plaza.api;

import com.ls.feature_plaza.bean.ResPlaza;
import com.ls.network.bean.ResBase;
import com.ls.network.bean.ResList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 这里存plaza模块的api
 */


public interface PlazaApiService {
        /**
         * 广场首页数据
         * @return服务器返回的数据类型
         */
        @GET("addons/cms/api.eye/square")//指定请求方式，并定义了 URL 的路径
        Call<ResBase<List<ResPlaza>>> getPlaza();




}
