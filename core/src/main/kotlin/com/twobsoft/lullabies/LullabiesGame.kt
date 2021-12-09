package com.twobsoft.lullabies

import com.badlogic.gdx.scenes.scene2d.Stage
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.assets.disposeSafely
import ktx.async.KtxAsync
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.utils.viewport.*
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.twobsoft.lullabies.LullabiesGame.Companion.BARREL_SHADER_PULSE_MAX_POWER
import com.twobsoft.lullabies.LullabiesGame.Companion.BARREL_SHADER_PULSE_START_POWER
import com.twobsoft.lullabies.gestures.StageInputListener
import com.twobsoft.lullabies.hud.HudModel
import com.twobsoft.lullabies.models.*
import com.twobsoft.lullabies.splash.SplashScreen


class LullabiesGame : KtxGame<KtxScreen>() {

    companion object {
        const val ANIMATION_TIME = 10f
        const val BARREL_SHADER_PULSE_START_POWER = 0.33f
        const val BARREL_SHADER_PULSE_MAX_POWER = 0.20f
    }

    val assets = Assets()

    override fun create() {
        KtxAsync.initiate()
        addScreen(SplashScreen(this))
        setScreen<SplashScreen>()
    }
}


class MainScreen(val game: LullabiesGame) : KtxScreen {

    companion object {
        val BG_WIDTH            = Gdx.graphics.width.toFloat()
        val BG_HEIGHT           = Gdx.graphics.height.toFloat()
        val layerHeight         = BG_WIDTH * 1.5625f
        val shapeRenderer       = ShapeRenderer()
        var layerWidth          = 0f
    }

    val STAGES_COUNT = 15
    var currentStageNumber = 0

    val stage: Stage
    private val camera: OrthographicCamera
    private val viewport: Viewport

    // SHADERS AND RENDERING
    val fbo = FrameBuffer(Pixmap.Format.RGB888, BG_WIDTH.toInt(), BG_HEIGHT.toInt(), false)
    val fbo2 = FrameBuffer(Pixmap.Format.RGB888, BG_WIDTH.toInt(), BG_HEIGHT.toInt(), false)
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

    // SHADE SHADER
    val shadeShader = ShaderProgram(
        Gdx.files.internal("shaders/shade/vertex.glsl").readString(),
        Gdx.files.internal("shaders/shade/fragment.glsl").readString()
    )
    var shadeTime = 0f
    var isShade = false


    // INTERSTELLAR SHADER
    var time = 6f
    val rgbNoiseTex = Texture(Gdx.files.internal("misc/rgb_noise.png"))
    val interStellarShader = ShaderProgram(
        Gdx.files.internal("shaders/interstellar/vertex.glsl").readString(),
        Gdx.files.internal("shaders/interstellar/fragment.glsl").readString()
    )
    var isInterStellar = false

    // INVERSE SHADING SHADER
    val inverseShader = ShaderProgram(
        Gdx.files.internal("shaders/inverse_shade/vertex.glsl").readString(),
        Gdx.files.internal("shaders/inverse_shade/fragment.glsl").readString()
    )

    var isInverseShading = false
    var isFishEye = false
    var inverseShadingTime = 0f
    val fishEyeDelta = 0.002f
    var inverseShadeTimeBound = 2.5f


    //
    val inputListener: StageInputListener
    val hudModel : HudModel
    var isHudTapable = true
    var isSwiping = false


    init {

        //ShaderProgram.pedantic = false
//        shapeRenderer.setAutoShapeType(true)
//        Gdx.gl.glLineWidth(10f)

        camera = OrthographicCamera(BG_WIDTH, BG_HEIGHT)
        viewport = FitViewport(BG_WIDTH, BG_HEIGHT, camera)
        stage = Stage(viewport)


        inputListener = StageInputListener(this)
        hudModel = HudModel(game.assets, inputListener)

//        val currentModel = AlienshipModel(game.assets)
        val currentModel = MenuModel(game.assets)
        inputListener.createSwipeStage(currentModel, 0)
        currentModel.all.forEach {
            stage.addActor(it)
        }
//        inputListener.refreshHud()

        Gdx.input.inputProcessor = GestureDetector(
            MyGestureListener(inputListener)
        )
    }



    // ==============================      METHODS     =============================================

//    fun reverseBarrelShader() {
//        isBarrelShaderReseted = true
//        powerDelta = (BARREL_SHADER_PULSE_START_POWER - barrelShaderPower) / 20
//    }

    fun resetBarrelShader() {
        barrelShaderPower = BARREL_SHADER_PULSE_START_POWER
        powerDelta = -(barrelShaderPower - BARREL_SHADER_PULSE_MAX_POWER) / 600
        isBarrelShaderReseted = false
    }

    fun resetInterstellarShader() {
        time = 6f
    }


    // =============================================================================================
    //                                  RENDER
    override fun render(delta: Float) {

        val gl = Gdx.graphics.gL20
        gl.glClearColor(0f, 0f, 0f, 1f)
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

//        shapeRenderer.begin()
        if (isBarrel) {
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
            barrelShaderPower += powerDelta
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
        } else if (isFishEye) {
            barrelShaderPower += fishEyeDelta
            isInverseShading = true
            if (barrelShaderPower > 0.55) {
                isFishEye = false
                stage.batch.shader = null
                barrelShaderPower = BARREL_SHADER_PULSE_START_POWER
            } else {
                barrelShader.bind()
                stage.batch.shader = barrelShader
                barrelShader.setUniformf("iTime", barrelShaderPower)
            }
        }

        var textureRegion = TextureRegion(texture0, BG_WIDTH.toInt(), BG_HEIGHT.toInt())
        textureRegion.flip(false, true)

        gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.batch.flush()

        fbo2.begin()
        stage.batch.begin()
        stage.batch.draw(textureRegion, 0f, 0f, BG_WIDTH, BG_HEIGHT)
        stage.batch.end()
        fbo2.end()

        if (isShade) {
            shadeTime += delta
            shadeShader.bind()
            shadeShader.setUniformf("iResolution", BG_WIDTH, BG_HEIGHT)
            shadeShader.setUniformf("iTime", shadeTime)
            stage.batch.shader = shadeShader
            if (shadeTime >= 1.6f) {
                isShade = false
                shadeTime = 0f
                stage.batch.shader = null
            }
        } else if (isInverseShading) {
            if (inverseShadingTime >= inverseShadeTimeBound) {
                isInverseShading = false
                isShade = true
                shadeTime += delta
                shadeShader.bind()
                shadeShader.setUniformf("iTime", shadeTime)
                stage.batch.shader = shadeShader
                inverseShadingTime = 0f
                inputListener.createMenu()
            } else {
                inverseShadingTime += delta
                inverseShader.bind()
                inverseShader.setUniformf("iResolution", BG_WIDTH, BG_HEIGHT)
                inverseShader.setUniformf("iTime", inverseShadingTime)
                stage.batch.shader = inverseShader
            }

        }

        textureRegion = TextureRegion(fbo2.colorBufferTexture, BG_WIDTH.toInt(), BG_HEIGHT.toInt())
        textureRegion.flip(false, true)

        stage.batch.begin()
        stage.batch.draw(textureRegion, 0f, 0f, BG_WIDTH, BG_HEIGHT)
        stage.batch.end()
//        shapeRenderer.end()

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
        game.assets.dispose()
    }

}

