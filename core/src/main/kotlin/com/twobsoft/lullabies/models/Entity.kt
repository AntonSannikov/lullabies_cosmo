package com.twobsoft.lullabies.models
import com.twobsoft.lullabies.GameComponent

abstract class Entity() {
    abstract val stageNumber: Int
    abstract val all: Array<GameComponent>
}
