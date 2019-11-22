package com.example.word;

import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.word.db.AppDatabase;
import com.example.word.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class ExamActivity extends AppCompatActivity {
    private AssetManager assetManager;
    private String[] files;
    private List<Question> list = new ArrayList<Question>();
    private TextView title;
    private  int position;
    private RadioButton radioButtonA;
    private RadioButton radioButtonB;
    private RadioButton radioButtonC;
    private RadioButton radioButtonD;
    private TextView tv_tile;
    private int succeed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        initView();
        init();
    }

    private void initView() {
        tv_tile = findViewById(R.id.tv_title);
        title  = findViewById(R.id.title);
        radioButtonA = findViewById(R.id.rba);
        radioButtonB = findViewById(R.id.rbb);
        radioButtonC = findViewById(R.id.rbc);
        radioButtonD = findViewById(R.id.rbd);
        Button bt_pre =  findViewById(R.id.bt_pre);
        Button bt_ok =  findViewById(R.id.bt_ok);
        Button bt_next =  findViewById(R.id.bt_next);
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position !=list.size()-1){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ExamActivity.this)
                            .setTitle("Tips")
                            .setMessage("You haven't finished yet, please try again")
                            .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                    dialog.create().show();
                }else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ExamActivity.this)
                            .setTitle("Tips")
                            .setMessage("Confirm Submission?")
                            .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                     showResult();
                                }
                            });
                    dialog.create().show();
                }
            }


        });
        bt_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position ==0){
                }else{
                    position--;
                    handler.sendEmptyMessage(0);
                }
            }
        });
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position ==list.size()-1){
                    Toast.makeText(ExamActivity.this,"Already the last question",Toast.LENGTH_SHORT).show();
                }else{
                    String ans = getAns();
                    if (TextUtils.isEmpty(ans)){
                        Toast.makeText(ExamActivity.this,"Please choose an answer",Toast.LENGTH_SHORT).show();
                    }else {
                        if (getAns().equals(list.get(position).getOk())){
                            succeed++;
                        }else{
                            new AddTask().execute(list.get(position));
                        }
                        position++;
                        handler.sendEmptyMessage(0);
                    }
                }
            }
        });
    }
    private void showResult() {
        new AlertDialog.Builder(this)
                .setTitle("Tips")
                .setMessage("You answered "+succeed+ " questions correctly")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                })
                .create().show();
    }
    private String getAns(){
        if (radioButtonA.isChecked()){
            return "a";
        }else if (radioButtonB.isChecked()){
            return "b";
        }else if (radioButtonC.isChecked()){
            return "c";
        }else if (radioButtonD.isChecked()){
            return "d";
        }
        return "";
    }
    private void init() {
        assetManager = getAssets();
        try{
            files = assetManager.list("");
        }catch(IOException e){

        }
        new Thread(){
            @Override
            public void run() {
                super.run();
                initData(files);
            }
        }.run();
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if (list.size()>0) {
                        tv_tile.setText("Q"+(position +1)+"/"+list.size());
                        title.setText(list.get(position).getTitle());
                        radioButtonA.setText("A:"+list.get(position).getA());
                        radioButtonB.setText("B:"+list.get(position).getB());
                        radioButtonC.setText("C:"+list.get(position).getC());
                        radioButtonD.setText("D:"+list.get(position).getD());
                    }
                    break;
            }
        }
    };
    private void initData(String [] files) {

            for (String name:files){
                if (name.endsWith(".json")&&name.startsWith("exam")){
                    try {
                        InputStream is = assetManager.open(name);
                        InputStreamReader inputStreamReader=new InputStreamReader(is,"utf-8");
                        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                        String line;
                        StringBuilder stringBuilder=new StringBuilder();
                        while ((line=bufferedReader.readLine())!=null){
                            stringBuilder.append(line);
                        }
                        bufferedReader.close();
                        inputStreamReader.close();
                         List<Question> dataList = new ArrayList<Question>();
                        JSONArray jsonArray = new JSONObject(stringBuilder.toString()).getJSONArray("exam");
                        for(int i=0; i< jsonArray.length(); i++){
                            JSONObject obj = (JSONObject) jsonArray.get(i);
                            dataList.add(new Question(obj));
                        }
                        Random random = new Random();
                        int size = dataList.size();
                        Set<Integer> totals = new HashSet<Integer>();

                        while (totals.size() < 10) {
                            totals.add(random.nextInt(size));
                        }
                        Iterator iterator = totals.iterator();
                        while (iterator.hasNext()) {
                            int next = (int) iterator.next();
                            list.add(dataList.get(next));
                        }
                    } catch (IOException e) {

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        handler.sendEmptyMessage(0);
    }

    private class AddTask extends AsyncTask<Question, Void, Void> {

        @Override
        protected Void doInBackground(Question... voids) {
            Question questions =voids[0];
            AppDatabase.getDatabase(ExamActivity.this).dao().insertError(questions);
            return null;
        }




    }
}
