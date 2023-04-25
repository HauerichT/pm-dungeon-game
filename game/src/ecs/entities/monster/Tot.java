package ecs.entities.monster;

import ecs.components.ai.idle.PatrouilleWalk;
import ecs.components.ai.idle.RadiusWalk;
import ecs.entities.Monster;

public class Tot extends Monster {

    public Tot() {
        super(
            "character/monster/tot/idleLeft",
            "character/monster/tot/runRight",
            "character/monster/tot/runLeft",
            "character/monster/tot/runRight",
            new PatrouilleWalk(2.0f,3,1, PatrouilleWalk.MODE.RANDOM),
            0.02f,
            0.02f,
            0.2f,
            0.5f
        );
    }}
