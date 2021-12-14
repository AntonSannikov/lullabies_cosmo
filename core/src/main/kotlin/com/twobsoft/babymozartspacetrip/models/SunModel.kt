package com.twobsoft.babymozartspacetrip.models

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.babymozartspacetrip.Assets
import com.twobsoft.babymozartspacetrip.components.LayerActor
import com.twobsoft.babymozartspacetrip.LullabiesGame


class SunModel(val assets: Assets): Entity() {

    companion object {
        const val backgroundTex     = "planets/sun/background.png"
        const val starsTex          = "planets/sun/stars.png"
        const val planetTex         = "planets/sun/planet.png"
        const val lavaTex           = "planets/sun/lava.png"
        const val protuberanceTex   = "planets/sun/protuberance.png"
        const val plan2Tex          = "planets/sun/2plan.png"
        const val plan1Tex          = "planets/sun/1plan.png"
        const val sparksTex         = "planets/sun/iskry.png"
        val all = arrayOf(
            backgroundTex, starsTex, planetTex, lavaTex, protuberanceTex, plan2Tex, plan1Tex,
            sparksTex
        )
    }

    override val stageNumber = 1

    val background = LayerActor(tex = backgroundTex, texture = assets.getAsset(backgroundTex))

    val stars = LayerActor(
        tex = starsTex,
        isRepeated = true,
        texture = assets.getAsset(starsTex)
    ).also {
        it.xDelta = 2
        it.yDelta = -1
    }

    val planet = LayerActor(tex = planetTex, texture = assets.getAsset(planetTex)).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.scaleBy(0.07f, 0.07f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.scaleBy(-0.07f, -0.07f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                )
            )
        )
    }

    val lava = LayerActor(tex = lavaTex, texture = assets.getAsset(lavaTex)).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.scaleBy(0.04f, 0.04f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.scaleBy(-0.04f, -0.04f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                )
            )
        )
    }

    val protuberance = LayerActor(tex = protuberanceTex, texture = assets.getAsset(protuberanceTex)).also {
        it.actions.add(
            Actions.parallel(
                Actions.repeat(
                    RepeatAction.FOREVER,
                    Actions.sequence(
                        Actions.scaleBy(0.06f, 0.06f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.scaleBy(-0.06f, -0.06f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    )
                ),
                Actions.repeat(
                    RepeatAction.FOREVER,
                    Actions.sequence(
                        Actions.moveBy(-20f, -20f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.moveBy(20f, 20f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    )
                ),
                Actions.repeat(
                    RepeatAction.FOREVER,
                    Actions.sequence(
                        Actions.rotateBy(2f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.rotateBy(-2f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    )
                )

            )

        )
    }

    val plan2 = LayerActor(tex = plan2Tex, texture = assets.getAsset(plan2Tex)).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.scaleBy(0.06f, 0.06f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.scaleBy(-0.06f, -0.06f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                )
            )
        )
    }

    val plan1 = LayerActor(tex = plan1Tex, texture = assets.getAsset(plan1Tex)).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.scaleBy(0.12f, 0.12f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.scaleBy(-0.12f, -0.12f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                )
            )
        )
    }

    val sparks = LayerActor(tex = sparksTex, texture = assets.getAsset(sparksTex)).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(-100f, -100f, LullabiesGame.ANIMATION_TIME - 2f, Interpolation.fade),
                    Actions.moveBy(0f, 0f, LullabiesGame.ANIMATION_TIME - 2f, Interpolation.fade),
                    )

            )
        )
    }


    override val all = arrayOf<Actor>(background, stars, planet, lava, protuberance, plan2, plan1, sparks)
}