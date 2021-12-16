package com.twobsoft.babymozartspacetrip.models


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.babymozartspacetrip.Assets
import com.twobsoft.babymozartspacetrip.LullabiesGame
import com.twobsoft.babymozartspacetrip.MainScreen
import com.twobsoft.babymozartspacetrip.components.LayerActor
import com.twobsoft.babymozartspacetrip.hud.HudModel


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

    override val background = LayerActor(
        tex = backgroundTex,
        texture = Texture(Gdx.files.internal(backgroundTex))
    )

    val sun = LayerActor(
        tex = sunTex,
        texture = Texture(Gdx.files.internal(sunTex)),
        isSceneDefaultLayer = true
    ).also {
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

    val stars = LayerActor(
        tex = starsTex,
        texture = Texture(Gdx.files.internal(starsTex))
    ).also {
        it.width    = MainScreen.BG_WIDTH
        it.height   = MainScreen.BG_HEIGHT
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

    val planet = LayerActor(
        tex = planetTex,
        texture = Texture(Gdx.files.internal(planetTex)),
        isSceneDefaultLayer = true
    ).also {
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

    val plan3 = LayerActor(
        tex = plan3Tex,
        texture = Texture(Gdx.files.internal(plan3Tex)),
        isSceneDefaultLayer = true
    ).also {
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

    val plan2 = LayerActor(
        tex = plan2Tex,
        texture = Texture(Gdx.files.internal(plan2Tex)),
        isSceneDefaultLayer = true
    ).also {
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

    val plan1 = LayerActor(
        tex = plan1Tex,
        texture = Texture(Gdx.files.internal(plan1Tex)),
        isSceneDefaultLayer = true
    ).also {
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
        texture = Texture(Gdx.files.internal(flareTex)),
    ).also {
        it.width    = MainScreen.BG_WIDTH * 0.15f
        it.height   = it.width
        it.x        = HudModel.layerXPosition - it.width / 2 + HudModel.layerWidth*0.0175f
        it.y        = HudModel.layerYPosition - it.height / 2 + HudModel.layerHeight * 0.6975f
        it.originX  = it.width / 2
        it.originY  = it.height / 2
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(0.2f, 0.2f, LullabiesGame.ANIMATION_TIME / 12, Interpolation.fade),
                        Actions.scaleBy(-0.2f, -0.2f, LullabiesGame.ANIMATION_TIME / 12, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.color(Color.RED, LullabiesGame.ANIMATION_TIME / 12, Interpolation.fade),
                        Actions.color(Color(Color.rgba8888(252/255f,166/255f,165/255f, 0.9f)),
                            LullabiesGame.ANIMATION_TIME / 10, Interpolation.fade)
                    ),
                )
            )
        )
    }

    val flare2 = LayerActor(
        tex = flareTex,
        texture = Texture(Gdx.files.internal(flareTex)),
    ).also {
        it.width    = MainScreen.BG_WIDTH * 0.13f
        it.height   = it.width
        it.x        = HudModel.layerXPosition - it.width / 2 + HudModel.layerWidth*0.1f
        it.y        = HudModel.layerYPosition - it.height / 2 + HudModel.layerHeight * 0.74f
        it.originX  = it.width / 2
        it.originY  = it.height / 2
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(0.5f, 0.5f, LullabiesGame.ANIMATION_TIME / 5, Interpolation.fade),
                        Actions.scaleBy(-0.5f, -0.5f, LullabiesGame.ANIMATION_TIME / 5, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.color(Color.CORAL, LullabiesGame.ANIMATION_TIME / 5, Interpolation.fade),
                        Actions.color(Color(Color.rgba8888(252/255f,166/255f,165/255f, 0.9f)),
                            LullabiesGame.ANIMATION_TIME / 10, Interpolation.fade)
                    ),
                )
            )
        )
    }

    val flare3 = LayerActor(
        tex = flareTex,
        texture = Texture(Gdx.files.internal(flareTex)),
    ).also {
        it.width    = MainScreen.BG_WIDTH * 0.13f
        it.height   = it.width
        it.x        = HudModel.layerXPosition - it.width / 2 + HudModel.layerWidth*0.254f
        it.y        = HudModel.layerYPosition - it.height / 2 + HudModel.layerHeight * 0.779f
        it.originX  = it.width / 2
        it.originY  = it.height / 2
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(0.3f, 0.3f, LullabiesGame.ANIMATION_TIME / 3, Interpolation.fade),
                        Actions.scaleBy(-0.3f, -0.3f, LullabiesGame.ANIMATION_TIME / 3, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.color(Color.CORAL, LullabiesGame.ANIMATION_TIME / 3, Interpolation.fade),
                        Actions.color(Color(Color.rgba8888(252/255f,166/255f,165/255f, 0.9f)),
                            LullabiesGame.ANIMATION_TIME / 10, Interpolation.fade)
                    ),
                )
            )
        )
    }

    val flare4 = LayerActor(
        tex = flareTex,
        texture = Texture(Gdx.files.internal(flareTex)),
    ).also {
        it.width    = MainScreen.BG_WIDTH * 0.1f
        it.height   = it.width
        it.x        = HudModel.layerXPosition - it.width / 2 + HudModel.layerWidth*0.766f
        it.y        = HudModel.layerYPosition - it.height / 2 + HudModel.layerHeight*0.723f
        it.originX  = it.width / 2
        it.originY  = it.height / 2
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(0.8f, 0.8f, LullabiesGame.ANIMATION_TIME / 10, Interpolation.fade),
                        Actions.scaleBy(-0.8f, -0.8f, LullabiesGame.ANIMATION_TIME / 10, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.color(Color.RED, LullabiesGame.ANIMATION_TIME / 10, Interpolation.fade),
                        Actions.color(Color(Color.rgba8888(252/255f,166/255f,165/255f, 0.9f)),
                            LullabiesGame.ANIMATION_TIME / 10, Interpolation.fade)
                    ),
                )
            )
        )
    }

    val flare5 = LayerActor(
        tex = flareTex,
        texture = Texture(Gdx.files.internal(flareTex)),
    ).also {
        it.width    = MainScreen.BG_WIDTH * 0.13f
        it.height   = it.width
        it.x        = HudModel.layerXPosition - it.width / 2 + HudModel.layerWidth*0.88f
        it.y        = HudModel.layerYPosition - it.height / 2 + HudModel.layerHeight*0.716f
        it.originX  = it.width / 2
        it.originY  = it.height / 2
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(0.2f, 0.2f, LullabiesGame.ANIMATION_TIME / 6, Interpolation.fade),
                        Actions.scaleBy(-0.2f, -0.2f, LullabiesGame.ANIMATION_TIME / 6, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.color(Color.CORAL, LullabiesGame.ANIMATION_TIME / 6, Interpolation.fade),
                        Actions.color(Color(Color.rgba8888(252/255f,166/255f,165/255f, 0.9f)),
                            LullabiesGame.ANIMATION_TIME / 10, Interpolation.fade)
                    ),
                )
            )
        )
    }


    override val all = arrayOf<Actor>(
        background,
        sun, stars, planet, plan3,
        flare1,
        flare2,
        flare3,
        flare4,
        flare5,
        plan2, plan1
    )

}