package com.pucmm.isc581_ecommerce.ui.profile;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.Fragment;

        import android.content.Intent;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.bumptech.glide.Glide;
        import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
        import com.google.firebase.auth.FirebaseAuth;
        import com.pucmm.isc581_ecommerce.R;
        import com.pucmm.isc581_ecommerce.activities.LoginActivity;
        import com.pucmm.isc581_ecommerce.firebaseHandlers.dbHelpers.UsersDB;
        import com.pucmm.isc581_ecommerce.models.User;

public class ProfileFragment extends Fragment {

    private ImageView profile_image;
    private TextView name;
    private TextView username;
    private TextView email;
    private TextView phone;
    private ExtendedFloatingActionButton logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_profile_view, container, false);

        profile_image = root.findViewById(R.id.profile_image);
        name = root.findViewById(R.id.tv_profile_user);
        username = root.findViewById(R.id.tv_profile_username);
        email = root.findViewById(R.id.tv_profile_email);
        phone = root.findViewById(R.id.tv_profile_phone);
        logout = root.findViewById(R.id.logout_btn);


        User user = UsersDB.getUserByUid(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        String image_url = user.getImageUrl();

        if ( ! TextUtils.isEmpty(image_url) ) {
            Glide.with(this).load(image_url).into(profile_image);
        }

        name.setText( user.getName() );
        username.setText( user.getUsername() );
        email.setText( user.getEmail() );
        phone.setText( user.getPhone() );

        logout.setOnClickListener(v-> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            getContext().startActivity(intent);
        });
        return root;
    }
}