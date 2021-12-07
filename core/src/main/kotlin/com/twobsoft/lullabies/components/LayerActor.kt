package com.twobsoft.lullabies.components


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.*
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.twobsoft.lullabies.Assets
import com.twobsoft.lullabies.MainScreen
import java.lang.Math.cos
import java.lang.Math.toRadians
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sin


class LayerGroup: Group() {
    var isNeedRemove = false
    val actions = arrayListOf<Action>()
}


class LayerActor(val tex: String, val isMenu: Boolean = false,
                 val cHeight: Float = 0f,
                 val cWidth: Float = 0f,
                 val cX: Float = 0f,
                 val cY: Float = 0f,
                 val isOrbit: Boolean = false,
                 val isRepeated: Boolean = false
)
    : Actor() {

    val actions = arrayListOf<Action>()
    val layers = arrayListOf<Texture>()
    var isNeedRemove = false
    var isNeedReinit = false
    var srcWidth = 0
    var srcHeight = 0
    var xOffset = 0
    var yOffset = 0
    var xBounds = Vector2(-MainScreen.BG_WIDTH, MainScreen.BG_WIDTH)

    // orbit
    var orbitRadius = 0f
    var time = 0f
    var angle = 0f
    var angleDelta = 0.07f
    var isOrbiting = false
    var startRadius = 0f
    var orbitAnchor = Vector2(0f, 0f)

    //repeating
    var xDelta = 0
    var yDelta = 0
    var repeatTime = 0f



    init {
        val imgTexture = Assets.getAsset(tex).also {
            it.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
            if (isRepeated) it.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
        }
        if (isOrbit) isOrbiting = true

        srcWidth = imgTexture.width
        srcHeight = imgTexture.height

        width = MainScreen.BG_WIDTH

        if (cHeight == 0f) {
            height = if (tex != "menu/background.png") width * 100 / 64 else MainScreen.BG_HEIGHT
        } else {
            height = cHeight
        }

        if (MainScreen.BG_WIDTH > 1600) {
            height = MainScreen.BG_HEIGHT * 0.8f
            width = height * 0.64f
            MainScreen.layerWidth = width
        }

        if (cWidth != 0f) { width = cWidth }

        originX = MainScreen.BG_WIDTH / 2
        originY = MainScreen.BG_HEIGHT / 2

        if (!isMenu) {
            if (MainScreen.BG_WIDTH <= 1600) {
                y = if (cY == 0f) MainScreen.BG_HEIGHT * 0.08f else cY
            } else {
                x += (MainScreen.BG_WIDTH - width) / 2
                y = if (cY == 0f) MainScreen.BG_HEIGHT * 0.15f else cY
            }
        }

        if (cX != 0f) { x = cX }
        if (cY != 0f) { y = cY }

        layers.add(imgTexture)
    }


    fun reInit() {
        addAction(Actions.moveBy(-xOffset.toFloat(), 0f))
        xOffset = 0
    }

    fun stopAnimation() {
        isOrbiting = false
    }

    fun startAnimation() {
        angle = abs((180*kotlin.math.atan(y/x) / kotlin.math.PI).toFloat())
        val rx = x - orbitAnchor.x
        val ry = y - orbitAnchor.y
        startRadius = kotlin.math.sqrt(ry*ry+rx*rx)

        isOrbiting = true
    }


    override fun act(delta: Float) {
        super.act(delta)
        if (isOrbiting) {
            time += delta
        }
        if (isRepeated) {
            repeatTime += delta
        }
    }


    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        if (x < xBounds.x || x >= xBounds.y) {
            return
        }

        batch!!.setColor(color.r, color.g, color.b, color.a * parentAlpha)

        if (isOrbiting) {
            angle += angleDelta

            if (angle > 360 || angle < -360) angle = 0f
            if (startRadius != orbitRadius) {
                if (startRadius > orbitRadius) startRadius-=0.05f else startRadius+=0.05f
                x = startRadius*kotlin.math.cos(PI / 180f * angle).toFloat() + orbitAnchor.x
                y = startRadius*sin(PI / 180f * angle).toFloat() + orbitAnchor.y
            } else {
                x = orbitRadius*kotlin.math.cos(PI / 180f * angle).toFloat() + orbitAnchor.x
                y = orbitRadius*sin(PI / 180f * angle).toFloat() + orbitAnchor.y
            }
        }

        if (isRepeated && repeatTime >= 0.1) {
            repeatTime = 0f
            xOffset += xDelta
            yOffset += yDelta
        }

        for (layer in layers) {
            batch.draw(layer,
                x, y,
                originX, originY,
                width, height,
                scaleX, scaleY,
                rotation,
                xOffset, yOffset,
                srcWidth, srcHeight,
                false, false
            )
        }

//        if (tex == "planets/alienship/hologram.png") {
//            MainScreen.shapeRenderer.set(ShapeRenderer.ShapeType.Line)
//            MainScreen.shapeRenderer.color = Color.RED
//            MainScreen.shapeRenderer.rect(x,y,width,height)
//        }
    }

}

