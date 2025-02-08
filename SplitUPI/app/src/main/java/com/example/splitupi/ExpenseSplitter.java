package com.example.splitupi;

import java.util.HashMap;
import java.util.List;
import com.google.firebase.firestore.FirebaseFirestore;
import android.util.Log;

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

    // Method to create a split transaction
    public void createSplitTransaction(String creator, double totalAmount, List<String> participants) {
        if (totalAmount <= 0) {
            Log.w("ExpenseSplitter", "Attempted to create a split transaction with non-positive total amount.");
            return;
        }
        if (participants == null || participants.isEmpty()) {
            Log.w("ExpenseSplitter", "Attempted to create a split transaction with no participants.");
            return;
        }

        Log.d("ExpenseSplitter", "Creating split transaction: Creator = " + creator 
            + ", Total Amount = " + totalAmount 
            + ", Participants = " + participants.size());

        // Prepare data
        HashMap<String, Object> splitData = new HashMap<>();
        splitData.put("created_by", creator);
        splitData.put("total_amount", totalAmount);
        splitData.put("participants", participants);
        splitData.put("created_at", System.currentTimeMillis());

        // Save to Firestore
        db.collection("splits")
            .add(splitData)
            .addOnSuccessListener(documentReference -> 
                Log.d("Firestore", "Split created with ID: " + documentReference.getId())
            )
            .addOnFailureListener(e -> 
                Log.w("Firestore", "Error adding split", e)
            );
    }
}
