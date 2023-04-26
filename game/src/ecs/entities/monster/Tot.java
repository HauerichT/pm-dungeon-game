package ecs.entities.monster;

import ecs.components.ai.idle.FollowHeroWalk;
import ecs.entities.Monster;

public class Tot extends Monster {

    public Tot() {
        super(
            "character/monster/tot/idleLeft",
            "character/monster/tot/runRight",
            "character/monster/tot/runLeft",
            "character/monster/tot/runRight",
            new FollowHeroWalk(),
            0.04f,
            0.04f,
            4.0f,
            15.0f
        );
    }
}