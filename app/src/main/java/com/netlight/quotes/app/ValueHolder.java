package com.netlight.quotes.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.netlight.quotes.app.model.dao.DaoMaster;
import com.netlight.quotes.app.model.dao.DaoSession;
import com.netlight.quotes.app.service.QuotesService;

public class ValueHolder {
    private static final String DATABASE_NAME = "com.netlight.quotes.db";
    private static ValueHolder instance;
    private DaoSession daoSession;
    private QuotesService quotesService;

    private ValueHolder(Context context) {
        initDataBaseSession(context);
        initRetrofitService();
    }

    private void initRetrofitService() {
        quotesService = new QuotesService();
    }

    public synchronized static ValueHolder getInstance(final Context context) {
        if (instance == null) {
            instance = new ValueHolder(context);
            Log.d(instance.getClass().getName(), "New ValueHolder instance");
        }
        return instance;
    }

    private void initDataBaseSession(final Context context) {
        DaoMaster.OpenHelper master = new DaoMaster.DevOpenHelper(context, DATABASE_NAME, null);
        SQLiteDatabase dataBase = master.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(dataBase);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public QuotesService getQuotesWebService() {
        return quotesService;
    }
}