package com.twobsoft.lullabies.android

import android.media.MediaPlayer
import android.os.Bundle

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.twobsoft.lullabies.LullabiesGame
import com.twobsoft.lullabies.R


/** Launches the Android application. */
class AndroidLauncher : AndroidApplication() {


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        initialize(
            LullabiesGame(ServicesApi( this)), AndroidApplicationConfiguration().apply {
                // Configure your application here.
            }
        )

    }


}




