package com.twobsoft.lullabies.models


import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.lullabies.GameComponent
import com.twobsoft.lullabies.LullabiesGame
import com.twobsoft.lullabies.MainScreen
import ktx.math.random


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

    val background = GameComponent(tex = backgroundTex)

    val oblako5 = GameComponent(tex = oblako5Tex).also {

        it.xOffset = -MainScreen.BG_WIDTH.toInt()
        it.isNeedReinit = true

        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(MainScreen.BG_WIDTH, 0f, 30f),
                    Actions.moveBy(-MainScreen.BG_WIDTH, 0f)
                )
            )
        )
    }

    val oblako4  = GameComponent(tex = oblako4Tex).also {

        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(-MainScreen.BG_WIDTH, 0f, 40f),
                    Actions.moveBy(MainScreen.BG_WIDTH, 0f)
                )
            )
        )
    }

    val oblako3 = GameComponent(tex = oblako3Tex).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(MainScreen.BG_WIDTH, 0f, 28f),
                    Actions.moveBy(-MainScreen.BG_WIDTH, 0f)
                )
            )
        )
    }

    val oblako2 = GameComponent(tex = oblako2Tex).also {
        it.xOffset = (MainScreen.BG_WIDTH * 0.2).toInt()
        it.isNeedReinit = true
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(-MainScreen.BG_WIDTH, 0f, 50f),
                    Actions.moveBy(MainScreen.BG_WIDTH, 0f)
                )
            )
        )
    }

    val oblako1 = GameComponent(tex = oblako1Tex).also {
        it.xOffset = (MainScreen.BG_WIDTH * 0.2).toInt()
        it.isNeedReinit = true
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(-MainScreen.BG_WIDTH, 0f, 42f),
                    Actions.moveBy(MainScreen.BG_WIDTH, 0f)
                )
            )
        )
    }

    val plan3 = GameComponent(tex = plan3Tex).also {
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
                    Actions.scaleBy(0.09f, 0.09f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.scaleBy(-0.09f, -0.09f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                )
            )
        )
    }


    override val all = arrayOf(
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