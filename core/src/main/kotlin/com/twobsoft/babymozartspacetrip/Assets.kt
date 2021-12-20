package com.twobsoft.babymozartspacetrip


import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.esotericsoftware.spine.SkeletonData
import com.esotericsoftware.spine.utils.SkeletonDataLoader
import com.twobsoft.babymozartspacetrip.hud.HudModel
import com.twobsoft.babymozartspacetrip.menu.MenuSpineModel
import com.twobsoft.babymozartspacetrip.splash.SplashScreenModel
import ktx.assets.async.loadSync
import ktx.assets.getAsset
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator

import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver

import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter





class Assets {

    val manager: AssetManager = AssetManager()
    val skeletonLoader = SkeletonDataLoader(manager.fileHandleResolver)

    // SPLASH
    fun loadSplash() {
        SplashScreenModel.all.forEach { manager.load(it, Texture::class.java) }
    }

    fun unloadSplash() {
        SplashScreenModel.all.forEach { manager.unload(it) }
    }

    // UI
    fun loadHud() {
        HudModel.allSkeletons.forEach { skeletonLoader.loadSync(manager, AssetDescriptor(it, SkeletonData::class.java)) }
        manager.finishLoading()
        HudModel.all.forEach { manager.load(it, Texture::class.java) }

    }

//    fun loadFont() {
//        val resolver: FileHandleResolver = InternalFileHandleResolver()
//        manager.setLoader(FreeTypeFontGenerator::class.java, FreeTypeFontGeneratorLoader(resolver))
//        manager.setLoader(BitmapFont::class.java, ".ttf", FreetypeFontLoader(resolver))
//
//        val parms = FreeTypeFontLoaderParameter()
//        parms.fontFileName          = "fonts/digital-7_mono.ttf"
//        parms.fontParameters.size   = 15
//
//        manager.load("digital-7_mono.ttf", BitmapFont::class.java, parms)
//    }

    // MENU
    fun loadMenu() {
        MenuSpineModel.all.forEach { manager.load(it, Texture::class.java)  }
        manager.load("planets/sleep.jpg", Texture::class.java)
        MenuSpineModel.allSkeletons.forEach { skeletonLoader.loadSync(manager, AssetDescriptor(it, SkeletonData::class.java)) }
    }


    fun getAsset(fileName: String): Texture {
        return manager.getAsset(fileName)
    }



    fun dispose() {
        manager.dispose()
    }
}

