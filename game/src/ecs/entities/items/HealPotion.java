package ecs.entities.items;

import ecs.entities.Item;

import java.io.Serializable;

/** HealPotion is an item which will be spawned in Dungeon and can be collected by Hero. */
public class HealPotion extends Item {

    public HealPotion() {
        super("character/healpotion/healpotion.png", "Heal Potion", "Heilt den Helden");
    }
}
