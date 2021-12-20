package com.twobsoft.babymozartspacetrip.components


import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2
import com.esotericsoftware.spine.*
import com.twobsoft.babymozartspacetrip.MainScreen
import ktx.assets.disposeSafely


class SpineComponent(
    var atlas: TextureAtlas,
    var jsonFile: FileHandle,
    val scale: Float,
    var isAlwaysAnimated: Boolean=true,
//    var font: BitmapFont?=null,
    var fontFile: FileHandle?=null,
    var fontPosition: Vector2=Vector2.Zero,
    var name: String?=null,
    var fontScaling: Float = MainScreen.BG_WIDTH * 0.002f,
    var timeScaling:Float = 0.5f
    ) {

    var font = BitmapFont()
    var oldTransformMatrix: Matrix4?=null
    var mx4Font = Matrix4()
    val fontColor: Color=Color(1f,1f,1f,1f)

    var position = Vector2()

    private val transPositionDelta = 5f

    var tapHandler : (() -> Unit)? = null

    var stageNumber = 0
    var scaling2 = 1f
    var hitBox = arrayListOf<Float>()
    var rotation = 0f
    var isTransitionAnimation = false
    var textWidth = 0f
    var textHeight = 0f




    var json = SkeletonJson(atlas).also{
        it.scale = scale
    }

    var skeletonData = json.readSkeletonData(jsonFile)

    var skeleton = Skeleton(skeletonData).also {
        it.setPosition(0f, 0f)
    }

    var stateData = AnimationStateData(skeletonData)

    var state = AnimationState(stateData).also {
        it.timeScale = timeScaling
        if (isAlwaysAnimated) it.setAnimation(0, "animation", true)
    }



    init {

        if (fontFile != null) {
            val generator   = FreeTypeFontGenerator(fontFile)
            val parameter   = FreeTypeFontGenerator.FreeTypeFontParameter()
            parameter.size  = 15
            font = generator.generateFont(parameter)
            generator.dispose()
            font.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
            font.data.scale(fontScaling)
            font.color.set(fontColor)
            val glyphLayout = GlyphLayout(font, name)
            textWidth   = glyphLayout.width
            textHeight  = glyphLayout.height
        }
    }


    fun setUnavailableColor() {
        for (slot in skeletonData.slots) {
            slot.color.set(Color(0.5f, 0.5f, 0.5f, 1f))
        }
        skeleton = Skeleton(skeletonData).also {
            it.setPosition(0f, 0f)
        }
        stateData = AnimationStateData(skeletonData)
        state = AnimationState(stateData).also {
            it.timeScale = timeScaling
            if (isAlwaysAnimated) it.setAnimation(0, "animation", true)
        }
        setPos(position.x, position.y)
    }


    fun restoreColor() {
        for (i in 0 until skeletonData.slots.size) {
            skeletonData.slots[i].color.set(1f,1f,1f,1f)
        }
        skeleton = Skeleton(skeletonData).also {
            it.setPosition(0f, 0f)
        }
        stateData = AnimationStateData(skeletonData)
        state = AnimationState(stateData).also {
            it.timeScale = timeScaling
            if (isAlwaysAnimated) it.setAnimation(0, "animation", true)
        }
        setPos(position.x, position.y)
    }


    fun changeAtlas(_atlas: TextureAtlas, _jsonFile: FileHandle) {
        atlas = _atlas
        jsonFile = _jsonFile

        json = SkeletonJson(atlas).also{
            it.scale = scale
        }

        skeletonData = json.readSkeletonData(jsonFile)
        skeleton = Skeleton(skeletonData).also {
            it.setPosition(position.x, position.y)
        }

        stateData = AnimationStateData(skeletonData)
        state = AnimationState(stateData).also {
            it.timeScale = timeScaling
            if (isAlwaysAnimated) it.setAnimation(0, "animation", true)
        }
    }

    fun setPos(_x: Float, _y: Float) {
        position = Vector2(_x, _y)
        skeleton.setPosition(_x, _y)
    }

    fun setTimeScale(_value: Float) {
        timeScaling = _value
        state.timeScale = _value
    }

    fun updateScaling(delta: Float) {
        scaling2 += delta
        skeleton.setScale(scaling2, scaling2)
    }

    fun getNewPosition(): Vector2 {
        val result = Vector2()

        if (position.x <= MainScreen.BG_WIDTH / 2) {
            if (position.x + transPositionDelta > MainScreen.BG_WIDTH / 2) result.x = MainScreen.BG_WIDTH / 2
            else result.x = position.x + transPositionDelta
        } else {
            if (position.x - transPositionDelta < MainScreen.BG_WIDTH / 2) result.x = MainScreen.BG_WIDTH / 2
            else result.x = position.x - transPositionDelta
        }

        if (position.y <= MainScreen.BG_HEIGHT / 2) {
            if (position.y + transPositionDelta > MainScreen.BG_HEIGHT / 2) result.y = MainScreen.BG_HEIGHT / 2
            else result.y = position.y + transPositionDelta
        } else {
            if (position.y - transPositionDelta < MainScreen.BG_HEIGHT / 2) result.y = MainScreen.BG_HEIGHT / 2
            else result.y = position.y - transPositionDelta
        }

        return  result
    }


    fun drawFont(batch: Batch) {
        if (fontFile != null) {
            batch.transformMatrix = mx4Font
            font.draw(batch, name, position.x + fontPosition.x, position.y + fontPosition.y)
            batch.transformMatrix = oldTransformMatrix
        }

    }

    fun dispose() {
        font.disposeSafely()
    }

}