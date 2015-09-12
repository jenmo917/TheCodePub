package com.netlight.quotes.app.service;

import android.util.Log;

import com.netlight.quotes.app.model.dao.DaoSession;
import com.netlight.quotes.app.model.db.Quote;

public class QuoteDatabaseService {
    private final DaoSession daoSession;

    public QuoteDatabaseService(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    public void saveQuote(Quote quote) {
        long id = daoSession.getQuoteDao().insert(quote);
        Log.d(getClass().getCanonicalName(), "Quote saved to db: " + id);
    }
}
