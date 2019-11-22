package com.example.word;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.word.db.AppDatabase;
import com.example.word.model.Words;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {

    private RecyclerView rv;
    private WordsAdapter adapter;
    private List<Words> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WordsAdapter(this,list);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void setOnItemClickListener(int position) {
                Intent intent =  new Intent(FavouriteActivity.this,WordRandomActivity.class);
                intent.putExtra("word",list.get(position));
                startActivity(intent);

            }
        });
        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void setOnItemLongClickListener(int position) {
                delete(position);
            }
        });
        new LoadTask().execute();
    }

    private void delete(final int position) {
        new AlertDialog.Builder(this)
                .setTitle("Tips")
                .setMessage("Confirm delete?")
                .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Words bean = list.get(position);
                        new DeleteTask(position).execute(bean);
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create().show();
    }
    private class DeleteTask extends AsyncTask<Words, Void, Void> {

        private int position;
        public DeleteTask(int position) {
            this.position = position;

        }

        @Override
        protected  Void doInBackground(Words... voids) {
            Words bean = voids[0];
          AppDatabase.getDatabase(FavouriteActivity.this).dao().delelte(bean.getWord());
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            list.remove(position);
            adapter.notifyDataSetChanged();
        }
    }
    private class LoadTask extends AsyncTask<Void, Void,  List<Words>> {

        @Override
        protected  List<Words> doInBackground(Void... voids) {
            List<Words> dataList = AppDatabase.getDatabase(FavouriteActivity.this).dao().getAll();
            return dataList;
        }


        @Override
        protected void onPostExecute(List<Words> words) {
            super.onPostExecute(words);
            list.clear();
            list.addAll(words);
            adapter.notifyDataSetChanged();
        }
    }
}
