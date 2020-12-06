package com.pucmm.isc581_ecommerce.firebaseHandlers.dbHelpers;

import com.google.firebase.database.DatabaseReference;
import com.pucmm.isc581_ecommerce.activities.MainActivity;
import com.pucmm.isc581_ecommerce.models.Category;
import com.pucmm.isc581_ecommerce.models.Product;

import java.util.ArrayList;

public class ProductsDB {

    private static final DatabaseReference myRef = MainActivity.database.getReference("products");

    public static void manageProducts(String child, Product value) {
        //Creates or updates category ?
        myRef.child(child).setValue(value);
    }

    public static void deleteProduct(Product product) {
        CategoriesDB.removeProductFromCategory(product.getCategoryID(), product.getUuid());
        myRef.child(product.getUuid()).removeValue();
    }

    public static String generateID() {
        return myRef.push().getKey();
    }

}
