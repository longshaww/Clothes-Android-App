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

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.huflit.clothes.API.ApiService;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.Utils.GetUserSharePreferences;
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
        init();
    }

    private void init() {
        nameCustomer = findViewById(R.id.name_customer_info_change);
        addressCustomer = findViewById(R.id.address_customer_info_change);
        phoneCustomer = findViewById(R.id.phone_customer_info_change);

        toolbar = findViewById(R.id.toolbar_back_to_customer_info_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCustomerInfoActivity.this, ChangeCustomerInfoActivity.class);
                startActivity(intent);
                finish();
            }
        });
        getSupportActionBar().setTitle("New customer info");
        changeCustomerInfoBtn = findViewById(R.id.btn_change_customer_info);
        changeCustomerInfoBtn.setOnClickListener(this::addNewCustomerInfoSubmit);
        user = GetUserSharePreferences.handleSharePreferences(getApplicationContext());
    }

    private void addNewCustomerInfoSubmit(View view) {
        String name = nameCustomer.getEditText().getText().toString();
        String address = addressCustomer.getEditText().getText().toString();
        String phone = phoneCustomer.getEditText().getText().toString();
        if (TextUtils.isEmpty(name)) {
            nameCustomer.setError("Bạn cần phải nhập tên");
        }
        if (TextUtils.isEmpty(address)) {
            addressCustomer.setError("Bạn cần phải nhập địa chỉ");
        }
        if (TextUtils.isEmpty(phone)) {
            phoneCustomer.setError("Bạn cần phải nhập sđt");
        }
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(phone)) {
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
                    Intent intent = new Intent(AddCustomerInfoActivity.this, ChangeCustomerInfoActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Toast.makeText(AddCustomerInfoActivity.this, "Something wrong ~!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
