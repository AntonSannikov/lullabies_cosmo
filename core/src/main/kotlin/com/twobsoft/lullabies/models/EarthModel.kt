package com.twobsoft.lullabies.models


import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.lullabies.components.LayerActor
import com.twobsoft.lullabies.LullabiesGame
import com.twobsoft.lullabies.MainScreen


class EarthModel: Entity() {
    companion object {
        const val cloud1Tex     = "planets/earth/cloud1.png"
        const val cloud2Tex     = "planets/earth/cloud2.png"
        const val cloud3Tex     = "planets/earth/cloud3.png"
        const val skyTex        = "planets/earth/sky.png"
        const val rockTex       = "planets/earth/rock.png"
        const val riverTex      = "planets/earth/river.png"
        const val forestTex     = "planets/earth/forest.png"
        const val coastTex      = "planets/earth/coast.png"
        const val smokeTex      = "planets/earth/smoke.png"
        const val treerockTex   = "planets/earth/treerock.png"
        const val leafTex       = "planets/earth/leaf.png"
    }

    override val stageNumber = 4

    val cloud1 = LayerActor(tex = cloud1Tex).also {
        it.xOffset = (MainScreen.BG_WIDTH * 0.5f).toInt()
        it.isNeedReinit = true

        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(-MainScreen.BG_WIDTH, 0f, 100f),
                    Actions.moveBy(MainScreen.BG_WIDTH * 2.2f, 0f),
                )
            )
        )
    }

    val cloud2 = LayerActor(tex = cloud2Tex).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(-MainScreen.BG_WIDTH, 0f, 160f),
                    Actions.moveBy(MainScreen.BG_WIDTH * 2f, 0f, ),
                )
            )
        )
    }

    val cloud3 = LayerActor(tex = cloud3Tex).also {

        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(-MainScreen.BG_WIDTH, 0f, 200f),
                    Actions.moveBy(MainScreen.BG_WIDTH * 2f, 0f, ),
                )
            )
        )
    }

    val sky = LayerActor(tex = skyTex)

    val rock = LayerActor(tex = rockTex).also {
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

    val river = LayerActor(tex = riverTex).also {
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

    val forest = LayerActor(tex = forestTex).also {
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

    val smoke = LayerActor(
        tex = smokeTex,
        cHeight = MainScreen.BG_HEIGHT * 0.3f,
        cY = MainScreen.BG_HEIGHT * 0.32f
    ).also {
        it.scaleBy(0.2f)
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(-MainScreen.BG_WIDTH, 0f, 100f),
                    Actions.run { it.x = MainScreen.BG_WIDTH * 1.2f }
                )
            )
        )
    }


    val coast = LayerActor(tex = coastTex).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.scaleBy(0.15f, 0.15f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.scaleBy(-0.15f, -0.15f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                )
            )
        )
    }

    val treerock = LayerActor(tex = treerockTex).also {
        it.originX = MainScreen.BG_WIDTH / 2
        it.originY = MainScreen.BG_HEIGHT
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(0.6f, 0.6f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.scaleBy(-0.6f, -0.6f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.moveBy(-MainScreen.BG_WIDTH * 0.1f, MainScreen.BG_HEIGHT * 0.4f,
                            LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.moveBy(MainScreen.BG_WIDTH * 0.1f, -MainScreen.BG_HEIGHT * 0.4f,
                            LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    )
                )
            )
        )
    }

    val leaf = LayerActor(
        tex = leafTex
    )

    override val all = arrayOf<Actor>(sky, cloud2, rock, cloud3, cloud1, river, forest, smoke, coast, treerock)

}