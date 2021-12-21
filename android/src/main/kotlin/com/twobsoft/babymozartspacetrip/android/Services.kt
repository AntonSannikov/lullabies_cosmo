package com.twobsoft.babymozartspacetrip.android

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.TimePickerDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import androidx.core.app.NotificationManagerCompat
import com.twobsoft.babymozartspacetrip.R
import com.twobsoft.babymozartspacetrip.ServicesCoreInterface
import java.util.*
import kotlin.concurrent.timerTask
import android.media.AudioManager
import android.view.View
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat.startActivity
import android.content.ComponentName

import android.content.ServiceConnection
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.*
import androidx.appcompat.content.res.AppCompatResources


class ServicesApi(val context: Context): ServicesCoreInterface, Playable {



    lateinit var mService: BackgroundSoundService
    private var mBound: Boolean = false
    private var playlist: Array<Track>? = null


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


    // BINDER CONNECTION ===========================================================================
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as BackgroundSoundService.LocalBinder
            mService = binder.getService()
            mBound = true
            mService.initEndOfTrackCallback(::endOfTrackCallback)
            playlist = mService.playlist
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }
    // BINDER CONNECTION ===========================================================================


    init {
        createChannel()
        context.registerReceiver(mediaButtonReceiver, IntentFilter("TRACKS_TRACKS"))
        context.startService(Intent(context, OnClearFromRecentService::class.java))
        Intent(context, BackgroundSoundService::class.java).also {
            context.bindService(it, connection, Context.BIND_AUTO_CREATE)
        }
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

        context.startService(Intent(context, BackgroundSoundService::class.java))
        if (isSwitching || isPaused || isNeedNewPlay) {
            isNeedNewPlay = false
            isPaused = false
            isPlaying = true
            CreateNotification.createNotification(
                context,
                playlist!![currentSong],
                R.drawable.ic_baseline_pause_24,
                stageNumber - 1,
                playlist!!.size - 1
            )
            mService.play("playNew", currentSong)

        } else {
            if (isPlaying) {
                isPlaying = false
                CreateNotification.createNotification(
                    context,
                    playlist!![currentSong],
                    R.drawable.ic_baseline_play_arrow_24,
                    stageNumber - 1,
                    playlist!!.size - 1
                )
                mService.play("pause", currentSong)
            } else {
                isPlaying = true
                CreateNotification.createNotification(
                    context,
                    playlist!![currentSong],
                    R.drawable.ic_baseline_pause_24,
                    stageNumber - 1,
                    playlist!!.size - 1
                )
                mService.play("resume", currentSong)
            }
        }
    }


    fun pauseMusic() {
        isPlaying = false
        isPaused = true
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val volumeLevel = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        val maxVolume: Int = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        lastVolume = volumeLevel.toFloat() / maxVolume

        CreateNotification.createNotification(
            context,
            playlist!![currentSong],
            R.drawable.ic_baseline_play_arrow_24,
            currentSong,
            playlist!!.size - 1
        )

        val delta = 0.05f
        currentVolume = lastVolume
        val timer = Timer()
        timer.schedule(
            timerTask {
                currentVolume -= delta
                if (currentVolume <= 0) {
                    mService.play("pause", currentSong)
                    mService.setVolume(lastVolume)
                    corePauseCallback()
                    timer.cancel()
                }
                mService.setVolume(currentVolume)
            },
            0, 50
        )
    }


    override fun setLooping(value: Boolean) {
        mService.setIsLooping(value)
    }


    override fun createTimer() {
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
//            val drw = AppCompatResources.getDrawable(context, R.mipmap.dialog_frame)
//            dialog.window?.setBackgroundDrawable(drw)
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
            playlist!![currentSong],
            R.drawable.ic_baseline_pause_24,
            currentSong,
            playlist!!.size - 1
        )

        mService.play("playNew", currentSong)
    }


    override fun onTrackPlay() {

        isPlaying = true

        corePlayCallback()

        CreateNotification.createNotification(
            context,
            playlist!![currentSong],
            R.drawable.ic_baseline_pause_24,
            currentSong,
            playlist!!.size - 1
        )

        mService.play("resume", currentSong)
    }


    override fun onTrackPause() {

        isPlaying = false

        CreateNotification.createNotification(
            context,
            playlist!![currentSong],
            R.drawable.ic_baseline_play_arrow_24,
            currentSong,
            playlist!!.size - 1
        )

        mService.play("pause", currentSong)
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
            playlist!![currentSong],
            R.drawable.ic_baseline_pause_24,
            currentSong,
            playlist!!.size - 1
        )
        mService.play("playNew", currentSong)
    }



    fun dispose(context: Context) {
        mService.isNeedDestroy = true
        context.unbindService(connection)
        mBound = false
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

