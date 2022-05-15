package vn.edu.huflit.clothes.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import vn.edu.huflit.clothes.Activity.SigninActivity;
import vn.edu.huflit.clothes.MainActivity;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.models.User;

public class AccountFragment extends Fragment {
    private View mView;
    Button logoutBtn;
    SharedPreferences sharedPreferences;
    Gson gson;

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
        String userJSON = sharedPreferences.getString("user", "0");//second parameter is necessary ie.,Value to return if this preference does not exist.
        if (userJSON != null) {
            User user = gson.fromJson(userJSON,User.class);
            Toast.makeText(getActivity(), user.getName(), Toast.LENGTH_SHORT).show();
        }
    }


}
