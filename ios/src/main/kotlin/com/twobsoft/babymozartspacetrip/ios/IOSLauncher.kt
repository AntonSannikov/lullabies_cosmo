@file:JvmName("IOSLauncher")

package com.twobsoft.babymozartspacetrip.ios

import AdInterface
import com.badlogic.gdx.backends.iosrobovm.IOSApplication
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration
import com.twobsoft.babymozartspacetrip.LullabiesGame
import org.robovm.apple.foundation.NSAutoreleasePool
import org.robovm.apple.uikit.UIApplication


/** Launches the iOS (RoboVM) application. */
class IOSLauncher : IOSApplication.Delegate(), AdInterface {
    var servicesApi: ServicesApi?=null

	override fun createApplication(): IOSApplication {

        servicesApi = ServicesApi()
//        IOSMusic().play()

		return IOSApplication(
                LullabiesGame(servicesApi!!, this),
                IOSApplicationConfiguration().apply {
            // Configure your application here.
        })
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


    override fun banner(isShowing: Boolean) {
        TODO("Not yet implemented")
    }

    override fun checkPurchasesStatus() {
        TODO("Not yet implemented")
    }

    override fun startPurchaseFlow(): Boolean {
        TODO("Not yet implemented")
    }

    override fun showOptions() {
        TODO("Not yet implemented")
    }
}




