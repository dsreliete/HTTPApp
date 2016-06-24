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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
