package com.doodleblue.personalapp.util;

/**
 * Created by satish on 1/12/2019.
 */

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.doodleblue.personalapp.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validations {
    Context context;
    String message;

    public static void showError(String error, EditText editText,
                                 Activity activity) {
        if (error != null && !TextUtils.isEmpty(error)) {

            if (editText != null) {
                Toast.makeText(activity,error , Toast.LENGTH_SHORT).show();
                editText.requestFocus();


            } else {
                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static boolean isEmptyString(String text) {
        return (text == null || text.trim().equals("null") || text.trim().length() <= 0);
    }



    public static boolean validateUserName(EditText editText, Activity activity) {

        String message = activity.getResources().getString(
                R.string.user_name_validations);

        String value = editText.getText().toString().trim();

        String userInvalid = activity.getResources().getString(
                R.string.invalid_username_validations);
        if (value == null || TextUtils.isEmpty(value)) {
            showError(message, editText, activity);
            return false;
        } else if (value.length() < 1) {
            message = activity.getResources().getString(
                    R.string.user_name_invalid_validations);
            showError(message, editText, activity);
            return false;
        } else if (!isValidUserName(value)) {
            System.out.println("message " + userInvalid);
            showError(userInvalid, editText, activity);
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }


    public static boolean validatePhoneNo(EditText editText, Activity activity) {

        String message = activity.getResources().getString(
                R.string.phone_validations);

        String value = editText.getText().toString().trim();

        if (value == null || TextUtils.isEmpty(value)) {
            showError(message, editText, activity);
            return false;
        }
        if (value.length() < 6) {
            String error = activity.getResources().getString(
                    R.string.phoneLength_validations);

            showError(error, editText, activity);
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    public static boolean validateEmail(EditText editText, Activity activity) {

        final String emailEmpty = activity.getResources().getString(
                R.string.email_validations);
        final String emailInvalid = activity.getResources().getString(
                R.string.invalid_email_validations);

        String email = editText.getText().toString().trim();

        if (email == null || TextUtils.isEmpty(email)) {
            showError(emailEmpty, editText, activity);
            return false;
        } else if (!isValidEmail(email)) {
            showError(emailInvalid, editText, activity);
            return false;
        } else {
            editText.setError(null);
            return true;
        }

    }

    public static boolean validatePassword(EditText editText, Activity activity) {

        String message = activity.getResources().getString(
                R.string.password_validations);

        String value = editText.getText().toString().trim();


        if (value == null || TextUtils.isEmpty(value)) {
            showError(message, editText, activity);
            return false;
        } else if (!isValidPasswordChar(value)) {
            message = activity.getResources().getString(
                    R.string.password_invalid_validations);
            showError(message, editText, activity);
            return false;
        } else {
            editText.setError(null);
            return true;
        }

    }


    public static boolean isValidPasswordChar(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*?[a-z])(?=\\S+$).{6,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public static boolean validateConfirmPassword(EditText etPassword,
                                                  EditText etConfirmPassword, Activity activity) {

        final String confirmPasswordEmpty = activity.getResources().getString(
                R.string.confirm_password_validations);
        final String confirmPasswordMismatched = activity.getResources()
                .getString(R.string.confirm_password_mismatch_validations);

        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (confirmPassword == null || TextUtils.isEmpty(confirmPassword)) {
            showError(confirmPasswordEmpty, etConfirmPassword, activity);
            return false;
        } else if (!password.equalsIgnoreCase(confirmPassword)) {
            showError(confirmPasswordMismatched, etConfirmPassword, activity);
            return false;
        } else {
            etConfirmPassword.setError(null);
            return true;
        }
    }


    public static final boolean isValidEmail(CharSequence target) {
        if (target == null || TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }

    public static final boolean isValidUserName(CharSequence target) {

        Pattern pattern;
        Matcher matcher;

        String emailPattern = "[a-zA-Z0-9\\+\\.\\_\\-\\ \\']{1,256}";
        // pattern = Pattern.compile(emailPattern);

        return String.valueOf(target).matches(emailPattern);
    }

    public static final boolean isValidPhoneNumber(CharSequence target) {
        if (target == null || TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }



}
