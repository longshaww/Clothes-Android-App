package vn.edu.huflit.clothes.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.edu.huflit.clothes.models.Bill;
import vn.edu.huflit.clothes.models.UserIdDTO;
import vn.edu.huflit.clothes.models.ChangePasswordDTO;
import vn.edu.huflit.clothes.models.CreateBillDTO;
import vn.edu.huflit.clothes.models.Customer;
import vn.edu.huflit.clothes.models.Product;
import vn.edu.huflit.clothes.models.UpdateAddressPhoneNumberDTO;
import vn.edu.huflit.clothes.models.User;
import vn.edu.huflit.clothes.models.UserLoginDTO;
import vn.edu.huflit.clothes.models.UserRegisterDTO;

public interface ApiService {

    //http://139.59.104.129:4000/

    Gson gson = new GsonBuilder()
            .setDateFormat("dd-MM-yyyy HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.129:4000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("android/collection")
    Call<List<Product>> getAllProduct();

    @GET("android/collection/new-arrivals")
    Call<List<Product>> getNewArrivals();

    @GET("android/collection/tops")
    Call<List<Product>> getTops();

    @GET("android/collection/bottoms")
    Call<List<Product>> getBottoms();

    @GET("android/collection/accessories")
    Call<List<Product>> getAccessories();

    @GET("android/collection/outerwears")
    Call<List<Product>> getOuterWears();

    @GET("search")
    Call<List<Product>> search(@Query("q") String nameProduct, @Query("ascending") String ascending, @Query("descending") String descending);

    @GET("product/{id}")
    Call<Product> getProductDetail(@Path("id") String productId);

    @POST("android/auth/login")
    Call<User> login(@Body UserLoginDTO user);

    @POST("android/auth/register")
    Call<User> register(@Body UserRegisterDTO user);

    @POST("android/bill")
    Call<Bill> postBill(@Body CreateBillDTO bill);

    @POST("android/bill/history")
    Call<List<Bill>> getBillHistory(@Body UserIdDTO body);

    @POST("android/bill/user")
    Call<List<Customer>> getCustomerInfo(@Body UserIdDTO body);

    @DELETE("android/bill/customer/{id}")
    Call<Customer> deleteCustomerInfo(@Path("id") String customerID);

    @PUT("android/bill/customer/{id}")
    Call<Customer> editCustomerInfo(@Path("id") String customerID, @Body Customer customer);

    @POST("android/bill/customer")
    Call<Customer> postNewCustomer(@Body Customer customer);

    @POST("android/auth/update")
    Call<User> changePassword(@Body ChangePasswordDTO body);
}
