package com.oldcaledonia.babymozartspacetrip.ios

import com.twobsoft.babymozartspacetrip.ServicesCoreInterface
import org.robovm.apple.avfoundation.*
import org.robovm.apple.avkit.AVPlayerViewController
import org.robovm.apple.coregraphics.CGSize
import org.robovm.apple.foundation.NSBundle
import org.robovm.apple.mediaplayer.*
import org.robovm.objc.block.Block1
import org.robovm.apple.dispatch.DispatchQueue
import org.robovm.apple.foundation.NSArray
import org.robovm.apple.foundation.NSData
import org.robovm.apple.foundation.NSObject
import org.robovm.apple.uikit.*
import org.robovm.objc.Selector
import org.robovm.objc.block.VoidBlock1


class ServicesApi : ServicesCoreInterface, Playable {

    override var AVAILABLE_STAGES: Int = 15
    override var isPlaying: Boolean = false
    override var isNeedNewPlay: Boolean = true

    var player: AVAudioPlayer?=null
    var image: UIImage?=null

    override fun init() {
//        UIApplication.getSharedApplication().beginReceivingRemoteControlEvents()
        val url = NSBundle.getMainBundle().findResourceURL(
                "iphone-notification-icon-20@3x",
                "png"
        )
        val data = NSData.read(url)
        image = UIImage(data)
    }

    private fun setupNowPlaying() {

        val artwork = MPMediaItemArtwork(
                image!!.size,
                Block1 {
                    return@Block1 image
                }
        )
        val nowPlayingInfo = MPNowPlayingInfo().also {
            it.artwork = artwork
            it.artist = "Test Artist"
            it.title = "Title"
            it.albumTitle = "Album Title"
            it.playbackRate = 1.0
            it.playbackDuration = 40.0
            it.elapsedPlaybackTime = 10.0
            it.playbackQueueCount = 2
            it.playbackQueueIndex = 1
        }
        MPNowPlayingInfoCenter.getDefaultCenter().nowPlayingInfo = nowPlayingInfo

    }


    override fun initPlayCallback(callback: () -> Unit) {
        println("")
    }

    override fun initPauseCallback(callback: () -> Unit) {
        println("")
    }

    override fun initPreviousCallback(callback: () -> Unit) {
        println("")
    }

    override fun initNextCallback(callback: () -> Unit) {
        println("")
    }

    override fun share() {
        DispatchQueue.getMainQueue().async {
            val dialog = UIAlertController("Test", "Message", UIAlertControllerStyle.Alert)
            dialog.addAction(
                    UIAlertAction(
                            "Action 1",
                            UIAlertActionStyle.Default,
                            VoidBlock1 {
                                println("ACTION")
                            }
                    )
            )

            UIApplication.getSharedApplication().windows[0].rootViewController.presentViewController(
                    dialog,
                    true,
                    Runnable {  }
            )

        }
    }


    override fun playMusic(stageNumber: Int, isSwitching: Boolean) {

        val audioSession = AVAudioSession.getSharedInstance()
        audioSession.category = null
        audioSession.category = AVAudioSessionCategory.Playback
        audioSession.setActive(true)
        val url = NSBundle.getMainBundle().findResourceURL("file1", "mp3")
        player?.release()
        player = AVAudioPlayer(url)
        setupLockScreen()
        player!!.numberOfLoops = -1
        player!!.prepareToPlay()
        player!!.play()

    }


    fun setupLockScreen() {

        val commandCenter = MPRemoteCommandCenter.getSharedCommandCenter()
        commandCenter.nextTrackCommand.isEnabled = true
        commandCenter.previousTrackCommand.isEnabled = true
        commandCenter!!.playCommand.addTarget(
                Block1 {
                    isPlaying = true
                    player?.play()
                    return@Block1 MPRemoteCommandHandlerStatus.Success
                }
        )
        commandCenter.pauseCommand.addTarget(
                Block1 {
                    isPlaying = false
                    player?.pause()
                    return@Block1 MPRemoteCommandHandlerStatus.Success
                }
        )

        setupNowPlaying()
    }


    override fun setLooping(value: Boolean) {
        println("")
    }

    override fun createTimer() {
        DispatchQueue.getMainQueue().async {
            val timePicker = UIDatePicker().also {
                it.datePickerMode = UIDatePickerMode.Time
                it.addTarget(
                        it,
                        Selector.register("timePickerValueChanged"),
                        UIControlEvents.ValueChanged
                )
                it.frame.size = CGSize(150.0, 150.0)
            }

            UIApplication.getSharedApplication().windows[0].rootViewController.presentViewController(
                    timePicker.inputViewController,
                    true,
                    Runnable {  }
            )
        }


    }

    fun timePickerValueChanged(sender: UIDatePicker) {
        println("")
    }


    override fun onPause() {
        println("")
    }

    override fun onResume() {
        println("")
    }

    override fun initOnPauseCallback(callback: () -> Unit) {
        println("")
    }

    override fun initOnResumeCallback(callback: () -> Unit) {
        println("")
    }

    override fun onTrackPrevious() {
        println("")
    }

    override fun onTrackPlay() {
        println("")
    }

    override fun onTrackPause() {
        println("")
    }

    override fun onTrackNext() {
        println("")
    }


}


