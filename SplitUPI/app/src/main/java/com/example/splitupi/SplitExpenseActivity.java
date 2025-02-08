package com.example.splitupi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SplitExpenseActivity extends AppCompatActivity {
    private EditText amountEditText;
    private EditText upiIdEditText;
    private EditText participantsEditText;
    private Button splitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_expense);

        amountEditText = findViewById(R.id.amount_edit_text);
        upiIdEditText = findViewById(R.id.upi_id_edit_text);
        participantsEditText = findViewById(R.id.participants_edit_text);
        splitButton = findViewById(R.id.split_button);

        splitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                splitExpense();
            }
        });
    }

    private void splitExpense() {
        String amountStr = amountEditText.getText().toString();
        String upiId = upiIdEditText.getText().toString();
        String participantsStr = participantsEditText.getText().toString();

        if (amountStr.isEmpty() || upiId.isEmpty() || participantsStr.isEmpty() || Double.parseDouble(amountStr) <= 0) {
            Toast.makeText(this, "Please fill all fields and ensure the amount is positive", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountStr);
        List<String> participants = new ArrayList<>();
        for (String participant : participantsStr.split(",")) {
            participants.add(participant.trim());
        }

        // Create a split transaction
        ExpenseSplitter expenseSplitter = new ExpenseSplitter();
        expenseSplitter.createSplitTransaction(upiId, amount, participants);

        // Initiate UPI payment
        initiateUpiPayment(upiId, amountStr, "Expense Split");

        Toast.makeText(this, "Expense split created successfully", Toast.LENGTH_SHORT).show();
        finish(); // Close the activity
    }

    private void initiateUpiPayment(String upiId, String amount, String note) {
        // Logic to initiate UPI payment (similar to MainActivity)
    }
}
