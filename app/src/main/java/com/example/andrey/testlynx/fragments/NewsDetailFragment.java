package com.example.andrey.testlynx.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrey.testlynx.adapters.ArticleListAdapter;
import com.example.andrey.testlynx.MainActivity;
import com.example.andrey.testlynx.NewsDetailRequest;
import com.example.andrey.testlynx.R;
import com.example.andrey.testlynx.model.Article;
import com.example.andrey.testlynx.model.NewsDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDetailFragment extends Fragment {

    private NewsDetailRequest newsDetailRequest;
    private String article;
    private TextView title;
    private TextView time;
    private TextView tournament;
    private TextView place;
    private RecyclerView articles;
    private TextView prediction;
    private NewsDetail newsDetail;

    public static final String KEY_ARTICLE = "ARTICLE";

    public NewsDetailFragment() {
        // Required empty public constructor
    }

    public static NewsDetailFragment newInstance(String article) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ARTICLE, article);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        article = getArguments().getString(KEY_ARTICLE);
        if (article == null) {
            Toast.makeText(getContext(), "Ошибка! Попытка загрузить неизвестную статью.", Toast.LENGTH_SHORT).show();
            return;
        }
        newsDetailRequest = ((MainActivity) getActivity()).getRetrofit().create(NewsDetailRequest.class); //Создаем объект, при помощи которого будем выполнять запросы
        requestNewsDetail(article);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = view.findViewById(R.id.news_detail_title);
        time = view.findViewById(R.id.news_detail_time);
        tournament = view.findViewById(R.id.news_detail_tournament);
        place = view.findViewById(R.id.news_detail_place);
        articles = view.findViewById(R.id.news_detail_article_list);
        prediction = view.findViewById(R.id.news_detail_prediction);
        prediction.setMovementMethod(new ScrollingMovementMethod());
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    // отправляем запрос к API и обрабатываем ответ
    private void requestNewsDetail(String article){
        Toast.makeText(getContext(), "Загрузка данных...", Toast.LENGTH_SHORT).show();
        newsDetailRequest.loadNewsDetail(article)
            .enqueue(new Callback<NewsDetail>() {
                @Override
                public void onResponse(Call<NewsDetail> call, Response<NewsDetail> response) {
                    if (response.body() != null) {
                        newsDetail = response.body();
                        initLayout();
                    }
                }

                @Override
                public void onFailure(Call<NewsDetail> call, Throwable t) {
                    Toast.makeText(getContext(), "Ошибка получения детальных данных статьи!", Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void initLayout() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(newsDetail.getTeam1());
        stringBuilder.append(" - ");
        stringBuilder.append(newsDetail.getTeam2());

        title.setText(stringBuilder);
        stringBuilder.setLength(0);

        if (newsDetail.getTime() != null) {
            time.setText(stringBuilder.append("Время: ").append(newsDetail.getTime()));
            stringBuilder.setLength(0);
        }

        if (newsDetail.getTournament() != null) {
            tournament.setText(stringBuilder.append("Турнир: ").append(newsDetail.getTournament()));
            stringBuilder.setLength(0);
        }

        if (newsDetail.getPlace() != null) {
            place.setText(stringBuilder.append("Место: ").append(newsDetail.getPlace()));
            stringBuilder.setLength(0);
        }

        ((TextView)getView().findViewById(R.id.prediction_title)).setText("Прогноз: ");
        prediction.setText(newsDetail.getPrediction());

        List<Article> articleList = newsDetail.getArticle();
        if (articleList.size() > 0) {
            RecyclerView recyclerView = getView().findViewById(R.id.news_detail_article_list);

            // Будем работать со встроенным менеджером
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

            // Установим адаптер
            ArticleListAdapter adapter = new ArticleListAdapter(articleList);
            recyclerView.setAdapter(adapter);
        }
    }
}
