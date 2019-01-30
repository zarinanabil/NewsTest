package com.newstest.android.newstest.di;

import android.support.v7.widget.LinearLayoutManager;

import com.newstest.android.newstest.view.DetailFragment;
import com.newstest.android.newstest.view.MainActivity;
import com.newstest.android.newstest.adapter.NewsListAdapter;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    private MainActivity mainActivity;

    public MainActivityModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    @Provides
    @ActivityScope
    NewsListAdapter getNewsListAdapter(Picasso picasso) {

        return new NewsListAdapter(picasso,mainActivity);
    }

    @Provides
    @ActivityScope

    LinearLayoutManager getLinearLayoutManager(){

        return new LinearLayoutManager(mainActivity);
    }

    @Provides
    @ActivityScope
    DetailFragment getDetailFragment(){
        return new DetailFragment();
    }
}
