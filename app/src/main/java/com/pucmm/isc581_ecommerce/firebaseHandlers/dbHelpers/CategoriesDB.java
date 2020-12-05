package com.pucmm.isc581_ecommerce.firebaseHandlers.dbHelpers;

import com.google.firebase.database.DatabaseReference;
import com.pucmm.isc581_ecommerce.activities.MainActivity;
import com.pucmm.isc581_ecommerce.models.Category;
import com.pucmm.isc581_ecommerce.models.Product;

import java.util.ArrayList;

public class CategoriesDB {
    private static final DatabaseReference myRef = MainActivity.database.getReference("categories");


    public static void manageCategory(String child, Category value) {
        //Creates or updates category ?
        myRef.child(child).setValue(value);
    }

    public static void updateProducts(String child, ArrayList<Product> products) {
        myRef.child(child).child("products").setValue(products);
    }

    public static void deleteCategory(String child) {
        myRef.child(child).removeValue();
    }

    public static String generateID() {
        return myRef.push().getKey();
    }


}
