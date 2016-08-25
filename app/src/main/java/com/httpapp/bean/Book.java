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

//https://raw.githubusercontent.com/nglauber/dominando_android/master/livros_novatec.json

public class Book extends Livro implements Serializable {

    public String category;

    public Book() {}

    public Book(String title, String category, String author, int year, int page, String cover) {
        this.title = title;
        this.category = category;
        this.author = author;
        this.year = year;
        this.page = page;
        this.cover = cover;
    }

        public List<Book> getBookListFromRetrofitJson(Response<Model> response){
            List <Book> bookList= new ArrayList<>();

            for (int i = 0; i < response.body().novatecList.size(); i++){
                String category = response.body().novatecList.get(i).category;

                for (int j = 0; j < response.body().novatecList.get(i).livros.size(); j++) {
                    Book book = new Book();
                    book.category = category;
                    book.author = response.body().novatecList.get(i).livros.get(j).author;
                    book.cover = response.body().novatecList.get(i).livros.get(j).cover;
                    book.title = response.body().novatecList.get(i).livros.get(j).title;
                    book.page = response.body().novatecList.get(i).livros.get(j).page;
                    book.year = response.body().novatecList.get(i).livros.get(j).year;

                    bookList.add(book);
                }
            }
            return bookList;
        }


    public static List<Book> getBookListFromVolleyJson(JSONObject json) throws JSONException {
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
