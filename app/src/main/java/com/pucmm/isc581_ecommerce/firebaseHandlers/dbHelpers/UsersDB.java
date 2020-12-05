package com.pucmm.isc581_ecommerce.firebaseHandlers.dbHelpers;

import com.google.firebase.database.DatabaseReference;
import com.pucmm.isc581_ecommerce.activities.MainActivity;
import com.pucmm.isc581_ecommerce.models.User;

public class UsersDB {
    private static final DatabaseReference myRef = MainActivity.database.getReference("users");


    public static void createUser(String child, User user) {
        //Creates or updates category ?
        myRef.child(child).setValue(user);
    }

}
