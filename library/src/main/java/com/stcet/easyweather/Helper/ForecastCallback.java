package com.stcet.easyweather.Helper;

import com.stcet.easyweather.retrofit.models.ForecastResponseModel;

public abstract class ForecastCallback {

    public abstract void success(ForecastResponseModel response);

    public abstract void failure(String message);
}
