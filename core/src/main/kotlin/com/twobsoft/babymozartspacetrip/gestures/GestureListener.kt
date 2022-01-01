package com.twobsoft.lullabies

import kotlin.math.abs
import com.badlogic.gdx.math.Vector2

import com.badlogic.gdx.input.GestureDetector.GestureListener


class MyGestureListener(private val directionListener: DirectionListener)
    : GestureListener {

     interface DirectionListener {
         fun onLeft()
         fun onRight()
         fun onUp()
         fun onDown()
         fun onPan(x: Float, y: Float, deltaX: Float, deltaY: Float)
         fun onTap(x: Float, y: Float, count: Int, button: Int)
    }


    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
        directionListener.onTap(x, y, count, button)
        return false
    }

    override fun longPress(x: Float, y: Float): Boolean {
        return false
    }

    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
        if (abs(velocityX) > abs(velocityY)) {
            if (velocityX > 0) {
                if (velocityX > 1000) {
                    directionListener.onRight()
                }
            } else {
                if (velocityX < -1000) {
                    directionListener.onLeft()
                }
            }
        } else {
            if (velocityY > 0) {
                directionListener.onDown()
            } else {
                directionListener.onUp()
            }
        }
        return false
    }

    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
        directionListener.onPan(x, y, deltaX, deltaY)
        return false
    }

    override fun panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun zoom(originalDistance: Float, currentDistance: Float): Boolean {
        return false
    }

    override fun pinch(
        initialFirstPointer: Vector2,
        initialSecondPointer: Vector2,
        firstPointer: Vector2,
        secondPointer: Vector2
    ): Boolean {
        return false
    }

    override fun pinchStop() {}
}




