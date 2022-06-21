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

public class OuterwearActivity extends AppCompatActivity implements ProductAdapter.Listener {
    private RecyclerView OuterwearCollectionView;
    private ProductAdapter productAdapter;
    private ArrayList<Product> listProducts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        initRecyclerView();
        getOuterWears();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Outerwears");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void initRecyclerView() {
        listProducts = new ArrayList<>();
        OuterwearCollectionView = findViewById(R.id.allProductView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(OuterwearActivity.this, 2);
        OuterwearCollectionView.setLayoutManager(gridLayoutManager);
        productAdapter = new ProductAdapter(OuterwearActivity.this, listProducts, OuterwearActivity.this::onClick);
        OuterwearCollectionView.setAdapter(productAdapter);
    }

    private void getOuterWears() {
        ApiService.apiService.getOuterWears().enqueue(new Callback<List<Product>>() {
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
