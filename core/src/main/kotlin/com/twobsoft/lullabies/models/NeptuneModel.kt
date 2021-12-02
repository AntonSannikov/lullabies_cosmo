package com.twobsoft.lullabies.models

import com.twobsoft.lullabies.GameComponent

class NeptuneModel: Entity() {

    companion object {
        const val backgroundTex     = "planets/neptune/background.png"
        const val plan3Tex          = "planets/neptune/3plan.png"
        const val plan2Tex          = "planets/neptune/2plan.png"
        const val plan1Tex          = "planets/neptune/1plan.png"
    }

    override val stageNumber = 10

    val background    = GameComponent(tex = backgroundTex)
    val plan3         = GameComponent(tex = plan3Tex)
    val plan2         = GameComponent(tex = plan2Tex)
    val plan1         = GameComponent(tex = plan1Tex)

    override val all = arrayOf(background, plan3, plan2, plan1)
}