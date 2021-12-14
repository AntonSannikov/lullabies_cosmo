package com.twobsoft.lullabies.hud


import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.lullabies.Assets
import com.twobsoft.lullabies.MainScreen
import com.twobsoft.lullabies.MediaPlayer
import com.twobsoft.lullabies.gestures.StageInputListener
import com.twobsoft.lullabies.models.Entity


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
        const val playlistTex = "hud/playlist.png"
        const val joystickTex = "hud/joystick.png"
        const val arrowLTex = "hud/arrowL.png"
        const val playTex = "hud/play.png"
        const val clockTex = "hud/clock.png"
        const val arrowRTex = "hud/arrowR.png"

        val all = arrayOf(
            frameTex, panelUpTex, optionsTex, lampLightTex, lampTex, shareButtonTex, shareAntennasTex,
            deckTex, playlistTex, joystickTex, arrowLTex, playTex, clockTex, arrowRTex
        )

    }

    override val stageNumber = -1

    val frame = HudActor(tex = frameTex,
        actorTexture = assets.getAsset(frameTex)).also {
        if (MainScreen.BG_WIDTH > 1600) {
            it.scaleBy(0.072f, 0.0524f)
            it.y = MainScreen.BG_HEIGHT * 0.02f
        } else {
            it.y = MainScreen.BG_HEIGHT * 0.025f
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
        it.y = MainScreen.BG_HEIGHT * 0.448f
        it.x = MainScreen.BG_WIDTH * 0.422f
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

    val playlist = HudActor(tex = playlistTex,
        actorTexture = assets.getAsset(playlistTex)).also {
        it.scaleBy(-0.8f, -0.9f)
        it.x = -MainScreen.BG_WIDTH * 0.33f
        it.y = -MainScreen.BG_HEIGHT * 0.35f
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.084f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.123f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.092f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.19f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.217f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.196f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.238f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.129f)
    }

    val joystick = HudActor(
        tex = joystickTex,
        actorTexture = assets.getAsset(joystickTex),
    ).also {
        it.scaleBy(-0.86f, -0.88f)
        it.x = -MainScreen.BG_WIDTH * 0.21f
        it.y = -MainScreen.BG_HEIGHT * 0.3f
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.238f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.168f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.24f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.255f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.347f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.259f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.34f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.17f)

        val map = hashMapOf<String, Float>()
        map["scaleBy"] = -0.01f
        map["duration"] = 0.2f
        it.interActions.add(map)


        it.tapHandler = { appListener.startMenuTransition() }
    }

    val play = HudActor(tex = playTex,
        actorTexture = assets.getAsset(playTex)).also {
        it.scaleBy(-0.7f, -0.88f)
        it.x = MainScreen.BG_WIDTH * 0.02f
        it.y = -MainScreen.BG_HEIGHT * 0.34f
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.402f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.136f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.381f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.208f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.642f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.208f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.608f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.134f)

        it.tapHandler = { MediaPlayer.play(appListener.screen.currentStageNumber) }
    }

    val arrowL = HudActor(tex = arrowLTex,
        actorTexture = assets.getAsset(arrowLTex)).also {
        it.scaleBy(-0.78f, -0.92f)
        it.x = -MainScreen.BG_WIDTH * 0.18f
        it.y = -MainScreen.BG_HEIGHT * 0.38f
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.233f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.102f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.253f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.15f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.374f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.155f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.408f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.1f)

        it.tapHandler = { appListener.onRight() }
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
    }

    val arrowR = HudActor(tex = arrowRTex,
        actorTexture = assets.getAsset(arrowRTex)).also {
        it.scaleBy(-0.78f, -0.92f)
        it.x = MainScreen.BG_WIDTH * 0.2f
        it.y = -MainScreen.BG_HEIGHT * 0.38f
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.603f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.104f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.629f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.15f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.710f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.153f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.776f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.139f)
        // 5
        it.hitBox.add(MainScreen.BG_WIDTH * 0.755f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.106f)

        it.tapHandler = { appListener.onLeft() }
    }

    val deckGroup = HudGroup().also {
        it.addActor(deck)
        it.addActor(playlist)
        it.addActor(joystick)
        it.addActor(play)
        it.addActor(arrowL)
        it.addActor(clock)
        it.addActor(arrowR)
    }


    override val all = arrayOf<Actor>(
        upperPanelGroup,
        deckGroup,
    )

}