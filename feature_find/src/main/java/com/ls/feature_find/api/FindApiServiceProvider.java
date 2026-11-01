package com.ls.feature_find.api;

import com.ls.network.RetrofitProvider;

import retrofit2.Retrofit;

public class FindApiServiceProvider {
    /**
     * plaza模块中的PlazaApiService统一在这里获取，已变统一管理
     */

    private static FindApiService mApiService;
    //单例
    public static FindApiService getApiService(){
        if(mApiService== null){
            Retrofit retrofit = RetrofitProvider.provide();
           mApiService = retrofit.create(FindApiService.class);


        }
        return mApiService;

    }
}
