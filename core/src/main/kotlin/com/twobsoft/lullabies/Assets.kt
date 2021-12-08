package com.twobsoft.lullabies


import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.twobsoft.lullabies.models.*
import com.twobsoft.lullabies.hud.HudModel
import ktx.assets.getAsset



class Assets {

    val manager: AssetManager = AssetManager()

    // UI
    fun loadHud() {
        HudModel.all.forEach { manager.load(it, Texture::class.java) }
    }

    // MENU
    fun loadMenu() {
        MenuModel.all.forEach { manager.load(it, Texture::class.java)  }
    }


    fun getAsset(fileName: String): Texture {
        return manager.getAsset(fileName)
    }


    fun load(screenNumber: Int) {
        when(screenNumber){
            0 -> loadMenu()
            1 -> SunModel.all.forEach { manager.load(it, Texture::class.java) }
            2 -> MercuryModel.all.forEach { manager.load(it, Texture::class.java) }
            3 -> VenusModel.all.forEach { manager.load(it, Texture::class.java) }
            4 -> EarthModel.all.forEach { manager.load(it, Texture::class.java) }
            5 -> MoonModel.all.forEach { manager.load(it, Texture::class.java) }
            6 -> MarsModel.all.forEach { manager.load(it, Texture::class.java) }
            7 -> JupiterModel.all.forEach { manager.load(it, Texture::class.java) }
            8 -> SaturnModel.all.forEach { manager.load(it, Texture::class.java) }
            9 -> UranusModel.all.forEach { manager.load(it, Texture::class.java) }
            10 -> NeptuneModel.all.forEach { manager.load(it, Texture::class.java) }
            11 -> PlutoModel.all.forEach { manager.load(it, Texture::class.java) }
            12 -> AsteroidModel.all.forEach { manager.load(it, Texture::class.java) }
            13 -> CometModel.all.forEach { manager.load(it, Texture::class.java) }
            14 -> SpaceshipModel.all.forEach { manager.load(it, Texture::class.java) }
            15 -> AlienshipModel.all.forEach { manager.load(it, Texture::class.java) }
        }
    }



    fun dispose() {
        manager.dispose()
    }
}

