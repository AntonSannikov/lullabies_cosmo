package com.twobsoft.babymozartspacetrip.models

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.babymozartspacetrip.Assets
import com.twobsoft.babymozartspacetrip.LullabiesGame
import com.twobsoft.babymozartspacetrip.components.LayerActor

class UranusModel(val assets: Assets): Entity() {

    companion object {
        const val backgroundTex     = "planets/uranus/background.png"
        const val plan4Tex          = "planets/uranus/4plan.png"
        const val plan3Tex          = "planets/uranus/3plan.png"
        const val plan2Tex          = "planets/uranus/2plan.png"
        const val plan1Tex          = "planets/uranus/1plan.png"
        val all = arrayOf(backgroundTex, plan4Tex, plan3Tex, plan2Tex, plan1Tex)
    }

    override val stageNumber = 9

    val background = LayerActor(tex = backgroundTex, texture = assets.getAsset(backgroundTex)).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.rotateBy(2f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.rotateBy(-2f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.moveBy(20f,10f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.moveBy(-20f, -10f,  LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    )
                )
            )
        )
    }

    val plan4 = LayerActor(tex = plan4Tex, texture = assets.getAsset(plan4Tex)).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(0.02f, 0.02f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.scaleBy(-0.02f, -0.02f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.moveBy(20f,-30f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.moveBy(-20f, 30f,  LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    )
                )
            )
        )
    }

    val plan3 = LayerActor(tex = plan3Tex, texture = assets.getAsset(plan3Tex)).also {
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

    val plan2 = LayerActor(tex = plan2Tex, texture = assets.getAsset(plan2Tex)).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(0.07f, 0.07f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.scaleBy(-0.07f, -0.07f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.moveBy(30f,30f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.moveBy(-30f, -30f,  LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    )
                )
            )
        )
    }

    val plan1 = LayerActor(tex = plan1Tex, texture = assets.getAsset(plan1Tex)).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(0.09f, 0.09f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.scaleBy(-0.09f, -0.09f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.moveBy(50f,25f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.moveBy(-20f,25f, LullabiesGame.ANIMATION_TIME / 2, Interpolation.fade),
                        Actions.moveBy(10f, -30f,  LullabiesGame.ANIMATION_TIME / 2, Interpolation.fade),
                        Actions.moveBy(-40f, -20f,  LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.rotateBy(2f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.rotateBy(-2f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    ),
                )
            )
        )
    }

    override val all = arrayOf<Actor>(background, plan4, plan3, plan2, plan1)
}