package com.twobsoft.babymozartspacetrip



import DialogInterface
import com.badlogic.gdx.scenes.scene2d.Stage
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.assets.disposeSafely
import ktx.async.KtxAsync
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.utils.viewport.*
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.BufferUtils
import com.esotericsoftware.spine.*
import com.twobsoft.babymozartspacetrip.LullabiesGame.Companion.BARREL_SHADER_PULSE_MAX_POWER
import com.twobsoft.babymozartspacetrip.LullabiesGame.Companion.BARREL_SHADER_PULSE_START_POWER
import com.twobsoft.babymozartspacetrip.components.SpineComponent
import com.twobsoft.babymozartspacetrip.gestures.StageInputListener
import com.twobsoft.babymozartspacetrip.hud.HudModel
import com.twobsoft.babymozartspacetrip.menu.MenuSpineModel
import com.twobsoft.babymozartspacetrip.splash.MySplashScreen
import com.twobsoft.lullabies.MyGestureListener



class LullabiesGame(val serviceApi: ServicesCoreInterface, val adServices: DialogInterface) : KtxGame<KtxScreen>() {

    companion object {
        const val ANIMATION_TIME                    = 20f
        const val BARREL_SHADER_PULSE_START_POWER   = 0.33f
        const val BARREL_SHADER_PULSE_MAX_POWER     = 0.20f
    }

    val assets = Assets()
    var mainScreen: MainScreen?=null

    override fun create() {
        MediaPlayer.serviceApi = serviceApi
        KtxAsync.initiate()
        assets.loadSplash()
        assets.manager.finishLoading()
        addScreen(MySplashScreen(this))
        setScreen<MySplashScreen>()
    }

}


class MainScreen(val game: LullabiesGame, var menuModel: MenuSpineModel) : KtxScreen {


    companion object {
        val isTablet            = Gdx.graphics.width.toFloat() >= 1600f
        val BG_WIDTH            = Gdx.graphics.width.toFloat()
        val BG_HEIGHT           = Gdx.graphics.height.toFloat()
        val ratio               = BG_WIDTH / BG_HEIGHT
        val shapeRenderer       = ShapeRenderer()
        val bottomPadding       = BG_HEIGHT * 0.12f
        var isNightMode         = false
        val NIGHT_MODE_VALUE    = 0.65f
    }


    var currentStageNumber  = 1
    var AVAILABLE_STAGES = game.serviceApi.AVAILABLE_STAGES

    val stage: Stage
    private val camera: OrthographicCamera
    private val viewport: Viewport


    // SHADERS AND RENDERING
    val fbo = FrameBuffer(Pixmap.Format.RGB888, BG_WIDTH.toInt(), BG_HEIGHT.toInt(), false)
    val fbo2 = FrameBuffer(Pixmap.Format.RGB888, BG_WIDTH.toInt(), BG_HEIGHT.toInt(), false)
    var shaderFocusOffset = Vector2(0f, 0f)

    // NIGHT SHADER
    val nightShader = ShaderProgram(
        Gdx.files.internal("shaders/night/vertex.glsl").readString(),
        Gdx.files.internal("shaders/night/fragment.glsl").readString()
    )
    var nightShaderTime     = 1.25f
    var isNightShader       = false

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
    var shadeTime           = 0f
    var isShade             = false
    var isInitialShading    = false
    var initialShadeDelta   = 0.04f


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

    var isInverseShading    = false
    var inverseShadingTime  = 0.5f


    //
    val hudHandler = HudHandler(this)
    val inputListener: StageInputListener
    val hudModel : HudModel
    var isHudTapable    = false
    var isSwiping       = false
    var isMenu          = false
    var isHud           = false
    var isLooping       = false
    var isMenuTappable  = true


    init {
        //ShaderProgram.pedantic = false
        game.serviceApi.initialize()
        shapeRenderer.setAutoShapeType(true)
        Gdx.gl.glLineWidth(10f)

        camera      = OrthographicCamera(BG_WIDTH, BG_HEIGHT)
        viewport    = FitViewport(BG_WIDTH, BG_HEIGHT, camera)
        stage       = Stage(viewport)

        inputListener = StageInputListener(this, hudHandler)
        inputListener.initCallbacks()
        hudModel = HudModel(game.assets, inputListener)
//        menuModel.all.forEach {
//            it.initFont()
//        }

        inputListener.createMenu()

        Gdx.input.inputProcessor = GestureDetector(
            MyGestureListener(inputListener)
        )
    }

    val renderer = SkeletonRenderer().also {
        it.setPremultipliedAlpha(true)
    }

    val polygonSpriteBatch = PolygonSpriteBatch()

    // ==============================      METHODS     =============================================

//    fun reverseBarrelShader() {
//        isBarrelShaderReseted = true
//        powerDelta = (BARREL_SHADER_PULSE_START_POWER - barrelShaderPower) / 20
//    }

    fun resetBarrelShader() {
        barrelShaderPower       = BARREL_SHADER_PULSE_START_POWER
        powerDelta              = -(barrelShaderPower - BARREL_SHADER_PULSE_MAX_POWER) / 600
        isBarrelShaderReseted   = false
    }

    fun resetInterstellarShader() {
        inverseShadingTime = 0.5f
        time = 6f
    }




    // =============================================================================================
    //                                  RENDER
    override fun render(delta: Float) {

        val gl = Gdx.graphics.gL20
        gl.glClearColor(0f, 0f, 0f, 1f)
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        shapeRenderer.begin()

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
            inverseShadingTime += delta * 1.2f
            interStellarShader.bind()
            Gdx.graphics.gL20.glActiveTexture(GL20.GL_TEXTURE1)
            texture0.bind(1);
            interStellarShader.setUniformi("u_texture"      , 1)
            interStellarShader.setUniformf("iFocus"         , shaderFocusOffset.x, shaderFocusOffset.y)
            interStellarShader.setUniformf("iResolution"    , BG_WIDTH, BG_HEIGHT)
            interStellarShader.setUniformf("shadeTime"      , inverseShadingTime)
            Gdx.graphics.gL20.glActiveTexture(GL20.GL_TEXTURE0)
            rgbNoiseTex.bind(0);
            interStellarShader.setUniformi("u_texture_noise"    , 0)
            interStellarShader.setUniformf("iTime"              , time)
            stage.batch.shader = interStellarShader
        }

        // NIGHT SHADER
        if (isNightMode) {
            nightShaderTime += 0.008f
            nightShader.bind()
            nightShader.setUniformf("isNight"       , 1f)
            nightShader.setUniformf("iTime"         , nightShaderTime)
            nightShader.setUniformf("shadeValue"    , NIGHT_MODE_VALUE)
            stage.batch.shader          = nightShader
            polygonSpriteBatch.shader   = nightShader
        } else if (isNightShader) {
            nightShaderTime += 0.005f
            if (nightShaderTime >= 1.25f) {
                nightShaderTime = 1.25f
                isNightShader   = false
            }
            nightShader.bind()
            nightShader.setUniformf("isNight"       , 0f)
            nightShader.setUniformf("iTime"         , nightShaderTime)
            nightShader.setUniformf("shadeValue"    , NIGHT_MODE_VALUE)
            stage.batch.shader          = nightShader
            polygonSpriteBatch.shader   = nightShader
        }


        var textureRegion = TextureRegion(texture0, BG_WIDTH.toInt(), BG_HEIGHT.toInt())
        textureRegion.flip(false, true)

        gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.batch.flush()
        fbo2.begin()
        stage.batch.begin()
        stage.batch.draw(textureRegion, 0f, 0f, BG_WIDTH, BG_HEIGHT)
        stage.batch.end()

        if (isMenu) {
            var topActor: SpineComponent?= null
            menuModel.all.forEach {
                if (it.isTransitionAnimation) {
                    topActor = it
                }
            }

            for (spine in menuModel.all) {
                spine.state.update(delta)
                spine.state.apply(spine.skeleton)
                if (spine.rotation != 0f) {
                    spine.skeleton.rootBone.rotation = spine.rotation
                }
                spine.skeleton.updateWorldTransform()
                if (spine.isTransitionAnimation) {
                    continue
                }
                polygonSpriteBatch.begin()
                renderer.draw(polygonSpriteBatch, spine.skeleton)
                polygonSpriteBatch.end()
            }

            if (topActor != null) {
                val newPosition = topActor!!.getNewPosition()
                topActor!!.updateScaling(0.01f)
                topActor!!.setPos(newPosition.x, newPosition.y)
                polygonSpriteBatch.begin()
                renderer.draw(polygonSpriteBatch, topActor!!.skeleton)
                polygonSpriteBatch.end()
            }

            if (isMenu) {
                stage.batch.begin()
                for (spine in menuModel.all) {
                    spine.drawFont(stage.batch)
//                    shapeRenderer.set(ShapeRenderer.ShapeType.Line)
//                    shapeRenderer.color = Color.RED
//                    if (spine.hitBox.size > 2) {
//                        shapeRenderer.polygon(spine.hitBox.toFloatArray())
//                    }
                }
                stage.batch.end()

            }

        } else if (isHud) {
            for (spine in hudModel.allSkeletons) {
                spine.state.update(0.03f)
                spine.state.apply(spine.skeleton)
                if (spine.rotation != 0f) {
                    spine.skeleton.rootBone.rotation = spine.rotation
                }
                spine.skeleton.updateWorldTransform()

                if (spine.isTransitionAnimation) {
                    continue
                }
                polygonSpriteBatch.begin()
                renderer.draw(polygonSpriteBatch, spine.skeleton)
                polygonSpriteBatch.end()

//                shapeRenderer.set(ShapeRenderer.ShapeType.Line)
//                shapeRenderer.color = Color.RED
//                if (spine.hitBox.size > 2) {
//                    shapeRenderer.polygon(spine.hitBox.toFloatArray())
//                }
            }
        }

        fbo2.end()

        if (isShade) {
            if (isInitialShading) {
                shadeTime += initialShadeDelta
            } else {
                shadeTime += delta*2f
            }

            shadeShader.bind()
            shadeShader.setUniformf("iResolution"   , BG_WIDTH, BG_HEIGHT)
            shadeShader.setUniformf("iTime"         , shadeTime)
            if (isNightMode) shadeShader.setUniformf("nightModeValue", NIGHT_MODE_VALUE)
            else shadeShader.setUniformf("nightModeValue", 0f)

            stage.batch.shader = shadeShader
            if (shadeTime >= 1.4f) {
                if (isInitialShading) isInitialShading = false
                isShade     = false
                shadeTime   = 0f
                if (isMenu) {
                    isMenuTappable = true
                }
            }

        } else if (isInverseShading) {
            if (inverseShadingTime >= 2f) {
                inputListener.createMenu()
                isInverseShading    = false
                isShade             = true
                inverseShadingTime  = 0.5f
                shadeShader.bind()
                shadeShader.setUniformf("iTime", inverseShadingTime)
                if (isNightMode) shadeShader.setUniformf("nightModeValue", NIGHT_MODE_VALUE)
                else shadeShader.setUniformf("nightModeValue", 0f)
                stage.batch.shader = shadeShader

            } else {
                inverseShadingTime += delta*3f
                inverseShader.bind()
                inverseShader.setUniformf("iResolution" , BG_WIDTH, BG_HEIGHT)
                inverseShader.setUniformf("iTime"       , inverseShadingTime)
                if (isNightMode) inverseShader.setUniformf("nightModeValue", NIGHT_MODE_VALUE)
                else inverseShader.setUniformf("nightModeValue", 0f)
                stage.batch.shader = inverseShader
            }
        }

        textureRegion = TextureRegion(fbo2.colorBufferTexture, BG_WIDTH.toInt(), BG_HEIGHT.toInt())
        textureRegion.flip(false, true)

        if (!isNightShader && !isNightMode && !isBarrel && !isInterStellar && !isShade && !isInverseShading) {
            stage.batch.shader          = null
            polygonSpriteBatch.shader   = null
        }
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
        interStellarShader.disposeSafely()
        inverseShader.disposeSafely()
        shadeShader.disposeSafely()
        barrelShader.disposeSafely()
        for (spine in menuModel.all) {
            spine.dispose()
        }
        stage.disposeSafely()
        game.assets.dispose()
    }

}

