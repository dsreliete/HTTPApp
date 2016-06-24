package com.httpapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.httpapp.HTTP;
import com.httpapp.R;
import com.httpapp.bean.Book;
import com.httpapp.rest.VolleySingleton;
import com.httpapp.ui.adapters.GridAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by eliete on 6/24/16.
 */
public class VolleyActivity extends AppCompatActivity implements
        Response.Listener<JSONObject>, Response.ErrorListener {

    public static final String TAG = MainActivity.class.getSimpleName();

        @Bind(R.id.toolbar)
        Toolbar toolbar;
        @Bind(R.id.gridView)
        GridView gridView;
        @Bind(R.id.progressBar)
        ProgressBar progressBar;

        private List<Book> bookList;
        private GridAdapter gridAdapter;
        private boolean isRunning;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_volley);
            ButterKnife.bind(this);

            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            progressBar.setVisibility(View.VISIBLE);

            if (MainActivity.hasConnection(this)) {
                if (!isRunning){
                    initDownloadVolley(this);
                }
            }else {
                Toast.makeText(this, "NO CONNECTION", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }

            gridAdapter = new GridAdapter(this, new ArrayList<Book>());
            gridView.setAdapter(gridAdapter);
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            isRunning = false;
            Log.w(TAG, "volley response error");
        }

        @Override
        public void onResponse(JSONObject response) {
            isRunning = false;
            try {
                bookList = new ArrayList<>();
                List<Book> list = Book.getBookFromJson(response);

                if (list != null)
                    bookList.addAll(list);

                gridAdapter.add(bookList);

                progressBar.setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private void initDownloadVolley(Context context) {
            isRunning = true;
            RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();
            JsonObjectRequest request =
                    new JsonObjectRequest(
                            Request.Method.GET,    // requisition method
                            HTTP.LIVROS_URL_JSON,  // url
                            null,  // JSONObject to send by POST
                            this,  // Response.Listener
                            this); // Response.ErrorListener
            request.setRetryPolicy(VolleySingleton.getDefaultRetryPolicy());
            queue.add(request);
        }


}

