package com.netlight.quotes.app.view.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netlight.quotes.app.R;
import com.netlight.quotes.app.model.db.Quote;

public class QuoteView extends RelativeLayout {

    private TextView textViewQuoteByLine;
    private TextView textViewQuote;
    private ImageView imageViewQuote;
    private ImageView imageViewQuoteByLine;
    private int defaultColor;


    public QuoteView(Context context) {
        super(context);
        init();
    }

    public QuoteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initAttrs(attrs);
    }

    public QuoteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAttrs(attrs);
    }

    @TargetApi(21)
    public QuoteView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.QuoteView, 0, 0);
        try {
            defaultColor = a.getColor(R.styleable.QuoteView_quotecolor, getResources().getColor(R.color.netlight_gold));
            setColors(defaultColor);
        } finally {
            a.recycle();
        }
    }

    private void setColors(int color) {
        imageViewQuote.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
        imageViewQuoteByLine.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
    }

    private void init() {
        inflate(getContext(), R.layout.view_quote, this);
        textViewQuote = (TextView) findViewById(R.id.textViewQuote);
        textViewQuoteByLine = (TextView) findViewById(R.id.textViewQuoteByLine);
        imageViewQuote = (ImageView) findViewById(R.id.imageViewQuote);
        imageViewQuoteByLine = (ImageView) findViewById(R.id.imageViewQuoteByLine);
    }

    public void bindTo(Quote quote) {
        textViewQuote.setText(quote.getQuote());
        textViewQuoteByLine.setText(quote.getAuthor());
    }

    public void setQuoteColor(int color) {
        setColors(getResources().getColor(color));
    }

    public void setQuoteToDefaultColor() {
        if (defaultColor != 0) {
            setColors(defaultColor);
        }
    }
}