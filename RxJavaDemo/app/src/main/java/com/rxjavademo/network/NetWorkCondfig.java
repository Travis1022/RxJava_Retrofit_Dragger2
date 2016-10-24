package com.rxjavademo.network;

import com.rxjavademo.network.api.ElementaryApi;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Travis1022 on 2016/10/24.
 */

public class NetWorkCondfig {
    private static ElementaryApi elementaryApi;
    private static OkHttpClient okHttpClient = new OkHttpClient();

    // Retrofit
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    public static ElementaryApi getElementayApi() {
        if (elementaryApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://www.zhuangbi.info/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            elementaryApi = retrofit.create(ElementaryApi.class);
        }
        return elementaryApi;
    }

}
