package com.ls.network;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.ls.network.bean.ResBase;
import com.ls.network.bean.ResList;
import com.ls.network.config.ErrorStatusConfig;


public class ApiCall {

    private static final String TAG="ApiCall";
    public interface ApiCallback<T>{
        void onSuccess(T result);
        void onError(int errorCode,String message);
    }

    public interface ApiListsCallback<T>{
        void onSuccess(ResList<T> result);
        void onError(int errorCode,String message);
    }


    /**
     * 发起异步请求，并且直接在这里处理服务端返回的状态
     * 如果成功，则通过onSuccess返回结果
     * 如果失败
     * @param call
     * @param callback
     * @param <T>
     */
    public static<T> void enqueue(Call<ResBase<T>> call,ApiCallback<ResBase<T>> callback){
        call.enqueue(new Callback<ResBase<T>>() {
            @Override
            public void onResponse(Call<ResBase<T>> call, Response<ResBase<T>> response) {
                if(response.isSuccessful()){



                    if(response.body()!=null && response.body().getCode()==1){
                   // if(response.code()==1&&response.body()!=null){
                        Log.i(TAG,"onResponse:通讯成功");
                        //请求成功，并且拿到数据
                        callback.onSuccess(response.body());
                    }else {
                        callback.onError(ErrorStatusConfig.ERROR_STATUS_SERVER_ERROR,response.body().getMsg());
                    }

                }else{
                    //与服务端通讯发生http请求错误，可以通过response.code（）获取错误404，400...：
                    callback.onError(ErrorStatusConfig.ERROR_STATUS_NETWORK_FIAL,"网络异常！请检查网络");
                }
            }

            @Override
            public void onFailure(Call<ResBase<T>> call, Throwable throwable) {
                callback.onError(ErrorStatusConfig.ERROR_STATUS_NETWORK_FIAL,"网络异常！请检查网络");
            }
        });

    }

    /**
     * 发起异步请求，并且直接在这里处理服务端返回的状态，
     * 如果成功，则通过onSuccess返回结果
     * 如果失败，则抛出错误
     *
     * @param call
     * @param callback
     * @param <T>
     */
    public static <T> void enqueueCommon(Call<T> call, ApiCallback<T> callback) {

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {

                if (response.isSuccessful()) {
                    //请求成功 并且拿到数据
                    callback.onSuccess(response.body());

                } else {
                    //与服务端通讯发生http请求错误，可以通过response.code()获取错误码:404 400 ....
                    callback.onError(ErrorStatusConfig.ERROR_STATUS_NETWORK_FIAL, "网络异常！请检查网络");
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable throwable) {
                callback.onError(ErrorStatusConfig.ERROR_STATUS_NETWORK_FIAL, "网络异常！请检查网络");
            }
        });
    }


    /**
     * 发起异步异步请求，获取列表型的变量，并且直接在这里处理服务端返回的状态
     * 如果成功，则通过onSuccess返回结果
     * 如果失败
     * @param call
     * @param callback
     * @param <T>
     */
    public static<T> void enqueueLists(Call<ResBase<ResList<T>>> call,ApiListsCallback callback){
        call.enqueue(new Callback<ResBase<ResList<T>>>() {
            @Override
            public void onResponse(Call<ResBase<ResList<T>>> call, Response<ResBase<ResList<T>>> response) {
                if(response.isSuccessful()){
                    if(response.body().getCode()==1&&response.body()!=null){
                        Log.i(TAG,"onResponse:通讯成功");
                        ResBase<ResList<T>> body=response.body();

                        if(body.getData()!=null&&body.getData().getList().size()>0){
                            //请求成功，并且拿到数据

                            callback.onSuccess(response.body().getData());
                        }else {
                            callback.onError(ErrorStatusConfig.ERROR_STATUS_EMPTY,"当前没有更多的数据");
                        }

                    }else {
                        callback.onError(ErrorStatusConfig.ERROR_STATUS_SERVER_ERROR,"服务器异常!");
                    }

                }else{
                    //与服务端通讯发生http请求错误，可以通过response.code（）获取错误404，400...：
                    callback.onError(ErrorStatusConfig.ERROR_STATUS_NETWORK_FIAL,"网络异常！请检查网络");
                }
            }

            @Override
            public void onFailure(Call<ResBase<ResList<T>>> call, Throwable throwable) {
                callback.onError(ErrorStatusConfig.ERROR_STATUS_NETWORK_FIAL,"网络异常！请检查网络");

            }
        });


    }

}
