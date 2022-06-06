package vn.edu.huflit.clothes.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.huflit.clothes.API.ApiService;
import vn.edu.huflit.clothes.Adapter.CustomerInfoBillAdapter;
import vn.edu.huflit.clothes.MainActivity;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.Utils.GetUserSharePreferences;
import vn.edu.huflit.clothes.models.UserIdDTO;
import vn.edu.huflit.clothes.models.Customer;
import vn.edu.huflit.clothes.models.User;

public class CustomerInfoActivity extends AppCompatActivity implements CustomerInfoBillAdapter.Listener {
    RecyclerView infoCustomerRcv;
    CustomerInfoBillAdapter customerInfoBillAdapter;
    ArrayList<Customer> listCustomers;
    User user;
    Button addNewCustomerInfoBtn;
    View view;
    Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_customer_info);
        init();
        initCustomerInfoRcv();
        getCustomerInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        getCustomerInfo();
    }


    private void init() {
        gson = new Gson();
        view = findViewById(R.id.activity_change_customer_info);
        user = GetUserSharePreferences.handleSharePreferences(getApplicationContext());
        listCustomers = new ArrayList<>();
        infoCustomerRcv = findViewById(R.id.info_customer_rcv);
        addNewCustomerInfoBtn = findViewById(R.id.intent_btn_new_customer_info);
        addNewCustomerInfoBtn.setOnClickListener(this::onClickAddNewCustomerInfo);
    }

    private void initCustomerInfoRcv() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        infoCustomerRcv.setLayoutManager(linearLayoutManager);
        customerInfoBillAdapter = new CustomerInfoBillAdapter(this, listCustomers, this);
        infoCustomerRcv.setAdapter(customerInfoBillAdapter);
    }

    private void getCustomerInfo() {
        User user = GetUserSharePreferences.handleSharePreferences(getApplicationContext());
        UserIdDTO customer = new UserIdDTO(user.getID());

        ApiService.apiService.getCustomerInfo(customer).enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (response.isSuccessful()) {
                    listCustomers.clear();
                    listCustomers.addAll(response.body());
                    customerInfoBillAdapter.setList(listCustomers);
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Toast.makeText(CustomerInfoActivity.this, "Something wrong ~!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickAddNewCustomerInfo(View view) {
        Intent intent = new Intent(this, AddCustomerInfoActivity.class);
        startActivity(intent);
    }


    @Override
    public void onDeleteClick(Customer customer) {
        if (customer.get_id().equals(user.customer.get_id())) {
            Snackbar.make(view, "Không thể xóa thông tin này"
                    , Snackbar.LENGTH_LONG).show();
        } else {
            ApiService.apiService.deleteCustomerInfo(customer.get_id()).enqueue(new Callback<Customer>() {
                @Override
                public void onResponse(Call<Customer> call, Response<Customer> response) {
                    if (response.isSuccessful()) {
                        Snackbar.make(view, "Đã xoá thông tin"
                                , Snackbar.LENGTH_LONG).show();
                        getCustomerInfo();
                    }
                }

                @Override
                public void onFailure(Call<Customer> call, Throwable t) {
                    Toast.makeText(CustomerInfoActivity.this, "Something wrong ~!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onEditClick(Customer customer) {
        Intent intent = new Intent(this, EditCustomerInfoActivity.class);
        intent.putExtra("customerId", customer.get_id());
        startActivity(intent);
    }

    @Override
    public void onChoosenClick(Customer customer) {
        SharedPreferences sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        User thisUser = user;
        thisUser.setCustomer(customer);
        editor.putString("user",gson.toJson(thisUser));
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}