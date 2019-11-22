package com.example.word;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.word.model.Book;

import java.util.ArrayList;
import java.util.List;

public class ReadListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private List<Book> books = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readlist);
        initBook();
        recyclerView =  findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final BookAdapter bookAdapter = new BookAdapter(this, books);
        recyclerView.setAdapter(bookAdapter);
        bookAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void setOnItemClickListener(int position) {
                Book book = books.get(position);
                Intent intent = new Intent();
                intent.setClass(ReadListActivity.this,ReadDetailActivity.class);
                intent.putExtra("book",book);
                startActivity(intent);
            }
        });
    }

    private void initBook() {
        books.add(new Book(getString(R.string.read1),getString(R.string.read1_content)));
        books.add(new Book(getString(R.string.read2),getString(R.string.read2_content)));
        books.add(new Book(getString(R.string.read3),getString(R.string.read3_content)));
        books.add(new Book(getString(R.string.read4),getString(R.string.read4_content)));
        books.add(new Book(getString(R.string.read5),getString(R.string.read5_content)));
        books.add(new Book(getString(R.string.read6),getString(R.string.read6_content)));
        books.add(new Book(getString(R.string.read7),getString(R.string.read7_content)));
    }
}
