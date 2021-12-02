package com.twobsoft.lullabies

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.Gdx

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Action


class ParallaxBackground(val tex: String) {

    val layers: ArrayList<Texture> = arrayListOf<Texture>()
    val actions: ArrayList<Action> = arrayListOf<Action>()

}