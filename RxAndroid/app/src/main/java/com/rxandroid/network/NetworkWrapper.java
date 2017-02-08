package com.rxandroid.network;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Travis1022 on 2017/2/8.
 */
public class NetworkWrapper {
    private static final String[] nameString = {"Travis1022", "JakeWharton", "rock3r", "Takhion", "dextorer", "Mariuxtheone"};

    //获取用户信息
    public static void getUserInfo(final UserListAdapter adapter) {
        GitHubService gitHubService = ServiceFactory
                .createServiceFrom(GitHubService.class, GitHubService.ENDPOINT);

        Observable.from(nameString)
                .flatMap(new Func1<String, Observable<GitHubUser>>() {
                    @Override
                    public Observable<GitHubUser> call(String s) {
                        return gitHubService.getUserData(s);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<GitHubUser>() {
                    @Override
                    public void call(GitHubUser user) {
                        adapter.addUser(user);
                    }
                });
    }

    //获取库信息
    public static void getRepoData(final DetailListAdapter adapter, final String userName) {
        GitHubService gitHubService = ServiceFactory.createServiceFrom(GitHubService.class, GitHubService.ENDPOINT);

        gitHubService.getRepoData(userName)
                .flatMap(new Func1<GitHubRepo[], Observable<? extends GitHubRepo>>() {
                    @Override
                    public Observable<? extends GitHubRepo> call(GitHubRepo[] gitHubRepos) {
                        return Observable.from(gitHubRepos);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<GitHubRepo>() {
                    @Override
                    public void call(GitHubRepo gitHubRepo) {
                        adapter.addDetail(gitHubRepo);
                    }
                });

    }
}