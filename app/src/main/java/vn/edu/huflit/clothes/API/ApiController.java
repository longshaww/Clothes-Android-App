package vn.edu.huflit.clothes.API;

import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.huflit.clothes.MainActivity;
import vn.edu.huflit.clothes.models.Product;

public class ApiController {
    public List<Product> allProducts , tops , bottoms , accessories , outerwears;

    public void RequestAPI (Call<List<Product>> list,Response<List<Product>> response){

    }

    public List<Product> getAllProducts(){
        ApiService.apiService.getAllProduct().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
              allProducts = response.body();
//                Gson gson = new Gson();
//                String strJson = gson.toJson(products);
                if(allProducts != null)
                {
                    System.out.println(allProducts);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                System.out.println("Call API Failed");
            }
        });
       return allProducts;
    }

}
