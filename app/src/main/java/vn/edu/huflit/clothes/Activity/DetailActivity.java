package vn.edu.huflit.clothes.Activity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.appcompat.app.AppCompatActivity;
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
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.models.Product;

public class DetailActivity extends AppCompatActivity {
    RelativeLayout relativeLayout;
    ImageSlider imageSlider;
    TextView txtNameDetail,txtOverviewDetail,txtPriceDetail;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
    }


    private void init(){
        imageSlider = findViewById(R.id.sliderDetail);
        relativeLayout = findViewById(R.id.relativeLayout);
        txtNameDetail = findViewById(R.id.txtNameDetail);
        txtOverviewDetail = findViewById(R.id.txtOverviewDetail);
        txtPriceDetail = findViewById(R.id.txtPriceDetail);
        getIncomingIntent();
    }

    private void getIncomingIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        Product product = (Product) bundle.get("object_product");
        setIncomingIntent(product);
    }

    private void setIncomingIntent(Product product) {
        txtNameDetail.setText(product.getNameProduct());
        txtOverviewDetail.setText(product.getDescription());
        txtPriceDetail.setText(product.getPrice()+"000đ");

        List<SlideModel> slideModels = new ArrayList<>();
        System.out.println(product.getImageList()[0]);
        slideModels.add(new SlideModel("https://"+product.getImageList()[0]));
        slideModels.add(new SlideModel("https://"+product.getImageList()[1]));
        imageSlider.setImageList(slideModels,true);
    }
}
