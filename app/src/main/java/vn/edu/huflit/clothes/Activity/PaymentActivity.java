package vn.edu.huflit.clothes.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.huflit.clothes.API.ApiService;
import vn.edu.huflit.clothes.Adapter.CartAdapter;
import vn.edu.huflit.clothes.CartHelper;
import vn.edu.huflit.clothes.MainActivity;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.Utils.GetUserSharePreferences;
import vn.edu.huflit.clothes.models.Bill;
import vn.edu.huflit.clothes.models.CreateBillDTO;
import vn.edu.huflit.clothes.models.Cart;
import vn.edu.huflit.clothes.models.User;

public class PaymentActivity extends AppCompatActivity implements CartAdapter.Listener {

    Toolbar toolbar;
    RecyclerView checkoutRcv;
    CartAdapter cartAdapter;
    TextView cartCount, nameCheckout, addressCheckout, phoneCheckout, dateDelivery, subTotalPrice, totalPrice, changeCustomerText;
    CartHelper cartHelper;
    Button confirmCheckoutButton;
    LinearProgressIndicator loadingCheckout;
    RecyclerView infoCustomerRcv;


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

    @Override
    public void onResume() {
        super.onResume();
    }

    public void init() {
        nameCheckout = findViewById(R.id.name_checkout);
        addressCheckout = findViewById(R.id.address_checkout);
        phoneCheckout = findViewById(R.id.phone_checkout);
        dateDelivery = findViewById(R.id.date_delivery);
        subTotalPrice = findViewById(R.id.sub_total_checkout);
        totalPrice = findViewById(R.id.total_checkout);
        loadingCheckout = findViewById(R.id.loading_checkout);
        changeCustomerText = findViewById(R.id.change_customer_bill_info);
        confirmCheckoutButton = findViewById(R.id.confirm_button_checkout);
        infoCustomerRcv = findViewById(R.id.info_customer_rcv);
        confirmCheckoutButton.setOnClickListener(this::onConfirmCheckout);
        changeCustomerText.setOnClickListener(this::onClickChangeCustomerInfoBil);
    }


    public void initCartCheckOutRcv() {
        checkoutRcv = findViewById(R.id.checkoutRcv);
        cartHelper = new CartHelper(getBaseContext());
        cartAdapter = new CartAdapter(getBaseContext(), cartHelper.getAllProductCart(), this::onClick, null, true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        checkoutRcv.setAdapter(cartAdapter);
        checkoutRcv.setLayoutManager(linearLayoutManager);
        cartCount = findViewById(R.id.cartCountCheckout);
        cartCount.setText(Integer.toString(cartAdapter.cartCount()) + " ITEMS");
    }

    public void setTextToView() {
        User user = GetUserSharePreferences.handleSharePreferences(getApplicationContext());
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

    public void onClickChangeCustomerInfoBil(View view) {
        Intent intent = new Intent(this, CustomerInfoActivity.class);
        startActivity(intent);
    }


    public void onConfirmCheckout(View view) {
        User user = GetUserSharePreferences.handleSharePreferences(getApplicationContext());
        CreateBillDTO bill = new CreateBillDTO(user.getID(), user.getName(), user.email, user.getPhoneNumber(), user.getAddress(), "COD", cartHelper.responseProducts());
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


    public void sendBill(CreateBillDTO bill) {
        ApiService.apiService.postBill(bill).enqueue(new Callback<Bill>() {
            @Override
            public void onResponse(Call<Bill> call, Response<Bill> response) {
                Toast.makeText(PaymentActivity.this, "Hoàn tất", Toast.LENGTH_SHORT).show();
                cartHelper.clearCart();
                loadingCheckout.setVisibility(View.INVISIBLE);
                sendNotification();
                MainActivity.bottomNavigationView.getOrCreateBadge(R.id.navigation_cart).setNumber(cartHelper.cartCount());
            }

            @Override
            public void onFailure(Call<Bill> call, Throwable t) {
                Toast.makeText(PaymentActivity.this, "Something wrong ~!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void sendNotification() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        String CHANNEL_ID = getResources().getString(R.string.channel_id);
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, getResources().getString(R.string.channel_name), NotificationManager.IMPORTANCE_HIGH);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);
        Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentText("Cảm ơn bạn đã lựa chọn HighClub !")
                .setContentTitle("Thư cảm ơn")
                .setContentIntent(pendingIntent)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(R.drawable.app_small_logo)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(1, notification);
    }
}