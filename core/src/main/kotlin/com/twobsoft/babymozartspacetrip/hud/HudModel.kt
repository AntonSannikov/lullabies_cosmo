package com.twobsoft.babymozartspacetrip.hud



import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.badlogic.gdx.utils.Timer
import com.twobsoft.babymozartspacetrip.Assets
import com.twobsoft.babymozartspacetrip.MainScreen
import com.twobsoft.babymozartspacetrip.MediaPlayer
import com.twobsoft.babymozartspacetrip.components.SpineComponent
import com.twobsoft.babymozartspacetrip.gestures.StageInputListener
import com.twobsoft.babymozartspacetrip.models.Entity


class HudGroup: Group()


class HudModel(val assets: Assets, val appListener: StageInputListener): Entity() {

    companion object {
        const val frameTex = "hud/frame_mod.png"
        // upper panel
        const val panelUpTex = "hud/panel_back.png"
        const val optionsTex = "hud/options.png"
        const val lampLightTex = "hud/lamp_light.png"
        const val lampTex = "hud/lamp.png"
        const val shareButtonTex = "hud/share_button.png"
        const val shareAntennasTex = "hud/share_antennas.png"
        // deck
        const val deckTex = "hud/deck.png"
        const val clockTex = "hud/clock.png"
        // flares
        const val optionsFlareTex = "hud/loop_flare.png"
        const val shareFlareTex = "hud/share_flare.png"


        val all = arrayOf(
            frameTex, panelUpTex, optionsTex, lampLightTex, lampTex, shareButtonTex, shareAntennasTex,
            deckTex, clockTex, optionsFlareTex, shareFlareTex
        )

        val allSkeletons = arrayOf(
            "hud/joystick/skeletons.atlas", "hud/joystick/joystick.json",
            "hud/left/skeletons.atlas", "hud/left/json.json",
            "hud/right/skeletons.atlas", "hud/right/json.json",
            "hud/play/skeletons.atlas", "hud/play/play.json",
            "hud/pause/skeletons.atlas", "hud/pause/pause.json",
        )

        var playButton: SpineComponent?=null
    }


    override val stageNumber = -1

    val timeScale = 0.5f

    val frame = HudActor(tex = frameTex,
        actorTexture = assets.getAsset(frameTex)).also {
        if (MainScreen.BG_WIDTH > 1600) {
            it.scaleBy(0.072f, 0.0524f)
            it.y = MainScreen.BG_HEIGHT * 0.02f
        } else {
            it.y = MainScreen.BG_HEIGHT * 0.03f
            it.scaleBy(0.436f, 0.001f)
        }
    }

    // upper panel
    val panelUp = HudActor(
        tex = panelUpTex,
        text = MediaPlayer.playlist[appListener.screen.currentStageNumber-1]!!,
        actorTexture = assets.getAsset(panelUpTex)
    ).also {

        if (MainScreen.BG_WIDTH > 1600) {
            it.y = MainScreen.BG_HEIGHT * 0.442f
            it.scaleBy(-MainScreen.BG_WIDTH * 0.00018f, -MainScreen.BG_HEIGHT * 0.00036f)
        } else {
            it.y = MainScreen.BG_HEIGHT * 0.448f
            it.scaleBy(-0.26f, -0.92f)
        }
        it.textX = MainScreen.BG_WIDTH * 0.25f
        if (MainScreen.BG_WIDTH > 1600) {
            it.textY = MainScreen.BG_HEIGHT * 0.973f
        } else {
            it.textY = MainScreen.BG_HEIGHT * 0.971f
        }
        it.textBound = MainScreen.BG_WIDTH * 0.17f
    }

    val options = HudActor(tex = optionsTex,
        actorTexture = assets.getAsset(optionsTex)).also {
        it.scaleBy(-0.85f, -0.88f)
        it.y = MainScreen.BG_HEIGHT * 0.437f
        it.x = -MainScreen.BG_WIDTH * 0.425f
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.009f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.915f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.015f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.995f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.136f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.996f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.141f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.915f)

        it.tapHandler = {
            flareToButton(it.x, it.y, Vector2(-0.85f, -0.88f), optionsFlareTex)
            appListener.onLoop()
        }
    }

    fun flareToButton(_x: Float, _y: Float, scaling: Vector2, tex: String, isSwicthable: Boolean=true, ) {
        if (isSwicthable) {
            if (appListener.screen.isLooping) {
                appListener.screen.stage.actors.forEach {
                    if (it is HudActor && it.tex == tex) {
                        it.addAction(Actions.removeActor())
                    }
                }
            } else {
                val actor = HudActor(
                    tex = tex,
                    actorTexture = assets.getAsset(tex)
                ).also {
                    it.x = _x
                    it.y = _y
                    it.scaleBy(scaling.x, scaling.y)
                }
                appListener.screen.stage.addActor(actor)
            }
        } else {
            val actor = HudActor(
                tex = tex,
                actorTexture = assets.getAsset(tex)
            ).also {
                it.x = _x
                it.y = _y
                it.scaleBy(scaling.x, scaling.y)
            }
            appListener.screen.stage.addActor(actor)

            val timer  = Timer()
            timer.scheduleTask(object : Timer.Task() {
                override fun run() {
                    actor.remove()
                }
            }, 0.1f)
        }
    }

    val lampLight = HudActor(tex = lampLightTex,
        actorTexture = assets.getAsset(lampLightTex)).also {
        it.scaleBy(-0.2f, -0.8f)
        it.y = MainScreen.BG_HEIGHT * 0.37f
        it.x = MainScreen.BG_WIDTH * 0.019f
        it.addAction(
            Actions.repeat(RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.scaleBy(0.15f, 0.15f, 5f, Interpolation.fade),
                    Actions.scaleBy(-0.15f, -0.15f, 5f, Interpolation.fade)
                )
            )
        )
    }

    val lamp = HudActor(tex = lampTex,
        actorTexture = assets.getAsset(lampTex)).also {
        it.scaleBy(-0.88f, -0.9f)
        if (MainScreen.BG_WIDTH > 1600) {
            it.y = MainScreen.BG_HEIGHT * 0.395f
        } else {
            it.y = MainScreen.BG_HEIGHT * 0.388f
        }
    }

    val shareButton = HudActor(tex = shareButtonTex,
        actorTexture = assets.getAsset(shareButtonTex)).also {
        it.scaleBy(-0.85f, -0.9f)
        it.x = MainScreen.BG_WIDTH * 0.422f
        it.y = MainScreen.BG_HEIGHT * 0.448f
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.846f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.91f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.851f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.996f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.982f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.998f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.984f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.909f)

        it.tapHandler = {
            flareToButton(it.x, it.y, Vector2(-0.85f, -0.9f), shareFlareTex, false)
        }
    }

    val shareAntennas = HudActor(tex = shareAntennasTex,
        actorTexture = assets.getAsset(shareAntennasTex)).also {
        it.scaleBy(-0.85f, -0.9f)
        it.y = MainScreen.BG_HEIGHT * 0.367f
        it.x = MainScreen.BG_WIDTH * 0.422f
    }

    val upperPanelGroup = HudGroup().also {
        it.addActor(panelUp)
        it.addActor(frame)
        it.addActor(lampLight)
        it.addActor(options)
        it.addActor(lamp)
        it.addActor(shareButton)
        it.addActor(shareAntennas)
    }

    // deck -------------------------------------------------------------
    val deck = HudActor(tex = deckTex,
        actorTexture = assets.getAsset(deckTex)).also {
        it.y = -MainScreen.BG_HEIGHT * 0.335f
        it.scaleBy( 0.03f, -0.81f)
    }

    val joystick = SpineComponent(
        TextureAtlas(assets.skeletonLoader.resolve("hud/joystick/skeletons.atlas")),
        assets.skeletonLoader.resolve("hud/joystick/joystick.json"),
        MainScreen.BG_HEIGHT / 2620,
        isAlwaysAnimated = false
    ).also {
        it.stageNumber = 13
        val x = MainScreen.BG_WIDTH * 0.21f
        val y = MainScreen.BG_HEIGHT * 0.13f
        it.setPos(x, y)
        it.setTimeScale(timeScale)

        // 1
        it.hitBox.add(x - MainScreen.BG_WIDTH * 0.061f)
        it.hitBox.add(y + MainScreen.BG_HEIGHT * 0.02f)
        // 2
        it.hitBox.add(x - MainScreen.BG_WIDTH * 0.074f)
        it.hitBox.add(y + MainScreen.BG_HEIGHT * 0.123f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.246f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.253f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.258f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.161f)
        it.tapHandler = {
            it.state.setAnimation(0, "animation", false)
            appListener.startMenuTransition()
        }
    }

    val play = SpineComponent(
        TextureAtlas(assets.skeletonLoader.resolve("hud/play/skeletons.atlas")),
        assets.skeletonLoader.resolve("hud/play/play.json"),
        MainScreen.BG_HEIGHT / 2620,
        isAlwaysAnimated = false
    ).also {
        it.stageNumber = 13
        it.setPos(MainScreen.BG_WIDTH / 2, MainScreen.BG_HEIGHT * 0.1f)
        it.setTimeScale(timeScale)

        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.402f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.121f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.381f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.193f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.642f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.193f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.608f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.121f)

        it.tapHandler = {
            
            if (appListener.screen.game.serviceApi.isPlaying) {
                it.changeAtlas(
                    TextureAtlas(assets.skeletonLoader.resolve("hud/play/skeletons.atlas")),
                    assets.skeletonLoader.resolve("hud/play/play.json")
                )
                it.setPos(MainScreen.BG_WIDTH / 2, MainScreen.BG_HEIGHT * 0.1f)
            } else {
                it.changeAtlas(
                    TextureAtlas(assets.skeletonLoader.resolve("hud/pause/skeletons.atlas")),
                    assets.skeletonLoader.resolve("hud/pause/pause.json")
                )
                it.setPos(MainScreen.BG_WIDTH * 0.494f, MainScreen.BG_HEIGHT * 0.1065f)
            }

            it.state.setAnimation(0, "animation", false)

            MediaPlayer.play(appListener.screen.currentStageNumber)
        }
    }

    val arrowL = SpineComponent(
        TextureAtlas(assets.skeletonLoader.resolve("hud/left/skeletons.atlas")),
        assets.skeletonLoader.resolve("hud/left/json.json"),
        MainScreen.BG_HEIGHT / 2382,
        isAlwaysAnimated = false
    ).also {
        it.stageNumber = 13
        it.setPos(MainScreen.BG_WIDTH * 0.3f, MainScreen.BG_HEIGHT * 0.08f)
        it.setTimeScale(timeScale)

        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.185f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.102f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.205f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.15f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.326f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.155f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.360f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.1f)

        it.tapHandler = {
            it.state.setAnimation(0, "animation", false)
            appListener.onRight()
        }
    }

    val arrowR = SpineComponent(
        TextureAtlas(assets.skeletonLoader.resolve("hud/right/skeletons.atlas")),
        assets.skeletonLoader.resolve("hud/right/json.json"),
        MainScreen.BG_HEIGHT / 2382,
        isAlwaysAnimated = false
    ).also {
        it.stageNumber = 13
        it.setPos(MainScreen.BG_WIDTH * 0.7f, MainScreen.BG_HEIGHT * 0.08f)
        it.setTimeScale(timeScale)

        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.65f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.102f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.653f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.155f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.712f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.153f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.789f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.143f)
        // 5
        it.hitBox.add(MainScreen.BG_WIDTH * 0.786f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.102f)

        it.tapHandler = {
            it.state.setAnimation(0, "animation", false)
            appListener.onLeft()
        }
    }


    val clock = HudActor(tex = clockTex,
        actorTexture = assets.getAsset(clockTex)).also {
        it.scaleBy(-0.76f, -0.84f)
        it.x = MainScreen.BG_WIDTH * 0.3f
        it.y = -MainScreen.BG_HEIGHT * 0.29f
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.69f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.163f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.710f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.282f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.853f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.282f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.841f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.137f)

        val map = hashMapOf<String, Float>()
        map["scaleBy"] = -0.01f
        map["duration"] = 0.1f
        it.interActions.add(map)

        it.tapHandler = { appListener.screen.game.serviceApi.createTimer() }

    }


    val deckGroup = HudGroup().also {
        it.addActor(deck)
        it.addActor(clock)
    }


    override val all = arrayOf<Actor>(
        upperPanelGroup,
        deckGroup,
    )

    val allSkeletons = arrayOf(joystick, play, arrowL, arrowR)

}