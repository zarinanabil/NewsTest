package com.newstest.android.newstest.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.newstest.android.newstest.data.Repository;

import javax.inject.Inject;


public class ViewModelFactory implements ViewModelProvider.Factory {

    private Repository repository;

    @Inject
    public ViewModelFactory(Repository repository) {
        this.repository = repository;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NewsListViewModel.class)) {
            return (T) new NewsListViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
