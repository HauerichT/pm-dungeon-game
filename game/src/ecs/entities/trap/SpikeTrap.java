package ecs.entities.trap;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HealthComponent;
import ecs.components.HitboxComponent;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Entity;
import ecs.entities.Trap;
import graphic.Animation;
import starter.Game;

public class SpikeTrap extends Trap {
    private final String inactive = "trap/spiketrap/inactive";
    private final String active = "trap/spiketrap/active";
    private Entity collide;

    private final int dmg = 2;

    public SpikeTrap() {
        super();
        setupAnimationComponent(0);
        setupHitboxComponent();
    }

    /** Set up the animation component */
    public void setupAnimationComponent(int a) {
        Animation inactive = AnimationBuilder.buildAnimation(this.inactive);
        Animation active = AnimationBuilder.buildAnimation(this.active);
        if (a == 0) {
            new AnimationComponent(this, inactive);
        } else {
            new AnimationComponent(this, active);
        }
    }

    /** Set up the hitbox component */
    public void setupHitboxComponent() {
        new HitboxComponent(
                this,
                (you, other, direction) -> {
                    if (other.getClass() == Game.getHero().get().getClass()) {
                        setupAnimationComponent(1);
                        Game.getHero()
                                .get()
                                .getComponent(HealthComponent.class)
                                .ifPresent(
                                        hc -> {
                                            ((HealthComponent) hc)
                                                    .receiveHit(
                                                            new Damage(
                                                                    dmg,
                                                                    DamageType.PHYSICAL,
                                                                    null));
                                        });
                    }
                },
                (you, other, direction) -> {
                    setupAnimationComponent(0);
                });
    }
}
