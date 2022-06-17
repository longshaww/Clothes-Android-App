package vn.edu.huflit.clothes.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.huflit.clothes.API.ApiService;
import vn.edu.huflit.clothes.Activity.CollectionActivity;
import vn.edu.huflit.clothes.Activity.PaymentActivity;
import vn.edu.huflit.clothes.Adapter.BillHistoryAdapter;
import vn.edu.huflit.clothes.MainActivity;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.Utils.GetUserSharePreferences;
import vn.edu.huflit.clothes.models.Bill;
import vn.edu.huflit.clothes.models.UserIdDTO;
import vn.edu.huflit.clothes.models.User;

public class BillHistoryFragment extends Fragment {
    private View mView;
    private ArrayList<Bill> listBills;
    private BillHistoryAdapter billHistoryAdapter;
    private RecyclerView billHistoryRcv;
    User user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_tab_history, container, false);

        init();
        user = GetUserSharePreferences.handleSharePreferences(getContext());
        getBillHistory(user);
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getBillHistory(user);
    }

    private void init() {
        billHistoryRcv = mView.findViewById(R.id.billRcv);
        listBills = new ArrayList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        billHistoryRcv.setLayoutManager(linearLayoutManager);
        billHistoryAdapter = new BillHistoryAdapter(getContext(), listBills);
        billHistoryRcv.setAdapter(billHistoryAdapter);
    }

    private void getBillHistory(User user) {
        UserIdDTO bill = new UserIdDTO(user.getID());
        ApiService.apiService.getBillHistory(bill).enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                if (response.isSuccessful()) {
                    listBills.clear();
                    listBills.addAll(response.body());
                    billHistoryAdapter.setList(listBills);
                }
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable t) {
                Toast.makeText(getContext(), "Something wrong ~!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
