package com.twobsoft.babymozartspacetrip.android.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.widget.Button
import android.widget.Toast
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.SkuDetails
import com.twobsoft.babymozartspacetrip.R

class PayWall(
    val context: Context,
    val activity: Activity,
    val skuDetails: SkuDetails?,
    val billingClient: BillingClient?) {


    fun showPayWall() {
        Handler(Looper.getMainLooper()).post {
            val dialog = Dialog(context)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(true)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.inapp_paywall)
            val payButton = dialog.findViewById(R.id.buy_button) as Button
            payButton.setOnClickListener {
                dialog.dismiss()
                if (skuDetails == null) {
                    Toast.makeText(context,
                        "Unable to get information from Google Services",
                        Toast.LENGTH_LONG).show()
                } else {
                    val flowParams = BillingFlowParams.newBuilder()
                        .setSkuDetails(skuDetails)
                        .build()
                    billingClient!!.launchBillingFlow(activity, flowParams)
                }
            }

            dialog.show()
        }
    }
}