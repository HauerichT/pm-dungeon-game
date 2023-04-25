package ecs.entities.monster;

import ecs.components.ai.idle.RadiusWalk;
import ecs.entities.Monster;

public class Skeleton extends Monster {

    public Skeleton() {
        super(
            "character/monster/skeleton/idleLeft",
            "character/monster/skeleton/runRight",
            "character/monster/skeleton/runLeft",
            "character/monster/skeleton/runRight",
            new RadiusWalk(1.0f,1),
            0.03f,
            0.03f,
            0.1f,
            0.5f
        );
    }
}
