package com.twobsoft.lullabies.hud

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Actor
import com.twobsoft.lullabies.Assets
import com.twobsoft.lullabies.MainScreen



class HudActor(
    val tex: String,
    val text: String = "",
    val actorTexture: Texture): Actor() {

    var texture: Texture? = null
    var srcWidth: Int = 0
    var scrHeight: Int = 0
    val hitBox = arrayListOf<Float>()
    var tapHandler : (text: String) -> Unit = {}
    var textX = 0f
    var textY = 0f
    val font = BitmapFont()
    var textPointer = 0
    var textPartPointer = 0
    var textBound = MainScreen.BG_WIDTH
    var textWidth = 0f
    var isTextDrawing = false
    var isTextPartAdded = false


    init {
        if (text != "") {
            isTextDrawing = true
            font.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
            font.data.scale(MainScreen.BG_HEIGHT * 0.00153f)
            font.color.set(144 / 255f, 210 / 255f, 1f, 1f)
            val glyphLayout = GlyphLayout(font, text)
            textWidth = glyphLayout.width
        }

        srcWidth = actorTexture.width
        scrHeight = actorTexture.height

        width = MainScreen.BG_WIDTH
        height = MainScreen.BG_HEIGHT

        originX = MainScreen.BG_WIDTH / 2
        originY = MainScreen.BG_HEIGHT / 2

        texture = actorTexture

    }

    fun addTextPart() {
        textPartPointer = (- textWidth * 1.3).toInt()
        isTextPartAdded = true
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {

//        if (texture == null) {
//            return
//        }

        batch!!.setColor(color.r, color.g, color.b, color.a * parentAlpha)

        batch.draw(texture,
            x, y,
            originX, originY,
            width, height,
            scaleX, scaleY,
            rotation,
            0, 0,
            srcWidth, scrHeight,
            false, false
        )

        if (isTextDrawing) {
            if (textX + textPointer >= textBound) {
                textPointer = textPartPointer
                isTextPartAdded = false
            }

            if (textX + textPointer + textWidth >= textBound && !isTextPartAdded) {
                addTextPart()
            }

            font.draw(batch, text, textX + textPointer, textY)
            textPointer++

        }

        if (isTextPartAdded) {
            font.draw(batch, text, textX + textPartPointer, textY)
            textPartPointer++
        }


//        MainScreen.shapeRenderer.set(ShapeRenderer.ShapeType.Line)
//        MainScreen.shapeRenderer.color = Color.RED
//        if (hitBox.size > 2) {
//            MainScreen.shapeRenderer.polygon(hitBox.toFloatArray())
//        }
    }
}