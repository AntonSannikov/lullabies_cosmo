package com.twobsoft.lullabies.hud

import com.badlogic.gdx.scenes.scene2d.Actor
import com.twobsoft.lullabies.components.HudActor
import com.twobsoft.lullabies.models.Entity

class HudModel: Entity() {

    companion object {
        const val frameTex = "ui/frame.png"
    }
    override val stageNumber = -1

    val frame = HudActor(tex = frameTex)

    override val all = arrayOf<Actor>(frame)

}