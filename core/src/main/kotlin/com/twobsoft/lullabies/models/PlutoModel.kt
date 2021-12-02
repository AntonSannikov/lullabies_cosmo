package com.twobsoft.lullabies.models
import com.twobsoft.lullabies.GameComponent

class PlutoModel: Entity() {
    companion object {
        const val backgroundTex     = "planets/pluto/background.png"
        const val sunTex            = "planets/pluto/sun.png"
        const val starsTex          = "planets/pluto/stars.png"
        const val planetTex         = "planets/pluto/planet.png"
        const val plan3Tex          = "planets/pluto/3plan.png"
        const val plan2Tex          = "planets/pluto/2plan.png"
        const val plan1Tex          = "planets/pluto/1plan.png"
        const val flareTex          = "planets/pluto/flare.png"
    }

    override val stageNumber = 11

    val background  = GameComponent(tex = backgroundTex)
    val sun         = GameComponent(tex = sunTex)
    val stars       = GameComponent(tex = starsTex)
    val planet      = GameComponent(tex = planetTex)
    val plan3       = GameComponent(tex = plan3Tex)
    val plan2       = GameComponent(tex = plan2Tex)
    val plan1       = GameComponent(tex = plan1Tex)
    val flare       = GameComponent(tex = flareTex)


    override val all = arrayOf(background, sun, stars, planet, plan3, plan2, plan1)

}