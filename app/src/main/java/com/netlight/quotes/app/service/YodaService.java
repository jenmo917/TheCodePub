package com.netlight.quotes.app.service;

import android.support.annotation.NonNull;

import com.netlight.quotes.app.service.webservice.YodaApi;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import retrofit.Callback;
import retrofit.Converter;
import retrofit.Retrofit;

public class YodaService extends AbstractBaseService<YodaApi> {

    public YodaService() {
        super(YodaApi.class);
    }

    @Override
    protected void initRestAdapter() {
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(createInterceptor());
        Retrofit restAdapter = new Retrofit.Builder()
                .client(client)
                .baseUrl(getBaseUrl())
                .addConverter(String.class, new StringConverter())
                .build();
        api = restAdapter.create(typeParameterClass);
    }

    @NonNull
    protected String getBaseUrl() {
        return "https://yoda.p.mashape.com/";
    }

    public void yodafy(String sentence, Callback<String> callback) {
        api.yodafy(sentence).enqueue(callback);
    }

    public class StringConverter implements Converter {

        @Override
        public Object fromBody(ResponseBody body) throws IOException {
            return body.string();
        }

        @Override
        public RequestBody toBody(Object value) {
            return null;
        }
    }
}