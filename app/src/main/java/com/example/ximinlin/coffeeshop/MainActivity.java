package com.example.ximinlin.coffeeshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    double price = 0;
    int PER_COFFEE = 5;
    int customer_number = 0;

    boolean summary_generated = false;

    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize toast object
        toast = Toast.makeText(this, "nothing", Toast.LENGTH_SHORT);

        //setup textedit listener
        EditText editText = (EditText) findViewById(R.id.quantities_vals);
        editText.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                String input = editText.getText().toString();
                                                if (input.length() != 0) {
                                                    int tmp = Integer.valueOf(input);
                                                    if (tmp < 0){
                                                        toast.cancel();
                                                        toast = Toast.makeText(MainActivity.this,"cannot have less than 0 coffee",Toast.LENGTH_SHORT);
                                                        toast.show();
                                                        editText.setText("0");
                                                    }
                                                    else if (tmp > 50){
                                                        toast.cancel();
                                                        toast = Toast.makeText(MainActivity.this,"cannot have more than 50 coffee",Toast.LENGTH_SHORT);
                                                        toast.show();
                                                        editText.setText("0");
                                                    }
                                                    quantity = tmp;
                                                    price = quantity*PER_COFFEE;
                                                    summary_generated = false;
                                                }
                                            }
                                        }
        );

    }

    public void send_order(View v) {
        if(summary_generated == false){
            toast.cancel();
            toast = Toast.makeText(MainActivity.this,"Please click on OK button to generate order summary",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        customer_number += 1;
        quantity = 0;

        clear_text();

        toast.cancel();
        toast = Toast.makeText(this,"Order Sent for Customer No." + String.valueOf(customer_number),Toast.LENGTH_SHORT);
        toast.show();
        summary_generated = false;
    }

    public void add_quantity(View v) {
        summary_generated = false;
        if (quantity >= 50){
            toast.cancel();
            toast = Toast.makeText(MainActivity.this,"cannot have less than 0 coffee",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        quantity += 1;
        display_quantity();
    }

    public void minus_quantity(View v) {
        summary_generated = false;
        if (quantity == 0){
            toast.cancel();
            toast = Toast.makeText(MainActivity.this,"cannot have more than 50 coffee",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        quantity -= 1;
        display_quantity();
    }


    private void display_quantity() {
        EditText text = (EditText) findViewById(R.id.quantities_vals);
        text.setText(String.valueOf(quantity));
    }

    private void clear_text() {
        CheckBox tmp = (CheckBox) findViewById(R.id.whipped_cream);
        tmp.setChecked(false);
        tmp = (CheckBox) findViewById(R.id.beans);
        tmp.setChecked(false);
        tmp = (CheckBox) findViewById(R.id.apples);
        tmp.setChecked(false);
        tmp = (CheckBox) findViewById(R.id.ground_peanuts);
        tmp.setChecked(false);
        EditText quantities = (EditText) findViewById(R.id.quantities_vals);
        quantities.setText("0");

        Button temp = (Button) findViewById(R.id.order_button);
        temp.setText("$ 0");
    }

    public void updateSummary(View view){
        summary_generated = true;
        Button text = (Button)findViewById(R.id.order_button);
        String toppings = "Toppings:";
        CheckBox ch1 = (CheckBox) findViewById(R.id.whipped_cream);
        toppings += ch1.isChecked() ? "\n\tWhipped Cream" : "";
        price += ch1.isChecked() ? 1.5*quantity : 0;
        CheckBox ch2 = (CheckBox) findViewById(R.id.beans);
        toppings += ch2.isChecked() ? "\n\tBeans" : "";
        price += ch2.isChecked() ? 2.5*quantity : 0;
        CheckBox ch3 = (CheckBox) findViewById(R.id.ground_peanuts);
        toppings += ch3.isChecked() ? "\n\tGrounded Peanuts" : "";
        price += ch3.isChecked() ? 1.5*quantity : 0;
        CheckBox ch4 = (CheckBox) findViewById(R.id.apples);
        toppings += ch4.isChecked() ? "\n\tApples" : "";
        price += ch4.isChecked() ? 2.5*quantity : 0;

        toppings += "\n\nTotal sums to $" + String.valueOf(price);
        text.setText(toppings);
    }


}
