package com.newstest.android.newstest.view;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.newstest.android.newstest.MyApplication;
import com.newstest.android.newstest.R;
import com.newstest.android.newstest.adapter.NewsListAdapter;
import com.newstest.android.newstest.data.network.entity.ApiResponse;
import com.newstest.android.newstest.data.network.entity.Article;
import com.newstest.android.newstest.di.DaggerMainActivityComponent;
import com.newstest.android.newstest.di.MainActivityComponent;
import com.newstest.android.newstest.di.MainActivityModule;
import com.newstest.android.newstest.utils.Constant;
import com.newstest.android.newstest.viewmodel.NewsListViewModel;
import com.newstest.android.newstest.viewmodel.ViewModelFactory;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NewsListAdapter.OnNewsItemClickListener {

    @BindView(R.id.my_recycler_view)
    RecyclerView recyclerView;


    @BindView(R.id.pull_to_refresh)
    SwipeRefreshLayout pullToRefresh;

    @BindView(R.id.detail_fragment_container)
    FrameLayout detailFrameLayout;

    @Inject
    LinearLayoutManager layoutManager;

    @Inject
    NewsListAdapter newsListAdapter;

    @Inject
    DetailFragment detailFragment;

    ViewModelFactory viewModelFactory;

    Picasso picasso;

    NewsListViewModel newsviewModel;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initviews();
    }

    private void initviews() {

        ButterKnife.bind(this);

        progressDialog = Constant.getProgressDialog(this, R.string.please_wait);

        viewModelFactory = (ViewModelFactory) ((MyApplication) getApplication()).getAppComponent().getViewModelFactory();

        picasso = (Picasso) ((MyApplication) getApplication()).getAppComponent().getPicasso();

        MainActivityComponent mainActivityComponent = DaggerMainActivityComponent.builder()
                .mainActivityModule(new MainActivityModule(this)).appComponent(MyApplication.get(this).getAppComponent()).build();

        mainActivityComponent.doInjection(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        newsviewModel = ViewModelProviders.of(this, viewModelFactory).get(NewsListViewModel.class);

        newsviewModel.getResponse().observe(this, this::consumeResponse);

        newsviewModel.getNewsLiveDataList().observe(this, this::showNewsList);

        if(Constant.checkInternetConnection(this)) {
            newsviewModel.getNewsList();
        }
        else
        {
            Constant.showToast(this,R.string.network_error);
        }
        pullToRefresh.setOnRefreshListener(() -> {
            if(Constant.checkInternetConnection(this)) {
                newsviewModel.getNewsList();
            }
            else
            {
                Constant.showToast(this,R.string.network_error);
            }
            pullToRefresh.setRefreshing(false);

        });

        recyclerView.setAdapter(newsListAdapter);

        newsListAdapter.setmItemMarkListener(this);

    }

    private void showNewsList(List<Article> article) {
        newsListAdapter.displayDataList(article);
    }

    /*
     * method to handle response
     * */
    private void consumeResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {

            case LOADING:
                progressDialog.show();
                break;

            case SUCCESS:
                progressDialog.dismiss();
                renderSuccessResponse(apiResponse.articles);
                break;

            case ERROR:
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, getResources().getString(R.string.error_string), Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

        /*
         * method to handle success response
         * */
        private void renderSuccessResponse(List<Article> response)
        {
            if (!(response == null || response.isEmpty())) {
                Log.d("response=", response.toString());
                newsviewModel.getNewsLiveDataList().setValue(response);

            } else {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.error_string), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onItemClicked (Article article){

            if(Constant.checkInternetConnection(this))
            {
                newsviewModel.setNewsLiveDataArticle(article);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_fragment_container, detailFragment)
                        .commit();

                showDetailFragment();
            }
            else
            {
                Constant.showToast(this,R.string.network_error);
            }
        }

        private void showDetailFragment()
        {
            detailFrameLayout.setVisibility(View.VISIBLE);
            pullToRefresh.setVisibility(View.GONE);
        }

        protected void hideDetailFragment()
        {
            pullToRefresh.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction().remove(detailFragment).commit();
            detailFrameLayout.setVisibility(View.GONE);
        }


    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getFragments().size();

        if (count == 0) {
            super.onBackPressed();

        } else {
            hideDetailFragment();
        }

    }

    }
