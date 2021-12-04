package com.twobsoft.lullabies.models


import com.badlogic.gdx.scenes.scene2d.Actor
import com.twobsoft.lullabies.MainScreen
import com.twobsoft.lullabies.components.AnimatedActor
import com.twobsoft.lullabies.components.LayerActor

class MenuModel: Entity() {

    companion object {
        const val backgroundTex   = "menu/background.png"
        const val radarTex        = "menu/radar.png"
        const val sunSS           = "menu/animations/sun.png"
        const val mercurySS       = "menu/animations/mercury.png"
        const val venusSS         = "menu/animations/venus.png"
        const val earthSS         = "menu/animations/earth.png"
        const val moonSS          = "menu/animations/moon.png"
        const val marsSS          = "menu/animations/mars.png"
        const val jupiterSS       = "menu/animations/jupiter.png"
        const val saturnSS        = "menu/animations/saturn.png"
        const val uranusSS        = "menu/animations/uranus.png"
        const val neptuneSS       = "menu/animations/neptune.png"
        const val plutoSS         = "menu/animations/pluto.png"
        const val asteroidSS      = "menu/animations/asteroid.png"
        const val cometSS         = "menu/animations/comet.png"
        const val spaceshipSS     = "menu/animations/spaceship.png"
        const val alienshipSS     = "menu/animations/ufo.png"

    }

    override val stageNumber = 0

    val background  = LayerActor(tex = backgroundTex)
    val radar       = LayerActor(tex = radarTex)

    val sun = AnimatedActor( sunSS, 5, 4, 820f,820f).also {
        it.xOffset = MainScreen.BG_WIDTH / 2 - 450
        // 1
        it.hitBox.add(it.xOffset)
        it.hitBox.add(0f)
        // 2
        it.hitBox.add(it.xOffset)
        it.hitBox.add(400f)
        // 3
        it.hitBox.add(it.xOffset + 400)
        it.hitBox.add(400f)
        // 4
        it.hitBox.add(it.xOffset + 400)
        it.hitBox.add(0f)

    }

    val mercury = AnimatedActor( mercurySS, 5, 4, 230f ,230f).also {
        it.xOffset = MainScreen.BG_WIDTH * 0.74f
        it.yOffset = MainScreen.BG_HEIGHT * 0.23f
    }

    val venus = AnimatedActor( venusSS, 5, 4, 300f ,300f).also {
        it.xOffset = MainScreen.BG_WIDTH * 0.02f
        it.yOffset = MainScreen.BG_HEIGHT * 0.29f
    }

    val earth = AnimatedActor( earthSS, 5, 4, 430f, 430f).also {
        it.rotation = -8f
        it.xOffset = MainScreen.BG_WIDTH * 0.53f
        it.yOffset = MainScreen.BG_HEIGHT * 0.37f
    }

    val moon = AnimatedActor( moonSS, 5, 4, 320f ,320f).also {
        it.xOffset = MainScreen.BG_WIDTH * 0.517f
        it.yOffset = MainScreen.BG_HEIGHT * 0.5f
    }

    val mars = AnimatedActor( marsSS, 5, 4, 300f ,300f).also {
        it.xOffset = MainScreen.BG_WIDTH * 0.77f
        it.yOffset = MainScreen.BG_HEIGHT * 0.63f
    }

    val jupiter = AnimatedActor( jupiterSS, 5, 4, 450f ,450f).also {
        it.xOffset = MainScreen.BG_WIDTH * 0.11f
        it.yOffset = MainScreen.BG_HEIGHT * 0.31f
    }

    val saturn = AnimatedActor( saturnSS, 4, 5, 570f ,380f).also {
        it.xOffset = MainScreen.BG_WIDTH * 0.03f
        it.yOffset = MainScreen.BG_HEIGHT * 0.75f
    }

    val uranus = AnimatedActor( uranusSS, 4, 5, 500f ,380f).also {
        it.xOffset = MainScreen.BG_WIDTH * 0.65f
        it.yOffset = MainScreen.BG_HEIGHT * 0.76f
    }

    val neptune = AnimatedActor( neptuneSS, 5, 4, 290f ,270f).also {
        it.xOffset = MainScreen.BG_WIDTH * 0.03f
        it.yOffset = MainScreen.BG_HEIGHT * 0.62f
    }

    val pluto = AnimatedActor( plutoSS, 5, 4, 300f ,270f).also {
        it.xOffset = MainScreen.BG_WIDTH * 0.02f
        it.yOffset = MainScreen.BG_HEIGHT * 0.13f
    }

    val asteroid = AnimatedActor( asteroidSS, 5, 4, 420f ,420f).also {
        it.xOffset = MainScreen.BG_WIDTH * 0.33f
        it.yOffset = MainScreen.BG_HEIGHT * 0.84f
    }

    val comet = AnimatedActor( cometSS, 5, 4, 800f ,800f).also {
        it.rotation = -10f
        it.xOffset = MainScreen.BG_WIDTH * 0.01f
        it.yOffset = MainScreen.BG_HEIGHT * 0.42f
    }

    val spaceship = AnimatedActor( spaceshipSS, 4, 6, 600f ,400f,
        frameCount = 19).also {
        it.rotation = -9f
        it.xOffset = MainScreen.BG_WIDTH * 0.3f
        it.yOffset = MainScreen.BG_HEIGHT * 0.63f
    }

    val alienship = AnimatedActor( alienshipSS, 5, 4, 350f ,350f).also {
        it.xOffset = MainScreen.BG_WIDTH * 0.74f
        it.yOffset = MainScreen.BG_HEIGHT * 0.48f
    }


    override val all = arrayOf<Actor>(
        background,
        radar,
        sun,
        mercury,
        jupiter,
        venus,
        earth,
        moon,
        mars,
        saturn,
        uranus,
        neptune,
        pluto,
        asteroid,
        comet,
        spaceship,
        alienship
    )
}