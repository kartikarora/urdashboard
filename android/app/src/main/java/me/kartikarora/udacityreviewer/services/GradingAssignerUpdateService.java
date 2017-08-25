package me.kartikarora.udacityreviewer.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import chipset.potato.Potato;
import me.kartikarora.udacityreviewer.BuildConfig;
import me.kartikarora.udacityreviewer.R;


/**
 * Developer: chipset
 * Package : me.kartikarora.udacityreviewer.services
 * Project : UdacityReviewer
 * Date : 2/6/17
 */

public class GradingAssignerUpdateService extends FirebaseMessagingService {
    private static final String LOG_TAG = GradingAssignerUpdateService.class.getName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        try {
            String type = remoteMessage.getData().get("type");
            switch (type) {
                case "assignment":
                    sendNotification("New Review", remoteMessage.getData().get("message"), remoteMessage.getData().get("url"));
                    break;
                case "registration":
                    Potato.potate(getApplicationContext()).Preferences()
                            .putSharedPreference(getString(R.string.pref_udacity_token), remoteMessage.getData().get("token"));
                    break;
                case "activation":
                case "deactivation":
                    sendNotification("Configuration Change", remoteMessage.getData().get("message"), null);
                    break;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private void sendNotification(String type, String messageBody, String action) {

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_info)
                .setContentTitle(type)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri);

        if (action != null) {
            Intent urlIntent = new Intent(Intent.ACTION_VIEW)
                    .setData(Uri.parse(action))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingUrlIntent = PendingIntent.getActivity(this, BuildConfig.VERSION_CODE, urlIntent,
                    PendingIntent.FLAG_ONE_SHOT);
            notificationBuilder.addAction(R.drawable.ic_launch, "View Submission", pendingUrlIntent);
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(BuildConfig.VERSION_CODE * (type + messageBody + action).length(), notificationBuilder.build());
    }
}
