package vn.edu.huflit.clothes.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.Headers;
import okhttp3.internal.http2.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.huflit.clothes.API.ApiService;
import vn.edu.huflit.clothes.MainActivity;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.models.User;
import vn.edu.huflit.clothes.models.UserLoginDTO;

public class SigninActivity extends AppCompatActivity {

    Button button_sign_up, button_sign_in;
    Toolbar toolbar;
    TextInputEditText emailInput;
    EditText passwordInput;
    SharedPreferences sharedPref;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
    }

    public void init() {
        button_sign_up = findViewById(R.id.button_sign_up);
        button_sign_up.setOnClickListener(this::onClickSignUp);

        button_sign_in = findViewById(R.id.button_sign_in);
        button_sign_in.setOnClickListener(this::onClickSignIn);

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);

        sharedPref = getSharedPreferences("dataLogin", Context.MODE_PRIVATE);

        gson = new Gson();
        checkLogin();
    }

    public void checkLogin(){
        if (sharedPref.getString("user", null) != null && sharedPref.getString("userCookie", null) != null) {
            Intent intent = new Intent(SigninActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void onClickSignUp(View v) {
        Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickSignIn(View v) {
        String getEmail = emailInput.getText().toString();
        String getPassword = passwordInput.getText().toString();
        UserLoginDTO user = new UserLoginDTO(getEmail, getPassword);
        requestLogin(user);
    }

    public void requestLogin(UserLoginDTO user) {
        ApiService.apiService.login(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    String cookie = response.headers().get("Set-Cookie");
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("userCookie", cookie);
                    editor.putString("user", gson.toJson(user));
                    editor.commit();
                    checkLogin();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(SigninActivity.this, "Something wrong ~!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

