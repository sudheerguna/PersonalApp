package com.doodleblue.personalapp.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.doodleblue.personalapp.R;
import com.doodleblue.personalapp.CustomView.ButtonTypeface;
import com.doodleblue.personalapp.CustomView.CustomEdittext_Regular;
import com.doodleblue.personalapp.CustomView.CustomTextview_Regular;
import com.doodleblue.personalapp.util.Validations;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Login_Activity extends AppCompatActivity {

    SharedPreferences users;
    SharedPreferences.Editor editor;
    ArrayList<String> usarList = new ArrayList<>();
    ArrayList<String> emailList = new ArrayList<>();
    ArrayList<String> pwdList = new ArrayList<>();
    CustomTextview_Regular txt_signup;
    ButtonTypeface btn_login,btn_signup;
    CustomEdittext_Regular et_pwd,et_email,et_UserName,et_email_signup,et_pwd_signup,et_cpwd_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_login);

        init();
        click_Event();
    }

    private void init(){

        users = getSharedPreferences("PersonalApp", Context.MODE_PRIVATE);
        editor = users.edit();

        txt_signup = (CustomTextview_Regular) findViewById(R.id.txt_signup);
        btn_login = (ButtonTypeface) findViewById(R.id.btn_login);

        et_email = (CustomEdittext_Regular) findViewById(R.id.et_email);
        et_pwd = (CustomEdittext_Regular) findViewById(R.id.et_pwd);
        validateDetails("username");

        Boolean login_state = users.getBoolean("Login_status",false);
        if (login_state.equals(true)){
            Intent intent = new Intent(this,HomePage.class);
            intent.putExtra("name",users.getString("current_user", ""));
            startActivity(intent);
            finish();
        }
    }

    private void click_Event(){

        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_popup();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }

    private void validation(){

        try {
            String name = "";
            if ((Validations.validateEmail(et_email, Login_Activity.this)) &&
                    Validations.validatePassword(et_pwd, Login_Activity.this)) {
                if ((checkEmail("email") == true)) {
                    if (checkPassword("password") == true) {

                        for (int i = 0; i < emailList.size(); i++) {
                            if (et_email.getText().toString().equals(emailList.get(i))) {
                                name = usarList.get(i);
                            }
                        }
                        editor.putBoolean("Login_status", true);
                        editor.putString("current_user", name);
                        editor.apply();
                        Intent intent = new Intent(Login_Activity.this, HomePage.class);
                        intent.putExtra("name", name);
                        startActivity(intent);
                        finish();
                    }
                }
            } else {
                Toast.makeText(this, "User not exist", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "User not exist", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkEmail(String val){
        emailList.removeAll(emailList);

        Boolean check = false;
        if (getDetails(val) != null) {
            emailList.addAll(getDetails(val));

            if (emailList.contains(et_email.getText().toString())) {
                check = true;
            }else {
                check = false;
            }
        }else {
            Toast.makeText(this, "User Not Exist", Toast.LENGTH_SHORT).show();
        }
        return check;
    }

    private boolean checkPassword(String val){
        pwdList.removeAll(pwdList);

        Boolean check = false;
        if (getDetails(val) != null) {
            pwdList.addAll(getDetails(val));

            if (pwdList.contains(et_pwd.getText().toString())) {
                check = true;
            }else {
                check = false;
            }
        }
        return check;
    }
    private void validateDetails(String val){
        usarList.removeAll(usarList);

        if (getDetails(val) != null) {
            usarList.addAll(getDetails("username"));

            }else {
                Toast.makeText(this, "User not exist", Toast.LENGTH_SHORT).show();
        }
    }
    public void show_popup() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_signup);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        et_UserName = (CustomEdittext_Regular) dialog.findViewById(R.id.et_UserName);
        et_email_signup = (CustomEdittext_Regular) dialog.findViewById(R.id.et_email_signup);
        et_pwd_signup = (CustomEdittext_Regular) dialog.findViewById(R.id.et_pwd_signup);
        et_cpwd_signup = (CustomEdittext_Regular) dialog.findViewById(R.id.et_cpwd_signup);
        btn_signup = (ButtonTypeface) dialog.findViewById(R.id.btn_signup);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((Validations.validateUserName(et_UserName,Login_Activity.this)) &&
                     Validations.validateEmail (et_email_signup, Login_Activity.this) &&
                     Validations.validatePassword(et_pwd_signup, Login_Activity.this) &&
                     Validations.validateConfirmPassword(et_pwd_signup,et_cpwd_signup, Login_Activity.this))
                {

                    checkUserName();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void checkUserName(){
        ArrayList<String> usarListTemp = new ArrayList<>();;
            usarListTemp.removeAll(usarListTemp);

        if (getDetails("username") != null) {
            usarListTemp.addAll(getDetails("username"));

            if (usarListTemp.contains(et_UserName.getText().toString())) {
                Toast.makeText(this, "Username already exist", Toast.LENGTH_SHORT).show();
            }else {
                String uName = et_UserName.getText().toString();
                String email = et_email_signup.getText().toString();
                String pwd = et_pwd_signup.getText().toString();
                addUser(uName,email,pwd);
            }
        }else {
            String uName = et_UserName.getText().toString();
            String email = et_email_signup.getText().toString();
            String pwd = et_pwd_signup.getText().toString();
            addUser(uName,email,pwd);
        }
    }
    private void addUser(String username, String email, String password){

        usarList.add(username);
        emailList.add(email);
        pwdList.add(password);
        saveDetails(usarList,"username");
        saveDetails(pwdList,"password");
        saveDetails(emailList,"email");
        nextActivity();
    }
    private void nextActivity(){
        editor.putBoolean("Login_status",true);
        editor.putString("current_user",et_UserName.getText().toString());
        editor.apply();
        Intent intent = new Intent(Login_Activity.this, HomePage.class);
        intent.putExtra("name",et_UserName.getText().toString());
        startActivity(intent);
        finish();
    }
    public void saveDetails(ArrayList<String> list, String key){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }

    public ArrayList<String> getDetails(String key){
        Gson gson = new Gson();
        String json = users.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

}
