package com.example.andrey.testlynx.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrey.testlynx.R;
import com.example.andrey.testlynx.model.Article;

import java.util.List;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ViewHolder> {

    private List<Article> articleList;

    public static class ViewHolder extends RecyclerView . ViewHolder {

        private CardView cardView;
        private TextView header;
        private TextView text;

        public ViewHolder (View v) {
            super (v);
            cardView = v.findViewById(R.id.article_list_card);
            header = v.findViewById(R.id.article_header);
            text = v.findViewById(R.id.article_text);
        }

    }

    // Передаем в конструктор источник данных
    // В нашем случае это список
    public ArticleListAdapter(List<Article> articleList) {
        this.articleList = articleList;
    }

    // Создать новый элемент пользовательского интерфейса
    @Override
    public ArticleListAdapter.ViewHolder onCreateViewHolder (ViewGroup parent,
                                                             int viewType) {
        // Создаем новый элемент пользовательского интерфейса
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_article_list, parent, false );

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Заменить данные в пользовательском интерфейсе
    @Override
    public void onBindViewHolder (ViewHolder holder, int position) {
        // Получить элемент из источника данных (БД, интернет...)
        // Вынести на экран используя ViewHolder
        Article article = articleList.get(position);
        holder.header.setText(article.getHeader());
        holder.text.setText(article.getText());
    }

    @Override
    public int getItemCount () {
        return articleList.size();
    }
}