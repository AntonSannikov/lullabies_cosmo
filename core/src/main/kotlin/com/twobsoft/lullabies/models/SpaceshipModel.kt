package com.twobsoft.lullabies.models

import com.twobsoft.lullabies.GameComponent

class SpaceshipModel: Entity() {

    companion object {
        const val backgroundTex     = "planets/spaceship/background.png"
        const val earthTex          = "planets/spaceship/zemlya.png"
        const val plan3Tex          = "planets/spaceship/3plan.png"
        const val plan2Tex          = "planets/spaceship/2plan.png"
        const val plan1Tex          = "planets/spaceship/1plan.png"
    }

    override val stageNumber = 14

    val background    = GameComponent(tex = backgroundTex)
    val earth         = GameComponent(tex = earthTex)
    val plan3         = GameComponent(tex = plan3Tex)
    val plan2         = GameComponent(tex = plan2Tex)
    val plan1         = GameComponent(tex = plan1Tex)

    override val all = arrayOf(background, earth, plan3, plan2, plan1)
}