package com.twobsoft.lullabies.models

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.lullabies.LullabiesGame
import com.twobsoft.lullabies.components.LayerActor

class SpaceshipModel: Entity() {

    companion object {
        const val backgroundTex     = "planets/spaceship/background.png"
        const val earthTex          = "planets/spaceship/zemlya.png"
        const val plan3Tex          = "planets/spaceship/3plan.png"
        const val plan2Tex          = "planets/spaceship/2plan.png"
        const val plan1Tex          = "planets/spaceship/1plan.png"
    }

    override val stageNumber = 14

    val background = LayerActor(
        tex = backgroundTex,
        isRepeated = true,
    ).also {
        it.xDelta = -1
        it.yDelta = -1
    }

    val earth = LayerActor(tex = earthTex).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(0.07f, 0.07f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.scaleBy(-0.07f, -0.07f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    ),
                    Actions.rotateBy(360f, LullabiesGame.ANIMATION_TIME * 20f, Interpolation.fade)
                )
            )
        )
    }

    val plan3         = LayerActor(tex = plan3Tex)
    val plan2         = LayerActor(tex = plan2Tex)
    val plan1         = LayerActor(tex = plan1Tex)

    override val all = arrayOf<Actor>(background, earth, plan3, plan2, plan1)
}