package com.twobsoft.lullabies.models


import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.lullabies.Assets
import com.twobsoft.lullabies.GameComponent
import com.twobsoft.lullabies.LullabiesGame
import com.twobsoft.lullabies.MainScreen
import ktx.actors.alpha


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

    val cloud1 = GameComponent(tex = cloud1Tex).also {
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

    val cloud2 = GameComponent(tex = cloud2Tex).also {
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

    val cloud3 = GameComponent(tex = cloud3Tex).also {
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

    val sky = GameComponent(tex = skyTex)

    val rock = GameComponent(tex = rockTex).also {
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

    val river = GameComponent(tex = riverTex).also {
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

    val forest = GameComponent(tex = forestTex).also {
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

    val smoke = GameComponent(tex = smokeTex).also {
        it.width = MainScreen.BG_WIDTH
        it.scaleX = 1.5f
        it.height = MainScreen.BG_HEIGHT * 0.45f
        it.y = MainScreen.BG_HEIGHT * 0.14f
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(-MainScreen.BG_WIDTH * 3f, 0f, 200f),
                    Actions.run { it.x = MainScreen.BG_WIDTH * 1.7f }
                )
            )
        )
    }


    val coast = GameComponent(tex = coastTex).also {
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

    val treerock = GameComponent(tex = treerockTex).also {
        it.originX = MainScreen.BG_WIDTH / 2
        it.originY = MainScreen.BG_HEIGHT
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.parallel(
                        Actions.scaleBy(0.6f, 0.6f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.moveBy(-MainScreen.BG_WIDTH * 0.1f, MainScreen.BG_HEIGHT * 0.1f,
                            LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    ),
                    Actions.parallel(
                        Actions.scaleBy(-0.6f, -0.6f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.moveBy(MainScreen.BG_WIDTH * 0.1f, -MainScreen.BG_HEIGHT * 0.1f,
                            LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    )
                )
            )
        )
    }

    val leaf = GameComponent(
        tex = leafTex
    )

    override val all = arrayOf(sky, cloud2, rock, cloud3, cloud1, river, forest, smoke, coast, treerock)

}