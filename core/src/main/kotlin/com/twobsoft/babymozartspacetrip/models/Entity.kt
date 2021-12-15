package com.twobsoft.babymozartspacetrip.models
import com.badlogic.gdx.scenes.scene2d.Actor
import com.twobsoft.babymozartspacetrip.components.LayerActor


abstract class Entity() {
    abstract val stageNumber: Int
    abstract val all: Array<Actor>
    abstract val background: LayerActor
}
