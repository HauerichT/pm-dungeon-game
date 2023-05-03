package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import graphic.Animation;

public abstract class Trap extends Entity {



    public Trap() {
        super();
        new PositionComponent(this);

    }
}
