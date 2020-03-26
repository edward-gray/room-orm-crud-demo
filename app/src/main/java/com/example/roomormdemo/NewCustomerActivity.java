package com.example.roomormdemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.roomormdemo.entity.Customer;

public class NewCustomerActivity extends AppCompatActivity {

    public static final String NEW_CUSTOMER = "NEW_CUSTOMER";
    public static final String UPDATED_CUSTOMER = "UPDATED_CUSTOMER";

    EditText nameEditText;
    EditText ageEditText;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);

        nameEditText = findViewById(R.id.nameEditText);
        ageEditText = findViewById(R.id.ageEditText);
        saveButton = findViewById(R.id.saveButton);

        int reqCode = getIntent().getIntExtra("requestCode", 0);
        Intent replyIntent = new Intent();

        System.out.println("req code ----- --- - --- --- -" + reqCode);

        if (reqCode != 0) {
            handleRequest(replyIntent, reqCode);
        }
    }

    public void handleRequest(Intent intent, int requestCode) {
        System.out.println("I WAS STARTED ----- --- - --- --- -");
        if (requestCode == MainActivity.NEW_CUSTOMER_ACTIVITY_REQUEST_CODE) {
            System.out.println("111111111");

            saveButton.setText("Save");

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Customer customer = new Customer();
                    if (!TextUtils.isEmpty(nameEditText.getText())) {
                        if (!TextUtils.isEmpty(ageEditText.getText())) {
                            customer.setName(nameEditText.getText().toString());
                            customer.setAge(Integer.parseInt(ageEditText.getText().toString()));
                            customer.setActive(true);
                            intent.putExtra(NEW_CUSTOMER, customer);
                            setResult(RESULT_OK, intent);
                        } else {
                            setResult(RESULT_CANCELED, intent);
                        }
                    } else {
                        setResult(RESULT_CANCELED, intent);
                    }
                    finish();
                }
            });
        }

        if (requestCode == MainActivity.UPDATE_CUSTOMER_ACTIVITY_REQUEST_CODE) {
            System.out.println("222222222222");
            Customer customer = (Customer) getIntent().getSerializableExtra(UPDATED_CUSTOMER);
            if (customer != null) {

                saveButton.setText("Update");
                nameEditText.setText(customer.getName());
                ageEditText.setText(String.valueOf(customer.getAge()));

                System.out.println("CUS   ----- --- --- ---- ->" + customer.getName());

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(nameEditText.getText())) {
                            if (!TextUtils.isEmpty(ageEditText.getText())) {
                                customer.setName(nameEditText.getText().toString());
                                customer.setAge(Integer.parseInt(ageEditText.getText().toString()));
                                customer.setActive(true);
                                intent.putExtra(UPDATED_CUSTOMER, customer);
                                setResult(RESULT_OK, intent);
                            } else {
                                setResult(RESULT_CANCELED, intent);
                            }
                        } else {
                            setResult(RESULT_CANCELED, intent);
                        }
                        finish();
                    }
                });
            }
        }
    }
}
