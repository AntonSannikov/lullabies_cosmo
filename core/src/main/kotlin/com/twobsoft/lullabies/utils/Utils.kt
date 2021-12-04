package com.twobsoft.lullabies.utils

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array

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


    }

}