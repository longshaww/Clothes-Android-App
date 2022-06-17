package vn.edu.huflit.clothes.Utils;

import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Patterns;

import java.nio.file.Path;
import java.util.regex.Pattern;

public class Validation {
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean isValidPhoneNumber(CharSequence phone) {
        return !TextUtils.isEmpty(phone) && !(phone.length() < 6 || phone.length() > 13) && android.util.Patterns.PHONE.matcher(phone).matches();
    }

    public static boolean isValidAddress(CharSequence address) {
        return !TextUtils.isEmpty(address) && address.length() > 8;
    }

    public static boolean isValidPassword(CharSequence password) {
        return !TextUtils.isEmpty(password) && password.length() > 5;
    }

    public static boolean isValidName(CharSequence name) {
        return !TextUtils.isEmpty(name) && name.length() > 4;
    }
}
