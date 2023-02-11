package com.tamiang.smart

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        if(message.data.isNotEmpty()){
            Log.d(TAG, message.data.get("nm_pasien").toString())
            Log.d(TAG, message.data.get("pesan").toString())

            showNotification(applicationContext, message.data.get("nm_pasien").toString(),message.data.get("pesan").toString())
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")

        val sharedPreference =  getSharedPreferences("TOKEN_FCM", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putString("token",token)
        editor.apply()
    }


    fun showNotification(context: Context, nm_pasien: String?, pesan: String?){
        val ii = Intent(context, HomeActivity::class.java)
        ii.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NO_HISTORY
        val pi = PendingIntent.getActivity(context, 0, ii, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification: Notification
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(getNotificationIcon())
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.icon_simrs))
                .setAutoCancel(true)
                .setStyle(NotificationCompat.BigTextStyle().bigText(pesan))
                .setContentIntent(pi)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setOngoing(true)
                .setContentTitle(nm_pasien).build()
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, nm_pasien, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)
            notificationManager.notify(NOTIFICATION_ID, notification)
        }else{
            notification = NotificationCompat.Builder(context)
                .setSmallIcon(getNotificationIcon())
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.icon_simrs))
                .setAutoCancel(true)
                .setStyle(NotificationCompat.BigTextStyle().bigText(pesan))
                .setOngoing(true)
                .setContentIntent(pi)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentTitle(nm_pasien).build()
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }

    private fun getNotificationIcon(): Int {
        val useWhiteIcon =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        return if (useWhiteIcon) R.drawable.icon_simrs else R.drawable.icon_simrs
    }

    companion object{

        private const val TAG = "MyFirebaseMsgService"
        var NOTIFICATION_CHANNEL_ID = "com.milaparsia.rsia"
        val NOTIFICATION_ID = 1234

    }
}