package com.twobsoft.lullabies.android

import com.badlogic.gdx.LifecycleListener



class AppLyfecycleListener(val servicesApi: ServicesApi): LifecycleListener {

    override fun pause() {
        servicesApi.onPause()
    }

    override fun resume() {
        servicesApi.onResume()
    }

    override fun dispose() {}
}