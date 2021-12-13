package com.twobsoft.lullabies.models

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.FloatArray
import com.twobsoft.lullabies.Assets
import com.twobsoft.lullabies.MainScreen
import com.twobsoft.lullabies.components.LayerActor
import com.twobsoft.lullabies.components.SpineComponent

class MenuSpineModel(val assets: Assets) {

    val timeScale = 0.2f

    companion object {
        const val backgroundTex = "menu/background.png"
        const val radarTex      = "menu/radar.png"

        val all = arrayOf(backgroundTex, radarTex)

        const val sunAtlas      = "menu/sun/sun.atlas"
        const val sunJson       = "menu/sun/json.json"


        val allSkeletons = arrayOf(sunAtlas, sunJson)


    }

    val background = LayerActor(tex = backgroundTex, isMenu = true, assets.getAsset(backgroundTex))
    val radar = LayerActor(tex = radarTex, texture = assets.getAsset(radarTex))


    val sun = SpineComponent(
        TextureAtlas(assets.skeletonLoader.resolve("menu/sun/sun.atlas")),
        assets.skeletonLoader.resolve("menu/sun/json.json"),
        1f
    ).also {
        it.stageNumber = 1
        it.setPos(MainScreen.BG_WIDTH / 2, -MainScreen.BG_HEIGHT * 0.038f)
        it.setTimeScale(timeScale)
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

    val mercury = SpineComponent(
        TextureAtlas(Gdx.files.internal("menu/mercury/mercury.atlas")),
        Gdx.files.internal("menu/mercury/json.json"),
        1f
    ).also {
        it.stageNumber = 2
        it.setPos(MainScreen.BG_WIDTH * 0.85f, MainScreen.BG_HEIGHT * 0.23f)
        it.setTimeScale(timeScale)
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.877f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.22f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.752f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.27f)
        // 3
        it.hitBox.add(MainScreen.BG_WIDTH * 0.828f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.33f)
        // 4
        it.hitBox.add(MainScreen.BG_WIDTH * 0.935f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.28f)
    }

    val venus = SpineComponent(
        TextureAtlas(Gdx.files.internal("menu/venus/venus.atlas")),
        Gdx.files.internal("menu/venus/json.json"),
        1f
    ).also {
        it.stageNumber = 3
        it.setPos(MainScreen.BG_WIDTH * 0.126f, MainScreen.BG_HEIGHT * 0.29f)
        it.setTimeScale(timeScale)
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

    val earth = SpineComponent(
        TextureAtlas(Gdx.files.internal("menu/earth/earth.atlas")),
        Gdx.files.internal("menu/earth/json.json"),
        1f
    ).also {
        it.stageNumber = 4
        it.setPos(MainScreen.BG_WIDTH * 0.652f, MainScreen.BG_HEIGHT * 0.37f)
        it.setTimeScale(timeScale)

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

    val moon = SpineComponent(
        TextureAtlas(Gdx.files.internal("menu/moon/moon.atlas")),
        Gdx.files.internal("menu/moon/json.json"),
        1f
    ).also {
        it.stageNumber = 5
        it.setPos(MainScreen.BG_WIDTH * 0.6f, MainScreen.BG_HEIGHT * 0.5f)
        it.setTimeScale(timeScale)
        // 1
        it.hitBox.add(MainScreen.BG_WIDTH * 0.563f)
        it.hitBox.add(MainScreen.BG_HEIGHT * 0.55f)
        // 2
        it.hitBox.add(MainScreen.BG_WIDTH * 0.545f)
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

    val mars = SpineComponent(
        TextureAtlas(Gdx.files.internal("menu/mars/mars.atlas")),
        Gdx.files.internal("menu/mars/json.json"),
        1f
    ).also {
        it.stageNumber = 6
        it.setPos(MainScreen.BG_WIDTH * 0.877f, MainScreen.BG_HEIGHT * 0.63f)
        it.setTimeScale(timeScale)
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

    val jupiter = SpineComponent(
        TextureAtlas(Gdx.files.internal("menu/saturn/saturn.atlas")),
        Gdx.files.internal("menu/saturn/json.json"),
        1f
    ).also {
        it.stageNumber = 7
        it.setPos(MainScreen.BG_WIDTH * 0.282f, MainScreen.BG_HEIGHT * 0.31f)
        it.setTimeScale(timeScale)
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

    val saturn = SpineComponent(
        TextureAtlas(Gdx.files.internal("menu/jupiter/jupiter.atlas")),
        Gdx.files.internal("menu/jupiter/json.json"),
        1f
    ).also {
        it.stageNumber = 8
        it.setPos(MainScreen.BG_WIDTH * 0.235f, MainScreen.BG_HEIGHT * 0.75f)
        it.setTimeScale(timeScale)
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

    val uranus = SpineComponent(
        TextureAtlas(Gdx.files.internal("menu/uranus/uranus.atlas")),
        Gdx.files.internal("menu/uranus/json.json"),
        1f
    ).also {
        it.stageNumber = 9
        it.setPos(MainScreen.BG_WIDTH * 0.807f, MainScreen.BG_HEIGHT * 0.76f)
        it.setTimeScale(timeScale)
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

    val neptune = SpineComponent(
        TextureAtlas(Gdx.files.internal("menu/neptune/neptune.atlas")),
        Gdx.files.internal("menu/neptune/json.json"),
        1f
    ).also {
        it.stageNumber = 10
        it.setPos(MainScreen.BG_WIDTH * 0.128f, MainScreen.BG_HEIGHT * 0.62f)
        it.setTimeScale(timeScale)
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

    val pluto = SpineComponent(
        TextureAtlas(Gdx.files.internal("menu/pluto/pluto.atlas")),
        Gdx.files.internal("menu/pluto/json.json"),
        1f
    ).also {
        it.stageNumber = 11
        it.setPos(MainScreen.BG_WIDTH * 0.126f, MainScreen.BG_HEIGHT * 0.16f)
        it.setTimeScale(timeScale)

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

    val asteroid = SpineComponent(
        TextureAtlas(Gdx.files.internal("menu/asteroid/asteroid.atlas")),
        Gdx.files.internal("menu/asteroid/json.json"),
        1f
    ).also {
        it.stageNumber = 12
        it.setPos(MainScreen.BG_WIDTH * 0.475f, MainScreen.BG_HEIGHT * 0.82f)
        it.setTimeScale(timeScale)

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

    val comet = SpineComponent(
        TextureAtlas(Gdx.files.internal("menu/comet/comet.atlas")),
        Gdx.files.internal("menu/comet/json.json"),
        0.9f
    ).also {
        it.stageNumber = 13
        it.setPos(MainScreen.BG_WIDTH * 0.31f, MainScreen.BG_HEIGHT * 0.46f)
        it.setTimeScale(timeScale)

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

    val spaceship = SpineComponent(
        TextureAtlas(Gdx.files.internal("menu/spaceship/spaceship.atlas")),
        Gdx.files.internal("menu/spaceship/json.json"),
        0.8f
    ).also {
        it.stageNumber = 14
        it.setPos(MainScreen.BG_WIDTH * 0.45f, MainScreen.BG_HEIGHT * 0.65f)
        it.setTimeScale(timeScale)
        it.rotation = -6f

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

    val alienship = SpineComponent(
        TextureAtlas(Gdx.files.internal("menu/ufo/ufo.atlas")),
        Gdx.files.internal("menu/ufo/json.json"),
        0.8f
    ).also {
        it.stageNumber = 15
        it.setPos(MainScreen.BG_WIDTH * 0.848f, MainScreen.BG_HEIGHT * 0.48f)
        it.setTimeScale(timeScale)

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


    val all = arrayOf(
        sun, mercury, jupiter, venus, earth, moon, mars, saturn,
        uranus, neptune, pluto, asteroid, comet, spaceship, alienship
    )

}