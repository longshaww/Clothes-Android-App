package vn.edu.huflit.clothes.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.huflit.clothes.API.ApiService;
import vn.edu.huflit.clothes.Activity.CollectionActivity;
import vn.edu.huflit.clothes.Activity.DetailActivity;
import vn.edu.huflit.clothes.Activity.TopActivity;
import vn.edu.huflit.clothes.Adapter.ProductAdapter;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.models.Product;


public class HomeFragment extends Fragment implements ProductAdapter.Listener{
    private ImageView topCollection, bottomCollection, accessoriesCollection, outerWearCollection;
    private CardView topCollectionCard,bottomCollectionCard,accessoriesCollectionCard,outerWearCollectionCard;
    private TextView textViewCollection;
    private View mView;
    private ImageSlider imageSlider;
    private RecyclerView newArrivalsRcv;
    private ProductAdapter productAdapter;
    List<SlideModel> slideModels;
    ArrayList listNewArrivals;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        initImageSlider();
        setImageToView();
        onClickTextCollection();
        onClickTopCollectionCard();
        return mView;
    }

    public void init(){
        textViewCollection = mView.findViewById(R.id.collections_text);
        topCollection = mView.findViewById(R.id.top_collection);
        bottomCollection = mView.findViewById(R.id.bottom_collection);
        outerWearCollection = mView.findViewById(R.id.outerwear_collection);
        accessoriesCollection = mView.findViewById(R.id.accesorie_collection);
        imageSlider = mView.findViewById(R.id.image_slider);
        topCollectionCard = mView.findViewById(R.id.top_collection_card);
        newArrivalsRcv = mView.findViewById(R.id.newArrivalRcv);
        initRcv();
    }

    public void initRcv(){
        listNewArrivals = new ArrayList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        newArrivalsRcv.setLayoutManager(linearLayoutManager);
        productAdapter = new ProductAdapter(getContext(), listNewArrivals,this::onClick);
        newArrivalsRcv.setAdapter(productAdapter);
        getNewArrivals();
    }

    public void getNewArrivals(){
        ApiService.apiService.getNewArrivals().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    productAdapter.setList(response.body());
                    productAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getContext(), "Something wrong ~!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickTextCollection(){
        textViewCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CollectionActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onClickTopCollectionCard (){
        topCollectionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TopActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setImageToView() {
        topCollection.setImageResource(R.drawable.top);
        bottomCollection.setImageResource(R.drawable.pant);
        outerWearCollection.setImageResource(R.drawable.outerwear);
        accessoriesCollection.setImageResource(R.drawable.accessorie);
    }

    public void initImageSlider(){
        slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.slide1));
        slideModels.add(new SlideModel(R.drawable.slide2));
        imageSlider.setImageList(slideModels,true);
    }



    @Override
    public void onClick(Product product) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("idProduct", product.get_id());
        startActivity(intent);
    }
}