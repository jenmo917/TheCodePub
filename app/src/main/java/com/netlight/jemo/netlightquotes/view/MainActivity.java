package com.netlight.jemo.netlightquotes.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.netlight.jemo.CustomApplication;
import com.netlight.jemo.netlightquotes.R;

import model.dto.QuoteDto;
import retrofit.Callback;
import service.QuotesService;

public class MainActivity extends AppCompatActivity {

    private Button buttonDislike;
    private Button buttonLike;
    private QuoteView quoteView;

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

    private void getNewQuote() {
        getQuotesService().getQuote("movies", new Callback<QuoteDto>() {
            @Override
            public void onResponse(retrofit.Response<QuoteDto> response) {
                QuoteDto quoteDto = response.body();
                setQuoteToView(quoteDto);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private QuotesService getQuotesService() {
        CustomApplication application = (CustomApplication) getApplication();
        return application.getQuotesService();
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
