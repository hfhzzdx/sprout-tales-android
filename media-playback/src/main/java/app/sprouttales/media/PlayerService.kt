package app.sprouttales.media

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.media3.session.MediaSession
import app.sprouttales.tts.TtsEngine

class PlayerService : Service() {
    private lateinit var tts: TtsEngine
    private var session: MediaSession? = null

    override fun onCreate() {
        super.onCreate()
        tts = TtsEngine(this)
        createChannel()
        startForeground(1, buildNotification("星芽故事正在播放"))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (session == null) {
            // TODO: init MediaSession
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        session?.release()
        tts.shutdown()
        super.onDestroy()
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            val nm = getSystemService(NotificationManager::class.java)
            val ch = NotificationChannel("sprout_tales", "播放", NotificationManager.IMPORTANCE_LOW)
            nm.createNotificationChannel(ch)
        }
    }

    private fun buildNotification(text: String): Notification =
        NotificationCompat.Builder(this, "sprout_tales")
            .setContentTitle("星芽故事")
            .setContentText(text)
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setOngoing(true)
            .build()
}
