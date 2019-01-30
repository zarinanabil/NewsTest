package com.newstest.android.newstest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newstest.android.newstest.MyApplication;
import com.newstest.android.newstest.R;
import com.newstest.android.newstest.data.network.entity.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {

    List<Article> newsList;
    private OnNewsItemClickListener eventListener;
    Picasso picasso;
    DisplayMetrics displayMetrics;
    Context context;

    class NewsViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle;
        ImageView imageViewIcon;
        CardView cardView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            this.textViewTitle = (TextView) itemView.findViewById(R.id.text_view_title);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.image_view_icon);
            this.cardView = itemView.findViewById(R.id.card_view);

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
    public void onBindViewHolder(final NewsViewHolder holder, final int listPosition) {

        DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics();
        int height = displaymetrics.heightPixels;
        android.widget.LinearLayout.LayoutParams mLayoutParams =
                ((LinearLayout.LayoutParams)holder.cardView.getLayoutParams());

        //for adjusting list item height based on screen length
        mLayoutParams.height=(int)(18 * height / 100);

        Article article = newsList.get(listPosition);
        holder.textViewTitle.setText(article.getTitle());

        picasso.with(holder.imageViewIcon.getContext())
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
