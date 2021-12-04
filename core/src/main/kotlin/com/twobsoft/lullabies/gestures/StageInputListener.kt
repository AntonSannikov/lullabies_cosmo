package com.twobsoft.lullabies.gestures

import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Intersector.isPointInPolygon
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Array
import com.twobsoft.lullabies.*
import com.twobsoft.lullabies.components.AnimatedActor
import com.twobsoft.lullabies.components.LayerActor
import com.twobsoft.lullabies.models.*
import com.twobsoft.lullabies.components.UiActor
import com.twobsoft.lullabies.ui.UiModel
import com.twobsoft.lullabies.utils.Utils

class StageInputListener(val screen: MainScreen): MyGestureListener.DirectionListener {

    override fun onLeft() {
        if (screen.currentStageNumber == screen.STAGES_COUNT || screen.isSwiping) {
            return
        }
        changeStage(1)
    }

    override fun onRight() {
        if (screen.currentStageNumber == 1 || screen.isSwiping) {
            return
        }
        changeStage(-1)
    }

    override fun onTap(x: Float, y: Float, count: Int, button: Int) {

        val xNorm = x / MainScreen.BG_WIDTH
        val yNorm = y / MainScreen.BG_HEIGHT
        screen.shaderFocusOffset = Vector2(-(xNorm - 0.5f),yNorm-0.5f)
//        screen.isInterStellar = true
//        screen.isBarrel = true


        screen.stage.actors.forEach {
            if (it is AnimatedActor && it.hitBox.size > 2) {
                if (isPointInPolygon(Utils.floatArrayToVec2Array(it.hitBox.toFloatArray()),
                        Vector2(x, MainScreen.BG_HEIGHT - y))
                ) {
                    it.isNeedAnimate = true
                    it.addAction(Actions.scaleBy(0.6f, 0.6f, 5f))
                }
            }
        }
    }

    override fun onUp() {}

    override fun onDown() {}

    override fun onPan(x: Float, y: Float, deltaX: Float, deltaY: Float) {}



    fun createStage(stageModel: Entity, increment: Int) {

        if (screen.currentStageNumber != 0) {
            val uiModel = UiModel()
            uiModel.all.forEach {
                it.init()
                screen.stage.addActor(it)
            }
        }

        stageModel.all.forEach {
            if (it is LayerActor) { it.init() }
            it.x = MainScreen.BG_WIDTH * increment
            screen.stage.addActor(it)
            it.actions.forEach { action ->
                it.addAction(action)
            }
        }
    }

    private fun changeStage(increment: Int) {
        screen.currentStageNumber += increment

        var newPlanetModel: Entity? =null

        screen.isSwiping = true

        when(screen.currentStageNumber){
            1 -> newPlanetModel     = SunModel()
            2 -> newPlanetModel     = MercuryModel()
            3 -> newPlanetModel     = VenusModel()
            4 -> newPlanetModel     = EarthModel()
            5 -> newPlanetModel     = MoonModel()
            6 -> newPlanetModel     = MarsModel()
            7 -> newPlanetModel     = JupiterModel()
            8 -> newPlanetModel     = SaturnModel()
            9 -> newPlanetModel     = UranusModel()
            10 -> newPlanetModel    = NeptuneModel()
            11 -> newPlanetModel    = PlutoModel()
            12 -> newPlanetModel    = AsteroidModel()
            13 -> newPlanetModel    = CometModel()
            14 -> newPlanetModel    = SpaceshipModel()
            15 -> newPlanetModel    = AlienshipModel()
        }

        if (newPlanetModel == null) {
            return
        }

        screen.stage.actors.forEach {
            if (it is LayerActor) {
                it.isNeedRemove = true
            }
        }

        createStage(newPlanetModel, increment)

        if (screen.currentStageNumber != 0) {

            val hudActors = arrayListOf<UiActor>()
            screen.stage.actors.forEach {
                if (it is UiActor) {
                    hudActors.add(it)
                    it.remove()
                }
            }
            hudActors.forEach { screen.stage.addActor(it) }
        }

        screen.resetShader()
        var isReseted = false

        screen.stage.actors.forEach {
            if (it is LayerActor) {
                it.addAction(
                    Actions.sequence(
                        Actions.moveBy(-MainScreen.BG_WIDTH * increment, 0f, 1f),
                        Actions.run {
                            if (!isReseted) {
                                isReseted = true
                                screen.isSwiping = false
                                screen.currentModel = newPlanetModel
                                screen.barrelShaderPower =
                                    LullabiesGame.BARREL_SHADER_PULSE_START_POWER
                                screen.powerDelta =
                                    -(screen.barrelShaderPower - LullabiesGame.BARREL_SHADER_PULSE_MAX_POWER) / 600
                                screen.isBarrelShaderReseted = false
                                screen.stage.actors.forEach { component ->
                                    if (component is LayerActor && component.isNeedReinit) {
                                        component.reInit()
                                    }
                                }
                            }
                            if (it.isNeedRemove) {
                                it.remove()
                            }
                        }
                    )
                )
            }
        }

    }
}