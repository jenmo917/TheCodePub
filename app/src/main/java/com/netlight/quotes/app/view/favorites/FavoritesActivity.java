package com.netlight.quotes.app.view.favorites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.netlight.quotes.app.R;
import com.netlight.quotes.app.model.db.Quote;
import com.netlight.quotes.app.service.TaskCallback;
import com.netlight.quotes.app.service.task.GetQuotesAsyncTask;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFavorites;
    private QuotesAdapter quotesAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        findViews();
        initRecyclerView();
        getQuotesFromDb();
    }

    private void getQuotesFromDb() {
        new GetQuotesAsyncTask(getApplicationContext(), new TaskCallback<List<Quote>>() {
            @Override
            public void onTaskFinished(List<Quote> quotes) {
                quotesAdapter.setItems(quotes);
            }
        }).execute();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerViewFavorites.setLayoutManager(linearLayoutManager);
        recyclerViewFavorites.setHasFixedSize(true);
        quotesAdapter = new QuotesAdapter(this);
        recyclerViewFavorites.setAdapter(quotesAdapter);
    }

    private void findViews() {
        recyclerViewFavorites = (RecyclerView) findViewById(R.id.recyclerViewFavorites);
    }
}
