package com.twobsoft.lullabies.models

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.lullabies.Assets
import com.twobsoft.lullabies.LullabiesGame
import com.twobsoft.lullabies.MainScreen
import com.twobsoft.lullabies.components.LayerActor

class AsteroidModel(val assets: Assets): Entity() {

    companion object {
        const val backgroundTex     = "planets/asteroid/background.png"
        const val plan5Tex          = "planets/asteroid/5plan.png"
        const val plan4Tex          = "planets/asteroid/4plan.png"
        const val plan3Tex          = "planets/asteroid/3plan.png"
        const val plan2Tex          = "planets/asteroid/2plan.png"
        const val plan1Tex          = "planets/asteroid/1plan.png"
        const val flareTex          = "planets/asteroid/svet.png"
        val all = arrayOf(backgroundTex, plan5Tex, plan4Tex, plan3Tex, plan2Tex, plan1Tex, flareTex)
    }

    override val stageNumber = 12

    val background = LayerActor(tex = backgroundTex, texture = assets.getAsset(backgroundTex))

    val plan5 = LayerActor(tex = plan5Tex, texture = assets.getAsset(plan5Tex)).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.scaleBy(0.05f, 0.05f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.scaleBy(-0.05f, -0.05f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                )
            )
        )
    }

    val plan4 = LayerActor(tex = plan4Tex, texture = assets.getAsset(plan4Tex)).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(0.06f, 0.06f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.scaleBy(-0.06f, -0.06f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.moveBy(20f, 20f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.moveBy(-20f, -20f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    ),
                )
            )
        )
    }

    val plan3 = LayerActor(tex = plan3Tex, texture = assets.getAsset(plan3Tex)).also {
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

    val plan2 = LayerActor(
        tex = plan2Tex,
        texture = assets.getAsset(plan2Tex),
        isOrbit = true
    ).also {
        it.orbitRadius = 150f
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(0.08f, 0.08f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.scaleBy(-0.08f, -0.08f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.moveBy(20f,15f, LullabiesGame.ANIMATION_TIME / 2, Interpolation.fade),
                        Actions.moveBy(10f,10f, LullabiesGame.ANIMATION_TIME / 2, Interpolation.fade),
                        Actions.moveBy(-5f, -20f,  LullabiesGame.ANIMATION_TIME / 2, Interpolation.fade),
                        Actions.moveBy(-25f, -5f,  LullabiesGame.ANIMATION_TIME / 2, Interpolation.fade),
                    ),
                )
            )
        )
    }

    val plan1 = LayerActor(
        tex = plan1Tex,
        texture = assets.getAsset(plan1Tex),
        isOrbit = true
    ).also {
        it.orbitRadius = 250f
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.scaleBy(0.12f, 0.12f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.scaleBy(-0.12f, -0.12f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                ),
            )
        )
    }

    val flare1 = LayerActor(
        tex = flareTex,
        texture = assets.getAsset(flareTex),
        cWidth = MainScreen.BG_WIDTH * 0.3f,
        cHeight = MainScreen.BG_HEIGHT * 0.15f,
        cX = MainScreen.BG_WIDTH * 0.2f,
        cY = MainScreen.BG_HEIGHT * 0.376f
    ).also {
        it.originX = it.cWidth / 2
        it.originY = it.cHeight / 2
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(1.2f, 1.2f, LullabiesGame.ANIMATION_TIME / 5, Interpolation.fade),
                        Actions.scaleBy(-1.2f, -1.2f, LullabiesGame.ANIMATION_TIME / 5, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.color(Color.GREEN,LullabiesGame.ANIMATION_TIME / 5, Interpolation.fade),
                        Actions.color(
                            Color(Color.rgba8888(152/255f,254/255f,203/255f, 1f)),
                            LullabiesGame.ANIMATION_TIME / 10, Interpolation.fade)
                    ),
                )
            )
        )
    }

    val flare2 = LayerActor(
        tex = flareTex,
        texture = assets.getAsset(flareTex),
        cWidth = MainScreen.BG_WIDTH * 0.2f,
        cHeight = MainScreen.BG_HEIGHT * 0.1f,
        cX = MainScreen.BG_WIDTH * 0.62f,
        cY = MainScreen.BG_HEIGHT * 0.348f
    ).also {
        it.originX = it.cWidth / 2
        it.originY = it.cHeight / 2
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(1f, 1f, LullabiesGame.ANIMATION_TIME / 2, Interpolation.fade),
                        Actions.scaleBy(-1f, -1f, LullabiesGame.ANIMATION_TIME / 2, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.color(Color.GREEN,LullabiesGame.ANIMATION_TIME / 5, Interpolation.fade),
                        Actions.color(
                            Color(Color.rgba8888(152/255f,254/255f,203/255f, 1f)),
                            LullabiesGame.ANIMATION_TIME / 10, Interpolation.fade)
                    ),
                )
            )
        )
    }

    val flare3 = LayerActor(
        tex = flareTex,
        texture = assets.getAsset(flareTex),
        cWidth = MainScreen.BG_WIDTH * 0.1f,
        cHeight = MainScreen.BG_HEIGHT * 0.05f,
        cX = MainScreen.BG_WIDTH * 0.576f,
        cY = MainScreen.BG_HEIGHT * 0.25f
    ).also {
        it.originX = it.cWidth / 2
        it.originY = it.cHeight / 2
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(1f, 1f, LullabiesGame.ANIMATION_TIME / 10, Interpolation.fade),
                        Actions.scaleBy(-1f, -1f, LullabiesGame.ANIMATION_TIME / 10, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.color(Color.GREEN,LullabiesGame.ANIMATION_TIME / 10, Interpolation.fade),
                        Actions.color(
                            Color(Color.rgba8888(152/255f,254/255f,203/255f, 1f)),
                            LullabiesGame.ANIMATION_TIME / 10, Interpolation.fade)
                    ),
                )
            )
        )
    }



    override val all = arrayOf<Actor>(
        background,
        plan5,
        plan4,
        plan3,
        flare1,
        flare2,
        flare3,
        plan2,
        plan1
    )
}