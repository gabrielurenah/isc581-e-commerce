package com.pucmm.isc581_ecommerce.firebaseHandlers.dbHelpers;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.pucmm.isc581_ecommerce.activities.MainActivity;
import com.pucmm.isc581_ecommerce.models.Category;
import com.pucmm.isc581_ecommerce.models.Product;
import com.pucmm.isc581_ecommerce.models.User;

public class UsersDB {
    private static final DatabaseReference myRef = MainActivity.database.getReference("users");
    private static User currentUser;


    public static void createUser(String child, User user) {
        //Creates or updates category ?
        myRef.child(child).setValue(user);
    }

    public static User getUser() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUser = null;
        myRef.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUser = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return currentUser;
    }
}
