package vn.edu.huflit.clothes.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.huflit.clothes.Activity.SigninActivity;
import vn.edu.huflit.clothes.MainActivity;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.models.User;

public class AccountFragment extends Fragment {
    private View mView;
    Button logoutBtn;
    SharedPreferences sharedPreferences;
    Gson gson;
    EditText nameAccount,emailAccount,phoneAccount,addressAccount;
    CircleImageView avatarAccount;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_account, container, false);
        init();
        return mView;
    }

    public void init() {
        sharedPreferences = this.getActivity().getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        gson = new Gson();
        logoutBtn = mView.findViewById(R.id.logout_btn);

        nameAccount = mView.findViewById(R.id.name_account);
        emailAccount = mView.findViewById(R.id.email_account);
        phoneAccount = mView.findViewById(R.id.phone_account);
        addressAccount = mView.findViewById(R.id.addess_account);
        avatarAccount = mView.findViewById(R.id.avatar_account);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
        handleSharePreferences();
    }

    public void handleSharePreferences() {
        String userJSON = sharedPreferences.getString("user", "0");//second parameter is necessary ie.,Value to return if this preference does not exist.
        if (userJSON != null) {
            User user = gson.fromJson(userJSON, User.class);
            setTextToView(user);
        }
    }

    public void setTextToView(User user) {
        nameAccount.setText(user.getName());
        emailAccount.setText(user.getEmail());
        phoneAccount.setText(user.getPhoneNumber());
        addressAccount.setText(user.getAddress());
        if (user.getAvatar() != null) {
            Picasso.get().load(user.getAvatar()).into(avatarAccount);
        }
    }
}
