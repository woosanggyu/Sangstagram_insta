package com.example.sangstagramapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


import androidx.appcompat.app.AppCompatActivity;

import com.example.sangstagramapp.login.LoginData;
import com.example.sangstagramapp.login.LoginResponse;
import com.example.sangstagramapp.network.Api;
import com.example.sangstagramapp.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    AutoCompleteTextView editTextemail;
    EditText editTextpssword;

    Button buttonlogin;
    Button facebook;
    Button google;

    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextemail = (AutoCompleteTextView) findViewById(R.id.email_editText);
        editTextpssword = (EditText) findViewById(R.id.password_editText);
        buttonlogin = (Button) findViewById(R.id.email_login_button);
        facebook = (Button) findViewById(R.id.facebook);
        google = (Button) findViewById(R.id.google);

        api = RetrofitClient.getClient().create(Api.class);

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void Login() {
        editTextemail.setError(null);
        editTextpssword.setError(null);

        String email = editTextemail.getText().toString();
        String password = editTextpssword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if(password.isEmpty()) {
           editTextemail.setError("비밀번호를 입력해주세요");
           focusView = editTextemail;
           cancel = true;
        } else if( !isPasswordValid(password)) {
            editTextpssword.setError("6자 이상 비밀번호를 입력해주세요.");
            focusView = editTextemail;
            cancel = true;
        }
        if (email.isEmpty()) {
            editTextemail.setError("이메일을 입력해주세요.");
            focusView = editTextemail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            editTextemail.setError("@를 포함한 유효한 이메일을 입력해주세요.");
            focusView = editTextemail;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            startLogin(new LoginData(email, password));
        }
    }
    private void startLogin(LoginData data) {
        api.userLogin(data).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse result = response.body();
                Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
                }
        });
    }
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }
    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }
}