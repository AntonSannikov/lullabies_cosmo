package com.twobsoft.babymozartspacetrip.android.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.widget.Button
import android.widget.Toast
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.SkuDetails
import com.twobsoft.babymozartspacetrip.R



class OptionsDialog(
    val context: Context,
    val activity: Activity,
    val skuDetails: SkuDetails?,
    val billingClient: BillingClient?
    ) {

    fun showOptions() {
        Handler(Looper.getMainLooper()).post {
            val dialog = Dialog(context)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(true)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.options)

            // more apps
            val moreappsButton = dialog.findViewById(R.id.moreapps_button) as Button
            moreappsButton.setOnClickListener {
                try {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://search?q=pub:Two+B+Soft"))
                    )
                } catch (e: ActivityNotFoundException) {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/search?q=pub:Two+B+Soft"))
                    )
                }
            }

            // buy
            val buyButton = dialog.findViewById(R.id.buy_button) as Button
            buyButton.setOnClickListener {
                PayWall(context, activity, skuDetails, billingClient)
                    .showPayWall()
                dialog.dismiss()
            }

            // rate
            val rateButton = dialog.findViewById(R.id.rate_button) as Button
            rateButton.setOnClickListener {
                try {
                    context.startActivity(Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=${context.packageName}")))
                } catch (e: ActivityNotFoundException) {
                    context.startActivity(Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=${context.packageName}")))
                }
            }

            // contact us
            val contactButton = dialog.findViewById(R.id.contact_button) as Button
            contactButton.setOnClickListener {
                val i = Intent(Intent.ACTION_SEND)
                i.type = "message/rfc822"
                i.putExtra(Intent.EXTRA_EMAIL, arrayOf("info@twobsoft.com"))
                try {
                    context.startActivity(Intent.createChooser(i, "Send mail..."))
                } catch (ex: ActivityNotFoundException) {
                    Toast.makeText(
                        context,
                        "There are no email clients installed.",
                        Toast.LENGTH_SHORT).show()
                }
            }

            //privacy
            val privacyButton = dialog.findViewById(R.id.privacy_button) as Button
            privacyButton.setOnClickListener {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://twobsoft.github.io/baby_mozart_space_trip_privacy_policy"))
                )
            }

            dialog.show()

        }
    }

}