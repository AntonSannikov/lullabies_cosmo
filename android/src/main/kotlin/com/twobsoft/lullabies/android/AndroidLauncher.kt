package com.twobsoft.lullabies.android

import android.media.MediaPlayer
import android.os.Bundle

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.twobsoft.lullabies.LullabiesGame
import com.twobsoft.lullabies.R


/** Launches the Android application. */
class AndroidLauncher : AndroidApplication() {


    var servicesApi: ServicesApi?=null


    override fun onCreate(savedInstanceState: Bundle?) {

        servicesApi = ServicesApi( this)

        super.onCreate(savedInstanceState)
        initialize(
            LullabiesGame(servicesApi!!), AndroidApplicationConfiguration().apply {
                // Configure your application here.
            }
        )
    }

    override fun onDestroy() {
        BackgroundSoundService.isNeedDestroy = true
        servicesApi?.dispose(this)
        super.onDestroy()
    }


}




