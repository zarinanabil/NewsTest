package com.newstest.android.newstest.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newstest.android.newstest.R;
import com.newstest.android.newstest.data.network.entity.Article;
import com.newstest.android.newstest.utils.Urls;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {

    List<Article> newsList;
    private OnNewsItemClickListener eventListener;
    Picasso picasso;


    class NewsViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle;
        ImageView imageViewIcon;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            this.textViewTitle = (TextView) itemView.findViewById(R.id.textviewtitle);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageviewicon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (eventListener != null && position != RecyclerView.NO_POSITION) {
                        eventListener.onItemClicked(newsList.get(position));
                    }
                }
            });

        }
    }

    public NewsListAdapter(Picasso picasso) {
        this.picasso = picasso;
    }

    public void displayDataList(List<Article> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    public void setmItemMarkListener(OnNewsItemClickListener listener) {
        this.eventListener = listener;
    }

    public void refreshDataList(List<Article> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        return new NewsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, final int listPosition) {

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
