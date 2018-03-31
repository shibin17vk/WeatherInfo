package com.testapp.problem2.feature_weather.presenter;

import com.testapp.problem2.utils.AppException;
import com.testapp.problem2.feature_weather.callback.WeatherInfoCallback;
import com.testapp.problem2.feature_weather.model.GetWeatherInfoRespEntity;
import com.testapp.problem2.utils.Constants;
import com.testapp.problem2.utils.Utils;
import com.testapp.problem2.webservice.WebserviceClient;

import com.google.android.gms.location.places.Place;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author shibin
 * @version 1.0
 * @date 30/03/18
 */

public class GetWeatherInfoPresenter extends BasePresenter<WeatherInfoCallback> {

    @Override
    public void registerCallback(final WeatherInfoCallback callback) {
        super.callBack = callback;
    }

    @Override
    public void unRegisterCallback() {
        super.callBack = null;
    }

    public void getWeather(Place place) {
        WebserviceClient webserviceClient = WebserviceClient.getInstance();
        String token = Constants.WEATHER_ORG_APP_ID;
        Call<GetWeatherInfoRespEntity> serviceCall = webserviceClient.getWebservices()
                .getWeatherInfo(place.getLatLng().latitude, place.getLatLng().longitude,token);
        callBack.onRequestWeatherRequestState(Constants.REQ_STATE_IN_PROGRESS);
        serviceCall.enqueue(new Callback<GetWeatherInfoRespEntity>() {

            @Override
            public void onResponse(final Call<GetWeatherInfoRespEntity> call,
                    final Response<GetWeatherInfoRespEntity> response) {
                if(callBack != null) {
                    callBack.onRequestWeatherRequestState(Constants.REQ_STATE_IN_SUCCESS);
                    if(response != null && response.body() != null) {
                        if(response.body().getCode() == Constants.API_RESPONSE_SUCCESS) {
                            callBack.onRequestWeather(response.body().getWeather());
                        } else {
                            callBack.onRequestWeatherError(Utils.getException(response.body().getCode()));
                        }
                    } else {
                        callBack.onRequestWeatherError(Utils.getException(AppException.ERROR_SERVICE_NOT_AVAILABLE));
                    }
                }
            }

            @Override
            public void onFailure(final Call<GetWeatherInfoRespEntity> call, final Throwable t) {
                if(callBack != null) {
                    callBack.onRequestWeatherRequestState(Constants.REQ_STATE_IN_FAILURE);
                    callBack.onRequestWeatherError(Utils.getException(AppException.ERROR_SERVICE_NOT_AVAILABLE));
                }
            }
        });
    }

}
