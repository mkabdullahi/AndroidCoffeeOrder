package com.habibu.zaki.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    //global variable to show quantity values
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        /*
            Add icon on the actionBar
         */
        getSupportActionBar ().setDisplayUseLogoEnabled (true);
        getSupportActionBar ().setDisplayShowHomeEnabled (true);
        getSupportActionBar ().setIcon (R.drawable.ic_coffee);

        setContentView (R.layout.activity_main);

    }

    /*
        Method to increment the values of quantity when clicked
     */
    public void increment(View view) {
        /*
            Control the possibility of more than 100 order numbers
         */
        if(quantity == 100)
        {
            Toast.makeText (this, "You could not make coffee order more than 100 cups", Toast.LENGTH_SHORT).show ();
            return;

        }
        quantity = quantity + 1;
        display (quantity);
    }

    /*
        Method to decrement the values of quantity when clicked
     */
    public void decrement(View view) {
        /*
            Controle the possibilty of making negative orders
         */
        if(quantity == 1 )
        {
            Toast.makeText (this, "You could not make coffee order less than 1 cups", Toast.LENGTH_SHORT).show ();
            return;
        }
        quantity = quantity - 1;
        display (quantity);
    }

    /*
      Method to response for the onClick action
     */
    public void submitOrder(View view) {

        /*
        int price = calculatePrice ();

            displayPrice(quantity * 5);

        String priceMessage = "Total: $" + price + "\nThank you!";
        displayMessage (priceMessage);

        String priceMessage = createOrderSummary (price);
        displayMessage (priceMessage);
        */
        /*
            Added @n18/Jan/2017
            @Param Checkbox
         */
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById (R.id.whipeed_cream_checkebox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked ();

        CheckBox chocolateCheckBox = (CheckBox) findViewById (R.id.chocolate_checkebox);
        boolean hasChocolate = chocolateCheckBox.isChecked ();

        EditText editText = (EditText) findViewById (R.id.order_name_field);
        String orderName = editText.getText ().toString ();
        editText.setText ("");
        /*
          Validate inputText if it is empty
         */
        if(orderName.matches (""))
        {
            Toast.makeText (this, "Please fill in your name", Toast.LENGTH_SHORT).show ();
            return;
        }
        int price = calculatePrice (hasWhippedCream, hasChocolate);
        String orderMessage = createOrderSummary (price, hasWhippedCream, hasChocolate, orderName);


        Intent intent = new Intent (Intent.ACTION_VIEW);
        intent.setData (Uri.parse ("mailto: "));
        //intent.putExtra (Intent.EXTRA_EMAIL, addresses);
        intent.putExtra (Intent.EXTRA_SUBJECT, "Coffee order for " + orderName);
        intent.putExtra (Intent.EXTRA_TEXT, orderMessage);
        if(intent.resolveActivity (getPackageManager ()) != null)
        {
            startActivity (intent);
        }

    }
    /*
        Another display method
     */

    protected void display(int number) {
        TextView quantityOfCoffees;
        quantityOfCoffees = (TextView) findViewById (R.id.quantity_text_view);
        quantityOfCoffees.setText ("" + number);
    }

    /*
        Method to create order summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String orderName) {
        /*
            condition to check if whipped cream been added
         */
        String whippedCreamAdded = addWhippedCream ? "Yes" : "No";
        String chocolateAdded = addChocolate ? "Yes" : "No";


        String priceMessage = "Name: " + orderName;
        priceMessage += "\nNo. of Cups " + quantity;
        priceMessage += "\nW. Cream added ? " + whippedCreamAdded;
        priceMessage += "\nChocolate added ?" + chocolateAdded;
        priceMessage += "\nTotal price: $" + price;
        priceMessage += "\nThank you!";

        return priceMessage;


    }

    /*
        Methods to calculate total price to be paid
     */
    private int calculatePrice(boolean addWhipped, boolean addChocolate)
    {
        int basePrice = 5;
        /*
            @params addWhipped checks whether the user choose whipped cream
            @params addChocolate checks whether the user choose aditional chocolate
         */
        if (addWhipped)
        {
            basePrice = basePrice + 2;
        }
        if(addChocolate)
        {
            basePrice = basePrice + 1;
        }
        return quantity * basePrice;
    }




}
