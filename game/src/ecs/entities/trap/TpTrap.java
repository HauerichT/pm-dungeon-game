package ecs.entities.trap;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import ecs.entities.Entity;
import ecs.entities.Trap;
import graphic.Animation;

public class TpTrap extends Trap{

    private final Entity hero;


    public TpTrap(Entity hero) {
        super();
        setupAnimationComponent(0);
        setupHitboxComponent();
        this.hero = hero;
    }

    void setupAnimationComponent(int a) {
        String inactive1 = "trap/tptrap/inactive";
        Animation inactive = AnimationBuilder.buildAnimation(inactive1);
        if (a == 0) {
            new AnimationComponent(this, inactive);
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



