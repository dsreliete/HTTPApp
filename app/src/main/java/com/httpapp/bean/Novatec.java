package com.httpapp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eliete on 4/7/16.
 */
public class Novatec {
    @SerializedName("categoria")
    private String category;
    @SerializedName("livros")
    private List<Livro> livros = new ArrayList<>();


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

}
