package com.twobsoft.babymozartspacetrip.ios

import com.twobsoft.babymozartspacetrip.ServicesCoreInterface


class ServicesApi : ServicesCoreInterface, Playable {

    override var AVAILABLE_STAGES: Int = 10
    override var isPlaying: Boolean = false
    override var isNeedNewPlay: Boolean = true



    override fun initPlayCallback(callback: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun initPauseCallback(callback: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun initPreviousCallback(callback: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun initNextCallback(callback: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun share() {
        TODO("Not yet implemented")
    }

    override fun playMusic(stageNumber: Int, isSwitching: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setLooping(value: Boolean) {
        TODO("Not yet implemented")
    }

    override fun createTimer() {
        TODO("Not yet implemented")
    }

    override fun onPause() {
        TODO("Not yet implemented")
    }

    override fun onResume() {
        TODO("Not yet implemented")
    }

    override fun initOnPauseCallback(callback: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun initOnResumeCallback(callback: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun onTrackPrevious() {
        TODO("Not yet implemented")
    }

    override fun onTrackPlay() {
        TODO("Not yet implemented")
    }

    override fun onTrackPause() {
        TODO("Not yet implemented")
    }

    override fun onTrackNext() {
        TODO("Not yet implemented")
    }


}

