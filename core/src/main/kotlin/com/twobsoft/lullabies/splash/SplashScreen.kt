package com.twobsoft.lullabies.splash

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.twobsoft.lullabies.LullabiesGame
import com.twobsoft.lullabies.MainScreen
import ktx.app.KtxScreen

class SplashScreen(val game: LullabiesGame): KtxScreen {

    init {
        game.assets.loadHud()
        game.assets.load(0)
        game.assets.load(1)
        game.assets.load(2)
        game.assets.load(3)
        game.assets.load(4)
        game.assets.load(5)
        game.assets.load(6)
        game.assets.load(7)
        game.assets.load(8)
        game.assets.load(9)
        game.assets.load(10)
        game.assets.load(11)
        game.assets.load(12)
        game.assets.load(13)
        game.assets.load(14)
        game.assets.load(15)
    }

    override fun render(delta: Float) {
        val gl = Gdx.graphics.gL20
        gl.glClearColor(1f, 0f, 0f, 1f)
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        if (game.assets.manager.update()) {
            game.addScreen(MainScreen(game))
            game.setScreen<MainScreen>()
        }

    }
}