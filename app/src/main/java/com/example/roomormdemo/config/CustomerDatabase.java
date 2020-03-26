package com.example.roomormdemo.config;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.roomormdemo.dao.CustomerDao;
import com.example.roomormdemo.entity.Customer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Customer.class}, version = 1, exportSchema = false)
public abstract class CustomerDatabase extends RoomDatabase {

    public abstract CustomerDao customerDao();

    private static volatile CustomerDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static CustomerDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CustomerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            CustomerDatabase.class,
                            "customer_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
