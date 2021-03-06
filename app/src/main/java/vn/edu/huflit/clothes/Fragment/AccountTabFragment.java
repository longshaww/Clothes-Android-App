package vn.edu.huflit.clothes.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.huflit.clothes.API.ApiService;
import vn.edu.huflit.clothes.Activity.ChangePasswordActivity;
import vn.edu.huflit.clothes.Activity.CustomerInfoActivity;
import vn.edu.huflit.clothes.Activity.SigninActivity;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.Utils.GetUserSharePreferences;
import vn.edu.huflit.clothes.models.UpdateAddressPhoneNumberDTO;
import vn.edu.huflit.clothes.models.User;

public class AccountTabFragment extends Fragment {
    private View mView;
    Button logoutBtn, changePasswordBtn,updateBtn;
    SharedPreferences sharedPreferences;
    Gson gson;
    TextView nameAccount, emailAccount,phoneAccount,addressAccount;
    LinearProgressIndicator loadingUpdateAccount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_tab_account, container, false);
        init();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTextToView();
    }

    public void init() {
        sharedPreferences = getContext().getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        gson = new Gson();
        logoutBtn = mView.findViewById(R.id.logout_btn);
        changePasswordBtn = mView.findViewById(R.id.btn_change_password_account);
        nameAccount = mView.findViewById(R.id.name_account);
        emailAccount = mView.findViewById(R.id.email_account);
        phoneAccount = mView.findViewById(R.id.phone_account);
        addressAccount = mView.findViewById(R.id.address_account);
        loadingUpdateAccount = mView.findViewById(R.id.loading_update_account);
        logoutBtn.setOnClickListener(this::onLogOutClick);
        changePasswordBtn.setOnClickListener(this::onChangePasswordClick);
        updateBtn = mView.findViewById(R.id.btn_update_customer_info);
        updateBtn.setOnClickListener(this::onUpdateClick);
        setTextToView();
    }

    public void setTextToView() {
        User user = GetUserSharePreferences.handleSharePreferences(getContext());
        nameAccount.setText(user.getName());
        emailAccount.setText(user.getEmail());
        phoneAccount.setText(user.getPhoneNumber());
        addressAccount.setText(user.getAddress());
    }

    public void onLogOutClick(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (String key : sharedPreferences.getAll().keySet()) {
            if (key.startsWith("user")) {
                editor.remove(key);
            }
            if (key.startsWith("userCookie")) {
                editor.remove(key);
            }
        }
        editor.commit();
        if (sharedPreferences.getString("user", null) == null || sharedPreferences.getString("userCookie", null) == null) {
            Intent intent = new Intent(getActivity().getBaseContext(), SigninActivity.class);
            startActivity(intent);
        }
    }

    private void onUpdateClick(View view){
        Intent intent = new Intent(getContext(), CustomerInfoActivity.class);
        startActivity(intent);
    }

    public void onChangePasswordClick(View view) {
        Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
        startActivity(intent);
    }


}
