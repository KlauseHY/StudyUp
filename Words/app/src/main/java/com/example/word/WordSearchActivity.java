package com.example.word;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.word.db.AppDatabase;
import com.example.word.model.Words;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WordSearchActivity extends AppCompatActivity {
    private Words words;
    private TextView tvWord;
    private TextView tvDefinition;
    private ProgressDialog dialog;
    private LinearLayout llBottom;
    private EditText et_word;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...");
        tvWord = findViewById(R.id.tv_word);
        et_word = findViewById(R.id.et_word);
        tvDefinition = findViewById(R.id.tv_definition);
        llBottom = findViewById(R.id.ll_bottom);
        findViewById(R.id.bt_coll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (words==null){
                    return;
                }else{
                    new AddTask().execute();


                }
            }
        });
        findViewById(R.id.bt_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = et_word.getText().toString();
                if (!TextUtils.isEmpty(keyword)) {
                    new RandomWordTask().execute(keyword);
                }
            }
        });


    }
    private class AddTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase.getDatabase(WordSearchActivity.this).dao().coll(words);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }






        @Override
        protected void onPostExecute(Void result) {
            dialog.dismiss();
            Toast.makeText(WordSearchActivity.this,"success",Toast.LENGTH_SHORT).show();
        }


    }
    private class RandomWordTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://wordsapiv1.p.rapidapi.com/words/"+params[0])
                    .get()
                    .addHeader("x-rapidapi-host", "wordsapiv1.p.rapidapi.com")
                    .addHeader("x-rapidapi-key", "11bd064a02msh0c0ee5f347264d9p116370jsn7bd53390e960")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                return  response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }




        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            Log.e("result",result);
            Gson gson = new Gson();
            WordResponse wordResponse = gson.fromJson(result, WordResponse.class);
            words = new Words();
            words.setWord(wordResponse.getWord());
            if (wordResponse.getResults()!=null&&wordResponse.getResults().size()>0){
                words.setDefinition(wordResponse.getResults().get(0).getDefinition());
            }else{
                words.setDefinition("");
            }

            updateUI();
        }


    }

    private void updateUI() {
        tvWord.setText(words.getWord());
        tvDefinition.setText(words.getDefinition());
    }
}
