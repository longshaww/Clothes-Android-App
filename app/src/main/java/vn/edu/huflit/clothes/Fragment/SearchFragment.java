package vn.edu.huflit.clothes.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.huflit.clothes.API.ApiService;
import vn.edu.huflit.clothes.Activity.DetailActivity;
import vn.edu.huflit.clothes.Activity.TopActivity;
import vn.edu.huflit.clothes.Adapter.CartAdapter;
import vn.edu.huflit.clothes.Adapter.ProductAdapter;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.models.Cart;
import vn.edu.huflit.clothes.models.Product;

public class SearchFragment extends Fragment implements ProductAdapter.Listener {
    private View mView;
    EditText searchInput;
    ArrayList listProducts;
    RecyclerView productRcv;
    ProductAdapter productAdapter;
    TextView notFoundText;
    RadioButton radioHighToLow, radioLowToHigh;
    RadioGroup radioPriceGroup;
    String ascendingState, descendingState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_search, container, false);
        init();
        return mView;
    }

    public void init() {
        listProducts = new ArrayList();
        searchInput = mView.findViewById(R.id.search_input);
        productRcv = mView.findViewById(R.id.searchRcv);
        notFoundText = mView.findViewById(R.id.not_found_text);
        radioLowToHigh = mView.findViewById(R.id.radio_low_to_high);
        radioHighToLow = mView.findViewById(R.id.radio_high_to_low);
        radioPriceGroup = mView.findViewById(R.id.radio_price_group);
        descendingState = "false";
        ascendingState = "true";

        radioPriceGroup = mView.findViewById(R.id.radio_price_group);
        radioPriceGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radio_low_to_high) {
                    searchApi(searchInput.getText().toString(), "true", "false");
                }
                if (i == R.id.radio_high_to_low) {
                    searchApi(searchInput.getText().toString(), "false", "true");
                }
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        productRcv.setLayoutManager(gridLayoutManager);
        productAdapter = new ProductAdapter(getContext(), listProducts, this::onClick);
        productRcv.setAdapter(productAdapter);
        searchApi(null, ascendingState, descendingState);

        searchInput.addTextChangedListener(new TextWatcher() {
            private Timer timer = new Timer();
            private final long DELAY = 300; // Milliseconds

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                if (radioLowToHigh.isChecked()) {
                                    searchApi(editable.toString(), "true", "false");
                                }
                                if (radioHighToLow.isChecked()) {
                                    searchApi(editable.toString(), "false", "true");
                                }
                            }
                        },
                        DELAY
                );
            }
        });
    }

    public void searchApi(String searchString, String ascendingState, String descendingState) {
        ApiService.apiService.search(searchString, ascendingState, descendingState).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    productAdapter.setList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getContext(), "Something wrong ~!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(Product product) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("idProduct", product.get_id());
        startActivity(intent);
    }
}
