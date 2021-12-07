package com.twobsoft.lullabies.models

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.lullabies.components.LayerActor
import com.twobsoft.lullabies.LullabiesGame


class VenusModel: Entity() {

    companion object {
        const val backgroundTex     = "planets/venus/background.png"
        const val plan4Tex          = "planets/venus/4plan.png"
        const val plan3Tex          = "planets/venus/3plan.png"
        const val plan2Tex          = "planets/venus/2plan.png"
        const val plan1Tex          = "planets/venus/1plan.png"
    }

    override val stageNumber = 3

    val background = LayerActor(tex = backgroundTex)

    val plan4 = LayerActor(tex = plan4Tex).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(0.02f, 0.02f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.scaleBy(-0.02f, -0.02f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.moveBy(50f, 50f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.moveBy(-50f, -50f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    ),
                )
            )
        )
    }

    val plan3 = LayerActor(tex = plan3Tex).also {
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

    val plan2 = LayerActor(tex = plan2Tex).also {
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

    val plan1 = LayerActor(tex = plan1Tex).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.scaleBy(0.1f, 0.1f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.scaleBy(-0.1f, -0.1f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                )
            )
        )
    }


    override val all = arrayOf<Actor>(background, plan4, plan3, plan2, plan1)
}