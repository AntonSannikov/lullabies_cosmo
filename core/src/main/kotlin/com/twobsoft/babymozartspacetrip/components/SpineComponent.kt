package com.twobsoft.babymozartspacetrip.components

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector2
import com.esotericsoftware.spine.*
import com.twobsoft.babymozartspacetrip.MainScreen

class SpineComponent(
    var atlas: TextureAtlas,
    var jsonFile: FileHandle,
    val scale: Float,
    var isAlwaysAnimated: Boolean=true
    ) {


    private var position = Vector2()
    private var timeScale = 0.5f
    private val transPositionDelta = 5f

    var tapHandler : (() -> Unit)? = null

    var stageNumber = 0
    var scaling2 = 1f
    var hitBox = arrayListOf<Float>()
    var rotation = 0f
    var isTransitionAnimation = false
    val defaultColors = arrayListOf<Color>()


    var json = SkeletonJson(atlas).also{
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


    init {
        for (slot in skeletonData.slots) {
            defaultColors.add(slot.color)
        }
    }

    fun setUnavailableColor() {
        for (slot in skeletonData.slots) {
            slot.color.set(Color.LIGHT_GRAY)
        }
        skeleton = Skeleton(skeletonData).also {
            it.setPosition(0f, 0f)
        }
        stateData = AnimationStateData(skeletonData)
        state = AnimationState(stateData).also {
            it.timeScale = 0.5f
            if (isAlwaysAnimated) it.setAnimation(0, "animation", true)
        }
        setPos(position.x, position.y)
    }


    fun restoreColor() {
        for (i in 0 until skeletonData.slots.size) {
            skeletonData.slots[i].color.set(defaultColors[i])
        }
        skeleton = Skeleton(skeletonData).also {
            it.setPosition(0f, 0f)
        }
        stateData = AnimationStateData(skeletonData)
        state = AnimationState(stateData).also {
            it.timeScale = 0.5f
            if (isAlwaysAnimated) it.setAnimation(0, "animation", true)
        }
        setPos(position.x, position.y)
    }


    fun changeAtlas(_atlas: TextureAtlas, _jsonFile: FileHandle) {
        atlas = _atlas
        jsonFile = _jsonFile

        json = SkeletonJson(atlas).also{
            it.scale = scale
        }

        skeletonData = json.readSkeletonData(jsonFile)
        skeleton = Skeleton(skeletonData).also {
            it.setPosition(position.x, position.y)
        }

        stateData = AnimationStateData(skeletonData)
        state = AnimationState(stateData).also {
            it.timeScale = 0.5f
            if (isAlwaysAnimated) it.setAnimation(0, "animation", true)
        }
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