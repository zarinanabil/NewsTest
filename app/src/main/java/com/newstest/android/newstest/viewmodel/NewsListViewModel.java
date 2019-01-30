package com.newstest.android.newstest.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.newstest.android.newstest.data.Repository;
import com.newstest.android.newstest.data.network.entity.ApiResponse;
import com.newstest.android.newstest.data.network.entity.Article;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NewsListViewModel extends ViewModel {

    private Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Article>> newsList = new MutableLiveData<>();
    private MutableLiveData <Article> newsArticle = new MutableLiveData<>();

    public NewsListViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ApiResponse> getResponse() {
        return responseLiveData;
    }

    /*
     * method to call  newslist api
     * */
    public void getNewsList() {

        disposables.add(repository.getNewsList()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result)),
                        throwable -> responseLiveData.setValue(ApiResponse.error(throwable))
                ));

    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }


    public MutableLiveData<List<Article>> getNewsLiveDataList() {
        return newsList;
    }

    public void setNewsLiveDataArticle(Article newsarticle) {
        newsArticle.setValue(newsarticle);
    }

    public MutableLiveData <Article> getNewsLiveDataArticle() {
        return newsArticle;
    }

}
