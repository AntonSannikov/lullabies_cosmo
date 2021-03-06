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


class MarsModel(val assets: Assets): Entity() {

    companion object {
        const val backgroundTex     = "planets/mars/background.png"
        const val oblako5Tex        = "planets/mars/oblako5.png"
        const val oblako4Tex        = "planets/mars/oblako4.png"
        const val oblako3Tex        = "planets/mars/oblako3.png"
        const val oblako2Tex        = "planets/mars/oblako2.png"
        const val oblako1Tex        = "planets/mars/oblako1.png"
        const val plan3Tex          = "planets/mars/3plan.png"
        const val plan2Tex          = "planets/mars/2plan.png"
        const val plan1Tex          = "planets/mars/1plan.png"
        val all = arrayOf(
            backgroundTex, oblako5Tex, oblako4Tex, oblako3Tex, oblako2Tex, oblako1Tex,
            plan3Tex, plan2Tex, plan1Tex
        )
    }

    override val stageNumber = 6

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
                    Actions.rotateBy(7f, 20f, Interpolation.fade),
                    Actions.rotateBy(-7f, 20f, Interpolation.fade),
                )
            )
        )
    }

    val oblako5 = LayerActor(
        tex = oblako5Tex,
        texture = Texture(Gdx.files.internal(oblako5Tex)),
        isSceneDefaultLayer = true
    ).also {
        it.isNeedReposition = true
        it.x= -MainScreen.BG_WIDTH
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(MainScreen.BG_WIDTH * 2f, 0f, 150f),
                    Actions.run { it.x = -MainScreen.BG_WIDTH }
                )
            )
        )
    }

    val oblako4  = LayerActor(
        tex = oblako4Tex,
        texture = Texture(Gdx.files.internal(oblako4Tex)),
        isSceneDefaultLayer = true
    ).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(MainScreen.BG_WIDTH * 1.5f, 0f, 190f),
                    Actions.run { it.x = -MainScreen.BG_WIDTH }
                )
            )
        )
    }

    val oblako3 = LayerActor(
        tex = oblako3Tex,
        texture = Texture(Gdx.files.internal(oblako3Tex)),
        isSceneDefaultLayer = true
    ).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(MainScreen.BG_WIDTH * 1.5f, 0f, 215f),
                    Actions.run { it.x = -MainScreen.BG_WIDTH }
                )
            )
        )
    }

    val oblako2 = LayerActor(
        tex = oblako2Tex,
        texture = Texture(Gdx.files.internal(oblako2Tex)),
        isSceneDefaultLayer = true
    ).also {
        it.isNeedReposition = true
        it.x = -MainScreen.BG_WIDTH * 0.8f
        it.actions.add(
        Actions.repeat(
            RepeatAction.FOREVER,
            Actions.sequence(
                Actions.moveBy(MainScreen.BG_WIDTH * 2f, 0f, 170f),
                Actions.run { it.x = -MainScreen.BG_WIDTH }
            )
        )
        )
    }

    val oblako1 = LayerActor(
        tex = oblako1Tex,
        texture = Texture(Gdx.files.internal(oblako1Tex)),
        isSceneDefaultLayer = true
    ).also {
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.moveBy(MainScreen.BG_WIDTH * 1.5f, 0f, 200f),
                    Actions.run { it.x = -MainScreen.BG_WIDTH }
                )
            )
        )
    }

    val plan3 = LayerActor(
        tex = plan3Tex,
        texture = Texture(Gdx.files.internal(plan3Tex)),
        isSceneDefaultLayer = true
    ).also {
        it.originX = 0f
        it.originY = 0f
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

    val plan2 = LayerActor(
        tex = plan2Tex,
        texture = Texture(Gdx.files.internal(plan2Tex)),
        isSceneDefaultLayer = true
    ).also {
        it.originX = 0f
        it.originY = 0f
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
        it.originX = 0f
        it.originY = 0f
        it.actions.add(
            Actions.repeat(
                RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.scaleBy(0.09f, 0.09f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                    Actions.scaleBy(-0.09f, -0.09f, LullabiesGame.ANIMATION_TIME, Interpolation.fade),
                )
            )
        )
    }


    override val all = arrayOf<Actor>(
        background,
        oblako5,
        oblako4,
        oblako3,
        oblako2,
        oblako1,
        plan3,
        plan2,
        plan1
    )

}