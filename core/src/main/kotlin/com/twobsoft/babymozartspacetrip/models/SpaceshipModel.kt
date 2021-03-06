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

class SpaceshipModel(val assets: Assets): Entity() {

    companion object {
        const val backgroundTex     = "planets/spaceship/background.png"
        const val earthTex          = "planets/spaceship/zemlya.png"
        const val plan3Tex          = "planets/spaceship/3plan.png"
        const val plan2Tex          = "planets/spaceship/2plan.png"
        const val plan1Tex          = "planets/spaceship/1plan.png"
        val all = arrayOf(backgroundTex, earthTex, plan3Tex, plan2Tex, plan1Tex)
    }

    override val stageNumber = 14

    override val background = LayerActor(
        tex = backgroundTex,
        texture = Texture(Gdx.files.internal(backgroundTex)),
        isRepeated = true,
    ).also {
        it.xDelta = -1
        it.yDelta = -1
    }

    val earth = LayerActor(
        tex = earthTex,
        texture = Texture(Gdx.files.internal(earthTex)),
        isSceneDefaultLayer = true
    ).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(0.07f, 0.07f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.scaleBy(-0.07f, -0.07f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    ),
                    Actions.rotateBy(360f, LullabiesGame.ANIMATION_TIME * 20f, Interpolation.fade)
                )
            )
        )
    }

    val plan3 = LayerActor(
        tex = plan3Tex,
        texture = Texture(Gdx.files.internal(plan3Tex)),
        isSceneDefaultLayer = true
    )

    val plan2 = LayerActor(
        tex = plan2Tex,
        texture = Texture(Gdx.files.internal(plan2Tex)),
        isSceneDefaultLayer = true
    )

    val plan1 = LayerActor(
        tex = plan1Tex,
        texture = Texture(Gdx.files.internal(plan1Tex)),
        isSceneDefaultLayer = true
    )

    override val all = arrayOf<Actor>(background, earth, plan3, plan2, plan1)
}