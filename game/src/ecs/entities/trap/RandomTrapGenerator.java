package ecs.entities.trap;

import ecs.entities.Entity;
import ecs.entities.Trap;
import starter.Game;


import static com.badlogic.gdx.math.MathUtils.random;

public class RandomTrapGenerator {

    private Entity hero;

    /**
     * Add random Traps to dungeon based on level.
     * <p>
     * Generate random amount of traps based on the current level.
     * Change the strength of the traps based on the current level.
     *
     * @author timo.haverich@hsbi.de
     */

    public RandomTrapGenerator(Entity hero) {
        this.hero = hero;
    }

    public void spawnRandomTrap(int level) {

        int trapAmount = random.nextInt(level/6, (level/6)+1);

        int randomTrap;

        for (int i = 0; i < trapAmount; i++) {

            randomTrap = random.nextInt(0,3);

            switch (randomTrap) {
                case 0 -> {
                    Trap spikeTrap = new SpikeTrap();
                    Game.addEntity(spikeTrap);
                }
                case 1 -> {
                    Trap tpTrap = new TpTrap(this.hero);
                    Game.addEntity(tpTrap);
                }
                case 2 -> {
                    Trap spawnTrap = new SpawnTrap();
                    Game.addEntity(spawnTrap);
                }
            }
        }

    }

}
