package com.newstest.android.newstest;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements NewsListAdapter.OnNewsItemClickListener {


    private RecyclerView recyclerView;

    @Inject
    LinearLayoutManager layoutManager;

    @Inject
    NewsListAdapter newsListAdapter;

    ViewModelFactory viewModelFactory;

    Picasso picasso;

    NewsListViewModel viewModel;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initviews();
    }

    private void initviews() {

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        progressDialog = Constant.getProgressDialog(this, "Please wait...");

        viewModelFactory = (ViewModelFactory) ((MyApplication) getApplication()).getAppComponent().getViewModelFactory();

        picasso = (Picasso) ((MyApplication) getApplication()).getAppComponent().getPicasso();

        MainActivityComponent mainActivityComponent = DaggerMainActivityComponent.builder()
                .mainActivityModule(new MainActivityModule(this)).appComponent(MyApplication.get(this).getAppComponent()).build();

        mainActivityComponent.doInjection(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewsListViewModel.class);

        viewModel.getResponse().observe(this, this::consumeResponse);

        viewModel.getNewsLiveDataList().observe(this, this::showNewsList);

        viewModel.getNewsList();

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
                Toast.makeText(MainActivity.this, getResources().getString(R.string.errorString), Toast.LENGTH_SHORT).show();
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
                viewModel.getNewsLiveDataList().setValue(response);

            } else {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.errorString), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onItemClicked (Article article){
            Toast.makeText(MainActivity.this, article.getTitle(), Toast.LENGTH_LONG).show();

        }
    }
