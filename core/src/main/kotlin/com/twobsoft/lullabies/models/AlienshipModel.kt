package com.twobsoft.lullabies.models

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.lullabies.Assets
import com.twobsoft.lullabies.LullabiesGame
import com.twobsoft.lullabies.MainScreen
import com.twobsoft.lullabies.components.LayerActor

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

    val background = LayerActor(tex = backgroundTex, texture = assets.getAsset(backgroundTex)).also {
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

    val plan4 = LayerActor(tex = plan4Tex, texture = assets.getAsset(plan4Tex)).also {
        it.orbitRadius = MainScreen.layerHeight * 0.04f
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

    val plan3 = LayerActor(tex = plan3Tex, texture = assets.getAsset(plan3Tex))
    val plan2 = LayerActor(tex = plan2Tex, texture = assets.getAsset(plan2Tex))
    val plan1 = LayerActor(tex = plan1Tex, texture = assets.getAsset(plan1Tex))

    val hologram = LayerActor(
        tex = hologramTex,
        texture = assets.getAsset(hologramTex),
        isOrbit = true,
        cWidth = MainScreen.BG_WIDTH * 0.14f,
        cHeight = MainScreen.layerHeight * 0.084f,
        cX = MainScreen.BG_WIDTH * 0.59f,
        cY = MainScreen.BG_HEIGHT * 0.08f + MainScreen.layerHeight * 0.31f - MainScreen.layerHeight * 0.042f,
    ).also {
        it.orbitRadius = MainScreen.layerHeight * 0.018f
        it.angleDelta = -0.8f
        it.orbitAnchor = Vector2(it.cX, it.cY)
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.color(Color.GREEN,LullabiesGame.ANIMATION_TIME / 5, Interpolation.fade),
                    Actions.color(
                        Color(Color.rgba8888(195/255f,255/255f,228/255f, 0.9f)),
                        LullabiesGame.ANIMATION_TIME / 10, Interpolation.fade)
                ),
            )
        )
    }

    val flare = LayerActor(tex = flareTex, texture = assets.getAsset(flareTex)).also {
        it.originX = MainScreen.BG_WIDTH * 0.598f
        it.originY = MainScreen.BG_HEIGHT * 0.457f
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