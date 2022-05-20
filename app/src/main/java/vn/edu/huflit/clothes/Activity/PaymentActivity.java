package vn.edu.huflit.clothes.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.huflit.clothes.API.ApiService;
import vn.edu.huflit.clothes.Adapter.CartAdapter;
import vn.edu.huflit.clothes.CartHelper;
import vn.edu.huflit.clothes.MainActivity;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.models.Bill;
import vn.edu.huflit.clothes.models.BillDTO;
import vn.edu.huflit.clothes.models.Cart;
import vn.edu.huflit.clothes.models.ProductBill;
import vn.edu.huflit.clothes.models.User;

public class PaymentActivity extends AppCompatActivity implements CartAdapter.Listener {

    Toolbar toolbar;
    RecyclerView checkoutRcv;
    CartAdapter cartAdapter;
    TextView cartCount, nameCheckout, addressCheckout, phoneCheckout, dateDelivery, subTotalPrice, totalPrice;
    SharedPreferences sharedPreferences;
    CartHelper cartHelper;
    Gson gson;
    Button confirmCheckoutButton;
    LinearProgressIndicator loadingCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(PaymentActivity.this, .class);
//                startActivity(intent);
//                finish();
            }
        });
        getSupportActionBar().setTitle("Payment");
        init();
        initCartCheckOutRcv();
        setTextToView();
    }

    public void init() {
        nameCheckout = findViewById(R.id.name_checkout);
        addressCheckout = findViewById(R.id.address_checkout);
        phoneCheckout = findViewById(R.id.phone_checkout);
        dateDelivery = findViewById(R.id.date_delivery);
        subTotalPrice = findViewById(R.id.sub_total_checkout);
        totalPrice = findViewById(R.id.total_checkout);
        loadingCheckout = findViewById(R.id.loading_checkout);
        confirmCheckoutButton = findViewById(R.id.confirm_button_checkout);
        confirmCheckoutButton.setOnClickListener(this::onConfirmCheckout);
    }

    public void initCartCheckOutRcv() {
        sharedPreferences = getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        checkoutRcv = findViewById(R.id.checkoutRcv);
        cartHelper = new CartHelper(getBaseContext());
        cartAdapter = new CartAdapter(getBaseContext(), cartHelper.getAllProductCart(), this::onClick, null, true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        checkoutRcv.setAdapter(cartAdapter);
        checkoutRcv.setLayoutManager(linearLayoutManager);
        cartCount = findViewById(R.id.cartCountCheckout);
        cartCount.setText(Integer.toString(cartAdapter.cartCount()) + " ITEMS");
    }

    public User handleSharePreferences() {
        gson = new Gson();
        String userJSON = sharedPreferences.getString("user", "0");//second parameter is necessary ie.,Value to return if this preference does not exist.
        if (userJSON == null) {
            Toast.makeText(this, "You haven't login !", Toast.LENGTH_SHORT).show();
        }
        User user = gson.fromJson(userJSON, User.class);
        return user;
    }

    public void setTextToView() {
        User user = handleSharePreferences();
        nameCheckout.setText(user.getName());
        addressCheckout.setText(user.getAddress());
        phoneCheckout.setText(user.getPhoneNumber());
        DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("E, MMM dd yyyy");
        LocalDate dateBegin = LocalDate.now();
        LocalDate dateEnd = LocalDate.now();
        dateBegin.plusDays(1);
        dateEnd.plusDays(5);
        String formattedDateBegin = dateBegin.format(formattedDate);
        String formattedDateEnd = dateEnd.format(formattedDate);
        dateDelivery.setText(formattedDateBegin + " - " + formattedDateEnd);
        String intentSubTotal = getIntent().getExtras().getString("subTotal");
        String intentTotal = getIntent().getExtras().getString("total");
        subTotalPrice.setText(intentSubTotal);
        totalPrice.setText(intentTotal);
    }

    @Override
    public void onClick(Cart cart) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("idProduct", cart.getId());
        startActivity(intent);
    }

    public void onConfirmCheckout(View view) {
        User user = handleSharePreferences();
        BillDTO bill = new BillDTO(user.getName(), user.email, user.getPhoneNumber(), user.getAddress(), "COD", cartHelper.responseProducts());
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Bạn đã kiểm tra thông tin chưa ?")
                .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        loadingCheckout.setVisibility(View.VISIBLE);
                        sendBill(bill);
                        Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }).setCancelable(true)
                .setNegativeButton("Thoát", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    public void sendBill(BillDTO bill) {
        ApiService.apiService.postBill(bill).enqueue(new Callback<Bill>() {
            @Override
            public void onResponse(Call<Bill> call, Response<Bill> response) {
                Toast.makeText(PaymentActivity.this, "Hoàn tất", Toast.LENGTH_SHORT).show();
                cartHelper.clearCart();
                loadingCheckout.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Bill> call, Throwable t) {
                Toast.makeText(PaymentActivity.this, "Something wrong ~!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}