package com.pucmm.isc581_ecommerce.models;

import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Cart {
    String id;
    float total;
    HashMap<Product, Integer> cart ;
//    ArrayList<Product> productsa ;

    public Cart() {
        this.total = 0;
        cart = new HashMap<>();
//        productsa = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getTotal() {
        return total;
    }

    public HashMap<Product, Integer> getCart() {
        return cart;
    }

    public void pushProduct(Product product) {
        if(cart.containsKey(product)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                cart.replace(product, cart.get(product) + 1);
            }
        }else {
            cart.put(product, 1);
        }

        Log.wtf("CART HASHMAP" , String.valueOf(cart));
    }

    public void removeProductQuantity(Product product) {
        if(cart.containsKey(product)) {

            if(cart.get(product) == 1) {
                Log.wtf("CART HASHMAP" , String.valueOf(cart));
                cart.remove(product);
                return;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                cart.replace(product, cart.get(product) - 1);
            }

        }else {
            cart.put(product, 1);
        }

        Log.wtf("CART HASHMAP" , String.valueOf(cart));
    }


//    public ArrayList<Product> getProducts() {
//        if(productsa!= null) {
//            return productsa;
//        }
//        return new ArrayList<>();
//    }

    public ArrayList<Product> getProductsArr(){

        ArrayList<Product> productsArr = new ArrayList<>();
        Log.wtf("CART GETPRODS" , String.valueOf(cart));

        for (Product prod : cart.keySet()) {
            productsArr.add(prod);
        }
        return productsArr;
    }

    public void deleteProduct(Product product) {
        Log.wtf("CART HASHMAP" , String.valueOf(cart));
        cart.remove(product);
        Log.wtf("CART HASHMAP AFTER DELETE " , String.valueOf(cart));
//        CartActivity.reloadAdapter();

    }

    public Integer getQuantityFromProd(Product product) {
        return cart.get(product);
    }

    public void calculateTotal() {
        total = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cart.forEach((product, quantity) -> {
                total += (product.getPrice() * quantity);
            });
        }
    }

    public void cleanCart() {
        this.id="";
        this.total = 0;
        cart = new HashMap<>();
    }

//    public void populateProductsQuantity() {
//        for (Product prod : cart.keySet()) {
//            prod.setQuantity(String.valueOf(cart.get(prod)));
//            Log.wtf("POPULATING PRODS", String.valueOf(prod.getQuantity()));
//        }
//    }
}
