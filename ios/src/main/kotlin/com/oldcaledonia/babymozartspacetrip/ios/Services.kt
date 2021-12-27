package com.oldcaledonia.babymozartspacetrip.ios

import com.twobsoft.babymozartspacetrip.ServicesCoreInterface
import org.robovm.apple.avfoundation.*
import org.robovm.apple.coregraphics.CGPoint
import org.robovm.apple.coregraphics.CGRect
import org.robovm.apple.coregraphics.CGSize
import org.robovm.apple.dispatch.DispatchQueue
import org.robovm.apple.mediaplayer.*
import org.robovm.objc.block.Block1
import org.robovm.apple.foundation.*
import org.robovm.apple.uikit.*
import org.robovm.objc.block.VoidBlock1





class ServicesApi : ServicesCoreInterface, Playable {

    override var AVAILABLE_STAGES: Int = 15
    override var isPlaying: Boolean = false
    var isPaused = false
    override var isNeedNewPlay: Boolean = true

    var timer: NSTimer?=null
    var selectedTime = 1800.0


    var player: AVAudioPlayer?=null
    var image: UIImage?=null
    var lastVolume: Float = 0f
    var currentVolume = 0f
    var currentTrack = 0
    var isPlayingPrepare = false




    override fun init() {
        val url = NSBundle.getMainBundle().findResourceURL(
                "iphone-notification-icon-20@3x",
                "png"
        )
        val data = NSData.read(url)
        image = UIImage(data)
        val audioSession = AVAudioSession.getSharedInstance()
        audioSession.category = null
        audioSession.category = AVAudioSessionCategory.Playback
        audioSession.setActive(true)
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
        val item = NSURL("https://apps.apple.com")
        val activityVc = UIActivityViewController(NSArray(item), null)
        activityVc.excludedActivityTypes = listOf(
                UIActivityType.AirDrop(),
                UIActivityType.AssignToContact(),
                UIActivityType.Message(),
                UIActivityType.Mail(),
                UIActivityType.CopyToPasteboard(),
                UIActivityType.PostToFacebook(),
                UIActivityType.PostToTwitter(),
                UIActivityType.PostToVimeo(),
                UIActivityType.PostToFlickr(),
        )
        UIApplication.getSharedApplication().windows[0].rootViewController.presentViewController(
                activityVc, true, null
        )
    }


    // =============================================================================================
    //                                              TIMER
    //
    override fun createTimer() {

        val view = NSBundle.getMainBundle().loadNib(
                "timepicker", null, null)[0] as UIView

        val image = view.getViewWithTag(100)
        image.backgroundColor = UIColor.fromPatternImage(
                resize(
                        UIImage.getImage("dialog_frame.png"),
                        CGSize(image.bounds.width, image.bounds.height)
                )
        )

        val datePicker = view.getViewWithTag(11) as UIDatePicker
        datePicker.timeZone = NSTimeZone("UTC")
        datePicker.date = NSDate(selectedTime)
        datePicker.addAction(
                UIAction(
                        VoidBlock1 {
                            selectedTime = (it.sender as UIDatePicker).countDownDuration
                        }
                ),
                UIControlEvents.ValueChanged
        )

        val okButton = view.getViewWithTag(33) as UIButton
        okButton.addAction(
                UIAction(
                        VoidBlock1 {
                            timer?.invalidate()
                            timer = NSTimer(
                                    10.0,
//                                    selectedTime,
                                    false,
                                    VoidBlock1 {
                                        pauseMusic()
                                    },
                                    true
                            )
                            view.removeFromSuperview()
                        }
                ),
                UIControlEvents.TouchUpInside
        )

        val cancelButton = view.getViewWithTag(22) as UIButton
        cancelButton.addAction(
                UIAction(
                        VoidBlock1 {
                            view.removeFromSuperview()
                        }
                ),
                UIControlEvents.TouchUpInside
        )

        UIApplication.getSharedApplication().windows[0].addSubview(view)

    }


    fun pauseMusic() {
        if (player == null) return
        isPlaying = false
        isNeedNewPlay = true
        lastVolume = player!!.volume

        // CORE PAUSE CALLBACK !!!!!!!!!!!!!!!!!
        val delta = 0.08f
        currentVolume = lastVolume
        val fadeTimer = NSTimer(
                0.05,
                true,
                VoidBlock1 {
                    currentVolume -= delta
                    if (currentVolume <= 0) {
                        player!!.volume = 0f
                        player!!.pause()
                        player!!.volume = lastVolume
                        player!!.currentTime = 0.0
//                        preparePlaying(currentTrack+1)
                        setupLockScreen()
                        it.invalidate()
                    }
                    player!!.volume = currentVolume
                },
                true
        )

    }
    //
    //                                              TIMER
    // =============================================================================================


    fun preparePlaying(stageNumber: Int) {
        currentTrack = stageNumber-1
        val url = NSBundle.getMainBundle().findResourceURL("file1", "mp3")
        player = AVAudioPlayer(url)
        player!!.prepareToPlay()
        isPlayingPrepare = true
    }


    override fun playMusic(stageNumber: Int, isSwitching: Boolean) {
        if (isNeedNewPlay) {
            if (!isPlayingPrepare) preparePlaying(stageNumber)
            player!!.play()
            isPlaying = true
        } else {
            isPlaying = true
            player?.play()
        }

        setupLockScreen()
    }


    fun setupLockScreen() {

        val commandCenter = MPRemoteCommandCenter.getSharedCommandCenter()
        commandCenter.nextTrackCommand.isEnabled = true
        commandCenter.previousTrackCommand.isEnabled = true

        commandCenter.playCommand.isEnabled = !isPlaying
        commandCenter.playCommand.addTarget(
                Block1 {
                    playMusic(currentTrack+1, false)
                    return@Block1 MPRemoteCommandHandlerStatus.Success
                }
        )

        commandCenter.pauseCommand.isEnabled = isPlaying
        commandCenter.pauseCommand.addTarget(
                Block1 {
                    isPlaying = false
                    isNeedNewPlay = false
                    player?.pause()
                    setupLockScreen()
                    return@Block1 MPRemoteCommandHandlerStatus.Success
                }
        )
        setupNowPlaying()

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
            it.playbackRate = if (isPlaying) 1.0 else 0.0
            it.playbackDuration = player!!.duration
            it.elapsedPlaybackTime = player!!.currentTime
            it.playbackQueueCount = 2
            it.playbackQueueIndex = 1
        }
        MPNowPlayingInfoCenter.getDefaultCenter().nowPlayingInfo = nowPlayingInfo

    }


    fun resize(image: UIImage, size: CGSize): UIImage {
       return UIGraphicsImageRenderer(size).toImage {
           image.draw(CGRect(CGPoint.Zero(), size))
       }
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


