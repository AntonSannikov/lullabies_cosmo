package com.twobsoft.lullabies.gestures

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.twobsoft.lullabies.*
import com.twobsoft.lullabies.models.*
import com.twobsoft.lullabies.ui.UiActor
import ktx.scene2d.actors
import javax.swing.GroupLayout

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
        screen.isInterStellar = true
//        screen.stage.actors.forEach {
//            if (it is GameComponent) {
//                it.addAction(Actions.scaleBy(-2f, -2f, 15f))
//            }
//        }
    }

    override fun onUp() {}

    override fun onDown() {}

    override fun onPan(x: Float, y: Float, deltaX: Float, deltaY: Float) {}


    fun createStage(planetModel: Entity, increment: Int) {
        planetModel.all.forEach {
            it.init()
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
            if (it is GameComponent) {
                it.isNeedRemove = true
            }
        }

        createStage(newPlanetModel, increment)

        val hudActors = arrayListOf<UiActor>()
        screen.stage.actors.forEach {
            if (it is UiActor) {
                hudActors.add(it)
                it.remove()
            }
        }
        hudActors.forEach { screen.stage.addActor(it) }

        screen.resetShader()
        var isReseted = false

        screen.stage.actors.forEach {
            if (it is GameComponent) {
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
                                screen.isShaderReseted = false
                                screen.stage.actors.forEach { component ->
                                    if (component is GameComponent && component.isNeedReinit) {
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