package com.example.roomormdemo.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roomormdemo.entity.Customer;

import java.util.List;

@Dao
public interface CustomerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insertCustomer(Customer customer);

    @Query("SELECT * FROM Customer")
    LiveData<List<Customer>> findAllCustomers();

    @Query("SELECT * FROM Customer WHERE id =:customerId")
    LiveData<Customer> getCustomer(int customerId);

    @Update
    void updateCustomer(Customer customer);

    @Delete
    void deleteCustomer(Customer customer);

}
