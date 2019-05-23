package com.example.demoretrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET(ApiConstans.WEATHER)
    Call<WeatherResponse> getWeatherByCityName(@Query("APIKEY") String appKey,
                                               @Query("q") String cityName);
}
