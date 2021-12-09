package com.twobsoft.lullabies.android

import android.app.Service
import android.content.Intent

import android.R
import android.media.MediaPlayer

import android.os.IBinder
import android.widget.Toast



class BackgroundSoundService: Service() {
    var mediaPlayer: MediaPlayer? = null

    val playlist = arrayOf<Int>(
        com.twobsoft.lullabies.R.raw.file1,
        com.twobsoft.lullabies.R.raw.file2,
        com.twobsoft.lullabies.R.raw.file3,
        com.twobsoft.lullabies.R.raw.file4,
        com.twobsoft.lullabies.R.raw.file5,
        com.twobsoft.lullabies.R.raw.file6,
        com.twobsoft.lullabies.R.raw.file7,
        com.twobsoft.lullabies.R.raw.file8,
        com.twobsoft.lullabies.R.raw.file9,
        com.twobsoft.lullabies.R.raw.file10,
        com.twobsoft.lullabies.R.raw.file11,
        com.twobsoft.lullabies.R.raw.file12,
        com.twobsoft.lullabies.R.raw.file13,
        com.twobsoft.lullabies.R.raw.file14,
        com.twobsoft.lullabies.R.raw.file15,
        com.twobsoft.lullabies.R.raw.file16,
    )




    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, playlist[0])
        mediaPlayer!!.isLooping = true // Set looping
        mediaPlayer!!.setVolume(100f, 100f)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer!!.start()
        Toast.makeText(applicationContext,
            "Playing Bohemian Rashpody in the Background",
            Toast.LENGTH_SHORT).show()

        return startId
    }



    override fun onStart(intent: Intent?, startId: Int) {}
    override fun onDestroy() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
    }

    override fun onLowMemory() {}
}