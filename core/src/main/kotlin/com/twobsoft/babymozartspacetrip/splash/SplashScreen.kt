package com.twobsoft.babymozartspacetrip.splash

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.twobsoft.babymozartspacetrip.LullabiesGame
import com.twobsoft.babymozartspacetrip.MainScreen
import com.twobsoft.babymozartspacetrip.components.LayerActor
import com.twobsoft.babymozartspacetrip.menu.MenuSpineModel
import ktx.app.KtxScreen
import ktx.assets.disposeSafely

class MySplashScreen(val game: LullabiesGame): KtxScreen {

    val camera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    val viewport = FitViewport(camera.viewportWidth, camera.viewportHeight, camera)
    val stage = Stage(viewport)

    companion object {
//        val shapeRenderer = ShapeRenderer()
    }

    // INVERSE SHADING SHADER
    val inverseShader = ShaderProgram(
        Gdx.files.internal("shaders/inverse_shade/vertex.glsl").readString(),
        Gdx.files.internal("shaders/inverse_shade/fragment.glsl").readString()
    )
    var isInverseShading = false
    var shadeTime = 0f
    var isShade = false

    var isAssetsLoaded = false
    var isTransitionDone = false

    init {
//        shapeRenderer.setAutoShapeType(true)
        Gdx.gl.glLineWidth(10f)

        val model = SplashScreenModel(game.assets)
        model.all.forEach { it as LayerActor
            stage.addActor(it)
            it.actions.forEach { action -> it.addAction(action) }
        }
        game.assets.loadMenu()
        game.assets.loadHud()
    }


    override fun dispose() {
        inverseShader.disposeSafely()
        stage.disposeSafely()
        game.assets.unloadSplash()
        super.dispose()
    }


    override fun render(delta: Float) {
        val gl = Gdx.graphics.gL20
        gl.glClearColor(0f, 0f, 0f, 1f)
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

//        shapeRenderer.begin()

        if (game.assets.manager.update()) {
            if (!isAssetsLoaded) {
                isAssetsLoaded = true
                isInverseShading = true
            }

            if (isInverseShading) {
                shadeTime += delta
                inverseShader.bind()
                inverseShader.setUniformf("iResolution"     , MainScreen.BG_WIDTH, MainScreen.BG_HEIGHT)
                inverseShader.setUniformf("iTime"           , shadeTime)
                inverseShader.setUniformf("nightModeValue"  , 0f)
                stage.batch.shader = inverseShader
                if (shadeTime >= 2f) {
                    shadeTime = 0f
                    isInverseShading = false
                    isShade = true
                }
            } else if (isShade) {
                val mainScreen = MainScreen(game, MenuSpineModel(game.assets))
                mainScreen.isShade = true
                mainScreen.isInitialShading = true
                isTransitionDone = true
                game.mainScreen = mainScreen
                game.addScreen(mainScreen)
                game.setScreen<MainScreen>()
                mainScreen.isMenu = true
                this.dispose()
            }

            if (!isTransitionDone) {
                stage.act()
                stage.draw()
            }

        } else {
            stage.act()
            stage.draw()
        }

//        shapeRenderer.end()

    }


    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }
}