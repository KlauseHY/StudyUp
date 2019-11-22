package com.example.word;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.word.model.Book;

import java.util.List;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private Context context;
    private List<Book> cats;
    public BookAdapter(Context context, List<Book> cats) {
        this.context = context;
        this.cats = cats;
    }



    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener ){
        this.onItemClickListener = listener;
    }
    @Override
    public int getItemCount() {
        return cats.size();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Book cat = cats.get(position);
        holder.tvTitle.setText(cat.getTitle());
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener!=null){
                    onItemClickListener.setOnItemClickListener(position);
                }
            }
        });

    }



    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }

}
