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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
        slider();
    }

    private void slider(){
        imageSlider = findViewById(R.id.sliderDetail);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.accessorie));
        slideModels.add(new SlideModel(R.drawable.outerwear));
        slideModels.add(new SlideModel(R.drawable.pant));

        imageSlider.setImageList(slideModels,true);
    }

    private void init(){
        relativeLayout = findViewById(R.id.relativeLayout);
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

    }
}
