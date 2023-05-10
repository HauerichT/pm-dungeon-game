package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import ecs.components.ai.idle.FollowHeroWalk;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.collision.ICollide;
import ecs.entities.items.HealPotion;
import ecs.entities.items.StrengthPotion;
import ecs.entities.items.Sword;
import ecs.entities.monster.Skeleton;
import ecs.entities.monster.Tot;
import ecs.entities.monster.Zombie;
import graphic.Animation;
import starter.Game;
import tools.Point;

import static com.badlogic.gdx.math.MathUtils.random;

public class Gravestone extends Entity{
    private final Entity ghost;
    private final Entity hero;
    private Entity collide;
    private final RandomEntityGenerator randomEntityGenerator;

    public Gravestone(Entity ghost,Entity hero){
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
            this.getComponent(HitboxComponent.class).ifPresent(
                collide -> {
                    this.collide = ((HitboxComponent) collide).getCollide();
                    if (((Ghost) ghost).getFollowWalk() && this.collide.equals(hero)){
                        int rnd = random.nextInt(2);
                        if (rnd == 0) {
                            randomEntityGenerator.spwanRandomItems();
                        } else {
                            randomEntityGenerator.spawnRandomMonster();
                        }
                        Game.removeEntity(this);
                        Game.removeEntity(ghost);
                    }
                }
            );
        }

    }


    private void setupHitboxComponent() {
        new HitboxComponent(
            this,
            (you, other, direction) -> setupAnimationComponent(true),
            (you, other, direction) -> setupAnimationComponent(false));

    }

}
