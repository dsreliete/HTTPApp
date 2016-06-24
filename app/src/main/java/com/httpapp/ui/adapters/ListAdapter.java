package com.httpapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.httpapp.R;
import com.httpapp.bean.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by eliete on 4/11/16.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    List<Book> bookList;
    Context context;

    public ListAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.book_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        Book book = bookList.get(position);

        if (book != null && getItemCount() > 0){
            holder.textViewAuthors.setText(book.author);
            holder.textViewCategory.setText("Categoria: " + book.category);
            holder.textViewTitle.setText(book.title);
            holder.textViewPage.setText(book.page + " páginas");
            holder.textViewYear.setText("" + book.year);
            Picasso.with(context)
                    .load(book.cover)
                    .placeholder(R.drawable.default_placeholder)
                    .into(holder.imageViewCover);
        }
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.imageView_cover)
        ImageView imageViewCover;

        @Bind(R.id.textView_category)
        TextView textViewCategory;

        @Bind(R.id.textView_title)
        TextView textViewTitle;

        @Bind(R.id.textView_authors)
        TextView textViewAuthors;

        @Bind(R.id.textView_year)
        TextView textViewYear;

        @Bind(R.id.textView_page)
        TextView textViewPage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
