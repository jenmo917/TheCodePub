package com.netlight.quotes.app.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netlight.quotes.app.R;

import com.netlight.quotes.app.model.dto.QuoteDto;

public class QuoteView extends RelativeLayout {

    private TextView textViewQuoteByLine;
    private TextView textViewQuote;

    public QuoteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QuoteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public QuoteView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_quote, this);
        textViewQuote = (TextView) findViewById(R.id.textViewQuote);
        textViewQuoteByLine = (TextView) findViewById(R.id.textViewQuoteByLine);
    }

    public void bindTo(QuoteDto quoteDto) {
        textViewQuote.setText(quoteDto.getQuote());
        textViewQuoteByLine.setText(quoteDto.getAuthor());
    }
}
