package ecs.entities.trap;

import static com.badlogic.gdx.math.MathUtils.random;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HitboxComponent;
import ecs.entities.Trap;
import ecs.entities.monster.Skeleton;
import ecs.entities.monster.Tot;
import ecs.entities.monster.Zombie;
import graphic.Animation;
import starter.Game;

import java.io.Serializable;

public class SpawnTrap extends Trap {
    private final String active = "trap/spawntrap/active";

    private final float damage = 15;

    public SpawnTrap() {
        super();
        setupAnimationComponent(0);
        setupHitboxComponent();
    }

    public void setupAnimationComponent(int a) {
        Animation active = AnimationBuilder.buildAnimation(this.active);
        if (a == 0) {
            new AnimationComponent(this, active);
        } else {
            int randomMonster = random.nextInt(3);
            switch (randomMonster) {
                case 0 -> new Skeleton();
                case 1 -> new Zombie();
                case 2 -> new Tot();
            }
        }
    }

    private void setupHitboxComponent() {
        new HitboxComponent(
                this,
                (you, other, direction) -> {
                    if (other.getClass() == Game.getHero().get().getClass()) {
                        setupAnimationComponent(1);
                    }
                },
                (you, other, direction) -> setupAnimationComponent(0));
    }
}
