package com.pucmm.isc581_ecommerce.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eyalbira.loadingdots.LoadingDots;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.pucmm.isc581_ecommerce.R;
import com.pucmm.isc581_ecommerce.Utils.Validator;
import com.pucmm.isc581_ecommerce.firebaseHandlers.dbHelpers.UsersDB;
import com.pucmm.isc581_ecommerce.models.User;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class RegisterUserActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private TextView mLoginNow;
    private TextView mForgotPassword;
    private ExtendedFloatingActionButton registerBtn;

    private String imageUrl;


    private EditText nameET;
    private EditText emailET;
    private EditText passwordET;
    private EditText passwordConfET;
    private EditText usernameET;
    private EditText phoneET;
    private LoadingDots progress;

    private Uri imageUri;
    private CircularImageView imageView;

    StorageReference usersImageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();
        mLoginNow = findViewById(R.id.reg_login_now);
        mForgotPassword = findViewById(R.id.reg_forgot_password);
        registerBtn = findViewById(R.id.reg_user_button);
        emailET = findViewById(R.id.reg_user_email);
        passwordET = findViewById(R.id.reg_user_password);
        nameET = findViewById(R.id.reg_user_name);
        progress = findViewById(R.id.reg_user_progress);
        imageView = findViewById(R.id.reg_user_image);
        usernameET = findViewById(R.id.reg_user_username);
        passwordConfET = findViewById(R.id.reg_user_confirm_password);
        phoneET = findViewById(R.id.reg_user_phone);

        imageView.setOnClickListener( v-> {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        });

        registerBtn.setOnClickListener( v-> {
            Toast.makeText(this, "Creating user...", Toast.LENGTH_SHORT).show();
            registerUser();

        });

        mForgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(this, ForgotPasswordActivity.class));
        });

        mLoginNow.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE &&
                resultCode == RESULT_OK && data != null){

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            imageView.setImageURI(imageUri);

        }
    }


    private void registerUser() {
        Log.wtf("THIS DOQWIND", String.valueOf(validateData()));
        if(!validateData()) return;
        Toast.makeText(this, "Data validated", Toast.LENGTH_SHORT).show();
        progress.setVisibility(View.VISIBLE);

        final String name = nameET.getText().toString().trim();
        final String email = emailET.getText().toString().trim();
        final String password = passwordET.getText().toString().trim();
        final String username = usernameET.getText().toString().trim();
        final String phone = phoneET.getText().toString().trim();


        mAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progress.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            //CREATING AN ACCOUNT WAS SUCCESSFUL, SEND EMAIL TO USER
                            sendVerificationEmail();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            usersImageRef = FirebaseStorage.getInstance().
                                    getReference("UserPics/" + user.getUid() + ".jpg");
                            usersImageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    //GET IMAGE URL TO CREATE ARTICLE
                                    taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Log.wtf("user created", "CREATED IMAGE ON STORAGE, URL : " + uri.toString());
                                            imageUrl = uri.toString();

                                            User newUser = new User(name,username,email,phone, imageUrl);
                                            UsersDB.createUser(user.getUid(), newUser);
                                        }
                                    });
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RegisterUserActivity.this, e.getMessage(),
                                                    Toast.LENGTH_SHORT).show();

                                        }
                                    });



                        } else {

                            //CHECKS IF EMAIL IS ALREADY REGISTERED
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(RegisterUserActivity.this, "You are already registered",
                                        Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(RegisterUserActivity.this, task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }

    private void sendVerificationEmail()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //EMAIL SENT
                            Toast.makeText(RegisterUserActivity.this, "An email was sent to your account", Toast.LENGTH_SHORT).show();
                            // AFTER EMAIL IS SENT LOGOUT
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(RegisterUserActivity.this, LoginActivity.class));
                            finish();
                        }
                        else
                        {
                            // VERIFICATION EMAIL WAS NOT SEND BY ANY ERROR
                            Toast.makeText(RegisterUserActivity.this, "There was an error sending the email, verify if email exists", Toast.LENGTH_SHORT).show();
                            //RESTART THE ACTIVITY
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());

                        }
                    }
                });
    }

    private boolean validateData() {

//        Log.wtf("VALIDATION EMPTY: " , String.valueOf(Validator.isEmpty(nameET,usernameET,emailET,passwordET, phoneET)));
//        Log.wtf("VALIDATION EMAIL: " , String.valueOf(Validator.isEmailValid(emailET)));
//        Log.wtf("VALIDATION PASSWORD: " , String.valueOf(Validator.isPasswordConfirmed(passwordET, passwordConfET)));

        if (imageUri == null) {
            imageView.requestFocus();
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            return false;
        }
        return (!Validator.isEmpty(nameET,usernameET,emailET,passwordET, phoneET)
                && Validator.isEmailValid(emailET));
               // && Validator.isPasswordConfirmed(passwordET, passwordConfET));
                // fix password validator
    }
}