package com.newstest.android.newstest;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.newstest.android.newstest.di.AppComponent;
import com.newstest.android.newstest.di.AppModule;
import com.newstest.android.newstest.di.DaggerAppComponent;
import com.newstest.android.newstest.di.UtilsModule;

public class MyApplication extends Application {
    AppComponent appComponent;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).utilsModule(new UtilsModule()).build();

    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    public static MyApplication get(Activity activity){
        return (MyApplication) activity.getApplication();
    }
}
