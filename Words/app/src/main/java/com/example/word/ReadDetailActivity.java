package com.example.word;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.word.model.Book;

public class ReadDetailActivity extends AppCompatActivity {


    private TextView tvWord;
    private TextView tvDefinition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        tvWord = findViewById(R.id.tv_word);
        tvDefinition = findViewById(R.id.tv_definition);
        final Book book = (Book) getIntent().getSerializableExtra("book");
        tvWord.setText(book.getTitle());
        tvDefinition.setText(book.getContent());
        findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,book.getContent());
                intent.setType("text/plain");
                startActivity(intent);
            }
        });
    }
}
