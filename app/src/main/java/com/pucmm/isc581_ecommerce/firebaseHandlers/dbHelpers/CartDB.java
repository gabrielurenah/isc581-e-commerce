package com.pucmm.isc581_ecommerce.firebaseHandlers.dbHelpers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.pucmm.isc581_ecommerce.activities.MainActivity;
import com.pucmm.isc581_ecommerce.models.Cart;

import java.util.ArrayList;

public class CartDB {
    private static final DatabaseReference myRef = MainActivity.database.getReference("cart");
    private static ArrayList<Cart> orders = new ArrayList<>();



    public static void createOrder(Cart cart) {
        //Creates or updates category ?
        String id = genKey();
        cart.setId(id);
        Gson gson = new Gson();
        Log.wtf("CART TO CREATE",gson.toJson(cart));
        myRef.child(id).setValue(cart);
    }

    public static String genKey() {
        return myRef.push().getKey();
    }

    public static ArrayList<Cart> getOrders() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.wtf("SE DATA VAL", "VAL: " + snapshot);
                orders.clear();
                for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                    Cart value = orderSnapshot.getValue(Cart.class);
                    Log.wtf("ADDING ORDERS ", String.valueOf(value));

                    orders.add(value);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.wtf("ORDERS ON THE WAY", orders.toString());
        return orders;
    }

}
