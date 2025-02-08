package com.example.splitupi;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import com.google.firebase.firestore.FirebaseFirestore; // Import Firestore

public class NotificationHelper {
    private Context context;
    private static final String CHANNEL_ID = "payment_reminders";
    private FirebaseFirestore db; // Firestore instance

    public NotificationHelper(Context context) {
        this.context = context;
        createNotificationChannel();
        db = FirebaseFirestore.getInstance(); // Initialize Firestore
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Payment Reminders",
                NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    public void sendNotification(String title, String message) {
        Log.d("NotificationHelper", "Sending notification: " + title + " - " + message);
        Notification notification = new Notification.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build();

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(1, notification);
        }
    }

    // New method to send payment reminders
    public void sendPaymentSuccess(String participant) {
        String message = "Success: " + participant + ", your payment has been completed.";
        sendNotification("Payment Success", message);
    }

    public void sendPaymentFailure(String participant) {
        String message = "Failure: " + participant + ", there was an issue with your payment.";
        sendNotification("Payment Failure", message);
    }
    public void sendPaymentReminder(String splitId, String participant) {
        String message = "Reminder: " + participant + ", please complete your payment for split ID: " + splitId;
        sendNotification("Payment Reminder", message);
    }
}
