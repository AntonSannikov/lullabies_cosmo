package com.twobsoft.lullabies

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.assets.disposeSafely
import ktx.async.KtxAsync
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.utils.viewport.*
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.twobsoft.lullabies.LullabiesGame.Companion.BARREL_SHADER_PULSE_MAX_POWER
import com.twobsoft.lullabies.LullabiesGame.Companion.BARREL_SHADER_PULSE_START_POWER
import com.twobsoft.lullabies.components.LayerActor
import com.twobsoft.lullabies.gestures.StageInputListener
import com.twobsoft.lullabies.models.*
import ktx.scene2d.actors


class LullabiesGame : KtxGame<KtxScreen>() {

    companion object {
        const val ANIMATION_TIME = 10f
        const val BARREL_SHADER_PULSE_START_POWER = 0.33f
        const val BARREL_SHADER_PULSE_MAX_POWER = 0.27f
    }

    override fun create() {
        KtxAsync.initiate()
        val assets = Assets
        assets.loadUi()
        assets.load(0)
//        assets.load(1)
//        assets.load(2)
//        assets.load(3)
//        assets.load(4)
//        assets.load(5)
//        assets.load(6)
//        assets.load(7)
//        assets.load(8)
//        assets.load(9)
//        assets.load(10)
//        assets.load(11)
//        assets.load(12)
//        assets.load(13)
//        assets.load(14)
//        assets.load(15)

        assets.manager.finishLoading()
        addScreen(MainScreen())
        setScreen<MainScreen>()
    }
}


class MainScreen : KtxScreen {

    companion object {
        val BG_WIDTH    = Gdx.graphics.width.toFloat()
        val BG_HEIGHT   = Gdx.graphics.height.toFloat()
        val ratio       = BG_WIDTH / BG_HEIGHT
        val shapeRenderer = ShapeRenderer()
    }

    val STAGES_COUNT = 15
    var currentStageNumber = 0

    val stage: Stage
    private var camera: OrthographicCamera
    private var viewport: Viewport

    // SHADERS AND RENDERING
    val fbo = FrameBuffer(Pixmap.Format.RGB888, BG_WIDTH.toInt(), BG_HEIGHT.toInt(), false)
    var shaderFocusOffset = Vector2(0f, 0f)

    // BARREL SHADER
    var barrelShaderPower: Float                = BARREL_SHADER_PULSE_START_POWER
    private var barrelShaderMaxPower: Float     = BARREL_SHADER_PULSE_MAX_POWER
    var powerDelta                              = -(barrelShaderPower - barrelShaderMaxPower) / 600
    var isBarrelShaderReseted : Boolean         = false
    val barrelShader = ShaderProgram(
        Gdx.files.internal("shaders/barrel/vertex.glsl").readString(),
        Gdx.files.internal("shaders/barrel/fragment.glsl").readString()
    )
    var isBarrel = false


    // INTERSTELLAR SHADER
    var time = 6f
    val rgbNoiseTex = Texture(Gdx.files.internal("misc/rgb_noise.png"))
    val interStellarShader = ShaderProgram(
        Gdx.files.internal("shaders/interstellar/vertex.glsl").readString(),
        Gdx.files.internal("shaders/interstellar/fragment.glsl").readString()
    )
    var isInterStellar = false



    //
    var currentModel: Entity
    val inputListener: StageInputListener

    var isSwiping = false


    init {
        //ShaderProgram.pedantic = false
        shapeRenderer.setAutoShapeType(true)
        Gdx.gl.glLineWidth(10f)

        camera = OrthographicCamera(BG_WIDTH, BG_HEIGHT )
        viewport = FitViewport(BG_WIDTH, BG_HEIGHT , camera)
        stage = Stage(viewport)

        inputListener = StageInputListener(this)
        currentModel = MenuModel()
        inputListener.createStage(currentModel, 0)

        currentModel.all.forEach {
            stage.addActor(it)
        }

        Gdx.input.inputProcessor = GestureDetector(
            MyGestureListener(inputListener)
        )
    }



    // ==============================      METHODS     =============================================

    fun resetShader() {
        isBarrelShaderReseted = true
        powerDelta = (barrelShaderPower - barrelShaderMaxPower) / 20
    }



    // =============================================================================================
    //                                  RENDER
    override fun render(delta: Float) {

        val gl = Gdx.graphics.gL20
        gl.glClearColor(0f, 0f, 0f, 1f)
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        shapeRenderer.begin()
        if (isBarrel) {
            barrelShaderPower += powerDelta

            if (!isBarrelShaderReseted) {
                if (barrelShaderPower <= barrelShaderMaxPower && powerDelta < 0) {
                    powerDelta = -powerDelta
                } else if (barrelShaderPower >= BARREL_SHADER_PULSE_START_POWER && powerDelta > 0) {
                    powerDelta = -powerDelta
                }
            } else {
                if (barrelShaderPower >= BARREL_SHADER_PULSE_START_POWER) {
                    powerDelta = 0f
                }
            }

            barrelShader.bind()
            stage.batch.shader = barrelShader
            barrelShader.setUniformf("iTime", barrelShaderPower)
//            barrelShader.setUniformf("iFocus", shaderFocusOffset.x, shaderFocusOffset.y)
        }

        fbo.begin()
        stage.act()
        stage.draw()
        fbo.end()

        val texture0 = fbo.colorBufferTexture

        if (isInterStellar) {
            time += delta
            interStellarShader.bind()
            Gdx.graphics.gL20.glActiveTexture(GL20.GL_TEXTURE1)
            texture0.bind(1);
            interStellarShader.setUniformi("u_texture", 1)
            interStellarShader.setUniformf("iFocus", shaderFocusOffset.x, shaderFocusOffset.y)

            Gdx.graphics.gL20.glActiveTexture(GL20.GL_TEXTURE0)
            rgbNoiseTex.bind(0);
            interStellarShader.setUniformi("u_texture_noise", 0)
            interStellarShader.setUniformf("iTime", time)
            stage.batch.shader = interStellarShader
        }

        val textureRegion = TextureRegion(texture0, BG_WIDTH.toInt(), BG_HEIGHT.toInt())
        textureRegion.flip(false, true)

        stage.batch.begin()
        stage.batch.draw(textureRegion, 0f, 0f, BG_WIDTH, BG_HEIGHT)
        stage.batch.end()

        shapeRenderer.end()

    }
    //                                  RENDER
    // =============================================================================================


    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        super.resize(width, height)
    }

    override fun dispose() {
        barrelShader.disposeSafely()
        stage.disposeSafely()
    }


}

