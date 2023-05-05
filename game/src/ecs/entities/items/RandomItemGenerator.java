package ecs.entities.items;

import ecs.entities.Item;
import starter.Game;


import static com.badlogic.gdx.math.MathUtils.random;

public class RandomItemGenerator {


    /**
     * Add random Items to dungeon based on level.
     * <p>
     * Generate random amount of items based on the current level.
     *
     * @author timo.haverich@hsbi.de
     */

    public void spwanRandomItems(int level) {

        int itemAmount = random.nextInt(level/4, (level/4)+1);

        int randomItem;

        for (int i = 0; i < itemAmount; i++) {
            randomItem = random.nextInt(0,4);
            switch (randomItem) {
                case 0 -> {
                    Item sword = new Sword();
                    Game.addEntity(sword);
                }
                case 1 -> {
                    Item healPotion = new HealPotion();
                    Game.addEntity(healPotion);
                }
                case 2 -> {
                    Item strengthPotion = new StrengthPotion();
                    Game.addEntity(strengthPotion);
                }
                case 3 -> {
                    Item bag = new Bag();
                    Game.addEntity(bag);
                }
            }
        }

    }

}
