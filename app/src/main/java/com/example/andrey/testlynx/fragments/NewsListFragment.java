package com.example.andrey.testlynx.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.andrey.testlynx.MainActivity;
import com.example.andrey.testlynx.adapters.NewsListAdapter;
import com.example.andrey.testlynx.NewsListRequest;
import com.example.andrey.testlynx.R;
import com.example.andrey.testlynx.model.Event;
import com.example.andrey.testlynx.model.Events;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsListFragment extends Fragment {

    private NewsListRequest newsListRequest;
    private List<Event> eventList;
    private String category;

    public static final String KEY_CATEGORY = "CATEGORY";

    public NewsListFragment() {
        // Required empty public constructor
    }


    public static NewsListFragment newInstance(String category) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_CATEGORY, category);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // если во фрагменте проинициализирован eventList, то не будем загружать список по api
        if (eventList != null) return;

        category = getArguments().getString(KEY_CATEGORY);
        if (category == null) {
            Toast.makeText(getContext(), "Ошибка! Попытка загрузить список новостей без категории.", Toast.LENGTH_SHORT).show();
            return;
        }
        newsListRequest = ((MainActivity) getActivity()).getRetrofit().create(NewsListRequest.class); //Создаем объект, при помощи которого будем выполнять запросы
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // инициализируем Recycler существующим списком или загружаем список по API
        if (eventList != null) {
            initRecycler(eventList);
        } else if (category != null) {
            requestNewList(category);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void initRecycler(List<Event> eventList) {
        RecyclerView recyclerView = getView().findViewById(R.id.recycler);

        // Будем работать со встроенным менеджером
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Установим адаптер
        NewsListAdapter adapter = new NewsListAdapter(eventList);
        adapter.setOnItemClickListener(new NewsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String article) {
                ((MainActivity) getActivity()).startNewsDetailFragment(article);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    // отправляем запрос к API и обрабатываем ответ
    public void requestNewList(String category){
        Toast.makeText(getContext(), "Загрузка данных...", Toast.LENGTH_SHORT).show();
        newsListRequest.loadNewsList(category)
            .enqueue(new Callback<Events>() {
                @Override
                public void onResponse(Call<Events> call, Response<Events> response) {
                    if (response.body() != null) {
                        eventList = response.body().getEvents();
                        initRecycler(eventList);
                    }
                }

                @Override
                public void onFailure(Call<Events> call, Throwable t) {
                    Toast.makeText(getContext(), "Ошибка получения списка новостей!", Toast.LENGTH_SHORT).show();
                }
            });
    }

}
