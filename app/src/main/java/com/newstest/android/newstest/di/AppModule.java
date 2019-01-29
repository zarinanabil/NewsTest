package com.newstest.android.newstest.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @ApplicationContext
    @Provides
    @Singleton
    Context provideContext() {
        return context.getApplicationContext();
    }
}
