package vn.edu.huflit.clothes.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.huflit.clothes.API.ApiService;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.models.User;
import vn.edu.huflit.clothes.models.UserLoginDTO;

public class SigninActivity extends AppCompatActivity {

    Button button_sign_up,button_sign_in;
    Toolbar toolbar;
    TextInputEditText emailInput;
    EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
    }

    public void init(){
        button_sign_up = findViewById(R.id.button_sign_up);
        button_sign_up.setOnClickListener(this::onClickSignUp);

        button_sign_in = findViewById(R.id.button_sign_in);
        button_sign_in.setOnClickListener(this::onClickSignIn);

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);

    }
    public void onClickSignUp(View v) {
        Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickSignIn(View v){
        String getEmail = emailInput.getText().toString();
        String getPassword = passwordInput.getText().toString();
        UserLoginDTO user = new UserLoginDTO(getEmail,getPassword);
        requestLogin(user);
    }

    public void requestLogin (UserLoginDTO user){
        ApiService.apiService.login(user).enqueue(new Callback<UserLoginDTO>() {
            @Override
            public void onResponse(Call<UserLoginDTO> call, Response<UserLoginDTO> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SigninActivity.this,response.body().toString(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserLoginDTO> call, Throwable t) {

            }
        });
    }
}

