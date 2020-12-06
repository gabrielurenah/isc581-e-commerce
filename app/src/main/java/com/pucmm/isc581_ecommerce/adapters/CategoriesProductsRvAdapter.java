package com.pucmm.isc581_ecommerce.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pucmm.isc581_ecommerce.models.Product;

import java.util.ArrayList;

public class CategoriesProductsRvAdapter extends RecyclerView.Adapter<CategoriesProductsRvAdapter.MyRecyclerItemViewHolder>{
    private ArrayList<Product> products;
    private Context context;

    public CategoriesProductsRvAdapter(ArrayList<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoriesProductsRvAdapter.MyRecyclerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesProductsRvAdapter.MyRecyclerItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class MyRecyclerItemViewHolder extends RecyclerView.ViewHolder {
        public MyRecyclerItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
