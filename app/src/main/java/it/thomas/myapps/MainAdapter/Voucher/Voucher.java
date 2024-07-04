package it.thomas.myapps.MainAdapter.Voucher;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.thomas.myapps.R;
import it.thomas.myapps.databinding.ActivityVoucherBinding;

public class Voucher extends AppCompatActivity {

    private ActivityVoucherBinding binding;
    private List<Product> productList = new ArrayList<>();
    private VoucherAdapter voucherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVoucherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initUI();
        initListener();
    }

    private void initListener() {
        binding.fabAddProduct.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProductAdd.class);
            startActivityForResult(intent, 123);
        });

        voucherAdapter.setProductDeleteListener(product -> {
            productList.remove(product);
            updateProductList();
        });

        binding.etFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Product> filterProduct = List.copyOf(productList);
                if (!s.toString().isEmpty()) {
                    filterProduct = filterProduct.stream().filter(product ->
                            product.name().contains(s.toString())
                    ).collect(Collectors.toList());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initUI() {
        voucherAdapter = new VoucherAdapter();
        binding.rvProducts.setAdapter(voucherAdapter);
        binding.rvProducts.setLayoutManager(new LinearLayoutManager(this));
    }

    private void updateProductList() {
        voucherAdapter.setProductList(productList);
        Double totalAmount = 0.0;
        for (Product product : productList) {
            totalAmount += (product.price() * product.quantity());
        }
        binding.tvTotalAmount.setText(totalAmount.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            Double price = data.getDoubleExtra("price", 0.0);
            Integer quantity = data.getIntExtra("quantity", 0);
            Product newProduct = new Product("", name, price, quantity);
            productList.add(newProduct);
            updateProductList();
        }
    }
}