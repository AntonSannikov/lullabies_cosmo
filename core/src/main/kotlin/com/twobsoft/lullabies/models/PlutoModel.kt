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

class PlutoModel(val assets: Assets): Entity() {
    companion object {
        const val backgroundTex     = "planets/pluto/background.png"
        const val sunTex            = "planets/pluto/sun.png"
        const val starsTex          = "planets/pluto/stars.png"
        const val planetTex         = "planets/pluto/planet.png"
        const val plan3Tex          = "planets/pluto/3plan.png"
        const val plan2Tex          = "planets/pluto/2plan.png"
        const val plan1Tex          = "planets/pluto/1plan.png"
        const val flareTex          = "planets/pluto/flare.png"
        val all = arrayOf(
            backgroundTex, sunTex, starsTex, planetTex, plan3Tex,
            plan2Tex, plan1Tex, flareTex
        )
    }

    override val stageNumber = 11

    val background = LayerActor(tex = backgroundTex, texture = assets.getAsset(backgroundTex))

    val sun = LayerActor(tex = sunTex, texture = assets.getAsset(sunTex)).also {
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

    val stars = LayerActor(tex = starsTex, texture = assets.getAsset(starsTex)).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.rotateBy(6f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.rotateBy(-6f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.moveBy(10f,15f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.moveBy(5f,10f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.moveBy(-5f, -20f,  LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.moveBy(-10f, -5f,  LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    ),
                )

            )
        )
    }

    val planet = LayerActor(tex = planetTex, texture = assets.getAsset(planetTex)).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.moveBy(20f,-10f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.moveBy(-20f,10f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.scaleBy(0.05f, 0.05f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.scaleBy(-0.05f, -0.05f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    )
                )
            )
        )

    }

    val plan3 = LayerActor(tex = plan3Tex, texture = assets.getAsset(plan3Tex)).also {
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

    val plan2 = LayerActor(tex = plan2Tex, texture = assets.getAsset(plan2Tex)).also {
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

    val plan1 = LayerActor(tex = plan1Tex, texture = assets.getAsset(plan1Tex)).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.scaleBy(0.1f, 0.1f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.scaleBy(-0.1f, -0.1f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                )
            )
        )
    }

    val flare1 = LayerActor(
        tex = flareTex,
        texture = assets.getAsset(flareTex),
        cWidth = 288f,
        cHeight = 262f,
        cX = MainScreen.BG_WIDTH * 0.0188f - 144,
        cY = MainScreen.BG_HEIGHT * 0.676f - 131
    ).also {
        it.originX = it.cWidth / 2
        it.originY = it.cHeight / 2
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(0.2f, 0.2f, LullabiesGame.ANIMATION_TIME / 12, Interpolation.fade),
                        Actions.scaleBy(-0.2f, -0.2f, LullabiesGame.ANIMATION_TIME / 12, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.color(Color.RED,LullabiesGame.ANIMATION_TIME / 12, Interpolation.fade),
                        Actions.color(Color(Color.rgba8888(252/255f,166/255f,165/255f, 0.9f)),
                            LullabiesGame.ANIMATION_TIME / 10, Interpolation.fade)
                    ),
                )
            )
        )
    }

    val flare2 = LayerActor(
        tex = flareTex,
        texture = assets.getAsset(flareTex),
        cWidth = 172f,
        cHeight = 183f,
        cX = MainScreen.BG_WIDTH * 0.097f - 86,
        cY = MainScreen.BG_HEIGHT * 0.712f - 92.5f
    ).also {
        it.originX = it.cWidth / 2
        it.originY = it.cHeight / 2
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(0.5f, 0.5f, LullabiesGame.ANIMATION_TIME / 5, Interpolation.fade),
                        Actions.scaleBy(-0.5f, -0.5f, LullabiesGame.ANIMATION_TIME / 5, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.color(Color.CORAL,LullabiesGame.ANIMATION_TIME / 5, Interpolation.fade),
                        Actions.color(Color(Color.rgba8888(252/255f,166/255f,165/255f, 0.9f)),
                            LullabiesGame.ANIMATION_TIME / 10, Interpolation.fade)
                    ),
                )
            )
        )
    }

    val flare3 = LayerActor(
        tex = flareTex,
        texture = assets.getAsset(flareTex),
        cWidth = 259f,
        cHeight = 262f,
        cX = MainScreen.BG_WIDTH * 0.264f - 129.5f,
        cY = MainScreen.BG_HEIGHT * 0.745f - 131
    ).also {
        it.originX = it.cWidth / 2
        it.originY = it.cHeight / 2
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(0.3f, 0.3f, LullabiesGame.ANIMATION_TIME / 3, Interpolation.fade),
                        Actions.scaleBy(-0.3f, -0.3f, LullabiesGame.ANIMATION_TIME / 3, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.color(Color.CORAL,LullabiesGame.ANIMATION_TIME / 3, Interpolation.fade),
                        Actions.color(Color(Color.rgba8888(252/255f,166/255f,165/255f, 0.9f)),
                            LullabiesGame.ANIMATION_TIME / 10, Interpolation.fade)
                    ),
                )
            )
        )
    }

    val flare4 = LayerActor(
        tex = flareTex,
        texture = assets.getAsset(flareTex),
        cWidth = 173f,
        cHeight = 183f,
        cX = MainScreen.BG_WIDTH * 0.765f - 86.5f,
        cY = MainScreen.BG_HEIGHT * 0.694f - 91.5f
    ).also {
        it.originX = it.cWidth / 2
        it.originY = it.cHeight / 2
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(0.6f, 0.6f, LullabiesGame.ANIMATION_TIME / 10, Interpolation.fade),
                        Actions.scaleBy(-0.6f, -0.6f, LullabiesGame.ANIMATION_TIME / 10, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.color(Color.RED,LullabiesGame.ANIMATION_TIME / 10, Interpolation.fade),
                        Actions.color(Color(Color.rgba8888(252/255f,166/255f,165/255f, 0.9f)),
                            LullabiesGame.ANIMATION_TIME / 10, Interpolation.fade)
                    ),
                )
            )
        )
    }

    val flare5 = LayerActor(
        tex = flareTex,
        texture = assets.getAsset(flareTex),
        cWidth = 259f,
        cHeight = 262f,
        cX = MainScreen.BG_WIDTH * 0.877f - 129.5f,
        cY = MainScreen.BG_HEIGHT * 0.692f - 131
    ).also {
        it.originX = it.cWidth / 2
        it.originY = it.cHeight / 2
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(0.2f, 0.2f, LullabiesGame.ANIMATION_TIME / 6, Interpolation.fade),
                        Actions.scaleBy(-0.2f, -0.2f, LullabiesGame.ANIMATION_TIME / 6, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.color(Color.CORAL,LullabiesGame.ANIMATION_TIME / 6, Interpolation.fade),
                        Actions.color(Color(Color.rgba8888(252/255f,166/255f,165/255f, 0.9f)),
                            LullabiesGame.ANIMATION_TIME / 10, Interpolation.fade)
                    ),
                )
            )
        )
    }


    override val all = arrayOf<Actor>(
        background, sun, stars, planet, plan3,
        flare1,
        flare2,
        flare3,
        flare4,
        flare5,
        plan2, plan1
    )

}