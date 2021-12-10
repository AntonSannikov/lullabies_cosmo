package com.twobsoft.lullabies.android

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.Build
import com.twobsoft.lullabies.R
import com.twobsoft.lullabies.ServicesCoreInterface


class ServicesApi(val context: Context): ServicesCoreInterface, Playable {


    var currentSong = 0
    var isPlaying = false

    var mediaPlayer: MediaPlayer?=null

    var notificationManager: NotificationManager?=null
    var corePlayCallback: () -> Unit = {}
    var corePauseCallback: () -> Unit = {}
    var corePreviousCallback: () -> Unit = {}
    var coreNextCallback: () -> Unit = {}



    val mediaButtonReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.getStringExtra("actionName")
            when (action) {
                CreateNotification.ACTION_PREVIOUS -> onTrackPrevious()
                CreateNotification.ACTION_PLAY -> {
                    if (isPlaying) {
                        onTrackPause()
                    } else {
                        onTrackPlay()
                    }
                }
                CreateNotification.ACTION_NEXT -> onTrackNext()
            }
        }
    }

    fun endOfTrackcallback() {
        println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE")
    }

    init {
        mediaPlayer = MediaPlayer.create(context, BackgroundSoundService.playlist[0].data)

        BackgroundSoundService.mediaPlayer = mediaPlayer
        BackgroundSoundService.endOfTrackCallback = ::endOfTrackcallback
        createChannel()
        context.registerReceiver(mediaButtonReceiver, IntentFilter("TRACKS_TRACKS"))
        context.startService(Intent(context, OnClearFromRecentService::class.java))
    }


    fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CreateNotification.CHANNEL_ID,
                "twoBsoft",
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager = context.getSystemService(NotificationManager::class.java)
            if (notificationManager != null) {
                notificationManager!!.createNotificationChannel(channel)

            }
        }

    }

    override fun initPlayCallback(callback: () -> Unit) {
        corePlayCallback = callback
    }

    override fun initPauseCallback(callback: () -> Unit) {
        corePauseCallback = callback
    }

    override fun initPreviousCallback(callback: () -> Unit) {
        corePreviousCallback = callback
    }

    override fun initNextCallback(callback: () -> Unit) {
        coreNextCallback = callback
    }


    override fun share() {
        println("SHARE")
    }


    override fun playMusic(stageNumber: Int, isSwitching: Boolean) {
        currentSong = stageNumber-1
        isPlaying = !isPlaying
        CreateNotification.createNotification(
            context,
            BackgroundSoundService.playlist[currentSong],
            R.drawable.ic_baseline_pause_24,
            stageNumber-1,
            BackgroundSoundService.playlist.size-1
        )
        val intent = Intent(context, BackgroundSoundService::class.java)
        intent.putExtra("songIndex", currentSong)
        intent.putExtra("action", "playNew")
        context.stopService(intent)
        context.startService(intent)

    }




    override fun onTrackPrevious() {
        if (currentSong == 0) {
            return
        }
        currentSong--
        isPlaying = true

        corePreviousCallback()

        CreateNotification.createNotification(
            context,
            BackgroundSoundService.playlist[currentSong],
            R.drawable.ic_baseline_pause_24,
            currentSong,
            BackgroundSoundService.playlist.size-1
        )

        val intent = Intent(context, BackgroundSoundService::class.java)
        intent.putExtra("songIndex", currentSong)
        intent.putExtra("action", "playNew")
        context.startService(intent)


    }


    override fun onTrackPlay() {

        isPlaying = true

        corePlayCallback()

        CreateNotification.createNotification(
            context,
            BackgroundSoundService.playlist[currentSong],
            R.drawable.ic_baseline_pause_24,
            currentSong,
            BackgroundSoundService.playlist.size-1
        )


        val intent = Intent(context, BackgroundSoundService::class.java)
        intent.putExtra("songIndex", currentSong)
        intent.putExtra("action", "resume")
        context.startService(intent)
    }


    override fun onTrackPause() {

        isPlaying = false

        corePauseCallback()

        CreateNotification.createNotification(
            context,
            BackgroundSoundService.playlist[currentSong],
            R.drawable.ic_baseline_play_arrow_24,
            currentSong,
            BackgroundSoundService.playlist.size-1
        )



        val intent = Intent(context, BackgroundSoundService::class.java)
        intent.putExtra("songIndex", currentSong)
        intent.putExtra("action", "pause")
        context.startService(intent)
    }


    override fun onTrackNext() {
        if (currentSong == BackgroundSoundService.playlist.size - 1) {
            return
        }
        isPlaying = true
        currentSong++

        coreNextCallback()

        CreateNotification.createNotification(
            context,
            BackgroundSoundService.playlist[currentSong],
            R.drawable.ic_baseline_pause_24,
            currentSong,
            BackgroundSoundService.playlist.size-1
        )
        val intent = Intent(context, BackgroundSoundService::class.java)
        intent.putExtra("songIndex", currentSong)
        intent.putExtra("action", "playNew")
        context.startService(intent)
    }


    fun dispose(context: Context) {
        val intent = Intent(context, BackgroundSoundService::class.java)
        context.stopService(intent)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager?.cancelAll()
            context.unregisterReceiver(mediaButtonReceiver)
        }
    }

}