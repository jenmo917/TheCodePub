package com.netlight.quotes.app.service;

import android.support.annotation.NonNull;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netlight.quotes.app.service.webservice.QuotesApi;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;

public abstract class AbstractBaseService<T> {

    protected T api;
    final Class<T> typeParameterClass;

    public AbstractBaseService(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
        initRestAdapter();
    }

    protected void initRestAdapter() {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();
        Interceptor interceptor = createInterceptor();
        client.networkInterceptors().add(interceptor);
        Retrofit restAdapter = new Retrofit.Builder()
                .client(client)
                .baseUrl(getBaseUrl())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();
        api = restAdapter.create(typeParameterClass);
    }

    @NonNull
    protected abstract String getBaseUrl();

    @NonNull
    protected Interceptor createInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Log.d(getClass().getCanonicalName(), "Retrofit request: " + request.url());
                Response response = chain.proceed(request);
                Log.d(getClass().getCanonicalName(), "Retrofit response: " + response);
                return response;
            }
        };
    }
}