package com.twobsoft.babymozartspacetrip.ios

import com.twobsoft.babymozartspacetrip.ServicesCoreInterface


class ServicesApi : ServicesCoreInterface, Playable {

    override var AVAILABLE_STAGES: Int = 15
    override var isPlaying: Boolean = false
    override var isNeedNewPlay: Boolean = true



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

    override fun playMusic(stageNumber: Int, isSwitching: Boolean) {
        println("")
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

