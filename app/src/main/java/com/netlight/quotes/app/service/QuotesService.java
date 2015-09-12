package com.netlight.quotes.app.service;

import android.support.annotation.NonNull;

import com.netlight.quotes.app.model.dto.QuoteDto;
import com.netlight.quotes.app.service.webservice.QuotesApi;

import retrofit.Callback;

public class QuotesService extends AbstractBaseService<QuotesApi> {

    public QuotesService() {
        super(QuotesApi.class);
    }

    @NonNull
    protected String getBaseUrl() {
        return "https://andruxnet-random-famous-quotes.p.mashape.com/";
    }

    public void getQuote(String category, Callback<QuoteDto> callback) {
        api.getQuote(category).enqueue(callback);
    }
}