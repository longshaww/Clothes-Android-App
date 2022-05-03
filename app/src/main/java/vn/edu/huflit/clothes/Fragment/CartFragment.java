package vn.edu.huflit.clothes.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.huflit.clothes.Adapter.CartAdapter;
import vn.edu.huflit.clothes.CartHelper;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.models.Cart;

public class CartFragment extends Fragment implements CartAdapter.Listener {
    private View mView;
    RecyclerView rcvCart;
    CartAdapter cartAdapter;

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
        rcvCart = mView.findViewById(R.id.rcvCart);
        cartAdapter = new CartAdapter(getContext(), cartHelper.getAllProductCart(), this::onClick);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvCart.setAdapter(cartAdapter);
        rcvCart.setLayoutManager(linearLayoutManager);
    }


    @Override
    public void onClick(Cart cart) {

    }
}
