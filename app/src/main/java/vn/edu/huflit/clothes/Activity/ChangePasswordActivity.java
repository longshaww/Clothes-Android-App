package vn.edu.huflit.clothes.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.huflit.clothes.API.ApiService;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.Utils.ErrorContent;
import vn.edu.huflit.clothes.Utils.GetUserSharePreferences;
import vn.edu.huflit.clothes.Utils.Validation;
import vn.edu.huflit.clothes.models.ChangePasswordDTO;
import vn.edu.huflit.clothes.models.User;

public class ChangePasswordActivity extends AppCompatActivity {
    TextInputLayout currentPasswordInput, newPasswordInput, confirmPasswordInput;
    Button changePasswordBtn;
    View changePasswordActivity;
    SharedPreferences sharedPreferences;
    Gson gson;
    LinearProgressIndicator loadingChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Change Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void init() {
        gson = new Gson();
        sharedPreferences = getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        changePasswordActivity = findViewById(R.id.change_password_activity);
        currentPasswordInput = findViewById(R.id.current_password_change);
        newPasswordInput = findViewById(R.id.new_password_change);
        confirmPasswordInput = findViewById(R.id.confirm_password_change);
        loadingChangePassword = findViewById(R.id.loading_change_password);
        changePasswordBtn = findViewById(R.id.btn_change_password);
        changePasswordBtn.setOnClickListener(this::onChangePasswordClick);
    }

    public void onChangePasswordClick(View view) {
        String currentPassword = currentPasswordInput.getEditText().getText().toString();
        String newPassword = newPasswordInput.getEditText().getText().toString();
        String confirmPassword = confirmPasswordInput.getEditText().getText().toString();
        if (TextUtils.isEmpty(currentPassword)) {
            currentPasswordInput.setError(ErrorContent.emptyCurrentPassword);
        }
        if (!Validation.isValidPassword(newPassword)) {
            newPasswordInput.setError(ErrorContent.emptyNewPassword);
        }
        if (!confirmPassword.equals(newPassword)) {
            confirmPasswordInput.setError(ErrorContent.invalidPasswordConfirm);
        }
        if (!TextUtils.isEmpty(currentPassword) && Validation.isValidPassword(newPassword) && confirmPassword.equals(newPassword)) {
            currentPasswordInput.setError(null);
            newPasswordInput.setError(null);
            confirmPasswordInput.setError(null);
            loadingChangePassword.setVisibility(View.VISIBLE);
            requestChangePassword(currentPassword, newPassword);
        }
    }

    public void requestChangePassword(String currentPassword, String newPassword) {
        User user = GetUserSharePreferences.handleSharePreferences(this);
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO(user.getID(), currentPassword, newPassword);
        ApiService.apiService.changePassword(changePasswordDTO).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User resUser = response.body();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user", gson.toJson(resUser));
                    editor.commit();
                    loadingChangePassword.setVisibility(View.INVISIBLE);
                    Snackbar.make(changePasswordActivity, "Đổi mật khẩu thành công"
                            , Snackbar.LENGTH_LONG).show();
                } else {
                    loadingChangePassword.setVisibility(View.INVISIBLE);
                    Snackbar.make(changePasswordActivity, "Mật khẩu cũ không đúng"
                            , Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Snackbar.make(changePasswordActivity, "Đổi mật khẩu thất bại"
                        , Snackbar.LENGTH_LONG).show();
            }
        });
    }
}