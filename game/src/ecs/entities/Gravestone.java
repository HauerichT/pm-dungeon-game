package ecs.entities;

import static com.badlogic.gdx.math.MathUtils.random;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import graphic.Animation;
import starter.Game;

/** Gravestone for the friendly NPC-Ghost ---> Ghost Class */
public class Gravestone extends Entity {
    private final Entity ghost;
    private final Entity hero;
    private Entity collide;
    private transient RandomEntityGenerator randomEntityGenerator;

    public Gravestone(Entity ghost, Entity hero) {
        super();
        new PositionComponent(this);
        setupRandomEntityGenerator();
        setupHitboxComponent();
        setupAnimationComponent(false);
        this.ghost = ghost;
        this.hero = hero;
    }

    /** Set up the random entity generator */
    public void setupRandomEntityGenerator() {
        randomEntityGenerator = new RandomEntityGenerator();
    }

    /**
     * Set up the animation component
     *
     * @param a checks if gravestone active
     */
    public void setupAnimationComponent(boolean a) {
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

    /** Set up the hitbox component */
    public void setupHitboxComponent() {
        new HitboxComponent(
                this,
                (you, other, direction) -> setupAnimationComponent(true),
                (you, other, direction) -> setupAnimationComponent(false));
    }
}
