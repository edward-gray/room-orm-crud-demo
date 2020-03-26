package com.example.roomormdemo.repository;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.roomormdemo.config.CustomerDatabase;
import com.example.roomormdemo.dao.CustomerDao;
import com.example.roomormdemo.entity.Customer;

import java.util.List;

public class CustomerRepository {

    private CustomerDao customerDao;

    public CustomerRepository(Application application) {
        CustomerDatabase database = CustomerDatabase.getDatabase(application);
        customerDao = database.customerDao();
    }

    public LiveData<List<Customer>> findAll() {
        return customerDao.findAllCustomers();
    }

    public LiveData<Customer> findCustomerById(int id) {
        return customerDao.getCustomer(id);
    }

    public void insert(Customer customer) {
        CustomerDatabase.databaseWriteExecutor.execute(() -> {
            customerDao.insertCustomer(customer);
        });
    }

    public void update(Customer customer) {
        CustomerDatabase.databaseWriteExecutor.execute(() -> {
            customerDao.updateCustomer(customer);
        });
    }

    @SuppressLint("StaticFieldLeak")
    public void delete(Customer customer) {
        CustomerDatabase.databaseWriteExecutor.execute(() -> {
            customerDao.deleteCustomer(customer);

        });
    }

}
