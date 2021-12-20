package com.twobsoft.babymozartspacetrip.gestures

import com.badlogic.gdx.Gdx
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
import com.twobsoft.babymozartspacetrip.menu.MenuSpineModel
import com.twobsoft.babymozartspacetrip.models.*
import com.twobsoft.babymozartspacetrip.utils.Utils

class StageInputListener(
    val screen: MainScreen,
    val hudHandler: HudHandler
    ): MyGestureListener.DirectionListener {

    var isCallback          = false
    var isBackground        = false
    var isAllowPlaying      = false
    var lastStage           = 0

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
            screen.hudModel.play.state.clearTrack(0)
            screen.hudModel.play.skeleton.setToSetupPose()
        }
    }


    fun toPlay() {}


    fun toNext() {
        if (isBackground) isCallback = true
        if (!isCallback) {
            Gdx.app.postRunnable { onLeft() }
        } else onLeft()
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
        isAllowPlaying = true
        if (screen.isSwiping || screen.currentStageNumber == 0) { return }
        if (screen.currentStageNumber == screen.AVAILABLE_STAGES) {
            screen.currentStageNumber = 1
            changeStage(0, isCallback)
        } else {
            changeStage(1, isCallback)
        }

    }


    override fun onRight() {
        isAllowPlaying = true
        if (screen.isSwiping || screen.currentStageNumber == 0) { return }
        if (screen.currentStageNumber == 1) {
            screen.currentStageNumber = screen.AVAILABLE_STAGES
            changeStage(0, isCallback)
        } else {
            changeStage(-1, isCallback)
        }
    }


    override fun onTap(x: Float, y: Float, count: Int, button: Int) {

//        println("${x / MainScreen.BG_WIDTH}:${1 - y / MainScreen.BG_HEIGHT}")
        val xNorm = x / MainScreen.BG_WIDTH
        val yNorm = y / MainScreen.BG_HEIGHT

        if (screen.isMenu && screen.isMenuTappable) {
            for (spineActor in screen.menuModel.all) {
                if (spineActor.hitBox.size > 2 &&
                    isPointInPolygon(
                        Utils.floatArrayToVec2Array(spineActor.hitBox.toFloatArray()),
                        Vector2(x, MainScreen.BG_HEIGHT - y))
                ) {

                    val newStageNumber = spineActor.stageNumber
                    if (newStageNumber >= 10 && screen.game.serviceApi.AVAILABLE_STAGES == 10) {
                        val result = screen.game.adServices.startPurchaseFlow()
                        if (!result) return
                    }

                    spineActor.isTransitionAnimation    = true
                    screen.shaderFocusOffset            = Vector2(-(xNorm-0.5f),yNorm-0.5f)
                    screen.isBarrel                     = true
                    screen.isInterStellar               = true
                    val timer = Timer()
                    timer.scheduleTask(object : Timer.Task() {
                        override fun run() {
                            spineActor.isTransitionAnimation = false
                            screen.isShade  = true
                            screen.isBarrel = false
                            screen.isMenu   = false
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



    fun share() {
        screen.game.serviceApi.share()
    }


    fun toggleNightMode() {
        if (!MainScreen.isNightMode) {
            screen.nightShaderTime  = 1.25f
            MainScreen.isNightMode  = true
            var removed = false
            for (actor in screen.stage.actors) {
                if (actor is HudGroup) {
                    var i = 0
                    for (child in actor.children) {
                        if (child is HudActor && child.tex == "hud/lamp_light.png") {
                            child.addAction(Actions.removeActor())
                            removed = true
                            break
                        }
                        i++
                    }
                    if (removed) break
                }
            }
            var i = 0
            var length = screen.stage.actors.size
            while (i < length) {
                val actor = screen.stage.actors[i]
                if (actor is LayerActor || actor is LayerGroup) {
                    actor.remove()
                    length--
                } else i++
            }
            val sleepBackground = LayerActor(
                tex = "planets/sleep.jpg",
                texture = screen.game.assets.getAsset("planets/sleep.jpg")
            ).also {
                it.width   = MainScreen.BG_WIDTH
                it.height  = MainScreen.BG_HEIGHT
            }
            screen.stage.addActor(sleepBackground)
            sleepBackground.toBack()

        } else {
            MainScreen.isNightMode  = false
            screen.nightShaderTime  = MainScreen.NIGHT_MODE_VALUE / 0.8f
            screen.isNightShader    = true
            for (actor in screen.stage.actors) {
                if (actor is LayerActor && actor.tex.contains("sleep.jpg")) {
                    actor.addAction(Actions.removeActor())
                    break
                }
            }
            createStage(getStageModel(screen.currentStageNumber))
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
        for (spine in screen.menuModel.all) {
            if (spine.stageNumber > screen.game.serviceApi.AVAILABLE_STAGES) {
                spine.setUnavailableColor()
            }
        }
        screen.stage.actors.forEach {
            if (it is LayerActor && it.tex == "menu/stars.png") {
                it.addAction(Actions.repeat(
                    RepeatAction.FOREVER,
                    Actions.rotateBy(360f, 220f),
                ))
            }
        }

        screen.game.adServices.banner(screen.game.serviceApi.AVAILABLE_STAGES != 15)

        screen.isMenu = true
        screen.currentStageNumber = 0
        screen.isInverseShading = false
    }

    //                                    BACK TO MENU
    // =============================================================================================


    fun createStage(stageModel: Entity) {

        screen.currentStageNumber = stageModel.stageNumber

        screen.stage.actors.forEach {
            if (it is LayerActor) it.isNeedRemove = true
            if (it is LayerGroup) it.isNeedRemove = true
        }

        if (!MainScreen.isNightMode) {
            stageModel.all.forEach {
                if (it is LayerActor || it is LayerGroup) {
                    screen.stage.addActor(it)
                    if ((it is LayerActor) || it is LayerGroup) {
                        it.actions.forEach { action ->
                            it.addAction(action)
                        }
                    }
                }
            }
        } else {
            val sleepBackground = LayerActor(
                tex = "planets/sleep.jpg",
                texture = screen.game.assets.getAsset("planets/sleep.jpg")
            ).also {
                it.width   = MainScreen.BG_WIDTH
                it.height  = MainScreen.BG_HEIGHT
            }
            screen.stage.addActor(sleepBackground)
        }

        val array = arrayListOf<Actor>()



        screen.stage.actors.forEach {
            if (it is LayerGroup && it.isNeedRemove) it.addAction(Actions.removeActor())
            if (it is LayerActor && it.isNeedRemove) it.addAction(Actions.removeActor())
        }

        changeStage(0)
    }


    fun createSwipeStage(stageModel: Entity) {

        val currentActors = arrayListOf<Actor>()
        currentActors.addAll(screen.stage.actors)

        stageModel.all.forEach {
            screen.stage.addActor(it)
            if ((it is LayerActor) || it is LayerGroup) {
//                if (it is LayerActor && it.isNeedReposition) {
//                    it.x = ((-1..1).random() * (0..(MainScreen.BG_WIDTH / 2).toInt()).random()).toFloat()
//                }

                it.actions.forEach { action -> it.addAction(action) }
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

        if (screen.currentStageNumber >= 10) {
            screen.game.adServices.checkPurchasesStatus()
        }

        if (isBackground) return

        screen.game.adServices.banner(screen.game.serviceApi.AVAILABLE_STAGES != 15)

        if (!isAllowPlaying) {
            lastStage = screen.currentStageNumber
        }

        var isNeedToRefreshText = true

        if (!callback) {
            screen.isSwiping = true
            if (isAllowPlaying) {
                if (lastStage == screen.currentStageNumber) {
                    screen.game.serviceApi.isNeedNewPlay = false
                    isNeedToRefreshText = false
                } else {
                    screen.game.serviceApi.isNeedNewPlay = true
                    lastStage = screen.currentStageNumber
                    MediaPlayer.play(screen.currentStageNumber, false)
                    screen.hudModel.play.state.timeScale = 20f
                    screen.hudModel.play.state.setAnimation(0, "animation", false)
                    val timer = Timer()
                    timer.scheduleTask(object : Timer.Task() {
                        override fun run() {
                            screen.hudModel.play.state.timeScale = 0.5f
                        }
                    }, 0.1f)
                }
            }
        }

        if (!MainScreen.isNightMode) {
            for (actor in screen.stage.actors) {
                if (actor is LayerActor) { actor.isNeedRemove = true }
                if (actor is LayerGroup) { actor.isNeedRemove = true }
            }
            val newStageModel = getStageModel(screen.currentStageNumber)
            createSwipeStage(newStageModel)
        }

        if (screen.currentStageNumber != 0) {
            hudHandler.refreshHud(isNeedToRefreshText)
        }

        var isReseted = false

        if (!MainScreen.isNightMode) {
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
        } else { screen.isSwiping = false }
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