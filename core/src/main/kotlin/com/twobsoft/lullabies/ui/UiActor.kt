package com.twobsoft.lullabies.ui

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.twobsoft.lullabies.Assets
import com.twobsoft.lullabies.MainScreen

class UiActor(val tex: String): Actor() {

    var texture: Texture? = null

    val offsetX = (MainScreen.BG_WIDTH * 0.09f).toInt()
    val offsetY = 0

    val yScale = MainScreen.ratio * 2.2f
    val xScale = MainScreen.ratio * 2f


    fun init() {

        val imgTexture = Assets.getAsset(tex).also {
            it.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        }

        if (width == 0f) {
            width = MainScreen.BG_HEIGHT * 0.64f
        }
        if (height == 0f) {
            height = MainScreen.BG_HEIGHT
        }

        originX = MainScreen.BG_WIDTH / 2
        originY = MainScreen.BG_HEIGHT / 2

        texture = imgTexture

    }


    override fun draw(batch: Batch?, parentAlpha: Float) {

        if (texture == null) {
            return
        }

        batch!!.setColor(color.r, color.g, color.b, color.a * parentAlpha)

        batch.draw(texture,
            x, y,
            originX, originY,
            width, height,
            xScale, yScale,
            rotation,
            offsetX, offsetY,
            MainScreen.BG_WIDTH.toInt(),
            MainScreen.BG_HEIGHT.toInt(),
            false, false
        )

    }

}