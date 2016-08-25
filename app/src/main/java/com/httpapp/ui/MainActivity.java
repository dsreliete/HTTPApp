package com.httpapp.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.httpapp.R;
import com.httpapp.bean.Book;
import com.httpapp.bean.Model;
import com.httpapp.rest.HTTPRestApi;
import com.httpapp.ui.adapters.ListAdapter;
import com.httpapp.widget.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.nav_view)
    NavigationView navigationView;

    private List<Book> bookList = new ArrayList<>();
    private ListAdapter listAdapter;
    private boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar.setVisibility(View.VISIBLE);

        if (navigationView != null) {
            this.setupDrawerContent(navigationView);
        }

        if (MainActivity.hasConnection(this)) {
            if (!isRunning){
                initDownloadRetrofit();
            }
        }else {
            Toast.makeText(this, "NO CONNECTION", Toast.LENGTH_SHORT).show();
        }

        listAdapter = new ListAdapter(bookList);
        recyclerView.setAdapter(listAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerView.setHasFixedSize(true);

    }

    public static boolean hasConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null){
            return networkInfo.isConnected();
        }else{
            return false;
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {

        Intent intent = null;

        switch(menuItem.getItemId()) {
            case R.id.nav_init:
                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                break;
            case R.id.nav_goals:
                intent = new Intent(this, VolleyActivity.class);
                break;
        }

        if (intent != null)
            startActivity(intent);

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_samples, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initDownloadRetrofit() {
        isRunning = true;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HTTPRestApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HTTPRestApi service = retrofit.create(HTTPRestApi.class);
        Call<Model> call = service.getJsonBook();
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                isRunning = false;
                if (response.isSuccessful()) {
                    Book book = new Book();
                    bookList.addAll(book.getBookListFromRetrofitJson(response));
                    listAdapter.notifyDataSetChanged();

                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                if (call.isCanceled()) {
                    Log.e(TAG, "retrofit response error");
                } else {
                    Toast.makeText(MainActivity.this, "Verifique se vc tem conexão com internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**private void initDownload() {
        if(bookTask == null || bookTask.getStatus() != AsyncTask.Status.RUNNING){
            bookTask = new BookTask();
            bookTask.execute();
        }
    }

    public class BookTask extends AsyncTask<Void, Void, List<Book>>{

        @Override
        protected List<Book> doInBackground(Void... params) {
            return HTTP.fetchJsonBook();
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            super.onPostExecute(books);
            if (books != null){
                Log.w("Eliete", books + "");
            }
        }
    }**/



}
