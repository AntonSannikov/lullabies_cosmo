package com.twobsoft.babymozartspacetrip.android

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.TimePickerDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.core.app.NotificationManagerCompat
import com.twobsoft.babymozartspacetrip.R
import com.twobsoft.babymozartspacetrip.ServicesCoreInterface
import java.util.*
import kotlin.concurrent.timerTask
import android.media.AudioManager
import android.view.View
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat.startActivity


class ServicesApi(val context: Context): ServicesCoreInterface, Playable {


    var currentSong                 = 0
    override var AVAILABLE_STAGES   = 10
    override var isPlaying          = false
    override var isNeedNewPlay      = true

    var isPaused = false

    var lastVolume      = 0f
    var currentVolume   = 0f
    var timer: Timer ?= null

    var lastSelectedMinute: Int?    = null
    var lastSelectedHour: Int?      = null

    var mediaPlayer: MediaPlayer?   = null


    var notificationManager: NotificationManager? = null
    var corePlayCallback: () -> Unit = {}
    var corePauseCallback: () -> Unit = {}
    var corePreviousCallback: () -> Unit = {}
    var coreNextCallback: () -> Unit = {}
    var coreOnAppPauseCallback: () -> Unit = {}
    var coreOnAppResumeCallback: () -> Unit = {}


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

    fun endOfTrackCallback() {
        onTrackNext()
    }

    init {
        mediaPlayer = MediaPlayer.create(context, BackgroundSoundService.playlist[0].data)

        BackgroundSoundService.mediaPlayer = mediaPlayer
        BackgroundSoundService.endOfTrackCallback = ::endOfTrackCallback
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
        val appPackageName = context.packageName
        val text = "Check out the App at: https://play.google.com/store/apps/details?id=$appPackageName"
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }


    override fun playMusic(stageNumber: Int, isSwitching: Boolean) {

        currentSong = stageNumber-1

        if (isSwitching || isPaused || isNeedNewPlay) {
            isNeedNewPlay = false
            isPaused = false
            isPlaying = true
            CreateNotification.createNotification(
                context,
                BackgroundSoundService.playlist[currentSong],
                R.drawable.ic_baseline_pause_24,
                stageNumber - 1,
                BackgroundSoundService.playlist.size - 1
            )
            val intent = Intent(context, BackgroundSoundService::class.java)
            intent.putExtra("songIndex" , currentSong)
            intent.putExtra("action"    , "playNew")
            context.stopService(intent)
            context.startService(intent)
        } else {
            if (isPlaying) {
                isPlaying = false
                CreateNotification.createNotification(
                    context,
                    BackgroundSoundService.playlist[currentSong],
                    R.drawable.ic_baseline_play_arrow_24,
                    stageNumber - 1,
                    BackgroundSoundService.playlist.size - 1
                )
                val intent = Intent(context, BackgroundSoundService::class.java)
                intent.putExtra("songIndex" , currentSong)
                intent.putExtra("action"    , "pause")
                context.stopService(intent)
                context.startService(intent)
            } else {
                isPlaying = true
                CreateNotification.createNotification(
                    context,
                    BackgroundSoundService.playlist[currentSong],
                    R.drawable.ic_baseline_pause_24,
                    stageNumber - 1,
                    BackgroundSoundService.playlist.size - 1
                )
                val intent = Intent(context, BackgroundSoundService::class.java)
                intent.putExtra("songIndex" , currentSong)
                intent.putExtra("action"    , "resume")
                context.stopService(intent)
                context.startService(intent)
            }
        }

    }


    fun pauseMusic() {
        isPlaying = false
        isPaused = true
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val volume_level = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        val maxVolume: Int = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        lastVolume = volume_level.toFloat() / maxVolume

        CreateNotification.createNotification(
            context,
            BackgroundSoundService.playlist[currentSong],
            R.drawable.ic_baseline_play_arrow_24,
            currentSong,
            BackgroundSoundService.playlist.size - 1
        )

        val delta = 0.05f
        currentVolume = lastVolume
        val timer = Timer()
        timer.schedule(
            timerTask {
                currentVolume -= delta
                if (currentVolume <= 0) {
                    val intent = Intent(context, BackgroundSoundService::class.java)
                    intent.putExtra("songIndex" , currentSong)
                    intent.putExtra("action"    , "pause")
                    context.stopService(intent)
                    context.startService(intent)
                    BackgroundSoundService.mediaPlayer!!.setVolume(lastVolume, lastVolume)
                    corePauseCallback()
                    timer.cancel()
                }
                BackgroundSoundService.mediaPlayer!!.setVolume(currentVolume, currentVolume)
            },
            0, 50
        )
    }


    override fun setLooping(value: Boolean) {
        BackgroundSoundService.mediaPlayer!!.isLooping = value
        BackgroundSoundService.isLooping = value
    }


    override fun createTimer() {
//        val cal = Calendar.getInstance()
        //cal.get(Calendar.HOUR_OF_DAY)
        //cal.get(Calendar.MINUTE)
        if (lastSelectedHour == null) lastSelectedHour = 0
        if (lastSelectedMinute == null) lastSelectedMinute = 30
        Handler(Looper.getMainLooper()).post {
            val dialog = TimePickerDialog(
                context,
                { _, hourOfDay, minute ->
                    if (timer != null) timer!!.cancel()
                    timer = Timer()
                    lastSelectedHour = hourOfDay
                    lastSelectedMinute = minute
                    timer!!.schedule(
                        timerTask { pauseMusic() },
                        (lastSelectedHour!! * 3600000 + lastSelectedMinute!! * 60000).toLong()
                    )
                },
                lastSelectedHour!!, lastSelectedMinute!!,
                true
            )
            dialog.show()
        }
    }


    override fun onTrackPrevious() {
        if (currentSong == 0) {
            currentSong = AVAILABLE_STAGES - 1
        } else {
            currentSong--
        }
        isPlaying = true

        corePreviousCallback()

        CreateNotification.createNotification(
            context,
            BackgroundSoundService.playlist[currentSong],
            R.drawable.ic_baseline_pause_24,
            currentSong,
            BackgroundSoundService.playlist.size - 1
        )

        val intent = Intent(context, BackgroundSoundService::class.java)
        intent.putExtra("songIndex" , currentSong)
        intent.putExtra("action"    , "playNew")
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
            BackgroundSoundService.playlist.size - 1
        )


        val intent = Intent(context, BackgroundSoundService::class.java)
        intent.putExtra("songIndex" , currentSong)
        intent.putExtra("action"    , "resume")
        context.startService(intent)
    }


    override fun onTrackPause() {

        isPlaying = false

        CreateNotification.createNotification(
            context,
            BackgroundSoundService.playlist[currentSong],
            R.drawable.ic_baseline_play_arrow_24,
            currentSong,
            BackgroundSoundService.playlist.size - 1
        )



        val intent = Intent(context, BackgroundSoundService::class.java)
        intent.putExtra("songIndex" , currentSong)
        intent.putExtra("action"    , "pause")
        context.startService(intent)
    }


    override fun onTrackNext() {
        if (currentSong == AVAILABLE_STAGES - 1) {
            currentSong = 0
        } else {
            currentSong++
        }
        isPlaying = true


        coreNextCallback()

        CreateNotification.createNotification(
            context,
            BackgroundSoundService.playlist[currentSong],
            R.drawable.ic_baseline_pause_24,
            currentSong,
            BackgroundSoundService.playlist.size - 1
        )
        val intent = Intent(context, BackgroundSoundService::class.java)
        intent.putExtra("songIndex" , currentSong)
        intent.putExtra("action"    , "playNew")
        context.startService(intent)
    }

    fun dispose(context: Context) {
        val intent = Intent(context, BackgroundSoundService::class.java)
        context.stopService(intent)
        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.cancelAll()
        context.unregisterReceiver(mediaButtonReceiver)

    }

    override fun onPause() {
        coreOnAppPauseCallback()
    }

    override fun onResume() {
        coreOnAppResumeCallback()
    }

    override fun initOnPauseCallback(callback: () -> Unit) {
        coreOnAppPauseCallback = callback
    }

    override fun initOnResumeCallback(callback: () -> Unit) {
        coreOnAppResumeCallback = callback
    }


}