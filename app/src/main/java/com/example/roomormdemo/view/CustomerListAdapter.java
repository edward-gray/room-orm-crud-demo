package com.example.roomormdemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomormdemo.R;
import com.example.roomormdemo.entity.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.CustomerViewHolder> {

    /// 1
    static class CustomerViewHolder extends RecyclerView.ViewHolder {
        private final TextView customerItemView;
        private ImageView deleteIcon;
        private ImageView updateIcon;

        CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            customerItemView = itemView.findViewById(R.id.textView);
            deleteIcon = itemView.findViewById(R.id.binImageView);
            updateIcon = itemView.findViewById(R.id.updateImageView);
        }
    }

    /// 2

    private final LayoutInflater inflater;
    private List<Customer> customerList;

    public CustomerListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new CustomerViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        if (customerList != null) {
            List<String> customerInfo = new ArrayList<>();
            for (Customer customer : customerList) {
                String info = customer.getName() + ", " + customer.getAge() + ". " + customer.isActive();
                customerInfo.add(info);
            }
            holder.customerItemView.setText(customerInfo.get(position));
            holder.deleteIcon.setTag(position);
            holder.updateIcon.setTag(position);

        } else {
            holder.customerItemView.setText("NO CUSTOMER!");
        }
    }

    @Override
    public int getItemCount() {
        if (customerList != null) {
            return customerList.size();
        }
        return 0;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public Customer getCustomer(int position) {
        return customerList.get(position);
    }

}
