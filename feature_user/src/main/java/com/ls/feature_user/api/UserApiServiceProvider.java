package com.ls.feature_user.api;

import com.ls.network.RetrofitProvider;

import retrofit2.Retrofit;

public class UserApiServiceProvider {
    /**
     * plaza模块中的PlazaApiService统一在这里获取，已变统一管理
     */

//    private static UserApiService mApiService;
    //单例
//    public static UserApiService getApiService(){
//        if(mApiService== null){
//            Retrofit retrofit = RetrofitProvider.provide();
//           mApiService = retrofit.create(UserApiService.class);
//
//
//        }
//        return mApiService;
//
//    }
    private static UserApiService mApiService;

    //单例
    public static UserApiService getApiService() {
        if (mApiService == null) {
            Retrofit retrofit = RetrofitProvider.provide();
            mApiService = retrofit.create(UserApiService.class);
        }
        return mApiService;
    }
}
