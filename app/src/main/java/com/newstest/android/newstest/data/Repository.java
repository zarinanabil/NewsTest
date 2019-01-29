package com.newstest.android.newstest.data;

import com.newstest.android.newstest.data.network.ApiCallInterface;
import com.newstest.android.newstest.data.network.entity.ApiResponse;
import com.newstest.android.newstest.data.network.entity.Article;

import java.util.List;

import io.reactivex.Observable;


public class Repository {

    private ApiCallInterface apiCallInterface;

    public Repository(ApiCallInterface apiCallInterface) {
        this.apiCallInterface = apiCallInterface;
    }

    /*
     * method to call news api
     * */
    public Observable <ApiResponse> getNewsList() {
        return apiCallInterface.getNewsList();
    }

}
