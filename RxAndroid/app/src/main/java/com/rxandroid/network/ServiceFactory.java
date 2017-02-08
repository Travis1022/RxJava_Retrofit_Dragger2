package com.rxandroid.network;


import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * 工厂模式
 * Created by Travis1022 on 2017/2/8.
 */
public class ServiceFactory {
    public static <T> T createServiceFrom(final Class<T> serviceClass, String endPoint) {
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl(endPoint)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) //添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create())       //添加Gson转换器
                .build();
        return adapter.create(serviceClass);
    }
}
