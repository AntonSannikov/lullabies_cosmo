package com.twobsoft.babymozartspacetrip

interface ServicesCoreInterface {

    var AVAILABLE_STAGES: Int
    var isPlaying: Boolean
    var isNeedNewPlay: Boolean

    fun init()
    fun initPlayCallback(callback: () -> Unit)
    fun initPauseCallback(callback: () -> Unit)
    fun initPreviousCallback(callback: () -> Unit)
    fun initNextCallback(callback: () -> Unit)
    fun share()
    fun playMusic(stageNumber: Int, isSwitching: Boolean)
    fun createTimer()

    fun onPause()
    fun onResume()

    fun initOnPauseCallback(callback: () -> Unit)
    fun initOnResumeCallback(callback: () -> Unit)

}


