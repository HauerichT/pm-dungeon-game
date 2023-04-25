package ecs.entities.monster;

import ecs.components.ai.idle.StaticRadiusWalk;
import ecs.entities.Monster;

public class Zombie extends Monster {

    public Zombie() {
        super(
            "character/monster/zombie/idleLeft",
            "character/monster/zombie/runRight",
            "character/monster/zombie/runLeft",
            "character/monster/zombie/runRight",
            new StaticRadiusWalk(1.0f,1),
            0.02f,
            0.02f,
            0.05f,
            0.5f
        );
    }
}
