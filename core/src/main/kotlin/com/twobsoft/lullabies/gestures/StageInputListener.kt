package com.twobsoft.lullabies.gestures

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Intersector.isPointInPolygon
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Array
import com.twobsoft.lullabies.*
import com.twobsoft.lullabies.components.AnimatedActor
import com.twobsoft.lullabies.components.LayerActor
import com.twobsoft.lullabies.models.*
import com.twobsoft.lullabies.components.HudActor
import com.twobsoft.lullabies.utils.Utils
import ktx.scene2d.actors

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

        println("${x / MainScreen.BG_WIDTH}:${1 - y / MainScreen.BG_HEIGHT}")
        val xNorm = x / MainScreen.BG_WIDTH
        val yNorm = y / MainScreen.BG_HEIGHT

        for (it in screen.stage.actors) {
            if (it is AnimatedActor && it.hitBox.size > 2) {
                if (isPointInPolygon(Utils.floatArrayToVec2Array(it.hitBox.toFloatArray()),
                        Vector2(x, MainScreen.BG_HEIGHT - y))
                ) {
                    it.remove()
                    screen.stage.addActor(it)
                    screen.shaderFocusOffset = Vector2(-(xNorm - 0.5f),yNorm-0.5f)
                    screen.isBarrel = true
                    it.isNeedAnimate = true
                    it.addAction(
                        Actions.parallel(
                            Actions.sequence(
                                Actions.delay(0.5f),
                                Actions.run {
                                    screen.isInterStellar = true
                                    addRollingHud(true) },
                                Actions.moveTo(MainScreen.BG_WIDTH / 2, MainScreen.BG_HEIGHT / 2, 3f)
                            ),
                            Actions.sequence(
                                Actions.scaleBy(1f, 1f, 2.7f, Interpolation.slowFast),
                                Actions.run {
                                    screen.isBarrel = false
                                    screen.resetBarrelShader()
                                    screen.isInterStellar = false
                                    screen.resetInterstellarShader()
                                    screen.currentStageNumber = it.stageNumber
                                    createStage(getStageModel(it.stageNumber))
                                    screen.isShade = true
                                },
                            )
                        )
                    )
                    break
                }
            }
        }
    }

    override fun onUp() {}

    override fun onDown() {}

    override fun onPan(x: Float, y: Float, deltaX: Float, deltaY: Float) {}


    fun refreshHud() {
        val hudActors = arrayListOf<HudActor>()
        var length = screen.stage.actors.size - 1
        var i = 0
        while (i < length) {
            val actor = screen.stage.actors[i]
            if (actor is HudActor) {
                hudActors.add(actor)
                actor.remove()
                length --
            } else { i++ }
        }

        if (hudActors.isEmpty()) {
            for (hudActor in screen.hudModel.all) {
                if (hudActor is HudActor) { hudActor.init() }
                screen.stage.addActor(hudActor)
            }
        } else {
            hudActors.forEach { screen.stage.addActor(it) }
        }
    }

    fun addRollingHud(isForward: Boolean) {
        var startingScale = Vector2()
        var targetScale = Vector2()

        if (isForward) {
            startingScale = Vector2(MainScreen.BG_WIDTH * 0.0003f, MainScreen.BG_HEIGHT * 0.0003f)
            targetScale = Vector2(-MainScreen.BG_WIDTH * 0.0003f, -MainScreen.BG_HEIGHT * 0.0003f)
        }

        screen.hudModel.all.forEach {
            if (it is HudActor) { it.init() }
            it.scaleBy(startingScale.x, startingScale.y)
            screen.stage.addActor(it)
            it.addAction(Actions.scaleBy(targetScale.x, targetScale.y, 2f))
        }

    }


    fun createStage(stageModel: Entity,) {

        screen.stage.actors.forEach {
            if (it is LayerActor) it.isNeedRemove = true
            if (it is AnimatedActor) it.isNeedRemove = true
        }

        stageModel.all.forEach {
            if (it is LayerActor) {
                it.init()
                screen.stage.addActor(it)
                if (it.isNeedReinit) { it.reInit() }
                it.actions.forEach { action ->
                    it.addAction(action)
                }
            }
        }
        screen.stage.actors.forEach {
            if (it is LayerActor && it.isNeedRemove) it.addAction(Actions.removeActor())
            if (it is AnimatedActor && it.isNeedRemove) it.addAction(Actions.removeActor())
        }

        refreshHud()
    }


    fun createSwipeStage(stageModel: Entity, increment: Int) {
        stageModel.all.forEach {
            if (it is LayerActor) {
                it.init()
                it.x = MainScreen.BG_WIDTH * increment
            }
            screen.stage.addActor(it)
        }
    }


    private fun getStageModel(stageNumber: Int): Entity {
        var newModel: Entity? =null

        when(stageNumber) {
            0 -> newModel     = MenuModel()
            1 -> newModel     = SunModel()
            2 -> newModel     = MercuryModel()
            3 -> newModel     = VenusModel()
            4 -> newModel     = EarthModel()
            5 -> newModel     = MoonModel()
            6 -> newModel     = MarsModel()
            7 -> newModel     = JupiterModel()
            8 -> newModel     = SaturnModel()
            9 -> newModel     = UranusModel()
            10 -> newModel    = NeptuneModel()
            11 -> newModel    = PlutoModel()
            12 -> newModel    = AsteroidModel()
            13 -> newModel    = CometModel()
            14 -> newModel    = SpaceshipModel()
            15 -> newModel    = AlienshipModel()
        }

        return  newModel!!
    }

    fun refreshStage() {
        screen.stage.actors.forEach {
            if (it is LayerActor) {
                if (it.isNeedRemove) { it.remove() }
                else { it.actions.forEach {action -> it.addAction(action) } }
            }
        }
    }

    private fun changeStage(increment: Int) {
        screen.currentStageNumber += increment
        screen.isSwiping = true

        val newStageModel = getStageModel(screen.currentStageNumber)

        screen.stage.actors.forEach {
            if (it is LayerActor) { it.isNeedRemove = true }
        }

        createSwipeStage(newStageModel, increment)

        if (screen.currentStageNumber != 0) {
            refreshHud()
        }

        screen.reverseBarrelShader()
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
                                screen.resetBarrelShader()
                                for (actor in screen.stage.actors) {
                                    if (actor is LayerActor && actor.isNeedReinit) {
                                        actor.reInit()
                                    }
                                }
                                refreshStage()
                            }
                        }
                    )
                )
            }
        }

    }
}