package com.twobsoft.lullabies.models

import com.twobsoft.lullabies.GameComponent

class CometModel: Entity() {

    companion object {
        const val backgroundTex     = "planets/comet/background.png"
        const val plan4Tex          = "planets/comet/4plan.png"
        const val plan3Tex          = "planets/comet/3plan.png"
        const val plan2Tex          = "planets/comet/2plan.png"
        const val plan1Tex          = "planets/comet/1plan.png"
    }

    override val stageNumber = 13

    val background    = GameComponent(tex = backgroundTex)
    val plan4         = GameComponent(tex = plan4Tex)
    val plan3         = GameComponent(tex = plan3Tex)
    val plan2         = GameComponent(tex = plan2Tex)
    val plan1         = GameComponent(tex = plan1Tex)

    override val all = arrayOf(background, plan4, plan3, plan2, plan1)
}