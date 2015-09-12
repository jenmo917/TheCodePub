package com.netlight.quotes.app.service.task;

import android.content.Context;
import android.os.AsyncTask;

import com.netlight.quotes.app.ValueHolder;
import com.netlight.quotes.app.model.dao.DaoSession;
import com.netlight.quotes.app.model.db.Quote;
import com.netlight.quotes.app.service.QuoteDatabaseService;
import com.netlight.quotes.app.service.TaskCallback;

public class SaveQuoteAsyncTask extends AsyncTask<Quote, Void, Quote>

    {
        private TaskCallback<Quote> taskCallback;
        private Context context;

        public SaveQuoteAsyncTask(Context context, TaskCallback<Quote> taskCallback) {
        this.taskCallback = taskCallback;
        this.context = context;
    }

        public SaveQuoteAsyncTask(Context context) {
        this.context = context;
    }

        @Override
        protected void onPreExecute() {
        super.onPreExecute();
    }

        @Override
        protected Quote doInBackground(Quote... params) {
        Quote quote = params[0];
        DaoSession superDaoSession = ValueHolder.getInstance(context).getDaoSession();
        new QuoteDatabaseService(superDaoSession).saveQuote(quote);
        return quote;
    }

        @Override
        protected void onPostExecute(Quote quote) {
        super.onPostExecute(quote);
        if (taskCallback != null) {
            taskCallback.onTaskFinished(quote);
        }
    }
}
