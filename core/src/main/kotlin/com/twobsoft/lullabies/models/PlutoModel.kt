package com.twobsoft.lullabies.models
import com.badlogic.gdx.scenes.scene2d.Actor
import com.twobsoft.lullabies.components.LayerActor

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

    val background  = LayerActor(tex = backgroundTex)
    val sun         = LayerActor(tex = sunTex)
    val stars       = LayerActor(tex = starsTex)
    val planet      = LayerActor(tex = planetTex)
    val plan3       = LayerActor(tex = plan3Tex)
    val plan2       = LayerActor(tex = plan2Tex)
    val plan1       = LayerActor(tex = plan1Tex)
    val flare       = LayerActor(tex = flareTex)


    override val all = arrayOf<Actor>(background, sun, stars, planet, plan3, plan2, plan1)

}