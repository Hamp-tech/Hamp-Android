package com.hamp.firebase

import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.hamp.extensions.logd

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        logd("From: " + remoteMessage!!.from!!)

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            logd("Message data payload: " + remoteMessage.data)
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            logd("Message Notification Body: " + remoteMessage.notification!!.body!!)
//            sendNotification(remoteMessage)
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val intent: Intent

        val clickAction = remoteMessage.notification!!.clickAction
//        if (clickAction != null) {
//            if (clickAction == "RANKING") {
//                intent = Intent(this, FriendsActivity_::class.java)
//            } else if (clickAction == "COURSES") {
//                intent = Intent(this, CourseStart_::class.java)
//                intent.putExtra("course_id", remoteMessage.data["course_id"])
//            } else {
//                intent = Intent(applicationContext, MainActivity_::class.java)
//            }
//        } else {
//            intent = Intent(applicationContext, MainActivity_::class.java)
//        }

//        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

//        val pendingIntent = PendingIntent.getActivity(
//                this,
//                1,
//                intent,
//                PendingIntent.FLAG_UPDATE_CURRENT)
//
//        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//
//        val notificationBuilder = NotificationCompat.Builder(this, "Digitalite")
//                .setSmallIcon(if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) R.mipmap.icon else R.mipmap.icon_notif_48)
//                .setColor(ContextCompat.getColor(applicationContext, R.color.notifications_icon_background))
//                .setContentTitle("Digitalite")
//                .setContentText(remoteMessage.notification!!.body)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setPriority(PRIORITY_HIGH)
//                .setContentIntent(pendingIntent)
//
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.notify(1, notificationBuilder.build())
    }
}
