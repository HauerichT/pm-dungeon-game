package ecs.entities.items;

import ecs.entities.Item;

/** Sword is an item which will be spawned in Dungeon and can be collected by Hero. */
public class Sword extends Item {
    public Sword() {
        super("character/sword/weapon.png", "Sword", "Starkes Schwert");
    }
}
