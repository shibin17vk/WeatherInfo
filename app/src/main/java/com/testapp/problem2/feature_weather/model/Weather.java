package com.testapp.problem2.feature_weather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author shibin
 * @version 1.0
 * @date 31/03/18
 */

public class Weather {

    @SerializedName("temp")
    @Expose
    private double temp;
    @SerializedName("pressure")
    @Expose
    private double pressure;
    @SerializedName("humidity")
    @Expose
    private double humidity;
    @SerializedName("temp_min")
    @Expose
    private double tempMin;
    @SerializedName("temp_max")
    @Expose
    private double tempMax;

    public double getTemp() {
        return temp;
    }

    public void setTemp(final double temp) {
        this.temp = temp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(final double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(final double humidity) {
        this.humidity = humidity;
    }

    public double getTempMin() {
        return tempMin - 273.15;
    }

    public void setTempMin(final double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax - 273.15;
    }

    public void setTempMax(final double tempMax) {
        this.tempMax = tempMax;
    }
}
