package vn.edu.huflit.clothes.API;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.huflit.clothes.models.Product;

public class ApiController {
    public List<Product> allProducts, tops, bottoms, accessories, outerwears;

    public void init(){
        allProducts = new ArrayList<>();
        tops = new ArrayList<>();
        bottoms = new ArrayList<>();
        accessories = new ArrayList<>();
        outerwears = new ArrayList<>();
    }

    public ApiController(){
        init();
    }

    public void getAllProducts() {
                ApiService.apiService.getAllProduct().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                 allProducts = response.body();
                if (allProducts != null) {
                    System.out.println("Call API Success");
//                    products.addAll(allProducts);
//                    System.out.println(products.size());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                System.out.println("Call API Failed");
            }
        });
    }
}
