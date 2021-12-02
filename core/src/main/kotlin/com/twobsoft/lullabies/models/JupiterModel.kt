package com.twobsoft.lullabies.models

import com.twobsoft.lullabies.GameComponent

class JupiterModel: Entity() {

    companion object {
        const val backgroundTex     = "planets/jupiter/background.png"
        const val plan5Tex          = "planets/jupiter/5plan.png"
        const val plan4Tex          = "planets/jupiter/4plan.png"
        const val plan3Tex          = "planets/jupiter/3plan.png"
        const val plan2Tex          = "planets/jupiter/2plan.png"
        const val plan1Tex          = "planets/jupiter/1plan.png"
    }

    override val stageNumber = 7

    val background    = GameComponent(tex = backgroundTex)
    val plan5         = GameComponent(tex = plan5Tex)
    val plan4         = GameComponent(tex = plan4Tex)
    val plan3         = GameComponent(tex = plan3Tex)
    val plan2         = GameComponent(tex = plan2Tex)
    val plan1         = GameComponent(tex = plan1Tex)

    override val all = arrayOf(background, plan5, plan4, plan3, plan2, plan1)
}