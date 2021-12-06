package com.twobsoft.lullabies


import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.twobsoft.lullabies.models.*
import com.twobsoft.lullabies.hud.HudModel
import ktx.assets.getAsset



object Assets {

    val manager: AssetManager = AssetManager()

    // UI
    val hudTextures = arrayOf(
        AssetDescriptor<Texture>(HudModel.frameTex          , Texture::class.java),
        AssetDescriptor<Texture>(HudModel.panelUpTex        , Texture::class.java),
        AssetDescriptor<Texture>(HudModel.optionsTex        , Texture::class.java),
        AssetDescriptor<Texture>(HudModel.lampLightTex      , Texture::class.java),
        AssetDescriptor<Texture>(HudModel.lampTex           , Texture::class.java),
        AssetDescriptor<Texture>(HudModel.shareButtonTex    , Texture::class.java),
        AssetDescriptor<Texture>(HudModel.shareAntennasTex  , Texture::class.java),
        AssetDescriptor<Texture>(HudModel.deckTex           , Texture::class.java),
        AssetDescriptor<Texture>(HudModel.playlistTex       , Texture::class.java),
        AssetDescriptor<Texture>(HudModel.joystickTex       , Texture::class.java),
        AssetDescriptor<Texture>(HudModel.arrowLTex         , Texture::class.java),
        AssetDescriptor<Texture>(HudModel.playTex           , Texture::class.java),
        AssetDescriptor<Texture>(HudModel.clockTex          , Texture::class.java),
        AssetDescriptor<Texture>(HudModel.arrowRTex         , Texture::class.java),
    )

    // MENU
    val menuTextures = arrayOf(
        AssetDescriptor<Texture>(MenuModel.backgroundTex, Texture::class.java),
        AssetDescriptor<Texture>(MenuModel.radarTex, Texture::class.java),
        AssetDescriptor<Texture>(MenuModel.sunSS, Texture::class.java),
        AssetDescriptor<Texture>(MenuModel.mercurySS, Texture::class.java),
        AssetDescriptor<Texture>(MenuModel.venusSS, Texture::class.java),
        AssetDescriptor<Texture>(MenuModel.earthSS, Texture::class.java),
        AssetDescriptor<Texture>(MenuModel.moonSS, Texture::class.java),
        AssetDescriptor<Texture>(MenuModel.marsSS, Texture::class.java),
        AssetDescriptor<Texture>(MenuModel.jupiterSS, Texture::class.java),
        AssetDescriptor<Texture>(MenuModel.saturnSS, Texture::class.java),
        AssetDescriptor<Texture>(MenuModel.uranusSS, Texture::class.java),
        AssetDescriptor<Texture>(MenuModel.neptuneSS, Texture::class.java),
        AssetDescriptor<Texture>(MenuModel.plutoSS, Texture::class.java),
        AssetDescriptor<Texture>(MenuModel.asteroidSS, Texture::class.java),
        AssetDescriptor<Texture>(MenuModel.cometSS, Texture::class.java),
        AssetDescriptor<Texture>(MenuModel.spaceshipSS, Texture::class.java),
        AssetDescriptor<Texture>(MenuModel.alienshipSS, Texture::class.java),
    )

    // SUN
    val sunTextures = arrayOf(
        AssetDescriptor<Texture>(SunModel.backgroundTex     , Texture::class.java),
        AssetDescriptor<Texture>(SunModel.starsTex          , Texture::class.java),
        AssetDescriptor<Texture>(SunModel.planetTex         , Texture::class.java),
        AssetDescriptor<Texture>(SunModel.lavaTex           , Texture::class.java),
        AssetDescriptor<Texture>(SunModel.protuberanceTex   , Texture::class.java),
        AssetDescriptor<Texture>(SunModel.plan2Tex          , Texture::class.java),
        AssetDescriptor<Texture>(SunModel.plan1Tex          , Texture::class.java),
        AssetDescriptor<Texture>(SunModel.sparksTex         , Texture::class.java)
    )

    // MERCURY
    val mercuryTextures = arrayOf(
        AssetDescriptor<Texture>(MercuryModel.backgroundTex , Texture::class.java),
        AssetDescriptor<Texture>(MercuryModel.planet2Tex    , Texture::class.java),
        AssetDescriptor<Texture>(MercuryModel.planet1Tex    , Texture::class.java),
        AssetDescriptor<Texture>(MercuryModel.plan2Tex      , Texture::class.java),
        AssetDescriptor<Texture>(MercuryModel.plan1Tex      , Texture::class.java),
    )

    // VENUS
    val venusTextures = arrayOf(
        AssetDescriptor<Texture>(VenusModel.backgroundTex   , Texture::class.java),
        AssetDescriptor<Texture>(VenusModel.plan4Tex        , Texture::class.java),
        AssetDescriptor<Texture>(VenusModel.plan3Tex        , Texture::class.java),
        AssetDescriptor<Texture>(VenusModel.plan2Tex        , Texture::class.java),
        AssetDescriptor<Texture>(VenusModel.plan1Tex        , Texture::class.java),

    )


    // EARTH
    val earthTextures = arrayOf(
        AssetDescriptor<Texture>(EarthModel.cloud1Tex   , Texture::class.java),
        AssetDescriptor<Texture>(EarthModel.cloud2Tex   , Texture::class.java),
        AssetDescriptor<Texture>(EarthModel.cloud3Tex   , Texture::class.java),
        AssetDescriptor<Texture>(EarthModel.skyTex      , Texture::class.java),
        AssetDescriptor<Texture>(EarthModel.rockTex     , Texture::class.java),
        AssetDescriptor<Texture>(EarthModel.riverTex    , Texture::class.java),
        AssetDescriptor<Texture>(EarthModel.forestTex   , Texture::class.java),
        AssetDescriptor<Texture>(EarthModel.smokeTex    , Texture::class.java),
        AssetDescriptor<Texture>(EarthModel.coastTex    , Texture::class.java),
        AssetDescriptor<Texture>(EarthModel.treerockTex , Texture::class.java),
        AssetDescriptor<Texture>(EarthModel.leafTex     , Texture::class.java),
    )

    // MOON
    val moonTextures = arrayOf(
        AssetDescriptor<Texture>(MoonModel.backgroundTex, Texture::class.java),
        AssetDescriptor<Texture>(MoonModel.planetsTex   , Texture::class.java),
        AssetDescriptor<Texture>(MoonModel.zemlyaTex    , Texture::class.java),
        AssetDescriptor<Texture>(MoonModel.star1Tex     , Texture::class.java),
        AssetDescriptor<Texture>(MoonModel.star2Tex     , Texture::class.java),
        AssetDescriptor<Texture>(MoonModel.star3Tex     , Texture::class.java),
        AssetDescriptor<Texture>(MoonModel.lunaTex      , Texture::class.java),
        AssetDescriptor<Texture>(MoonModel.goryTex      , Texture::class.java),
        AssetDescriptor<Texture>(MoonModel.flagTex      , Texture::class.java),
    )

    // MARS
    val marsTextures = arrayOf(
        AssetDescriptor<Texture>(MarsModel.backgroundTex, Texture::class.java),
        AssetDescriptor<Texture>(MarsModel.oblako5Tex   , Texture::class.java),
        AssetDescriptor<Texture>(MarsModel.oblako4Tex   , Texture::class.java),
        AssetDescriptor<Texture>(MarsModel.oblako3Tex   , Texture::class.java),
        AssetDescriptor<Texture>(MarsModel.oblako2Tex   , Texture::class.java),
        AssetDescriptor<Texture>(MarsModel.oblako1Tex   , Texture::class.java),
        AssetDescriptor<Texture>(MarsModel.plan3Tex     , Texture::class.java),
        AssetDescriptor<Texture>(MarsModel.plan2Tex     , Texture::class.java),
        AssetDescriptor<Texture>(MarsModel.plan1Tex     , Texture::class.java),
    )

    // JUPITER
    val jupiterTextures = arrayOf(
        AssetDescriptor<Texture>(JupiterModel.backgroundTex , Texture::class.java),
        AssetDescriptor<Texture>(JupiterModel.plan5Tex      , Texture::class.java),
        AssetDescriptor<Texture>(JupiterModel.plan4Tex      , Texture::class.java),
        AssetDescriptor<Texture>(JupiterModel.plan3Tex      , Texture::class.java),
        AssetDescriptor<Texture>(JupiterModel.plan2Tex      , Texture::class.java),
        AssetDescriptor<Texture>(JupiterModel.plan1Tex      , Texture::class.java),
    )

    // SATURN
    val saturnTextures = arrayOf(
        AssetDescriptor<Texture>(SaturnModel.backgroundTex  , Texture::class.java),
        AssetDescriptor<Texture>(SaturnModel.sunTex         , Texture::class.java),
        AssetDescriptor<Texture>(SaturnModel.ringTex        , Texture::class.java),
        AssetDescriptor<Texture>(SaturnModel.plan2Tex       , Texture::class.java),
        AssetDescriptor<Texture>(SaturnModel.plan1Tex       , Texture::class.java),
    )

    // URANUS
    val uranusTextures = arrayOf(
        AssetDescriptor<Texture>(UranusModel.backgroundTex  , Texture::class.java),
        AssetDescriptor<Texture>(UranusModel.plan4Tex       , Texture::class.java),
        AssetDescriptor<Texture>(UranusModel.plan3Tex       , Texture::class.java),
        AssetDescriptor<Texture>(UranusModel.plan2Tex       , Texture::class.java),
        AssetDescriptor<Texture>(UranusModel.plan1Tex       , Texture::class.java),
    )

    // NEPTUNE
    val neptuneTextures = arrayOf(
        AssetDescriptor<Texture>(NeptuneModel.backgroundTex  , Texture::class.java),
        AssetDescriptor<Texture>(NeptuneModel.plan3Tex       , Texture::class.java),
        AssetDescriptor<Texture>(NeptuneModel.plan2Tex       , Texture::class.java),
        AssetDescriptor<Texture>(NeptuneModel.plan1Tex       , Texture::class.java),
    )

    // PLUTO
    val plutoTextures= arrayOf(
        AssetDescriptor<Texture>(PlutoModel.backgroundTex   , Texture::class.java),
        AssetDescriptor<Texture>(PlutoModel.sunTex          , Texture::class.java),
        AssetDescriptor<Texture>(PlutoModel.starsTex        , Texture::class.java),
        AssetDescriptor<Texture>(PlutoModel.planetTex       , Texture::class.java),
        AssetDescriptor<Texture>(PlutoModel.plan3Tex        , Texture::class.java),
        AssetDescriptor<Texture>(PlutoModel.plan2Tex        , Texture::class.java),
        AssetDescriptor<Texture>(PlutoModel.plan1Tex        , Texture::class.java),
    )

    // ASTEROID
    val asteroidTextures= arrayOf(
        AssetDescriptor<Texture>(AsteroidModel.backgroundTex    , Texture::class.java),
        AssetDescriptor<Texture>(AsteroidModel.plan5Tex         , Texture::class.java),
        AssetDescriptor<Texture>(AsteroidModel.plan4Tex         , Texture::class.java),
        AssetDescriptor<Texture>(AsteroidModel.plan3Tex         , Texture::class.java),
        AssetDescriptor<Texture>(AsteroidModel.plan2Tex         , Texture::class.java),
        AssetDescriptor<Texture>(AsteroidModel.plan1Tex         , Texture::class.java),
        AssetDescriptor<Texture>(AsteroidModel.flareTex         , Texture::class.java),
    )

    // COMET
    val cometTextures= arrayOf(
        AssetDescriptor<Texture>(CometModel.backgroundTex   , Texture::class.java),
        AssetDescriptor<Texture>(CometModel.plan4Tex        , Texture::class.java),
        AssetDescriptor<Texture>(CometModel.plan3Tex        , Texture::class.java),
        AssetDescriptor<Texture>(CometModel.plan2Tex        , Texture::class.java),
        AssetDescriptor<Texture>(CometModel.plan1Tex        , Texture::class.java),
    )

    // SPACE SHIP
    val spaceshipTextures= arrayOf(
        AssetDescriptor<Texture>(SpaceshipModel.backgroundTex   , Texture::class.java),
        AssetDescriptor<Texture>(SpaceshipModel.earthTex        , Texture::class.java),
        AssetDescriptor<Texture>(SpaceshipModel.plan3Tex        , Texture::class.java),
        AssetDescriptor<Texture>(SpaceshipModel.plan2Tex        , Texture::class.java),
        AssetDescriptor<Texture>(SpaceshipModel.plan1Tex        , Texture::class.java),
    )

    // ALIEN SHIP
    val alienshipTextures= arrayOf(
        AssetDescriptor<Texture>(AlienshipModel.backgroundTex   , Texture::class.java),
        AssetDescriptor<Texture>(AlienshipModel.plan4Tex        , Texture::class.java),
        AssetDescriptor<Texture>(AlienshipModel.plan3Tex        , Texture::class.java),
        AssetDescriptor<Texture>(AlienshipModel.plan2Tex        , Texture::class.java),
        AssetDescriptor<Texture>(AlienshipModel.plan1Tex        , Texture::class.java),
        AssetDescriptor<Texture>(AlienshipModel.hologramTex     , Texture::class.java),
        AssetDescriptor<Texture>(AlienshipModel.flareTex        , Texture::class.java),
    )

    // MISC
    val rgbNoise = AssetDescriptor<Texture>("misc/rgb_noise.png", Texture::class.java)


    // ANIMATIONS
    val animations = arrayOf(
        AssetDescriptor<Texture>(AlienshipModel.backgroundTex   , Texture::class.java),
        AssetDescriptor<Texture>(AlienshipModel.plan4Tex        , Texture::class.java),
        AssetDescriptor<Texture>(AlienshipModel.plan3Tex        , Texture::class.java),
        AssetDescriptor<Texture>(AlienshipModel.plan2Tex        , Texture::class.java),
        AssetDescriptor<Texture>(AlienshipModel.plan1Tex        , Texture::class.java),
        AssetDescriptor<Texture>(AlienshipModel.hologramTex     , Texture::class.java),
        AssetDescriptor<Texture>(AlienshipModel.flareTex        , Texture::class.java),
    )



    fun loadMisc() {
        manager.load(rgbNoise)
    }

    fun getMisc() {
        return manager.getAsset("misc/rgb_noise.png")
    }

    fun getAsset(fileName: String): Texture {
        return manager.getAsset(fileName)
    }


    fun load(screenNumber: Int) {
        when(screenNumber){
            0 -> menuTextures.forEach { manager.load(it) }
            1 -> sunTextures.forEach { manager.load(it) }
            2 -> mercuryTextures.forEach { manager.load(it) }
            3 -> venusTextures.forEach { manager.load(it) }
            4 -> earthTextures.forEach { manager.load(it) }
            5 -> moonTextures.forEach { manager.load(it) }
            6 -> marsTextures.forEach { manager.load(it) }
            7 -> jupiterTextures.forEach { manager.load(it) }
            8 -> saturnTextures.forEach { manager.load(it) }
            9 -> uranusTextures.forEach { manager.load(it) }
            10 -> neptuneTextures.forEach { manager.load(it) }
            11 -> plutoTextures.forEach { manager.load(it) }
            12 -> asteroidTextures.forEach { manager.load(it) }
            13 -> cometTextures.forEach { manager.load(it) }
            14 -> spaceshipTextures.forEach { manager.load(it) }
            15 -> alienshipTextures.forEach { manager.load(it) }
        }
    }

    fun loadHud() {
        hudTextures.forEach { manager.load(it) }
    }

    fun loadAnimation() {

    }


    fun dispose() {
        manager.dispose()
    }
}

