package vn.edu.huflit.clothes.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;

import vn.edu.huflit.clothes.models.User;

public class GetUserSharePreferences {
    public static Gson gson;
    public static SharedPreferences sharedPreferences;

    public static User handleSharePreferences(Context context) {
        gson = new Gson();
        sharedPreferences = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        String userJSON = sharedPreferences.getString("user", "0");//second parameter is necessary ie.,Value to return if this preference does not exist.
        if (userJSON == null) {
            Toast.makeText(context, "You haven't login !", Toast.LENGTH_SHORT).show();
        }
        User user = gson.fromJson(userJSON, User.class);
        return user;
    }
}
