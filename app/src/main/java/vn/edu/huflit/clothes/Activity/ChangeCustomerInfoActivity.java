package vn.edu.huflit.clothes.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.huflit.clothes.API.ApiService;
import vn.edu.huflit.clothes.Adapter.CustomerInfoBillAdapter;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.Utils.GetUserSharePreferences;
import vn.edu.huflit.clothes.models.BillHistoryDTO;
import vn.edu.huflit.clothes.models.Customer;
import vn.edu.huflit.clothes.models.User;

public class ChangeCustomerInfoActivity extends AppCompatActivity implements CustomerInfoBillAdapter.Listener {
    RecyclerView infoCustomerRcv;
    CustomerInfoBillAdapter customerInfoBillAdapter;
    ArrayList<Customer> listCustomers;
    User user;
    Button addNewCustomerInfoBtn;


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
        BillHistoryDTO customer = new BillHistoryDTO(user.getID());

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
                Toast.makeText(ChangeCustomerInfoActivity.this, "Something wrong ~!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickAddNewCustomerInfo(View view){
        Intent intent = new Intent(this,AddCustomerInfoActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(Customer customer) {

    }
}