package com.twobsoft.lullabies

import com.badlogic.gdx.scenes.scene2d.Actor



class ActorRenderComparator : Comparator<Actor> {
    override fun compare(a: Actor, b: Actor): Int {

    if (a.zIndex == b.zIndex) {
        return 0
    }
    return if (a.zIndex > b.zIndex) {
        -1
    } else 1
}


}