package ecs.entities;

import static com.badlogic.gdx.math.MathUtils.random;

import ecs.entities.items.Bag;
import ecs.entities.items.HealPotion;
import ecs.entities.items.StrengthPotion;
import ecs.entities.items.Sword;
import ecs.entities.monster.Skeleton;
import ecs.entities.monster.Tot;
import ecs.entities.monster.Zombie;
import ecs.entities.trap.SpawnTrap;
import ecs.entities.trap.SpikeTrap;
import ecs.entities.trap.TpTrap;
import starter.Game;

public class RandomEntityGenerator {

    /**
     * Add random Items to dungeon based on level.
     *
     * <p>Generate random amount of items based on the current level.
     *
     * @author timo.haverich@hsbi.de
     */
    public void spwanRandomItems() {
        int level = Game.getLevelCounter();

        int itemAmount = random.nextInt(level / 4, (level / 4) + 1);

        int randomItem;

        for (int i = 0; i < itemAmount; i++) {
            randomItem = random.nextInt(0, 4);
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

    /**
     * Add random Monsters to dungeon based on level.
     *
     * <p>Generate random amount of monsters based on the current level. Change the strength of the
     * monsters based on the current level.
     *
     * @author timo.haverich@hsbi.de
     */
    public void spawnRandomMonster() {
        int level = Game.getLevelCounter();

        int monsterAmount = random.nextInt(level / 2, (level / 2) + 1);
        boolean totCanBeSpawned = level > 10;

        int randomMonster;

        for (int i = 0; i < monsterAmount; i++) {

            if (totCanBeSpawned) {
                randomMonster = random.nextInt(0, 3);
            } else {
                randomMonster = random.nextInt(0, 2);
            }

            switch (randomMonster) {
                case 0 -> {
                    Monster skeleton = new Skeleton();
                    Game.addEntity(skeleton);
                }
                case 1 -> {
                    Monster zombie = new Zombie();
                    Game.addEntity(zombie);
                }
                case 2 -> {
                    Monster tot = new Tot();
                    Game.addEntity(tot);
                }
            }
        }
    }

    /**
     * Add random Traps to dungeon based on level.
     *
     * <p>Generate random amount of traps based on the current level. Change the strength of the
     * traps based on the current level.
     *
     * @author timo.haverich@hsbi.de
     */
    public void spawnRandomTrap() {
        int level = Game.getLevelCounter();

        int trapAmount = random.nextInt(level / 6, (level / 6) + 1);

        int randomTrap;

        for (int i = 0; i < trapAmount; i++) {

            randomTrap = random.nextInt(0, 3);

            switch (randomTrap) {
                case 0 -> {
                    Trap spikeTrap = new SpikeTrap();
                    Game.addEntity(spikeTrap);
                }
                case 1 -> {
                    Trap tpTrap = new TpTrap(Game.getHero().get());
                    Game.addEntity(tpTrap);
                }
                case 2 -> {
                    Trap spawnTrap = new SpawnTrap();
                    Game.addEntity(spawnTrap);
                }
            }
        }
    }

    /**
     * Add ghost and gravestone to dungeon based on level.
     *
     * @author timo.haverich@hsbi.de
     */
    public void spawnGhostAndGravestone() {

        int level = Game.getLevelCounter();

        if (level % 5 == 0 && level != 0) {
            Ghost ghost = new Ghost();
            Gravestone gravestone = new Gravestone(ghost, Game.getHero().get());
            Game.addEntity(ghost);
            Game.addEntity(gravestone);
            Game.setGhost(ghost);
        }
    }
}
