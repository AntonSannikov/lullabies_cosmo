package com.twobsoft.lullabies.models

import com.twobsoft.lullabies.GameComponent

class SaturnModel: Entity() {

    companion object {
        const val backgroundTex     = "planets/saturn/background.png"
        const val sunTex            = "planets/saturn/sun.png"
        const val ringTex           = "planets/saturn/ring.png"
        const val plan2Tex          = "planets/saturn/2plan.png"
        const val plan1Tex          = "planets/saturn/1plan.png"
    }

    override val stageNumber = 8

    val background    = GameComponent(tex = backgroundTex)
    val sun         = GameComponent(tex = sunTex)
    val ring         = GameComponent(tex = ringTex)
    val plan2         = GameComponent(tex = plan2Tex)
    val plan1         = GameComponent(tex = plan1Tex)

    override val all = arrayOf(background, sun, ring, plan2, plan1)
}