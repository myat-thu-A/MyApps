package it.thomas.myapps.MainAdapter.Voucher;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.thomas.myapps.databinding.ItemVoucherProductBinding;


public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder>{
    private List<Product> productList = new ArrayList<>();
    private ProductDeleteListener productDeleteListener;
    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemVoucherProductBinding binding = ItemVoucherProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new VoucherViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.binding.tvProductName.setText(product.name());
        holder.binding.tvPrice.setText(product.price().toString());
        holder.binding.tvQuantity.setText(product.quantity().toString());

        holder.binding.btProductDelete.setOnClickListener(v -> {
            productDeleteListener.onDelete(product);
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    public void setProductDeleteListener(ProductDeleteListener productDeleteListener) {
        this.productDeleteListener = productDeleteListener;
    }

    public class VoucherViewHolder extends RecyclerView.ViewHolder{
        ItemVoucherProductBinding binding;
        public VoucherViewHolder(ItemVoucherProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
