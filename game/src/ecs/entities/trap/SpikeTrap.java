package ecs.entities.trap;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import ecs.entities.Trap;
import graphic.Animation;

public class SpikeTrap extends Trap {
    private final String inactive = "trap/spiketrap/inactive";
    private final String active = "trap/spiketrap/active";

    private final float damage = 15;

    public SpikeTrap() {
        super();
        setupAnimationComponent(0);
        setupHitboxComponent();

    }

    void setupAnimationComponent(int a) {
        Animation inactive = AnimationBuilder.buildAnimation(this.inactive);
        Animation active = AnimationBuilder.buildAnimation(this.active);
        if (a == 0) {
            new AnimationComponent(this, inactive);
        } else {
            new AnimationComponent(this, active);
        }

    }

    private void setupHitboxComponent() {
        new HitboxComponent(
            this,
            (you, other, direction) -> setupAnimationComponent(1),
            (you, other, direction) -> setupAnimationComponent(0));

    }
}
