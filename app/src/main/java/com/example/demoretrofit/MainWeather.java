package com.example.demoretrofit;

class MainWeather {

    private Double temp;
    private Double pressure;
    private Double humidity;
    private Double temp_min;
    private Double temp_max;

    @Override
    public String toString() {
        return "MainWeather{" +
                "temp=" + temp +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", temp_min=" + temp_min +
                ", temp_max=" + temp_max +
                '}';
    }
}
