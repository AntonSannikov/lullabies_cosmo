package com.twobsoft.lullabies.models

import com.badlogic.gdx.scenes.scene2d.Actor
import com.twobsoft.lullabies.components.LayerActor

class SpaceshipModel: Entity() {

    companion object {
        const val backgroundTex     = "planets/spaceship/background.png"
        const val earthTex          = "planets/spaceship/zemlya.png"
        const val plan3Tex          = "planets/spaceship/3plan.png"
        const val plan2Tex          = "planets/spaceship/2plan.png"
        const val plan1Tex          = "planets/spaceship/1plan.png"
    }

    override val stageNumber = 14

    val background    = LayerActor(tex = backgroundTex)
    val earth         = LayerActor(tex = earthTex)
    val plan3         = LayerActor(tex = plan3Tex)
    val plan2         = LayerActor(tex = plan2Tex)
    val plan1         = LayerActor(tex = plan1Tex)

    override val all = arrayOf<Actor>(background, earth, plan3, plan2, plan1)
}