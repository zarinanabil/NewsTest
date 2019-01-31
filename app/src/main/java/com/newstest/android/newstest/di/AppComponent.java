package com.newstest.android.newstest.di;


import android.arch.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Component;


@Component(modules = {AppModule.class, UtilsModule.class, PicassoModule.class})
@Singleton
public interface AppComponent {

    ViewModelProvider.Factory getViewModelFactory();

    Picasso getPicasso();
}
