package com.twobsoft.babymozartspacetrip.android


import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import android.view.Window
import android.widget.Button
import android.widget.Toast
import com.android.billingclient.api.*
import com.twobsoft.babymozartspacetrip.R
import AdInterface
import android.content.ActivityNotFoundException
import android.net.Uri
import com.twobsoft.babymozartspacetrip.android.dialogs.ChildWall
import com.twobsoft.babymozartspacetrip.android.dialogs.OptionsDialog
import com.twobsoft.babymozartspacetrip.android.dialogs.PayWall


const val GOOGLE_ERROR = "Error on connecting to Google Billing Services: "
const val CONNECTIONS_MAX_COUNT = 5


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
    var isLocalBillingCacheEnabled = false
    val SUBS_SKU = "test_sub"
    var connectionsCount = 0
    var skuDetails: SkuDetails?=null

    var subsName    = ""
    var subsPrice   = ""
    var game: LullabiesGame?=null


    var sharedPrefs: SharedPreferences? = null
    val APP_PREFERENCES                 = "spacetripconf"
    val APP_PREFRENCES_CACHE_STATUS     = "Cachestatus"




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPrefs = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        servicesApi = ServicesApi( this)
        lifecycleListener = AppLyfecycleListener(servicesApi!!)

        addLifecycleListener(lifecycleListener)

        val config = AndroidApplicationConfiguration()
        game = LullabiesGame(servicesApi!!, this)

        gameView = initializeForView(
            game,
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
//        { println("ADMOB STATUS $it") }

        bannerAdView = AdView(this)
        bannerAdView!!.visibility   = View.GONE
        bannerAdView!!.adUnitId     = "ca-app-pub-6781199721577549/4945324556"
        bannerAdView!!.adSize       = getAdSize()
        bannerAdView!!.adListener = object : AdListener() {
            override fun onAdLoaded() {
//                println("BANNER AD LOADED")
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
                    override fun onPurchasesUpdated(result: BillingResult, purchases: MutableList<Purchase>?) {
                        if (result.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                            val subscription = purchases[0]
                            if (!subscription.isAcknowledged && subscription.purchaseState == Purchase.PurchaseState.PURCHASED) {

                                val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                                    .setPurchaseToken(subscription.purchaseToken)
                                    .build()

                                billingClient!!.acknowledgePurchase(
                                    acknowledgePurchaseParams,
                                    object: AcknowledgePurchaseResponseListener {
                                        override fun onAcknowledgePurchaseResponse(ackResult: BillingResult) {
                                            if (ackResult.responseCode == BillingClient.BillingResponseCode.OK) {
                                                servicesApi!!.AVAILABLE_STAGES = 15
                                                banner(false)
                                                game!!.mainScreen!!.inputListener.unlockContent()
                                            } else {
                                                Toast.makeText(context,
                                                    "$GOOGLE_ERROR ${ackResult.debugMessage}",
                                                    Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    }
                                )
                            }
                        } else if (
                            result.responseCode != BillingClient.BillingResponseCode.OK &&
                            result.responseCode != BillingClient.BillingResponseCode.USER_CANCELED
                        ) {
                            runOnUiThread {
                                Toast.makeText(context,
                                    "$GOOGLE_ERROR ${result.debugMessage}",
                                    Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            )
            .build()

        checkPurchases()
    }



    // INAPP =======================================================================================
    fun getPurchasesHistory() {

        billingClient!!.queryPurchaseHistoryAsync(
            BillingClient.SkuType.SUBS,
            object: PurchaseHistoryResponseListener {
                override fun onPurchaseHistoryResponse(
                    result: BillingResult,
                    list: MutableList<PurchaseHistoryRecord>?
                ) {
                    if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                        isLocalBillingCacheEnabled = true
                        val editor = sharedPrefs!!.edit();
                        editor.putString(APP_PREFRENCES_CACHE_STATUS, "enabled");
                        editor.apply()
                        getCachedPurchases()
                    } else {
                        if (connectionsCount < CONNECTIONS_MAX_COUNT) {
                            connectionsCount++
                            checkPurchasesStatus()
                        } else {
                            connectionsCount = 0
                            runOnUiThread {
                                Toast.makeText(context,
                                    "$GOOGLE_ERROR ${result.debugMessage}",
                                    Toast.LENGTH_LONG).show()
                            }
                        }

                    }
                }
            }
        )
    }


    override fun checkPurchasesStatus() {
        isLocalBillingCacheEnabled = sharedPrefs!!.contains(APP_PREFRENCES_CACHE_STATUS)
        if (isLocalBillingCacheEnabled) {
            getCachedPurchases()
        } else {
            getPurchasesHistory()
        }
    }


    fun getCachedPurchases() {
        billingClient!!.queryPurchasesAsync(
            BillingClient.SkuType.SUBS,
            object: PurchasesResponseListener {
                override fun onQueryPurchasesResponse(
                    result: BillingResult,
                    list: MutableList<Purchase>
                ) {
                    if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                       if (list.isNotEmpty() && list[0].purchaseState == Purchase.PurchaseState.PURCHASED) {

                           if (!list[0].isAcknowledged) {
                               val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                                   .setPurchaseToken(list[0].purchaseToken)
                                   .build()
                               billingClient!!.acknowledgePurchase(
                                   acknowledgePurchaseParams,
                                   object: AcknowledgePurchaseResponseListener {
                                       override fun onAcknowledgePurchaseResponse(ackResult: BillingResult) {
                                           servicesApi!!.AVAILABLE_STAGES = 15
                                       }
                                   }
                               )
                           } else {
                               servicesApi!!.AVAILABLE_STAGES = 15
                           }

                       }
                    } else {
                        runOnUiThread {
                            Toast.makeText(context,
                                "$GOOGLE_ERROR ${result.debugMessage}",
                                Toast.LENGTH_LONG).show()
                        }
                    }
                }

            }
        )
    }


    fun checkPurchases() {

        billingClient!!.startConnection(
            object: BillingClientStateListener {
                override fun onBillingServiceDisconnected() {
                    checkPurchases()
                }
                override fun onBillingSetupFinished(result: BillingResult) {
                    if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                        getProductDetails()
                        checkPurchasesStatus()
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
        productsIds.add(SUBS_SKU)

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
                        if (list.isEmpty()) return
                        skuDetails = list[0]
                        subsName  = skuDetails!!.description
                        subsPrice = skuDetails!!.price
                    }
                }

            }
        )

    }


    override fun startPurchaseFlow(): Boolean {
        ChildWall(this, this, skuDetails, billingClient)
            .showChildWall()
        return false
    }


    override fun showOptions() {
        OptionsDialog(this, this, skuDetails, billingClient)
            .showOptions()
    }


    // AD ==========================================================================================
    //
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
    //
    // AD ==========================================================================================


    override fun onDestroy() {
        billingClient!!.endConnection()
        servicesApi?.dispose(this)
        super.onDestroy()
    }
}



