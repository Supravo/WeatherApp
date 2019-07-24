package com.stcet.easyweather.Helper;

import com.stcet.easyweather.retrofit.models.WeatherResponseModel;

public abstract class WeatherCallback {

    public abstract void success(WeatherResponseModel response);

    public abstract void failure(String message);
}
