package com.twobsoft.babymozartspacetrip.models
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.babymozartspacetrip.Assets
import com.twobsoft.babymozartspacetrip.components.LayerActor
import com.twobsoft.babymozartspacetrip.LullabiesGame
import com.twobsoft.babymozartspacetrip.MainScreen

class MercuryModel(val assets: Assets): Entity() {

    companion object {
        const val backgroundTex   = "planets/mercury/background.png"
        const val planet1Tex      = "planets/mercury/planet1.png"
        const val planet2Tex      = "planets/mercury/planet2.png"
        const val plan2Tex        = "planets/mercury/2plan.png"
        const val plan1Tex        = "planets/mercury/1plan.png"
        val all = arrayOf(backgroundTex, planet1Tex, planet2Tex, plan1Tex, plan2Tex, plan1Tex)
    }

    override val stageNumber = 2

    override val background = LayerActor(tex = backgroundTex, texture = assets.getAsset(backgroundTex)).also {
            it.originX = 0f
            it.actions.add(
                Actions.repeat(
                    RepeatAction.FOREVER,
                    Actions.parallel(
                        Actions.sequence(
                            Actions.scaleBy(0.03f, 0.03f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                            Actions.scaleBy(-0.03f, -0.03f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        ),
                        Actions.sequence(
                            Actions.moveBy(MainScreen.BG_WIDTH * 0.03f, MainScreen.BG_HEIGHT * 0.03f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                            Actions.moveBy(-MainScreen.BG_WIDTH * 0.03f, -MainScreen.BG_HEIGHT * 0.03f, LullabiesGame.ANIMATION_TIME, Interpolation.fade)
                        )
                    )
                )
            )
    }

    val planet1 = LayerActor(tex = planet1Tex, texture = assets.getAsset(planet1Tex)).also {
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

    val planet2 = LayerActor(tex = planet2Tex, texture = assets.getAsset(planet2Tex)).also {
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

    override val all = arrayOf<Actor>(background, planet1, planet2, plan2, plan1)
}