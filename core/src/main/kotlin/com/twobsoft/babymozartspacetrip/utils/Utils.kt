package com.twobsoft.babymozartspacetrip.utils

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.twobsoft.babymozartspacetrip.MainScreen

class Utils {

    companion object {

        fun floatArrayToVec2Array(array: FloatArray): Array<Vector2> {
            val result = Array<Vector2>()
            var vector = Vector2()
            var firstPoint = true
            for (i in array.indices) {
                if (firstPoint) {
                    vector.x = array[i]
                    firstPoint = false
                } else {
                    if ((i + 1).mod(2) == 0) {
                        vector.y = array[i]
                        result.add(vector)
                        vector = Vector2()
                    } else {
                        vector.x = array[i]
                    }
                }
            }

            return result
        }

        fun getScale(
            tXpercent: Float,
            tYpercent: Float,
            width: Float,
            height: Float
        ): Vector2 {
            val targetWidth = MainScreen.BG_WIDTH * tXpercent
            val targetHeight = MainScreen.BG_HEIGHT * tYpercent
            val difWidth = targetWidth - width
            val difHeight = targetHeight - height
            return Vector2(difWidth / width, difHeight / height)
        }

        fun getYoffset(height: Float): Float {


            return 0.5f
        }

    }

}