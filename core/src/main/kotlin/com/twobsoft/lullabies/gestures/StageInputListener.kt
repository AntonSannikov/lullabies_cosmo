package com.twobsoft.lullabies.gestures

import com.badlogic.gdx.LifecycleListener
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Intersector.isPointInPolygon
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Timer
import com.twobsoft.lullabies.*
import com.twobsoft.lullabies.components.LayerActor
import com.twobsoft.lullabies.models.*
import com.twobsoft.lullabies.hud.HudActor
import com.twobsoft.lullabies.components.LayerGroup
import com.twobsoft.lullabies.hud.HudGroup
import com.twobsoft.lullabies.utils.Utils

class StageInputListener(val screen: MainScreen): MyGestureListener.DirectionListener {

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
        screen.hudModel.play.changeAtlas(
            TextureAtlas(screen.game.assets.skeletonLoader.resolve("hud/play/skeletons.atlas")),
            screen.game.assets.skeletonLoader.resolve("hud/play/play.json")
        )
        screen.hudModel.play.setPos(MainScreen.BG_WIDTH / 2, MainScreen.BG_HEIGHT * 0.1f)
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
            changeStage(0, true)
            if (screen.game.serviceApi.isPlaying) {
                screen.hudModel.play.changeAtlas(
                    TextureAtlas(screen.game.assets.skeletonLoader.resolve("hud/pause/skeletons.atlas")),
                    screen.game.assets.skeletonLoader.resolve("hud/pause/pause.json")
                )
                screen.hudModel.play.setPos(MainScreen.BG_WIDTH * 0.494f, MainScreen.BG_HEIGHT * 0.1065f)
            } else {
                screen.hudModel.play.changeAtlas(
                    TextureAtlas(screen.game.assets.skeletonLoader.resolve("hud/play/skeletons.atlas")),
                    screen.game.assets.skeletonLoader.resolve("hud/play/play.json")
                )
                screen.hudModel.play.setPos(MainScreen.BG_WIDTH / 2, MainScreen.BG_HEIGHT * 0.1f)
            }
        }


    }


    override fun onLeft() {
        println("")
        if (screen.currentStageNumber == screen.STAGES_COUNT
            || screen.isSwiping
            || screen.currentStageNumber == 0) {
            return
        }
        changeStage(1, isCallback)
    }


    override fun onRight() {
        if (screen.currentStageNumber == 1
            || screen.isSwiping
            || screen.currentStageNumber == 0) {
            return
        }
        changeStage(-1, isCallback)
    }


    fun onLoop() {
        screen.isLooping = !screen.isLooping
        screen.game.serviceApi.setLooping(screen.isLooping)
    }


    override fun onTap(x: Float, y: Float, count: Int, button: Int) {

        println("${x / MainScreen.BG_WIDTH}:${1 - y / MainScreen.BG_HEIGHT}")
        val xNorm = x / MainScreen.BG_WIDTH
        val yNorm = y / MainScreen.BG_HEIGHT

        if (screen.isMenu && screen.isMenuTappable) {
            for (spineActor in screen.menuModel.all) {
                if (spineActor.hitBox.size > 2 &&
                    isPointInPolygon(Utils.floatArrayToVec2Array(spineActor.hitBox.toFloatArray()),
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
                        isPointInPolygon(Utils.floatArrayToVec2Array(hudActor.hitBox.toFloatArray()),
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
                    isPointInPolygon(Utils.floatArrayToVec2Array(spine.hitBox.toFloatArray()),
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


    // =============================================================================================
    //                                          HUD
    fun refreshHud() {
        var length = screen.stage.actors.size - 1
        var i = 0
        while (i < length) {
            val actor = screen.stage.actors[i]
            if (actor is HudGroup) {
                actor.actions.clear()
                actor.remove()
                length --
            } else { i++ }
        }

        for (hudActor in screen.hudModel.all) {
            if (hudActor is HudGroup) {
                hudActor.children.forEach { it as HudActor
                    if (it.text != "") {
                        it.changeText(MediaPlayer.playlist[screen.currentStageNumber-1]!!)
                    }
                }
            }
            screen.stage.addActor(hudActor)
        }
        screen.isHud = true
        screen.isHudTapable = true
    }


    fun addRollingHud() {

        val startingScale = Vector2(MainScreen.BG_WIDTH * 0.0005f, MainScreen.BG_HEIGHT * 0.0005f)
        val targetScale = Vector2(-MainScreen.BG_WIDTH * 0.0005f, -MainScreen.BG_HEIGHT * 0.0005f)
        screen.isHudTapable = false
        screen.isMenuTappable = false
        screen.hudModel.all.forEach {
            it.y = -MainScreen.BG_HEIGHT * 0.3f
            it.x = -MainScreen.BG_WIDTH * 0.2f
            it.scaleBy(startingScale.x, startingScale.y)
            screen.stage.addActor(it)
            it.addAction(
                Actions.parallel(
                    Actions.scaleBy(targetScale.x, targetScale.y, 0.8f),
                    Actions.moveBy(MainScreen.BG_WIDTH * 0.2f, MainScreen.BG_HEIGHT * 0.3f, 0.8f)
                )
            )
        }
    }


    fun rollbackHud() {
        screen.stage.actors.forEach {
            if (it is HudGroup) {
                it.addAction(
                    Actions.sequence(
                        Actions.delay(0.3f),
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
        screen.isHud = false
        screen.isHudTapable = false
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
                                    screen.isInverseShading = true
                                    isHudRollbacks = true
                                    rollbackHud()
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
        screen.stage.addActor(screen.menuModel.radar)
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
        refreshHud()
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
            screen.hudModel.play.changeAtlas(
                TextureAtlas(screen.game.assets.skeletonLoader.resolve("hud/pause/skeletons.atlas")),
                screen.game.assets.skeletonLoader.resolve("hud/pause/pause.json")
            )
            screen.hudModel.play.setPos(MainScreen.BG_WIDTH * 0.494f, MainScreen.BG_HEIGHT * 0.1065f)
        }


        for (actor in screen.stage.actors) {
            if (actor is LayerActor) { actor.isNeedRemove = true }
            if (actor is LayerGroup) { actor.isNeedRemove = true }
        }

        val newStageModel = getStageModel(screen.currentStageNumber)

        createSwipeStage(newStageModel)

        if (screen.currentStageNumber != 0) {
            refreshHud()
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