package com.pucmm.isc581_ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class LoginActivity extends AppCompatActivity {

    private ExtendedFloatingActionButton mLoginBtn;
    private TextView mForgotPassword;
    private TextView mRegisterNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginBtn = findViewById(R.id.log_in_button);
        mForgotPassword = findViewById(R.id.log_in_forgot_password);
        mRegisterNow = findViewById(R.id.log_in_register_now);

        mLoginBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        mForgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(this, ForgotPasswordActivity.class));
        });

        mRegisterNow.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterUserActivity.class));
        });

    }
}