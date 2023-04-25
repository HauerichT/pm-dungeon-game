package ecs.entities.trap;
import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HitboxComponent;
import ecs.entities.Trap;
import graphic.Animation;

public class SpawnTrap extends Trap {
    private final String inactive = "trap/spawntrap/inactive";

    private final float damage = 15;

    public SpawnTrap() {
        super();
        setupAnimationComponent(0);
        setupHitboxComponent();

    }

    void setupAnimationComponent(int a) {
        Animation inactive = AnimationBuilder.buildAnimation(this.inactive);
        if (a == 0) {
            new AnimationComponent(this, inactive);
        } else {

        }

    }

    private void setupHitboxComponent() {
        new HitboxComponent(
            this,
            (you, other, direction) -> setupAnimationComponent(1),
            (you, other, direction) -> setupAnimationComponent(0));

    }

}

