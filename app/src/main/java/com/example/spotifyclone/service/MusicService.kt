package com.example.spotifyclone.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.NotificationCompat
import com.example.spotifyclone.MainActivity
import com.example.spotifyclone.R
import com.example.spotifyclone.screens.detail.movieImage
import com.example.spotifyclone.screens.detail.movieLink
import com.example.spotifyclone.screens.detail.movieName
import com.example.spotifyclone.screens.player.mediaPlayerBinder
import com.squareup.picasso.Picasso
import java.net.URLDecoder
import java.net.URLEncoder


class MusicService() : Service() {
    private lateinit var mediaPlayer: MediaPlayer
    private var lastPlaybackPosition: Int = 0
    private var isForegroundService = false
    private lateinit var notificationManager: NotificationManager
    var notificationLayout: RemoteViews? = null
    var musicName = "Ajj ke baad"
    var musicImage =
        ("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSI1V-71eaUtLGLGHkpTb4kYE6W5Kmz0reauA&usqp=CAU")

    var musicArtist = "Arijit Singh"
    var IsPlaying by mutableStateOf(true)

    companion object {
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "MusicServiceChannel"
    }

    override fun onBind(intent: Intent?): IBinder? {
        return MediaPlayerBinder()
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        //  restorePlaybackState()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        notificationLayout = RemoteViews(packageName, R.layout.notification_layout)
        if (mediaPlayerBinder?.IsPlayingProgress() == true) {
            notificationLayout?.setViewVisibility(R.id.playButton, View.VISIBLE)
            notificationLayout?.setViewVisibility(R.id.pauseButton, View.GONE)
        } else {
            notificationLayout?.setViewVisibility(R.id.playButton, View.GONE)
            notificationLayout?.setViewVisibility(R.id.pauseButton, View.VISIBLE)

        }
//        notificationLayout?.setTextViewText(R.id.SongName,musicName)


        when (intent?.action) {

            "PLAY_MUSIC" -> {
                //          stop()

                val musicLink = intent.getStringExtra("musicLink")
                val musicNameTemp = intent.getStringExtra("musicName")
                val musicImageTemp = intent.getStringExtra("musicImage")
                val musicArtistTemp = intent.getStringExtra("musicArtist")
                //   val musicIsPlaying = intent.getBooleanExtra("IsPlaying",true)
                Log.d("musicName", musicLink.toString())
                Log.d("musicName", musicNameTemp.toString())
                //      Log.d("musicIsplaying", musicIsPlaying.toString())
                musicName = musicNameTemp!!
                musicImage = musicImageTemp!!
                musicArtist = musicArtistTemp!!
                //  IsPlaying= musicIsPlaying

                if (musicLink != null) {
                    mediaPlayer = MediaPlayer().apply {
                        setDataSource(musicLink)
                        prepare()
                        start()
                        IsPlaying = true
                    }
                    startForegroundService()
                    showNotification()
                }
            }

            "PAUSE_MUSIC" -> {
                pause()
                Log.d("isplayingmusic", mediaPlayerBinder?.IsPlayingProgress().toString())

            }

            "RESUME_MUSIC" -> {
                resume()
                Log.d("isplayingmusic", mediaPlayerBinder?.IsPlayingProgress().toString())

            }

            "STOP_MUSIC" -> {
                stop()
                Log.d("isplayingmusic", mediaPlayerBinder?.IsPlayingProgress().toString())

            }
        }

        val playIntent = Intent(this, MusicService::class.java).apply {
            action = "RESUME_MUSIC"
            //        IsPlaying= true
        }
        val playPendingIntent = PendingIntent.getService(
            this,
            0,
            playIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val pauseIntent = Intent(this, MusicService::class.java).apply {
            action = "PAUSE_MUSIC"
            //          IsPlaying= false
        }
        val pausePendingIntent = PendingIntent.getService(
            this,
            0,
            pauseIntent,
            PendingIntent.FLAG_IMMUTABLE
        )


        /* if(mediaPlayerBinder?.IsPlayingProgress()==true){
             notificationLayout?.setViewVisibility(R.id.playButton, View.VISIBLE)
             notificationLayout?.setViewVisibility(R.id.pauseButton, View.GONE)

         }else {
             notificationLayout?.setViewVisibility(R.id.playButton,View.GONE)
             notificationLayout?.setViewVisibility(R.id.pauseButton,View.VISIBLE)

         }*/
        notificationLayout?.setOnClickPendingIntent(R.id.playButton, playPendingIntent)
        notificationLayout?.setOnClickPendingIntent(R.id.pauseButton, pausePendingIntent)


        notificationLayout?.setTextViewText(R.id.SongName, musicName)
        notificationLayout?.setTextViewText(R.id.songArtist, musicArtist)
        return START_STICKY
    }

    private fun startForegroundService() {
        if (!isForegroundService) {
            val notification = createNotification()
            startForeground(NOTIFICATION_ID, notification)
            if (notificationLayout != null) {
                Picasso.get().load(musicImage).into(
                    notificationLayout!!,
                    R.id.background_Image,
                    NOTIFICATION_ID,
                    notification
                )
            }

            isForegroundService = true
        }
    }

    // Function to show the custom notification
    @SuppressLint("RemoteViewLayout")
    private fun createNotification(): Notification {

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setCustomBigContentView(notificationLayout)
            // .setContentTitle(MusicName)
            //  .setContentText("Now Playing")
            .setSmallIcon(R.drawable.downloaded)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun showNotification() {
        notificationManager.notify(NOTIFICATION_ID, createNotification())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Music Player Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }


    private fun pause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                lastPlaybackPosition = it.currentPosition
                IsPlaying = false
                showNotification()
            }
        }
    }

    private fun stop() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
                lastPlaybackPosition = 0
                isForegroundService = false
                IsPlaying = false
                stopForeground(true)
                notificationManager.cancel(NOTIFICATION_ID)
            }
        }
    }

    private fun resume() {
        mediaPlayer?.let {
            it.seekTo(lastPlaybackPosition)
            it.start()
            IsPlaying = true
            showNotification()
        }
    }

    override fun onDestroy() {
        savePlaybackState()
        mediaPlayer?.stop()
        mediaPlayer?.release()
        super.onDestroy()
    }

    inner class MediaPlayerBinder : Binder() {
        fun mediaPlayerPostion(): Int {
            return mediaPlayer.currentPosition
        }

        fun mediaPlayerTotalDuration(): Int {
            return mediaPlayer.duration
        }

        fun mediaPlayerSeekToProgress(position: Int) {
            return mediaPlayer.seekTo(position)
        }

        fun IsPlayingProgress(): Boolean {
            return IsPlaying
        }
    }

    private fun savePlaybackState() {
        val sharedPreferences = getSharedPreferences("PlaybackState", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("currentMusicName", musicName)
        editor.putString("currentMusicImage", musicImage)
        editor.putString("currentMusicArtist", musicArtist)
        editor.putString("currentMusicLink", movieLink)
        editor.putInt("currentPlaybackPosition", mediaPlayer.currentPosition)
        editor.apply()
    }

    private fun restorePlaybackState() {
        val sharedPreferences = getSharedPreferences("PlaybackState", Context.MODE_PRIVATE)
        val currentMusicName = sharedPreferences.getString("currentMusicName", null)
        val currentMusicArtist = sharedPreferences.getString("currentMusicArtist", null)
        val currentMusicLink = sharedPreferences.getString("currentMusicLink", null)
        val currentMusicImage = sharedPreferences.getString("currentMusicImage", null)
        val savedPlaybackPosition = sharedPreferences.getInt("currentPlaybackPosition", 0)

        musicName = currentMusicName!!
        musicArtist = currentMusicArtist!!
        musicName = currentMusicImage!!

        movieLink = URLEncoder.encode(currentMusicName)
        movieName = URLEncoder.encode(currentMusicArtist)
        movieImage = URLEncoder.encode(currentMusicImage)

        val musicString = URLDecoder.decode(currentMusicLink, "UTF-8")
        if (currentMusicLink != null) {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(musicString)
                prepare()
                start()
            }
            startForegroundService()
            showNotification()
        }
    }
}

