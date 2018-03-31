package com.testapp.problem2.webservice;

import com.testapp.problem2.feature_weather.model.GetWeatherInfoRespEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author shibin
 * @version 1.0
 * @date 31/03/18
 */

public interface Webservices {
    @GET(WebserviceUrl.SERVICE_GET_WEATHER )
    Call<GetWeatherInfoRespEntity> getWeatherInfo(@Query("lat") double lat, @Query("lon") double lon, @Query("appid") String appid);

}