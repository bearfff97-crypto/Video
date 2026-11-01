package com.ls.network;
import com.ls.network.OkhttpClientProvider;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider {

    private  static  final String BASE_URL="https://titok.fzqq.fun/";
    private  static Retrofit mRetrofit;

    /**
     * 单例设计模式
     *
     * @return
     */
    public static Retrofit provide(){
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(OkhttpClientProvider.provide())
                    .addConverterFactory(GsonConverterFactory.create())// 配置 Gson 转换器
                    .build();

        }

        return mRetrofit;
    }
}
