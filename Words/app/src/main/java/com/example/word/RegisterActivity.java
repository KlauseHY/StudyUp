package com.example.word;

import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {
    private EditText etUserName;
    private EditText etPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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
                new SignupTask(name,password).execute();
            }
        });
    }
    private class SignupTask extends AsyncTask<Void, Void, Boolean> {


        private String name;
        private String password;
        public SignupTask(String name, String password) {
            this.name = name;
            this.password = password;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            User user = new User();
            user.setName(name);
            user.setPassword(password);
            try {
                AppDatabase.getDatabase(RegisterActivity.this).dao().signUp(user);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean flag) {
            super.onPostExecute(flag);
            if (flag){

                finish();
            }else{
                Toast.makeText(RegisterActivity.this,"failed",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
