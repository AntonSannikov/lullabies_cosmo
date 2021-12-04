package com.twobsoft.lullabies.ui

import com.twobsoft.lullabies.components.UiActor

class UiModel {

    companion object {
        const val frameTex = "ui/frame.png"
    }

    val frame = UiActor(tex = frameTex).also {

//        it.addAction(Actions.moveBy(-40f, -90f))
    }

    val all = arrayOf(frame)

}