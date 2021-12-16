package com.twobsoft.babymozartspacetrip.models

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.babymozartspacetrip.Assets
import com.twobsoft.babymozartspacetrip.LullabiesGame
import com.twobsoft.babymozartspacetrip.components.LayerActor

class NeptuneModel(val assets: Assets): Entity() {

    companion object {
        const val backgroundTex     = "planets/neptune/background.png"
        const val plan3Tex          = "planets/neptune/3plan.png"
        const val plan2Tex          = "planets/neptune/2plan.png"
        const val plan1Tex          = "planets/neptune/1plan.png"
        val all = arrayOf(backgroundTex, plan3Tex, plan2Tex, plan1Tex)
    }

    override val stageNumber = 10

    override val background = LayerActor(
        tex = backgroundTex,
        texture = Texture(Gdx.files.internal(backgroundTex)),
    ).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(50f,25f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.moveBy(-20f,25f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.moveBy(10f, -30f,  LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.moveBy(-40f, -20f,  LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                ),
            )
        )
    }

    val plan3 = LayerActor(
        tex = plan3Tex,
        texture = Texture(Gdx.files.internal(plan3Tex)),
        isSceneDefaultLayer = true
    ).also {
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

    val plan2 = LayerActor(
        tex = plan2Tex,
        texture = Texture(Gdx.files.internal(plan2Tex)),
        isSceneDefaultLayer = true
    ).also {
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

    val plan1 = LayerActor(
        tex = plan1Tex,
        texture = Texture(Gdx.files.internal(plan1Tex)),
        isSceneDefaultLayer = true
    ).also {
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

    override val all = arrayOf<Actor>(background, plan3, plan2, plan1)
}