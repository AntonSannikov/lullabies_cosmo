package com.twobsoft.babymozartspacetrip.gestures

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Intersector.isPointInPolygon
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.badlogic.gdx.utils.Timer
import com.twobsoft.babymozartspacetrip.HudHandler
import com.twobsoft.babymozartspacetrip.MainScreen
import com.twobsoft.babymozartspacetrip.MediaPlayer
import com.twobsoft.lullabies.*
import com.twobsoft.babymozartspacetrip.components.LayerActor
import com.twobsoft.babymozartspacetrip.hud.HudActor
import com.twobsoft.babymozartspacetrip.components.LayerGroup
import com.twobsoft.babymozartspacetrip.hud.HudGroup
import com.twobsoft.babymozartspacetrip.hud.HudModel
import com.twobsoft.babymozartspacetrip.models.*
import com.twobsoft.babymozartspacetrip.utils.Utils
import kotlinx.coroutines.awaitAll
import ktx.scene2d.actors

class StageInputListener(
    val screen: MainScreen,
    val hudHandler: HudHandler
    ): MyGestureListener.DirectionListener {

    var isCallback = false
    var isBackground = false

    // callbacks from notification media buttons
    fun initCallbacks() {
        screen.game.serviceApi.initPlayCallback(::toPlay)
        screen.game.serviceApi.initPauseCallback(::toPause)
        screen.game.serviceApi.initPreviousCallback(::toPrevious)
        screen.game.serviceApi.initNextCallback(::toNext)
        screen.game.serviceApi.initOnPauseCallback(::onAppPause)
        screen.game.serviceApi.initOnResumeCallback(::onAppResume)
    }


    fun toPause() {
        Gdx.app.postRunnable {
            screen.hudModel.play.state.timeScale = 20f
            screen.hudModel.play.state.setAnimation(0, "animation", false)
            screen.hudModel.play.state.timeScale = 0.5f
        }
    }


    fun toPlay() {}


    fun toNext() {
        isCallback = true
        onLeft()
        isCallback = false
    }


    fun toPrevious() {
        isCallback = true
        onRight()
        isCallback = false
    }


    fun onAppPause() {
        isBackground = true
    }


    fun onAppResume() {
        isBackground = false
        if (screen.currentStageNumber != 0) {
            Gdx.app.postRunnable {
                changeStage(0, true)
                if (screen.game.serviceApi.isPlaying) {
                    screen.hudModel.play.state.timeScale = 20f
                    screen.hudModel.play.state.setAnimation(0, "animation", false)
                    val timer = Timer()
                    timer.scheduleTask(object : Timer.Task() {
                        override fun run() {
                            screen.hudModel.play.state.timeScale = 0.5f
                        }
                    }, 0.1f)
                } else {
                    screen.hudModel.play.state.clearTrack(0)
                    screen.hudModel.play.skeleton.setToSetupPose()
                }
            }
        }
    }


    override fun onLeft() {
        println("")
        if (screen.isSwiping || screen.currentStageNumber == 0) { return }
        if (screen.currentStageNumber == screen.AVAILABLE_STAGES) {
            screen.currentStageNumber = 1
            changeStage(0, isCallback)
        } else {
            changeStage(1, isCallback)
        }

    }


    override fun onRight() {
        if (screen.isSwiping || screen.currentStageNumber == 0) { return }
        if (screen.currentStageNumber == 1) {
            screen.currentStageNumber = screen.AVAILABLE_STAGES
            changeStage(0, isCallback)
        } else {
            changeStage(-1, isCallback)
        }
    }


    override fun onTap(x: Float, y: Float, count: Int, button: Int) {

        println("${x / MainScreen.BG_WIDTH}:${1 - y / MainScreen.BG_HEIGHT}")
        val xNorm = x / MainScreen.BG_WIDTH
        val yNorm = y / MainScreen.BG_HEIGHT

        if (screen.isMenu && screen.isMenuTappable) {
            for (spineActor in screen.menuModel.all) {
                if (spineActor.hitBox.size > 2 &&
                    isPointInPolygon(
                        Utils.floatArrayToVec2Array(spineActor.hitBox.toFloatArray()),
                        Vector2(x, MainScreen.BG_HEIGHT - y))
                ) {
                    spineActor.isTransitionAnimation = true
//                    addRollingHud()
                    screen.shaderFocusOffset = Vector2(-(xNorm-0.5f),yNorm-0.5f)
                    screen.isBarrel = true
                    screen.isInterStellar = true
                    val timer = Timer()
                    timer.scheduleTask(object : Timer.Task() {
                        override fun run() {
                            spineActor.isTransitionAnimation = false
                            screen.isShade = true
                            screen.isBarrel = false
                            screen.isMenu = false
                            screen.resetBarrelShader()
                            screen.isInterStellar = false
                            screen.resetInterstellarShader()
                            screen.currentStageNumber = spineActor.stageNumber
                            createStage(getStageModel(spineActor.stageNumber))
                        }
                    }, 0.8f)
                    break
                }
            }
        }


        if (!screen.isHudTapable) return

        var hudTapHandler : (() -> Unit)? = null

        // HUD ACTORS
        //
        for (actor in screen.stage.actors) {
            if (actor is HudGroup) {
                for (child in actor.children) {
                    val hudActor = child as HudActor
                    if (hudActor.hitBox.size > 2 &&
                        isPointInPolygon(
                            Utils.floatArrayToVec2Array(hudActor.hitBox.toFloatArray()),
                            Vector2(x, MainScreen.BG_HEIGHT - y))
                    )
                    {
                        hudActor.getActionsFromMap().forEach {interaction->
                            hudActor.addAction(interaction)
                        }

                        hudTapHandler = hudActor.tapHandler
                        break
                    }
                }
            }
            if (hudTapHandler != null) {
                break
            }
        }

        if (hudTapHandler == null) {
            for (spine in screen.hudModel.allSkeletons) {
                if (spine.hitBox.size > 2 &&
                    isPointInPolygon(
                        Utils.floatArrayToVec2Array(spine.hitBox.toFloatArray()),
                        Vector2(x, MainScreen.BG_HEIGHT - y))
                )    {
                    hudTapHandler = spine.tapHandler
                    break
                }
            }

        }
        //
        // HUD ACTORS

        hudTapHandler?.invoke()
    }

    override fun onUp() {}
    override fun onDown() {}
    override fun onPan(x: Float, y: Float, deltaX: Float, deltaY: Float) {}


    fun clearStage() {
        var length = screen.stage.actors.size
        while (0 < length) {
            screen.stage.actors[0].remove()
            length--
        }
        screen.stage.actors.clear()
    }


    fun toggleNightMode() {
        if (!MainScreen.isNightMode) {
            screen.nightShaderTime = 1.25f
            MainScreen.isNightMode = true
            screen.stage.actors.forEach {
                if (it is HudGroup) {
                    var i = 0
                    for (actor in it.children) {
                        if (actor is HudActor && actor.tex == "hud/lamp_light.png") {
                            actor.addAction(Actions.removeActor())
                            break
                        }
                        i++
                    }
                } else if (it is LayerActor && it.tex.contains("background.png")) {
                    it.changeBackground(
                        "planets/sleep.jpg",
                        screen.game.assets.getAsset("planets/sleep.jpg")
                    )
                }
            }
        } else {
            MainScreen.isNightMode = false
            screen.nightShaderTime = 0.875f
            screen.isNightShader = true
            for (actor in screen.stage.actors) {
                if (actor is LayerActor && actor.tex.contains("sleep.jpg")) {
                    val background = getStageModel(screen.currentStageNumber).background
                    actor.changeBackground(background.tex, background.texture)
                    break
                }
            }
            hudHandler.addLampLight()
        }
    }



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
                                    screen.isInverseShading = true
                                    isHudRollbacks = true
                                    hudHandler.rollbackHud()
                                }
                            }
                        ),
                        Actions.scaleBy(-0.2f, -0.2f, 1.2f, Interpolation.slowFast)
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
        screen.menuModel = MenuSpineModel(screen.game.assets)
        screen.stage.addActor(screen.menuModel.background)
        screen.stage.addActor(screen.menuModel.stars)
        screen.stage.addActor(screen.menuModel.radar)

        screen.stage.actors.forEach {
            if (it is LayerActor && it.tex == "menu/stars.png") {
                it.addAction(Actions.repeat(
                    RepeatAction.FOREVER,
                    Actions.rotateBy(360f, 220f),
                ))
            }
        }

        screen.isMenu = true
        screen.currentStageNumber = 0
        screen.isInverseShading = false
    }

    //                                    BACK TO MENU
    // =============================================================================================


    fun createStage(stageModel: Entity) {

        screen.currentStageNumber = stageModel.stageNumber

        if (screen.game.serviceApi.isPlaying) {
            screen.game.serviceApi.playMusic(screen.currentStageNumber, true)
        } else {
            screen.game.serviceApi.isNeedNewPlay = true
        }

        screen.stage.actors.forEach {
            if (it is LayerActor) it.isNeedRemove = true
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
        }

        refreshStage()
        hudHandler.refreshHud()
    }


    fun createSwipeStage(stageModel: Entity) {

        val currentActors = arrayListOf<Actor>()
        currentActors.addAll(screen.stage.actors)

        stageModel.all.forEach {
            screen.stage.addActor(it)
            if ((it is LayerActor && !it.isNeedReposition) || it is LayerGroup) {
                it.actions.forEach { action -> it.addAction(action) }
            } else if (it is LayerActor && it.isNeedReposition) {
                it.xOffset = (-1..1).random() * (0..MainScreen.BG_WIDTH.toInt()).random()
            }
        }

        var i = 0
        var length = screen.stage.actors.size

        while(i < length) {
            val actor = screen.stage.actors[i]
            if (actor is LayerActor && actor.isNeedRemove) {
                actor.remove()
                length--
            } else if (actor is LayerGroup && actor.isNeedRemove) {
                actor.remove()
                length--
            }
            else {
                i++
            }
        }

        currentActors.forEach {
            screen.stage.addActor(it)
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


    private fun changeStage(increment: Int, callback: Boolean=false) {
        screen.currentStageNumber += increment

        if (isBackground) return

        if (!callback) {
            screen.isSwiping = true
            MediaPlayer.play(screen.currentStageNumber, true)
            screen.hudModel.play.state.timeScale = 20f
            screen.hudModel.play.state.setAnimation(0, "animation", false)
            val timer = Timer()
            timer.scheduleTask(object : Timer.Task() {
                override fun run() {
                    screen.hudModel.play.state.timeScale = 0.5f
                }
            }, 0.1f)
        }

        for (actor in screen.stage.actors) {
            if (actor is LayerActor) { actor.isNeedRemove = true }
            if (actor is LayerGroup) { actor.isNeedRemove = true }
        }

        val newStageModel = getStageModel(screen.currentStageNumber)

        createSwipeStage(newStageModel)

        if (screen.currentStageNumber != 0) {
            hudHandler.refreshHud()
        }

        var isReseted = false

        screen.stage.actors.forEach {
            if (it is LayerActor || it is LayerGroup) {
                if (it is LayerActor && it.isOrbit) it.stopAnimation()
                it.addAction(
                    Actions.sequence(
                        Actions.fadeOut(0.5f, Interpolation.linear),
                        Actions.parallel(
                            Actions.fadeIn(0.5f, Interpolation.linear),
                            Actions.run {
                                if (!isReseted) {
                                    isReseted = true
                                    screen.isSwiping = false
                                    refreshStage()
                                }
                            }
                        )
                    )
                )
            }
        }
    }


    private fun getStageModel(stageNumber: Int): Entity {
        var newModel: Entity? =null

        when(stageNumber) {
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