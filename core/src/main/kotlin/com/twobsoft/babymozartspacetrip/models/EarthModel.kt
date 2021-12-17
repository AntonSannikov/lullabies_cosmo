package com.twobsoft.babymozartspacetrip.models


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.babymozartspacetrip.Assets
import com.twobsoft.babymozartspacetrip.components.LayerActor
import com.twobsoft.babymozartspacetrip.LullabiesGame
import com.twobsoft.babymozartspacetrip.MainScreen
import com.twobsoft.babymozartspacetrip.components.LayerGroup
import com.twobsoft.babymozartspacetrip.hud.HudModel
import com.twobsoft.babymozartspacetrip.hud.HudModel.Companion.layerHeight
import com.twobsoft.babymozartspacetrip.hud.HudModel.Companion.layerWidth
import com.twobsoft.babymozartspacetrip.hud.HudModel.Companion.layerXPosition
import com.twobsoft.babymozartspacetrip.hud.HudModel.Companion.layerYPosition


class EarthModel(val assets: Assets): Entity() {
    companion object {
        const val cloud1Tex     = "planets/earth/cloud1.png"
        const val cloud2Tex     = "planets/earth/cloud2.png"
        const val cloud3Tex     = "planets/earth/cloud3.png"
        const val skyTex        = "planets/earth/background.png"
        const val rockTex       = "planets/earth/rock.png"
        const val riverTex      = "planets/earth/river.png"
        const val forestTex     = "planets/earth/forest.png"
        const val coastTex      = "planets/earth/coast.png"
        const val smokeTex      = "planets/earth/smoke.png"
        const val treerockTex   = "planets/earth/treerock.png"
        const val leafTex       = "planets/earth/leaf.png"
        val all = arrayOf(
            cloud1Tex, cloud2Tex, cloud3Tex, skyTex, rockTex, riverTex,
            forestTex, coastTex, smokeTex, treerockTex, leafTex
        )
    }

    override val stageNumber = 4


    val cloud1 = LayerActor(
        tex = cloud1Tex,
        texture = Texture(Gdx.files.internal(cloud1Tex)),
        isSceneDefaultLayer = true
    ).also {
        it.isNeedReposition = true
        it.x = MainScreen.BG_WIDTH * 0.2f
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(-MainScreen.BG_WIDTH * 2.2f, 0f, 280f),
                    Actions.moveTo(MainScreen.BG_WIDTH * 1.2f, MainScreen.bottomPadding),
                )
            )
        )
    }

    val cloud2 = LayerActor(
        tex = cloud2Tex,
        texture = Texture(Gdx.files.internal(cloud2Tex)),
        isSceneDefaultLayer = true
    ).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(-MainScreen.BG_WIDTH * 2.2f, 0f, 190f),
                    Actions.moveTo(MainScreen.BG_WIDTH * 1.2f, MainScreen.bottomPadding),
                )
            )
        )
    }

    val cloud3 = LayerActor(
        tex = cloud3Tex,
        texture = Texture(Gdx.files.internal(cloud3Tex)),
        isSceneDefaultLayer = true
    ).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(-MainScreen.BG_WIDTH * 2.2f, 0f, 230f),
                    Actions.moveTo(MainScreen.BG_WIDTH * 1.2f, MainScreen.bottomPadding),
                )
            )
        )
    }

    override val background = LayerActor(
        tex = skyTex,
        texture = Texture(Gdx.files.internal(skyTex))
    ).also {
        it.width    = MainScreen.BG_WIDTH
        it.height   = MainScreen.BG_HEIGHT
        it.originX  = it.width / 2
        it.originY  = it.height / 2
    }

    val rock = LayerActor(
        tex = rockTex,
        texture = Texture(Gdx.files.internal(rockTex)),
        isSceneDefaultLayer = true
    ).also {
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

    val river = LayerActor(
        tex = riverTex,
        texture = Texture(Gdx.files.internal(riverTex)),
        isSceneDefaultLayer = true
    ).also {
        it.initOrigin(0f, 0f)
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

    val forest = LayerActor(
        tex = forestTex,
        texture = Texture(Gdx.files.internal(forestTex)),
        isSceneDefaultLayer = true
    ).also {
        it.initOrigin(0f, 0f)
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

    val smoke = LayerActor(
        tex = smokeTex,
        texture = Texture(Gdx.files.internal(smokeTex)),
    ).also {
        it.width    = MainScreen.BG_WIDTH
        it.height   = layerHeight / 2.5f
        it.y        = layerYPosition + layerHeight * 0.25f
        it.initOrigin(0f, 0f)
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(-MainScreen.BG_WIDTH*2f, 0f, 150f),
                    Actions.moveTo(MainScreen.BG_WIDTH, it.y),
                ),

            )
        )
    }


    val coast = LayerActor(
        tex = coastTex,
        texture = Texture(Gdx.files.internal(coastTex)),
        isSceneDefaultLayer = true
    ).also {
        it.initOrigin(0f, 0f)
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.scaleBy(0.15f, 0.15f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.scaleBy(-0.15f, -0.15f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                )
            )
        )
    }

    val treerock = LayerActor(
        tex = treerockTex,
        texture = Texture(Gdx.files.internal(treerockTex)),
        isSceneDefaultLayer = true
    ).also {
        it.initOrigin(0f, 0f)
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.parallel(
                    Actions.sequence(
                        Actions.scaleBy(0.6f, 0.6f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                        Actions.scaleBy(-0.6f, -0.6f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    ),
//                    Actions.sequence(
//                        Actions.moveBy(-MainScreen.BG_WIDTH * 0.1f, MainScreen.BG_HEIGHT * 0.1f,
//                            LullabiesGame.ANIMATION_TIME, Interpolation.fade),
//                        Actions.moveBy(
//                            MainScreen.BG_WIDTH * 0.1f, -MainScreen.BG_HEIGHT * 0.1f,
//                            LullabiesGame.ANIMATION_TIME, Interpolation.fade),
//                    )
                )
            )
        )
    }

    val leaf = LayerActor(
        tex = leafTex,
        texture = Texture(Gdx.files.internal(leafTex))
    )

    override val all = arrayOf<Actor>(
        background,
        cloud2,
        rock,
        cloud3, cloud1,
        river,
        forest, smoke, coast, treerock
    )

}