package com.geekbrains.weather.ui.weather;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.geekbrains.weather.R;
import com.geekbrains.weather.model.note.Note;
import com.geekbrains.weather.model.note.NoteDataReader;
import com.geekbrains.weather.model.note.NoteDataSource;
import com.geekbrains.weather.model.weather.WeatherDataReader;
import com.geekbrains.weather.model.weather.WeatherDataSource;
import com.geekbrains.weather.model.weather.WeatherModel;
import com.geekbrains.weather.ui.base.BaseFragment;
import com.geekbrains.weather.ui.plan.NoteAdapter;

public class AddWeatherItemFragment extends BaseFragment {

    private WeatherDataSource weatherDataSource;
    private WeatherDataReader weatherDataReader;
    private Button addItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weather_item_fragment, container, false);
    }

    @Override
    protected void initLayout(View view, Bundle savedInstanceState) {
        initDataSource();
        addItem = view.findViewById(R.id.btn_add_weather_item);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addElement();
            }
        });
    }

    private void initDataSource() {
        weatherDataSource = new WeatherDataSource(getActivity().getApplicationContext());
        weatherDataSource.open();
        weatherDataReader = weatherDataSource.getWeatherDataReader();
    }

    private void addElement() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View aletrView = inflater.inflate(R.layout.add_weather_item_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(aletrView);
        builder.setTitle("Добавить данные о погоде");
        builder.setNegativeButton(R.string.cancel, null);
        builder.setPositiveButton(R.string.menu_add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText editTextDatetime = aletrView.findViewById(R.id.et_weather_datetime);
                EditText editTextTemperature = aletrView.findViewById(R.id.et_weather_temperature);
                EditText editTextHumidity =  aletrView.findViewById(R.id.et_weather_humidity);
                EditText editTextPressure =  aletrView.findViewById(R.id.et_weather_pressure);

                WeatherModel newItem = weatherDataSource.addWeatherItem(editTextTemperature.getText().toString(),
                                                    editTextPressure.getText().toString(),
                                                    editTextHumidity.getText().toString(),
                                                    editTextDatetime.getText().toString());

                dataUpdate();
            }
        });
        builder.show();
    }

    private void dataUpdate() {
        weatherDataReader.Refresh();
    }

}
