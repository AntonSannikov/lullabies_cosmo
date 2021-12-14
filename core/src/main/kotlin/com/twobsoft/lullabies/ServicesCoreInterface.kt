package com.twobsoft.lullabies

interface ServicesCoreInterface {
    fun initPlayCallback(callback: () -> Unit)
    fun initPauseCallback(callback: () -> Unit)
    fun initPreviousCallback(callback: () -> Unit)
    fun initNextCallback(callback: () -> Unit)
    fun share()
    fun playMusic(stageNumber: Int, isSwitching: Boolean)

    fun onPause()
    fun onResume()

    fun initOnPauseCallback(callback: () -> Unit)
    fun initOnResumeCallback(callback: () -> Unit)

}


