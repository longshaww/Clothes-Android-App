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

public class EditCustomerInfoActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button changeCustomerInfoBtn;
    TextInputLayout nameCustomer, addressCustomer, phoneCustomer;
    User user;
    Customer customerGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer_info);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Customer Info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void init() {
        customerGlobal = (Customer) getIntent().getSerializableExtra("customer");
        nameCustomer = findViewById(R.id.name_customer_info_change_edit);
        addressCustomer = findViewById(R.id.address_customer_info_change_edit);
        phoneCustomer = findViewById(R.id.phone_customer_info_change_edit);

        nameCustomer.getEditText().setText(customerGlobal.getNameCustomer());
        addressCustomer.getEditText().setText(customerGlobal.getAddress());
        phoneCustomer.getEditText().setText(customerGlobal.getPhoneNumber());

        changeCustomerInfoBtn = findViewById(R.id.btn_change_customer_info_edit);
        changeCustomerInfoBtn.setOnClickListener(this::editNewCustomerInfoSubmit);
        user = GetUserSharePreferences.handleSharePreferences(getApplicationContext());
    }

    private void editNewCustomerInfoSubmit(View view) {
        String name = nameCustomer.getEditText().getText().toString();
        String address = addressCustomer.getEditText().getText().toString();
        String phone = phoneCustomer.getEditText().getText().toString();
        if (Validation.isValidName(name)) {
            nameCustomer.setError(ErrorContent.invalidName);
        }
        if (Validation.isValidAddress(address)) {
            addressCustomer.setError(ErrorContent.invalidAddress);
        }
        if (Validation.isValidPhoneNumber(phone)) {
            phoneCustomer.setError(ErrorContent.invalidPhone);
        }
        if (Validation.isValidName(name) && Validation.isValidPhoneNumber(phone) && Validation.isValidAddress(address)) {
            nameCustomer.setError(null);
            addressCustomer.setError(null);
            phoneCustomer.setError(null);
            Customer customer = new Customer(name, address, phone);
            sendEditCustomer(customer);
        }
    }

    private void sendEditCustomer(Customer customer) {
        ApiService.apiService.editCustomerInfo(customerGlobal.get_id(), customer).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(EditCustomerInfoActivity.this, CustomerInfoActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Sửa thông tin thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Sửa thông tin thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Toast.makeText(EditCustomerInfoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
