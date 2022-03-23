package vn.edu.huflit.clothes.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.huflit.clothes.API.ApiService;
import vn.edu.huflit.clothes.Adapter.ProductAdapter;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.models.Product;


public class CollectionActivity extends AppCompatActivity implements ProductAdapter.Listener{
    private RecyclerView allProductView;
    private ProductAdapter productAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        initRecyclerView();
    }

    public void initRecyclerView(){
        allProductView = findViewById(R.id.allProductView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        allProductView.setLayoutManager(gridLayoutManager);
        getAllProduct();
    }


    public void getAllProduct() {
        ApiService.apiService.getAllProduct().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    productAdapter = new ProductAdapter(CollectionActivity.this,response.body(),CollectionActivity.this::onClick);
                    allProductView.setAdapter(productAdapter);
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
        Intent intent = new Intent(this,DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_product",product);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}