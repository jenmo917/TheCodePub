package com.netlight.quotes.app.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.netlight.quotes.app.R;
import com.netlight.quotes.app.ValueHolder;
import com.netlight.quotes.app.model.db.Quote;
import com.netlight.quotes.app.model.dto.QuoteDto;
import com.netlight.quotes.app.service.task.SaveQuoteAsyncTask;
import com.netlight.quotes.app.view.favorites.FavoritesActivity;

import retrofit.Callback;
import retrofit.Response;

public class MainActivity extends AppCompatActivity {

    private Button buttonDislike;
    private Button buttonLike;
    private QuoteView quoteView;
    private QuoteDto quote;
    private Button buttonYodafy;

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
        buttonYodafy = (Button) findViewById(R.id.buttonYodafy);
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
        buttonYodafy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yodafyQuote(quote);
            }
        });
    }

    private void yodafyQuote(final QuoteDto quote) {
        ValueHolder.getInstance(getApplicationContext()).getYodaWebService().yodafy(quote.getQuote(), new Callback<String>() {

            @Override
            public void onResponse(Response<String> response) {
                quote.setQuote(response.body());
                quote.setAuthor("Yoda");
                quote.setCategory("Star Wars");
                quoteView.bindTo(quote);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void saveQuoteToDb() {
        Quote quote = new Quote(this.quote);
        new SaveQuoteAsyncTask(getApplicationContext()).execute(quote);
    }

    private void getNewQuote() {
        ValueHolder.getInstance(getApplicationContext()).getQuotesWebService().getQuote("movies", new Callback<QuoteDto>() {
            @Override
            public void onResponse(retrofit.Response<QuoteDto> response) {
                quote = response.body();
                setQuoteToView(quote);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setQuoteToView(QuoteDto quoteDto) {
        quoteView.bindTo(quoteDto);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_favorites) {
            Intent intent = new Intent(this, FavoritesActivity.class);
            overridePendingTransition(0, 0);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
