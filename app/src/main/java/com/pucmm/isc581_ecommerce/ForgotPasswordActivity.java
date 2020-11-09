package com.pucmm.isc581_ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ForgotPasswordActivity extends AppCompatActivity {

    private TextView mLoginNow;
    private TextView mRegisterNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mLoginNow = findViewById(R.id.forgot_password_login_now);
        mRegisterNow = findViewById(R.id.forgot_password_register_now);

        mLoginNow.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        mRegisterNow.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterUserActivity.class));
        });

    }
}