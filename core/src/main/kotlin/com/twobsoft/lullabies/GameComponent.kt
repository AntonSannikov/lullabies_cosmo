package com.twobsoft.lullabies


import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions


class GameComponent(val tex: String): Actor() {

    val actions = arrayListOf<Action>()
    val layers = arrayListOf<Texture>()
    var isNeedRemove = false
    var isNeedReinit = false
    var xOffset = 0
    var xBounds = Vector2(-MainScreen.BG_WIDTH, MainScreen.BG_WIDTH)



    fun init() {
        val imgTexture = Assets.getAsset(tex).also {
            it.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        }
        if (width == 0f) {
            width = imgTexture.width.toFloat()
        }
        if (height == 0f) {
            height = imgTexture.height.toFloat()
        }
        y += MainScreen.BG_HEIGHT * 0.03f

        originX = width / 2
        originY = height / 2

        layers.add(imgTexture)
    }

    fun reInit() {
        addAction(Actions.moveBy(-xOffset.toFloat(), 0f))
        xOffset = 0
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {

        if (x < xBounds.x || x >= xBounds.y) {
            return
        }

        batch!!.setColor(color.r, color.g, color.b, color.a * parentAlpha)

        for (layer in layers) {
            batch.draw(layer,
                x, y,
                originX, originY,
                width, height,
                scaleX, scaleY,
                rotation,
                xOffset,0,
                layer.width,
                layer.height,
                false, false
            )
        }

    }

    fun stop() {
        actions.forEach {
            removeAction(it)
        }
    }


}