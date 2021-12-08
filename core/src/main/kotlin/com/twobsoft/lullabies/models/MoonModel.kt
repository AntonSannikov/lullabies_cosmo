package com.twobsoft.lullabies.models

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.lullabies.Assets
import com.twobsoft.lullabies.components.LayerActor
import com.twobsoft.lullabies.LullabiesGame
import com.twobsoft.lullabies.MainScreen
import com.twobsoft.lullabies.components.LayerGroup


class MoonModel(val assets: Assets): Entity() {

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
        val all = arrayOf(
            backgroundTex, planetsTex, zemlyaTex, star1Tex, star2Tex, star3Tex,
            goryTex, lunaTex, flagTex
        )
    }


    override val stageNumber = 5

    val background  = LayerActor(tex = backgroundTex, texture = assets.getAsset(backgroundTex))
    val moonGroup = LayerGroup()

    val planets = LayerActor(tex = planetsTex, texture = assets.getAsset(planetsTex))

    val zemlya = LayerActor(tex = zemlyaTex, texture = assets.getAsset(zemlyaTex)).also {
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

    val star1 = LayerActor(tex = star1Tex, texture = assets.getAsset(star1Tex)).also {
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

    val star2 = LayerActor(tex = star2Tex, texture = assets.getAsset(star2Tex)).also {
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

    val star3 = LayerActor(tex = star3Tex, texture = assets.getAsset(star3Tex))


    val gory = LayerActor(tex = goryTex, texture = assets.getAsset(goryTex)).also {
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

    val luna = LayerActor(tex = lunaTex, texture = assets.getAsset(lunaTex))

    val flag = LayerActor(
        tex = flagTex,
        texture = assets.getAsset(flagTex),
        cWidth = MainScreen.BG_WIDTH * 0.15f,
        cHeight = MainScreen.BG_HEIGHT * 0.15f,
        cX = MainScreen.BG_WIDTH * 0.28f,
        cY = MainScreen.BG_HEIGHT * 0.08f + MainScreen.layerHeight * 0.24f
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