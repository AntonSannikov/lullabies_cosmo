package com.twobsoft.lullabies.android

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import com.twobsoft.lullabies.R
import com.twobsoft.lullabies.ServicesCoreInterface


class ServicesApi(val context: Context): ServicesCoreInterface {

    val playlist = arrayListOf<Int>()
    var currentSong = 0
    var isPlaying = false


    init {
        playlist.add(R.raw.file1)
        playlist.add(R.raw.file2)
        playlist.add(R.raw.file3)
        playlist.add(R.raw.file4)
        playlist.add(R.raw.file5)
        playlist.add(R.raw.file6)
        playlist.add(R.raw.file7)
        playlist.add(R.raw.file8)
        playlist.add(R.raw.file9)
        playlist.add(R.raw.file10)
        playlist.add(R.raw.file11)
        playlist.add(R.raw.file12)
        playlist.add(R.raw.file13)
        playlist.add(R.raw.file14)
        playlist.add(R.raw.file15)
        playlist.add(R.raw.file16)
    }

    var mediaPlayer = MediaPlayer.create(context, playlist[0])

    override fun share() {
        println("SHARE")
    }



    override fun playMusic(stageNumber: Int, isSwitching: Boolean) {

        val intent = Intent(context, BackgroundSoundService::class.java)
        context.startService(intent)


//        if (!isPlaying) {
//            if (!isSwitching) {
//                if (currentSong == stageNumber - 1) {
//                    isPlaying = true
//                    mediaPlayer!!.start()
//                    return
//                }
//                currentSong = stageNumber - 1
//                createNewPlay()
//            }
//
//        } else {
//            if (!isSwitching) {
//                isPlaying = false
//                mediaPlayer!!.pause()
////                mediaPlayer!!.seekTo(0)
//            } else {
//                currentSong = stageNumber - 1
//                createNewPlay()
//            }
//        }
    }

    fun createNewPlay() {
        mediaPlayer!!.reset()
        mediaPlayer = MediaPlayer.create(context, playlist[currentSong])
        isPlaying = true

        // startService(Intent(MainActivity.this, BackGroundMusic.class))
//        mediaPlayer!!.start()
    }

    override fun playNext() {
//        if (currentSong <= playlist.size - 2) {
//            currentSong++
//            playMusic(currentSong + 1, true)
//        }
    }


    override fun playPrevious() {
//        if (currentSong >= 1) {
//            playMusic(currentSong + 1, true)
//        }
    }


}