package vn.edu.huflit.clothes.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.huflit.clothes.API.ApiService;
import vn.edu.huflit.clothes.Adapter.ProductAdapter;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.models.Product;

public class AccessoriesActivity extends AppCompatActivity implements ProductAdapter.Listener {
    private RecyclerView AccessoriesCollectionView;
    private ProductAdapter productAdapter;
    private ArrayList<Product> listProducts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        initRecyclerView();
        getAccessories();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Accessories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void initRecyclerView() {
        listProducts = new ArrayList<>();
        AccessoriesCollectionView = findViewById(R.id.allProductView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(AccessoriesActivity.this, 2);
        AccessoriesCollectionView.setLayoutManager(gridLayoutManager);
        productAdapter = new ProductAdapter(AccessoriesActivity.this, listProducts, AccessoriesActivity.this::onClick);
        AccessoriesCollectionView.setAdapter(productAdapter);
    }

    private void getAccessories() {
        ApiService.apiService.getAccessories().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    listProducts.clear();
                    listProducts.addAll(response.body());
                    productAdapter.setList(listProducts);
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
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("idProduct", product.get_id());
        startActivity(intent);
    }
}
