package com.newstest.android.newstest.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.newstest.android.newstest.R;
import com.newstest.android.newstest.data.network.entity.Article;
import com.newstest.android.newstest.utils.Constant;


import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment {

    @BindView(R.id.button_back)
    Button buttonBack;
    @BindView(R.id.button_continue)
    Button buttonContinueReading;
    @BindView(R.id.news_imageview)
    ImageView imageViewNews;
    @BindView(R.id.news_title)
    TextView textViewTitle;
    @BindView(R.id.news_publish_date)
    TextView textViewPublishDate;
    @BindView(R.id.news_description)
    TextView textViewDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
        setData();
    }


    public void onClickContinueReading () {
        if(Constant.checkInternetConnection(Objects.requireNonNull(getContext())))
        {
            Article article = getArticle();
            goToUrl(article.getUrl());
        }
        else
        {
            Constant.showToast(getContext(),R.string.network_error);
        }
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    @Nullable
    @Override
    public MainActivity getContext() {
        return (MainActivity) getActivity();
    }

    private Article getArticle(){

        return (Objects.requireNonNull(getContext()).newsviewModel.getNewsLiveDataArticle().getValue());

    }

    private void setData() {

        Article article = getArticle();

        textViewTitle.setText(article.getTitle());
        textViewPublishDate.setText(article.getPublishedAt());
        textViewDescription.setText(article.getDescription());
        (Objects.requireNonNull(getContext())).picasso.with(getContext())
                .load(article.getUrlToImage())
                .into(imageViewNews);

    }

    private void initListeners() {

        buttonBack.setOnClickListener(v -> onDetailBackPressed());
        buttonContinueReading.setOnClickListener(v -> onClickContinueReading());

    }

    private void onDetailBackPressed()
    {
        (Objects.requireNonNull(getContext())).hideDetailFragment();
    }

}
