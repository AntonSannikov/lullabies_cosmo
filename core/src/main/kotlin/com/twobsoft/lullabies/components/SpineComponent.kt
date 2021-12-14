package com.twobsoft.lullabies.components

import com.badlogic.gdx.Files
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector2
import com.esotericsoftware.spine.*
import com.twobsoft.lullabies.MainScreen
import kotlinx.coroutines.MainScope

class SpineComponent(
    val atlas: TextureAtlas,
    val jsonFile: FileHandle,
    val scale: Float,
    var isAlwaysAnimated: Boolean=true
    ) {


    private var position = Vector2()
    private var timeScale = 0.5f
    private val transPositionDelta = 5f

    var tapHandler : (() -> Unit)? = null

    var stageNumber = 0
    var scaling2 = scale
    var hitBox = arrayListOf<Float>()
    var rotation = 0f
    var isTransitionAnimation = false


    val json = SkeletonJson(atlas).also{
        it.scale = scale
    }

    var skeletonData = json.readSkeletonData(jsonFile)
    var skeleton = Skeleton(skeletonData).also {
        it.setPosition(0f, 0f)
    }

    var stateData = AnimationStateData(skeletonData)

    var state = AnimationState(stateData).also {
        it.timeScale = 0.5f
        if (isAlwaysAnimated) it.setAnimation(0, "animation", true)
    }


    fun setPos(_x: Float, _y: Float) {
        position = Vector2(_x, _y)
        skeleton.setPosition(_x, _y)
    }

    fun setTimeScale(_value: Float) {
        timeScale = _value
        state.timeScale = _value
    }

    fun updateScaling(delta: Float) {
        scaling2 += delta
        skeleton.setScale(scaling2, scaling2)
    }

    fun getNewPosition(): Vector2 {
        val result = Vector2()

        if (position.x <= MainScreen.BG_WIDTH / 2) {
            if (position.x + transPositionDelta > MainScreen.BG_WIDTH / 2) result.x = MainScreen.BG_WIDTH / 2
            else result.x = position.x + transPositionDelta
        } else {
            if (position.x - transPositionDelta < MainScreen.BG_WIDTH / 2) result.x = MainScreen.BG_WIDTH / 2
            else result.x = position.x - transPositionDelta
        }

        if (position.y <= MainScreen.BG_HEIGHT / 2) {
            if (position.y + transPositionDelta > MainScreen.BG_HEIGHT / 2) result.y = MainScreen.BG_HEIGHT / 2
            else result.y = position.y + transPositionDelta
        } else {
            if (position.y - transPositionDelta < MainScreen.BG_HEIGHT / 2) result.y = MainScreen.BG_HEIGHT / 2
            else result.y = position.y - transPositionDelta
        }


        return  result
    }
}