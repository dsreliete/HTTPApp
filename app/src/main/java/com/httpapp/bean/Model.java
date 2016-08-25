package com.httpapp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eliete on 4/7/16.
 */
public class Model {

    @SerializedName("novatec")
    public List<Novatec> novatecList = new ArrayList<>();

}
