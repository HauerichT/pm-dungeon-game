package ecs.entities.trap;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import ecs.entities.Entity;
import ecs.entities.Trap;
import graphic.Animation;

public class TpTrap extends Trap{

    private final String active = "trap/tptrap/active";
    private Entity hero;

    private final float damage = 15;

    public TpTrap(Entity hero) {
        super();
        setupAnimationComponent(0);
        setupHitboxComponent();
        this.hero = hero;


    }

    void setupAnimationComponent(int a) {
        Animation active = AnimationBuilder.buildAnimation(this.active);
        if (a == 0) {
            new AnimationComponent(this, active);
        } else {
            new PositionComponent(hero);
        }

    }

    private void setupHitboxComponent() {
        new HitboxComponent(
            this,
            (you, other, direction) -> setupAnimationComponent(1),
            (you, other, direction) -> setupAnimationComponent(0));
    }
}



