package com.twobsoft.lullabies.models

import com.badlogic.gdx.scenes.scene2d.Actor
import com.twobsoft.lullabies.components.LayerActor

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

    val background    = LayerActor(tex = backgroundTex)
    val plan5         = LayerActor(tex = plan5Tex)
    val plan4         = LayerActor(tex = plan4Tex)
    val plan3         = LayerActor(tex = plan3Tex)
    val plan2         = LayerActor(tex = plan2Tex)
    val plan1         = LayerActor(tex = plan1Tex)
    val flare         = LayerActor(tex = flareTex)

    override val all = arrayOf<Actor>(background, plan5, plan4, plan3, plan2, plan1)
}