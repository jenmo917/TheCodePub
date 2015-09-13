package com.netlight.quotes.app.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.netlight.quotes.app.R;
import com.netlight.quotes.app.ValueHolder;
import com.netlight.quotes.app.model.db.Quote;
import com.netlight.quotes.app.model.dto.QuoteDto;
import com.netlight.quotes.app.service.task.SaveQuoteAsyncTask;
import com.netlight.quotes.app.view.about.AboutActivity;
import com.netlight.quotes.app.view.custom.QuoteView;
import com.netlight.quotes.app.view.custom.YodaButton;
import com.netlight.quotes.app.view.favorites.FavoritesActivity;

import retrofit.Callback;
import retrofit.Response;

public class MainActivity extends AppCompatActivity {

    private Button buttonDislike;
    private Button buttonLike;
    private QuoteView quoteView;
    private QuoteDto quote;
    private YodaButton buttonYodafy;
    LoadingLayout loadingLayout;
    private Filter filter = Filter.movies;

    private enum Filter {
        movies, famous
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        getNewQuote();
        setOnButtonClickListener();
        quoteView.setQuoteColor(R.color.netlight_gold);
    }

    private void findViews() {
        buttonLike = (Button) findViewById(R.id.buttonLike);
        buttonDislike = (Button) findViewById(R.id.buttonDislike);
        buttonYodafy = (YodaButton) findViewById(R.id.buttonYodafy);
        quoteView = (QuoteView) findViewById(R.id.quoteView);
        loadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
    }

    private void setOnButtonClickListener() {
        buttonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(getResources().getString(R.string.save_as_favorite));
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
                loadingLayout.loadingStart();
                quoteView.setQuoteColor(R.color.yoda);
                yodafyQuote(quote);
            }
        });
    }

    private void showToast(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, (int) getResources().getDimension(R.dimen.margin_l));
        toast.show();
    }

    private void yodafyQuote(final QuoteDto quote) {
        ValueHolder.getInstance(getApplicationContext()).getYodaWebService().yodafy(quote.getQuote(), new Callback<String>() {

            @Override
            public void onResponse(Response<String> response) {
                String quoteText = response.body();
                setYodaQuoteToView(quoteText, quote);
                loadingLayout.loadingSuccesssfull();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                loadingLayout.loadingFailed(null);
            }
        });
    }

    private void setYodaQuoteToView(String quoteText, QuoteDto quote) {
        quote.setQuote(quoteText);
        quote.setAuthor(getResources().getString(R.string.yoda));
        quote.setCategory(getResources().getString(R.string.star_wars));
        quoteView.bindTo(quote);
    }

    private void saveQuoteToDb() {
        Quote quote = new Quote(this.quote);
        new SaveQuoteAsyncTask(getApplicationContext()).execute(quote);
    }

    private void getNewQuote() {
        loadingLayout.loadingStart();
        quoteView.setQuoteColor(R.color.netlight_gold);
        ValueHolder.getInstance(getApplicationContext()).getQuotesWebService().getQuote(filter.name(), new Callback<QuoteDto>() {
            @Override
            public void onResponse(retrofit.Response<QuoteDto> response) {
                quote = response.body();
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
                startFavoritesActivity(new Intent(this, FavoritesActivity.class));
                break;
            case R.id.action_about:
                startFavoritesActivity(new Intent(this, AboutActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startFavoritesActivity(Intent intent) {
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
}
