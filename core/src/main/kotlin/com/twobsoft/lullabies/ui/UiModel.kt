package com.twobsoft.lullabies.ui

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class UiModel {

    companion object {
        const val frameTex = "ui/frame.png"
    }

    val frame = UiActor(tex = frameTex).also {
        it.scaleBy(0.3f)
        it.addAction(Actions.moveBy(-40f, -90f))
    }

    val all = arrayOf(frame)

}