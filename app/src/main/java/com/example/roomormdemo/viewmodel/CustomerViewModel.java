package com.example.roomormdemo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roomormdemo.entity.Customer;
import com.example.roomormdemo.repository.CustomerRepository;

import java.util.List;

public class CustomerViewModel extends AndroidViewModel {

    private CustomerRepository customerRepository;

    private LiveData<List<Customer>> customersLiveData;


    public CustomerViewModel(@NonNull Application application) {
        super(application);
        customerRepository = new CustomerRepository(application);
        customersLiveData = customerRepository.findAll();
    }

    public LiveData<List<Customer>> findAll() {
        return customersLiveData;
    }

    public void insert(Customer customer) {
        customerRepository.insert(customer);
    }

    public void delete(Customer customer) {
        customerRepository.delete(customer);
    }

    public void update(Customer customer) {
        customerRepository.update(customer);
    }
}
