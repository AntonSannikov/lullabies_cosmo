package com.twobsoft.babymozartspacetrip.hud



import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
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
import com.twobsoft.babymozartspacetrip.components.LayerActor
import com.twobsoft.babymozartspacetrip.components.SpineComponent
import com.twobsoft.babymozartspacetrip.gestures.StageInputListener
import com.twobsoft.babymozartspacetrip.models.Entity
import com.twobsoft.babymozartspacetrip.utils.Utils
import ktx.scene2d.vis.visTextTooltip


class HudGroup: Group() {
    var stageZIndex = 0
}


class HudModel(val assets: Assets, val appListener: StageInputListener): Entity() {

    companion object {
        const val frameTex = "hud/planka_mod.png"
        // upper panel
        const val panelUpTex = "hud/panel_up.png"
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
            deckTex, clockTex,
//            optionsFlareTex, shareFlareTex
        )

        val allSkeletons = arrayOf(
            "hud/joystick/skeletons.atlas", "hud/joystick/joystick.json",
            "hud/left/skeletons.atlas", "hud/left/json.json",
            "hud/right/skeletons.atlas", "hud/right/json.json",
            "hud/play/play.atlas", "hud/play/play.json",
        )

        var sideFramePadding    = 0f
        var layerWidth          = 0f
        var layerHeight         = 0f
        var layerXPosition      = 0f
        var layerYPosition      = 0f

    }

    override val background = LayerActor(panelUpTex, texture = assets.getAsset(panelUpTex))

    override val stageNumber = -1

    val timeScale = 0.5f

    val frame = HudActor(
        tex = frameTex,
        actorTexture = assets.getAsset(frameTex),
        isOriginSize = true
    ).also {
        it.x = (MainScreen.BG_WIDTH - it.width) / 2
        it.y = (MainScreen.BG_HEIGHT - it.height) / 2
        it.originX = it.width / 2
        it.originY = it.height / 2
        var scale = Vector2()
        if (MainScreen.isTablet) {
            scale = Utils.getScale(1.1f, 1.5f, it.width, it.height)
            it.scaleBy(scale.x, scale.y)
        } else {
            scale = Utils.getScale(1.38f, 1.5f, it.width, it.height)
            it.scaleBy(scale.x, scale.y)
        }

        sideFramePadding    = 276 * (1 + scale.x) - (it.width * (1 + scale.x) - MainScreen.BG_WIDTH) / 2
        layerWidth          = MainScreen.BG_WIDTH - sideFramePadding*1.9f
        layerHeight         = layerWidth * 100 / 64
        layerXPosition      = (MainScreen.BG_WIDTH - layerWidth) / 2
        layerYPosition      = (MainScreen.BG_HEIGHT - layerHeight) / 1.5f
    }

    // upper panel
    val panelUp = HudActor(
        tex = panelUpTex,
        text = MediaPlayer.playlist[appListener.screen.currentStageNumber-1]!!,
        actorTexture = assets.getAsset(panelUpTex),
        isOriginSize = true
    ).also {

        it.textX = MainScreen.BG_WIDTH * 0.25f
        it.textY = (MainScreen.BG_HEIGHT - it.textHeight) / 0.5f

        it.textBound = MainScreen.BG_WIDTH * 0.17f
    }

    val options = HudActor(tex = optionsTex,
        actorTexture = assets.getAsset(optionsTex)).also {
        it.scaleBy(-0.84f, -0.88f)
        it.x = -MainScreen.BG_WIDTH * 0.425f
        it.y = MainScreen.BG_HEIGHT * 0.435f
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
            flareToButton(it.x, it.y, Vector2(-0.84f, -0.88f), optionsFlareTex)
            appListener.hudHandler.onLoop()
        }
    }

    fun flareToButton(_x: Float, _y: Float, scaling: Vector2, _tex: String, isSwicthable: Boolean=true, ) {
        if (isSwicthable) {
            if (appListener.screen.isLooping) {
                appListener.screen.stage.actors.forEach {
                    if (it is HudActor && it.tex == _tex) {
                        it.addAction(Actions.removeActor())
                    }
                }
            } else {
                val actor = HudActor(
                    tex = _tex,
                    actorTexture = Texture(Gdx.files.internal(_tex))
                ).also {
                    it.x = _x
                    it.y = _y
                    it.scaleBy(scaling.x, scaling.y)
                }
                appListener.screen.stage.addActor(actor)
            }
        } else {
            val actor = HudActor(
                tex = _tex,
                actorTexture = Texture(Gdx.files.internal(_tex))
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
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.441f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.828f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.441f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.932f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.551f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.932f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.551f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.828f)

        val map = hashMapOf<String, Float>()
        map["scaleBy"] = -0.01f
        map["duration"] = 0.2f
        it.interActions.add(map)

        it.tapHandler = { appListener.toggleNightMode() }
    }

    val shareButton = HudActor(tex = shareButtonTex,
        actorTexture = assets.getAsset(shareButtonTex)).also {
        it.scaleBy(-0.85f, -0.905f)
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
            flareToButton(it.x, it.y, Vector2(-0.85f, -0.905f), shareFlareTex, false)
            appListener.share()
        }
    }

    val shareAntennas = HudActor(tex = shareAntennasTex,
        actorTexture = assets.getAsset(shareAntennasTex)).also {
        it.scaleBy(-0.85f, -0.9f)
        it.y = MainScreen.BG_HEIGHT * 0.367f
        it.x = MainScreen.BG_WIDTH * 0.422f
    }

    val upperPanelGroup = HudGroup().also {
        it.stageZIndex = 0

        panelUp.stageZIndex         = 0
        frame.stageZIndex           = 1
        lampLight.stageZIndex       = 2
        options.stageZIndex         = 3
        lamp.stageZIndex            = 4
        shareButton.stageZIndex     = 5
        shareAntennas.stageZIndex   = 6

        it.addActor(panelUp)
        it.addActor(frame)
        it.addActor(lampLight)
        it.addActor(options)
        it.addActor(lamp)
        it.addActor(shareButton)
        it.addActor(shareAntennas)
    }

    // deck -------------------------------------------------------------
    val deck = HudActor(
        tex = deckTex,
        actorTexture = assets.getAsset(deckTex),
        isOriginSize = true
    ).also {
        it.x = (MainScreen.BG_WIDTH - it.width) / 2
        it.y = MainScreen.BG_HEIGHT * 0.07f
        it.originX = it.width / 2
        it.originY = it.height / 2
        val scale = Utils.getScale(0.997f, 0.2f, it.width, it.height)
        it.scaleBy(scale.x, scale.y)
    }

    val joystick = SpineComponent(
        TextureAtlas(assets.skeletonLoader.resolve("hud/joystick/skeletons.atlas")),
        assets.skeletonLoader.resolve("hud/joystick/joystick.json"),
        MainScreen.BG_HEIGHT / 2500,
        isAlwaysAnimated = false
    ).also {
        it.stageNumber = 13
        val x = MainScreen.BG_WIDTH * 0.21f
        val y = MainScreen.BG_HEIGHT * 0.11f
        it.setPos(x, y)
        it.setTimeScale(timeScale)

        // 1
        it.hitBox.add(x - MainScreen.BG_WIDTH * 0.061f)
        it.hitBox.add(y + MainScreen.BG_HEIGHT * 0.02f)
        // 2
        it.hitBox.add(x - MainScreen.BG_WIDTH * 0.074f)
        it.hitBox.add(y + MainScreen.BG_HEIGHT * 0.123f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.261f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.235f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.258f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.161f)
        it.tapHandler = {
            it.state.setAnimation(0, "animation", false)
            appListener.startMenuTransition()
        }
    }

    val play = SpineComponent(
        TextureAtlas(assets.skeletonLoader.resolve("hud/play/play.atlas")),
        assets.skeletonLoader.resolve("hud/play/play.json"),
        MainScreen.BG_HEIGHT / 2460,
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
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.196f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.614f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.196f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.588f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.121f)

        it.tapHandler = {
            it.state.setAnimation(0, "animation", false)
            val timer = Timer()
            timer.scheduleTask(object : Timer.Task() {
                override fun run() {
                    if (!appListener.screen.game.serviceApi.isPlaying) {
                        Gdx.app.postRunnable {
                            it.state.clearTrack(0)
                            it.skeleton.setToSetupPose()
                        }
                    }
                }
            }, 0.4f)

            MediaPlayer.play(appListener.screen.currentStageNumber)
        }
    }

    val arrowL = SpineComponent(
        TextureAtlas(assets.skeletonLoader.resolve("hud/left/skeletons.atlas")),
        assets.skeletonLoader.resolve("hud/left/json.json"),
        MainScreen.BG_HEIGHT / 2300,
        isAlwaysAnimated = false
    ).also {
        it.stageNumber = 13
        it.setPos(MainScreen.BG_WIDTH * 0.31f, MainScreen.BG_HEIGHT * 0.07f)
        it.setTimeScale(timeScale)

        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.246f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.091f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.240f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.144f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.313f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.160f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.371f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.157f)
        // 5
        it.hitBox.add(MainScreen.BG_WIDTH * 0.394f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.1f)

        it.tapHandler = {
            it.state.setAnimation(0, "animation", false)
            appListener.onRight()
        }
    }

    val arrowR = SpineComponent(
        TextureAtlas(assets.skeletonLoader.resolve("hud/right/skeletons.atlas")),
        assets.skeletonLoader.resolve("hud/right/json.json"),
        MainScreen.BG_HEIGHT / 2300,
        isAlwaysAnimated = false
    ).also {
        it.stageNumber = 13
        it.setPos(MainScreen.BG_WIDTH * 0.69f, MainScreen.BG_HEIGHT * 0.072f)
        it.setTimeScale(timeScale)

        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.595f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.094f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.619f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.15f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.671f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.157f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.757f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.133f)
        // 5
        it.hitBox.add(MainScreen.BG_WIDTH * 0.744f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.096f)

        it.tapHandler = {
            it.state.setAnimation(0, "animation", false)
            appListener.onLeft()
        }
    }

    val clock = HudActor(tex = clockTex,
        actorTexture = assets.getAsset(clockTex)).also {
        it.scaleBy(-0.74f, -0.84f)
        it.x = MainScreen.BG_WIDTH * 0.3f
        it.y = -MainScreen.BG_HEIGHT * 0.275f
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.69f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.163f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.710f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.268f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.853f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.268f)
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
        it.stageZIndex = 1
        deck.stageZIndex    = 0
        clock.stageZIndex   = 1

        it.addActor(deck)
        it.addActor(clock)
        it.moveBy(0f, -MainScreen.BG_HEIGHT * 0.03f)
    }


    override val all = arrayOf<Actor>(
        upperPanelGroup,
        deckGroup,
    )

    val allSkeletons = arrayOf(joystick, play, arrowL, arrowR)

}