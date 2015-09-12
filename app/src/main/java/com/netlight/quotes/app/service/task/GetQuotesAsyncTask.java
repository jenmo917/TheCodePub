package com.netlight.quotes.app.service.task;

import android.content.Context;
import android.os.AsyncTask;

import com.netlight.quotes.app.ValueHolder;
import com.netlight.quotes.app.model.dao.DaoSession;
import com.netlight.quotes.app.model.db.Quote;
import com.netlight.quotes.app.service.QuoteDatabaseService;
import com.netlight.quotes.app.service.TaskCallback;

import java.util.List;

public class GetQuotesAsyncTask extends AsyncTask<Void, Void, List<Quote>> {
    private TaskCallback<List<Quote>> taskCallback;
    private Context context;

    public GetQuotesAsyncTask(Context context, TaskCallback<List<Quote>> taskCallback) {
        this.taskCallback = taskCallback;
        this.context = context;
    }

    public GetQuotesAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Quote> doInBackground(Void... params) {
        DaoSession superDaoSession = ValueHolder.getInstance(context).getDaoSession();
        List<Quote> quotes = new QuoteDatabaseService(superDaoSession).getAllQuotes();
        return quotes;
    }

    @Override
    protected void onPostExecute(List<Quote> quotes) {
        super.onPostExecute(quotes);
        if (taskCallback != null) {
            taskCallback.onTaskFinished(quotes);
        }
    }
}