package com.twobsoft.babymozartspacetrip.splash

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.babymozartspacetrip.Assets
import com.twobsoft.babymozartspacetrip.MainScreen
import com.twobsoft.babymozartspacetrip.MainScreen.Companion.ratio
import com.twobsoft.babymozartspacetrip.components.LayerActor
import com.twobsoft.babymozartspacetrip.models.Entity
import com.twobsoft.babymozartspacetrip.utils.Utils
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

    override val background = LayerActor(
        tex = backgroundTex,
        texture = assets.manager.getAsset(backgroundTex),
    ).also {
        it.width = MainScreen.BG_WIDTH
        it.height = MainScreen.BG_HEIGHT
    }

    val star1 = LayerActor(
        tex = star1Tex,
        texture = assets.manager.getAsset(star1Tex),
    ).also {
        it.width = MainScreen.BG_WIDTH
        it.height = MainScreen.BG_HEIGHT
        it.originX = it.width / 2
        it.originY = it.height / 2
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
        it.width = MainScreen.BG_WIDTH
        it.height = MainScreen.BG_HEIGHT
        it.originX = it.width / 2
        it.originY = it.height / 2
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
        it.width = MainScreen.BG_WIDTH
        it.height = MainScreen.BG_HEIGHT
        it.originX = it.width / 2
        it.originY = it.height / 2
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
        isOriginalSize = true,
    ).also {
        val scale = Utils.getScale(0.7f, 0.7f * ratio, it.width, it.height)
        it.width = it.srcWidth + it.srcWidth*scale.x
        it.height = it.srcHeight + it.srcHeight*scale.y
        it.x = (MainScreen.BG_WIDTH - it.width) / 3
        it.y = (MainScreen.BG_HEIGHT - it.height) / 2
        it.originX = it.width / 2
        it.originY = it.height / 2

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
        isOriginalSize = true,
    ).also {
        it.originX = it.width / 2
        it.originY = it.height / 2
        val scale = Utils.getScale(0.4f, 0.2f, it.width, it.height)
        it.scaleBy(scale.x, scale.y)
        it.isSplashOrbit = true
        it.splashOrbitY = 0.35f
    }

    val cometUp = LayerActor(
        tex = cometUpTex,
        texture = assets.manager.getAsset(cometUpTex),
        isOriginalSize = true,
    ).also {
        it.originX = it.width / 2
        it.originY = it.height / 2
        val scale = Utils.getScale(0.3f, 0.2f, it.width, it.height)
        it.scaleBy(scale.x, scale.y)
        it.isSplashOrbit = true
        it.splashAngleDelta = 1f
        it.angleOffset = -117f
        it.splashOrbitX = 0.53f
        it.splashOrbitY = 0.27f
    }


    override val all = arrayOf<Actor>(background, star1, star2, star3, cometBot, cometUp, title)


}