package com.twobsoft.babymozartspacetrip.android


import android.os.Bundle
import android.view.WindowManager

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.twobsoft.babymozartspacetrip.LullabiesGame




/** Launches the Android application. */
class AndroidLauncher : AndroidApplication() {


    var servicesApi: ServicesApi?=null
    var lifecycleListener: AppLyfecycleListener?=null


    override fun onCreate(savedInstanceState: Bundle?) {

        servicesApi = ServicesApi( this)
        lifecycleListener = AppLyfecycleListener(servicesApi!!)
        super.onCreate(savedInstanceState)
        addLifecycleListener(lifecycleListener)

        initialize(
            LullabiesGame(servicesApi!!), AndroidApplicationConfiguration().apply {
                // Configure your application here.
            }
        )
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onDestroy() {
        BackgroundSoundService.isNeedDestroy = true
        servicesApi?.dispose(this)

        super.onDestroy()
    }


}




