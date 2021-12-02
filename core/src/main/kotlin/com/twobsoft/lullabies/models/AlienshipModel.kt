package com.twobsoft.lullabies.models

import com.twobsoft.lullabies.GameComponent

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

    val background    = GameComponent(tex = backgroundTex)
    val plan4         = GameComponent(tex = plan4Tex)
    val plan3         = GameComponent(tex = plan3Tex)
    val plan2         = GameComponent(tex = plan2Tex)
    val plan1         = GameComponent(tex = plan1Tex)
    val hologram      = GameComponent(tex = hologramTex)
    val flare         = GameComponent(tex = flareTex)

    override val all = arrayOf(background, plan4, plan3, plan2, plan1, hologram, flare)
}