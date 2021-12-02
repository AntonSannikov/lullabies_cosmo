package com.twobsoft.lullabies.models

import com.twobsoft.lullabies.GameComponent

class AsteroidModel: Entity() {

    companion object {
        const val backgroundTex     = "planets/asteroid/background.png"
        const val plan5Tex          = "planets/asteroid/5plan.png"
        const val plan4Tex          = "planets/asteroid/4plan.png"
        const val plan3Tex          = "planets/asteroid/3plan.png"
        const val plan2Tex          = "planets/asteroid/2plan.png"
        const val plan1Tex          = "planets/asteroid/1plan.png"
        const val flareTex          = "planets/asteroid/svet.png"
    }

    override val stageNumber = 12

    val background    = GameComponent(tex = backgroundTex)
    val plan5         = GameComponent(tex = plan5Tex)
    val plan4         = GameComponent(tex = plan4Tex)
    val plan3         = GameComponent(tex = plan3Tex)
    val plan2         = GameComponent(tex = plan2Tex)
    val plan1         = GameComponent(tex = plan1Tex)
    val flare         = GameComponent(tex = flareTex)

    override val all = arrayOf(background, plan5, plan4, plan3, plan2, plan1)
}