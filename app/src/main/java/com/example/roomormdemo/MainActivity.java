package com.example.roomormdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomormdemo.entity.Customer;
import com.example.roomormdemo.view.CustomerListAdapter;
import com.example.roomormdemo.viewmodel.CustomerViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_CUSTOMER_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_CUSTOMER_ACTIVITY_REQUEST_CODE = 2;

    CustomerViewModel customerViewModel;
    CustomerListAdapter customerListAdapter;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewCustomerActivity.class);
                intent.putExtra("requestCode", NEW_CUSTOMER_ACTIVITY_REQUEST_CODE);
                startActivityForResult(intent, NEW_CUSTOMER_ACTIVITY_REQUEST_CODE);
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        customerListAdapter = new CustomerListAdapter(this);
        recyclerView.setAdapter(customerListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);

        customerViewModel.findAll().observe(this, new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customers) {
                customerListAdapter.setCustomerList(customers);
            }
        });
    }

    public void deleteCustomer(View view) {
        ImageView bin = (ImageView) view;
        int customerPosition = (int) bin.getTag();

        new AlertDialog.Builder(this)
                .setTitle("Are you sure?")
                .setMessage("Are you sure you want to delete " + customerListAdapter.getCustomer(customerPosition).getName())
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        customerViewModel.delete(customerListAdapter.getCustomer(customerPosition));
                        customerListAdapter.getCustomerList().remove(customerPosition);
                        customerListAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void updateCustomer(View view) {
        ImageView bin = (ImageView) view;
        int customerPosition = (int) bin.getTag();

        Intent intent = new Intent(MainActivity.this, NewCustomerActivity.class);
        intent.putExtra(NewCustomerActivity.UPDATED_CUSTOMER, customerListAdapter.getCustomer(customerPosition));
        intent.putExtra("requestCode", UPDATE_CUSTOMER_ACTIVITY_REQUEST_CODE);
        startActivityForResult(intent, UPDATE_CUSTOMER_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_CUSTOMER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Customer customer = (Customer) data.getSerializableExtra(NewCustomerActivity.NEW_CUSTOMER);
            customerViewModel.insert(customer);
            customerListAdapter.notifyDataSetChanged();
        } else if (requestCode == NEW_CUSTOMER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_CANCELED){
            Toast.makeText(
                    getApplicationContext(),
                    "Couldn't save Customer",
                    Toast.LENGTH_LONG).show();
        }

        if (requestCode == UPDATE_CUSTOMER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Customer customer = (Customer) data.getSerializableExtra(NewCustomerActivity.UPDATED_CUSTOMER);
            customerViewModel.update(customer);
            customerListAdapter.notifyDataSetChanged();
            finish();
            startActivity(getIntent());
        } else if (requestCode == UPDATE_CUSTOMER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_CANCELED) {
            Toast.makeText(
                    getApplicationContext(),
                    "Couldn't update Customer",
                    Toast.LENGTH_LONG).show();
        }
    }
}
