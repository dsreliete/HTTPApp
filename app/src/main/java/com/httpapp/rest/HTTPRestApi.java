package com.httpapp.rest;

import com.httpapp.bean.Model;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by eliete on 4/7/16.
 */
public interface HTTPRestApi {

    public static final String BASE_URL =
            "https://raw.githubusercontent.com/nglauber/"+
                    "dominando_android/master/";

    @GET("livros_novatec.json")
    Call<Model> getJsonBook();

}
