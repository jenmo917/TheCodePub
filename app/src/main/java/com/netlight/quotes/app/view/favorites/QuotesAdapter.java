package com.netlight.quotes.app.view.favorites;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netlight.quotes.app.R;
import com.netlight.quotes.app.model.db.Quote;

import java.util.List;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.ViewHolder> {
    private final Context context;
    private List<Quote> items;

    public QuotesAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_quote, parent, false);
        ViewHolder viewholder = new ViewHolder(v);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Quote quote = items.get(position);
        viewHolder.quote.setText(quote.getQuote());
        viewHolder.quoteByLine.setText(quote.getAuthor());
        setTopBottomMargin(position, viewHolder);
    }

    private void setTopBottomMargin(int position, ViewHolder viewHolder) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) viewHolder.layoutItemView.getLayoutParams();
        int margin = (int) context.getResources().getDimension(R.dimen.margin_l);
        layoutParams.topMargin = margin;
        layoutParams.bottomMargin = margin;
        if (position == 0) {
            layoutParams.topMargin = margin * 2;
        } else if (position == items.size() - 1) {
            layoutParams.bottomMargin = margin * 2;
        }
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void setItems(List<Quote> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout layoutItemView;
        public final TextView quote;
        public final TextView quoteByLine;

        public ViewHolder(View itemView) {
            super(itemView);
            layoutItemView = (LinearLayout) itemView.findViewById(R.id.layoutItemView);
            quote = (TextView) itemView.findViewById(R.id.textViewQuote);
            quoteByLine = (TextView) itemView.findViewById(R.id.textViewQuoteByLine);
        }
    }
}
