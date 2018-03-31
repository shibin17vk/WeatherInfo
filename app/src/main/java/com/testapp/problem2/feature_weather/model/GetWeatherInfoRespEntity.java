package com.testapp.problem2.feature_weather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author shibin
 * @version 1.0
 * @date 31/03/18
 */

public class GetWeatherInfoRespEntity {

    @SerializedName("cod")
    @Expose
    private int code;

    @SerializedName("main")
    @Expose
    private Weather weather;

    public int getCode() {
        return code;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(final Weather weather) {
        this.weather = weather;
    }
}
