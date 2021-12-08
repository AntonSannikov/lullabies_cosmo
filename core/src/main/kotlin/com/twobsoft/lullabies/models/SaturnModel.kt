package com.twobsoft.lullabies.models

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.lullabies.Assets
import com.twobsoft.lullabies.LullabiesGame
import com.twobsoft.lullabies.components.LayerActor

class SaturnModel(val assets: Assets): Entity() {

    companion object {
        const val backgroundTex     = "planets/saturn/background.png"
        const val sunTex            = "planets/saturn/sun.png"
        const val ringTex           = "planets/saturn/ring.png"
        const val plan2Tex          = "planets/saturn/2plan.png"
        const val plan1Tex          = "planets/saturn/1plan.png"
        val all = arrayOf(
            backgroundTex, sunTex, ringTex, plan2Tex, plan1Tex
        )
    }

    override val stageNumber = 8

    val background = LayerActor(tex = backgroundTex, texture = assets.getAsset(backgroundTex)).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.rotateBy(1f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.rotateBy(-1f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                )
            )
        )
    }

    val sun = LayerActor(tex = sunTex, texture = assets.getAsset(sunTex)).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.scaleBy(0.03f, 0.03f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.scaleBy(-0.03f, -0.03f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                )
            )
        )
    }

    val ring = LayerActor(tex = ringTex, texture = assets.getAsset(ringTex)).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(0.05f, 0.05f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.scaleBy(-0.05f, -0.05f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.moveBy(100f, 100f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.moveBy(-100f, -100f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    ),
                )

            )
        )
    }

    val plan2 = LayerActor(tex = plan2Tex, texture = assets.getAsset(plan2Tex)).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.scaleBy(0.08f, 0.08f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.scaleBy(-0.08f, -0.08f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
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

    override val all = arrayOf<Actor>(background, sun, ring, plan2, plan1)
}