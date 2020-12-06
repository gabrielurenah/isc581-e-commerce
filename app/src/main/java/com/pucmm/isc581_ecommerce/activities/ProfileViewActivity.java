package com.pucmm.isc581_ecommerce.activities;

        import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;
        import android.text.TextUtils;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.bumptech.glide.Glide;
        import com.pucmm.isc581_ecommerce.R;
        import com.pucmm.isc581_ecommerce.firebaseHandlers.dbHelpers.UsersDB;
        import com.pucmm.isc581_ecommerce.models.User;

public class ProfileViewActivity extends AppCompatActivity {

    private ImageView profile_image;
    private TextView name;
    private TextView username;
    private TextView email;
    private TextView phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        profile_image = findViewById(R.id.profile_image);
        name = findViewById(R.id.tv_profile_user);
        username = findViewById(R.id.tv_profile_username);
        email = findViewById(R.id.tv_profile_email);
        phone = findViewById(R.id.tv_profile_phone);

        User user = UsersDB.getUser();
        String image_url = user.getImageUrl();

        if ( ! TextUtils.isEmpty(image_url) ) {
            Glide.with(this).load(image_url).into(profile_image);
        }

        name.setText( user.getName() );
        username.setText( user.getUsername() );
        email.setText( user.getEmail() );
        phone.setText( user.getPhone() );

    }
}