package com.netlight.quotes.app;

import android.app.Application;
import com.netlight.quotes.app.service.QuotesService;

public class CustomApplication extends Application {
    QuotesService quotesService;

    public CustomApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        quotesService = new QuotesService();
    }

    public QuotesService getQuotesService() {
        return quotesService;
    }
}
