package vn.edu.huflit.clothes.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.Headers;
import okhttp3.internal.http2.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.huflit.clothes.API.ApiService;
import vn.edu.huflit.clothes.Fragment.CartFragment;
import vn.edu.huflit.clothes.MainActivity;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.Utils.ErrorContent;
import vn.edu.huflit.clothes.Utils.Validation;
import vn.edu.huflit.clothes.models.User;
import vn.edu.huflit.clothes.models.UserLoginDTO;

public class SigninActivity extends AppCompatActivity {

    Button button_sign_up, button_sign_in;
    Toolbar toolbar;
    TextInputLayout passwordInput, emailInput;
    SharedPreferences sharedPref;
    Gson gson;
    LinearProgressIndicator loadingSignIn;
    View signInActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
    }

    public void init() {
        signInActivity = findViewById(R.id.sign_in_activity);
        button_sign_up = findViewById(R.id.button_sign_up);
        button_sign_up.setOnClickListener(this::onClickSignUp);

        button_sign_in = findViewById(R.id.button_sign_in);
        button_sign_in.setOnClickListener(this::onClickSignIn);

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);

        sharedPref = getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        loadingSignIn = findViewById(R.id.loading_sign_in);
        gson = new Gson();
        checkLogin();
    }

    public void checkLogin() {
        if (sharedPref.getString("user", null) != null && sharedPref.getString("userCookie", null) != null) {
            loadingSignIn.setVisibility(View.INVISIBLE);
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
        String getEmail = emailInput.getEditText().getText().toString().trim();
        String getPassword = passwordInput.getEditText().getText().toString().trim();
        if (!Validation.isValidEmail(getEmail)) {
            emailInput.setError(ErrorContent.invalidEmail);
        }
        if (!Validation.isValidPassword(getPassword)) {
            passwordInput.setError(ErrorContent.invalidPassword);
        }
        if (Validation.isValidEmail(getEmail) && Validation.isValidPassword(getPassword)) {
            emailInput.setError(null);
            passwordInput.setError(null);
            UserLoginDTO user = new UserLoginDTO(getEmail, getPassword);
            loadingSignIn.setVisibility(View.VISIBLE);
            requestLogin(user);
        }
    }

    public void requestLogin(UserLoginDTO user) {
        ApiService.apiService.login(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    loadingSignIn.setVisibility(View.INVISIBLE);
                    User user = response.body();
                    String cookie = response.headers().get("Set-Cookie");
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("userCookie", cookie);
                    editor.putString("user", gson.toJson(user));
                    editor.commit();
                    checkLogin();
                } else {
                    loadingSignIn.setVisibility(View.INVISIBLE);
                    Snackbar.make(signInActivity, "Tài khoản hoặc mật khẩu không đúng"
                            , Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Snackbar.make(signInActivity, getResources().getString(R.string.response_fail)
                        , Snackbar.LENGTH_LONG).show();
            }
        });
    }
}

