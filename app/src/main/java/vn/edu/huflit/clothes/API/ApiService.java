package vn.edu.huflit.clothes.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.edu.huflit.clothes.models.Product;
import vn.edu.huflit.clothes.models.User;
import vn.edu.huflit.clothes.models.UserLoginDTO;

public interface ApiService {
    //http://localhost:4000/

    Gson gson = new GsonBuilder()
            .setDateFormat("dd-MM-yyyy HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.6:4000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("collections")
    Call<List<Product>> getAllProduct();

    @GET("collections/tops-without-pag")
    Call<List<Product>> getTops();

    @GET("product/{id}")
    Call<Product> getProductDetail(@Path("id") String productId);

    @POST("authCookie/login")
    Call<UserLoginDTO> login(@Body UserLoginDTO user);
}
