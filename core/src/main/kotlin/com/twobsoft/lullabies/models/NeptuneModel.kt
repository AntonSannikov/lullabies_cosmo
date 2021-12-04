package com.twobsoft.lullabies.models

import com.badlogic.gdx.scenes.scene2d.Actor
import com.twobsoft.lullabies.components.LayerActor

class NeptuneModel: Entity() {

    companion object {
        const val backgroundTex     = "planets/neptune/background.png"
        const val plan3Tex          = "planets/neptune/3plan.png"
        const val plan2Tex          = "planets/neptune/2plan.png"
        const val plan1Tex          = "planets/neptune/1plan.png"
    }

    override val stageNumber = 10

    val background    = LayerActor(tex = backgroundTex)
    val plan3         = LayerActor(tex = plan3Tex)
    val plan2         = LayerActor(tex = plan2Tex)
    val plan1         = LayerActor(tex = plan1Tex)

    override val all = arrayOf<Actor>(background, plan3, plan2, plan1)
}