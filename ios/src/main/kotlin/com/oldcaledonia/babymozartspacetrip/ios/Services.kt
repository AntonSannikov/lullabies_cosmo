package com.oldcaledonia.babymozartspacetrip.ios

import com.twobsoft.babymozartspacetrip.ServicesCoreInterface
import org.robovm.apple.avfoundation.*
import org.robovm.apple.avkit.AVPlayerViewController
import org.robovm.apple.foundation.NSBundle
import org.robovm.apple.mediaplayer.*
import org.robovm.apple.uikit.UIApplication
import org.robovm.objc.block.Block1
import org.robovm.apple.dispatch.DispatchQueue
import org.robovm.apple.foundation.NSArray
import org.robovm.apple.foundation.NSData
import org.robovm.apple.foundation.NSObject
import org.robovm.apple.uikit.UIImage
import org.robovm.objc.Selector
import java.io.File


class ServicesApi : ServicesCoreInterface, Playable {

    override var AVAILABLE_STAGES: Int = 15
    override var isPlaying: Boolean = false
    override var isNeedNewPlay: Boolean = true

    var player: AVAudioPlayer?=null
    var commandCenter = MPRemoteCommandCenter.getSharedCommandCenter()
    val nowPlayingInfoCenter = MPNowPlayingInfoCenter.getDefaultCenter()
    val audioSession = AVAudioSession.getSharedInstance()


    override fun init() {
        UIApplication.getSharedApplication().beginReceivingRemoteControlEvents()
    }

    private fun setupNowPlaying() {
        DispatchQueue.getMainQueue().async {
            val url = NSBundle.getMainBundle().findResourceURL(
                    "iphone-notification-icon-20@3x",
                    "png"
            )
            val data = NSData.read(url)
            val image = UIImage(data)
            val artwork = MPMediaItemArtwork(
                    image.size,
                    Block1 {
                        return@Block1 image
                    }
            )
            val nowPlayingInfo = MPNowPlayingInfo()
                    .setArtwork(artwork)
                    .setArtist("Test")
                    .setTitle("Title")
                    .setArtist("Artist")
                    .setAlbumTitle("Album Title")
                    .setPlaybackDuration(10.0)
                    .setPlaybackRate(1.0)
            nowPlayingInfoCenter.nowPlayingInfo = nowPlayingInfo
        }

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
        println("")
    }

    fun setCommandTargets() {
        commandCenter.playCommand.isEnabled = true
        commandCenter.pauseCommand.isEnabled = true
        commandCenter!!.playCommand.addTarget(
                Block1 {

                    return@Block1 MPRemoteCommandHandlerStatus.Success
                }
        )
        commandCenter!!.pauseCommand.addTarget(
                Block1 {

                    return@Block1 MPRemoteCommandHandlerStatus.Success
                }
        )
//        commandCenter!!.previousTrackCommand.addTarget(
//                Block1 {
//                    return@Block1 MPRemoteCommandHandlerStatus.Success
//                }
//        )
//        commandCenter!!.nextTrackCommand.addTarget(
//                Block1 {
//                    return@Block1 MPRemoteCommandHandlerStatus.Success
//                }
//        )
    }

    override fun playMusic(stageNumber: Int, isSwitching: Boolean) {
        DispatchQueue.getMainQueue().async {
            audioSession.setCategory(
                    AVAudioSessionCategory.Playback,
            )
            audioSession.setActive(true)
            val url = NSBundle.getMainBundle().findResourceURL("file1", "mp3")
            player?.release()
            player = AVAudioPlayer(url)
            player!!.numberOfLoops = -1
            player!!.play()
            setupLockScreen()
//        setCommandTargets()
//        setupNowPlaying()

        }

    }


    fun setupLockScreen() {
        DispatchQueue.getMainQueue().async {
            val commandCenter = MPRemoteCommandCenter.getSharedCommandCenter()
            commandCenter.togglePlayPauseCommand.isEnabled = true
            commandCenter.nextTrackCommand.isEnabled = true
            commandCenter.togglePlayPauseCommand.addTarget(
                    Block1 {
                        controlPlay()
                        return@Block1 MPRemoteCommandHandlerStatus.Success
                    }
            )
            setupNowPlaying()
        }

    }


    fun controlPlay() {
        DispatchQueue.getMainQueue().async {
            if (isPlaying) {
                player?.pause()
                isPlaying = false
            } else {
                player?.play()
                isPlaying = true
            }
        }

    }

    override fun setLooping(value: Boolean) {
        println("")
    }

    override fun createTimer() {
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


