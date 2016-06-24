package com.httpapp.bean;

/**
 * Created by eliete on 4/7/16.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class Book implements Serializable {
    public String title;
    public String category;
    public String author;
    public int year;
    public int page;
    public String cover;

    public static List<Book> mBookList = new ArrayList<>();

    public Book() {}

    public Book(String title, String category, String author, int year, int page, String cover) {
        this.title = title;
        this.category = category;
        this.author = author;
        this.year = year;
        this.page = page;
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static List<Book> getBookList() {
        return mBookList;
    }

    public static void setBookList(List<Book> bookList) {
        mBookList = bookList;
    }

    @Override
    public String toString() {
        return category + "\n" + title + "\n" + author + "\n" + year + "\n" + page + "\n" + cover ;
    }

    public static void getBookListFromJson(Response<Model> response){
        List<Novatec> novatecList = response.body().getNovatecList();
        for (int i = 0; i < novatecList.size(); i++){
            String categoria = novatecList.get(i).getCategory();
            Book book = new Book();
            book.category = categoria;

            List<Livro> booksList = novatecList.get(i).getLivros();
            for (int j = 0; j < booksList.size(); j++){
                String title = booksList.get(j).getTitle();
                String author = booksList.get(j).getAuthor();
                int year = booksList.get(j).getYear();
                int page = booksList.get(j).getPage();
                String cover = booksList.get(j).getCover();

                book.title = title;
                book.author = author;
                book.year = year;
                book.page = page;
                book.cover = cover;
            }
            mBookList.add(book);
        }
    }

    public static List<Book> getBookFromJson(JSONObject json) throws JSONException {
        List<Book> bookList = new ArrayList<>();
        String category;
        JSONArray novatecArray = json.getJSONArray("novatec");
        for (int i = 0; i < novatecArray.length(); i++) {

            JSONObject categoryJsonObject = novatecArray.getJSONObject(i);
            category = categoryJsonObject.getString("categoria");

            JSONArray bookArray = categoryJsonObject.getJSONArray("livros");
            for (int j = 0; j < bookArray.length(); j++) {

                JSONObject bookJsonObject = bookArray.getJSONObject(j);
                Book book = new Book(
                        bookJsonObject.getString("titulo"),
                        category,
                        bookJsonObject.getString("autor"),
                        bookJsonObject.getInt("ano"),
                        bookJsonObject.getInt("paginas"),
                        bookJsonObject.getString("capa")
                );
                bookList.add(book);
            }
        }
        return bookList;
    }
}
