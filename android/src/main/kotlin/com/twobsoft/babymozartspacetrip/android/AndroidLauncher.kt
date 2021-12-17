package com.twobsoft.babymozartspacetrip.android

import AdInterface
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.twobsoft.babymozartspacetrip.LullabiesGame
import android.widget.RelativeLayout
import com.google.android.gms.ads.*
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.AdListener
import android.view.ViewGroup
import android.widget.Toast
import com.android.billingclient.api.*


/** Launches the Android application. */
class AndroidLauncher : AndroidApplication(), AdInterface {


    var servicesApi: ServicesApi?=null
    var lifecycleListener: AppLyfecycleListener?=null

    var gameView: View? = null
    var bannerAdView: AdView? = null
    var layout: RelativeLayout? = null
    var params: RelativeLayout.LayoutParams? = null
    var adRequest: AdRequest? = null

    var billingClient: BillingClient? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        servicesApi = ServicesApi( this)
        lifecycleListener = AppLyfecycleListener(servicesApi!!)

        addLifecycleListener(lifecycleListener)

        val config = AndroidApplicationConfiguration()
        gameView = initializeForView(
            LullabiesGame(servicesApi!!, this),
            config
        )

        // ADMOB INIT
        // child treatment
        val requestConfiguration = MobileAds.getRequestConfiguration()
            .toBuilder()
            .setTagForChildDirectedTreatment(RequestConfiguration.TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE)
            .build()
        MobileAds.setRequestConfiguration(requestConfiguration)
        MobileAds.initialize(this)
        { println("ADMOB STATUS $it") }

        bannerAdView = AdView(this)
        bannerAdView!!.visibility   = View.GONE
        bannerAdView!!.adUnitId     = "ca-app-pub-6781199721577549/4945324556"
        bannerAdView!!.adSize       = getAdSize()
        bannerAdView!!.adListener = object : AdListener() {
            override fun onAdLoaded() {
                println("BANNER AD LOADED")
            }
        }

        layout = RelativeLayout(this)
        layout!!.addView(
            gameView, ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        params = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params!!.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        layout!!.addView(bannerAdView, params)
        setContentView(layout)

        adRequest = AdRequest.Builder().build()
        bannerAdView!!.loadAd(adRequest!!)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)


        // =========================================================================================
        //              IAP
        billingClient = BillingClient.newBuilder(this)
            .enablePendingPurchases()
            .setListener(
                object: PurchasesUpdatedListener {
                    override fun onPurchasesUpdated(result: BillingResult, list: MutableList<Purchase>?) {
                        if (result.responseCode == BillingClient.BillingResponseCode.OK && list != null) {
                            val subscription = list[0]
                            if (!subscription.isAcknowledged && subscription.purchaseState == Purchase.PurchaseState.PURCHASED) {
                                println("PURCHASED")
                            }
                        }
                    }
                }
            )
            .build()

    }



    override fun connectToBilling() {
        billingClient!!.startConnection(
            object: BillingClientStateListener {
                override fun onBillingServiceDisconnected() {
                    connectToBilling()
                }
                override fun onBillingSetupFinished(result: BillingResult) {
                    if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                        getProductDetails()
                    } else {
                        runOnUiThread {
                            Toast.makeText(context,
                                result.debugMessage,
                                Toast.LENGTH_LONG).show()
                        }

                    }
                }
            }
        )

    }


    fun getProductDetails() {
        val productsIds = arrayListOf<String>()
        productsIds.add("test_sub")

        val getProductDetailsQuery = SkuDetailsParams
            .newBuilder()
            .setSkusList(productsIds)
            .setType(BillingClient.SkuType.SUBS)
            .build()

        billingClient!!.querySkuDetailsAsync(
            getProductDetailsQuery,
            object: SkuDetailsResponseListener {
                override fun onSkuDetailsResponse(result: BillingResult, list: MutableList<SkuDetails>?) {
                    if (result.responseCode == BillingClient.BillingResponseCode.OK && list != null) {
                        val skuDetails = list[0]
                        runOnUiThread {
                            Toast.makeText(context,
                                "${skuDetails.description}: ${skuDetails.price}",
                                Toast.LENGTH_LONG).show()
                        }
                    }
                }

            }
        )

    }

    override fun onDestroy() {
        BackgroundSoundService.isNeedDestroy = true
        servicesApi?.dispose(this)

        super.onDestroy()
    }


    // AD ==========================================================================================
    override fun banner(isShowing: Boolean) {
        runOnUiThread {
            if (isShowing) {
                bannerAdView!!.visibility = View.VISIBLE
            } else {
                bannerAdView!!.visibility = View.GONE
            }
        }
    }

    private fun getAdSize(): AdSize? {
        val outMetrics = context.resources.displayMetrics
        val widthPixels = outMetrics.widthPixels.toFloat()
        val density = outMetrics.density
        val adWidth = (widthPixels / density).toInt()

        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
    }
}



