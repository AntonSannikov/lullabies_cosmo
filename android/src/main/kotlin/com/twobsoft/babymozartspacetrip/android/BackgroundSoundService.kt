package com.twobsoft.babymozartspacetrip.android

import android.app.Service
import android.content.Intent

import android.media.MediaPlayer

import android.os.IBinder


class BackgroundSoundService: Service() {


    companion object {
        var mediaPlayer: MediaPlayer?= null
        var isNeedDestroy = false
        var endOfTrackCallback: ()-> Unit = {}
        var isLooping = false
        var id = 0

        val playlist = arrayOf(
            Track("Twinkle Twinkle Little Star", "", com.twobsoft.babymozartspacetrip.R.raw.file1, 1),
            Track("Madamina, il catalogo è questo", "Don Giovanni", com.twobsoft.babymozartspacetrip.R.raw.file2, 2),
            Track("Symphony No.11 in D major", "", com.twobsoft.babymozartspacetrip.R.raw.file3, 3),
            Track("Andiam, Andiam", "Don Giovanni", com.twobsoft.babymozartspacetrip.R.raw.file4, 4),
            Track("Le nozze di Figaro", "Arietta", com.twobsoft.babymozartspacetrip.R.raw.file5, 5),
            Track("Violin Sonata No. 24", "", com.twobsoft.babymozartspacetrip.R.raw.file6, 6),
            Track("Piano Sonata", "Alla turca", com.twobsoft.babymozartspacetrip.R.raw.file7, 7),
            Track("Ho capito, Signor si", "Don Giovanni", com.twobsoft.babymozartspacetrip.R.raw.file8, 8),
            Track("Moonlight sonata (special for A.)", "Beethoven", com.twobsoft.babymozartspacetrip.R.raw.file9, 9),
            Track("Trois beaux enfants fins", "The magic flute", com.twobsoft.babymozartspacetrip.R.raw.file10, 10),
            Track("Twinkle Little Star (Christmas Version)", "", com.twobsoft.babymozartspacetrip.R.raw.file11, 11),
            Track("Variations in F major", "", com.twobsoft.babymozartspacetrip.R.raw.file12, 12),
            Track("Eine Kleine Nachtmusik", "", com.twobsoft.babymozartspacetrip.R.raw.file13, 13),
            Track("Kirie Eleison", "", com.twobsoft.babymozartspacetrip.R.raw.file14, 14),
            Track("Sinfonia Concertante", "", com.twobsoft.babymozartspacetrip.R.raw.file15, 15),
            Track("Serenade in D major", "Haffner", com.twobsoft.babymozartspacetrip.R.raw.file16, 16),
        )
    }



    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        id = startId

        val songIndex = intent!!.getIntExtra("songIndex", 0)
        val action = intent.getStringExtra("action")

        when (action) {
            "playNew" -> {
                mediaPlayer?.reset()
                mediaPlayer = MediaPlayer.create(
                    this, playlist[songIndex].data
                )
                mediaPlayer!!.isLooping = isLooping
                mediaPlayer?.start()
                mediaPlayer!!.setOnCompletionListener { endOfTrackCallback() }
            }
            "resume" -> mediaPlayer?.start()
            "pause" -> mediaPlayer?.pause()

        }

//        Toast.makeText(applicationContext,
//            "$songIndex",
//            Toast.LENGTH_SHORT).show()

        return START_STICKY
    }


    override fun onStart(intent: Intent?, startId: Int) {}



    override fun onDestroy() {
        if (!isNeedDestroy) return
        mediaPlayer?.stop()
        mediaPlayer?.release()
    }

    override fun onLowMemory() {}
}