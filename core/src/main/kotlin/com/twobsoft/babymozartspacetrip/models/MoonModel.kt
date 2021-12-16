package com.twobsoft.babymozartspacetrip.models

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.babymozartspacetrip.Assets
import com.twobsoft.babymozartspacetrip.components.LayerActor
import com.twobsoft.babymozartspacetrip.LullabiesGame
import com.twobsoft.babymozartspacetrip.MainScreen
import com.twobsoft.babymozartspacetrip.components.LayerGroup
import com.twobsoft.babymozartspacetrip.hud.HudModel


class MoonModel(val assets: Assets): Entity() {

    companion object {
        const val backgroundTex   = "planets/moon/background.png"
        const val planetsTex      = "planets/moon/planets.png"
        const val zemlyaTex       = "planets/moon/zemlya.png"
        const val star1Tex        = "planets/moon/star1.png"
        const val star2Tex        = "planets/moon/star2.png"
        const val star3Tex        = "planets/moon/star3.png"
        const val goryTex         = "planets/moon/gory.png"
        const val lunaTex         = "planets/moon/luna.png"
        const val flagTex         = "planets/moon/flag.png"
        val all = arrayOf(
            backgroundTex, planetsTex, zemlyaTex, star1Tex, star2Tex, star3Tex,
            goryTex, lunaTex, flagTex
        )
    }


    override val stageNumber = 5

    override val background  = LayerActor(
        tex = backgroundTex,
        texture = Texture(Gdx.files.internal(backgroundTex))
    )

    val moonGroup = LayerGroup()

    val planets = LayerActor(
        tex = planetsTex,
        texture = Texture(Gdx.files.internal(planetsTex)),
        isSceneDefaultLayer = true
    )

    val zemlya = LayerActor(
        tex = zemlyaTex,
        texture = Texture(Gdx.files.internal(zemlyaTex)),
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

    val star1 = LayerActor(
        tex = star1Tex,
        texture = Texture(Gdx.files.internal(star1Tex)),
    ).also {
        it.width    = MainScreen.BG_WIDTH
        it.height   = MainScreen.BG_HEIGHT
        it.originY  = MainScreen.BG_HEIGHT * 0.7f
        it.originX  = MainScreen.BG_HEIGHT * 0.3f
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.rotateBy(10f, LullabiesGame.ANIMATION_TIME),
                    Actions.rotateBy(-10f, LullabiesGame.ANIMATION_TIME),
                )

            )
        )
    }

    val star2 = LayerActor(
        tex = star2Tex,
        texture = Texture(Gdx.files.internal(star2Tex)),
    ).also {
        it.width    = MainScreen.BG_WIDTH
        it.height   = MainScreen.BG_HEIGHT
        it.originY  = MainScreen.BG_HEIGHT * 0.7f
        it.originX  = MainScreen.BG_HEIGHT * 0.3f
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.rotateBy(6f, LullabiesGame.ANIMATION_TIME),
                    Actions.rotateBy(-6f, LullabiesGame.ANIMATION_TIME),
                )

            )
        )
    }

    val star3 = LayerActor(
        tex = star3Tex,
        texture = Texture(Gdx.files.internal(star3Tex))
    ).also {
        it.width    = MainScreen.BG_WIDTH
        it.height   = MainScreen.BG_HEIGHT
        it.originX  = it.width / 2
        it.originY  = it.height / 2
    }


    val gory = LayerActor(
        tex = goryTex,
        texture = Texture(Gdx.files.internal(goryTex)),
        isSceneDefaultLayer = true
    )


    val luna = LayerActor(
        tex = lunaTex,
        texture = Texture(Gdx.files.internal(lunaTex)),
        isSceneDefaultLayer = true
    )

    val flag = LayerActor(
        tex = flagTex,
        texture = Texture(Gdx.files.internal(flagTex)),
    ).also {
        it.width    = HudModel.layerWidth * 0.15f
        it.height   = it.width * 1.92f
        it.x        = HudModel.layerXPosition - it.width / 2 + HudModel.layerWidth * 0.37f
        it.y        = HudModel.layerYPosition + HudModel.layerHeight * 0.25f
    }

    init {
        moonGroup.addActor(gory)
        moonGroup.addActor(luna)
        moonGroup.addActor(flag)
        moonGroup.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(0.1f, 0.1f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.scaleBy(-0.1f, -0.1f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    ),
                    Actions.sequence(
                        Actions.moveBy(-MainScreen.BG_HEIGHT * 0.007f, -MainScreen.BG_HEIGHT * 0.01f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.moveBy(MainScreen.BG_HEIGHT * 0.007f, MainScreen.BG_HEIGHT * 0.01f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    )
                )

            )
        )
    }



    override val all = arrayOf(background, star1, star2, star3, planets, zemlya, moonGroup)
}