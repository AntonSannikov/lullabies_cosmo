package com.twobsoft.lullabies.models

import com.badlogic.gdx.scenes.scene2d.Actor
import com.twobsoft.lullabies.components.LayerActor

class SaturnModel: Entity() {

    companion object {
        const val backgroundTex     = "planets/saturn/background.png"
        const val sunTex            = "planets/saturn/sun.png"
        const val ringTex           = "planets/saturn/ring.png"
        const val plan2Tex          = "planets/saturn/2plan.png"
        const val plan1Tex          = "planets/saturn/1plan.png"
    }

    override val stageNumber = 8

    val background    = LayerActor(tex = backgroundTex)
    val sun         = LayerActor(tex = sunTex)
    val ring         = LayerActor(tex = ringTex)
    val plan2         = LayerActor(tex = plan2Tex)
    val plan1         = LayerActor(tex = plan1Tex)

    override val all = arrayOf<Actor>(background, sun, ring, plan2, plan1)
}