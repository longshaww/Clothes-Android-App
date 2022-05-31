package vn.edu.huflit.clothes.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import vn.edu.huflit.clothes.R;

public class AccountFragment extends Fragment {
    View mView;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    AccountViewPagerAdapter accountViewPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_account, container, false);
        tabLayout = mView.findViewById(R.id.account_tab_layout);
        viewPager2 = mView.findViewById(R.id.account_viewpager);
        accountViewPagerAdapter = new AccountViewPagerAdapter(getActivity());
        viewPager2.setAdapter(accountViewPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Account");
                    break;
                case 1:
                    tab.setText("Bill history");
                    break;
            }
        }).attach();
        return mView;
    }
}
