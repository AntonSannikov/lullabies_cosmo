package com.twobsoft.babymozartspacetrip.models
import com.badlogic.gdx.scenes.scene2d.Actor


abstract class Entity() {
    abstract val stageNumber: Int
    abstract val all: Array<Actor>
}
