package vn.edu.huflit.clothes.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.huflit.clothes.API.ApiService;
import vn.edu.huflit.clothes.Activity.DetailActivity;
import vn.edu.huflit.clothes.Activity.PaymentActivity;
import vn.edu.huflit.clothes.Adapter.CartAdapter;
import vn.edu.huflit.clothes.Adapter.ProductAdapter;
import vn.edu.huflit.clothes.CartHelper;
import vn.edu.huflit.clothes.MainActivity;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.models.Cart;
import vn.edu.huflit.clothes.models.Product;

public class CartFragment extends Fragment implements CartAdapter.Listener, CartAdapter.UpdateTotal {
    private View mView;
    RecyclerView rcvCart;
    CartAdapter cartAdapter;
    TextView subTotalPrice, totalPrice;
    ArrayList listNewArrivals;
    RecyclerView newArrivalsRcv;
    ProductAdapter productAdapter;
    CartHelper cartHelper;
    Button btnCheckOut;
    public static View cartFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        CartHelper cartHelper = new CartHelper(getContext());
        cartAdapter.setList(cartHelper.getAllProductCart());
        checkCartUpdate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_cart, container, false);
        init();
        return mView;
    }

    private void init() {
        cartFragment = mView.findViewById(R.id.cart_fragment);
        subTotalPrice = mView.findViewById(R.id.subTotalPrice);
        totalPrice = mView.findViewById(R.id.totalPrice);
        btnCheckOut = mView.findViewById(R.id.button_check_out);
        btnCheckOut.setOnClickListener(this::onCheckoutClick);
        cartHelper = new CartHelper(getContext());
        checkCartUpdate();
        rcvCart = mView.findViewById(R.id.rcvCart);
        newArrivalsRcv = mView.findViewById(R.id.cartNewArrivalsRcv);
        initCartRcv();
        initNewArrivalsRcv();
    }

    public void checkCartUpdate() {
        Double sub = cartHelper.getAllProductCart().stream().mapToDouble(cart -> cart.getPrice() * cart.getQty()).sum();
        Double total = sub + 35;
        subTotalPrice.setText(sub.toString() + "00 VNĐ");
        totalPrice.setText(total.toString() + "00 VNĐ");
    }

    public void initCartRcv() {
        CartHelper cartHelper = new CartHelper(getContext());
        cartAdapter = new CartAdapter(getContext(), cartHelper.getAllProductCart(), this::onClick, this::updateCartTotal, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvCart.setAdapter(cartAdapter);
        rcvCart.setLayoutManager(linearLayoutManager);
    }

    public void initNewArrivalsRcv() {
        listNewArrivals = new ArrayList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        newArrivalsRcv.setLayoutManager(linearLayoutManager);
        productAdapter = new ProductAdapter(getContext(), listNewArrivals, this::onClick);
        newArrivalsRcv.setAdapter(productAdapter);
        getNewArrival();
    }

    public void getNewArrival() {
        ApiService.apiService.getNewArrivals().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    productAdapter.setList(response.body());
                    productAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getContext(), "Something wrong ~!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onCheckoutClick(View view) {
        if (cartAdapter.cartCount() < 1) {
            Toast.makeText(getContext(), "Your cart is empty", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getContext(), PaymentActivity.class);
            intent.putExtra("subTotal", subTotalPrice.getText().toString());
            intent.putExtra("total", totalPrice.getText().toString());
            startActivity(intent);
        }
    }

    public void onClick(Product product) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("idProduct", product.get_id());
        startActivity(intent);
    }


    @Override
    public void onClick(Cart cart) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("idProduct", cart.getId());
        startActivity(intent);
    }

    @Override
    public void updateCartTotal(String subTotal, String total) {
        subTotalPrice.setText(subTotal + "00 VNĐ");
        totalPrice.setText(total + "00 VNĐ");
        MainActivity.bottomNavigationView.getOrCreateBadge(R.id.navigation_cart).setNumber(cartAdapter.cartCount());
    }
}
