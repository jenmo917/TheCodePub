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
import com.netlight.quotes.app.util.Util;
import com.netlight.quotes.app.view.custom.QuoteView;
import com.netlight.quotes.app.view.custom.YodaButton;
import com.netlight.quotes.app.view.favorites.FavoritesActivity;

import retrofit.Callback;
import retrofit.Response;

public class MainActivity extends AppCompatActivity {

    private Button buttonDislike;
    private Button buttonLike;
    private QuoteView quoteView;
    private Quote quote;
    private YodaButton buttonYodafy;
    LoadingLayout loadingLayout;
    private Filter filter = Filter.movies;
    private boolean lastClickWasOnYodaButton;

    private enum Filter {
        movies, famous
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        getNewQuote();
        setListeners();
    }

    private void findViews() {
        buttonLike = (Button) findViewById(R.id.buttonLike);
        buttonDislike = (Button) findViewById(R.id.buttonDislike);
        buttonYodafy = (YodaButton) findViewById(R.id.buttonYodafy);
        quoteView = (QuoteView) findViewById(R.id.quoteView);
        loadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
    }

    private void setListeners() {
        buttonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!loadingLayout.isLoading()) {
                    lastClickWasOnYodaButton = false;
                    Util.showToast(getApplicationContext(), getResources().getString(R.string.save_as_favorite));
                    saveQuoteToDb();
                    getNewQuote();
                }
            }
        });
        buttonDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!loadingLayout.isLoading()) {
                    lastClickWasOnYodaButton = false;
                    getNewQuote();
                }
            }
        });
        buttonYodafy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!loadingLayout.isLoading()) {
                    lastClickWasOnYodaButton = true;
                    loadingLayout.loadingStart();
                    quoteView.setQuoteColor(R.color.yoda);
                    yodafyQuote(quote);
                }
            }
        });
        loadingLayout.setLoadingListener(new LoadingLayout.LoadingListener() {
            @Override
            public void OnRetryPressed() {
                loadingLayout.loadingStart();
                if (lastClickWasOnYodaButton) {
                    yodafyQuote(quote);
                } else {
                    getNewQuote();
                }
            }
        });
    }

    private void yodafyQuote(Quote quote) {
        ValueHolder.getInstance(getApplicationContext()).getYodaWebService().yodafy(quote.getQuote(), new Callback<String>() {

            @Override
            public void onResponse(Response<String> response) {
                String quoteText = response.body();
                setYodaQuoteToView(quoteText);
                loadingLayout.loadingSuccesssfull();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                loadingLayout.loadingFailed(null);
            }
        });
    }

    private void setYodaQuoteToView(String quoteText) {
        quote.setYodafied(true);
        quote.setQuote(quoteText);
        quote.setAuthor(getResources().getString(R.string.yoda));
        quote.setCategory(getResources().getString(R.string.star_wars));
        quoteView.bindTo(quote);
    }

    private void saveQuoteToDb() {
        new SaveQuoteAsyncTask(getApplicationContext()).execute(quote);
    }

    private void getNewQuote() {
        loadingLayout.loadingStart();
        quoteView.setQuoteColor(R.color.netlight_gold);
        ValueHolder.getInstance(getApplicationContext()).getQuotesWebService().getQuote(filter.name(), new Callback<QuoteDto>() {
            @Override
            public void onResponse(retrofit.Response<QuoteDto> response) {
                quote = new Quote(response.body(), false);
                setQuoteToView(quote);
                loadingLayout.loadingSuccesssfull();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                loadingLayout.loadingFailed(null);
            }
        });
    }

    private void setQuoteToView(Quote quoteDto) {
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

        switch (id) {
            case R.id.action_movie_quotes:
                filter = Filter.movies;
                getNewQuote();
                break;
            case R.id.action_famous_quotes:
                filter = Filter.famous;
                getNewQuote();
                break;
            case R.id.action_favorites:
                startFavoritesActivity();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startFavoritesActivity() {
        Intent intent = new Intent(this, FavoritesActivity.class);
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
}