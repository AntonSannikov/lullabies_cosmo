package com.twobsoft.babymozartspacetrip.hud


import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.twobsoft.babymozartspacetrip.MainScreen
import ktx.assets.disposeSafely
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator

import com.badlogic.gdx.Gdx




class HudActor(
    val tex: String,
    var text: String = "",
    val actorTexture: Texture,
    val isOriginSize: Boolean = false,
    ): Actor() {

    var texture: Texture? = null
    var srcWidth: Int = 0
    var srcHeight: Int = 0
    val hitBox = arrayListOf<Float>()
    var tapHandler : (() -> Unit)? = null

    var textX = 0f
    var textY = 0f
    var font : BitmapFont? = null
    var textPointer = 0f
    var textPartPointer = 0f
    var textBound = MainScreen.BG_WIDTH
    var textWidth = 0f
    var textHeight = 0f
    var isTextDrawing = false
    var isTextPartAdded = false
    var interActions = arrayListOf<HashMap<String, Float>>()
    var stageZIndex = 0


    init {
        if (text != "") {
            isTextDrawing = true
            val fontFile    = Gdx.files.internal("fonts/digital-7_mono.ttf")
            val generator   = FreeTypeFontGenerator(fontFile)
            val parameter   = FreeTypeFontGenerator.FreeTypeFontParameter()
            parameter.size  = 15
            font = generator.generateFont(parameter)
            generator.dispose()
            font!!.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
            font!!.data.scale(MainScreen.BG_HEIGHT * 0.00153f)
            font!!.color.set(0f, 0f, 0f, 1f)
            val glyphLayout = GlyphLayout(font, text)
            textWidth   = glyphLayout.width
            textHeight  = glyphLayout.height
        }

        srcWidth = actorTexture.width
        srcHeight = actorTexture.height

        if (isOriginSize) {
            width = srcWidth.toFloat()
            height = srcHeight.toFloat()
        }

        texture = actorTexture

    }

    fun changeText(newText: String) {
        text = newText
        textPartPointer = 0f
        textPointer = 0f
        isTextPartAdded = false
        val glyphLayout = GlyphLayout(font, text)
        textWidth = glyphLayout.width
    }

    fun addTextPart() {
        if (textWidth >= MainScreen.BG_WIDTH * 0.8) {
            textPartPointer = MainScreen.BG_WIDTH * 0.9f + textWidth / 2
        } else textPartPointer = MainScreen.BG_WIDTH * 0.9f

        isTextPartAdded = true
    }


    fun getActionsFromMap(): Array<Action> {
        val result = arrayListOf<Action>()
        interActions.forEach {
            if (it.containsKey("scaleBy")) {
                val action = Actions.sequence(
                    Actions.scaleBy(it["scaleBy"]!!, it["scaleBy"]!!, it["duration"]!!, Interpolation.fade),
                    Actions.scaleBy(-it["scaleBy"]!!, -it["scaleBy"]!!, it["duration"]!!, Interpolation.fade )
                )
                result.add(action)
            }
        }
        return  result.toTypedArray()
    }


    override fun remove(): Boolean {
        actorTexture.disposeSafely()
        texture.disposeSafely()
        font.disposeSafely()
        return super.remove()
    }


    override fun draw(batch: Batch?, parentAlpha: Float) {

        batch!!.setColor(color.r, color.g, color.b, color.a * parentAlpha)

        batch.draw(texture,
            x, y,
            originX, originY,
            width, height,
            scaleX, scaleY,
            rotation,
            0, 0,
            srcWidth, srcHeight,
            false, false
        )

        if (isTextDrawing) {
            if (textX + textPointer + textWidth <= textBound) {
                textPointer = textPartPointer
                isTextPartAdded = false
            }
            if (textX + textPointer <= textBound && !isTextPartAdded) {
                addTextPart()
            }

            font!!.draw(batch, text, textX + textPointer, textY)
            textPointer -= 0.7f

        }

        if (isTextPartAdded) {
            font!!.draw(batch, text, textX + textPartPointer, textY)
            textPartPointer -= 0.7f
        }

//        MainScreen.shapeRenderer.set(ShapeRenderer.ShapeType.Line)
//        MainScreen.shapeRenderer.color = Color.RED
//        if (hitBox.size > 2) {
//            MainScreen.shapeRenderer.polygon(hitBox.toFloatArray())
//        }

    }
}