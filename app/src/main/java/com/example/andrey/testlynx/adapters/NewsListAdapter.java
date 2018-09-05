package com.example.andrey.testlynx.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrey.testlynx.R;
import com.example.andrey.testlynx.model.Event;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private List<Event> eventList;
    private OnItemClickListener itemClickListener;


    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public TextView title;
        public TextView coefficient;
        public TextView time;
        public TextView place;
        public TextView preview;
        public Event event;

        public ViewHolder (View v) {
            super(v);
            cardView = v.findViewById(R.id.news_list_card);
            title = v.findViewById(R.id.event_title);
            coefficient = v.findViewById(R.id.event_coefficient);
            time = v.findViewById(R.id.event_time);
            place = v.findViewById(R.id.event_place);
            preview = v.findViewById(R.id.event_preview);

            // обработка нажатия на CardView
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(event.getArticle());
                    } else {
                        Toast.makeText(view.getContext(), "Ошибка загрузки данных!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        public void setEvent(Event event) {
            this.event = event;
        }

    }

    // Передаем в конструктор источник данных
    public NewsListAdapter (List<Event> eventList) {
        this.eventList = eventList;
    }

    // Создать новый элемент пользовательского интерфейса
    @Override
    public NewsListAdapter.ViewHolder onCreateViewHolder (ViewGroup parent,
                                                        int viewType) {
        // Создаем новый элемент пользовательского интерфейса
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler, parent, false );

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Заменить данные в пользовательском интерфейсе
    @Override
    public void onBindViewHolder (ViewHolder holder, int position) {
        // Получить элемент из источника данных (БД, интернет...)
        // Вынести на экран используя ViewHolder
        Event event = eventList.get(position);
        holder.setEvent(event);
        holder.title.setText(event.getTitle());
        holder.coefficient.setText(event.getCoefficient());
        holder.time.setText(event.getTime());
        holder.place.setText(event.getPlace());
        holder.preview.setText(event.getPreview());
    }

    @Override
    public int getItemCount () {
        return eventList.size();
    }

    // установка слушателя
    public void setOnItemClickListener(OnItemClickListener onMenuItemClickListener){
        this.itemClickListener = onMenuItemClickListener;
    }

    // интерфейс для обработки нажатия на элемент списка
    public interface OnItemClickListener {
        void onItemClick(String article);
    }
}