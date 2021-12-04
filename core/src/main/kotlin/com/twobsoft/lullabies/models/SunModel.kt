package com.twobsoft.lullabies.models

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.lullabies.components.LayerActor
import com.twobsoft.lullabies.LullabiesGame


class SunModel: Entity() {

    companion object {
        const val backgroundTex     = "planets/sun/background.png"
        const val starsTex          = "planets/sun/stars.png"
        const val planetTex         = "planets/sun/planet.png"
        const val lavaTex           = "planets/sun/lava.png"
        const val protuberanceTex   = "planets/sun/protuberance.png"
        const val plan2Tex          = "planets/sun/2plan.png"
        const val plan1Tex          = "planets/sun/1plan.png"
        const val sparksTex         = "planets/sun/iskry.png"
    }

    override val stageNumber = 1

    val background = LayerActor(tex = backgroundTex)

    val stars = LayerActor(tex = starsTex)

    val planet = LayerActor(tex = planetTex).also {
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

    val lava = LayerActor(tex = lavaTex).also {
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

    val protuberance = LayerActor(tex = protuberanceTex).also {
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
                    Actions.scaleBy(0.12f, 0.12f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.scaleBy(-0.12f, -0.12f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                )
            )
        )
    }

    val sparks = LayerActor(tex = sparksTex).also {
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