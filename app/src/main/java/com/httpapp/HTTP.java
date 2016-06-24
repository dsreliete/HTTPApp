package com.httpapp;

import android.util.Log;

import com.httpapp.bean.Book;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by eliete on 4/7/16.
 */
public class HTTP {

    public static final String LIVROS_URL_JSON =
            "https://raw.githubusercontent.com/nglauber/"+
                    "dominando_android/master/livros_novatec.json";

    private static HttpURLConnection connect(String surl) throws IOException {
        URL url = new URL(surl);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setReadTimeout(10 * 1000);
        connection.setConnectTimeout(15 * 1000);
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.setDoOutput(false);
        connection.connect();
        return connection;
    }


    public static List<Book> fetchJsonBook() {
        try {
            HttpURLConnection connection = connect(LIVROS_URL_JSON);
            int connectionResponse = connection.getResponseCode();
            if (connectionResponse ==  HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                if (inputStream == null) {
                    Log.e("Eliete", "inputStream failed ");
                    return null;
                }
                JSONObject json = new JSONObject(getStringFromBuffer(inputStream));
                return Book.getBookFromJson(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getStringFromBuffer(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int bytesLidos;

        while ((bytesLidos = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesLidos);
        }

        return new String(byteArrayOutputStream.toByteArray(), "UTF-8");
    }





}
