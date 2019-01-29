package com.newstest.android.newstest.data.network;


import com.newstest.android.newstest.data.network.entity.Article;
import com.newstest.android.newstest.utils.Urls;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;


public interface ApiCallInterface {

    @GET(Urls.NEWSLIST)
    Observable<List<Article>> getNewsList();
}
