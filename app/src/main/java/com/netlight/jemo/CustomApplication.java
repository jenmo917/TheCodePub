package com.netlight.jemo;

import android.app.Application;
import service.QuotesService;

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
