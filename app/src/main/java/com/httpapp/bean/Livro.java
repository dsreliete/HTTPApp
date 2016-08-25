package com.httpapp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eliete on 4/7/16.
 */
public class Livro {

    @SerializedName("titulo")
    public String title;
    @SerializedName("autor")
    public String author;
    @SerializedName("ano")
    public int year;
    @SerializedName("paginas")
    public int page;
    @SerializedName("capa")
    public String cover;

    @Override
    public String toString() {
        return "Livro{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", page=" + page +
                ", cover='" + cover + '\'' +
                '}';
    }
}
