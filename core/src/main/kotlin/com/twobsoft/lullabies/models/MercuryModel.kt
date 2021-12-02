package com.twobsoft.lullabies.models
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.lullabies.GameComponent
import com.twobsoft.lullabies.LullabiesGame

class MercuryModel: Entity() {

    companion object {
        const val backgroundTex   = "planets/mercury/background.png"
        const val planet1Tex      = "planets/mercury/planet1.png"
        const val planet2Tex      = "planets/mercury/planet2.png"
        const val plan2Tex        = "planets/mercury/2plan.png"
        const val plan1Tex        = "planets/mercury/1plan.png"
    }

    override val stageNumber = 2

    val background = GameComponent(tex = backgroundTex).also {
            it.originX = 0f
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

    val planet1 = GameComponent(tex = planet1Tex).also {
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
    val planet2 = GameComponent(tex = planet2Tex).also {
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

    val plan2 = GameComponent(tex = plan2Tex).also {
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
    val plan1 = GameComponent(tex = plan1Tex).also {
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

    override val all = arrayOf(background, planet1, planet2, plan2, plan1)
}