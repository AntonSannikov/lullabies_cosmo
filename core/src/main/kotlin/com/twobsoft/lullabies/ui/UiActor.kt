package com.twobsoft.lullabies.ui

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.twobsoft.lullabies.Assets
import com.twobsoft.lullabies.MainScreen

class UiActor(val tex: String): Actor() {

    var texture: Texture? = null

    fun init() {

        val imgTexture = Assets.getAsset(tex).also {
            it.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        }
        if (width == 0f) {
            width = MainScreen.BG_WIDTH
        }
        if (height == 0f) {
            height = MainScreen.BG_HEIGHT
        }

        originX = width / 2
        originY = height / 2

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
            scaleX, scaleY,
            rotation,
            0,0,
            width.toInt(),
            height.toInt(),
            false, false
        )

    }

}