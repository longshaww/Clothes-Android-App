package vn.edu.huflit.clothes.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import vn.edu.huflit.clothes.R;

public class BillHistoryFragment extends Fragment {
    View mView;

    public BillHistoryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_tab_history, container, false);
        return mView;
    }
}
