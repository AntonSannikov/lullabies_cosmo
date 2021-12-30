@file:JvmName("IOSLauncher")

package com.oldcaledonia.babymozartspacetrip.ios

import DialogInterface
import com.badlogic.gdx.backends.iosrobovm.IOSApplication
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration
import com.twobsoft.babymozartspacetrip.LullabiesGame
import org.robovm.apple.coreanimation.CAGradientLayer
import org.robovm.apple.coregraphics.CGSize
import org.robovm.apple.foundation.NSAutoreleasePool
import org.robovm.apple.foundation.NSBundle
import org.robovm.apple.foundation.NSError
import org.robovm.apple.foundation.NSURL
import org.robovm.apple.messageui.MFMailComposeResult
import org.robovm.apple.messageui.MFMailComposeViewController
import org.robovm.apple.messageui.MFMailComposeViewControllerDelegate
import org.robovm.apple.uikit.*
import org.robovm.objc.block.VoidBlock1
import org.robovm.objc.block.VoidBooleanBlock



/** Launches the iOS (RoboVM) application. */
class IOSLauncher : IOSApplication.Delegate(), DialogInterface, MFMailComposeViewControllerDelegate {
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
        val view = NSBundle.getMainBundle().loadNib(
                "options", null, null)[0] as UIView

        val image = view.getViewWithTag(100)
        image.backgroundColor = UIColor.fromPatternImage(
                servicesApi!!.resize(
                        UIImage.getImage("dialog_frame.png"),
                        CGSize(image.bounds.width, image.bounds.height)
                )
        )

        // MORE APPS -----------------
        val moreappsButton = view.getViewWithTag(11) as UIButton
        var icon = UIImage.getImage("dashboard.png")
        moreappsButton.setImage(icon, UIControlState.Normal)
        moreappsButton.imageView.contentMode = UIViewContentMode.ScaleAspectFit
        moreappsButton.setTitle(
                "    ${moreappsButton.getTitle(UIControlState.Normal)}",
                UIControlState.Normal
        )
        applyGradient(moreappsButton)
        moreappsButton.addAction(
                UIAction(
                        VoidBlock1 {
//                            UIApplication.getSharedApplication().openURL(
//                                    NSURL("itms-apps://itunes.apple.com/developer/"),
//                                    UIApplicationOpenURLOptions(),
//                                    VoidBooleanBlock {  }
//                            )
                        }
                ),
                UIControlEvents.TouchUpInside
        )

        // RATE APP --------------------
        val starButton = view.getViewWithTag(22) as UIButton
        icon = UIImage.getImage("star.png")
        starButton.setImage(icon, UIControlState.Normal)
        starButton.imageView.contentMode = UIViewContentMode.ScaleAspectFit
        starButton.setTitle(
                "    ${starButton.getTitle(UIControlState.Normal)}",
                UIControlState.Normal
        )
        applyGradient(starButton)
        starButton.addAction(
                UIAction(
                        VoidBlock1 {

                        }
                ),
                UIControlEvents.TouchUpInside
        )

        // CONTACT US -------------------
        val contactButton = view.getViewWithTag(33) as UIButton
        icon = UIImage.getImage("email.png")
        contactButton.setImage(icon, UIControlState.Normal)
        contactButton.imageView.contentMode = UIViewContentMode.ScaleAspectFit
        contactButton.setTitle(
                "    ${contactButton.getTitle(UIControlState.Normal)}",
                UIControlState.Normal
        )
        applyGradient(contactButton)
        contactButton.addAction(
                UIAction(
                        VoidBlock1 {
                            if (MFMailComposeViewController.canSendMail()) {
                                val mailVC = MFMailComposeViewController()
                                mailVC.mailComposeDelegate = this
                                mailVC.setToRecipients(listOf("info@twobsoft.com"))
                                mailVC.setSubject("")
                                mailVC.setMessageBody("", false)
                                UIApplication.getSharedApplication().windows[0].rootViewController.presentViewController(
                                        mailVC, true, null
                                )
                            } else {
                                val emailUrl = createEmailUrl("info@twobsoft.com")
                                UIApplication.getSharedApplication().openURL(
                                        emailUrl,
                                        UIApplicationOpenURLOptions(),
                                        VoidBooleanBlock {  }
                                )
                            }
                        }
                ),
                UIControlEvents.TouchUpInside
        )

        // PRIVACY AND TERMS ----------------
        val policyButton = view.getViewWithTag(44) as UIButton
        icon = UIImage.getImage("policy.png")
        policyButton.setImage(icon, UIControlState.Normal)
        policyButton.imageView.contentMode = UIViewContentMode.ScaleAspectFit
        policyButton.setTitle(
                "    ${policyButton.getTitle(UIControlState.Normal)}",
                UIControlState.Normal
        )
        applyGradient(policyButton)
        policyButton.addAction(
                UIAction(
                        VoidBlock1 {
                            UIApplication.getSharedApplication().openURL(
                                    NSURL("https://twobsoft.github.io/baby_mozart_space_trip_privacy_policy"),
                                    UIApplicationOpenURLOptions(),
                                    VoidBooleanBlock {  }
                            )
                        }
                ),
                UIControlEvents.TouchUpInside
        )


        UIApplication.getSharedApplication().windows[0].addSubview(view)
        view.center = UIApplication.getSharedApplication().windows[0].convertPointFromView(
                UIApplication.getSharedApplication().windows[0].center,
                UIApplication.getSharedApplication().windows[0].superview
        )

        val outsideTapRecognizer = UITapGestureRecognizer(
                UIGestureRecognizer.OnGestureListener {
                    view.removeFromSuperview()
                }
        )

        val insideTapGestureRecognizer = UITapGestureRecognizer(
            UIGestureRecognizer.OnGestureListener {
            }
        )

        image.isUserInteractionEnabled = true
        image.addGestureRecognizer(insideTapGestureRecognizer)

        UIApplication.getSharedApplication().windows[0].addGestureRecognizer(outsideTapRecognizer)

    }

    override fun didFinish(controller: MFMailComposeViewController?, result: MFMailComposeResult?, error: NSError?) {
        controller?.dismissViewController(true, null)
    }

    private fun createEmailUrl(to: String): NSURL {

        val gmailUrl = NSURL("googlegmail://co?to=$to")
        val outlookUrl = NSURL("ms-outlook://compose?to=$to")
        val yahooMail = NSURL("ymail://mail/compose?to=$to")
        val sparkUrl = NSURL("readdle-spark://compose?recipient=$to")
        val defaultUrl = NSURL("mailto:$to")

        if (UIApplication.getSharedApplication().canOpenURL(gmailUrl))
            return  gmailUrl
        else if (UIApplication.getSharedApplication().canOpenURL(outlookUrl))
            return  outlookUrl
        else if (UIApplication.getSharedApplication().canOpenURL(yahooMail))
            return  yahooMail
        else if (UIApplication.getSharedApplication().canOpenURL(sparkUrl))
            return  sparkUrl

        return defaultUrl
    }


    private fun applyGradient(button: UIButton) {
        val gradientLayer = CAGradientLayer()
        gradientLayer.frame = button.bounds
        gradientLayer.cornerRadius = 10.0
        gradientLayer.colors = listOf(
                UIColor.fromRGBA(137.0/255.0,203.0/255.0,238.0/255.0, 1.0).cgColor,
                UIColor.fromRGBA(0.0,79.0/255.0,129.0/255.0, 1.0).cgColor
        )
        button.layer.addSublayer(gradientLayer)
    }

}




