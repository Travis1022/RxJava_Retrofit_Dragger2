package com.rxandroid.network;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * 设置网络请求的url
 * Created by Travis1022 on 2017/2/8.
 */
public interface GitHubService {
    String ENDPOINT = "https://api.github.com";

    // 获取个人信息
    @GET("/users/{user}")
    Observable<GitHubUser> getUserData(@Path("user") String user);

}
