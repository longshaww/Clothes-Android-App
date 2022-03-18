package vn.edu.huflit.clothes.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

import vn.edu.huflit.clothes.Adapter.ProductAdapter;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.models.Product;


public class HomeFragment extends Fragment implements ProductAdapter.Listener{
    private ImageView topCollection, bottomCollection, accessoriesCollection, outerWearCollection;
    private TextView textViewCollection;
    private View mView;
    private ImageSlider imageSlider;
    List<SlideModel> slideModels;

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
        return mView;
    }

    public void init(){
        textViewCollection = mView.findViewById(R.id.collections_text);
        topCollection = mView.findViewById(R.id.top_collection);
        bottomCollection = mView.findViewById(R.id.bottom_collection);
        outerWearCollection = mView.findViewById(R.id.outerwear_collection);
        accessoriesCollection = mView.findViewById(R.id.accesorie_collection);
        imageSlider = mView.findViewById(R.id.image_slider);
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

    }
}