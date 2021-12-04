package com.twobsoft.lullabies.models

import com.badlogic.gdx.scenes.scene2d.Actor
import com.twobsoft.lullabies.components.LayerActor

class UranusModel: Entity() {

    companion object {
        const val backgroundTex     = "planets/uranus/background.png"
        const val plan4Tex          = "planets/uranus/4plan.png"
        const val plan3Tex          = "planets/uranus/3plan.png"
        const val plan2Tex          = "planets/uranus/2plan.png"
        const val plan1Tex          = "planets/uranus/1plan.png"
    }

    override val stageNumber = 9

    val background    = LayerActor(tex = backgroundTex)
    val plan4         = LayerActor(tex = plan4Tex)
    val plan3         = LayerActor(tex = plan3Tex)
    val plan2         = LayerActor(tex = plan2Tex)
    val plan1         = LayerActor(tex = plan1Tex)

    override val all = arrayOf<Actor>(background, plan4, plan3, plan2, plan1)
}