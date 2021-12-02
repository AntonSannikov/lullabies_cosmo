package com.twobsoft.lullabies.android

import android.os.Bundle

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.twobsoft.lullabies.LullabiesGame

/** Launches the Android application. */
class AndroidLauncher : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize(LullabiesGame(), AndroidApplicationConfiguration().apply {
            // Configure your application here.
        })
    }
}
