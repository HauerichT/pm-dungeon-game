package ecs.entities.trap;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import ecs.entities.Entity;
import ecs.entities.Trap;
import graphic.Animation;
import starter.Game;

public class TpTrap extends Trap {

    private final String active = "trap/tptrap/active";
    private Entity hero;

    public TpTrap(Entity hero) {
        super();
        setupAnimationComponent(false);
        setupHitboxComponent();
        this.hero = hero;
    }

    /** Set up the animation component */
    public void setupAnimationComponent(boolean b) {
        Animation active = AnimationBuilder.buildAnimation(this.active);
        if (!b) {
            new AnimationComponent(this, active);
        } else {
            new PositionComponent(hero);
        }
    }

    /** Set up the hitbox component */
    public void setupHitboxComponent() {
        new HitboxComponent(
                this,
                (you, other, direction) -> {
                    if (other.getClass() == Game.getHero().get().getClass()) {
                        setupAnimationComponent(true);
                    }
                },
                (you, other, direction) -> setupAnimationComponent(false));
    }
}
