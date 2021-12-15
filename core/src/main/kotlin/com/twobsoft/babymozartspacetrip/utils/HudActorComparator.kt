package com.twobsoft.babymozartspacetrip.utils
import com.badlogic.gdx.scenes.scene2d.Actor
import com.twobsoft.babymozartspacetrip.hud.HudActor


class HudActorComparator : Comparator<Actor?> {
    override fun compare(p0: Actor?, p1: Actor?): Int {
        if (p0 is HudActor && p1 is HudActor && p0.stageZIndex == p1.stageZIndex) {
            return 0
        }
        return if (p0 is HudActor && p1 is HudActor && p0.stageZIndex < p1.stageZIndex) {
            -1
        } else 1
    }
}