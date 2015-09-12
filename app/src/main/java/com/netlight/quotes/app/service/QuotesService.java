package com.netlight.quotes.app.service;

import android.support.annotation.NonNull;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netlight.quotes.app.model.dto.QuoteDto;
import com.netlight.quotes.app.service.webservice.QuotesApi;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import retrofit.Callback;
import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;

public class QuotesService {

    private QuotesApi quotesApi;

    public QuotesService() {
        initRestAdapter();
    }

    private void initRestAdapter() {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();
        Interceptor interceptor = createInterceptor();
        client.networkInterceptors().add(interceptor);
        Retrofit restAdapter = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://andruxnet-random-famous-quotes.p.mashape.com/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();
        quotesApi = restAdapter.create(QuotesApi.class);
    }

    @NonNull
    private Interceptor createInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Log.d(getClass().getCanonicalName(), "Retrofit request: " + request.url());
                Response response = chain.proceed(request);
                Log.d(getClass().getCanonicalName(), "Retrofit response: " + response.body());
                return response;
            }
        };
    }

    public void getQuote(String category, Callback<QuoteDto> callback) {
        quotesApi.getQuote(category).enqueue(callback);
    }
}