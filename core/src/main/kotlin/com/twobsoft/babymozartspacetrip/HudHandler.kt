package com.twobsoft.babymozartspacetrip

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.twobsoft.babymozartspacetrip.hud.HudActor
import com.twobsoft.babymozartspacetrip.hud.HudGroup
import com.twobsoft.babymozartspacetrip.hud.HudModel
import com.twobsoft.babymozartspacetrip.utils.HudActorComparator

class HudHandler(val screen: MainScreen) {

    fun refreshHud() {

        val tempArray = arrayListOf<Actor>()
        for (actor in screen.stage.actors) {
            if (actor is HudActor || actor is HudGroup) {
                tempArray.add(actor)
            }
        }

        if (tempArray.isEmpty()) {
            for (hudActor in screen.hudModel.all) {
                if (hudActor is HudGroup) {
                    hudActor.children.forEach { it as HudActor
                        if (it.text != "") {
                            it.changeText(MediaPlayer.playlist[screen.currentStageNumber-1]!!)
                        }
                    }
                }
                screen.stage.addActor(hudActor)
            }
        } else {
            for (actor in tempArray) {
                if (actor is HudGroup) {
                    actor.children.forEach { it as HudActor
                        if (it.text != "") {
                            it.changeText(MediaPlayer.playlist[screen.currentStageNumber-1]!!)
                        }
                    }
                }
                actor.toFront()
            }
        }

        if (screen.isLooping) {
            val actor = HudActor(
                tex = HudModel.optionsFlareTex,
                actorTexture = Texture(Gdx.files.internal(HudModel.optionsFlareTex))
            ).also {
                it.x = screen.hudModel.options.x
                it.y = screen.hudModel.options.y
                it.scaleBy(-0.84f, -0.88f)
            }
            screen.stage.addActor(actor)
        }
        screen.isHud = true
        screen.isHudTapable = true
    }


    fun rollbackHud() {
        screen.stage.actors.forEach {
            if (it is HudGroup) {
                it.addAction(
                    Actions.sequence(
                        Actions.delay(0.3f),
                        Actions.run { removeHud() }
                    )
                )
            }
        }
    }


    fun removeHud() {
        var i = 0
        var length = screen.stage.actors.size

        while (i < length) {
            val actor = screen.stage.actors[i]
            if (actor is HudGroup) {
                actor.remove()
                length--
            } else {
                i++
            }
        }
        screen.isHud = false
        screen.isHudTapable = false
    }


    fun onLoop() {
        screen.isLooping = !screen.isLooping
        screen.game.serviceApi.setLooping(screen.isLooping)
    }


    fun addLampLight() {
        for (actor in screen.stage.actors) {
            if (actor is HudGroup) {
                val lampLight = HudActor(
                    tex = HudModel.lampLightTex,
                    actorTexture = Texture(Gdx.files.internal(HudModel.lampLightTex))
                ).also {
                    it.width    = MainScreen.BG_WIDTH * 0.3f
                    it.height   = it.width * 0.898f
                    it.x        = (MainScreen.BG_WIDTH - it.width) / 2
                    it.y        = MainScreen.BG_HEIGHT - HudModel.upperFramePadding / 2 - HudModel.upperPanelWindowHeight - it.height
                    it.originX  = it.width / 2
                    it.originY  = it.height / 2
                    it.addAction(
                        Actions.repeat(
                            RepeatAction.FOREVER,
                            Actions.sequence(
                                Actions.scaleBy(0.4f, 0.4f, 5f, Interpolation.fade),
                                Actions.scaleBy(-0.4f, -0.4f, 5f, Interpolation.fade)
                            )
                        )
                    )
                }
                actor.addActor(lampLight)
                actor.children.sort(HudActorComparator())
                break
            }
        }
    }


}