package com.example.demoretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView weatherResponseText;
    private EditText cityNameEditText;
    private Button showWeatherButton;
    private WeatherService gitHubService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherResponseText = (TextView) findViewById(R.id.weather_response_text);
        cityNameEditText = (EditText) findViewById(R.id.city_name_edit_text);
        showWeatherButton = (Button) findViewById(R.id.show_weather_button);
        buildRetrofit();
        setListeners();
    }


    public void buildRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstans.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        gitHubService = retrofit.create(WeatherService.class);
    }

    private void loadWeather() {
        Call<WeatherResponse> call = gitHubService.getWeatherByCityName(ApiConstans.APP_KEY, cityNameEditText.getText().toString());

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                weatherResponseText.setText(response.body().main.toString());
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                weatherResponseText.setText("Error");
            }
        });
    }

    private void setListeners() {
        showWeatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadWeather();
            }
        });
    }

}
