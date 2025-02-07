package com.example.splitupi;

import java.util.HashMap;
import com.google.firebase.firestore.FirebaseFirestore; // Import Firestore
import java.util.List; // Import List
import android.util.Log; // Import Log

public class ExpenseSplitter {
    private HashMap<String, Double> expenses;
    private FirebaseFirestore db; // Firestore instance

    public ExpenseSplitter() {
        expenses = new HashMap<>();
        db = FirebaseFirestore.getInstance(); // Initialize Firestore
    }

    public void addExpense(String name, double amount) {
        expenses.put(name, amount);
    }

    public HashMap<String, Double> getExpenses() {
        return expenses;
    }

    // New method to create a split transaction
    public void createSplitTransaction(String creator, double totalAmount, List<String> participants) {
        HashMap<String, Object> splitData = new HashMap<>();
        splitData.put("created_by", creator);
        splitData.put("total_amount", totalAmount);
        splitData.put("participants", participants);
        splitData.put("created_at", System.currentTimeMillis());

        db.collection("splits")
            .add(splitData)
            .addOnSuccessListener(documentReference -> {
                Log.d("Firestore", "Split created with ID: " + documentReference.getId());
            })
            .addOnFailureListener(e -> {
                Log.w("Firestore", "Error adding split", e);
            });
    }
}
