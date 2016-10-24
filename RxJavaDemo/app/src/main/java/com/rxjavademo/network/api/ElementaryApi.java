package com.rxjavademo.network.api;

import com.rxjavademo.model.ElementaryImage;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Travis1022 on 2016/10/24.
 */

/**
 * 使用Retrofit + RxJava 写的接口
 */
public interface ElementaryApi {
    @GET("search")
    Observable<List<ElementaryImage>> search(@Query("q") String query);
}
