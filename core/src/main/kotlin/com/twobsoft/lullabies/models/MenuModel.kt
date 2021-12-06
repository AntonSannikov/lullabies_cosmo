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

    val background  = LayerActor(tex = backgroundTex, isMenu = true)
    val radar       = LayerActor(tex = radarTex)

    val sun = AnimatedActor( sunSS, 5, 4, 1,
        MainScreen.BG_HEIGHT * 0.31f,MainScreen.BG_HEIGHT * 0.31f).also {
        it.x = MainScreen.BG_WIDTH * 0.188f
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.5f)
        it.hitBox.add(0f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.22f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.15f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.34f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.28f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.6f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.31f)
        // 5
        it.hitBox.add(MainScreen.BG_WIDTH * 0.75f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.19f)
        // 6
        it.hitBox.add(MainScreen.BG_WIDTH * 0.61f)
        it.hitBox.add(0f)

    }

    val mercury = AnimatedActor( mercurySS, 5, 4, 2,
        MainScreen.BG_HEIGHT * 0.09f ,MainScreen.BG_HEIGHT * 0.09f).also {
        it.x = MainScreen.BG_WIDTH * 0.74f
        it.y = MainScreen.BG_HEIGHT * 0.23f
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.84f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.22f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.71f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.27f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.81f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.33f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.91f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.28f)
    }

    val venus = AnimatedActor( venusSS, 5, 4, 3,
        MainScreen.BG_HEIGHT * 0.114f ,MainScreen.BG_HEIGHT * 0.114f).also {
        it.x = MainScreen.BG_WIDTH * 0.02f
        it.y = MainScreen.BG_HEIGHT * 0.29f
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.13f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.28f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.01f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.34f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.086f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.41f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.185f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.398f)
        // 5
        it.hitBox.add(MainScreen.BG_WIDTH * 0.235f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.34f)
    }

    val earth = AnimatedActor( earthSS, 5, 4, 4,
        MainScreen.BG_HEIGHT * 0.16f, MainScreen.BG_HEIGHT * 0.16f).also {
        it.rotation = -8f
        it.x = MainScreen.BG_WIDTH * 0.53f
        it.y = MainScreen.BG_HEIGHT * 0.37f
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.51f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.42f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.53f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.51f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.56f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.54f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.83f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.47f)
        // 5
        it.hitBox.add(MainScreen.BG_WIDTH * 0.7f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.36f)
    }

    val moon = AnimatedActor( moonSS, 5, 4, 5,
        MainScreen.BG_HEIGHT * 0.12f ,MainScreen.BG_HEIGHT * 0.12f).also {
        it.x = MainScreen.BG_WIDTH * 0.517f
        it.y = MainScreen.BG_HEIGHT * 0.5f
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.563f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.55f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.55f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.6f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.65f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.62f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.697f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.57f)
        // 5
        it.hitBox.add(MainScreen.BG_WIDTH * 0.63f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.538f)
    }

    val mars = AnimatedActor( marsSS, 5, 4, 6,
        MainScreen.BG_HEIGHT * 0.117f ,MainScreen.BG_HEIGHT * 0.117f).also {
        it.x = MainScreen.BG_WIDTH * 0.77f
        it.y = MainScreen.BG_HEIGHT * 0.63f
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.89f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.622f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.747f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.687f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.87f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.76f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.69f)
    }

    val jupiter = AnimatedActor( jupiterSS, 5, 4, 7,
        MainScreen.BG_HEIGHT * 0.17f ,MainScreen.BG_HEIGHT * 0.17f).also {
        it.x = MainScreen.BG_WIDTH * 0.11f
        it.y = MainScreen.BG_HEIGHT * 0.31f
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.36f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.32f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.23f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.32f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.243f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.346f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.19f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.4f)
        // 5
        it.hitBox.add(MainScreen.BG_WIDTH * 0.11f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.42f)
        // 6
        it.hitBox.add(MainScreen.BG_WIDTH * 0.23f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.486f)
        // 7
        it.hitBox.add(MainScreen.BG_WIDTH * 0.36f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.46f)
        // 8
        it.hitBox.add(MainScreen.BG_WIDTH * 0.44f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.4f)
    }

    val saturn = AnimatedActor( saturnSS, 4, 5, 8,
        MainScreen.BG_HEIGHT * 0.218f ,MainScreen.BG_HEIGHT * 0.145f).also {
        it.x = MainScreen.BG_WIDTH * 0.03f
        it.y = MainScreen.BG_HEIGHT * 0.75f
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.128f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.769f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.019f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.848f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.298f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.9f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.431f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.778f)
        // 5
        it.hitBox.add(MainScreen.BG_WIDTH * 0.26f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.744f)
    }

    val uranus = AnimatedActor( uranusSS, 4, 5, 9,
        MainScreen.BG_HEIGHT * 0.191f ,MainScreen.BG_HEIGHT * 0.145f).also {
        it.x = MainScreen.BG_WIDTH * 0.65f
        it.y = MainScreen.BG_HEIGHT * 0.76f
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.64f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.778f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.718f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.9f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.972f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.909f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.93f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.797f)
        // 5
        it.hitBox.add(MainScreen.BG_WIDTH * 0.778f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.761f)
    }

    val neptune = AnimatedActor( neptuneSS, 5, 4, 10,
        MainScreen.BG_HEIGHT * 0.11f ,MainScreen.BG_HEIGHT * 0.1f).also {
        it.x = MainScreen.BG_WIDTH * 0.03f
        it.y = MainScreen.BG_HEIGHT * 0.62f
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.126f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.613f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.019f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.676f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.149f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.735f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.24f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.665f)
    }

    val pluto = AnimatedActor( plutoSS, 5, 4, 11,
        MainScreen.BG_HEIGHT * 0.12f ,MainScreen.BG_HEIGHT * 0.1f).also {
        it.x = MainScreen.BG_WIDTH * 0.02f
        it.y = MainScreen.BG_HEIGHT * 0.13f
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.148f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.115f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.02f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.17f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.12f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.25f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.245f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.184f)
    }

    val asteroid = AnimatedActor( asteroidSS, 5, 4, 12,
        MainScreen.BG_HEIGHT * 0.16f ,MainScreen.BG_HEIGHT * 0.16f).also {
        it.x = MainScreen.BG_WIDTH * 0.33f
        it.y = MainScreen.BG_HEIGHT * 0.84f
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.356f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.88f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.35f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.969f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.6f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.982f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.601f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.9f)
        // 5
        it.hitBox.add(MainScreen.BG_WIDTH * 0.483f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.869f)
    }

    val comet = AnimatedActor( cometSS, 5, 4, 13,
        MainScreen.BG_HEIGHT * 0.31f,MainScreen.BG_HEIGHT * 0.32f).also {
        it.rotation = -10f
        it.x = MainScreen.BG_WIDTH * 0.01f
        it.y = MainScreen.BG_HEIGHT * 0.42f
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.08f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.47f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.013f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.52f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.22f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.6f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.49f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.62f)
        // 5
        it.hitBox.add(MainScreen.BG_WIDTH * 0.23f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.51f)
    }

    val spaceship = AnimatedActor( spaceshipSS, 4, 6, 14,
        MainScreen.BG_HEIGHT * 0.23f ,MainScreen.BG_HEIGHT * 0.15f,
        frameCount = 19).also {
        it.rotation = -9f
        it.x = MainScreen.BG_WIDTH * 0.3f
        it.y = MainScreen.BG_HEIGHT * 0.63f
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.32f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.66f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.295f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.708f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.52f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.767f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.726f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.716f)
        // 5
        it.hitBox.add(MainScreen.BG_WIDTH * 0.528f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.663f)
    }

    val alienship = AnimatedActor( alienshipSS, 5, 4, 15,
        MainScreen.BG_HEIGHT * 0.134f ,MainScreen.BG_HEIGHT * 0.134f).also {
        it.x = MainScreen.BG_WIDTH * 0.74f
        it.y = MainScreen.BG_HEIGHT * 0.48f
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.72f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.54f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.76f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.61f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.91f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.62f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.99f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.53f)
        // 5
        it.hitBox.add(MainScreen.BG_WIDTH * 0.92f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.47f)
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