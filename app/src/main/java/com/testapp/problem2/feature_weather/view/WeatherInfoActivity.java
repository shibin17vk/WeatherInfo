package com.testapp.problem2.feature_weather.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.testapp.problem2.R;
import com.testapp.problem2.feature_weather.adapter.LocationSuggestionAdapter;
import com.testapp.problem2.feature_weather.callback.LocationInfoCallback;
import com.testapp.problem2.feature_weather.callback.WeatherInfoCallback;
import com.testapp.problem2.feature_weather.model.Weather;
import com.testapp.problem2.feature_weather.presenter.GetWeatherInfoPresenter;
import com.testapp.problem2.feature_weather.presenter.LocationSelectorPresenter;
import com.testapp.problem2.utils.AppException;
import com.testapp.problem2.utils.Constants;

import com.google.android.gms.location.places.Place;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author shibin
 * @version 1.0
 * @date 31/03/18
 */

public class WeatherInfoActivity extends AppCompatActivity implements LocationInfoCallback, WeatherInfoCallback {

    private LocationSelectorPresenter mLocationSelectorPresenter;
    private GetWeatherInfoPresenter mGetWeatherInfoPresenter;
    private ProgressDialog progressDialog;

    @BindView(R.id.autocomplete_places)
    protected AutoCompleteTextView mAutocompleteView;

    @BindView(R.id.txtMaxTemp)
    protected TextView txtMaxTemp;

    @BindView(R.id.txthumidity)
    protected TextView txthumidity;

    private LocationSuggestionAdapter mPlaceArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);
        ButterKnife.bind(this);
        progressDialog =  new ProgressDialog(this);
        mLocationSelectorPresenter = new LocationSelectorPresenter(getApplicationContext());
        mGetWeatherInfoPresenter = new GetWeatherInfoPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocationSelectorPresenter.registerCallback(this);
        mGetWeatherInfoPresenter.registerCallback(this);
        mLocationSelectorPresenter.registerLocationSuggestionService(mAutocompleteView, getApplicationContext());
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationSelectorPresenter.unRegisterCallback();
        mLocationSelectorPresenter.unRegisterLocationSuggestionService();
    }

    @Override
    public void onCitySelected(final Place place) {
        mGetWeatherInfoPresenter.getWeather(place);
    }

    @Override
    public void onLocationSuggestionError(final String error) {
        Toast.makeText(getApplicationContext(), error , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestWeather(final Weather weather) {
        txthumidity.setText(String.valueOf(weather.getHumidity()));
        txtMaxTemp.setText(String.valueOf((int)weather.getTempMax()) + " \u2103");
    }

    @Override
    public void onRequestWeatherError(final AppException er) {
        Toast.makeText(getApplicationContext(), er.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestWeatherRequestState(final int requestState) {
        if(requestState == Constants.REQ_STATE_IN_PROGRESS) {
            progressDialog.show();
        } else if(requestState == Constants.REQ_STATE_IN_FAILURE || requestState == Constants.REQ_STATE_IN_SUCCESS) {
            progressDialog.dismiss();
        }
    }
}
