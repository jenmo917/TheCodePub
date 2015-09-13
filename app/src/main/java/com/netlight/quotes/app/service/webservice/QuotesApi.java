package com.netlight.quotes.app.service.webservice;

import com.netlight.quotes.app.model.dto.QuoteDto;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import retrofit.http.Query;

public interface QuotesApi {
    @Headers({
            "X-Mashape-Key : OVEPFZxY9ImshDy9tJFFZjHT6xR9p1XnoEejsnpkTn1rsmKPrH",
            "Accept: application/json"
    })
    @GET("quotes")
    Call<QuoteDto> getQuote(@Query("cat") String cat);
}
