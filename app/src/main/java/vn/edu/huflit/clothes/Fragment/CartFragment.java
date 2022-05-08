package vn.edu.huflit.clothes.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import vn.edu.huflit.clothes.Adapter.CartAdapter;
import vn.edu.huflit.clothes.CartHelper;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.models.Cart;

public class CartFragment extends Fragment implements CartAdapter.Listener {
    private View mView;
    RecyclerView rcvCart;
    CartAdapter cartAdapter;
    TextView totalPrice;
    Button updateTotalPrice;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_cart, container, false);
        init();
        return mView;
    }

    private void init() {
        CartHelper cartHelper = new CartHelper(getContext());
        totalPrice = mView.findViewById(R.id.totalPrice);
        updateTotalPrice = mView.findViewById(R.id.updateTotalPrice);
        updateTotalPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double total = cartHelper.getAllProductCart().stream().mapToDouble(cart -> cart.getPrice() * cart.getQty()).sum();
                totalPrice.setText(total.toString());
            }
        });

        rcvCart = mView.findViewById(R.id.rcvCart);
        cartAdapter = new CartAdapter(getContext(), cartHelper.getAllProductCart(), this::onClick);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvCart.setAdapter(cartAdapter);
        rcvCart.setLayoutManager(linearLayoutManager);
        Double total = cartHelper.getAllProductCart().stream().mapToDouble(cart -> cart.getPrice() * cart.getQty()).sum();
        totalPrice.setText(total.toString());
    }


    @Override
    public void onClick(Cart cart) {

    }
}
