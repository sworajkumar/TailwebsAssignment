package com.cpetsolut.com.tailwebsassignment.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cpetsolut.com.tailwebsassignment.DBHelper.Controllerdb;
import com.cpetsolut.com.tailwebsassignment.R;
import com.cpetsolut.com.tailwebsassignment.helper.InputValidation;
import com.cpetsolut.com.tailwebsassignment.model.User;
import com.google.android.material.snackbar.Snackbar;

public class UserSignUpActivity extends AppCompatActivity {

    private EditText firstname,email,mobilenumber,password;
    private Button register;
    private InputValidation inputValidation;
    private Controllerdb databaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

        Toolbar mToolbar =  findViewById(R.id.bgHeaderr);
        mToolbar.setTitle("SIGN UP");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }});

        firstname=(EditText)findViewById(R.id.firstname);
        email=(EditText)findViewById(R.id.email);
        mobilenumber=(EditText)findViewById(R.id.mobilenumber);
        password=(EditText)findViewById(R.id.password);
        register=(Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstname.getText().toString().isEmpty()){
                    firstname.setError("User Name");
                }else {
                    if (email.getText().toString().isEmpty()){
                        email.setError("E-Mail");
                    }else {
                        if (mobilenumber.getText().toString().isEmpty()){
                            mobilenumber.setError("Mobile Number");
                        }else {
                            if (password.getText().toString().isEmpty()){
                                password.setError("Password");
                            }else {
                                databaseHelper=new Controllerdb(UserSignUpActivity.this);
                                if (!databaseHelper.checkUser(mobilenumber.getText().toString())) {
                                    databaseHelper.addUser(firstname.getText().toString(),email.getText().toString(),mobilenumber.getText().toString(),password.getText().toString());
                                    emptyInputEditText();
                                     Intent intent=new Intent(UserSignUpActivity.this,LoginActivity.class);
                                     startActivity(intent);
                                } else {
                                    Toast.makeText(UserSignUpActivity.this, "This mobile Number Already exists.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
            }

            private void emptyInputEditText() {
                    firstname.setText(null);
                    email.setText(null);
                    mobilenumber.setText(null);
                    password.setText(null);
            }
        });
    }

}
