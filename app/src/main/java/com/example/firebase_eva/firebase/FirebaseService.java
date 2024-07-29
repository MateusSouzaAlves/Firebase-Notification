package com.example.firebase_eva.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.firebase_eva.MainActivity;
import com.example.firebase_eva.NotificationActivity;
import com.example.firebase_eva.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class FirebaseService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {

           //Mensagem Data
        if (!message.getData().isEmpty()){

            String titulo = message.getData().get("titulo");
            String messageData = message.getData().get("mensagem");
            String name = message.getData().get("nome");
            String urlImagem = message.getData().get("urlimagem");

            String messageCompleteData = messageData + " -- " + name;

            sendNotificationWithImage(titulo,messageCompleteData, urlImagem);
        }
           //Mensagem Simples
        else if (message.getNotification() != null){

            String title = message.getNotification().getTitle();
            String messageBody = message.getNotification().getBody();

            sendNotification(title, messageBody);

        }
    }

    private void sendNotification(String title,String message){

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        String channel = getString(R.string.default_notification_channel_id);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, channel)
                .setSmallIcon(R.drawable.ic_laucher_eva)
                .setContentTitle(title)
                .setContentText(message)
                .setSound(sound)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification.build());
    }

    private void sendNotificationWithImage(String title,String message, String urlImage){

        Bitmap bitmap = null;

        try {
            bitmap = Picasso.get().load(urlImage).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Intent intent = new Intent(this, NotificationActivity.class);

        intent.putExtra("url", urlImage);
        intent.putExtra("mensagem", message);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        String channel = getString(R.string.default_notification_channel_id);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, channel)
                .setSmallIcon(R.drawable.ic_laucher_eva)
                .setContentTitle(title)
                .setContentText(message)
                .setSound(sound)
                .setLargeIcon(bitmap)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification.build());
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }
}
