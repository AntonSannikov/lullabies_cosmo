package com.twobsoft.lullabies.splash

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.twobsoft.lullabies.LullabiesGame
import com.twobsoft.lullabies.MainScreen
import com.twobsoft.lullabies.components.LayerActor
import ktx.app.KtxScreen

class SplashScreen(val game: LullabiesGame): KtxScreen {

    val camera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    val viewport = FitViewport(camera.viewportWidth, camera.viewportHeight, camera)
    val stage = Stage(viewport)

    companion object {
        val shapeRenderer = ShapeRenderer()
    }


    // SHADE SHADER
    val shadeShader = ShaderProgram(
        Gdx.files.internal("shaders/shade/vertex.glsl").readString(),
        Gdx.files.internal("shaders/shade/fragment.glsl").readString()
    )
    var shadeTime = 0f
    var isShade = false

    // INVERSE SHADING SHADER
    val inverseShader = ShaderProgram(
        Gdx.files.internal("shaders/inverse_shade/vertex.glsl").readString(),
        Gdx.files.internal("shaders/inverse_shade/fragment.glsl").readString()
    )
    var isInverseShading = false

    var isAssetsLoaded = false
    var isTransitionDone = false

    init {
        shapeRenderer.setAutoShapeType(true)
        Gdx.gl.glLineWidth(10f)


        val model = SplashScreenModel(game.assets)
        model.all.forEach { it as LayerActor
            stage.addActor(it)
            it.actions.forEach { action -> it.addAction(action) }
        }

        game.assets.loadHud()
        game.assets.load(0)
        game.assets.load(1)
        game.assets.load(2)
        game.assets.load(3)
        game.assets.load(4)
//        game.assets.load(5)
//        game.assets.load(6)
//        game.assets.load(7)
//        game.assets.load(8)
//        game.assets.load(9)
//        game.assets.load(10)
//        game.assets.load(11)
//        game.assets.load(12)
//        game.assets.load(13)
//        game.assets.load(14)
//        game.assets.load(15)
    }

    override fun render(delta: Float) {
        val gl = Gdx.graphics.gL20
        gl.glClearColor(0f, 0f, 0f, 1f)
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        if (game.assets.manager.update()) {
            if (!isAssetsLoaded) {
                isAssetsLoaded = true
                isInverseShading = true
            }

            if (isInverseShading) {
                shadeTime += delta
                inverseShader.bind()
                inverseShader.setUniformf("iResolution", MainScreen.BG_WIDTH, MainScreen.BG_HEIGHT)
                inverseShader.setUniformf("iTime", shadeTime)
                stage.batch.shader = inverseShader
                if (shadeTime >= 1.6f) {
                    shadeTime = 0f
                    isInverseShading = false
                    isShade = true
                }
            } else if (isShade) {
                val mainScreen = MainScreen(game)
                mainScreen.isShade = true
                game.addScreen(mainScreen)
                this.dispose()
                game.setScreen<MainScreen>()
            }

            if (!isTransitionDone) {
                stage.act()
                stage.draw()
            }

        } else {
            stage.act()
            stage.draw()
        }

    }


    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }
}