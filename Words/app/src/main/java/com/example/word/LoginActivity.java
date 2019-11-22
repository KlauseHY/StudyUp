package com.example.word;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.word.db.AppDatabase;
import com.example.word.model.User;
import com.example.word.model.Words;

import org.w3c.dom.Text;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private EditText etUserName;
    private EditText etPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etUserName.getText().toString();
                String password = etPassword.getText().toString();
                if (TextUtils.isEmpty(name)){
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    return;
                }
                new LoginTask(name,password).execute();
            }
        });

        findViewById(R.id.tv_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        SharedPreferences sp = getSharedPreferences("sign", MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("login",false);
        if (isLogin){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();;
        }
    }

    private class LoginTask extends AsyncTask<Void, Void, User> {


        private String name;
        private String password;
        public LoginTask(String name, String password) {
            this.name = name;
            this.password = password;
        }

        @Override
        protected User doInBackground(Void... voids) {
            return  AppDatabase.getDatabase(LoginActivity.this).dao().login(name,password);
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            if (user!=null){
                SharedPreferences sp = getSharedPreferences("sign", MODE_PRIVATE);
                sp.edit().putBoolean("login",true).commit();
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
            }else{
                Toast.makeText(LoginActivity.this,"failed",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
