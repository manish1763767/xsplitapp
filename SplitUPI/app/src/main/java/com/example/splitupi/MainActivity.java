package com.example.splitupi;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button splitExpenseButton = findViewById(R.id.split_expense_button);
        Button paymentTrackerButton = findViewById(R.id.payment_tracker_button);
        
        splitExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SplitExpenseActivity.class);
                startActivity(intent);
            }
        });

        paymentTrackerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PaymentTrackerActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initiateUpiPayment(String upiId, String amount, String note) {
        Uri uri = Uri.parse("upi://pay")
                .buildUpon()
                .appendQueryParameter("pa", upiId)  // UPI ID
                .appendQueryParameter("pn", "Split Payment")
                .appendQueryParameter("mc", "")  // Merchant Code (optional)
                .appendQueryParameter("tid", String.valueOf(System.currentTimeMillis()))  // Unique Transaction ID
                .appendQueryParameter("tr", String.valueOf(System.currentTimeMillis()))  // Transaction Ref ID
                .appendQueryParameter("tn", note)  // Transaction Note
                .appendQueryParameter("am", amount)  // Amount
                .appendQueryParameter("cu", "INR")  // Currency
                .build();

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.nbu.paisa.user");  // Google Pay
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No UPI app found", Toast.LENGTH_SHORT).show();
        }
    }
}
