package ecs.entities;

import ecs.components.PositionComponent;

/** Trap is the superclass for all types of traps which will be spawned in Dungeon. */
public abstract class Trap extends Entity {

    public Trap() {
        super();
        new PositionComponent(this);
    }

}
