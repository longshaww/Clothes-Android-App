package vn.edu.huflit.clothes.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.huflit.clothes.API.ApiService;
import vn.edu.huflit.clothes.Activity.DetailActivity;
import vn.edu.huflit.clothes.Adapter.ProductAdapter;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.models.Product;

public class TopActivity extends AppCompatActivity implements ProductAdapter.Listener {
    private RecyclerView topCollectionView;
    private ProductAdapter productAdapter;
    private ArrayList<Product> listProducts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        initRecyclerView();
        getTopList();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Top");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void initRecyclerView() {
        listProducts = new ArrayList<>();
        topCollectionView = findViewById(R.id.allProductView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(TopActivity.this, 2);
        topCollectionView.setLayoutManager(gridLayoutManager);
        productAdapter = new ProductAdapter(TopActivity.this, listProducts, TopActivity.this::onClick);
        topCollectionView.setAdapter(productAdapter);
    }

    public void getTopList() {
        ApiService.apiService.getTops().enqueue(new Callback<List<Product>>() {
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
