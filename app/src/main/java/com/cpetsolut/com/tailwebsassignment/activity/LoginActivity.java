package com.cpetsolut.com.tailwebsassignment.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cpetsolut.com.tailwebsassignment.DBHelper.Controllerdb;
import com.cpetsolut.com.tailwebsassignment.R;
import com.cpetsolut.com.tailwebsassignment.helper.InputValidation;
import com.cpetsolut.com.tailwebsassignment.model.User;

public class LoginActivity extends AppCompatActivity {

private EditText mobilenumber,password;
private Button loginnn;
    private InputValidation inputValidation;
    private Controllerdb databaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar mToolbar=(Toolbar)findViewById(R.id.toolbar);
        mToolbar.setTitle("LOGIN");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }});
        mobilenumber=(EditText)findViewById(R.id.mobilenumber);
        password=(EditText)findViewById(R.id.password);
        loginnn=(Button) findViewById(R.id.loginnn);
        loginnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mobilenumber.getText().toString().isEmpty()){
                    mobilenumber.setError("MobileNumber");
                }else {
                    if (password.getText().toString().isEmpty()){
                        password.setError("Password");
                    }else {
                        databaseHelper=new Controllerdb(LoginActivity.this);
                        if (databaseHelper.checkUser(mobilenumber.getText().toString().trim(), password.getText().toString().trim())) {
                            Intent accountsIntent = new Intent(LoginActivity.this, DashboardActivity.class);
                            emptyInputEditText();
                            startActivity(accountsIntent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            private void emptyInputEditText() {
                mobilenumber.setText(null);
                password.setText(null);            }
        });
    }

}
