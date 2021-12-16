package com.twobsoft.babymozartspacetrip.models

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.babymozartspacetrip.Assets
import com.twobsoft.babymozartspacetrip.LullabiesGame
import com.twobsoft.babymozartspacetrip.MainScreen
import com.twobsoft.babymozartspacetrip.components.LayerActor
import com.twobsoft.babymozartspacetrip.hud.HudModel

class AlienshipModel(val assets: Assets): Entity() {

    companion object {
        const val backgroundTex     = "planets/alienship/background.png"
        const val plan4Tex          = "planets/alienship/4plan.png"
        const val plan3Tex          = "planets/alienship/3plan.png"
        const val plan2Tex          = "planets/alienship/2plan.png"
        const val plan1Tex          = "planets/alienship/1plan.png"
        const val hologramTex       = "planets/alienship/hologram.png"
        const val flareTex          = "planets/alienship/svechenie.png"
        val all = arrayOf(
            backgroundTex, plan4Tex, plan3Tex, plan2Tex, plan1Tex,
            hologramTex, flareTex
        )
    }

    override val stageNumber = 15

    override val background = LayerActor(
        tex = backgroundTex,
        texture = Texture(Gdx.files.internal(backgroundTex))
    ).also {
        it.originX = it.width / 2
        it.originY = it.height / 2
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.scaleBy(0.4f, 0.4f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.scaleBy(-0.4f, -0.4f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                ),
            )
        )
    }

    val plan4 = LayerActor(
        tex = plan4Tex,
        texture = Texture(Gdx.files.internal(plan4Tex)),
        isSceneDefaultLayer = true
    ).also {
        it.orbitRadius = HudModel.layerHeight * 0.04f
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(0.1f, 0.1f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.scaleBy(-0.08f, -0.01f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
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

    val hologram = LayerActor(
        tex = hologramTex,
        texture = Texture(Gdx.files.internal(hologramTex)),
        isOrbit = true,
    ).also {
        it.width    = HudModel.layerWidth * 0.2f
        it.height   = it.width * 0.648f
        it.x        = HudModel.layerXPosition - it.width / 2 + HudModel.layerWidth*0.667f
        it.y        = HudModel.layerYPosition - it.height / 2 + HudModel.layerHeight*0.32f
        it.orbitRadius = HudModel.layerHeight * 0.018f
        it.angleDelta = -0.8f
        it.orbitAnchor = Vector2(it.x, it.y)
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.color(Color.GREEN, LullabiesGame.ANIMATION_TIME / 5, Interpolation.fade),
                    Actions.color(
                        Color(Color.rgba8888(195/255f,255/255f,228/255f, 0.9f)),
                        LullabiesGame.ANIMATION_TIME / 10, Interpolation.fade)
                ),
            )
        )
    }

    val flare = LayerActor(
        tex = flareTex,
        texture = Texture(Gdx.files.internal(flareTex)),
        isSceneDefaultLayer = true
    ).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(0.03f, 0.03f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.scaleBy(-0.03f, -0.03f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    ),
                )
            )
        )
    }

    override val all = arrayOf<Actor>(background, plan4, plan3, plan2, plan1, hologram, flare)
}