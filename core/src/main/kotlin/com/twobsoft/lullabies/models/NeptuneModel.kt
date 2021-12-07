package com.twobsoft.lullabies.models

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.lullabies.LullabiesGame
import com.twobsoft.lullabies.components.LayerActor

class NeptuneModel: Entity() {

    companion object {
        const val backgroundTex     = "planets/neptune/background.png"
        const val plan3Tex          = "planets/neptune/3plan.png"
        const val plan2Tex          = "planets/neptune/2plan.png"
        const val plan1Tex          = "planets/neptune/1plan.png"
    }

    override val stageNumber = 10

    val background = LayerActor(tex = backgroundTex).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(50f,25f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.moveBy(-20f,25f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.moveBy(10f, -30f,  LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.moveBy(-40f, -20f,  LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                ),
            )
        )
    }

    val plan3 = LayerActor(tex = plan3Tex).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.scaleBy(0.05f, 0.05f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.scaleBy(-0.05f, -0.05f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                )
            )
        )
    }

    val plan2 = LayerActor(tex = plan2Tex).also {
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

    val plan1 = LayerActor(tex = plan1Tex).also {
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

    override val all = arrayOf<Actor>(background, plan3, plan2, plan1)
}