package vn.edu.huflit.clothes.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.huflit.clothes.API.ApiService;
import vn.edu.huflit.clothes.Adapter.ProductAdapter;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.models.Product;

public class TopActivity extends AppCompatActivity {
    private RecyclerView topCollectionView;
    private ProductAdapter productAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_top);
        initRecyclerView();
    }

    public void initRecyclerView() {
        topCollectionView = findViewById(R.id.top_collection_list);
        productAdapter = new ProductAdapter(TopActivity.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(TopActivity.this, 2);
        topCollectionView.setLayoutManager(gridLayoutManager);
        topCollectionView.setAdapter(productAdapter);
        getTopList();
    }

    public void getTopList() {
        ApiService.apiService.getTops().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    productAdapter.setList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                System.out.println("Call API Failed");
            }
        });
    }

}
