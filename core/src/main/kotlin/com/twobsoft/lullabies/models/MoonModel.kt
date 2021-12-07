package com.twobsoft.lullabies.models

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.lullabies.components.LayerActor
import com.twobsoft.lullabies.LullabiesGame
import com.twobsoft.lullabies.MainScreen
import com.twobsoft.lullabies.components.LayerGroup


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

    val background  = LayerActor(tex = backgroundTex)
    val moonGroup = LayerGroup()

    val planets = LayerActor(tex = planetsTex)

    val zemlya = LayerActor(tex = zemlyaTex).also {
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

    val star1 = LayerActor(tex = star1Tex).also {
        it.originY = MainScreen.BG_HEIGHT * 0.7f
        it.originX = MainScreen.BG_HEIGHT * 0.3f
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.rotateBy(10f, LullabiesGame.ANIMATION_TIME),
                    Actions.rotateBy(-10f, LullabiesGame.ANIMATION_TIME),
                )

            )
        )
    }

    val star2 = LayerActor(tex = star2Tex).also {
        it.originY = MainScreen.BG_HEIGHT * 0.7f
        it.originX = MainScreen.BG_HEIGHT * 0.3f
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.rotateBy(6f, LullabiesGame.ANIMATION_TIME),
                    Actions.rotateBy(-6f, LullabiesGame.ANIMATION_TIME),
                )

            )
        )
    }

    val star3 = LayerActor(tex = star3Tex)


    val gory = LayerActor(tex = goryTex).also {
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

    val luna = LayerActor(tex = lunaTex)

    val flag = LayerActor(
        tex = flagTex,
        cWidth = MainScreen.BG_WIDTH * 0.15f,
        cHeight = MainScreen.BG_HEIGHT * 0.15f,
        cX = MainScreen.BG_WIDTH * 0.3f,
        cY = MainScreen.BG_HEIGHT * 0.25f
    )

    init {
        moonGroup.addActor(luna)
        moonGroup.addActor(flag)
        moonGroup.actions.add(
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



    override val all = arrayOf<Actor>(background, star1, star2, star3, planets, zemlya, gory, moonGroup)
}