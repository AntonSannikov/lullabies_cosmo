package com.twobsoft.babymozartspacetrip.hud


import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.twobsoft.babymozartspacetrip.MainScreen
import ktx.assets.disposeSafely


class HudActor(
    val tex: String,
    var text: String = "",
    val actorTexture: Texture,

    ): Actor() {

    var texture: Texture? = null
    var srcWidth: Int = 0
    var scrHeight: Int = 0
    val hitBox = arrayListOf<Float>()
    var tapHandler : (() -> Unit)? = null

    var textX = 0f
    var textY = 0f
    val font = BitmapFont()
    var textPointer = 0
    var textPartPointer = 0
    var textBound = MainScreen.BG_WIDTH
    var textWidth = 0f
    var isTextDrawing = false
    var isTextPartAdded = false
    var interActions = arrayListOf<HashMap<String, Float>>()


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

    fun changeText(newText: String) {
        text = newText
        textPartPointer = 0
        textPointer = 0
        isTextPartAdded = false
        val glyphLayout = GlyphLayout(font, text)
        textWidth = glyphLayout.width

    }

    fun addTextPart() {
//        textPartPointer = (textBound + textWidth * 1.3).toInt()
        if (textWidth >= MainScreen.BG_WIDTH * 0.8) {
            textPartPointer = (MainScreen.BG_WIDTH * 0.9f + textWidth / 2).toInt()
        } else textPartPointer = (MainScreen.BG_WIDTH * 0.9f).toInt()

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
            srcWidth, scrHeight,
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

            font.draw(batch, text, textX + textPointer, textY)
            textPointer--

        }

        if (isTextPartAdded) {
            font.draw(batch, text, textX + textPartPointer, textY)
            textPartPointer--
        }


//        MainScreen.shapeRenderer.set(ShapeRenderer.ShapeType.Line)
//        MainScreen.shapeRenderer.color = Color.RED
//        if (hitBox.size > 2) {
//            MainScreen.shapeRenderer.polygon(hitBox.toFloatArray())
//        }
    }
}