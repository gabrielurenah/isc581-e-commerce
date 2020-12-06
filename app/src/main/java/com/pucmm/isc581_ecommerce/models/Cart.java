package com.pucmm.isc581_ecommerce.models;

import android.os.Build;

import java.util.ArrayList;

public class Cart {
    ArrayList<Product> products;
    float total;

    public Cart() {
        this.products = new ArrayList<>();
        this.total = 0;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public float getTotal() {
        return total;
    }

    public void pushProduct(Product product) {
        this.products.add(product);
    }

    public void calculateTotal() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getProducts().forEach(product -> {
                total += Float.valueOf(product.getPrice());
            });
        }
    }
}
