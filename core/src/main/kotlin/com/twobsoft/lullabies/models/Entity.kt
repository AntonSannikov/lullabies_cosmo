package com.twobsoft.lullabies.models
import com.badlogic.gdx.scenes.scene2d.Actor


abstract class Entity() {
    abstract val stageNumber: Int
    abstract val all: Array<Actor>
}
