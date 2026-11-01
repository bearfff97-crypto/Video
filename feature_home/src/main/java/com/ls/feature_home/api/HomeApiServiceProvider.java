package com.ls.feature_home.api;

import com.ls.network.RetrofitProvider;


import retrofit2.Retrofit;

public class HomeApiServiceProvider {

    private static HomeApiService mApiService;
    //单例
    public static HomeApiService getApiService(){
        if(mApiService== null){
            Retrofit retrofit = RetrofitProvider.provide();
           mApiService = retrofit.create(HomeApiService.class);


        }
        return mApiService;

    }
}
