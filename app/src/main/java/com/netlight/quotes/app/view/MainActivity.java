package com.netlight.quotes.app.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.netlight.quotes.app.R;
import com.netlight.quotes.app.ValueHolder;
import com.netlight.quotes.app.model.db.Quote;
import com.netlight.quotes.app.model.dto.QuoteDto;
import com.netlight.quotes.app.service.QuotesService;
import com.netlight.quotes.app.service.task.SaveQuoteAsyncTask;

import retrofit.Callback;

public class MainActivity extends AppCompatActivity {

    private Button buttonDislike;
    private Button buttonLike;
    private QuoteView quoteView;
    private QuoteDto currentQuoteDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        getNewQuote();
        setOnButtonClickListener();
    }

    private void findViews() {
        buttonLike = (Button) findViewById(R.id.buttonLike);
        buttonDislike = (Button) findViewById(R.id.buttonDislike);
        quoteView = (QuoteView) findViewById(R.id.quoteView);
    }

    private void setOnButtonClickListener() {
        buttonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveQuoteToDb();
                getNewQuote();
            }
        });
        buttonDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNewQuote();
            }
        });
    }

    private void saveQuoteToDb() {
        Quote quote = new Quote(currentQuoteDto);
        new SaveQuoteAsyncTask(getApplicationContext()).execute(quote);
    }

    private void getNewQuote() {
        getQuotesWebService().getQuote("movies", new Callback<QuoteDto>() {
            @Override
            public void onResponse(retrofit.Response<QuoteDto> response) {
                currentQuoteDto = response.body();
                setQuoteToView(currentQuoteDto);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @NonNull
    private QuotesService getQuotesWebService() {
        QuotesService quotesService = ValueHolder.getInstance(getApplicationContext()).getQuotesWebService();
        return quotesService;
    }

    private void setQuoteToView(QuoteDto quoteDto) {
        quoteView.bindTo(quoteDto);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
