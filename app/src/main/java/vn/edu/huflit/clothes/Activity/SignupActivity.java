package vn.edu.huflit.clothes.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.huflit.clothes.API.ApiService;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.models.User;
import vn.edu.huflit.clothes.models.UserRegisterDTO;

public class SignupActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button buttonSignUp;
    TextInputEditText emailInput, nameInput, phoneInput, addressInput, passwordInput, confirmPasswordInput;
    LinearProgressIndicator loadingSignUp;
    View sighUpActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                startActivity(intent);
                finish();
            }
        });
        init();
    }

    public void init() {
        sighUpActivity = findViewById(R.id.sign_up_activity);
        buttonSignUp = findViewById(R.id.button_sign_up);
        buttonSignUp.setOnClickListener(this::onClickSignUp);
        emailInput = findViewById(R.id.et_email);
        nameInput = findViewById(R.id.et_name);
        phoneInput = findViewById(R.id.et_address);
        addressInput = findViewById(R.id.et_address);
        passwordInput = findViewById(R.id.et_password);
        confirmPasswordInput = findViewById(R.id.et_confirm_password);
        loadingSignUp = findViewById(R.id.loading_sign_up);
    }

    public void onClickSignUp(View view) {
        String getEmail = emailInput.getText().toString();
        String getName = nameInput.getText().toString();
        String getPhone = phoneInput.getText().toString();
        String getAddress = addressInput.getText().toString();
        String getPassword = passwordInput.getText().toString();
        String getConfirmPassword = confirmPasswordInput.getText().toString();
        if (SigninActivity.isValidEmail(getEmail) && !TextUtils.isEmpty(getName) && !TextUtils.isEmpty(getPhone)
                && !TextUtils.isEmpty(getAddress) && !TextUtils.isEmpty(getPassword) && !TextUtils.isEmpty(getConfirmPassword)) {
            UserRegisterDTO user = new UserRegisterDTO(getEmail, getName, getPhone, getPassword, getAddress);
            loadingSignUp.setVisibility(View.VISIBLE);
            requestRegister(user);
        } else {
            Snackbar.make(sighUpActivity, "Đăng ký thất bại !"
                    , Snackbar.LENGTH_LONG).show();
        }
    }

    public void requestRegister(UserRegisterDTO user) {
        ApiService.apiService.register(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    loadingSignUp.setVisibility(View.INVISIBLE);
                    Toast.makeText(SignupActivity.this, "Tạo tài khoản thành công !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}