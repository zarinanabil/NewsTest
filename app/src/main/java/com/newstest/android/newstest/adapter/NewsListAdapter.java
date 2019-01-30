package com.newstest.android.newstest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newstest.android.newstest.R;
import com.newstest.android.newstest.data.network.entity.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {

    private List<Article> newsList;
    private OnNewsItemClickListener eventListener;
    private Picasso picasso;
    private Context context;

    class NewsViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.text_view_title)
        TextView textViewTitle;
        @BindView(R.id.image_view_icon)
        ImageView imageViewIcon;
        @BindView(R.id.card_view)
        CardView cardView;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (eventListener != null && position != RecyclerView.NO_POSITION) {
                    eventListener.onItemClicked(newsList.get(position));
                }
            });

        }
    }

    public NewsListAdapter(Picasso picasso, Context context) {
        this.picasso = picasso;
        this.context = context;
    }

    public void displayDataList(List<Article> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    public void setmItemMarkListener(OnNewsItemClickListener listener) {
        this.eventListener = listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        return new NewsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder holder, final int listPosition) {

        DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics();
        int height = displaymetrics.heightPixels;
        android.widget.LinearLayout.LayoutParams mLayoutParams =
                ((LinearLayout.LayoutParams)holder.cardView.getLayoutParams());

        //for adjusting list item height based on screen length
        mLayoutParams.height=(int)(18 * height / 100);

        Article article = newsList.get(listPosition);
        holder.textViewTitle.setText(article.getTitle());

        Picasso.with(holder.imageViewIcon.getContext())
                .load(article.getUrlToImage())
                .into(holder.imageViewIcon);
    }

    @Override
    public int getItemCount() {
        return newsList == null ? 0 : newsList.size();
    }

    public interface OnNewsItemClickListener {

        void onItemClicked(Article article);
    }

}
