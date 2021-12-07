package com.twobsoft.lullabies.models
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.lullabies.LullabiesGame
import com.twobsoft.lullabies.MainScreen
import com.twobsoft.lullabies.components.LayerActor

class PlutoModel: Entity() {
    companion object {
        const val backgroundTex     = "planets/pluto/background.png"
        const val sunTex            = "planets/pluto/sun.png"
        const val starsTex          = "planets/pluto/stars.png"
        const val planetTex         = "planets/pluto/planet.png"
        const val plan3Tex          = "planets/pluto/3plan.png"
        const val plan2Tex          = "planets/pluto/2plan.png"
        const val plan1Tex          = "planets/pluto/1plan.png"
        const val flareTex          = "planets/pluto/flare.png"
    }

    override val stageNumber = 11

    val background = LayerActor(tex = backgroundTex)

    val sun = LayerActor(tex = sunTex).also {
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

    val stars = LayerActor(tex = starsTex).also {
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

    val planet = LayerActor(tex = planetTex).also {
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

    val plan3 = LayerActor(tex = plan3Tex).also {
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
                    Actions.scaleBy(0.1f, 0.1f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.scaleBy(-0.1f, -0.1f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                )
            )
        )
    }

    val flare1 = LayerActor(
        tex = flareTex,
        cWidth = MainScreen.BG_WIDTH * 0.2f,
        cHeight = MainScreen.BG_HEIGHT * 0.1f,
        cX = -MainScreen.BG_WIDTH * 0.079f,
        cY = MainScreen.BG_HEIGHT * 0.628f
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
        cWidth = MainScreen.BG_WIDTH * 0.12f,
        cHeight = MainScreen.BG_HEIGHT * 0.07f,
        cX = MainScreen.BG_WIDTH * 0.045f,
        cY = MainScreen.BG_HEIGHT * 0.675f
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
        cWidth = MainScreen.BG_WIDTH * 0.18f,
        cHeight = MainScreen.BG_HEIGHT * 0.1f,
        cX = MainScreen.BG_WIDTH * 0.175f,
        cY = MainScreen.BG_HEIGHT * 0.695f
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
        cWidth = MainScreen.BG_WIDTH * 0.12f,
        cHeight = MainScreen.BG_HEIGHT * 0.07f,
        cX = MainScreen.BG_WIDTH * 0.71f,
        cY = MainScreen.BG_HEIGHT * 0.662f
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
                        Actions.color(Color.RED,LullabiesGame.ANIMATION_TIME / 8, Interpolation.fade),
                        Actions.color(Color(Color.rgba8888(252/255f,166/255f,165/255f, 0.9f)),
                            LullabiesGame.ANIMATION_TIME / 10, Interpolation.fade)
                    ),
                )
            )
        )
    }

    val flare5 = LayerActor(
        tex = flareTex,
        cWidth = MainScreen.BG_WIDTH * 0.18f,
        cHeight = MainScreen.BG_HEIGHT * 0.1f,
        cX = MainScreen.BG_WIDTH * 0.787f,
        cY = MainScreen.BG_HEIGHT * 0.644f
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