package com.oldcaledonia.babymozartspacetrip.ios


import com.badlogic.gdx.LifecycleListener
import com.badlogic.gdx.backends.iosrobovm.objectal.OALAudioSession
import com.twobsoft.babymozartspacetrip.ServicesCoreInterface
import org.robovm.apple.avfoundation.*
import org.robovm.apple.coregraphics.CGPoint
import org.robovm.apple.coregraphics.CGRect
import org.robovm.apple.coregraphics.CGSize
import org.robovm.apple.dispatch.DispatchQueue
import org.robovm.apple.dispatch.DispatchQueueAttr
import org.robovm.apple.mediaplayer.*
import org.robovm.objc.block.Block1
import org.robovm.apple.foundation.*
import org.robovm.apple.uikit.*
import org.robovm.objc.Selector
import org.robovm.objc.block.VoidBlock1
import org.robovm.rt.bro.annotation.Callback
import java.lang.Exception


class ServicesApi : ServicesCoreInterface, LifecycleListener, AVAudioPlayerDelegate {

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
    var currentTime = 0.0

    val audioSession = AVAudioSession.getSharedInstance()

    var corePlayCallback: () -> Unit = {}
    var corePauseCallback: () -> Unit = {}
    var corePreviousCallback: () -> Unit = {}
    var coreNextCallback: () -> Unit = {}
    var coreOnAppPauseCallback: () -> Unit = {}
    var coreOnAppResumeCallback: () -> Unit = {}

    val playlist = arrayOf(
            Track("Twinkle Twinkle Little Star", "", 1,
                    NSBundle.getMainBundle().findResourceURL("file1", "mp3")),
            Track("Madamina, il catalogo è questo", "Don Giovanni",2,
                    NSBundle.getMainBundle().findResourceURL("file2", "mp3")),
            Track("Symphony No.11 in D major", "",3,
                    NSBundle.getMainBundle().findResourceURL("file3", "mp3")),
            Track("Andiam, Andiam", "Don Giovanni", 4,
                    NSBundle.getMainBundle().findResourceURL("file4", "mp3")),
            Track("Le nozze di Figaro", "Arietta",  5,
                    NSBundle.getMainBundle().findResourceURL("file5", "mp3")),
            Track("Violin Sonata No. 24", "", 6,
                    NSBundle.getMainBundle().findResourceURL("file6", "mp3")),
            Track("Piano Sonata", "Alla turca", 7,
                    NSBundle.getMainBundle().findResourceURL("file7", "mp3")),
            Track("Ho capito, Signor si", "Don Giovanni", 8,
                    NSBundle.getMainBundle().findResourceURL("file8", "mp3")),
            Track("Moonlight sonata (special for A.)", "Beethoven", 9,
                    NSBundle.getMainBundle().findResourceURL("file9", "mp3")),
            Track("Trois beaux enfants fins", "The magic flute", 10,
                    NSBundle.getMainBundle().findResourceURL("file10", "mp3")),
            Track("Twinkle Little Star (Christmas Version)", "", 11,
                    NSBundle.getMainBundle().findResourceURL("file11", "mp3")),
            Track("Variations in F major", "", 12,
                    NSBundle.getMainBundle().findResourceURL("file12", "mp3")),
            Track("Eine Kleine Nachtmusik", "", 13,
                    NSBundle.getMainBundle().findResourceURL("file13", "mp3")),
            Track("Kirie Eleison", "", 14,
                    NSBundle.getMainBundle().findResourceURL("file14", "mp3")),
            Track("Sinfonia Concertante", "", 15,
                    NSBundle.getMainBundle().findResourceURL("file15", "mp3"))
    )


    override fun initialize() {
        val url = NSBundle.getMainBundle().findResourceURL(
                "notification",
                "png"
        )
        val data = NSData.read(url)
        image = UIImage(data)
        audioSession.setCategory(
                AVAudioSessionCategory.Playback,
                AVAudioSessionMode.Default.toString(),
                AVAudioSessionRouteSharingPolicy.LongFormAudio,
                AVAudioSessionCategoryOptions.with(
                        AVAudioSessionCategoryOptions.None
                )
        )
        audioSession.setActive(true, AVAudioSessionSetActiveOptions.with(
                AVAudioSessionSetActiveOptions.NotifyOthersOnDeactivation
        ))
        initRemoteCenter()
    }

    fun initRemoteCenter() {
        val commandCenter = MPRemoteCommandCenter.getSharedCommandCenter()

        addPlayCommand()

        commandCenter.nextTrackCommand.isEnabled        = true
        commandCenter.previousTrackCommand.isEnabled    = true

        commandCenter.previousTrackCommand.addTarget(
                Block1 {
                    currentTrack--
                    corePreviousCallback()
                    playMusic(currentTrack+1, true)
                    return@Block1 MPRemoteCommandHandlerStatus.Success
                }
        )

        commandCenter.nextTrackCommand.addTarget(
                Block1 {
                    currentTrack++
                    coreNextCallback()
                    playMusic(currentTrack+1, true)
                    return@Block1 MPRemoteCommandHandlerStatus.Success
                }
        )
    }


    fun addPauseCommand() {
        val commandCenter = MPRemoteCommandCenter.getSharedCommandCenter()
        commandCenter.playCommand.isEnabled = false
        commandCenter.playCommand.removeTarget(null)
        commandCenter.pauseCommand.isEnabled = true
        commandCenter.pauseCommand.addTarget(
                Block1 {
                    isPlaying = false
                    isNeedNewPlay = false
                    player?.pause()
                    corePauseCallback()
                    setupLockScreen()
                    return@Block1 MPRemoteCommandHandlerStatus.Success
                }
        )
    }


    fun addPlayCommand() {
        val commandCenter = MPRemoteCommandCenter.getSharedCommandCenter()
        commandCenter.pauseCommand.isEnabled = false
        commandCenter.pauseCommand.removeTarget(null)
        commandCenter.playCommand.addTarget(
                Block1 {
                    isPlaying = true
                    isNeedNewPlay = false
                    player?.play()
                    corePlayCallback()
                    setupLockScreen()
                    return@Block1 MPRemoteCommandHandlerStatus.Success
                }
        )
        commandCenter.playCommand.isEnabled = true
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

    override fun initOnPauseCallback(callback: () -> Unit) {
        coreOnAppPauseCallback = callback
    }

    override fun initOnResumeCallback(callback: () -> Unit) {
        coreOnAppResumeCallback = callback
    }

    // =============================================================================================
    //                  SHARE
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
                                    selectedTime,
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
                        corePauseCallback()
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


    override fun playMusic(stageNumber: Int, isSwitching: Boolean) {
        currentTrack = stageNumber-1
        if (currentTrack == -1) {
            currentTrack = AVAILABLE_STAGES - 1
        } else if (currentTrack == 15) {
            currentTrack = 0
        }

        if (isNeedNewPlay || isSwitching || isPaused) {
            if (player == null) {
                isPlaying = true
            }
            player?.pause()
            player          = null
            isPaused        = false
            isNeedNewPlay   = false

            val url = playlist[currentTrack].url
            audioSession.setActive(true, AVAudioSessionSetActiveOptions.with(
                    AVAudioSessionSetActiveOptions.NotifyOthersOnDeactivation
            ))
            player = AVAudioPlayer(url)
            player!!.delegate = this
            player!!.prepareToPlay()
            if (isPlaying) {
                player!!.play()
            }

        } else {
            if (isPlaying) {
                player!!.pause()
                isPlaying = false
            } else {
                player!!.play()
                isPlaying = true
            }
        }

        setupLockScreen()
    }


    fun setupLockScreen() {
        if (isPlaying) {
            addPauseCommand()
        } else {
            addPlayCommand()
        }
        if (player != null) { setupNowPlaying() }
    }


    private fun setupNowPlaying() {

        val artwork = MPMediaItemArtwork(
                image!!.size,
                Block1 {
                    return@Block1 image
                }
        )
        val nowPlayingInfo = MPNowPlayingInfo().also {
            it.artwork              = artwork
            it.artist               = playlist[currentTrack].artist
            it.title                = playlist[currentTrack].title
            it.playbackRate         = if (isPlaying) 1.0 else 0.0
            it.playbackDuration     = player!!.duration.toDouble()
            it.elapsedPlaybackTime  = player!!.currentTime.toDouble()
        }
        MPNowPlayingInfoCenter.getDefaultCenter().nowPlayingInfo = nowPlayingInfo
    }


    fun resize(image: UIImage, size: CGSize): UIImage {
       return UIGraphicsImageRenderer(size).toImage {
           image.draw(CGRect(CGPoint.Zero(), size))
       }
    }


    override fun pause() {
        coreOnAppPauseCallback()
    }


    override fun resume() {
        coreOnAppResumeCallback()
    }


    override fun dispose() {
        player!!.stop()
        player = null
        audioSession.setActive(false)
    }

    override fun didFinishPlaying(player: AVAudioPlayer?, flag: Boolean) {
        currentTrack++
        coreNextCallback()
        isNeedNewPlay = true
        playMusic(currentTrack+1, true)
    }

    override fun decodeErrorDidOccur(player: AVAudioPlayer?, error: NSError?) {
    }

    override fun beginInterruption(player: AVAudioPlayer?) {
    }

    override fun endInterruption(player: AVAudioPlayer?, flags: Long) {
    }

}

