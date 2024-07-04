package it.thomas.myapps.MainAdapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.thomas.myapps.databinding.ItemAppBinding;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    List<AppData> AppDatas;
    AppClickListener appClickListener;
    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var binding = ItemAppBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MainViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        AppData appData = AppDatas.get(position);
        holder.bind(appData);

        holder.binding.getRoot().setOnClickListener(v -> {
            appClickListener.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return AppDatas.size();
    }

    public void setAppDatas(List<AppData> appDatas) {
        this.AppDatas = appDatas;
        notifyDataSetChanged();
    }

    public void setAppClickListener(AppClickListener appClickListener) {
        this.appClickListener = appClickListener;
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        ItemAppBinding binding;

        public MainViewHolder(ItemAppBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(AppData appData) {
            binding.ivAppImage.setImageResource(appData.img());
            binding.tvAppName.setText(appData.name());
        }
    }
}
