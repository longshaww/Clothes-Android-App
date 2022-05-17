package vn.edu.huflit.clothes.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.huflit.clothes.API.ApiService;
import vn.edu.huflit.clothes.Activity.TopActivity;
import vn.edu.huflit.clothes.Adapter.CartAdapter;
import vn.edu.huflit.clothes.Adapter.ProductAdapter;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.models.Cart;
import vn.edu.huflit.clothes.models.Product;

public class SearchFragment extends Fragment implements ProductAdapter.Listener {
    private View mView;
    RecyclerView searchRcv;
    EditText searchInput;
    ArrayList listProducts;
    RecyclerView productRcv;
    ProductAdapter productAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_search, container, false);
        init();
        return mView;
    }

    public void init(){
        listProducts = new ArrayList();
        searchRcv = mView.findViewById(R.id.searchRcv);
        searchInput = mView.findViewById(R.id.search_input);
        productRcv = mView.findViewById(R.id.searchRcv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        productRcv.setLayoutManager(gridLayoutManager);
        productAdapter = new ProductAdapter(getContext(),listProducts, this::onClick);
        productRcv.setAdapter(productAdapter);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ApiService.apiService.search(editable.toString()).enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        productAdapter.setList(response.body());
                        productAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public void onClick(Product product) {

    }
}
