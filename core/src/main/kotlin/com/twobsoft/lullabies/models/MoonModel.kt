package com.twobsoft.lullabies.models

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.lullabies.GameComponent
import com.twobsoft.lullabies.LullabiesGame
import com.twobsoft.lullabies.MainScreen


class MoonModel: Entity() {

    companion object {
        const val backgroundTex   = "planets/moon/fon.png"
        const val planetsTex      = "planets/moon/planets.png"
        const val zemlyaTex       = "planets/moon/zemlya.png"
        const val star1Tex        = "planets/moon/star1.png"
        const val star2Tex        = "planets/moon/star2.png"
        const val star3Tex        = "planets/moon/star3.png"
        const val goryTex         = "planets/moon/gory.png"
        const val lunaTex         = "planets/moon/luna.png"
        const val flagTex         = "planets/moon/flag.png"
    }
    override val stageNumber = 5

    val background  = GameComponent(tex = backgroundTex)

    val planets = GameComponent(tex = planetsTex)

    val zemlya = GameComponent(tex = zemlyaTex).also {
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

    val star1 = GameComponent(tex = star1Tex).also {
        it.originY = MainScreen.BG_HEIGHT * 0.7f
        it.originX = MainScreen.BG_HEIGHT * 0.3f
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.rotateBy(360f, LullabiesGame.ANIMATION_TIME * 40),
            )
        )
    }

    val star2 = GameComponent(tex = star2Tex).also {
        it.originY = MainScreen.BG_HEIGHT * 0.7f
        it.originX = MainScreen.BG_HEIGHT * 0.3f
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.rotateBy(360f, LullabiesGame.ANIMATION_TIME * 50),
            )
        )
    }

    val star3 = GameComponent(tex = star3Tex)




    val gory = GameComponent(tex = goryTex).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(0.06f, 0.06f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.scaleBy(-0.06f, -0.06f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.moveBy(-MainScreen.BG_HEIGHT * 0.007f, -MainScreen.BG_HEIGHT * 0.01f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.moveBy(MainScreen.BG_HEIGHT * 0.007f, MainScreen.BG_HEIGHT * 0.01f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    )
                )

            )
        )
    }

    val luna = GameComponent(tex = lunaTex).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(0.1f, 0.1f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.scaleBy(-0.1f, -0.1f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.moveBy(-MainScreen.BG_HEIGHT * 0.007f, -MainScreen.BG_HEIGHT * 0.01f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.moveBy(MainScreen.BG_HEIGHT * 0.007f, MainScreen.BG_HEIGHT * 0.01f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    )
                )

            )
        )
    }

    val flag = GameComponent(tex = flagTex).also {
        it.actions.add(Actions.moveBy(MainScreen.BG_WIDTH * 0.3f, MainScreen.BG_HEIGHT * 0.25f))
        it.width = MainScreen.BG_WIDTH * 0.15f
        it.height = MainScreen.BG_HEIGHT * 0.15f

        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(-MainScreen.BG_HEIGHT * 0.007f, -MainScreen.BG_HEIGHT * 0.03f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.moveBy(MainScreen.BG_HEIGHT * 0.007f, MainScreen.BG_HEIGHT * 0.03f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                )
            )
        )
    }

    override val all = arrayOf(background, star1, star2, star3, planets, zemlya, gory, luna, flag)
}