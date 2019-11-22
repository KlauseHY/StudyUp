package com.example.word;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.word.db.AppDatabase;
import com.example.word.model.Question;

import java.util.ArrayList;
import java.util.List;


public class ErrorActivity extends AppCompatActivity {
    private  int position;
    private RadioButton rl_a;
    private RadioButton rl_b;
    private RadioButton rl_c;
    private RadioButton rl_d;
    private List<Question> list = new ArrayList<>();
    private TextView title;
    private LinearLayout ll_data;
    private TextView tv_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        initView();
        new LoadTask().execute();
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if (list.size()>0) {
                        tv_title.setText("Q"+(position +1)+"/"+list.size());
                        title.setText(list.get(position).getTitle());
                        rl_a.setText("A:"+list.get(position).getA());
                        rl_b.setText("B: "+list.get(position).getB());
                        String c = list.get(position).getC();
                        if (TextUtils.isEmpty(c)){
                            rl_c.setVisibility(View.GONE);
                            rl_d.setVisibility(View.GONE);

                        }else{
                            rl_c.setVisibility(View.VISIBLE);
                            rl_d.setVisibility(View.VISIBLE);
                            rl_c.setText("C: "+c);
                            rl_d.setText("D: "+list.get(position).getD());
                        }

                    }
                    break;
                case 1:
                    ll_data.setVisibility(View.GONE);
                    Toast.makeText(ErrorActivity.this,"empty",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private class LoadTask extends AsyncTask<Void, Void,  List<Question>> {

        @Override
        protected  List<Question> doInBackground(Void... voids) {
            List<Question> dataList = AppDatabase.getDatabase(ErrorActivity.this).dao().getAllQuestion();
            return dataList;
        }


        @Override
        protected void onPostExecute(List<Question> words) {
            super.onPostExecute(words);
            list.clear();
            list.addAll(words);
            if (list.size()>0){
                handler.sendEmptyMessage(0);
            }else{
                handler.sendEmptyMessage(1);
            }
        }
    }
    private void next(){
        if (position ==list.size()-1){
            Toast.makeText(ErrorActivity.this,"This is the last question",Toast.LENGTH_SHORT).show();
            return;
        }
        position++;
        handler.sendEmptyMessage(0);
        rl_a.setChecked(false);
        rl_b.setChecked(false);
        rl_c.setChecked(false);
        rl_d.setChecked(false);
    }
    private void initView() {
        title  = findViewById(R.id.title);
        rl_a = findViewById(R.id.rba);
        rl_b = findViewById(R.id.rbb);
        rl_c = findViewById(R.id.rbc);
        rl_d = findViewById(R.id.rl_d);
        ll_data = findViewById(R.id.ll_data);
        Button bt_ok =  findViewById(R.id.bt_ok);
        Button bt_pre =  findViewById(R.id.bt_pre);
        tv_title = findViewById(R.id.tv_title);
        bt_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.size()>0) {
                    new DeleteTask().execute(list.get(position));
                }

            }
        });
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ok = list.get(position).getOk();
                if (rl_a.isChecked()){
                    if (ok.equals("a")){
                        next();
                    }else{
                        Toast.makeText(ErrorActivity.this,"error",Toast.LENGTH_SHORT).show();
                    }
                }else if (rl_b.isChecked()){
                    if (ok.equals("b")){
                        next();
                    }else{
                        Toast.makeText(ErrorActivity.this,"error",Toast.LENGTH_SHORT).show();
                    }
                }else if (rl_c.isChecked()){
                    if (ok.equals("c")){
                        next();
                    }else{
                        Toast.makeText(ErrorActivity.this,"error",Toast.LENGTH_SHORT).show();
                    }
                }else if (rl_d.isChecked()){
                    if (ok.equals("d")){
                        next();
                    }else{
                        Toast.makeText(ErrorActivity.this,"error",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    private class DeleteTask extends AsyncTask<Question, Void, Void> {



        @Override
        protected  Void doInBackground(Question... voids) {
            Question bean = voids[0];
            AppDatabase.getDatabase(ErrorActivity.this).dao().delelteQuestion(bean.getId()+"");
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            list.remove(position);
            if (list.size()==0){
                finish();
            }else {
                handler.sendEmptyMessage(0);
            }
        }
    }
}
