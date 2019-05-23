package com.example.demoretrofit;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1234;
    private TextView weatherResponseText;
    private EditText cityNameEditText;
    private Button showWeatherButton;
    private Button requestPermissionsButton;
    private WeatherService gitHubService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherResponseText = (TextView) findViewById(R.id.weather_response_text);
        cityNameEditText = (EditText) findViewById(R.id.city_name_edit_text);
        showWeatherButton = (Button) findViewById(R.id.show_weather_button);
        requestPermissionsButton = (Button) findViewById(R.id.request_permissions_button);
        buildRetrofit();
        setListeners();
    }

    private void setListeners() {
        showWeatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadWeather();
            }
        });

        requestPermissionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestReadContactPermisions();
            }
        });
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

    private void requestReadContactPermisions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);

        } else {
            Toast.makeText(this, "Persmision already axist", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Persmision just GRANTED", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Persmision just DENIED", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
