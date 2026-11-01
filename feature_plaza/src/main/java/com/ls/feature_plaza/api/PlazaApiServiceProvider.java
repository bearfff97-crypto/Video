package com.ls.feature_plaza.api;

import com.ls.network.RetrofitProvider;


import retrofit2.Retrofit;

public class PlazaApiServiceProvider {
    /**
     * plaza模块中的PlazaApiService统一在这里获取，已变统一管理
     */

    private static PlazaApiService mApiService;
    //单例
    public static PlazaApiService getApiService(){
        if(mApiService== null){
            Retrofit retrofit = RetrofitProvider.provide();
           mApiService = retrofit.create(PlazaApiService.class);


        }
        return mApiService;

    }
}
