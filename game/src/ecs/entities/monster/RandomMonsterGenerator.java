package ecs.entities.monster;


import ecs.entities.Monster;
import starter.Game;


import static com.badlogic.gdx.math.MathUtils.random;

public class RandomMonsterGenerator {


    /**
     * Add random Monsters to dungeon based on level.
     * <p>
     * Generate random amount of monsters based on the current level.
     * Change the strength of the monsters based on the current level.
     *
     * @author timo.haverich@hsbi.de
     */

    public void spawnRandomMonster(int level) {

        int monsterAmount = random.nextInt(level/4, (level/4)+1);
        int strengthAddOn = 1 + level/7;
        boolean totCanBeSpawned = level > 10;

        int randomMonster;

        for (int i = 0; i < monsterAmount+1; i++) {

            if (totCanBeSpawned) {
                randomMonster = random.nextInt(0,3);
            }
            else {
                randomMonster = random.nextInt(0,2);
            }

            switch (randomMonster) {
                case 0 -> {
                    Monster skeleton = new Skeleton();
                    skeleton.setDmg((int) (skeleton.getDmg() * strengthAddOn));
                    System.out.println("Skelet: " + skeleton.getDmg());
                    Game.addEntity(skeleton);
                }
                case 1 -> {
                    Monster zombie = new Zombie();
                    zombie.setDmg((int) (zombie.getDmg() * strengthAddOn));
                    System.out.println("Zombie: " + zombie.getDmg());
                    Game.addEntity(zombie);
                }
                case 2 -> {
                    Monster tot = new Tot();
                    tot.setDmg((int) (tot.getDmg() * strengthAddOn));
                    System.out.println("Tot: " + tot.getDmg());
                    Game.addEntity(tot);
                }
            }
        }

    }

}
