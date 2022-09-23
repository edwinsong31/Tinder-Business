package com.tinderbusiness.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.dovar.dtoast.DToast;
import com.tinderbusiness.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static boolean checkEdtValidation(EditText editText, String error) {
        String value = editText.getText().toString().trim();
        if (value.isEmpty()) {
            editText.requestFocus();
            showToastError(editText, error);
            return false;
        }
        return true;
    }

    public static boolean checkStringValue(String str, String error, Context context){

        if (str.isEmpty()){

            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    DToast.make(context)
                            .setText(com.dovar.dtoast.R.id.tv_content_default, error)
                            .setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 120)
                            .show();
                }
            });
            return false;
        }
        return true;
    }

    public static boolean checkTxvValidation(TextView editText, String error) {
        String value = editText.getText().toString().trim();
        if (value.isEmpty()) {
            editText.requestFocus();
            showToastError(editText, error);
            return false;
        }
        return true;
    }

    public static boolean isEmailValid(EditText editText, Context context) {
        String email = editText.getText().toString().trim();
        if (email.isEmpty()) {
            editText.requestFocus();
            showToastError(editText, context.getString(R.string.enter_email));
            return false;
        }
        String expression = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern patternSimplestRegex = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcherSimplestRegex = patternSimplestRegex.matcher(email);
        if (matcherSimplestRegex.matches()) {
            return true;
        }
        editText.requestFocus();
        showToastError(editText, context.getString(R.string.enter_valid_email));
        return false;
    }

    public static boolean checkPassWordMatch(EditText editText1, EditText editText2, Context context) {
        String pass1 = editText1.getText().toString().trim();
        String pass2 = editText2.getText().toString().trim();
        if (pass1.isEmpty() && pass2.isEmpty()) {
            editText1.requestFocus();
            showToastError(editText1, context.getString(R.string.enter_password));
            return false;
        } else if (!pass1.equals(pass2)) {
            editText1.requestFocus();
            showToastError(editText1, context.getString(R.string.enter_pass_match));
            return false;
        }
        else if (pass1.length() < 6){
            editText1.requestFocus();
            showToastError(editText1, context.getString(R.string.password_6));
            return false;
        }
        return true;
    }

    public static void showToastError(View editText, final String message) {

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                DToast.make(editText.getContext())
                        .setText(com.dovar.dtoast.R.id.tv_content_default, message)
                        .setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 120)
                        .show();
            }
        });
    }

    public static void hideKeyBoard(Activity context) {
        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
