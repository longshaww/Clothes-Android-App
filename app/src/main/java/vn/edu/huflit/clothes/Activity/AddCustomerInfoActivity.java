package vn.edu.huflit.clothes.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.huflit.clothes.API.ApiService;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.Utils.ErrorContent;
import vn.edu.huflit.clothes.Utils.GetUserSharePreferences;
import vn.edu.huflit.clothes.Utils.Validation;
import vn.edu.huflit.clothes.models.Customer;
import vn.edu.huflit.clothes.models.User;

public class AddCustomerInfoActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button changeCustomerInfoBtn;
    TextInputLayout nameCustomer, addressCustomer, phoneCustomer;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer_info);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Customer Info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    private void init() {
        nameCustomer = findViewById(R.id.name_customer_info_change);
        addressCustomer = findViewById(R.id.address_customer_info_change);
        phoneCustomer = findViewById(R.id.phone_customer_info_change);

        changeCustomerInfoBtn = findViewById(R.id.btn_change_customer_info);
        changeCustomerInfoBtn.setOnClickListener(this::addNewCustomerInfoSubmit);
        user = GetUserSharePreferences.handleSharePreferences(getApplicationContext());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void addNewCustomerInfoSubmit(View view) {
        String name = nameCustomer.getEditText().getText().toString();
        String address = addressCustomer.getEditText().getText().toString();
        String phone = phoneCustomer.getEditText().getText().toString();
        if (!Validation.isValidName(name)) {
            nameCustomer.setError(ErrorContent.invalidName);
        }
        if (!Validation.isValidAddress(address)) {
            addressCustomer.setError(ErrorContent.invalidAddress);
        }
        if (!Validation.isValidPhoneNumber(phone)) {
            phoneCustomer.setError(ErrorContent.invalidPhone);
        }
        if (Validation.isValidName(name) && Validation.isValidAddress(address) && Validation.isValidPhoneNumber(phone)) {
            nameCustomer.setError(null);
            addressCustomer.setError(null);
            phoneCustomer.setError(null);
            Customer customer = user.getCustomer();
            customer.setNameCustomer(name);
            customer.setPhoneNumber(phone);
            customer.setAddress(address);
            customer.setUserID(user.getID());
            sendNewCustomer(customer);
        }
    }

    private void sendNewCustomer(Customer customer) {
        ApiService.apiService.postNewCustomer(customer).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(AddCustomerInfoActivity.this, CustomerInfoActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Thêm thông tin thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Thêm thông tin thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Toast.makeText(AddCustomerInfoActivity.this, "Something wrong ~!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
