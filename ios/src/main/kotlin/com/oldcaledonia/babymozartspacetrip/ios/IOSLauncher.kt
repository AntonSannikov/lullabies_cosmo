@file:JvmName("IOSLauncher")

package com.oldcaledonia.babymozartspacetrip.ios

import DialogInterface
import com.badlogic.gdx.backends.iosrobovm.IOSApplication
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration
import com.twobsoft.babymozartspacetrip.LullabiesGame
import org.robovm.apple.foundation.NSAutoreleasePool
import org.robovm.apple.uikit.UIApplication


/** Launches the iOS (RoboVM) application. */
class IOSLauncher : IOSApplication.Delegate(), DialogInterface {
    var servicesApi: ServicesApi?=null

	override fun createApplication(): IOSApplication {
        servicesApi = ServicesApi()

		return IOSApplication(
                LullabiesGame(servicesApi!!, this),
                IOSApplicationConfiguration().apply {
                }
        ).also { it.addLifecycleListener(servicesApi) }
	}

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val pool = NSAutoreleasePool()
            val principalClass: Class<UIApplication>? = null
            val delegateClass = IOSLauncher::class.java
            UIApplication.main(args, principalClass, delegateClass)
            pool.close()
        }
    }



    override fun showOptions() {
        println("")
    }
}




