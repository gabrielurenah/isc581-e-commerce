package com.pucmm.isc581_ecommerce.firebaseHandlers.dbHelpers;

import com.google.firebase.database.DatabaseReference;
import com.pucmm.isc581_ecommerce.activities.MainActivity;
import com.pucmm.isc581_ecommerce.models.Cart;
import com.pucmm.isc581_ecommerce.models.User;

public class CartDB {
    private static final DatabaseReference myRef = MainActivity.database.getReference("cart");


    public static void createOrder(Cart cart) {
        //Creates or updates category ?
        String id = genKey();
        cart.setId(id);
        myRef.child(id).setValue(cart);
    }

    public static String genKey() {
        return myRef.push().getKey();
    }
}
