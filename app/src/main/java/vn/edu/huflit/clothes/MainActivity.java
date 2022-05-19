package vn.edu.huflit.clothes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import vn.edu.huflit.clothes.Activity.SigninActivity;
import vn.edu.huflit.clothes.Fragment.ViewPagerAdapter;
import vn.edu.huflit.clothes.API.ApiController;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    public static BottomNavigationView bottomNavigationView;
    SharedPreferences sharedPreferences;
    CartHelper cartHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cartHelper = new CartHelper(getApplicationContext());
        sharedPreferences = getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("user", null) == null || sharedPreferences.getString("userCookie", null) == null) {
            Intent intent = new Intent(MainActivity.this, SigninActivity.class);
            startActivity(intent);
        }


        viewPager2 = findViewById(R.id.viewpager2);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);
        viewPager2.setUserInputEnabled(false);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    viewPager2.setCurrentItem(0);
                } else if (id == R.id.navigation_search) {
                    viewPager2.setCurrentItem(1);
                } else if (id == R.id.navigation_cart) {
                    viewPager2.setCurrentItem(2);
                } else if (id == R.id.navigation_account) {
                    viewPager2.setCurrentItem(3);
                }
                return true;
            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.navigation_search).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.navigation_cart).setChecked(true);
                        break;
                    case 4:
                        bottomNavigationView.getMenu().findItem(R.id.navigation_account).setChecked(true);
                        break;
                }
            }
        });
        bottomNavigationView.getOrCreateBadge(R.id.navigation_cart).setNumber(cartHelper.cartCount());
    }
}

