package com.twobsoft.lullabies.components


import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.*
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.twobsoft.lullabies.Assets
import com.twobsoft.lullabies.MainScreen


class LayerActor(val tex: String): Actor() {

    val actions = arrayListOf<Action>()
    val layers = arrayListOf<Texture>()
    var isNeedRemove = false
    var isNeedReinit = false
    var srcWidth = 0
    var srcHeight = 0
    var xOffset = 0
    var xBounds = Vector2(-MainScreen.BG_WIDTH, MainScreen.BG_WIDTH)


    fun init() {
        val imgTexture = Assets.getAsset(tex).also {
            it.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        }

        srcWidth = imgTexture.width
        srcHeight = imgTexture.height

        width = MainScreen.BG_WIDTH
        height = if (tex != "menu/background.png") width * 100 / 64 else MainScreen.BG_HEIGHT

        y = (MainScreen.BG_HEIGHT - height) / 2

        originX = width / 2
        originY = MainScreen.BG_HEIGHT / 2


        layers.add(imgTexture)
    }


    fun reInit() {
        addAction(Actions.moveBy(-xOffset.toFloat(), 0f))
        xOffset = 0
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

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
                srcWidth,
                srcHeight,
                false, false
            )
        }
    }

}

