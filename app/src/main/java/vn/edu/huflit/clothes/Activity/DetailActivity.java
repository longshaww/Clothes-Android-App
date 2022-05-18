package vn.edu.huflit.clothes.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.huflit.clothes.API.ApiService;
import vn.edu.huflit.clothes.Adapter.CartAdapter;
import vn.edu.huflit.clothes.Adapter.ProductAdapter;
import vn.edu.huflit.clothes.CartHelper;
import vn.edu.huflit.clothes.Fragment.CartFragment;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.models.Cart;
import vn.edu.huflit.clothes.models.Product;

public class DetailActivity extends AppCompatActivity {
    RelativeLayout relativeLayout;
    ImageSlider imageSlider;
    TextView txtNameDetail, txtOverviewDetail, txtPriceDetail;
    Button addToCartBtn;
    RadioButton rdM, rdL, rdXL;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
    }

    private void init() {
        imageSlider = findViewById(R.id.sliderDetail);
        relativeLayout = findViewById(R.id.relativeLayout);
        txtNameDetail = findViewById(R.id.txtNameDetail);
        txtOverviewDetail = findViewById(R.id.txtOverviewDetail);
        txtPriceDetail = findViewById(R.id.txtPriceDetail);
        addToCartBtn = findViewById(R.id.addToCartBtn);
        rdM = findViewById(R.id.radio_M);
        rdL = findViewById(R.id.radio_L);
        rdXL = findViewById(R.id.radio_XL);
        getIncomingIntent();
    }

    private String onCheckedChange() {
        if (rdL.isChecked()) {
            return rdL.getText().toString();
        }
        if (rdXL.isChecked()) {
            return rdXL.getText().toString();
        }
        return rdM.getText().toString();
    }

    private void getIncomingIntent() {
        String idProduct = getIntent().getStringExtra("idProduct");
        ApiService.apiService.getProductDetail(idProduct).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    setIncomingIntent(response.body());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                System.out.println("Call Api Failed");
            }
        });
    }

    private void setIncomingIntent(Product product) {
        txtNameDetail.setText(product.getNameProduct());
        txtOverviewDetail.setText(product.getDescription());
        txtPriceDetail.setText(product.getPrice() + ".000đ");

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartHelper cartHelper = new CartHelper(getBaseContext());
                cartHelper.insertCart(product.get_id(), product.getImageList()[0], product.getNameProduct(), product.getPrice(), product.getDescription(), onCheckedChange(), "1", product.getPrice());
            }
        });
        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel("https://" + product.getImageList()[0]));
        slideModels.add(new SlideModel("https://" + product.getImageList()[1]));
        imageSlider.setImageList(slideModels, true);
    }
}
