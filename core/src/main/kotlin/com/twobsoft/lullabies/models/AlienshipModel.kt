package com.twobsoft.lullabies.models

import com.badlogic.gdx.scenes.scene2d.Actor
import com.twobsoft.lullabies.components.LayerActor

class AlienshipModel: Entity() {

    companion object {
        const val backgroundTex     = "planets/alienship/background.png"
        const val plan4Tex          = "planets/alienship/4plan.png"
        const val plan3Tex          = "planets/alienship/3plan.png"
        const val plan2Tex          = "planets/alienship/2plan.png"
        const val plan1Tex          = "planets/alienship/1plan.png"
        const val hologramTex       = "planets/alienship/hologram.png"
        const val flareTex          = "planets/alienship/svechenie.png"
    }

    override val stageNumber = 15

    val background    = LayerActor(tex = backgroundTex)
    val plan4         = LayerActor(tex = plan4Tex)
    val plan3         = LayerActor(tex = plan3Tex)
    val plan2         = LayerActor(tex = plan2Tex)
    val plan1         = LayerActor(tex = plan1Tex)
    val hologram      = LayerActor(tex = hologramTex)
    val flare         = LayerActor(tex = flareTex)

    override val all = arrayOf<Actor>(background, plan4, plan3, plan2, plan1, hologram, flare)
}