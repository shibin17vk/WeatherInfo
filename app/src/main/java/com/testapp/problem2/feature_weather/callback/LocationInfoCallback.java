package com.testapp.problem2.feature_weather.callback;

import com.google.android.gms.location.places.Place;

/**
 * @author shibin
 * @version 1.0
 * @date 30/03/18
 */

public interface LocationInfoCallback {

    public void onCitySelected(Place place);

    public void onLocationSuggestionError(String error);

}
