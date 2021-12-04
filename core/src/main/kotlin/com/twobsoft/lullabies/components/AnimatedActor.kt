package com.twobsoft.lullabies.components

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.graphics.g2d.TextureRegion

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.FloatArray
import com.twobsoft.lullabies.Assets
import com.twobsoft.lullabies.MainScreen


class AnimatedActor(val texturePath: String,
                    val frameCols: Int,
                    val frameRows: Int,
                    sizeX: Float,
                    sizeY: Float,
                    frameCount: Int? = null
                    )
    : Actor() {

    val hitBox = arrayListOf<Float>()
    var animation: Animation<TextureRegion>? = null
    var textureRegion: TextureRegion? = null
    var stateTime = 0f
    var xOffset = 0f
    var yOffset = 0f
    var isNeedAnimate = false

    init {

        height  = sizeY
        width   = sizeX

        val spriteSheet = Assets.getAsset(texturePath).also {
            it.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        }

        val tmp = TextureRegion.split(
            spriteSheet,
            spriteSheet.width / frameCols,
            spriteSheet.height / frameRows
        )

        val animationFrames = Array<TextureRegion>()
        var count = 0
        for (i in 0 until frameCols) {
            for (k in 0 until frameRows) {
                if ( frameCount == null || count < frameCount ) {
                    animationFrames.add(tmp[k][i])
                } else {
                    break
                }
                count ++
            }
        }

        animation = Animation<TextureRegion>(0.1f, animationFrames)
        stateTime = 0f
        textureRegion = animation!!.getKeyFrame(0f)

    }



    override fun act(delta: Float) {
        super.act(delta)
        if (isNeedAnimate) {
            stateTime += delta
            textureRegion = animation?.getKeyFrame(stateTime, false)
        }
        if (animation?.isAnimationFinished(stateTime) != false) {
            textureRegion = animation!!.getKeyFrame(0f)
            stateTime = 0f
            isNeedAnimate = false
        }
    }


    override fun draw(batch: Batch, parentAlpha: Float) {

        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha)

        batch.draw(
            textureRegion,
            xOffset, yOffset,
            width / 2, height / 2,
            width, height,
            scaleX, scaleY,
            rotation
        )

        MainScreen.shapeRenderer.set(ShapeRenderer.ShapeType.Line)
        MainScreen.shapeRenderer.color = Color.RED
//        MainScreen.shapeRenderer.rect(xOffset, yOffset, width, height)
        if (hitBox.size > 2) {
            MainScreen.shapeRenderer.polygon(hitBox.toFloatArray())
        }

    }
}

