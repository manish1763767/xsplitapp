package com.example.splitupi;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore; // Import Firestore
import com.google.firebase.firestore.QueryDocumentSnapshot; // Import Firestore document
import com.google.firebase.firestore.QuerySnapshot; // Import Firestore query snapshot
import android.util.Log; // Import Log
import java.util.HashMap; // Import HashMap

public class PaymentTrackerActivity extends AppCompatActivity {
    private TextView paymentStatusTextView;
    private FirebaseFirestore db; // Firestore instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_tracker);

        paymentStatusTextView = findViewById(R.id.payment_status_text_view);
        db = FirebaseFirestore.getInstance(); // Initialize Firestore

        // Logic to retrieve and display payment statuses
        displayPaymentStatuses();
    }

    private void displayPaymentStatuses() {
        db.collection("splits")
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    StringBuilder statuses = new StringBuilder();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        HashMap<String, Object> data = (HashMap<String, Object>) document.getData();
                        statuses.append("Split ID: ").append(document.getId()).append("\n");
                        statuses.append("Participants: ").append(data.get("participants")).append("\n");
                        statuses.append("Total Amount: ").append(data.get("total_amount")).append("\n\n");
                    }
                    paymentStatusTextView.setText(statuses.toString());
                } else {
                    Log.w("Firestore", "Error getting documents.", task.getException());
                }
            });
    }
}
