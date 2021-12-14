package com.twobsoft.babymozartspacetrip.splash

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.babymozartspacetrip.Assets
import com.twobsoft.babymozartspacetrip.MainScreen
import com.twobsoft.babymozartspacetrip.components.LayerActor
import com.twobsoft.babymozartspacetrip.models.Entity
import ktx.assets.getAsset

class SplashScreenModel(val assets: Assets) : Entity() {

    companion object {
        const val backgroundTex = "splash/background.jpg"
        const val star1Tex = "splash/stars1.png"
        const val star2Tex = "splash/stars2.png"
        const val star3Tex = "splash/stars3.png"
        const val titleTex = "splash/title.png"
        const val cometBotTex = "splash/comet_bot.png"
        const val cometUpTex = "splash/comet_up.png"
        val all = arrayOf(backgroundTex, star1Tex, star2Tex, star3Tex, titleTex, cometBotTex, cometUpTex)

    }

    override val stageNumber = -1

    val padding = (MainScreen.BG_HEIGHT - (MainScreen.BG_WIDTH * 100 / 64)) / 2

    val background = LayerActor(
        tex = backgroundTex,
        texture = assets.manager.getAsset(backgroundTex),
        cY = 0.1f
    )

    val star1 = LayerActor(
        tex = star1Tex,
        texture = assets.manager.getAsset(star1Tex)
    ).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.rotateBy(360f, 50f)
            )
        )
    }

    val star2 = LayerActor(
        tex = star2Tex,
        texture = assets.manager.getAsset(star2Tex)
    ).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.rotateBy(360f, 100f)
            )
        )
    }

    val star3 = LayerActor(
        tex = star3Tex,
        texture = assets.manager.getAsset(star3Tex)
    ).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.rotateBy(-360f, 150f)
            )
        )
    }

    val title = LayerActor(
        tex = titleTex,
        texture = assets.manager.getAsset(titleTex),
        cWidth = MainScreen.BG_WIDTH * 0.8f,
        cHeight = MainScreen.BG_WIDTH * 0.8f,
        cY = MainScreen.BG_HEIGHT / 2 - MainScreen.BG_WIDTH * 0.4f + padding / 4,
        cX = MainScreen.BG_WIDTH / 2 - MainScreen.BG_WIDTH * 0.4f
    ).also {
        it.originX = it.cWidth/2
        it.originY = it.cHeight/2
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.scaleBy(0.1f, 0.1f, 5f, Interpolation.fade),
                    Actions.scaleBy(-0.1f, -0.1f, 5f, Interpolation.fade),
                )
            )
        )
    }

    val cometBot = LayerActor(
        tex = cometBotTex,
        texture = assets.manager.getAsset(cometBotTex),
        cWidth = MainScreen.BG_WIDTH * 0.4f,
        cHeight = MainScreen.BG_HEIGHT * 0.2f,
        cY = padding + MainScreen.BG_HEIGHT * 0.3f,
        cX = MainScreen.BG_WIDTH / 2
    ).also {
        it.isSplashOrbit = true
        it.splashOrbitY = 0.35f
    }

    val cometUp = LayerActor(
        tex = cometUpTex,
        texture = assets.manager.getAsset(cometUpTex),
        cWidth = MainScreen.BG_WIDTH * 0.4f,
        cHeight = MainScreen.BG_HEIGHT * 0.2f,
        cY = MainScreen.BG_HEIGHT * 0.8f - MainScreen.BG_HEIGHT * 0.1f,
        cX = MainScreen.BG_WIDTH * 0.8f
    ).also {
        it.isSplashOrbit = true
        it.splashAngleDelta = 1f
        it.angleOffset = -115f
        it.splashOrbitX = 0.53f
        it.splashOrbitY = 0.27f
    }


    override val all = arrayOf<Actor>(background, star1, star2, star3, cometBot, cometUp, title)


}