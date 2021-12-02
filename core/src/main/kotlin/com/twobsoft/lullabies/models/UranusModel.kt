package com.twobsoft.lullabies.models

import com.twobsoft.lullabies.GameComponent

class UranusModel: Entity() {

    companion object {
        const val backgroundTex     = "planets/uranus/background.png"
        const val plan4Tex          = "planets/uranus/4plan.png"
        const val plan3Tex          = "planets/uranus/3plan.png"
        const val plan2Tex          = "planets/uranus/2plan.png"
        const val plan1Tex          = "planets/uranus/1plan.png"
    }

    override val stageNumber = 9

    val background    = GameComponent(tex = backgroundTex)
    val plan4         = GameComponent(tex = plan4Tex)
    val plan3         = GameComponent(tex = plan3Tex)
    val plan2         = GameComponent(tex = plan2Tex)
    val plan1         = GameComponent(tex = plan1Tex)

    override val all = arrayOf(background, plan4, plan3, plan2, plan1)
}