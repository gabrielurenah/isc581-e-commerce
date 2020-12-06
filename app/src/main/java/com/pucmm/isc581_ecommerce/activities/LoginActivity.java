package com.pucmm.isc581_ecommerce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pucmm.isc581_ecommerce.R;
import com.pucmm.isc581_ecommerce.Utils.Validator;
import com.pucmm.isc581_ecommerce.firebaseHandlers.dbHelpers.CategoriesDB;
import com.pucmm.isc581_ecommerce.firebaseHandlers.dbHelpers.ProductsDB;
import com.pucmm.isc581_ecommerce.models.Category;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private ExtendedFloatingActionButton mLoginBtn;
    private TextView mForgotPassword;
    private TextView mRegisterNow;

    private EditText emailET;
    private EditText passwordET;

    private ProgressBar progress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginBtn = findViewById(R.id.log_in_button);
        mForgotPassword = findViewById(R.id.log_in_forgot_password);
        mRegisterNow = findViewById(R.id.log_in_register_now);
        emailET = findViewById(R.id.log_in_email);
        passwordET = findViewById(R.id.log_in_password);
        progress = findViewById(R.id.log_in_progress);

        mAuth = FirebaseAuth.getInstance();
//        CategoriesDB.removeProductFromCategory();

       ArrayList<Category> categories  =  CategoriesDB.getCategories();
        Log.wtf("LOG TEST OF CAT", "CATEGORIES UPDATING" + categories.toString());


        mLoginBtn.setOnClickListener(v -> {
//            logInUser();
            startActivity(new Intent(this, MainActivity.class));

        });

        mForgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(this, ForgotPasswordActivity.class));
        });

        mRegisterNow.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterUserActivity.class));
        });

    }

    private void logInUser() {
        if(!validateData()) return;


        progress.setVisibility(View.VISIBLE);

        final String email = emailET.getText().toString().trim();
        final String password = passwordET.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            progress.setVisibility(View.INVISIBLE);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Login failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });

        startActivity(new Intent(this, MainActivity.class));
    }

    private Boolean validateData() {
        if (!Validator.isInternetConnected(LoginActivity.this)) {

            new MaterialAlertDialogBuilder(LoginActivity.this)
                    .setTitle("Network Error")
                    .setMessage("You are not connected to the internet")
                    .setPositiveButton("Try again", (dialog, which) -> logInUser())
                    .show();
            return false;
        }

        if (!Validator.isEmailValid(emailET) || Validator.isEmpty(passwordET)) {
            Log.e("ERROR", "User did not pass validation");
            return false;
        }

        return true;
    }


}