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
import android.widget.TextView
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.SkuDetails
import com.twobsoft.babymozartspacetrip.R
import kotlin.random.Random

class ChildWall(val context: Context,
                val activity: Activity,
                val skuDetails: SkuDetails?,
                val billingClient: BillingClient?) {


    private var result          = 0
    private var element1        = 0
    private var element2        = 0
    private var element3        = 0
    private var firstOperation  = ""
    private var secondOperation = ""
    private var exampleText     = ""

    private var dialog: Dialog?=null

    private val variants = arrayListOf<Int>()


    fun showChildWall() {

        Handler(Looper.getMainLooper()).post {
            dialog = Dialog(context)
            dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.setCanceledOnTouchOutside(true)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.setCancelable(true)
            dialog!!.setContentView(R.layout.child_wall)

            generateExample()
            generateVariants()

            val exampleTextView = dialog!!.findViewById(R.id.exampleTextView) as TextView
            exampleTextView.text = "Solve an example: $exampleText"

            val button1 = dialog!!.findViewById(R.id.first) as Button
            button1.text = variants[0].toString()
            button1.setOnClickListener { variantButtonListener(variants[0]) }

            val button2 = dialog!!.findViewById(R.id.second) as Button
            button2.text = variants[1].toString()
            button2.setOnClickListener { variantButtonListener(variants[1]) }

            val button3 = dialog!!.findViewById(R.id.third) as Button
            button3.text = variants[2].toString()
            button3.setOnClickListener { variantButtonListener(variants[2]) }

            val button4 = dialog!!.findViewById(R.id.fourth) as Button
            button4.text = variants[3].toString()
            button4.setOnClickListener { variantButtonListener(variants[3]) }

            dialog!!.show()

        }

    }


    private fun variantButtonListener(value: Int) {
        if (value == result) {
            dialog!!.dismiss()
            PayWall(context, activity, skuDetails, billingClient)
                .showPayWall()
        } else {
            dialog!!.dismiss()
        }
    }


    private fun generateExample() {
        element3 = (2..10).random()
        secondOperation = generateSecondMathOperation()

        var result23 = 0

        if (secondOperation == "/") {
            val divider = (2..4).random()
            element2 = element3*divider
            result23 = divider
        } else {
            element2 = (2..4).random()
            result23 = element3*element2
        }

        element1 = (1..30).random()

        firstOperation = generateFirstMathOperation()

        if (firstOperation == "+") {
            result = element1 + result23
        } else {
            result = element1 - result23
        }

        exampleText = "$element1 $firstOperation $element2$secondOperation$element3"

    }


    private fun generateFirstMathOperation(): String {
        return if (Random.nextBoolean()) "+" else "-"
    }


    private fun generateSecondMathOperation(): String {
        return if (Random.nextBoolean()) "/" else "*"
    }


    private fun generateK(): Int {
        return if (Random.nextBoolean()) 1 else -1
    }


    private fun generateVariants() {
        val resultPosition = (0..3).random()

        var signCount = 0

        for (x in 0..3) {
            if (x == resultPosition) {
                variants.add(result)
                continue
            }
            var currentVariant = 0
            currentVariant = result + generateK()*(3..12).random()
            while (variants.contains(currentVariant)) {
                currentVariant = result + generateK()*(3..12).random()
            }
            if (currentVariant < 0) {
                signCount++
            }

            variants.add(currentVariant)
        }

        if (signCount == 0 || signCount == 3) {
            var index = (0..3).random()
            while (index == resultPosition) {
                index = (0..3).random()
            }
            variants[index] = -variants[index]
        }
    }
}