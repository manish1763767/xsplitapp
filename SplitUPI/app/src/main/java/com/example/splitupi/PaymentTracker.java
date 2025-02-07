package com.example.splitupi;

import java.util.HashMap;
import com.google.firebase.firestore.FirebaseFirestore; // Import Firestore

public class PaymentTracker {
    private HashMap<String, Boolean> paymentStatus;
    private FirebaseFirestore db; // Firestore instance

    public PaymentTracker() {
        paymentStatus = new HashMap<>();
        db = FirebaseFirestore.getInstance(); // Initialize Firestore
    }

    public void addPayment(String name, boolean isPaid) {
        paymentStatus.put(name, isPaid);
    }

    public HashMap<String, Boolean> getPaymentStatus() {
        return paymentStatus;
    }

    // New method to update payment status in Firestore
    public void updatePaymentStatus(String splitId, String participant, boolean isPaid) {
        db.collection("splits").document(splitId)
            .update("participants." + participant, isPaid ? "paid" : "pending")
            .addOnSuccessListener(aVoid -> {
                Log.d("Firestore", "Payment status updated for " + participant);
            })
            .addOnFailureListener(e -> {
                Log.w("Firestore", "Error updating payment status", e);
            });
    }
}
