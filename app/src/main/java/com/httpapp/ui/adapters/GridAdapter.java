package com.httpapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.httpapp.R;
import com.httpapp.bean.Book;
import com.httpapp.rest.VolleySingleton;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by eliete on 4/12/16.
 */
public class GridAdapter extends ArrayAdapter<Book> {

    List<Book> bookList;
    Context context;

    public GridAdapter(Context context, List<Book> bookList) {
        super(context, 0, bookList);
        this.context = context;
        this.bookList = bookList;
    }


    @Override
    public Book getItem(int position) {
        return bookList.get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.book_grid_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.textViewCategory.setText(getItem(position).category);
        holder.textViewTitle.setText(getItem(position).title);
        holder.imageViewCover.setTag(position);

        ImageLoader imageLoader = VolleySingleton.getImageLoader();
        imageLoader.get(getItem(position).cover, ImageLoader.getImageListener(holder.imageViewCover,
                R.drawable.default_placeholder, R.drawable.default_placeholder));

        return convertView;
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    public void add(List<Book> list) {
        bookList.addAll(list);
        notifyDataSetChanged();
    }


    public class ViewHolder {

        @Bind(R.id.imageView_cover)
        ImageView imageViewCover;

        @Bind(R.id.textView_category)
        TextView textViewCategory;

        @Bind(R.id.textView_title)
        TextView textViewTitle;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
