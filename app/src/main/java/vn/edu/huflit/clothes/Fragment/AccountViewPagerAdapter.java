package vn.edu.huflit.clothes.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AccountViewPagerAdapter extends FragmentStateAdapter {

    public AccountViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AccountTabFragment();
            case 1:
                return new BillHistoryFragment();
            default:
                return new AccountTabFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}