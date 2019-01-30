package com.newstest.android.newstest.view;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
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

import java.util.Objects;

public class DetailFragment extends Fragment {

    Button buttonBack;
    ImageView imageViewNews;
    TextView textViewTitle,textViewPublishDate,textViewDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initListeners();
        setData();
    }

    private void setData() {

        MainActivity context = ((MainActivity)Objects.requireNonNull(getActivity()));
        Article article = context.newsviewModel.getNewsLiveDataArticle().getValue();

        textViewTitle.setText(article.getTitle());
        textViewPublishDate.setText(article.getPublishedAt());
        textViewDescription.setText(article.getDescription());
        ((MainActivity)Objects.requireNonNull(getActivity())).picasso.with(context)
                .load(article.getUrlToImage())
                .into(imageViewNews);

    }

    private void initListeners() {

        buttonBack.setOnClickListener(v -> onDetailBackPressed());

    }

    private void initView(View view) {

        buttonBack = view.findViewById(R.id.button_back);
        imageViewNews=view.findViewById(R.id.news_imageview);
        textViewTitle=view.findViewById(R.id.news_title);
        textViewDescription=view.findViewById(R.id.news_description);
        textViewPublishDate=view.findViewById(R.id.news_publish_date);
    }

    private void onDetailBackPressed()
    {
        ((MainActivity)getActivity()).hideDetailFragment();
    }

}
