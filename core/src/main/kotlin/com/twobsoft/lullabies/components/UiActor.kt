package com.twobsoft.lullabies.components

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.twobsoft.lullabies.Assets
import com.twobsoft.lullabies.MainScreen

class UiActor(val tex: String): Actor() {

    var texture: Texture? = null

//    val offsetX = (MainScreen.BG_WIDTH * 0.025f).toInt()
//    val offsetY = -(MainScreen.BG_HEIGHT * 0.015f).toInt()
    val offsetX = 0
    val offsetY = 0
    var srcWidth: Int = 0
    var scrHeight: Int = 0
//    val yScale = MainScreen.ratio * 2.3f
//    val xScale = MainScreen.ratio * 2.3f
//    val yScale = 1f
//    val xScale = 1f

    fun init() {

        val imgTexture = Assets.getAsset(tex).also {
            it.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        }
        srcWidth = imgTexture.width
        scrHeight = imgTexture.height

        width = MainScreen.BG_WIDTH
        height = MainScreen.BG_HEIGHT


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
            scaleX, scaleY,
            rotation,
            offsetX, offsetY,
            srcWidth,
            scrHeight,
            false, false
        )

    }

}