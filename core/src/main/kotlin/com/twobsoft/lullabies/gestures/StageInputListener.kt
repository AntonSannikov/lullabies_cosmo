package com.twobsoft.lullabies.gestures

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Intersector.isPointInPolygon
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.twobsoft.lullabies.*
import com.twobsoft.lullabies.components.AnimatedActor
import com.twobsoft.lullabies.components.LayerActor
import com.twobsoft.lullabies.models.*
import com.twobsoft.lullabies.hud.HudActor
import com.twobsoft.lullabies.components.LayerGroup
import com.twobsoft.lullabies.hud.HudGroup
import com.twobsoft.lullabies.utils.Utils
import java.lang.Exception
import kotlin.random.Random

class StageInputListener(val screen: MainScreen): MyGestureListener.DirectionListener {


    override fun onLeft() {
        if (screen.currentStageNumber == screen.STAGES_COUNT
            || screen.isSwiping
            || screen.currentStageNumber == 0) {
            return
        }
        changeStage(1)
    }

    override fun onRight() {
        if (screen.currentStageNumber == 1
            || screen.isSwiping
            || screen.currentStageNumber == 0) {
            return
        }
        changeStage(-1)
    }

    override fun onTap(x: Float, y: Float, count: Int, button: Int) {

        println("${x / MainScreen.BG_WIDTH}:${1 - y / MainScreen.BG_HEIGHT}")
        val xNorm = x / MainScreen.BG_WIDTH
        val yNorm = y / MainScreen.BG_HEIGHT

        var hudTapHandler : (() -> Unit)? = null

        for (it in screen.stage.actors) {
            // ANIMATED ACTORS
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
                                    addRollingHud() },
                                Actions.moveTo(
                                    MainScreen.BG_WIDTH / 2 - it.width / 2,
                                    MainScreen.BG_HEIGHT / 2 - it.height / 2,
                                    2f
                                )
                            ),
                            Actions.sequence(
                                Actions.scaleBy(0.8f, 0.8f, 2f, Interpolation.slowFast),
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
            // HUD ACTORS
            //
            else if (it is HudGroup && screen.isHudTapable) {
                for (child in it.children) {
                    val hudActor = child as HudActor
                    if (hudActor.hitBox.size > 2 &&
                        isPointInPolygon(Utils.floatArrayToVec2Array(hudActor.hitBox.toFloatArray()),
                            Vector2(x, MainScreen.BG_HEIGHT - y))
                    )
                    {
                        hudActor.actions.forEach { hudActor.addAction(it) }
                        hudTapHandler = hudActor.tapHandler
                        break
                    }
                }
            }
            //
            // HUD ACTORS
            if (hudTapHandler != null) {
                break
            }
        }

        hudTapHandler?.invoke()
    }

    override fun onUp() {}

    override fun onDown() {}

    override fun onPan(x: Float, y: Float, deltaX: Float, deltaY: Float) {}


    // =============================================================================================
    //                                          HUD
    fun refreshHud() {
        val hudGroups = arrayListOf<HudGroup>()
        var length = screen.stage.actors.size - 1
        var i = 0
        while (i < length) {
            val actor = screen.stage.actors[i]
            if (actor is HudGroup) {
                hudGroups.add(actor)
                actor.remove()
                length --
            } else { i++ }
        }

        if (hudGroups.isEmpty()) {
            for (hudActor in screen.hudModel.all) {
                screen.stage.addActor(hudActor)
            }
        } else {
            hudGroups.forEach { screen.stage.addActor(it) }
        }
    }

    fun addRollingHud() {

        val startingScale = Vector2(MainScreen.BG_WIDTH * 0.0003f, MainScreen.BG_HEIGHT * 0.0003f)
        val targetScale = Vector2(-MainScreen.BG_WIDTH * 0.0003f, -MainScreen.BG_HEIGHT * 0.0003f)
        screen.isHudTapable = false
        screen.hudModel.all.forEach {
            it.y = -MainScreen.BG_HEIGHT * 0.4f
            it.x = -MainScreen.BG_WIDTH * 0.2f
            it.scaleBy(startingScale.x, startingScale.y)
            screen.stage.addActor(it)
            it.addAction(
                Actions.parallel(
                    Actions.scaleBy(targetScale.x, targetScale.y, 1.5f),
                    Actions.moveBy(MainScreen.BG_WIDTH * 0.2f, MainScreen.BG_HEIGHT * 0.4f, 1.5f)
                )
            )
        }
    }

    fun rollbackHud() {
        screen.stage.actors.forEach {
            if (it is HudGroup) {
                it.addAction(
                    Actions.sequence(
                        Actions.delay(2f),
                        Actions.run { removeHud() }
                    )
                )
            }
        }
    }


    fun removeHud() {
        var i = 0
        var length = screen.stage.actors.size

        while (i < length) {
            val actor = screen.stage.actors[i]
            if (actor is HudGroup) {
                actor.remove()
                length--
            } else {
                i++
            }
        }
    }
    //                                          HUD
    // =============================================================================================



    // =============================================================================================
    //                                    BACK TO MENU

    fun startMenuTransition() {
        var isHudRollbacks = false
        screen.stage.actors.forEach {
            if (it is LayerActor || it is LayerGroup) {
                it.addAction(
                    Actions.parallel(
                        Actions.sequence(
                            Actions.delay(0.3f),
                            Actions.run {
                                if (!isHudRollbacks) {
                                    screen.isFishEye = true
                                    isHudRollbacks = true
                                    rollbackHud()
                                }
                            }
                        ),
                        Actions.scaleBy(-0.3f, -0.3f, 2f, Interpolation.slowFast)
                    )
                )
            }
        }
    }

    fun createMenu() {
        screen.resetBarrelShader()
        var length = screen.stage.actors.size

        while (0 < length) {
            screen.stage.actors[0].remove()
            length--
        }

        createSwipeStage(MenuModel(screen.game.assets), 0)
        screen.isFishEye = false
        screen.isInverseShading = false
    }

    //                                    BACK TO MENU
    // =============================================================================================


    fun createStage(stageModel: Entity,) {

        screen.stage.actors.forEach {
            if (it is LayerActor) it.isNeedRemove = true
            if (it is AnimatedActor) it.isNeedRemove = true
        }

        stageModel.all.forEach {
            if (it is LayerActor || it is LayerGroup) {
                screen.stage.addActor(it)
                if ((it is LayerActor && !it.isNeedReposition) || it is LayerGroup) {
                    it.actions.forEach { action ->
                        it.addAction(action)
                    }
                }
                if (it is LayerActor && it.isNeedReposition) {
                    it.offsetToPosition()
                }
            }
        }

        screen.stage.actors.forEach {
            if (it is LayerGroup && it.isNeedRemove) it.addAction(Actions.removeActor())
            if (it is LayerActor && it.isNeedRemove) it.addAction(Actions.removeActor())
            if (it is AnimatedActor && it.isNeedRemove) it.addAction(Actions.removeActor())
        }

        refreshStage()
        refreshHud()
    }


    fun createSwipeStage(stageModel: Entity, increment: Int) {
        stageModel.all.forEach {
            if (it is LayerActor || it is LayerGroup) {
                if (MainScreen.BG_WIDTH > 1660 && MainScreen.layerWidth != 0f) {
                    it.x += MainScreen.BG_WIDTH * increment - increment * (MainScreen.BG_WIDTH - MainScreen.layerWidth) / 2
                } else {
                    it.x += MainScreen.BG_WIDTH * increment
                }
            }

            screen.stage.addActor(it)
            if ((it is LayerActor && !it.isNeedReposition) || it is LayerGroup) {
                it.actions.forEach { action -> it.addAction(action) }
            } else if (it is LayerActor && it.isNeedReposition) {
                it.xOffset = (-1..1).random() * (0..MainScreen.BG_WIDTH.toInt()).random()
            }
        }
    }


    fun refreshStage() {
        var i = 0
        var length = screen.stage.actors.size
        while(i < length) {
            val actor = screen.stage.actors[i]
            if (actor is LayerActor) {
                if (actor.isNeedRemove) {
                    actor.remove()
                    length--
                } else {
                    if (actor.isOrbit) actor.startAnimation()
                    if (actor.isNeedReposition) actor.offsetToPosition()
                    actor.actions.forEach { actor.addAction(it) }
                    i++
                }
            } else if (actor is LayerGroup) {
                if (actor.isNeedRemove) {
                    actor.remove()
                    length--
                } else {
                    actor.actions.forEach { actor.addAction(it) }
                    i++
                }
            }
            else { i++ }
        }
        screen.isHudTapable = true
    }


    private fun changeStage(increment: Int) {
        screen.currentStageNumber += increment
        screen.isSwiping = true

        for (actor in screen.stage.actors) {
            if (actor is LayerActor) { actor.isNeedRemove = true }
            if (actor is LayerGroup) { actor.isNeedRemove = true }
        }

        val newStageModel = getStageModel(screen.currentStageNumber)

        createSwipeStage(newStageModel, increment)

        if (screen.currentStageNumber != 0) {
            refreshHud()
        }

//        screen.reverseBarrelShader()
        var isReseted = false

        screen.stage.actors.forEach {
            if (it is LayerActor || it is LayerGroup) {
                if (it is LayerActor && it.isOrbit) it.stopAnimation()
                it.addAction(
                    Actions.sequence(
                        getSwipingAction(increment),
                        Actions.run {
                            if (!isReseted) {
                                isReseted = true
                                screen.isSwiping = false
//                                screen.resetBarrelShader()
                                refreshStage()
                            }
                        }
                    )
                )
            }
        }
    }


    fun getSwipingAction(increment: Int) :Action {
        if (MainScreen.BG_WIDTH > 1600) {
            return Actions.moveBy(-MainScreen.BG_WIDTH * increment + increment * (MainScreen.BG_WIDTH - MainScreen.layerWidth) / 2, 0f, 0.5f)
        } else {
            return Actions.moveBy(-MainScreen.BG_WIDTH * increment, 0f, 0.5f)
        }
    }


    private fun getStageModel(stageNumber: Int): Entity {
        var newModel: Entity? =null

        when(stageNumber) {
            0 -> newModel     = MenuModel(screen.game.assets)
            1 -> newModel     = SunModel(screen.game.assets)
            2 -> newModel     = MercuryModel(screen.game.assets)
            3 -> newModel     = VenusModel(screen.game.assets)
            4 -> newModel     = EarthModel(screen.game.assets)
            5 -> newModel     = MoonModel(screen.game.assets)
            6 -> newModel     = MarsModel(screen.game.assets)
            7 -> newModel     = JupiterModel(screen.game.assets)
            8 -> newModel     = SaturnModel(screen.game.assets)
            9 -> newModel     = UranusModel(screen.game.assets)
            10 -> newModel    = NeptuneModel(screen.game.assets)
            11 -> newModel    = PlutoModel(screen.game.assets)
            12 -> newModel    = AsteroidModel(screen.game.assets)
            13 -> newModel    = CometModel(screen.game.assets)
            14 -> newModel    = SpaceshipModel(screen.game.assets)
            15 -> newModel    = AlienshipModel(screen.game.assets)
        }

        return  newModel!!
    }

}