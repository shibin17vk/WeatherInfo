package com.testapp.problem2.webservice;

import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author shibin
 * @version 1.0
 * @date 30/03/18
 */

public class WebserviceClient {

    private Webservices mWebservices;
    private static WebserviceClient mWebserviceClient;

    private WebserviceClient() {

        HttpLoggingInterceptor.Level logLevel = HttpLoggingInterceptor.Level.BODY;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(logLevel);
        builder.interceptors().add(0, interceptor);


        OkHttpClient client = builder.connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebserviceUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        mWebservices = retrofit.create(Webservices.class);
    }

    public static WebserviceClient getInstance() {
        if(mWebserviceClient == null) {
            mWebserviceClient = new WebserviceClient();
        }
        return mWebserviceClient;
    }

    public Webservices getWebservices() {
        return mWebservices;
    }
}
