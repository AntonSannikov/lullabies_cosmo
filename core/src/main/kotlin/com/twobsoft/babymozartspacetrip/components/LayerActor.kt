package com.twobsoft.babymozartspacetrip.components


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.*
import com.twobsoft.babymozartspacetrip.MainScreen
import com.twobsoft.babymozartspacetrip.splash.SplashScreen
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sin


class LayerGroup: Group() {
    var isNeedRemove = false
    val actions = arrayListOf<Action>()
}


class LayerActor(
    var tex: String,
    val isMenu: Boolean = false,
    var texture: Texture,
    val cHeight: Float = 0f,
    val cWidth: Float = 0f,
    val cX: Float = 0f,
    val cY: Float = 0f,
    val isOrbit: Boolean = false,
    val isRepeated: Boolean = false,
    val isOriginalSize: Boolean = false
)
    : Actor() {

    var notAct = false
    val actions = arrayListOf<Action>()
    val layers = arrayListOf<Texture>()
    var isNeedRemove = false
    var isNeedReposition = false
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

    // splash orbit
    var isSplashOrbit = false
    var splashAngleDelta = -2f
    var splashOrbitX = 0.6f
    var splashOrbitY = 0.15f
    var angleOffset = 107f


    init {
        if (MainScreen.isNightMode && !isMenu && tex.contains("background.png")) {
            tex = "planets/sleep.jpg"
            texture = Texture(Gdx.files.internal("planets/sleep.jpg"))
        }

        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        if (isRepeated) texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
        if (isOrbit) isOrbiting = true

        srcWidth = texture.width
        srcHeight = texture.height

        if (isOriginalSize) {
            width   = srcWidth.toFloat()
            height  = srcHeight.toFloat()
        }

        if (tex == "menu/stars.png") {
            height = MainScreen.BG_HEIGHT * 1.2f
            width = MainScreen.BG_HEIGHT * 1.2f
            x = -(width - MainScreen.BG_WIDTH) / 2
            y = -(height - MainScreen.BG_HEIGHT) / 2
            originX = width / 2
            originY = height / 2
        }

        layers.add(texture)
    }

    fun changeBackground(_tex: String, _texture: Texture) {
        tex = _tex
        texture = _texture
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        layers.clear()
        layers.add(texture)
        if (isRepeated) texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
    }

    fun offsetToPosition() {
        x = -xOffset.toFloat()
        xOffset = 0
        actions.forEach { addAction(it) }
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
        if (notAct) {
            return
        }
        super.act(delta)
        if (isOrbiting) {
            time += delta
        }
        if (isRepeated) {
            repeatTime += delta
        }
    }


    override fun draw(batch: Batch?, parentAlpha: Float) {
        batch!!.setColor(color.r, color.g, color.b, color.a * parentAlpha)

        super.draw(batch, parentAlpha)

        if (x < xBounds.x || x >= xBounds.y) {
            return
        }

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

        if (isSplashOrbit) {
            angle += splashAngleDelta
            if (angle > 360 || angle < -360) angle = 0f


            x = splashOrbitX * MainScreen.BG_WIDTH*kotlin.math.cos(PI / 180f * angle).toFloat()
            y = splashOrbitY * MainScreen.BG_HEIGHT*sin(PI / 180f * angle).toFloat()

            x+= MainScreen.BG_WIDTH / 2 - width/2
            y+= MainScreen.BG_HEIGHT / 2 - height/2

            originX = width / 2
            originY = height / 2
            rotation = angle + angleOffset
            if (rotation > 360 || rotation < -360) rotation = 0f
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


//        if (tex == "splash/comet_bot.png") {
//            MainScreen.shapeRenderer.set(ShapeRenderer.ShapeType.Line)
//            MainScreen.shapeRenderer.color = Color.RED
//            MainScreen.shapeRenderer.rect(x,y,width,height)
//        }


//        SplashScreen.shapeRenderer.set(ShapeRenderer.ShapeType.Line)
//        SplashScreen.shapeRenderer.color = Color.RED
//        SplashScreen.shapeRenderer.rect(x,y,width,height)



    }

}

