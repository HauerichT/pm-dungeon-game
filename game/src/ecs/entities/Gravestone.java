package ecs.entities;

import static com.badlogic.gdx.math.MathUtils.random;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import graphic.Animation;
import starter.Game;

import java.io.Serializable;

/** Gravestone for the friendly NPC-Ghost ---> Ghost Class */
public class Gravestone extends Entity {
    private final Entity ghost;
    private final Entity hero;
    private Entity collide;
    private final RandomEntityGenerator randomEntityGenerator;

    public Gravestone(Entity ghost, Entity hero) {
        super();
        new PositionComponent(this);
        randomEntityGenerator = new RandomEntityGenerator();
        setupHitboxComponent();
        setupAnimationComponent(false);
        this.ghost = ghost;
        this.hero = hero;
    }

    private void setupAnimationComponent(boolean a) {
        String gravestone = "character/grabstein/grave.png";
        Animation graveStone = AnimationBuilder.buildAnimation(gravestone);
        new AnimationComponent(this, graveStone);

        if (a) {
            this.getComponent(HitboxComponent.class)
                    .ifPresent(
                            collide -> {
                                this.collide = ((HitboxComponent) collide).getCollide();
                                if (((Ghost) ghost).getFollowWalk() && this.collide.equals(hero)) {
                                    int rnd = random.nextInt(2);
                                    if (rnd == 0) {
                                        randomEntityGenerator.spwanRandomItems();
                                    } else {
                                        randomEntityGenerator.spawnRandomMonster();
                                    }
                                    Game.removeEntity(this);
                                    Game.removeEntity(ghost);
                                }
                            });
        }
    }

    private void setupHitboxComponent() {
        new HitboxComponent(
                this,
                (you, other, direction) -> setupAnimationComponent(true),
                (you, other, direction) -> setupAnimationComponent(false));
    }
}
