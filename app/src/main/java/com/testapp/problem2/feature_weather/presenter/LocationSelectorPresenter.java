package com.testapp.problem2.feature_weather.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.testapp.problem2.feature_weather.adapter.LocationSuggestionAdapter;
import com.testapp.problem2.feature_weather.callback.LocationInfoCallback;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;

/**
 * @author shibin
 * @version 1.0
 * @date 30/03/18
 */

public class LocationSelectorPresenter extends BasePresenter<LocationInfoCallback> implements GoogleApiClient
        .ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener ,AdapterView.OnItemClickListener{

    public GoogleApiClient mGoogleApiClient;
    private LocationSuggestionAdapter mLocationSuggestionAdapter;
    private AutoCompleteTextView mAutoCompleteTextView;

    public LocationSelectorPresenter(Context context) {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(LocationServices.API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
    }

    private void initAdapter(AutoCompleteTextView autoCompleteTextView, Context context) {
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder().
                setTypeFilter(Place.TYPE_CITY_HALL).build();
        mLocationSuggestionAdapter = new LocationSuggestionAdapter(
                context,
                android.R.layout.simple_list_item_1,
                null,
                typeFilter);
        mLocationSuggestionAdapter.setGoogleApiClient(mGoogleApiClient);
        if(autoCompleteTextView != null) {
            autoCompleteTextView.setOnItemClickListener(this);
            autoCompleteTextView.setAdapter(mLocationSuggestionAdapter);
        }
    }

    public void registerLocationSuggestionService(AutoCompleteTextView autoCompleteTextView, Context context) {
        if (!mGoogleApiClient.isConnected() && !mGoogleApiClient.isConnecting()) {
            mGoogleApiClient.connect();
        }
        initAdapter(autoCompleteTextView,context);
    }

    public void unRegisterLocationSuggestionService() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        mLocationSuggestionAdapter = null;
    }

    @Override
    public void registerCallback(final LocationInfoCallback callback) {
        super.callBack = callback;
    }

    @Override
    public void unRegisterCallback() {
        super.callBack = null;
    }

    @Override
    public void onConnected(@Nullable final Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(final int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull final ConnectionResult connectionResult) {
        callBack.onLocationSuggestionError(connectionResult.getErrorMessage());
    }

    @Override
    public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, final long l) {
        final LocationSuggestionAdapter.PlaceAutocomplete item = mLocationSuggestionAdapter.getItem(position);
        final String placeId = String.valueOf(item.placeId);
        PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                .getPlaceById(mGoogleApiClient, placeId);
        placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
    }

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                String error = "Place query did not complete. Error: " + places.getStatus().toString();
                callBack.onLocationSuggestionError(error);
                return;
            }
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();
            callBack.onCitySelected(place);
        }
    };
}
