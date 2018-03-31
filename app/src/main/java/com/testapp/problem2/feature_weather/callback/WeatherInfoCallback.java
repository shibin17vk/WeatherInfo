package com.testapp.problem2.feature_weather.callback;

import com.testapp.problem2.utils.AppException;
import com.testapp.problem2.feature_weather.model.Weather;

/**
 * @author shibin
 * @version 1.0
 * @date 30/03/18
 */

public interface WeatherInfoCallback {

    public void onRequestWeather(Weather weather);

    public void onRequestWeatherError(AppException er);

    public void onRequestWeatherRequestState(int requestState);

}
