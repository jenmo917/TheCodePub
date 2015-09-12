package com.netlight.quotes.app.service.webservice;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

public interface YodaApi {
    @Headers({
            "X-Mashape-Key : OVEPFZxY9ImshDy9tJFFZjHT6xR9p1XnoEejsnpkTn1rsmKPrH",
            "Accept: text/plain"
    })
    @GET("yoda")
    Call<String> yodafy(@Query("sentence") String sentence);
}
