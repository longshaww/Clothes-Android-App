package vn.edu.huflit.clothes.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import vn.edu.huflit.clothes.Adapter.ProductAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.huflit.clothes.API.ApiService;
import vn.edu.huflit.clothes.Adapter.ProductAdapter;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.models.Product;


public class HomeFragment extends Fragment implements ProductAdapter.Listener{
    private RecyclerView allProductView;
    private ProductAdapter productAdapter;
    private View mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        allProductView = mView.findViewById(R.id.allProductView);
        productAdapter = new ProductAdapter(getContext());
        getAllProduct();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        allProductView.setLayoutManager(gridLayoutManager);
//        productAdapter.setList(getAllProduct());
//        allProductView.setAdapter(productAdapter);
        return mView;
    }

    public void getAllProduct() {
        ApiService.apiService.getAllProduct().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productAdapter.setList(response.body());
                allProductView.setAdapter(productAdapter);
                if (response.body() != null) {
                    System.out.println(productAdapter.getList().size());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                System.out.println("Call API Failed");
            }
        });
    }


    @Override
    public void onClick(Product product) {

    }
}