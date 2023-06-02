package ecs.entities.items;

import ecs.entities.Item;

/** StrengthPotion is an item which will be spawned in Dungeon and can be collected by Hero. */
public class StrengthPotion extends Item {

    public StrengthPotion() {
        super(
                "character/strengthpotion/strengthpotion.png",
                "Strength Potion",
                "Erhöht die Stärke des Helden");
    }
}
