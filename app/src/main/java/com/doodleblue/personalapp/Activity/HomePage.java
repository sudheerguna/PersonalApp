package com.doodleblue.personalapp.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.doodleblue.personalapp.Adapter.homepageAdapter;
import com.doodleblue.personalapp.CustomView.ButtonTypeface;
import com.doodleblue.personalapp.CustomView.CustomEdittext_Regular;
import com.doodleblue.personalapp.CustomView.CustomTextview_Regular;
import com.doodleblue.personalapp.R;
import com.doodleblue.personalapp.util.Validations;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    SharedPreferences users;
    SharedPreferences.Editor editor;

    public RecyclerView listContacts;
    CustomEdittext_Regular et_name,et_phone;
    CustomTextview_Regular no_data;
    ButtonTypeface btn_save,btn_delete;

    ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> phoneList = new ArrayList<>();
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               show_popup(0, false,"","");
            }
        });
    }

    private void init(){
        name = getIntent().getStringExtra("name");

        users = getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = users.edit();

        no_data = (CustomTextview_Regular) findViewById(R.id.no_data);

        listContacts = (RecyclerView) findViewById(R.id.listContacts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        listContacts.setLayoutManager(linearLayoutManager);

        listDetails();

    }

    private void listDetails(){
        nameList.removeAll(nameList);
        phoneList.removeAll(phoneList);

        if (getDetails("name") != null) {
            nameList.addAll(getDetails("name"));
            no_data.setVisibility(View.GONE);
        }else {
            no_data.setVisibility(View.VISIBLE);
        }
        if (getDetails("phone") != null) {
            phoneList.addAll(getDetails("phone"));
        }
        setAdapter();
    }

    @SuppressLint("ResourceAsColor")
    public void show_popup(final int position, final boolean page, String name, String number) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pop_details);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        et_name = (CustomEdittext_Regular) dialog.findViewById(R.id.et_name);
        et_phone = (CustomEdittext_Regular) dialog.findViewById(R.id.et_phone);
        btn_save = (ButtonTypeface) dialog.findViewById(R.id.btn_save);
        btn_delete = (ButtonTypeface) dialog.findViewById(R.id.btn_delete);
        View tempView = (View) dialog.findViewById(R.id.tempView);

        if (page == true){
            tempView.setVisibility(View.VISIBLE);
            btn_delete.setVisibility(View.VISIBLE);
            btn_save.setText("Edit");
            btn_save.setTextColor(getResources().getColor(R.color.white));
            et_name.setText(name);
            et_phone.setText(number);
        }else {
            tempView.setVisibility(View.GONE);
            btn_delete.setVisibility(View.GONE);
            btn_save.setText("Save");
        }
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (page == false) {
                    if ((Validations.validateUserName(et_name, HomePage.this)) &&
                            Validations.validatePhoneNo(et_phone, HomePage.this)) {

                        saveContacts(et_name.getText().toString(), et_phone.getText().toString());
                        dialog.dismiss();
                    }
                }else {

                    if ((Validations.validateUserName(et_name, HomePage.this)) &&
                            Validations.validatePhoneNo(et_phone, HomePage.this)) {

                      nameList.set(position,et_name.getText().toString());
                      phoneList.set(position,et_phone.getText().toString());
                        saveDetails(nameList,"name");
                        saveDetails(phoneList,"phone");
                        listDetails();
                        dialog.dismiss();
                    }
                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              deleteIndex(position);
              dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void deleteIndex(int index){

       nameList.remove(index);
       phoneList.remove(index);
        saveDetails(nameList,"name");
        saveDetails(phoneList,"phone");
        listDetails();
    }
    private void saveContacts(String name, String phone){

        nameList.add(name);
        phoneList.add(phone);
        saveDetails(nameList,"name");
        saveDetails(phoneList,"phone");
        no_data.setVisibility(View.GONE);
    }
    public void saveDetails(ArrayList<String> list, String key){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }
    public ArrayList<String> getDetails(String key){
        Gson gson = new Gson();
        String json = users.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }
    private void setAdapter(){

        homepageAdapter Adapter = new homepageAdapter(this,nameList,phoneList);
        listContacts.setAdapter(Adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLogout:
                SharedPreferences temp = getSharedPreferences("PersonalApp", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = temp.edit();
                edit.putBoolean("Login_status",false);
                edit.apply();
                finish();
                Intent intent = new Intent(HomePage.this,Login_Activity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                finish();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
}
