package com.newstest.android.newstest.di;


import com.newstest.android.newstest.MainActivity;

import dagger.Component;

@Component(modules = MainActivityModule.class, dependencies = AppComponent.class)
@ActivityScope
public interface MainActivityComponent {

    void doInjection(MainActivity mainActivity);
}
