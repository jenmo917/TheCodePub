package com.netlight.jemo.netlightquotes.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netlight.jemo.netlightquotes.R;

import model.dto.QuoteDto;

/**
 * Created by n06963 on 11/09/15.
 */
public class QuoteView extends RelativeLayout {

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

        QuoteDto quoteDto = new QuoteDto("Blessed is the man, who having nothing to say, abstains from giving wordy evidence of the fact.", "George Eliot", "Famous");

        TextView textViewQuote = (TextView) findViewById(R.id.textViewQuote);
        TextView textViewQuoteByLine = (TextView) findViewById(R.id.textViewQuoteByLine);

        textViewQuote.setText(quoteDto.getText());
        textViewQuoteByLine.setText(quoteDto.getSource());
    }
}
