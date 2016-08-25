package com.httpapp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eliete on 4/7/16.
 */
public class Novatec {
    @SerializedName("categoria")
    public String category;
    @SerializedName("livros")
    public List<Livro> livros = new ArrayList<>();

    public List<Livro> getLivros() {
        return livros;
    }

    public String getCategory() {
        return category;
    }

}
