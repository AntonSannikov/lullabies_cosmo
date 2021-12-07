package com.twobsoft.lullabies.models


import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.lullabies.components.LayerActor
import com.twobsoft.lullabies.LullabiesGame
import com.twobsoft.lullabies.MainScreen


class MarsModel: Entity() {

    companion object {
        const val backgroundTex     = "planets/mars/background.png"
        const val oblako5Tex        = "planets/mars/oblako5.png"
        const val oblako4Tex        = "planets/mars/oblako4.png"
        const val oblako3Tex        = "planets/mars/oblako3.png"
        const val oblako2Tex        = "planets/mars/oblako2.png"
        const val oblako1Tex        = "planets/mars/oblako1.png"
        const val plan3Tex          = "planets/mars/3plan.png"
        const val plan2Tex          = "planets/mars/2plan.png"
        const val plan1Tex          = "planets/mars/1plan.png"
    }

    override val stageNumber = 6

    val background = LayerActor(tex = backgroundTex).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.rotateBy(7f, 20f, Interpolation.fade),
                    Actions.rotateBy(-7f, 20f, Interpolation.fade),
                )
            )
        )
    }

    val oblako5 = LayerActor(tex = oblako5Tex).also {
        it.xOffset = -MainScreen.BG_WIDTH.toInt()
        it.isNeedReinit = true
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(MainScreen.BG_WIDTH * 2, 0f, 60f),
                    Actions.run { it.x = -MainScreen.BG_WIDTH }
                )
            )
        )
    }

    val oblako4  = LayerActor(tex = oblako4Tex).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(MainScreen.BG_WIDTH * 2, 0f, 70f),
                    Actions.run { it.x = -MainScreen.BG_WIDTH }
                )
            )
        )
    }

    val oblako3 = LayerActor(tex = oblako3Tex).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(MainScreen.BG_WIDTH * 2, 0f, 55f),
                    Actions.run { it.x = -MainScreen.BG_WIDTH }
                )
            )
        )
    }

    val oblako2 = LayerActor(tex = oblako2Tex).also {
        it.xOffset = (-MainScreen.BG_WIDTH * 0.2).toInt()
        it.isNeedReinit = true
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(MainScreen.BG_WIDTH * 2, 0f, 40f),
                    Actions.run { it.x = -MainScreen.BG_WIDTH }
                )
            )
        )
    }

    val oblako1 = LayerActor(tex = oblako1Tex).also {
        it.xOffset = (MainScreen.BG_WIDTH * 0.2).toInt()
        it.isNeedReinit = true
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(MainScreen.BG_WIDTH * 2, 0f, 66f),
                    Actions.run { it.x = -MainScreen.BG_WIDTH }
                )
            )
        )
    }

    val plan3 = LayerActor(tex = plan3Tex).also {
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
                    Actions.scaleBy(0.09f, 0.09f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.scaleBy(-0.09f, -0.09f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                )
            )
        )
    }


    override val all = arrayOf<Actor>(
        background,
        oblako5,
        oblako4,
        oblako3,
        oblako2,
        oblako1,
        plan3,
        plan2,
        plan1
    )

}