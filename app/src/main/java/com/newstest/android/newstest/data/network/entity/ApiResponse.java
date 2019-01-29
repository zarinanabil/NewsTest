package com.newstest.android.newstest.data.network.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.newstest.android.newstest.utils.Status;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static com.newstest.android.newstest.utils.Status.ERROR;
import static com.newstest.android.newstest.utils.Status.LOADING;
import static com.newstest.android.newstest.utils.Status.SUCCESS;


public class ApiResponse {

 /*   @SerializedName("status")
    @Expose
    public String status;*/
    @SerializedName("totalResults")
    @Expose
    private Integer totalResults;
    @SerializedName("articles")
    @Expose
    public List<Article> articles = null;

    public final Status status;

    /*@Nullable
    public final List<Article> data;
*/
    @Nullable
    public final Throwable error;

    private ApiResponse(Status status, @Nullable List<Article> articles, @Nullable Throwable error) {
        this.status=status;
        //this.status = status;
        this.articles = articles;
        this.error = error;
    }


    /*public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }*/

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;

    }


    public static ApiResponse loading() {
        return new ApiResponse(LOADING,null, null);
    }

    public static ApiResponse success(@NonNull ApiResponse apiResponse) {
        //return new ApiResponse(SUCCESS, articles, null);
        return new ApiResponse(SUCCESS, apiResponse.getArticles(), null);
    }

    public static ApiResponse error(@NonNull Throwable error) {
        return new ApiResponse(ERROR, null, error);
    }


}