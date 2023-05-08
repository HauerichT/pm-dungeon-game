package ecs.entities.items;

import ecs.entities.Item;

/**
 * Bag is an item which will be spawned in Dungeon and can be collected by Hero.
 */
public class Bag extends Item  {

    public Bag() {
        super("character/bag/bag.png","Bag","Erweitert das Inventar");
    }

}
