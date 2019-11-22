package com.example.word;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.word.model.Words;

import java.util.List;


public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.ViewHolder> {
    private Context context;
    private List<Words> cats;
    public WordsAdapter(Context context, List<Words> cats) {
        this.context = context;
        this.cats = cats;
    }

    private OnItemLongClickListener onItemLongClickListener;
    public void setOnItemLongClickListener(OnItemLongClickListener listener ){
        this.onItemLongClickListener = listener;
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
        Words wrod = cats.get(position);
        holder.tvTitle.setText(wrod.getWord());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener!=null){
                    onItemClickListener.setOnItemClickListener(position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onItemLongClickListener!=null){
                    onItemLongClickListener.setOnItemLongClickListener(position);
                }
                return true;
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
